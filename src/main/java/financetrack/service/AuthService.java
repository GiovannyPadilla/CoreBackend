package financetrack.service;

import financetrack.dto.AuthResponse;
import financetrack.dto.LoginRequest;
import financetrack.dto.RegisterRequest;
import financetrack.entity.Usuario;
import financetrack.exception.BusinessException;
import financetrack.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("El email ya está registrado");
        }

        validarPassword(request.getPassword());

        String rolAsignado = obtenerRolValido(
                request.getRol()
        );

        Usuario usuario = Usuario.builder()
                .nombreCompleto(request.getNombreCompleto())
                .email(request.getEmail().toLowerCase().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(rolAsignado)
                .activo(true)
                .build();

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(
                usuario.getEmail()
        );

        return AuthResponse.builder()
                .token(token)
                .email(usuario.getEmail())
                .nombreCompleto(usuario.getNombreCompleto())
                .rol(usuario.getRol())
                .build();
    }

    public AuthResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository
                .findByEmail(
                        request.getEmail()
                                .toLowerCase()
                                .trim()
                )
                .orElseThrow(() ->
                        new BusinessException(
                                "Credenciales inválidas"
                        )
                );

        if (!usuario.getActivo()) {
            throw new BusinessException(
                    "La cuenta está desactivada"
            );
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword()
        )) {
            throw new BusinessException(
                    "Credenciales inválidas"
            );
        }

        String token = jwtService.generateToken(
                usuario.getEmail()
        );

        return AuthResponse.builder()
                .token(token)
                .email(usuario.getEmail())
                .nombreCompleto(usuario.getNombreCompleto())
                .rol(usuario.getRol())
                .build();
    }

    private String obtenerRolValido(String rol) {

        if (rol == null || rol.isBlank()) {
            return "USER";
        }

        String rolNormalizado =
                rol.toUpperCase().trim();

        if (
                !rolNormalizado.equals("USER") &&
                        !rolNormalizado.equals("ADMIN")
        ) {
            throw new BusinessException(
                    "Rol inválido. Debe ser USER o ADMIN"
            );
        }

        return rolNormalizado;
    }

    /**
     * Validación backend del dato sensible: contraseña
     */
    private void validarPassword(String password) {

        if (password == null || password.length() < 8) {
            throw new BusinessException(
                    "La contraseña debe tener al menos 8 caracteres"
            );
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new BusinessException(
                    "La contraseña debe contener al menos una letra mayúscula"
            );
        }

        if (!password.matches(".*[0-9].*")) {
            throw new BusinessException(
                    "La contraseña debe contener al menos un número"
            );
        }

        if (password.matches(".*\\s.*")) {
            throw new BusinessException(
                    "La contraseña no puede contener espacios"
            );
        }
    }
}
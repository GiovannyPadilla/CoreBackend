package financetrack.service;

import financetrack.dto.CuentaRequest;
import financetrack.dto.CuentaResponse;
import financetrack.entity.Cuenta;
import financetrack.entity.Usuario;
import financetrack.exception.*;
import financetrack.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public CuentaResponse crear(CuentaRequest request, String email) {
        if (cuentaRepository.existsByNumeroCuenta(request.getNumeroCuenta())) {
            throw new BusinessException("El número de cuenta ya existe");
        }

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        Cuenta cuenta = Cuenta.builder()
                .nombreBanco(request.getNombreBanco())
                .numeroCuenta(request.getNumeroCuenta())
                .tipoCuenta(request.getTipoCuenta())
                .saldo(request.getSaldo())
                .moneda(request.getMoneda())
                .usuario(usuario)
                .build();

        return toResponse(cuentaRepository.save(cuenta));
    }

    public List<CuentaResponse> listarPorUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));
        return cuentaRepository.findByUsuarioId(usuario.getId())
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public void eliminar(Integer id, String email) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta", id));
        if (!cuenta.getUsuario().getEmail().equals(email)) {
            throw new BusinessException("No tienes permiso sobre esta cuenta");
        }
        cuentaRepository.delete(cuenta);
    }

    public CuentaResponse toResponse(Cuenta c) {
        return CuentaResponse.builder()
                .id(c.getId())
                .nombreBanco(c.getNombreBanco())
                .numeroCuenta(c.getNumeroCuenta())
                .tipoCuenta(c.getTipoCuenta())
                .saldo(c.getSaldo())
                .moneda(c.getMoneda())
                .createdAt(c.getCreatedAt())
                .build();
    }
}

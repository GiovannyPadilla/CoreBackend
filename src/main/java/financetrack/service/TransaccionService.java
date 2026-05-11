package financetrack.service;

import financetrack.dto.TransaccionRequest;
import financetrack.dto.TransaccionResponse;
import financetrack.entity.*;
import financetrack.exception.*;
import financetrack.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final CategoriaRepository categoriaRepository;
    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public TransaccionResponse crear(TransaccionRequest request, String email) {

        if (request.getMonto() == null || request.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El monto debe ser mayor a cero");
        }
        if (request.getMonto().scale() > 2) {
            throw new BusinessException("El monto no puede tener más de 2 decimales");
        }
        if (request.getMonto().compareTo(new BigDecimal("999999999.99")) > 0) {
            throw new BusinessException("El monto excede el límite permitido");
        }


        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", request.getCategoriaId()));

        Cuenta cuenta = cuentaRepository.findById(request.getCuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta", request.getCuentaId()));

        // Verificar que la cuenta pertenece al usuario autenticado
        if (!cuenta.getUsuario().getEmail().equals(email)) {
            throw new BusinessException("No tienes permiso sobre esta cuenta");
        }

        Transaccion t = Transaccion.builder()
                .descripcion(request.getDescripcion())
                .monto(request.getMonto())
                .fechaTransaccion(request.getFechaTransaccion())
                .tipo(request.getTipo())
                .notas(request.getNotas())
                .categoria(categoria)
                .cuenta(cuenta)
                .build();

        Transaccion saved = transaccionRepository.save(t);
        return toResponse(saved);
    }

    public List<TransaccionResponse> listarPorUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));
        return transaccionRepository
                .findByCuentaUsuarioIdOrderByFechaTransaccionDesc(usuario.getId())
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public void eliminar(Integer id, String email) {
        Transaccion t = transaccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción", id));

        if (!t.getCuenta().getUsuario().getEmail().equals(email)) {
            throw new BusinessException("No tienes permiso para eliminar esta transacción");
        }

        transaccionRepository.delete(t);
    }

    public TransaccionResponse toResponse(Transaccion t) {
        return TransaccionResponse.builder()
                .id(t.getId())
                .descripcion(t.getDescripcion())
                .monto(t.getMonto())
                .fechaTransaccion(t.getFechaTransaccion())
                .tipo(t.getTipo())
                .notas(t.getNotas())
                .categoriaId(t.getCategoria().getId())
                .categoriaNombre(t.getCategoria().getNombre())
                .categoriaColor(t.getCategoria().getColor())
                .categoriaIcono(t.getCategoria().getIcono())
                .cuentaId(t.getCuenta().getId())
                .cuentaNombreBanco(t.getCuenta().getNombreBanco())
                .createdAt(t.getCreatedAt())
                .build();
    }
}

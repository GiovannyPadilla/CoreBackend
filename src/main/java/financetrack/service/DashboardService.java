package financetrack.service;

import financetrack.dto.DashboardResponse;
import financetrack.dto.TransaccionResponse;
import financetrack.entity.Usuario;
import financetrack.exception.BusinessException;
import financetrack.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UsuarioRepository usuarioRepository;
    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;
    private final AlertaRepository alertaRepository;
    private final TransaccionService transaccionService;

    public DashboardResponse getDashboard(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        Integer uid = usuario.getId();

        BigDecimal ingresos = transaccionRepository.sumMontoByUsuarioIdAndTipo(uid, "INGRESO");
        BigDecimal egresos  = transaccionRepository.sumMontoByUsuarioIdAndTipo(uid, "EGRESO");
        BigDecimal balance  = ingresos.subtract(egresos);

        List<TransaccionResponse> ultimasTransacciones = transaccionRepository
                .findRecentByUsuarioId(uid)
                .stream().limit(5)
                .map(transaccionService::toResponse)
                .collect(Collectors.toList());

        return DashboardResponse.builder()
                .totalIngresos(ingresos)
                .totalEgresos(egresos)
                .balance(balance)
                .totalCuentas(cuentaRepository.findByUsuarioId(uid).size())
                .alertasNoLeidas(alertaRepository.countByUsuarioIdAndLeidaFalse(uid))
                .ultimasTransacciones(ultimasTransacciones)
                .build();
    }
}

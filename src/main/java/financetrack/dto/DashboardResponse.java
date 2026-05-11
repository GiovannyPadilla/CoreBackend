package financetrack.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import financetrack.dto.TransaccionResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private BigDecimal totalIngresos;
    private BigDecimal totalEgresos;
    private BigDecimal balance;
    private long totalCuentas;
    private long alertasNoLeidas;
    private List<TransaccionResponse> ultimasTransacciones;
}

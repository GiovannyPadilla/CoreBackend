package financetrack.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponse {
    private Integer id;
    private String nombreBanco;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldo;
    private String moneda;
    private LocalDateTime createdAt;
}

package financetrack.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
public class CuentaRequest {

    @NotBlank(message = "El nombre del banco es obligatorio")
    @Size(max = 100)
    private String nombreBanco;

    @NotBlank(message = "El número de cuenta es obligatorio")
    @Size(min = 5, max = 50, message = "El número de cuenta debe tener entre 5 y 50 caracteres")
    private String numeroCuenta;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    @Pattern(regexp = "CORRIENTE|AHORROS|INVERSIÓN|OTRO",
             message = "Tipo inválido. Valores: CORRIENTE, AHORROS, INVERSIÓN, OTRO")
    private String tipoCuenta;

    @DecimalMin(value = "0.0", message = "El saldo no puede ser negativo")
    private BigDecimal saldo = BigDecimal.ZERO;

    @Size(max = 10)
    private String moneda = "USD";
}

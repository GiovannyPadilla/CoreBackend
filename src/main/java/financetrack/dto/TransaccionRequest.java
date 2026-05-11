package financetrack.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransaccionRequest {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255)
    private String descripcion;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fechaTransaccion;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "INGRESO|EGRESO|TRANSFERENCIA",
             message = "Tipo inválido. Valores permitidos: INGRESO, EGRESO, TRANSFERENCIA")
    private String tipo;

    @Size(max = 500)
    private String notas;

    @NotNull(message = "La categoría es obligatoria")
    private Integer categoriaId;

    @NotNull(message = "La cuenta es obligatoria")
    private Integer cuentaId;
}

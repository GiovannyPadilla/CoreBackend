package financetrack.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
public class PresupuestoRequest {

    @NotNull @DecimalMin("0.01")
    private BigDecimal monthlyLimit;

    @NotNull @Min(1) @Max(12)
    private Integer monthNumber;

    @NotNull @Min(2000)
    private Integer yearNumber;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoryId;
}

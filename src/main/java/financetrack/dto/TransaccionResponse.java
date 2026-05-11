package financetrack.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TransaccionResponse {
    private Integer id;
    private String descripcion;
    private BigDecimal monto;
    private LocalDate fechaTransaccion;
    private String tipo;
    private String notas;
    private String categoriaNombre;
    private String categoriaColor;
    private String categoriaIcono;
    private Integer categoriaId;
    private String cuentaNombreBanco;
    private Integer cuentaId;
    private LocalDateTime createdAt;
}

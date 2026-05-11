package financetrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "predicciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prediccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull @DecimalMin("0.0")
    @Column(name = "monto_predicho", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoPredicho;

    @NotNull @Min(1) @Max(12)
    @Column(name = "prediction_month", nullable = false)
    private Integer predictionMonth;

    @NotNull @Min(2000)
    @Column(name = "prediction_year", nullable = false)
    private Integer predictionYear;

    @Digits(integer = 3, fraction = 2)
    @Column(name = "porcentaje_confianza", precision = 5, scale = 2)
    private BigDecimal porcentajeConfianza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
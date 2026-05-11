package financetrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cuentas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del banco es obligatorio")
    @Size(max = 100)
    @Column(name = "nombre_banco", nullable = false)
    private String nombreBanco;

    @NotBlank(message = "El número de cuenta es obligatorio")
    @Size(max = 50)
    @Column(name = "numero_cuenta", nullable = false, unique = true)
    private String numeroCuenta;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    @Size(max = 30)
    @Column(name = "tipo_cuenta", nullable = false)
    private String tipoCuenta;

    @DecimalMin(value = "0.0", message = "El saldo no puede ser negativo")
    @Column(precision = 12, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Size(max = 10)
    private String moneda = "USD";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
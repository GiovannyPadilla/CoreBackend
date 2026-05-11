package financetrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank @Size(max = 150)
    @Column(nullable = false)
    private String titulo;

    @NotBlank @Size(max = 500)
    @Column(nullable = false)
    private String mensaje;

    @Size(max = 50)
    @Column(name = "tipo_alerta")
    private String tipoAlerta;

    @Size(max = 20)
    private String severidad;

    @Column(nullable = false)
    private Boolean leida = false;

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

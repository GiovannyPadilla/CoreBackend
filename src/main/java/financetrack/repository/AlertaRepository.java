package financetrack.repository;

import financetrack.entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Integer> {
    List<Alerta> findByUsuarioIdOrderByCreatedAtDesc(Integer usuarioId);
    long countByUsuarioIdAndLeidaFalse(Integer usuarioId);
}
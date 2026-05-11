package financetrack.repository;


import financetrack.entity.Prediccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrediccionRepository extends JpaRepository<Prediccion, Integer> {
    List<Prediccion> findByUsuarioIdOrderByPredictionYearDescPredictionMonthDesc(Integer usuarioId);
}

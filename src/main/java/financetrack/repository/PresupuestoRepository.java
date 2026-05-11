package financetrack.repository;



import financetrack.entity.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Integer> {
    List<Presupuesto> findByUsuarioId(Integer usuarioId);
    List<Presupuesto> findByUsuarioIdAndMonthNumberAndYearNumber(Integer usuarioId, int month, int year);
}

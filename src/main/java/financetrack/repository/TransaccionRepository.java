package financetrack.repository;

import financetrack.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {

    List<Transaccion> findByCuentaUsuarioIdOrderByFechaTransaccionDesc(Integer usuarioId);

    @Query("SELECT COALESCE(SUM(t.monto), 0) FROM Transaccion t " +
            "WHERE t.cuenta.usuario.id = :usuarioId AND t.tipo = :tipo")
    BigDecimal sumMontoByUsuarioIdAndTipo(@Param("usuarioId") Integer usuarioId,
                                          @Param("tipo") String tipo);

    @Query("SELECT t FROM Transaccion t WHERE t.cuenta.usuario.id = :usuarioId " +
            "ORDER BY t.fechaTransaccion DESC")
    List<Transaccion> findRecentByUsuarioId(@Param("usuarioId") Integer usuarioId);
}
package financetrack.repository;

import financetrack.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
    List<Cuenta> findByUsuarioId(Integer usuarioId);
    boolean existsByNumeroCuenta(String numeroCuenta);
}

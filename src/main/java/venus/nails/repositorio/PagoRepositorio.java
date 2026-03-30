package venus.nails.repositorio;
import venus.nails.modelo.Pago;
import venus.nails.modelo.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * Repositorio JPA para la entidad Pago.
 * @author Carolina
 * @version 1.0
 */
@Repository
public interface PagoRepositorio extends JpaRepository<Pago, Integer> {
    /** Lista pagos asociados a una cita especifica */
    List<Pago> findByCita(Cita cita);
}
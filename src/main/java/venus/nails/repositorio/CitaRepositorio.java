package venus.nails.repositorio;
import venus.nails.modelo.Cita;
import venus.nails.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * Repositorio JPA para la entidad Cita.
 * @author Carolina
 * @version 1.0
 */
@Repository
public interface CitaRepositorio extends JpaRepository<Cita, Integer> {
    /** Lista citas de un usuario ordenadas por fecha y hora */
    List<Cita> findByUsuarioOrderByFechaAscHoraInicioAsc(Usuario usuario);
    /** Lista todas las citas ordenadas por fecha y hora */
    List<Cita> findAllByOrderByFechaAscHoraInicioAsc();
    /** Lista citas por estado */
    List<Cita> findByEstado(String estado);
}
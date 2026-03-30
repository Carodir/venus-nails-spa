package venus.nails.repositorio;
import venus.nails.modelo.Horario;
import venus.nails.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * Repositorio JPA para la entidad Horario.
 * @author Carolina
 * @version 1.0
 */
@Repository
public interface HorarioRepositorio extends JpaRepository<Horario, Integer> {
    /** Lista horarios de un manicurista especifico */
    List<Horario> findByManicurista(Usuario manicurista);
}
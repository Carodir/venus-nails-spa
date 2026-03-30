package venus.nails.repositorio;
import venus.nails.modelo.Resena;
import venus.nails.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * Repositorio JPA para la entidad Resena.
 * @author Carolina
 * @version 1.0
 */
@Repository
public interface ResenaRepositorio extends JpaRepository<Resena, Integer> {
    /** Lista resenas de un usuario especifico */
    List<Resena> findByUsuario(Usuario usuario);
    /** Lista resenas ordenadas por fecha descendente */
    List<Resena> findAllByOrderByFechaDesc();
}
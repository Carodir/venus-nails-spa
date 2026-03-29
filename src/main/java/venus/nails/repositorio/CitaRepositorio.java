package venus.nails.repositorio;

import venus.nails.modelo.Cita;
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

    /** Lista citas de un usuario especifico */
    List<Cita> findByIdUsuario(int idUsuario);

    /** Lista citas por estado */
    List<Cita> findByEstado(String estado);
}
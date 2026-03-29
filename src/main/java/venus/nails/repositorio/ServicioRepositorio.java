package venus.nails.repositorio;

import venus.nails.modelo.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Servicio.
 * Hereda findAll(), findById(), save(), deleteById() de JpaRepository.
 * @author Carolina
 * @version 1.0
 */
@Repository
public interface ServicioRepositorio extends JpaRepository<Servicio, Integer> {
    // Los metodos base de JpaRepository son suficientes para este modulo
}
package venus.nails.repositorio;

import venus.nails.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Usuario.
 * Spring genera automaticamente la implementacion de todos los metodos.
 * @author Carolina
 * @version 1.0
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    /** Busca usuario por correo - usado en el login */
    Optional<Usuario> findByCorreo(String correo);

    /** Verifica si el correo ya existe - evita duplicados */
    boolean existsByCorreo(String correo);

    /** Lista usuarios por rol */
    List<Usuario> findByRol(String rol);
}
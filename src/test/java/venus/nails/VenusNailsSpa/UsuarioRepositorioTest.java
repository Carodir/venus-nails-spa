package venus.nails.VenusNailsSpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import venus.nails.modelo.Usuario;
import venus.nails.repositorio.UsuarioRepositorio;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioRepositorioTest {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Test
    @DisplayName("PT-01: Verificar que existen usuarios en la base de datos")
    void testListarUsuarios() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        assertNotNull(usuarios, "La lista de usuarios no debe ser nula");
        assertFalse(usuarios.isEmpty(), "Debe haber al menos un usuario registrado");
        System.out.println("✅ Usuarios encontrados: " + usuarios.size());
    }

    @Test
    @DisplayName("PT-02: Verificar búsqueda de usuario por correo existente")
    void testBuscarPorCorreoExistente() {
        Optional<Usuario> usuario = usuarioRepositorio.findByCorreo("admin@venusnails.com");
        assertTrue(usuario.isPresent(), "El usuario admin debe existir");
        assertEquals("admin", usuario.get().getRol(), "El rol debe ser admin");
        System.out.println("✅ Usuario encontrado: " + usuario.get().getNombre());
    }

    @Test
    @DisplayName("PT-03: Verificar búsqueda de usuario por correo inexistente")
    void testBuscarPorCorreoInexistente() {
        Optional<Usuario> usuario = usuarioRepositorio.findByCorreo("noexiste@test.com");
        assertFalse(usuario.isPresent(), "No debe encontrar un usuario inexistente");
        System.out.println("✅ Correo inexistente manejado correctamente");
    }

    @Test
    @DisplayName("PT-04: Verificar existencia de correo duplicado")
    void testExistsByCorreo() {
        boolean existe = usuarioRepositorio.existsByCorreo("admin@venusnails.com");
        assertTrue(existe, "El correo del admin debe existir");
        System.out.println("✅ Validación de correo duplicado funciona correctamente");
    }

    @Test
    @DisplayName("PT-05: Verificar usuarios por rol admin")
    void testFindByRol() {
        List<Usuario> admins = usuarioRepositorio.findByRol("admin");
        assertNotNull(admins, "La lista no debe ser nula");
        assertFalse(admins.isEmpty(), "Debe existir al menos un administrador");
        System.out.println("✅ Administradores encontrados: " + admins.size());
    }
}
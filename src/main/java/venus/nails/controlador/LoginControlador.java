package venus.nails.controlador;
import venus.nails.modelo.Usuario;
import venus.nails.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
/**
 * Controlador de autenticacion del sistema Venus Nails Spa.
 * Gestiona el inicio y cierre de sesion de todos los tipos de usuario.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Controller
public class LoginControlador {
    /** Repositorio para consultar usuarios en la base de datos */
    @Autowired
    private UsuarioRepositorio usuarioRepo;
    /**
     * Muestra el formulario de login.
     * @return vista login.html
     */
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }
    /**
     * Procesa las credenciales del formulario de login.
     * @param correo correo del formulario
     * @param contrasena contrasena del formulario
     * @param session sesion HTTP para guardar el usuario autenticado
     * @param model modelo para enviar mensajes de error a la vista
     * @return redireccion al panel segun rol, o login con error
     */
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
                                @RequestParam String contrasena,
                                HttpSession session,
                                Model model) {
        // Buscar usuario por correo en la base de datos
        Optional<Usuario> resultado = usuarioRepo.findByCorreo(correo);
        // Verificar si el correo existe
        if (resultado.isEmpty()) {
            model.addAttribute("error", "Correo no registrado");
            return "login";
        }
        Usuario usuario = resultado.get();
        // Verificar contrasena directamente
        if (!contrasena.equals(usuario.getContrasena())) {
            model.addAttribute("error", "Contrasena incorrecta");
            return "login";
        }
        // Guardar usuario en sesion
        session.setAttribute("usuarioLogueado", usuario);
        session.setAttribute("rol", usuario.getRol());
        // Redirigir segun el rol del usuario
        return switch (usuario.getRol()) {
            case "admin" -> "redirect:/admin/panel";
            case "manicurista" -> "redirect:/manicurista/panel";
            default -> "redirect:/cliente/panel";
        };
    }
    /**
     * Cierra la sesion del usuario actual.
     * @return redireccion al login
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
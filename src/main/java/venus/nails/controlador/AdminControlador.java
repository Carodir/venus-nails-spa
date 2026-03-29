package venus.nails.controlador;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import venus.nails.modelo.Usuario;

/**
 * Controlador para el panel de administracion.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {

    /**
     * Muestra el panel principal del administrador.
     * @param session sesion HTTP para verificar usuario logueado
     * @param model modelo para pasar datos a la vista
     * @return vista admin/panel.html
     */
    @GetMapping("/panel")
    public String mostrarPanel(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        return "admin/panel";
    }
}
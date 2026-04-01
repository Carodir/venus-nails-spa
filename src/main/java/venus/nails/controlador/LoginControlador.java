package venus.nails.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador de autenticacion del sistema Venus Nails Spa.
 * Spring Security maneja el POST de login y el logout.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Controller
public class LoginControlador {

    /**
     * Muestra el formulario de login.
     * @return vista login.html
     */
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }
}
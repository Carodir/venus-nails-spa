package venus.nails.controlador;
import venus.nails.modelo.Usuario;
import venus.nails.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/**
 * Controlador para el modulo de gestion de usuarios.
 * Permite registrar nuevos usuarios y listar los existentes.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {
    /** Repositorio de usuarios */
    @Autowired
    private UsuarioRepositorio usuarioRepo;
    /**
     * Lista todos los usuarios. Solo para admin.
     * @param model modelo para pasar datos a la vista
     * @return vista usuarios/listar.html
     */
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioRepo.findAll());
        return "usuarios/listar";
    }
    /**
     * Muestra el formulario de registro de nuevo usuario.
     * @param model modelo para pasar objeto vacio a la vista
     * @return vista usuarios/registrar.html
     */
    @GetMapping("/registrar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/registrar";
    }
    /**
     * Procesa el formulario de registro.
     * @param usuario objeto usuario con datos del formulario
     * @param model modelo para enviar errores a la vista
     * @return redireccion a listar o formulario con error
     */
    @PostMapping("/registrar")
    public String registrar(@ModelAttribute Usuario usuario, Model model) {
        // Verificar que el correo no este ya registrado
        if (usuarioRepo.existsByCorreo(usuario.getCorreo())) {
            model.addAttribute("error", "El correo ya esta registrado");
            return "usuarios/registrar";
        }
        // Guardar en la base de datos directamente
        usuarioRepo.save(usuario);
        return "redirect:/usuarios/listar";
    }
}
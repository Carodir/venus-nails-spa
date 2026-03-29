package venus.nails.controlador;

import venus.nails.modelo.Horario;
import venus.nails.modelo.Usuario;
import venus.nails.repositorio.HorarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador para el modulo de horarios de Venus Nails Spa.
 * Permite agregar y listar los horarios disponibles de los manicuristas.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Controller
@RequestMapping("/horarios")
public class HorarioControlador {

    /** Repositorio de horarios */
    @Autowired
    private HorarioRepositorio horarioRepo;

    /**
     * Lista todos los horarios disponibles.
     * @param model modelo para pasar datos a la vista
     * @return vista horarios/listar.html
     */
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("horarios", horarioRepo.findAll());
        return "horarios/listar";
    }

    /**
     * Muestra el formulario para agregar nuevo horario.
     * @param model modelo con objeto vacio
     * @return vista horarios/agregar.html
     */
    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("horario", new Horario());
        return "horarios/agregar";
    }

    /**
     * Procesa el formulario de nuevo horario.
     * @param horario objeto horario con datos del formulario
     * @param session sesion con el usuario autenticado
     * @return redireccion a listar horarios
     */
    @PostMapping("/agregar")
    public String agregar(@ModelAttribute Horario horario, HttpSession session) {
        // Obtener el manicurista logueado para asignar el id automaticamente
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        horario.setIdUsuario(usuario.getIdUsuario());

        // Guardar el horario en la base de datos
        horarioRepo.save(horario);
        return "redirect:/horarios/listar";
    }
}
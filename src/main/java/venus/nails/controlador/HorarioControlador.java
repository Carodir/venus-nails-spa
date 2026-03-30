package venus.nails.controlador;
import venus.nails.modelo.Horario;
import venus.nails.modelo.Usuario;
import venus.nails.repositorio.HorarioRepositorio;
import venus.nails.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/**
 * Controlador para el modulo de horarios de Venus Nails Spa.
 * Permite agregar, eliminar y listar los horarios de los manicuristas.
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
    /** Repositorio de usuarios para obtener manicuristas */
    @Autowired
    private UsuarioRepositorio usuarioRepo;
    /**
     * Redirige /horarios a /horarios/listar
     * @return redireccion a listar horarios
     */
    @GetMapping
    public String index() {
        return "redirect:/horarios/listar";
    }
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
     * @param model modelo con lista de manicuristas
     * @return vista horarios/agregar.html
     */
    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("manicuristas", usuarioRepo.findByRol("manicurista"));
        return "horarios/agregar";
    }
    /**
     * Procesa el formulario de nuevo horario.
     * @param idUsuario id del manicurista seleccionado
     * @param diaSemana dia de la semana
     * @param horaInicio hora de inicio
     * @param horaFin hora de fin
     * @return redireccion a listar horarios
     */
    @PostMapping("/agregar")
    public String agregar(@RequestParam int idUsuario,
                          @RequestParam String diaSemana,
                          @RequestParam String horaInicio,
                          @RequestParam String horaFin) {
        Usuario manicurista = usuarioRepo.findById(idUsuario).orElseThrow();
        Horario horario = new Horario();
        horario.setManicurista(manicurista);
        horario.setDiaSemana(diaSemana);
        horario.setHoraInicio(java.time.LocalTime.parse(horaInicio));
        horario.setHoraFin(java.time.LocalTime.parse(horaFin));
        horarioRepo.save(horario);
        return "redirect:/horarios/listar";
    }
    /**
     * Elimina un horario por su ID.
     * @param id identificador del horario a eliminar
     * @return redireccion a listar horarios
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        horarioRepo.deleteById(id);
        return "redirect:/horarios/listar";
    }
}
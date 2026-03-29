package venus.nails.controlador;
import venus.nails.modelo.Servicio;
import venus.nails.repositorio.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/**
 * Controlador para el modulo de servicios de Venus Nails Spa.
 * Permite agregar, modificar, eliminar y listar los servicios ofrecidos.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Controller
@RequestMapping("/servicios")
public class ServicioControlador {
    /** Repositorio de servicios */
    @Autowired
    private ServicioRepositorio servicioRepo;
    /**
     * Redirige /servicios a /servicios/listar
     * @return redireccion a listar servicios
     */
    @GetMapping
    public String index() {
        return "redirect:/servicios/listar";
    }
    /**
     * Lista todos los servicios disponibles.
     * @param model modelo para pasar datos a la vista
     * @return vista servicios/listar.html
     */
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("servicios", servicioRepo.findAll());
        return "servicios/listar";
    }
    /**
     * Muestra el formulario para agregar nuevo servicio.
     * @param model modelo con objeto vacio
     * @return vista servicios/agregar.html
     */
    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "servicios/agregar";
    }
    /**
     * Procesa el formulario para agregar nuevo servicio.
     * @param servicio objeto servicio con datos del formulario
     * @return redireccion a listar servicios
     */
    @PostMapping("/agregar")
    public String agregar(@ModelAttribute Servicio servicio) {
        servicioRepo.save(servicio);
        return "redirect:/servicios/listar";
    }
    /**
     * Muestra el formulario para modificar un servicio existente.
     * @param id identificador del servicio
     * @param model modelo con datos del servicio
     * @return vista servicios/modificar.html
     */
    @GetMapping("/modificar/{id}")
    public String mostrarModificar(@PathVariable int id, Model model) {
        Servicio servicio = servicioRepo.findById(id).orElseThrow();
        model.addAttribute("servicio", servicio);
        return "servicios/modificar";
    }
    /**
     * Procesa la modificacion de un servicio.
     * @param id identificador del servicio
     * @param servicio objeto con datos actualizados
     * @return redireccion a listar servicios
     */
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable int id, @ModelAttribute Servicio servicio) {
        servicio.setIdServicio(id);
        servicioRepo.save(servicio);
        return "redirect:/servicios/listar";
    }
    /**
     * Elimina un servicio por su ID.
     * @param id identificador del servicio a eliminar
     * @return redireccion a listar servicios
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        servicioRepo.deleteById(id);
        return "redirect:/servicios/listar";
    }
}
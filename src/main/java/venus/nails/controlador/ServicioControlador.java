package venus.nails.controlador;

import venus.nails.modelo.Servicio;
import venus.nails.repositorio.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para el modulo de servicios de Venus Nails Spa.
 * Permite agregar y listar los servicios ofrecidos.
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
        // Guardar el nuevo servicio en la base de datos
        servicioRepo.save(servicio);
        return "redirect:/servicios/listar";
    }
}
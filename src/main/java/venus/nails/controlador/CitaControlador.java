package venus.nails.controlador;

import venus.nails.modelo.Cita;
import venus.nails.modelo.Servicio;
import venus.nails.modelo.Usuario;
import venus.nails.repositorio.CitaRepositorio;
import venus.nails.repositorio.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 * Controlador para el modulo de citas de Venus Nails Spa.
 * Los administradores ven todas las citas, los clientes solo las suyas.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Controller
@RequestMapping("/citas")
public class CitaControlador {

    /** Repositorio de citas */
    @Autowired
    private CitaRepositorio citaRepo;

    /** Repositorio de servicios para calcular hora fin */
    @Autowired
    private ServicioRepositorio servicioRepo;

    /**
     * Lista citas segun el rol del usuario en sesion.
     * @param session sesion con el usuario autenticado
     * @param model modelo para pasar datos a la vista
     * @return vista citas/listar.html
     */
    @GetMapping("/listar")
    public String listar(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        List<Cita> citas;

        // Admin ve todas las citas, cliente solo las suyas
        if ("admin".equals(usuario.getRol())) {
            citas = citaRepo.findAll();
        } else {
            citas = citaRepo.findByIdUsuario(usuario.getIdUsuario());
        }

        model.addAttribute("citas", citas);
        return "citas/listar";
    }

    /**
     * Muestra el formulario para agendar nueva cita.
     * @param model modelo con lista de servicios disponibles
     * @return vista citas/agendar.html
     */
    @GetMapping("/agendar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("servicios", servicioRepo.findAll());
        return "citas/agendar";
    }

    /**
     * Procesa el agendamiento de una nueva cita.
     * @param cita objeto cita con datos del formulario
     * @param session sesion con el usuario autenticado
     * @return redireccion a listar citas
     */
    @PostMapping("/agendar")
    public String agendar(@ModelAttribute Cita cita, HttpSession session) {
        // Obtener el servicio para calcular la hora de fin
        Servicio servicio = servicioRepo.findById(cita.getIdServicio()).orElseThrow();

        // Calcular hora fin = hora inicio + duracion del servicio en minutos
        cita.setHoraFin(cita.getHoraInicio().plusMinutes(servicio.getDuracion()));

        // Establecer estado inicial
        cita.setEstado("Pendiente");
        citaRepo.save(cita);

        return "redirect:/citas/listar";
    }

    /**
     * Cancela una cita por su ID.
     * @param id identificador de la cita a cancelar
     * @return redireccion a listar citas
     */
    @PostMapping("/cancelar/{id}")
    public String cancelar(@PathVariable int id) {
        Cita cita = citaRepo.findById(id).orElseThrow();
        cita.setEstado("Cancelada");
        citaRepo.save(cita);
        return "redirect:/citas/listar";
    }
}
package venus.nails.controlador;
import venus.nails.modelo.Cita;
import venus.nails.modelo.Servicio;
import venus.nails.modelo.Usuario;
import venus.nails.repositorio.CitaRepositorio;
import venus.nails.repositorio.ServicioRepositorio;
import venus.nails.repositorio.UsuarioRepositorio;
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
    /** Repositorio de servicios */
    @Autowired
    private ServicioRepositorio servicioRepo;
    /** Repositorio de usuarios */
    @Autowired
    private UsuarioRepositorio usuarioRepo;
    /**
     * Redirige /citas a /citas/listar
     * @return redireccion a listar citas
     */
    @GetMapping
    public String index() {
        return "redirect:/citas/listar";
    }
    /**
     * Lista citas segun el rol del usuario en sesion, ordenadas por fecha y hora.
     * @param session sesion con el usuario autenticado
     * @param model modelo para pasar datos a la vista
     * @return vista citas/listar.html
     */
    @GetMapping("/listar")
    public String listar(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        List<Cita> citas;
        if ("admin".equals(usuario.getRol())) {
            citas = citaRepo.findAllByOrderByFechaAscHoraInicioAsc();
        } else {
            citas = citaRepo.findByUsuarioOrderByFechaAscHoraInicioAsc(usuario);
        }
        model.addAttribute("citas", citas);
        return "citas/listar";
    }
    /**
     * Muestra el formulario para agendar nueva cita.
     * @param model modelo con lista de servicios y usuarios disponibles
     * @return vista citas/agendar.html
     */
    @GetMapping("/agendar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("servicios", servicioRepo.findAll());
        model.addAttribute("usuarios", usuarioRepo.findAll());
        return "citas/agendar";
    }
    /**
     * Procesa el agendamiento de una nueva cita.
     * @param idUsuario id del usuario seleccionado
     * @param idServicio id del servicio seleccionado
     * @param fecha fecha de la cita
     * @param horaInicio hora de inicio de la cita
     * @return redireccion a listar citas
     */
    @PostMapping("/agendar")
    public String agendar(@RequestParam int idUsuario,
                          @RequestParam int idServicio,
                          @RequestParam String fecha,
                          @RequestParam String horaInicio) {
        Usuario usuario = usuarioRepo.findById(idUsuario).orElseThrow();
        Servicio servicio = servicioRepo.findById(idServicio).orElseThrow();
        Cita cita = new Cita();
        cita.setUsuario(usuario);
        cita.setServicio(servicio);
        cita.setFecha(java.time.LocalDate.parse(fecha));
        cita.setHoraInicio(java.time.LocalTime.parse(horaInicio));
        cita.setHoraFin(java.time.LocalTime.parse(horaInicio).plusMinutes(servicio.getDuracion()));
        cita.setEstado("Pendiente");
        citaRepo.save(cita);
        return "redirect:/citas/listar";
    }
    /**
     * Muestra formulario para modificar una cita existente.
     * @param id identificador de la cita
     * @param model modelo con datos de la cita
     * @return vista citas/modificar.html
     */
    @GetMapping("/modificar/{id}")
    public String mostrarModificar(@PathVariable int id, Model model) {
        Cita cita = citaRepo.findById(id).orElseThrow();
        model.addAttribute("cita", cita);
        model.addAttribute("servicios", servicioRepo.findAll());
        return "citas/modificar";
    }
    /**
     * Procesa la modificacion de una cita.
     * @param id identificador de la cita
     * @param idServicio id del servicio seleccionado
     * @param fecha fecha actualizada
     * @param horaInicio hora de inicio actualizada
     * @return redireccion a listar citas
     */
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable int id,
                            @RequestParam int idServicio,
                            @RequestParam String fecha,
                            @RequestParam String horaInicio) {
        Cita citaExistente = citaRepo.findById(id).orElseThrow();
        Servicio servicio = servicioRepo.findById(idServicio).orElseThrow();
        citaExistente.setFecha(java.time.LocalDate.parse(fecha));
        citaExistente.setHoraInicio(java.time.LocalTime.parse(horaInicio));
        citaExistente.setHoraFin(java.time.LocalTime.parse(horaInicio).plusMinutes(servicio.getDuracion()));
        citaExistente.setServicio(servicio);
        citaRepo.save(citaExistente);
        return "redirect:/citas/listar";
    }
    /**
     * Confirma una cita por su ID.
     * @param id identificador de la cita a confirmar
     * @return redireccion a listar citas
     */
    @PostMapping("/confirmar/{id}")
    public String confirmar(@PathVariable int id) {
        Cita cita = citaRepo.findById(id).orElseThrow();
        cita.setEstado("Confirmada");
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
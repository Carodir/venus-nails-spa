package venus.nails.controlador;

import venus.nails.modelo.Cita;
import venus.nails.modelo.Resena;
import venus.nails.modelo.Servicio;
import venus.nails.modelo.Usuario;
import venus.nails.repositorio.CitaRepositorio;
import venus.nails.repositorio.ResenaRepositorio;
import venus.nails.repositorio.ServicioRepositorio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private CitaRepositorio citaRepo;

    @Autowired
    private ServicioRepositorio servicioRepo;

    @Autowired
    private ResenaRepositorio resenaRepo;

    // Panel principal del cliente
    @GetMapping("/panel")
    public String panel(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";
        if (!usuario.getRol().equals("cliente")) return "redirect:/login";

        List<Cita> citas = citaRepo.findByUsuarioOrderByFechaAscHoraInicioAsc(usuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("citas", citas);
        return "cliente/panel";
    }

    // Formulario agendar cita
    @GetMapping("/agendar")
    public String agendarForm(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";
        if (!usuario.getRol().equals("cliente")) return "redirect:/login";

        model.addAttribute("servicios", servicioRepo.findAll());
        model.addAttribute("horas", generarHoras());
        return "cliente/agendar";
    }

    // Guardar cita del cliente
    @PostMapping("/agendar")
    public String agendarCita(@RequestParam int idServicio,
                               @RequestParam String fecha,
                               @RequestParam String horaInicio,
                               HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        Servicio servicio = servicioRepo.findById(idServicio).orElseThrow();

        Cita cita = new Cita();
        cita.setUsuario(usuario);
        cita.setServicio(servicio);
        cita.setFecha(LocalDate.parse(fecha));
        cita.setHoraInicio(LocalTime.parse(horaInicio));
        cita.setHoraFin(LocalTime.parse(horaInicio).plusMinutes(servicio.getDuracion()));
        cita.setEstado("Pendiente");
        citaRepo.save(cita);

        return "redirect:/cliente/panel";
    }

    // Ver reseñas del cliente
    @GetMapping("/resenas")
    public String resenas(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";
        if (!usuario.getRol().equals("cliente")) return "redirect:/login";

        List<Resena> resenas = resenaRepo.findByUsuario(usuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("resenas", resenas);
        model.addAttribute("servicios", servicioRepo.findAll());
        return "cliente/resenas";
    }

    // Guardar reseña del cliente
    @PostMapping("/resenas/agregar")
    public String agregarResena(@RequestParam String comentario,
                                 @RequestParam int calificacion,
                                 HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        Resena resena = new Resena();
        resena.setUsuario(usuario);
        resena.setComentario(comentario);
        resena.setCalificacion(calificacion);
        resena.setFecha(LocalDate.now());
        resenaRepo.save(resena);

        return "redirect:/cliente/resenas";
    }

    // Genera bloques de 30 min de 9AM a 7PM
    private List<String> generarHoras() {
        List<String> horas = new ArrayList<>();
        for (int h = 9; h < 19; h++) {
            horas.add(String.format("%02d:00", h));
            horas.add(String.format("%02d:30", h));
        }
        return horas;
    }
}
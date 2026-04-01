package venus.nails.controlador;

import venus.nails.modelo.Cita;
import venus.nails.modelo.Horario;
import venus.nails.modelo.Usuario;
import venus.nails.repositorio.CitaRepositorio;
import venus.nails.repositorio.HorarioRepositorio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/manicurista")
public class ManicuristaControlador {

    @Autowired
    private CitaRepositorio citaRepo;

    @Autowired
    private HorarioRepositorio horarioRepo;

    // Panel principal
    @GetMapping("/panel")
    public String panel(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";
        if (!usuario.getRol().equals("manicurista")) return "redirect:/login";

        // Citas del día de hoy
        List<Cita> citasHoy = citaRepo.findByUsuarioOrderByFechaAscHoraInicioAsc(usuario)
                .stream()
                .filter(c -> c.getFecha().equals(LocalDate.now()))
                .toList();

        // Todos sus horarios
        List<Horario> horarios = horarioRepo.findByManicurista(usuario);

        model.addAttribute("usuario", usuario);
        model.addAttribute("citasHoy", citasHoy);
        model.addAttribute("horarios", horarios);
        return "manicurista/panel";
    }

    // Confirmar cita
    @PostMapping("/confirmar/{id}")
    public String confirmar(@PathVariable int id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        Cita cita = citaRepo.findById(id).orElseThrow();
        cita.setEstado("Confirmada");
        citaRepo.save(cita);
        return "redirect:/manicurista/panel";
    }

    // Cancelar cita
    @PostMapping("/cancelar/{id}")
    public String cancelar(@PathVariable int id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        Cita cita = citaRepo.findById(id).orElseThrow();
        cita.setEstado("Cancelada");
        citaRepo.save(cita);
        return "redirect:/manicurista/panel";
    }
}
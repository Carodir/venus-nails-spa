package venus.nails.controlador;
import venus.nails.modelo.Resena;
import venus.nails.modelo.Usuario;
import venus.nails.repositorio.ResenaRepositorio;
import venus.nails.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
/**
 * Controlador para el modulo de resenas de Venus Nails Spa.
 * Permite registrar, eliminar y listar las resenas de los clientes.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Controller
@RequestMapping("/resenas")
public class ResenaControlador {
    /** Repositorio de resenas */
    @Autowired
    private ResenaRepositorio resenaRepo;
    /** Repositorio de usuarios */
    @Autowired
    private UsuarioRepositorio usuarioRepo;
    /**
     * Redirige /resenas a /resenas/listar
     * @return redireccion a listar resenas
     */
    @GetMapping
    public String index() {
        return "redirect:/resenas/listar";
    }
    /**
     * Lista todas las resenas registradas.
     * @param model modelo para pasar datos a la vista
     * @return vista resenas/listar.html
     */
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("resenas", resenaRepo.findAll());
        return "resenas/listar";
    }
    /**
     * Muestra el formulario para agregar nueva resena.
     * @return vista resenas/agregar.html
     */
    @GetMapping("/agregar")
    public String mostrarFormulario() {
        return "resenas/agregar";
    }
    /**
     * Procesa el formulario de nueva resena.
     * @param comentario texto del comentario
     * @param calificacion calificacion del 1 al 5
     * @param session sesion con el usuario autenticado
     * @return redireccion a listar resenas
     */
    @PostMapping("/agregar")
    public String agregar(@RequestParam String comentario,
                          @RequestParam int calificacion,
                          HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        Resena resena = new Resena();
        resena.setUsuario(usuario);
        resena.setComentario(comentario);
        resena.setCalificacion(calificacion);
        resena.setFecha(LocalDate.now());
        resenaRepo.save(resena);
        return "redirect:/resenas/listar";
    }
    /**
     * Elimina una resena por su ID.
     * @param id identificador de la resena a eliminar
     * @return redireccion a listar resenas
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        resenaRepo.deleteById(id);
        return "redirect:/resenas/listar";
    }
}
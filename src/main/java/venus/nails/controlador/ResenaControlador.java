package venus.nails.controlador;

import venus.nails.modelo.Resena;
import venus.nails.modelo.Usuario;
import venus.nails.repositorio.ResenaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador para el modulo de resenas de Venus Nails Spa.
 * Permite registrar y listar las resenas de los clientes.
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
     * @param model modelo con objeto vacio
     * @return vista resenas/agregar.html
     */
    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("resena", new Resena());
        return "resenas/agregar";
    }

    /**
     * Procesa el formulario de nueva resena.
     * @param resena objeto resena con datos del formulario
     * @param session sesion con el usuario autenticado
     * @return redireccion a listar resenas
     */
    @PostMapping("/agregar")
    public String agregar(@ModelAttribute Resena resena, HttpSession session) {
        // Obtener el usuario logueado para asignar el id automaticamente
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        resena.setIdUsuario(usuario.getIdUsuario());

        // Guardar la resena en la base de datos
        resenaRepo.save(resena);
        return "redirect:/resenas/listar";
    }
}
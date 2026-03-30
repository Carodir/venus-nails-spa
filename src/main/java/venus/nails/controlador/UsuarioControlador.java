package venus.nails.controlador;
import venus.nails.modelo.Usuario;
import venus.nails.repositorio.CitaRepositorio;
import venus.nails.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/**
 * Controlador para el modulo de gestion de usuarios.
 * Permite registrar, modificar, eliminar y listar usuarios.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Controller
public class UsuarioControlador {
    /** Repositorio de usuarios */
    @Autowired
    private UsuarioRepositorio usuarioRepo;
    /** Repositorio de citas para verificar dependencias */
    @Autowired
    private CitaRepositorio citaRepo;
    /**
     * Lista todos los usuarios. Solo para admin.
     * @param model modelo para pasar datos a la vista
     * @return vista usuarios/listar.html
     */
    @GetMapping("/usuarios/listar")
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioRepo.findAll());
        return "usuarios/listar";
    }
    /**
     * Muestra el formulario de registro de nuevo usuario (admin).
     * @param model modelo para pasar objeto vacio a la vista
     * @return vista usuarios/registrar.html
     */
    @GetMapping("/usuarios/registrar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/registrar";
    }
    /**
     * Procesa el formulario de registro (admin).
     * @param usuario objeto usuario con datos del formulario
     * @param model modelo para enviar errores a la vista
     * @return redireccion a listar o formulario con error
     */
    @PostMapping("/usuarios/registrar")
    public String registrar(@ModelAttribute Usuario usuario, Model model) {
        if (usuarioRepo.existsByCorreo(usuario.getCorreo())) {
            model.addAttribute("error", "El correo ya esta registrado");
            return "usuarios/registrar";
        }
        usuarioRepo.save(usuario);
        return "redirect:/usuarios/listar";
    }
    /**
     * Muestra el formulario de registro publico para nuevos clientes.
     * @param model modelo para pasar objeto vacio a la vista
     * @return vista registro.html
     */
    @GetMapping("/registro")
    public String mostrarRegistroPublico(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }
    /**
     * Procesa el registro publico de un nuevo cliente.
     * @param usuario objeto usuario con datos del formulario
     * @param model modelo para enviar errores a la vista
     * @return redireccion al login o formulario con error
     */
    @PostMapping("/registro")
    public String registroPublico(@ModelAttribute Usuario usuario, Model model) {
        if (usuarioRepo.existsByCorreo(usuario.getCorreo())) {
            model.addAttribute("error", "El correo ya esta registrado");
            return "registro";
        }
        usuario.setRol("cliente");
        usuarioRepo.save(usuario);
        return "redirect:/login";
    }
    /**
     * Muestra el formulario para modificar un usuario existente.
     * @param id identificador del usuario
     * @param model modelo con datos del usuario
     * @return vista usuarios/modificar.html
     */
    @GetMapping("/usuarios/modificar/{id}")
    public String mostrarModificar(@PathVariable int id, Model model) {
        Usuario usuario = usuarioRepo.findById(id).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "usuarios/modificar";
    }
    /**
     * Procesa la modificacion de un usuario.
     * @param id identificador del usuario
     * @param usuario objeto con datos actualizados
     * @return redireccion a listar usuarios
     */
    @PostMapping("/usuarios/modificar/{id}")
    public String modificar(@PathVariable int id, @ModelAttribute Usuario usuario) {
        usuario.setIdUsuario(id);
        usuarioRepo.save(usuario);
        return "redirect:/usuarios/listar";
    }
    /**
     * Elimina un usuario por su ID si no tiene registros asociados.
     * @param id identificador del usuario a eliminar
     * @param model modelo para enviar errores a la vista
     * @return redireccion a listar usuarios o mensaje de error
     */
    @PostMapping("/usuarios/eliminar/{id}")
    public String eliminar(@PathVariable int id, Model model) {
        try {
            usuarioRepo.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("usuarios", usuarioRepo.findAll());
            model.addAttribute("error", "No se puede eliminar el usuario porque tiene registros asociados en el sistema.");
            return "usuarios/listar";
        }
        return "redirect:/usuarios/listar";
    }
}
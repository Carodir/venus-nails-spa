package venus.nails.controlador;

import venus.nails.modelo.Usuario;
import venus.nails.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para el módulo de Usuarios
 * Expone endpoints JSON para registro, login y consulta
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioApiControlador {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    /**
     * GET /api/usuarios/listar
     * Retorna la lista de todos los usuarios registrados
     */
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * POST /api/usuarios/registro
     * Recibe un usuario y lo registra en la base de datos
     */
    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registro(
            @RequestBody Usuario usuario) {

        Map<String, Object> respuesta = new HashMap<>();

        // Verificar si el correo ya está registrado
        if (usuarioRepositorio.existsByCorreo(usuario.getCorreo())) {
            respuesta.put("exito", false);
            respuesta.put("mensaje", "El correo ya está registrado.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
        }

        // Guardar el nuevo usuario
        usuarioRepositorio.save(usuario);
        respuesta.put("exito", true);
        respuesta.put("mensaje", "Usuario registrado exitosamente.");
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    /**
     * POST /api/usuarios/login
     * Recibe correo y contrasena, valida y retorna mensaje
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody Map<String, String> credenciales) {

        Map<String, Object> respuesta = new HashMap<>();

        String correo     = credenciales.get("correo");
        String contrasena = credenciales.get("contrasena");

        // Buscar usuario por correo
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByCorreo(correo);
        Usuario usuario = usuarioOpt.orElse(null);

        if (usuario == null) {
            respuesta.put("exito", false);
            respuesta.put("mensaje", "Error en la autenticación: usuario no encontrado.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
        }

        // Verificar contraseña
        if (!usuario.getContrasena().equals(contrasena)) {
            respuesta.put("exito", false);
            respuesta.put("mensaje", "Error en la autenticación: contraseña incorrecta.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
        }

        // Autenticación exitosa
        respuesta.put("exito", true);
        respuesta.put("mensaje", "Autenticación satisfactoria.");
        respuesta.put("usuario", usuario.getNombre());
        respuesta.put("rol", usuario.getRol());
        return ResponseEntity.ok(respuesta);
    }
}
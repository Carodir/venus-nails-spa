package venus.nails.controlador;

import venus.nails.modelo.Servicio;
import venus.nails.repositorio.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para el módulo de Servicios
 * Expone endpoints JSON para listar, crear y eliminar servicios
 */
@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioApiControlador {

    @Autowired
    private ServicioRepositorio servicioRepositorio;

    /**
     * GET /api/servicios/listar
     * Retorna la lista de todos los servicios disponibles
     */
    @GetMapping("/listar")
    public ResponseEntity<List<Servicio>> listar() {
        List<Servicio> servicios = servicioRepositorio.findAll();
        return ResponseEntity.ok(servicios);
    }

    /**
     * GET /api/servicios/{id}
     * Retorna un servicio específico por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable int id) {

        Map<String, Object> respuesta = new HashMap<>();
        Optional<Servicio> servicio = servicioRepositorio.findById(id);

        if (servicio.isEmpty()) {
            respuesta.put("exito", false);
            respuesta.put("mensaje", "Servicio no encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }

        return ResponseEntity.ok(servicio.get());
    }

    /**
     * POST /api/servicios/crear
     * Recibe un servicio y lo guarda en la base de datos
     */
    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crear(
            @RequestBody Servicio servicio) {

        Map<String, Object> respuesta = new HashMap<>();

        // Guardar el nuevo servicio
        servicioRepositorio.save(servicio);
        respuesta.put("exito", true);
        respuesta.put("mensaje", "Servicio creado exitosamente.");
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }

    /**
     * DELETE /api/servicios/eliminar/{id}
     * Elimina un servicio por su ID
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable int id) {

        Map<String, Object> respuesta = new HashMap<>();

        // Verificar si existe el servicio
        if (!servicioRepositorio.existsById(id)) {
            respuesta.put("exito", false);
            respuesta.put("mensaje", "Servicio no encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }

        // Eliminar el servicio
        servicioRepositorio.deleteById(id);
        respuesta.put("exito", true);
        respuesta.put("mensaje", "Servicio eliminado exitosamente.");
        return ResponseEntity.ok(respuesta);
    }
}

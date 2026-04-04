package venus.nails.controlador;

import venus.nails.modelo.Cita;
import venus.nails.repositorio.CitaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para el módulo de Citas
 * Expone endpoints JSON para listar, crear, confirmar y cancelar citas
 */
@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaApiControlador {

    @Autowired
    private CitaRepositorio citaRepositorio;

    /**
     * GET /api/citas/listar
     * Retorna la lista de todas las citas ordenadas por fecha y hora
     */
    @GetMapping("/listar")
    public ResponseEntity<List<Cita>> listar() {
        List<Cita> citas = citaRepositorio.findAllByOrderByFechaAscHoraInicioAsc();
        return ResponseEntity.ok(citas);
    }

    /**
     * POST /api/citas/crear
     * Recibe una cita y la guarda con estado Pendiente
     */
    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crear(
            @RequestBody Cita cita) {

        Map<String, Object> respuesta = new HashMap<>();

        // Estado inicial siempre Pendiente
        cita.setEstado("Pendiente");

        // Guardar la cita
        citaRepositorio.save(cita);
        respuesta.put("exito", true);
        respuesta.put("mensaje", "Cita creada exitosamente.");
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    /**
     * PUT /api/citas/confirmar/{id}
     * Cambia el estado de una cita a Confirmada
     */
    @PutMapping("/confirmar/{id}")
    public ResponseEntity<Map<String, Object>> confirmar(@PathVariable int id) {

        Map<String, Object> respuesta = new HashMap<>();
        Optional<Cita> citaOpt = citaRepositorio.findById(id);

        // Verificar si existe la cita
        if (citaOpt.isEmpty()) {
            respuesta.put("exito", false);
            respuesta.put("mensaje", "Cita no encontrada.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }

        // Cambiar estado a Confirmada
        Cita cita = citaOpt.get();
        cita.setEstado("Confirmada");
        citaRepositorio.save(cita);

        respuesta.put("exito", true);
        respuesta.put("mensaje", "Cita confirmada exitosamente.");
        return ResponseEntity.ok(respuesta);
    }

    /**
     * PUT /api/citas/cancelar/{id}
     * Cambia el estado de una cita a Cancelada
     */
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Map<String, Object>> cancelar(@PathVariable int id) {

        Map<String, Object> respuesta = new HashMap<>();
        Optional<Cita> citaOpt = citaRepositorio.findById(id);

        // Verificar si existe la cita
        if (citaOpt.isEmpty()) {
            respuesta.put("exito", false);
            respuesta.put("mensaje", "Cita no encontrada.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }

        // Cambiar estado a Cancelada
        Cita cita = citaOpt.get();
        cita.setEstado("Cancelada");
        citaRepositorio.save(cita);

        respuesta.put("exito", true);
        respuesta.put("mensaje", "Cita cancelada exitosamente.");
        return ResponseEntity.ok(respuesta);
    }
}
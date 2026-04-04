package venus.nails.controlador;

import venus.nails.modelo.Pago;
import venus.nails.repositorio.PagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para el módulo de Pagos
 * Expone endpoints JSON para listar y registrar pagos
 */
@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoApiControlador {

    @Autowired
    private PagoRepositorio pagoRepositorio;

    /**
     * GET /api/pagos/listar
     * Retorna la lista de todos los pagos registrados
     */
    @GetMapping("/listar")
    public ResponseEntity<List<Pago>> listar() {
        List<Pago> pagos = pagoRepositorio.findAll();
        return ResponseEntity.ok(pagos);
    }

    /**
     * POST /api/pagos/crear
     * Recibe un pago y lo registra en la base de datos
     * Métodos aceptados: Efectivo, Tarjeta, Transferencia
     */
    @PostMapping("/crear")
    public ResponseEntity<Map<String, Object>> crear(
            @RequestBody Pago pago) {

        Map<String, Object> respuesta = new HashMap<>();

        // Validar método de pago
        String metodo = pago.getMetodo();
        if (!metodo.equals("Efectivo") &&
            !metodo.equals("Tarjeta") &&
            !metodo.equals("Transferencia")) {

            respuesta.put("exito", false);
            respuesta.put("mensaje", "Método de pago no válido. Use: Efectivo, Tarjeta o Transferencia.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }

        // Guardar el pago
        pagoRepositorio.save(pago);
        respuesta.put("exito", true);
        respuesta.put("mensaje", "Pago registrado exitosamente.");
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}
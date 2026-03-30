package venus.nails.controlador;
import venus.nails.modelo.Pago;
import venus.nails.modelo.Cita;
import venus.nails.repositorio.PagoRepositorio;
import venus.nails.repositorio.CitaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
/**
 * Controlador para el modulo de pagos de Venus Nails Spa.
 * Permite registrar y listar los pagos de cada cita.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Controller
@RequestMapping("/pagos")
public class PagoControlador {
    /** Repositorio de pagos */
    @Autowired
    private PagoRepositorio pagoRepo;
    /** Repositorio de citas para obtener el servicio asociado */
    @Autowired
    private CitaRepositorio citaRepo;
    /**
     * Redirige /pagos a /pagos/listar
     * @return redireccion a listar pagos
     */
    @GetMapping
    public String index() {
        return "redirect:/pagos/listar";
    }
    /**
     * Lista todos los pagos registrados.
     * @param model modelo para pasar datos a la vista
     * @return vista pagos/listar.html
     */
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("pagos", pagoRepo.findAll());
        return "pagos/listar";
    }
    /**
     * Muestra el formulario para registrar nuevo pago.
     * @param model modelo con objeto vacio y lista de citas
     * @return vista pagos/agregar.html
     */
    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("citas", citaRepo.findAllByOrderByFechaAscHoraInicioAsc());
        return "pagos/agregar";
    }
    /**
     * Procesa el formulario de nuevo pago.
     * El monto se calcula automaticamente desde el precio del servicio.
     * @param idCita id de la cita seleccionada
     * @param metodo metodo de pago
     * @param observaciones observaciones del pago
     * @return redireccion a listar pagos
     */
    @PostMapping("/agregar")
    public String agregar(@RequestParam int idCita,
                          @RequestParam String metodo,
                          @RequestParam(required = false) String observaciones) {
        Cita cita = citaRepo.findById(idCita).orElseThrow();
        Pago pago = new Pago();
        pago.setCita(cita);
        pago.setFecha(LocalDate.now());
        pago.setMonto(cita.getServicio().getPrecio());
        pago.setMetodo(metodo);
        pago.setObservaciones(observaciones);
        pagoRepo.save(pago);
        return "redirect:/pagos/listar";
    }
}
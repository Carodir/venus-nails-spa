package venus.nails.controlador;
import venus.nails.modelo.Pago;
import venus.nails.modelo.Cita;
import venus.nails.modelo.Servicio;
import venus.nails.repositorio.PagoRepositorio;
import venus.nails.repositorio.CitaRepositorio;
import venus.nails.repositorio.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    /** Repositorio de servicios para obtener el monto automaticamente */
    @Autowired
    private ServicioRepositorio servicioRepo;
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
        model.addAttribute("pago", new Pago());
        model.addAttribute("citas", citaRepo.findAll());
        return "pagos/agregar";
    }
    /**
     * Procesa el formulario de nuevo pago.
     * El monto se calcula automaticamente desde el precio del servicio.
     * @param pago objeto pago con datos del formulario
     * @return redireccion a listar pagos
     */
    @PostMapping("/agregar")
    public String agregar(@ModelAttribute Pago pago) {
        // Obtener la cita y su servicio para calcular el monto automaticamente
        Cita cita = citaRepo.findById(pago.getIdCita()).orElseThrow();
        Servicio servicio = cita.getServicio();
        // Asignar el monto automaticamente desde el precio del servicio
        pago.setMonto(servicio.getPrecio());
        // Guardar el pago en la base de datos
        pagoRepo.save(pago);
        return "redirect:/pagos/listar";
    }
}
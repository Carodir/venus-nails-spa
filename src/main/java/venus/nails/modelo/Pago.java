package venus.nails.modelo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
/**
 * Entidad JPA que representa la tabla pago en la base de datos venus_nails.
 * Registra los pagos realizados por cada cita.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    /** Identificador unico del pago */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private int idPago;
    /** Cita asociada al pago */
    @ManyToOne
    @JoinColumn(name = "id_cita")
    private Cita cita;
    /** Fecha en que se realizo el pago */
    @Column(name = "fecha")
    private LocalDate fecha;
    /** Monto pagado */
    @Column(name = "monto")
    private double monto;
    /** Metodo de pago: Efectivo, Tarjeta o Transferencia */
    @Column(name = "metodo")
    private String metodo;
    /** Observaciones adicionales sobre el pago */
    @Column(name = "observaciones")
    private String observaciones;
}
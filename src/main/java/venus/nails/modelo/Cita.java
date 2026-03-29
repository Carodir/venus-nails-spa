package venus.nails.modelo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
/**
 * Entidad JPA que representa la tabla citas en la base de datos venus_nails.
 * Registra cada cita agendada por un cliente para un servicio especifico.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "citas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita {
    /** Identificador unico de la cita */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private int idCita;
    /** Usuario que agenda la cita */
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    /** Servicio solicitado */
    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;
    /** Fecha de la cita */
    @Column(name = "fecha")
    private LocalDate fecha;
    /** Hora de inicio de la cita */
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;
    /** Hora de fin calculada automaticamente */
    @Column(name = "hora_fin")
    private LocalTime horaFin;
    /** Estado: Pendiente, Confirmada o Cancelada */
    @Column(name = "estado")
    private String estado;
}
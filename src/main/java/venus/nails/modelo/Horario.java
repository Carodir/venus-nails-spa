package venus.nails.modelo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;
/**
 * Entidad JPA que representa la tabla horario_disponible en la base de datos venus_nails.
 * Define los bloques de tiempo disponibles de cada manicurista.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "horario_disponible")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horario {
    /** Identificador unico del horario */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private int idHorario;
    /** Manicurista al que pertenece el horario */
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario manicurista;
    /** Dia de la semana: Lunes, Martes, etc */
    @Column(name = "dia_semana")
    private String diaSemana;
    /** Hora de inicio del bloque disponible */
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;
    /** Hora de fin del bloque disponible */
    @Column(name = "hora_fin")
    private LocalTime horaFin;
}
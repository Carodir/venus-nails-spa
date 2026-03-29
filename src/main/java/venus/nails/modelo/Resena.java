package venus.nails.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

/**
 * Entidad JPA que representa la tabla reseña en la base de datos venus_nails.
 * Almacena las calificaciones y comentarios de los clientes.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "reseña")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resena {

    /** Identificador unico de la resena */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reseña")
    private int idResena;

    /** ID del usuario que escribe la resena */
    @Column(name = "id_usuario")
    private int idUsuario;

    /** Texto del comentario */
    @Column(name = "comentario")
    private String comentario;

    /** Calificacion del 1 al 5 */
    @Column(name = "calificacion")
    private int calificacion;

    /** Fecha en que se publico la resena */
    @Column(name = "fecha")
    private LocalDate fecha;
}
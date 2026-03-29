package venus.nails.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entidad JPA que representa la tabla servicio en la base de datos venus_nails.
 * Contiene el catalogo de servicios ofrecidos por Venus Nails Spa.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "servicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {

    /** Identificador unico del servicio */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private int idServicio;

    /** Nombre del servicio */
    @Column(name = "nombre")
    private String nombre;

    /** Descripcion detallada del servicio */
    @Column(name = "descripcion")
    private String descripcion;

    /** Duracion en minutos - se usa para calcular hora_fin de la cita */
    @Column(name = "duracion")
    private int duracion;

    /** Precio del servicio */
    @Column(name = "precio")
    private double precio;
}
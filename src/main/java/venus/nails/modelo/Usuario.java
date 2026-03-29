package venus.nails.modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entidad JPA que representa la tabla usuario en la base de datos venus_nails.
 * Almacena la informacion de clientes, manicuristas y administradores.
 * @author Carolina
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    /** Identificador unico del usuario */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int idUsuario;

    /** Nombre del usuario */
    @Column(name = "nombre")
    private String nombre;

    /** Apellido del usuario */
    @Column(name = "apellido")
    private String apellido;

    /** Correo electronico - debe ser unico */
    @Column(name = "correo", unique = true)
    private String correo;

    /** Telefono de contacto */
    @Column(name = "telefono")
    private String telefono;

    /** Contrasena encriptada con BCrypt */
    @Column(name = "contrasena")
    private String contrasena;

    /** Rol del usuario: admin, cliente o manicurista */
    @Column(name = "rol")
    private String rol;
}
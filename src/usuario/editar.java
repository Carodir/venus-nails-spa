package usuario;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class editar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Ingrese el ID del usuario a actualizar: ");
            int id = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Nuevo apellido: ");
            String apellido = sc.nextLine();
            System.out.print("Nuevo correo: ");
            String correo = sc.nextLine();
            System.out.print("Nuevo teléfono: ");
            String telefono = sc.nextLine();
            System.out.print("Nuevo rol: ");
            String rol = sc.nextLine();

            Conexion con = new Conexion();
            Connection cn = con.conectar();

            String sql = "UPDATE usuario SET nombre=?, apellido=?, correo=?, telefono=?, rol=? WHERE id=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, correo);
            ps.setString(4, telefono);
            ps.setString(5, rol);
            ps.setInt(6, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println(" Usuario actualizado correctamente.");
            } else {
                System.out.println("No se encontró un usuario con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
        
    }
}
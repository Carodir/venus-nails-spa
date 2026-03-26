package servicio;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class editar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Ingrese el ID del servicio a editar: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Nueva descripción: ");
            String descripcion = sc.nextLine();
            System.out.print("Nueva duración (ej: 00:45:00): ");
            String duracion = sc.nextLine();
            System.out.print("Nuevo precio: ");
            double precio = sc.nextDouble();

            Conexion con = new Conexion();
            Connection cn = con.conectar();

            String sql = "UPDATE servicio SET nombre=?, descripcion=?, duracion=?, precio=? WHERE id_servicio=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setString(3, duracion);
            ps.setDouble(4, precio);
            ps.setInt(5, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Servicio actualizado correctamente.");
            } else {
                System.out.println("❌ No se encontró un servicio con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
        
    }
}
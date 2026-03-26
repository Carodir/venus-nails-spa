package servicio;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class agregar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Ingrese nombre del servicio: ");
            String nombre = sc.nextLine();
            System.out.print("Ingrese descripción: ");
            String descripcion = sc.nextLine();
            System.out.print("Ingrese duración (ej: 00:45:00): ");
            String duracion = sc.nextLine();
            System.out.print("Ingrese precio: ");
            double precio = sc.nextDouble();

            Conexion con = new Conexion();
            Connection cn = con.conectar();

            String sql = "INSERT INTO servicio (nombre, descripcion, duracion, precio) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setString(3, duracion);
            ps.setDouble(4, precio);
            ps.executeUpdate();

            System.out.println(" Servicio agregado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
       
    }
}
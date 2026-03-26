package resena;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class agregar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Ingrese ID del usuario: ");
            int idUsuario = sc.nextInt();
            sc.nextLine();

            System.out.print("Ingrese comentario: ");
            String comentario = sc.nextLine();

            System.out.print("Ingrese calificación (1 al 5): ");
            int calificacion = sc.nextInt();
            sc.nextLine();

            if (calificacion < 1 || calificacion > 5) {
                System.out.println("⚠️ La calificación debe ser entre 1 y 5.");
                return;
            }

            System.out.print("Ingrese fecha (ej: 2024-12-31): ");
            String fecha = sc.nextLine();

            Conexion con = new Conexion();
            Connection cn = con.conectar();

            String sql = "INSERT INTO reseña (id_usuario, comentario, calificacion, fecha) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setString(2, comentario);
            ps.setInt(3, calificacion);
            ps.setString(4, fecha);
            ps.executeUpdate();

            System.out.println("✅ Reseña agregada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
        
    }
}
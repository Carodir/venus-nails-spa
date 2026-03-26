package resena;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class editar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Ingrese el ID de la reseña a editar: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Nuevo comentario: ");
            String comentario = sc.nextLine();

            System.out.print("Nueva calificación (1 al 5): ");
            int calificacion = sc.nextInt();
            sc.nextLine();

            if (calificacion < 1 || calificacion > 5) {
                System.out.println("⚠️ La calificación debe ser entre 1 y 5.");
                return;
            }

            System.out.print("Nueva fecha (ej: 2024-12-31): ");
            String fecha = sc.nextLine();

            Conexion con = new Conexion();
            Connection cn = con.conectar();

            String sql = "UPDATE reseña SET comentario=?, calificacion=?, fecha=? WHERE id_reseña=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, comentario);
            ps.setInt(2, calificacion);
            ps.setString(3, fecha);
            ps.setInt(4, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Reseña actualizada correctamente.");
            } else {
                System.out.println("❌ No se encontró una reseña con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
        
    }
}
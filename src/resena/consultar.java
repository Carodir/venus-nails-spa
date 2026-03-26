package resena;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class consultar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el ID de la reseña a consultar: ");
        int id = sc.nextInt();

        Conexion con = new Conexion();
        try {
            Connection cn = con.conectar();
            String sql = "SELECT * FROM reseña WHERE id_reseña = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Reseña encontrada:");
                System.out.println("ID Reseña: "     + rs.getInt("id_reseña"));
                System.out.println("ID Usuario: "    + rs.getInt("id_usuario"));
                System.out.println("Comentario: "    + rs.getString("comentario"));
                System.out.println("Calificación: "  + rs.getInt("calificacion") + "/5");
                System.out.println("Fecha: "         + rs.getString("fecha"));
            } else {
                System.out.println("❌ No existe una reseña con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
}
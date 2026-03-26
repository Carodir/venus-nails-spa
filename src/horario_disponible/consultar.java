package horario_disponible;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class consultar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el ID del horario a consultar: ");
        int id = sc.nextInt();

        Conexion con = new Conexion();
        try {
            Connection cn = con.conectar();
            String sql = "SELECT * FROM horario_disponible WHERE id_horario = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Horario encontrado:");
                System.out.println("ID Horario: "  + rs.getInt("id_horario"));
                System.out.println("ID Usuario: "  + rs.getInt("id_usuario"));
                System.out.println("Día: "         + rs.getString("dia_semana"));
                System.out.println("Hora inicio: " + rs.getString("hora_inicio"));
                System.out.println("Hora fin: "    + rs.getString("hora_fin"));
            } else {
                System.out.println(" No existe un horario con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
}
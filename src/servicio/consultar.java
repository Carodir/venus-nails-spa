package servicio;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class consultar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el ID del servicio a consultar: ");
        int id = sc.nextInt();

        Conexion con = new Conexion();
        try {
            Connection cn = con.conectar();
            String sql = "SELECT * FROM servicio WHERE id_servicio = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Servicio encontrado:");
                System.out.println("ID: "          + rs.getInt("id_servicio"));
                System.out.println("Nombre: "      + rs.getString("nombre"));
                System.out.println("Descripción: " + rs.getString("descripcion"));
                System.out.println("Duración: "    + rs.getString("duracion"));
                System.out.println("Precio: $"     + rs.getDouble("precio"));
            } else {
                System.out.println("❌ No existe un servicio con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
}
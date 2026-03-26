package usuario;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class consultar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el ID a consultar: ");
        int id = sc.nextInt();

        Conexion con = new Conexion();
        try {
            Connection cn = con.conectar();
            String sql = "SELECT * FROM usuario WHERE id = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Usuario encontrado:");
                System.out.println("ID: "         + rs.getInt("id"));
                System.out.println("Nombre: "     + rs.getString("nombre"));
                System.out.println("Apellido: "   + rs.getString("apellido"));
                System.out.println("Correo: "     + rs.getString("correo"));
                System.out.println("Teléfono: "   + rs.getString("telefono"));
                System.out.println("Rol: "        + rs.getString("rol"));
            } else {
                System.out.println(" No existe un usuario con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
       
    }
}
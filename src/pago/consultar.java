package pago;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class consultar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el ID del pago a consultar: ");
        int id = sc.nextInt();

        Conexion con = new Conexion();
        try {
            Connection cn = con.conectar();
            String sql = "SELECT * FROM pago WHERE id_pago = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Pago encontrado:");
                System.out.println("ID Pago: "   + rs.getInt("id_pago"));
                System.out.println("ID Cita: "   + rs.getInt("id_cita"));
                System.out.println("Fecha: "     + rs.getString("fecha"));
                System.out.println("Monto: $"    + rs.getDouble("monto"));
                System.out.println("Método: "    + rs.getString("metodo"));
            } else {
                System.out.println("❌ No existe un pago con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
       
    }
}
package pago;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class eliminar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Ingrese el ID del pago a eliminar: ");
            int id = sc.nextInt();

            Conexion con = new Conexion();
            Connection cn = con.conectar();

            String sql = "DELETE FROM pago WHERE id_pago = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Pago eliminado correctamente.");
            } else {
                System.out.println("❌ No se encontró un pago con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
        
    }
}
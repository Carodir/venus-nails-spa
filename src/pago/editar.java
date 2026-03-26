package pago;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class editar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Ingrese el ID del pago a editar: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Nueva fecha (ej: 2024-12-31): ");
            String fecha = sc.nextLine();

            System.out.print("Nuevo monto: ");
            double monto = sc.nextDouble();
            sc.nextLine();

            System.out.println("Seleccione nuevo método de pago:");
            System.out.println("1. Efectivo");
            System.out.println("2. Tarjeta");
            System.out.println("3. Transferencia");
            System.out.print("Seleccione: ");
            int opcionMetodo = sc.nextInt();

            String metodo;
            switch (opcionMetodo) {
                case 1 -> metodo = "Efectivo";
                case 2 -> metodo = "Tarjeta";
                case 3 -> metodo = "Transferencia";
                default -> {
                    System.out.println("⚠️ Opción no válida.");
                    return;
                }
            }

            Conexion con = new Conexion();
            Connection cn = con.conectar();

            String sql = "UPDATE pago SET fecha=?, monto=?, metodo=? WHERE id_pago=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setDouble(2, monto);
            ps.setString(3, metodo);
            ps.setInt(4, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Pago actualizado correctamente.");
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
package pago;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class agregar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Ingrese ID de la cita: ");
            int idCita = sc.nextInt();
            sc.nextLine();

            System.out.print("Ingrese fecha del pago (ej: 2024-12-31): ");
            String fecha = sc.nextLine();

            System.out.print("Ingrese monto: ");
            double monto = sc.nextDouble();
            sc.nextLine();

            System.out.println("Seleccione método de pago:");
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

            String sql = "INSERT INTO pago (id_cita, fecha, monto, metodo) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idCita);
            ps.setString(2, fecha);
            ps.setDouble(3, monto);
            ps.setString(4, metodo);
            ps.executeUpdate();

            System.out.println("✅ Pago registrado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
        
    }
}
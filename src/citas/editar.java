package citas;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class editar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Ingrese el ID de la cita a editar: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Nueva fecha (ej: 2024-12-31): ");
            String fecha = sc.nextLine();
            System.out.print("Nueva hora inicio (ej: 09:00:00): ");
            String horaInicio = sc.nextLine();
            System.out.print("Nueva hora fin (ej: 09:45:00): ");
            String horaFin = sc.nextLine();
            System.out.println("Nuevo estado (Pendiente/Confirmada/Cancelada/Completada): ");
            String estado = sc.nextLine();

            Conexion con = new Conexion();
            Connection cn = con.conectar();

            String sql = "UPDATE citas SET fecha=?, hora_inicio=?, hora_fin=?, estado=? WHERE id_cita=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setString(2, horaInicio);
            ps.setString(3, horaFin);
            ps.setString(4, estado);
            ps.setInt(5, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Cita actualizada correctamente.");
            } else {
                System.out.println(" No se encontró una cita con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
        
    }
}
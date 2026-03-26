package citas;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class agregar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Ingrese ID del usuario: ");
            int idUsuario = sc.nextInt();
            System.out.print("Ingrese ID del servicio: ");
            int idServicio = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese fecha (ej: 2024-12-31): ");
            String fecha = sc.nextLine();
            System.out.print("Ingrese hora inicio (ej: 09:00:00): ");
            String horaInicio = sc.nextLine();

            Conexion con = new Conexion();
            Connection cn = con.conectar();

            // Obtener duración del servicio automáticamente
            String sqlDuracion = "SELECT duracion FROM servicio WHERE id_servicio = ?";
            PreparedStatement psDuracion = cn.prepareStatement(sqlDuracion);
            psDuracion.setInt(1, idServicio);
            ResultSet rs = psDuracion.executeQuery();

            if (!rs.next()) {
                System.out.println("No existe un servicio con ese ID.");
                return;
            }

            // Calcular hora fin sumando la duración a la hora inicio
            String sqlHoraFin = "SELECT ADDTIME(?, ?) AS hora_fin";
            PreparedStatement psHoraFin = cn.prepareStatement(sqlHoraFin);
            psHoraFin.setString(1, horaInicio);
            psHoraFin.setString(2, rs.getString("duracion"));
            ResultSet rsHoraFin = psHoraFin.executeQuery();
            rsHoraFin.next();
            String horaFin = rsHoraFin.getString("hora_fin");

            System.out.println(" Hora fin calculada automaticamente: " + horaFin);

            // Insertar la cita
            String sql = "INSERT INTO citas (id_usuario, id_servicio, fecha, hora_inicio, hora_fin, estado) VALUES (?, ?, ?, ?, ?, 'Pendiente')";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setInt(2, idServicio);
            ps.setString(3, fecha);
            ps.setString(4, horaInicio);
            ps.setString(5, horaFin);
            ps.executeUpdate();

            System.out.println("✅ Cita agendada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }
}
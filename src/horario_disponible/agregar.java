package horario_disponible;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class agregar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Ingrese ID del usuario: ");
            int idUsuario = sc.nextInt();
            sc.nextLine();

            System.out.println("Ingrese día de la semana: ");
            System.out.println("1. Lunes");
            System.out.println("2. Martes");
            System.out.println("3. Miércoles");
            System.out.println("4. Jueves");
            System.out.println("5. Viernes");
            System.out.println("6. Sábado");
            System.out.print("Seleccione: ");
            int opcionDia = sc.nextInt();
            sc.nextLine();

            String dia;
            switch (opcionDia) {
                case 1 -> dia = "Lunes";
                case 2 -> dia = "Martes";
                case 3 -> dia = "Miércoles";
                case 4 -> dia = "Jueves";
                case 5 -> dia = "Viernes";
                case 6 -> dia = "Sábado";
                default -> {
                    System.out.println("⚠️ Opción no válida.");
                    return;
                }
            }

            System.out.print("Ingrese hora inicio (ej: 08:00:00): ");
            String horaInicio = sc.nextLine();
            System.out.print("Ingrese hora fin (ej: 18:00:00): ");
            String horaFin = sc.nextLine();

            Conexion con = new Conexion();
            Connection cn = con.conectar();

            String sql = "INSERT INTO horario_disponible (id_usuario, dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setString(2, dia);
            ps.setString(3, horaInicio);
            ps.setString(4, horaFin);
            ps.executeUpdate();

            System.out.println(" Horario agregado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
       
    }
}
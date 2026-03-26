package citas;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class consultar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el ID de la cita a consultar: ");
        int id = sc.nextInt();

        Conexion con = new Conexion();
        try {
            Connection cn = con.conectar();
            String sql = "SELECT * FROM citas WHERE id_cita = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(" Cita encontrada:");
                System.out.println("ID Cita: "      + rs.getInt("id_cita"));
                System.out.println("ID Usuario: "   + rs.getInt("id_usuario"));
                System.out.println("ID Servicio: "  + rs.getInt("id_servicio"));
                System.out.println("Fecha: "        + rs.getString("fecha"));
                System.out.println("Hora inicio: "  + rs.getString("hora_inicio"));
                System.out.println("Hora fin: "     + rs.getString("hora_fin"));
                System.out.println("Estado: "       + rs.getString("estado"));
            } else {
                System.out.println(" No existe una cita con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
}
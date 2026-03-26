package usuario;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;



public class agregar {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Ingrese nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Ingrese apellido: ");
            String apellido = sc.nextLine();

            System.out.print("Ingrese correo: ");
            String correo = sc.nextLine();

            System.out.print("Ingrese telefono: ");
            String telefono = sc.nextLine();

            System.out.print("Ingrese contraseña: ");
            String contrasena = sc.nextLine();

            System.out.print("Ingrese rol: ");
            String rol = sc.nextLine();

            // Conexión
            Conexion con = new Conexion();
            Connection cn = con.conectar();

            // Consulta SQL
            String sql = "INSERT INTO usuario (nombre, apellido, correo, telefono, contrasena, rol) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, correo);
            ps.setString(4, telefono);
            ps.setString(5, contrasena);
            ps.setString(6, rol);

            ps.executeUpdate();

            System.out.println("Usuario agregado correctamente ");

        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }

     
    }
}
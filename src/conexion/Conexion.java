package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public Connection conectar() {

        Connection con = null;

        try {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/venus_nails",
                "root",
                "Caro1073."
            );

            System.out.println("CONEXION EXITOSA ");

        } catch (SQLException e) {
            System.out.println("ERROR DE CONEXION ");
            e.printStackTrace();
        }

        return con;
    }
}
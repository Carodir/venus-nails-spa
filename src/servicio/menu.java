package servicio;
import java.util.Scanner;

public class menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n╔══════════════════════════╗");
            System.out.println("║     💅 VENUS NAILS       ║");
            System.out.println("║   Gestión de Servicios   ║");
            System.out.println("╠══════════════════════════╣");
            System.out.println("║  1. Agregar servicio     ║");
            System.out.println("║  2. Consultar servicio   ║");
            System.out.println("║  3. Editar servicio      ║");
            System.out.println("║  4. Eliminar servicio    ║");
            System.out.println("║  0. Salir                ║");
            System.out.println("╚══════════════════════════╝");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> agregar.main(null);
                case 2 -> consultar.main(null);
                case 3 -> editar.main(null);
                case 4 -> eliminar.main(null);
                case 0 -> System.out.println("👋 Regresando...");
                default -> System.out.println("⚠️ Opción no válida.");
            }
        } while (opcion != 0);
    }
}
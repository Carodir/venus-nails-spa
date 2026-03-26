import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        int opcion;
        try (Scanner sc = new Scanner(System.in)) {
            do {
                System.out.println("\n╔══════════════════════════════╗");
                System.out.println("║       💅 VENUS NAILS         ║");
                System.out.println("║     Sistema de Gestión       ║");
                System.out.println("╠══════════════════════════════╣");
                System.out.println("║  1. Gestión de Usuarios      ║");
                System.out.println("║  2. Gestión de Servicios     ║");
                System.out.println("║  3. Gestión de Citas         ║");
                System.out.println("║  4. Gestión de Horarios      ║");
                System.out.println("║  5. Gestión de Pagos         ║");
                System.out.println("║  6. Gestión de Reseñas       ║");
                System.out.println("║  0. Salir                    ║");
                System.out.println("╚══════════════════════════════╝");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();

                switch (opcion) {
                    case 1 -> usuario.menu.main(null);
                    case 2 -> servicio.menu.main(null);
                    case 3 -> citas.menu.main(null);
                    case 4 -> horario_disponible.menu.main(null);
                    case 5 -> pago.menu.main(null);
                    case 6 -> resena.menu.main(null);
                    case 0 -> System.out.println("👋 Hasta luego, Venus Nails!");
                    default -> System.out.println("⚠️ Opción no válida.");
                }
            } while (opcion != 0);
        }
    }
}
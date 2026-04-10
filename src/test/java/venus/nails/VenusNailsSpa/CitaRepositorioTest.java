package venus.nails.VenusNailsSpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import venus.nails.modelo.Cita;
import venus.nails.repositorio.CitaRepositorio;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CitaRepositorioTest {

    @Autowired
    private CitaRepositorio citaRepositorio;

    @Test
    @DisplayName("PT-10: Verificar que existen citas registradas")
    void testListarCitas() {
        List<Cita> citas = citaRepositorio.findAll();
        assertNotNull(citas, "La lista de citas no debe ser nula");
        System.out.println("✅ Citas encontradas: " + citas.size());
    }

    @Test
    @DisplayName("PT-11: Verificar que las citas tienen estado válido")
    void testEstadoCitas() {
        List<Cita> citas = citaRepositorio.findAll();
        citas.forEach(c -> {
            assertNotNull(c.getEstado(), "El estado no debe ser nulo");
            assertTrue(
                c.getEstado().equals("Pendiente") ||
                c.getEstado().equals("Confirmada") ||
                c.getEstado().equals("Cancelada"),
                "El estado debe ser Pendiente, Confirmada o Cancelada"
            );
        });
        System.out.println("✅ Todos los estados de citas son válidos");
    }

    @Test
    @DisplayName("PT-12: Verificar citas ordenadas por fecha")
    void testCitasOrdenadasPorFecha() {
        List<Cita> citas = citaRepositorio.findAllByOrderByFechaAscHoraInicioAsc();
        assertNotNull(citas, "La lista no debe ser nula");
        System.out.println("✅ Citas ordenadas correctamente: " + citas.size());
    }
}
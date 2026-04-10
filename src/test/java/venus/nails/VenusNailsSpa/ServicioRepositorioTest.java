package venus.nails.VenusNailsSpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import venus.nails.modelo.Servicio;
import venus.nails.repositorio.ServicioRepositorio;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServicioRepositorioTest {

    @Autowired
    private ServicioRepositorio servicioRepositorio;

    @Test
    @DisplayName("PT-06: Verificar que existen servicios registrados")
    void testListarServicios() {
        List<Servicio> servicios = servicioRepositorio.findAll();
        assertNotNull(servicios, "La lista de servicios no debe ser nula");
        assertFalse(servicios.isEmpty(), "Debe haber al menos un servicio registrado");
        System.out.println("✅ Servicios encontrados: " + servicios.size());
    }

    @Test
    @DisplayName("PT-07: Verificar búsqueda de servicio por ID existente")
    void testBuscarServicioPorId() {
        Optional<Servicio> servicio = servicioRepositorio.findById(1);
        assertTrue(servicio.isPresent(), "El servicio con ID 1 debe existir");
        assertNotNull(servicio.get().getNombre(), "El nombre del servicio no debe ser nulo");
        System.out.println("✅ Servicio encontrado: " + servicio.get().getNombre());
    }

    @Test
    @DisplayName("PT-08: Verificar búsqueda de servicio por ID inexistente")
    void testBuscarServicioPorIdInexistente() {
        Optional<Servicio> servicio = servicioRepositorio.findById(9999);
        assertFalse(servicio.isPresent(), "No debe encontrar un servicio con ID inexistente");
        System.out.println("✅ ID inexistente manejado correctamente");
    }

    @Test
    @DisplayName("PT-09: Verificar que los servicios tienen precio mayor a cero")
    void testPrecioServicios() {
        List<Servicio> servicios = servicioRepositorio.findAll();
        servicios.forEach(s -> {
            assertTrue(s.getPrecio() > 0, "El precio debe ser mayor a cero");
        });
        System.out.println("✅ Todos los servicios tienen precio válido");
    }
}
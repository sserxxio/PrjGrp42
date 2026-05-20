package maquinas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.*;


class EstimacionTest {
	Maquina maquina;
	Producto producto;
	
	@BeforeEach
	void setUp() {
		maquina = mock(Maquina.class);
		producto = mock(Producto.class);
		
		HashMap<String, Integer> ventas = new HashMap<>();
	    ventas.put("CocaCola", 50);
	    
	    HashMap<String, Integer> inventario = new HashMap<>();
	    inventario.put("CocaCola", 20);
		
	    //Simulamos el comportamiento real de las clases
		when(producto.getNombre()).thenReturn("CocaCola");
		when(maquina.getFechaReposicion()).thenReturn(LocalDate.now().minusDays(10));
        when(maquina.getVentas()).thenReturn(ventas);
        when(maquina.getInventario()).thenReturn(inventario);
	}
	
	
	@Test
	@DisplayName("Prueba de Estimación: caso válido")
	void testCalculoNormal() {
	    int resultado = Estimacion.calcularEstimacion(maquina, producto);
	    assertEquals(4, resultado);
	}
	
	
	@DisplayName("Prueba de Estimación: mapa de ventas vacío")
	@Test
	void testSinVentas() {
		when(maquina.getVentas()).thenReturn(new HashMap<>());
		int resultado = Estimacion.calcularEstimacion(maquina, producto);
	    assertEquals(15, resultado);
	}

	
	@Test
	@DisplayName("Prueba de Estimación: producto no existe en ventas")
	void testVentasNombreNoCoincidente() {
		HashMap<String, Integer> ventas = new HashMap<>();
		ventas.put("BebidaInvalida", 10);
		when(maquina.getVentas()).thenReturn(ventas);
		
		int resultado = Estimacion.calcularEstimacion(maquina, producto);
	    assertEquals(15, resultado);
	}
	
	@Test
	@DisplayName("Prueba de Estimación: inventario vacío")
	void testInventarioVacío() {
		when(maquina.getInventario()).thenReturn(new HashMap<>());
        assertThrows(Exception.class, () -> {
            Estimacion.calcularEstimacion(maquina, producto);
        });
	}
		
	@Test
	@DisplayName("Prueba de Estimación: producto no existe en inventario")
	void testInventarioNombreNoCoincidente() {
        assertThrows(NullPointerException.class, () -> {
        	HashMap<String, Integer> ventas = new HashMap<>();
    		ventas.put("CocaCola", 10);
    		when(maquina.getVentas()).thenReturn(ventas);
    		
            HashMap<String, Integer> inventario = new HashMap<>();
            inventario.put("BebidaInvalida", 20);
            when(maquina.getInventario()).thenReturn(inventario);

            int resultado = Estimacion.calcularEstimacion(maquina, producto);
	    });
	}
	
	
	//TESTS DE CAJA BLANCA
	@Test
	@DisplayName("Caja Blanca: Inventario nulo")
	void testInventarioNulo() {
		when(maquina.getInventario()).thenReturn(null);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
			Estimacion.calcularEstimacion(maquina, producto);
		});
		assertEquals("El inventario no puede ser nulo", ex.getMessage());
	}

	@Test
	@DisplayName("Caja Blanca: Fecha reposición nula")
	void testFechaReposicionNula() {
		when(maquina.getFechaReposicion()).thenReturn(null);

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
			Estimacion.calcularEstimacion(maquina, producto);
		});
		assertEquals("La fecha de reposición no puede ser nula", ex.getMessage());
	}

	@Test
	@DisplayName("Caja Blanca: Ventas nulas")
	void testVentasNulas() {
		when(maquina.getVentas()).thenReturn(null);

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
			Estimacion.calcularEstimacion(maquina, producto);
		});
		assertEquals("Las ventas no pueden ser nulas", ex.getMessage());
	}

	@Test
	@DisplayName("Caja Blanca: Nombre de producto nulo")
	void testProductoNombreNulo() {
		when(producto.getNombre()).thenReturn(null);

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
			Estimacion.calcularEstimacion(maquina, producto);
		});
		assertEquals("El nombre del producto no puede ser nulo", ex.getMessage());
	}

	@Test
	@DisplayName("Caja Blanca: Nombre de producto vacío")
	void testProductoNombreVacio() {
		when(producto.getNombre()).thenReturn("");

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
			Estimacion.calcularEstimacion(maquina, producto);
		});
		assertEquals("El nombre del producto no puede ser nulo o vacío", ex.getMessage());
	}
	

}
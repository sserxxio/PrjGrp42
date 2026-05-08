package maquinas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;


class EstimacionTest {
	static Estimacion e;
	static Maquina maquina;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		e = new Estimacion();
	}
	
	@BeforeEach
	void setUp() {
	    ArrayList<Producto> productos = new ArrayList<>();
	    productos.add(new Producto("Botella", "CocaCola"));
	    maquina = new Maquina(1, 0.00f, 1.00f, 20, true, productos);
	}
	
	
	@Test
	@DisplayName("Prueba de Estimación: caso válido")
	void testCalculoNormal() {
		
	    Producto producto = new Producto("Botella", "CocaCola");

	    maquina.setFechaReposicion(LocalDate.now().minusDays(10));

	    HashMap<String, Integer> ventas = new HashMap<>();
	    ventas.put("CocaCola", 50);
	    maquina.setVentas(ventas);
	    
	    HashMap<String, Integer> temporal = new HashMap<>();
	    temporal.put("CocaCola", 20);
	    maquina.setInventario(temporal);

	    int resultado = e.calcularEstimacion(maquina, producto);

	    assertEquals(4, resultado);
	}
	
	@Test
	@DisplayName("Prueba de Estimación: fecha de reposición nula")
	void testFechaNula() {
	    assertThrows(IllegalArgumentException.class, () -> {
		    maquina.setFechaReposicion(null);
	    });
	}
	
	@Test
	@DisplayName("Prueba de Estimación: fecha de reposición futura")
	void testFechaFutura() {
	    assertThrows(IllegalArgumentException.class, () -> {
		    maquina.setFechaReposicion(LocalDate.now().plusDays(10));
	    });
	}
	
	@DisplayName("Prueba de Estimación: mapa de ventas vacío")
	@Test
	void testSinVentas() {
        
	    Producto producto = new Producto("Botella", "CocaCola");

	    maquina.setFechaReposicion(LocalDate.now().minusDays(10));
	    maquina.setVentas(new HashMap<>());

	    HashMap<String, Integer> temporal = new HashMap<>();
	    temporal.put("CocaCola", 20);

	    maquina.setInventario(temporal);

	    int resultado = e.calcularEstimacion(maquina, producto);

	    assertEquals(15, resultado);
	}
	
	@Test
	@DisplayName("Prueba de Estimación: venta inválida (valor negativo)")
	void testVentaInvalida() {
	    assertThrows(IllegalArgumentException.class, () -> {
	    	HashMap<String, Integer> ventas = new HashMap<>();
		    ventas.put("CocaCola", -4);
		    maquina.setVentas(ventas);
		});
	}
	
	@Test
	@DisplayName("Prueba de Estimación: inventario vacío")
	void testInventarioVacío() {
		assertThrows(IllegalArgumentException.class, () -> {
		    Producto producto = new Producto("Botella", "CocaCola");
	
		    maquina.setFechaReposicion(LocalDate.now().minusDays(10));
	
		    HashMap<String, Integer> ventas = new HashMap<>();
		    ventas.put("CocaCola", 50);
		    maquina.setVentas(ventas);
	
		    int resultado = e.calcularEstimacion(maquina, producto);
		 });
	}
	
	@Test
	@DisplayName("Prueba de Estimación: inventario inválido (valor negativo)")
	void testInventarioNegativo() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			 HashMap<String, Integer> temporal = new HashMap<>();
			 temporal.put("CocaCola", -5);
			 maquina.setInventario(temporal);
	    });  
	}
	
	@Test
	@DisplayName("Prueba de Estimación: producto no existe en ventas")
	void testVentasNombreNoCoincidente() {
	    Producto producto = new Producto("Botella", "CocaCola");
	    
	    maquina.setFechaReposicion(LocalDate.now().minusDays(10));
	    
	    HashMap<String, Integer> ventas = new HashMap<>();
	    ventas.put("BebidaInvalida", 10);
	    maquina.setVentas(ventas);
	    
	    HashMap<String, Integer> inventario = new HashMap<>();
	    inventario.put("CocaCola", 20);
	    maquina.setInventario(inventario);

	    int resultado = e.calcularEstimacion(maquina, producto);
	    assertEquals(15, resultado);
	}
	

}
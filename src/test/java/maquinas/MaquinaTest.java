package maquinas;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MaquinaTest {
	static Maquina m;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ArrayList<Producto> productos = new ArrayList<>();
	    productos.add(new Producto("Botella", "CocaCola"));
	    m = new Maquina(1, 0.00f, 1.00f, 20, true, productos);
	}
	
	// PRUEBAS DEL CONSTRUCTOR DE LA CLASE //
	
	@Test
	@DisplayName("Constructor clase correcto")
	void testConstructorCorrecto() {
	    Maquina maquina = new Maquina(1, 0.00f, 1.00f, 20, true, null);
	    assertAll("Verificar atributos de la máquina",
                () -> assertNotNull(maquina, "La máquina no debería ser null"),
                () -> assertEquals(1, maquina.getId()),
                () -> assertEquals(0.00f, maquina.getCoordX()),
                () -> assertEquals(1.00f, maquina.getCoordY()),
                () -> assertEquals(20, maquina.getCapacidad()),
                () -> assertTrue(maquina.estaOperativa()),
                () -> assertEquals(null, maquina.getProductos())
            );
	}
	
	@Test
	@DisplayName("Constructor: id negativo")
	void testConstructorIdNegativo() {
		assertThrows(IllegalArgumentException.class, () -> {
	        Maquina maquina = new Maquina(-1, 0.00f, 1.00f, 20, true, null);
	    });
	}
	
	@Test
	@DisplayName("Constructor capacidad negativa")
	void testConstructorCapacidadNegativa() {
		assertThrows(IllegalArgumentException.class, () -> {
	        Maquina maquina = new Maquina(1, 0.00f, 1.00f, -20, true, null);
	    });
	}
	
	/////////////////////////////////////////////////////////////


	/// PRUEBAS DE SETTERS //
	///
	///@Test
	@DisplayName("Prueba de Estimación: fecha de reposición futura")
	void testFechaFutura() {
	    assertThrows(IllegalArgumentException.class, () -> {
		    m.setFechaReposicion(LocalDate.now().plusDays(10));
	    });
	}

	@Test
	@DisplayName("Prueba de Setters: fecha de reposición nula")
	void testFechaNula() {
	    assertThrows(Exception.class, () -> {
		    m.setFechaReposicion(null);
	    });
	}
	
	@Test
	@DisplayName("Prueba de Estimación: venta inválida (valor negativo)")
	void testVentaInvalida() {
	    assertThrows(IllegalArgumentException.class, () -> {
	    	HashMap<String, Integer> ventas = new HashMap<>();
		    ventas.put("CocaCola", -4);
		    m.setVentas(ventas);
		});
	}
	
	@Test
	@DisplayName("Prueba de Estimación: inventario inválido (valor negativo)")
	void testInventarioNegativo() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			 HashMap<String, Integer> temporal = new HashMap<>();
			 temporal.put("CocaCola", -5);
			 m.setInventario(temporal);
	    });  
	}
	
	
	
	/////////////////////////////////////////////////////////
	
	@Test
	@DisplayName("Comprobar necesidad reposición: Caso afirmativo")
	void testComprobarNecesidadReposicion_verdadero() {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(output));

	    m.setFechaReposicion(LocalDate.now().minusDays(10));
	    HashMap<String, Integer> ventas = new HashMap<>();
	    ventas.put("CocaCola", 100);
	    m.setVentas(ventas);
	    
	    HashMap<String, Integer> inventario = new HashMap<>();
	    inventario.put("CocaCola", 2);
	    m.setInventario(inventario);

	    m.comprobarNecesidadReposicion();

	    assertTrue(output.toString().contains("Máquina 1: Reposición necesaria para el producto CocaCola"));

	    System.setOut(System.out);
	}
	
	@Test
	@DisplayName("Comprobar necesidad reposición: Caso negativo")
	void testComprobarNecesidadReposicion_falso() {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(output));

	    m.setFechaReposicion(LocalDate.now().minusDays(1));
	    HashMap<String, Integer> ventas = new HashMap<>();
	    ventas.put("CocaCola", 1);
	    m.setVentas(ventas);
	    HashMap<String, Integer> inventario = new HashMap<>();
	    inventario.put("CocaCola", 50);
	    m.setInventario(inventario);

	    m.comprobarNecesidadReposicion();

	    assertTrue(output.toString().isEmpty(), "No debería imprimir nada");

	    System.setOut(System.out);
	}

	@Test
	@DisplayName("Comprobar necesidad reposición: lista de productos nula")
	void testComprobarNecesidadReposicionProductosNulo() {	
		assertThrows(NullPointerException.class, () -> {
			Maquina maquina =  new Maquina(1, 0.00f, 1.00f, 20, true, null);
			maquina.comprobarNecesidadReposicion();
	    });
	}

}

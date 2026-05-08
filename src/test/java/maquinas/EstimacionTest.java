package maquinas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;


class EstimacionTest {
	static Estimacion e;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		e = new Estimacion();
	}
	
	@DisplayName("Prueba de Estimación: Calcular Estimaciones cuando ventas es 0")
	@Test
	void testSinVentas() {
	    	    
		ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Botella", "CocaCola"));
        productos.add(new Producto("Botella", "Pepsi"));
        productos.add(new Producto("Snack", "Pelotazos"));

        Maquina maquina = new Maquina(1, 0.00f, 1.00f, 20, true, productos);
        
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
	@DisplayName("Calcula correctamente los días restantes con ventas")
	void testCalculoNormal() {
		
		ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Botella", "CocaCola"));
        productos.add(new Producto("Botella", "Pepsi"));
        productos.add(new Producto("Snack", "Pelotazos"));

        Maquina maquina = new Maquina(1, 0.00f, 1.00f, 20, true, productos);
        
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

}

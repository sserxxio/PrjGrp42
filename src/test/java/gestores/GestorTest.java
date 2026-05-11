package gestores;

import maquinas.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GestorTest {
	static ArrayList<Producto> productos;
	Gestor gestor;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		productos = new ArrayList<>();
	    productos.add(new Producto("Botella", "CocaCola"));
	    productos.add(new Producto("Botella", "Pepsi"));
	    productos.add(new Producto("Snack", "Pelotazos"));
	}
	
	@BeforeEach
	void setUp() throws Exception {
		gestor = new Gestor();
	}
	
	//PRUEBAS DE REGISTRAR MÁQUINA

	@DisplayName("Prueba registrarMaquina: Prueba válida")
    @Test
    void testRegistrarMaquina() {
        gestor.registrarMaquina(1, 0.00f, 1.00f, 20, true, productos);

        Maquina maquina = gestor.buscarMaquina(1);

        assertAll("Verificar atributos de la máquina",
                () -> assertNotNull(maquina, "La máquina no debería ser null"),
                () -> assertEquals(1, maquina.getId()),
                () -> assertEquals(0.00f, maquina.getCoordX()),
                () -> assertEquals(1.00f, maquina.getCoordY()),
                () -> assertEquals(20, maquina.getCapacidad()),
                () -> assertTrue(maquina.estaOperativa()),
                () -> assertEquals(productos, maquina.getProductos())
            );
    }

	
	@DisplayName("Prueba registrarMaquina: id repetido")
    @Test
    void testRegistrarMaquina_idRepetido() {
		gestor.registrarMaquina(1, 0.00f, 1.00f, 20, true, productos);
		
		assertThrows(IllegalArgumentException.class, () -> {
	        gestor.registrarMaquina(1, 1.00f, 10.00f, 20, true, productos);
	    });
    }
	
	@DisplayName("Prueba registrarMaquina: capacidad negativa")
    @Test
    void testRegistrarMaquina_capacidadNegativa() {
		assertThrows(IllegalArgumentException.class, () -> {
	        gestor.registrarMaquina(1, 0.00f, 1.00f, -20, true, productos);
	    });
    }
	
	/////////////////////////////////////////////////////////////
	
	
    
	@DisplayName("Prueba de Gestor: Ver maquina")
    @Test
    void testMostrarInformación() {

        gestor.registrarMaquina(1, 0.00f, 1.00f, 20, true, productos);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
			gestor.mostrarInformacion(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        String salida = outContent.toString();

        assertTrue(salida.contains("Máquina encontrada"));
    }
    
    @DisplayName("Prueba de Gestor: Ver maquina sin maquina existente")
    @Test
    void testMostrarInformaciónNoExiste() {
        
        assertThrows(Exception.class, () -> {
        	gestor.mostrarInformacion(999);
        });
    }
    
    @DisplayName("Prueba de Gestor: Cargar maquinas de archivo json")
    @Test
    void testCargarMaquinas() {
        
        assertDoesNotThrow(() -> {
        	gestor.cargarMaquinaDesdeJSON("maquinas.json");
        	gestor.buscarMaquina(1);
        });
    }
    
    @DisplayName("Prueba de Gestor: Cargar maquinas de archivo txt")
    @Test
    void testCargarMaquinasTxt() {
        
        assertDoesNotThrow(() -> {
        	gestor.cargarMaquinaDesdeJSON("maquinas.txt");
        	gestor.buscarMaquina(1);
        });
    }
    
    @DisplayName("Prueba de Gestor: Cargar maquinas de archivo inexistente")
    @Test
    void testCargarMaquinasNoExiste() {
    	assertThrows(Exception.class, () -> {
    		gestor.cargarMaquinaDesdeJSON("noexiste");
	    });
    }
    
    @DisplayName("Prueba de Gestor: Cargar maquinas de archivo json mal formado")
    @Test
    void testCargarMaquinasJsonMal() {
    	assertThrows(Exception.class, () -> {
    		gestor.cargarMaquinaDesdeJSON("maquinasMal.json");
	    });
    }
}
package gestores;

import maquinas.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

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
    
//PRUEBAS DE REGISTRAR ALMACÉN

    @DisplayName("Prueba de Gestor: Registrar un almacén")
    @Test
    void testRegistrarAlmacenCorrecto() {
    	gestor.crearAlmacen("Almaceneros",productos);
    	Almacen almacen = gestor.buscarAlmacen("Almaceneros");
    	assertNotNull(almacen, "El almacen no debería ser null");
    	assertEquals("Almaceneros", almacen.getNombre());
    }
//PRUEBAS DE NOTIFICAR REPOSICION
    @DisplayName("Prueba de Gestor: Pasar una máquina que necesita reposición")
    @Test
    void testSacaNotificacion() {
    	gestor.registrarMaquina(1, 0.00f, 1.00f, 2, true, productos);
        Maquina maquina = gestor.buscarMaquina(1);
        HashMap<String, Integer> inventario = new HashMap<>();
        for(Producto p: productos){
        	inventario.put(p.getNombre(), maquina.getCapacidad());
        }
        maquina.setInventario(inventario);
        
        maquina.venderProducto(productos.get(0));
        maquina.setFechaReposicion(LocalDate.now());
        
        assertTrue(gestor.notificarReposicion(maquina, productos.get(0)));
    }
    @DisplayName("Prueba de Gestor: Pasar una máquina que no necesita reposición")
    @Test
    void testNOSacaNotificacion() {
    	gestor.registrarMaquina(2, 0.00f, 1.00f, 30, true, productos);
        Maquina maquina = gestor.buscarMaquina(2);
        HashMap<String, Integer> inventario = new HashMap<>();
        for(Producto p: productos){
        	inventario.put(p.getNombre(), maquina.getCapacidad());
        }
        maquina.setInventario(inventario);
        
        maquina.venderProducto(productos.get(0));
        maquina.setFechaReposicion(LocalDate.now());
        
        assertFalse(gestor.notificarReposicion(maquina, productos.get(0)));
    }
//PRUEBAS ELIMINAR MAQUINA
    @DisplayName("Prueba de Gestor: Eliminar una máquina")
    @Test
    void testEliminarMaquina() {
    	gestor.registrarMaquina(1, 0.00f, 1.00f, 30, true, productos);
    	
    	assertDoesNotThrow(() -> {
    		gestor.eliminarMaquina(1);
        });
    }
    
    @DisplayName("Prueba de Gestor: Eliminar una máquina que no existe")
    @Test
    void testEliminarMaquinaNoExiste() {
    	gestor.registrarMaquina(1, 0.00f, 1.00f, 30, true, productos);
    	
    	assertThrows(IllegalArgumentException.class, () -> {
    		gestor.eliminarMaquina(5);
	    });
    }
//PRUEBAS REPONER DESDE ALMACEN
    @DisplayName("Prueba de Gestor: Eliminar una máquina que no existe")
    @Test
    void testReponerArgumentos() {
    	gestor.crearAlmacen("almacen1", productos);
    	gestor.registrarMaquina(2, 0.00f, 1.00f, 30, true, productos);
        Maquina maquina = gestor.buscarMaquina(2);
        
    	assertAll("Verificar atributos no nulos",
                () -> assertThrows(IllegalArgumentException.class, () -> {
            		gestor.reponerMaquina(null, null, null);
        	    }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
            		gestor.reponerMaquina("almacen1", maquina, null);
        	    }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
            		gestor.reponerMaquina(null, maquina, productos.get(0));
        	    }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
            		gestor.reponerMaquina("almacen1", null, productos.get(0));
        	    }),
                () -> assertDoesNotThrow(() -> {
                	gestor.reponerMaquina("almacen1", maquina, productos.get(0));
                })
            );
    }
    @DisplayName("Prueba de Gestor: Existe almacen")
    @Test
    void testExisteAlmacen() {
    	gestor.crearAlmacen("almacen1", productos);
    	gestor.registrarMaquina(2, 0.00f, 1.00f, 30, true, productos);
        Maquina maquina = gestor.buscarMaquina(2);
    	
    	assertThrows(IllegalArgumentException.class, () -> {
    		gestor.reponerMaquina("almacen2", maquina, productos.get(0));
	    });
    }
    
    @DisplayName("Prueba de Gestor: Coinciden los productos")
    @Test
    void testCoincidenProductos() {
    	gestor.crearAlmacen("almacen1", productos);
    	gestor.registrarMaquina(2, 0.00f, 1.00f, 30, true, productos);
        Maquina maquina = gestor.buscarMaquina(2);
        ArrayList<Producto> productos2 = new ArrayList<>();
        productos2.add(new Producto("lata", "roncola"));
        gestor.registrarMaquina(34, 0.00f, 1.00f, 30, true, productos2);
        Maquina maquina2 = gestor.buscarMaquina(34);
        gestor.crearAlmacen("almacen2", productos2);
        assertAll("Verificar atributos no nulos",
                () -> assertThrows(IllegalArgumentException.class, () -> {
            		gestor.reponerMaquina("almacen1", maquina, new Producto("lata", "Fritas"));
        	    }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
            		gestor.reponerMaquina("almacen2", maquina, productos2.get(0));
        	    }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
            		gestor.reponerMaquina("almacen1", maquina2, productos2.get(0));
        	    }),
                () -> assertDoesNotThrow(() -> {
                	gestor.reponerMaquina("almacen1", maquina, productos.get(0));
                })
            );
    }
    ///////////////////////////////////////////////////////////////
}
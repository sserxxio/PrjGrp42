package gestores;

import maquinas.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GestorTest {

	@DisplayName("Prueba de Gestor: Añadir maquina")
    @Test
    void testRegistrarMaquina() {

        Gestor gestor = new Gestor();

        ArrayList<String> productos = new ArrayList<>();
        productos.add("CocaCola");
        productos.add("Pepsi");
        productos.add("Pelotazos");
        

        gestor.registrarMaquina(1, 0.00f, 1.00f, 20, true, productos);

        Maquina maquina = gestor.buscarMaquina(1);

        assertNotNull(maquina, "La máquina no debería ser null");
    }
    
    @DisplayName("Prueba de Gestor: Ver maquina")
    @Test
    void testVerMaquina() {

    	Gestor gestor = new Gestor();
        
        ArrayList<String> productos = new ArrayList<>();
        productos.add("CocaCola");
        productos.add("Pepsi");
        productos.add("Pelotazos");

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
    
    @DisplayName("Prueba de Gestor: Ver maquina falla")
    @Test
    void testVerMaquinaFalla() {

        Gestor gestor = new Gestor();
        
        assertThrows(Exception.class, () -> {
        	gestor.mostrarInformacion(999);
        });
    }
}
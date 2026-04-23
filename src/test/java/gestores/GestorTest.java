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

        gestor.registrarMaquina(1, 0.00f, 1.00f, 20, 20, true);

        Maquina maquina = gestor.buscarMaquina(1);

        assertNotNull(maquina, "La máquina no debería ser null");
    }
    
    @DisplayName("Prueba de Gestor: Ver maquina")
    @Test
    void testVerMaquina() {

    	Gestor gestor = new Gestor();

        ArrayList<Integer> coord = new ArrayList<>();
        coord.add(2);
        coord.add(3);

        gestor.registrarMaquina(1, 0.00f, 1.00f, 20, 20, true);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        gestor.mostrarInformacion(1);

        String salida = outContent.toString();

        assertTrue(salida.contains("Máquina encontrada"));
    }
    
    @DisplayName("Prueba de Gestor: Ver maquina falla")
    @Test
    void testVerMaquinaFalla() {

        Gestor gestor = new Gestor();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        gestor.mostrarInformacion(999);

        String salida = outContent.toString();

        assertTrue(salida.contains("Error: no existe una máquina"));
    }
    
}
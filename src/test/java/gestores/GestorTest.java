package gestores;

import maquinas.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GestorTest {

    @DisplayName("Prueba de Gestor: Añadir maquina")
    @Test
    void testRegistrarMaquina() {

        Gestor gestor = new Gestor();

        ArrayList<Integer> coord = new ArrayList<>();
        coord.add(2);
        coord.add(3);

        gestor.registrarMaquina(1, coord, 4, true);

        Maquina maquina = gestor.buscarMaquina(1);

        assertNotNull(maquina, "La máquina no debería ser null");
    }
}
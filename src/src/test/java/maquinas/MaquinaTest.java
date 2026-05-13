package maquinas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MaquinaTest {
	static Maquina maquina;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	    ArrayList<Producto> productos = new ArrayList<>();
	    productos.add(new Producto("Botella", "CocaCola"));
	    maquina = new Maquina(1, 0.00f, 1.00f, 20, true, productos);
	}

	@Test
	@DisplayName("Prueba de Actualización de Máquina: caso válido capacidad")
	void testActualizarCapacidadMaquinaCorrectamente() {
		int nuevaCapacidad = 15;
		maquina.actualizarMaquina(nuevaCapacidad, true);
		assertEquals(maquina.getCapacidad(), nuevaCapacidad);
	}
	
	@Test
	@DisplayName("Prueba de Actualización de Máquina: caso inválido capacidad")
	void testActualizarCapacidadMaquinaIncorrectamente() {
		int nuevaCapacidad = -3;
		assertThrows(Exception.class, () -> {
			maquina.actualizarMaquina(nuevaCapacidad, true);
	    });
	}
	
	@Test
	@DisplayName("Prueba de Actualización de Máquina: caso válido operatividad")
	void testActualizarOperatividadMaquina() {
		maquina.actualizarMaquina(maquina.getCapacidad(), false);
		assertEquals(maquina.estaOperativa(), false);
	}

}

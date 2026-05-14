package maquinas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MaquinasVenderTest {
	
	@Mock
    Producto mockProducto;
	static Maquina maquina;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
    void setUp() {
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(mockProducto);
        maquina = new Maquina(1, 10.0f, 20.0f, 50, true, productos);
    }

	@Test
	void testVenderProducto() {
		when(mockProducto.getNombre()).thenReturn("CocaCola");
		maquina.getInventario().put("CocaCola",5);
		maquina.venderProducto(mockProducto);
        assertEquals(4, maquina.getInventario().get("CocaCola"));
        assertEquals(1, maquina.getVentas().get("CocaCola"));
	}

}

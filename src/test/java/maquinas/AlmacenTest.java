package maquinas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AlmacenTest {
	static ArrayList<Producto> productos1;
	static ArrayList<Producto> productos2;
	static Almacen almacen1;
	static Almacen almacen2;
	static String stringPrueba;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		productos1= new ArrayList<>();
		productos1.add(new Producto("Lata","CocaCola"));
		productos1.add(new Producto("Lata","Pepsi"));
		almacen1= new Almacen("Almacen1",productos1);
		productos2= new ArrayList<>();
		productos2.add(new Producto("Bolsa","Chetos"));
		productos2.add(new Producto("Bolsa","Doritos"));
		almacen2= new Almacen("Almacen2",productos2);
		stringPrueba = new String("Hola"); 
	}

	@Test
	@DisplayName("Caja Blanca: Equals método")
	void testEqualsObject() {
		boolean prueba1 = almacen1.equals(almacen1);
		boolean prueba2 = almacen1.equals(stringPrueba);
		boolean prueba3 = almacen1.equals(almacen2);
		
		assertEquals(prueba1,true);
		assertEquals(prueba2,false);
		assertEquals(prueba3,false);
	}
	
	
	@Test
	@DisplayName("Comprobar Setters: Almacen")
	void testSetters() {
		String pruebaNombre = "Almacen de prueba";
		almacen1.setNombre(pruebaNombre);
		almacen1.setProductos(productos2);
		assertEquals(pruebaNombre,almacen1.getNombre());
		assertEquals(productos2,almacen1.getProductos());
		almacen1.anadirProducto(new Producto("Bolsa","Quesos"));
		assertEquals(3,almacen1.getProductos().size());

	}

}

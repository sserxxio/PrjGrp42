package maquinas;

import java.util.ArrayList;

public class Almacen {
	
	private String nombre;
	private ArrayList<Producto> productos = new ArrayList<>();
	
	public Almacen(String nombre, ArrayList<Producto> productos) {
		this.nombre = nombre;
		this.productos = productos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}
	
	public void anadirProducto(Producto producto) {
		this.productos.add(producto);
	}
	
	
}

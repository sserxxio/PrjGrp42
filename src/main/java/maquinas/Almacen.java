package maquinas;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	public void reponerMaquina(Maquina maquina,Producto producto) {
		HashMap<String, Integer> inventario = maquina.getInventario();
		if(!maquina.getProductos().contains(producto)) {
			throw new IllegalArgumentException("La maquina no vende este producto");
		}else {
			if(productos.contains(producto)) {
				inventario.put(producto.getNombre(), maquina.getCapacidad());
				maquina.setInventario(inventario);
			}else {
				throw new IllegalArgumentException("El almacen no contiene este producto");
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		 if (o == this) {
	            return true;
	        }
	        if (!(o instanceof Almacen)) {
	            return false;
	        }
	       Almacen almacen = (Almacen)o;
	       return this.nombre.equals(almacen.getNombre());	
	}
	
}

package maquinas;


public class Producto {
	private String tipo;
	private String nombre;
	
	public Producto(String tipo, String nombre) {
		this.tipo = tipo;
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}

package maquinas;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.time.*;

public class Maquina {
	private int id;
	private float coordenadas[];
	private int capacidad;
	private int slots;
	private HashMap<String, Integer> inventario = new HashMap<>();
	private ArrayList<Producto> productos = new ArrayList<>();
	private boolean estaOperativa;
	private HashMap<String, Integer> ventas = new HashMap<>();
	private LocalDate fechaReposicion;
	
	
	public Maquina(int id, float coordenadaX, float coordenadaY, int capacidad, boolean estaOperativa, ArrayList<String> productos) {
		this.id=id;
		this.coordenadas= new float[2];
		this.coordenadas[0] = coordenadaX;
		this.coordenadas[1] = coordenadaY;
		this.capacidad=capacidad;
		this.slots = productos.size();
		this.estaOperativa=estaOperativa;
		this.fechaReposicion=null;
		
		for(int i =0; i<this.slots; i++) {
    		this.productos.add(new Producto("botella", productos.get(i)));
    		this.inventario.put(productos.get(i), this.capacidad);
    	}
	}

    public int getId(){
        return id;
    }

    public void informacion(){
        System.out.println("\nID: " + id + 
        		"\nCoordenadas: " + coordenadas[0] + ", " + coordenadas[1] + 
        		"\nCapacidad: " + capacidad +
        		"\nSlots: " + slots +
        		"\nOperativa: " + estaOperativa);
    }
    
    public void actualizarMaquina(int capacidad, boolean estaOperativa) {
    	this.capacidad = capacidad;
    	this.estaOperativa=estaOperativa;
    }
    
    public void venderProducto(Producto producto,int cantidad) {
    	actualizarMaquina(this.capacidad,true);
    	
    	Integer num = ventas.get(producto);
    	if(num==null) num=0;
	    ventas.put(producto.getNombre(), num+1);
    	
    	notificarVenta(producto);
    }
    
    public void notificarVenta(Producto producto) {
    	System.out.println("Se han vendido una unidad del producto "+producto.getNombre());
    }
    
    public void setFechaReposicion(LocalDate fechRep) {
    	this.fechaReposicion=fechRep;
    }
    
    public LocalDate getFechaReposicion() {
    	return this.fechaReposicion;
    }
    
    public HashMap<String, Integer> getVentas() {
    	return this.ventas;
    }
    
    public void setVentas(HashMap<String, Integer> vent) {
    	this.ventas=vent;
    }
    
    public HashMap<String, Integer> getInventario() {
    	return this.inventario;
    }
    
    public void setInventario(HashMap<String, Integer> invent) {
    	this.inventario=invent;
    }

}



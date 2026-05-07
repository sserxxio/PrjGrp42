package maquinas;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.time.*;
import java.time.temporal.ChronoUnit;

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
	
	
	public Maquina(int id, float coordenadaX, float coordenadaY, int capacidad, boolean estaOperativa, ArrayList<Producto> productos) {
		//Validación de parámetros
		if (id<=0) throw new IllegalArgumentException("El id debe ser positivo");
	    if (capacidad<=0) throw new IllegalArgumentException("La capacidad debe ser positiva");
		
		//Asignación
		this.id=id;
		this.coordenadas= new float[2];
		this.coordenadas[0] = coordenadaX;
		this.coordenadas[1] = coordenadaY;
		this.capacidad=capacidad;
		this.slots = productos.size();
		this.estaOperativa=estaOperativa;
		this.productos=productos;
		this.fechaReposicion=null;
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
    
    public void venderProducto(Producto producto) {
		if(this.inventario==null) throw new IllegalArgumentException("El inventario de la máquina no puede ser nulo");
    	
    	actualizarMaquina(this.capacidad,true);
    	
    	Integer num = ventas.get(producto.getNombre());
    	if(num==null) num=0;
	    ventas.put(producto.getNombre(), num+1);
	    
	    //Reducimos en 1 el stock actual de la máquina
	    int inventarioActual = inventario.get(producto.getNombre());
	    inventario.put(producto.getNombre(),inventarioActual-1);
	    
    	notificarVenta(producto);
    }
    
    public void notificarVenta(Producto producto) {
    	System.out.println("Se han vendido una unidad del producto "+producto.getNombre());
    }
    
    //Función que comprueba si algún producto de la máquina debe ser repuesto
    public void comprobarNecesidadReposicion() {
    	for(Producto p:productos) {
    		int dias = Estimacion.calcularEstimacion(this, p);
    		if(dias<=3) System.out.println("Máquina" + id + ": Reposición necesaria para el producto " + p.getNombre());
    	}
    }
    
    public void setFechaReposicion(LocalDate fechRep) {
    	 LocalDate hoy = LocalDate.now();
    	 if((ChronoUnit.DAYS.between(fechRep, hoy)<0)||fechRep==null) {
    		 throw new IllegalArgumentException("Fecha de reposición inválida");
    	 }
    	 
    	this.fechaReposicion=fechRep;
    }
    
    public LocalDate getFechaReposicion() {
    	return this.fechaReposicion;
    }
    
    public HashMap<String, Integer> getVentas() {	
    	return this.ventas;
    }
    
    public void setVentas(HashMap<String, Integer> vent) {
    	for (Integer valor : vent.values()) {
    		if(valor<0)  throw new IllegalArgumentException("Valor de venta inválido");
    	}

    	this.ventas=vent;
    }
    
    public HashMap<String, Integer> getInventario() {
    	return this.inventario;
    }
    
    public void setInventario(HashMap<String, Integer> invent) {
    	for (Integer valor : invent.values()) {
    		if(valor<0)  throw new IllegalArgumentException("Número de productos en el inventario inválido");
    	}
    	
    	this.inventario=invent;
    }
    
    public float getCoordX() {
    	return this.coordenadas[0];
    }
    
    public float getCoordY() {
    	return this.coordenadas[1];
    }
    
    public int getCapacidad() {
    	return this.capacidad;
    }
    
    public ArrayList<Producto> getProductos(){
    	return this.productos;
    }
    
    public boolean estaOperativa() {
    	return this.estaOperativa;
    }

}



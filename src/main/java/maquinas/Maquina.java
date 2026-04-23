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
	
	
	public Maquina(int id, float coordenadaX, float coordenadaY, int capacidad, int slots, boolean estaOperativa) {
		this.id=id;
		this.coordenadas= new float[2];
		this.coordenadas[0] = coordenadaX;
		this.coordenadas[1] = coordenadaY;
		this.capacidad=capacidad;
		this.slots = slots;
		this.estaOperativa=estaOperativa;
		this.fechaReposicion=null;
	}

    public int getId(){
        return id;
    }

    public void informacion(){
        System.out.println("\nID: " + id + 
        		"\nCoordenadas: " + coordenadas[0] + ", " + coordenadas[1] + 
        		"\nCapacidad: " + capacidad +
        		"\nOperativa: " + estaOperativa);
    }
    
    public void actualizarMaquina(int capacidad, boolean estaOperativa) {
    	this.capacidad = capacidad;
    	this.estaOperativa=estaOperativa;
    }
    
    public void venderProducto(Producto producto,int cantidad) {
    	actualizarMaquina(this.capacidad,true);
    	
    	Integer num = ventas.get(producto);
    	if(num==null) num=cantidad;
    	ventas.put(producto.getNombre(), num);
    	
    	notificarVenta(producto,cantidad);
    	
    }
    
    public void notificarVenta(Producto producto,int cantidad) {
    	System.out.println("Se han vendido "+ cantidad +" del producto"+producto.getNombre());
    }
    public void inicializarStock() {
    	Scanner sc = new Scanner(System.in);
    	for(int i =0; i<this.slots; i++) {
    		System.out.println("Producto "+i);
    		System.out.print("Nombre: ");
    		String linea = sc.nextLine().trim();
    		this.productos.add(new Producto("botella", linea));
    		this.inventario.put(linea, this.capacidad);
    	}
    	//sc.close();
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



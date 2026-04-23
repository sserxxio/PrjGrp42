package maquinas;
import java.util.ArrayList;

public class Maquina {
	private int id;
	private ArrayList<Integer> coordenadas;
	private int capacidad;
	private Boolean estaOperativa;
	
	public Maquina(int id, ArrayList<Integer> coordenadas, int capacidad, Boolean estaOperativa) {
		this.id=id;
		this.coordenadas=coordenadas;
		this.capacidad=capacidad;
		this.estaOperativa=estaOperativa;
	}

    public int getId(){
        return id;
    }

    public void informacion(){
        System.out.println("\nID: " + id + 
        		"\nCoordenadas: " + coordenadas.get(0) + ", " + coordenadas.get(1) + 
        		"\nCapacidad: " + capacidad +
        		"\nOperativa: " + estaOperativa);
    }
    
    public void actualizarMaquina(int capacidad, boolean estaOperativa) {
    	this.capacidad = capacidad;
    	this.estaOperativa=estaOperativa;
    }
    
    public void venderProducto(Producto producto,int cantidad) {
    	actualizarMaquina(this.capacidad,true);
    	notificarVenta(producto,cantidad);
    	
    }
    
    public void notificarVenta(Producto producto,int cantidad) {
    	System.out.println("Se han vendido "+ cantidad +" del producto"+producto.getNombre());
    }
    
    

}



package gestores;

import maquinas.Maquina;

import maquinas.Producto;
import maquinas.Estimacion;
import maquinas.Almacen;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import maquinas.Maquina;


public class Gestor {

    private ArrayList<Maquina> maquinas;
    private ArrayList<Almacen> almacenes;

    public Gestor() {
        this.maquinas = new ArrayList<>();
        this.almacenes = new ArrayList<>();
    }
    
    public Maquina buscarMaquina(int id) {
        for (Maquina m : maquinas) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    public void registrarMaquina(int id, float coordX, float coordY, int capacidad, boolean estaOperativa, ArrayList<Producto> productos) {
    	if(buscarMaquina(id)!=null) {
    		 throw new IllegalArgumentException("Ya hay una máquina registrada con el id "+ id);
    	}
        Maquina m = new Maquina(id, coordX, coordY, capacidad, estaOperativa, productos);
        maquinas.add(m);
    }
    
    public void eliminarMaquina(int id) throws Exception {
    	Maquina m = buscarMaquina(id);
    	if(m == null) {
    		throw new IllegalArgumentException("No existe ninguna máquina con el id: "+id);
    	}else {
    		this.maquinas.remove(m);
    	}
    }
    
    public void mostrarInformacion(int id) throws Exception {
    	
    	Maquina m = buscarMaquina(id);
    	
    	if (m == null) {
            throw new Exception();
        } else {
            System.out.println("Máquina encontrada:");
            m.informacion();
        }
    }
    
    public void cargarMaquinaDesdeJSON(String rutaArchivo) throws Exception {

        DAOCarga dao = new DAOCarga();
        dao.cargarMaquinaDesdeJSON(this, rutaArchivo);
    }
    
    public boolean notificarReposicion(Maquina m, Producto p) {
    	if(Estimacion.calcularEstimacion(m, p) <2) {
    		return true;
    	}else {
    		return false;
    	}
    }
    public void crearAlmacen(String nombre, ArrayList<Producto> productos) {
    	this.almacenes.add(new Almacen(nombre, productos));
    }
    
    public Almacen buscarAlmacen(String nombre) {
        for (Almacen a : almacenes) {
            if (a.getNombre().equals(nombre)) {
                return a;
            }
        }
        return null;
    }
    
    public void reponerMaquina(String almacen, Maquina maquina, Producto producto) throws IllegalArgumentException{
    	if(maquina == null) {
    		throw new IllegalArgumentException("El argumento maquina no puede ser nulo");
    	}
    	if(almacen == null) {
    		throw new IllegalArgumentException("El argumento almacen no puede ser nulo");
    	}
    	if(producto == null) {
    		throw new IllegalArgumentException("El argumento producto no puede ser nulo");
    	}
    	for(Almacen a: this.almacenes) {
    		if(a.getNombre().equals(almacen)) {
    			a.reponerMaquina(maquina, producto);
    			return;
    		}
    	}
    	throw new IllegalArgumentException("El almacen no existe");
    }
    
    
    public void listarMaquinasReposicion() {
        if(maquinas==null) throw new NullPointerException("La lista de maquinas no puede ser nula");
        
        for(Maquina m:maquinas) {
    		m.comprobarNecesidadReposicion();
    	}
    }
    
    public void listarMaquinasCoordenadas(float xMin, float xMax, float yMin, float yMax) throws Exception {
    	
    	if(xMax < xMin || yMax < yMin) {
    		Exception e = new Exception();
    		throw e;
    	}
    	
    	for(Maquina m:maquinas) {
    		float xCord = m.getCoordX();
    		float yCord = m.getCoordY();
    		
    		if(xCord < xMax && xCord > xMin && yCord < yMax && yCord > yMin) {
    			m.informacion();
    		}
    	}
    }
}
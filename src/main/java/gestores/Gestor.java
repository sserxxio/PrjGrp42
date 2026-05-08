package gestores;

import maquinas.Maquina;

import maquinas.Producto;
import maquinas.Estimacion;
import maquinas.Almacen;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import maquinas.Maquina;
import maquinas.Maquina;

public class Gestor {

    private ArrayList<Maquina> maquinas;
    private ArrayList<Almacen> almacenes;

    public Gestor() {
        this.maquinas = new ArrayList<>();
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
    
    public void eliminarMaquina(int id) {
    	this.maquinas.remove(buscarMaquina(id));
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
    
    public void cargarMaquinaDesdeJSON(String rutaArchivo) throws Exception{

        FileReader reader = new FileReader(rutaArchivo);

        JSONTokener tokener = new JSONTokener(reader);
        JSONObject obj = new JSONObject(tokener);

        int id = obj.getInt("id");
        float coordenadaX = obj.getFloat("coordenadaX");
        float coordenadaY = obj.getFloat("coordenadaY");
        int capacidad = obj.getInt("capacidad");
        boolean estaOperativa = obj.getBoolean("estaOperativa");

        JSONArray productosJSON = obj.getJSONArray("productos");

        ArrayList<Producto> productos = new ArrayList<>();

        for (int i = 0; i < productosJSON.length(); i++) {
            productos.add(new Producto("botella", productosJSON.getString(i)));
        }

        registrarMaquina(
            id,
            coordenadaX,
            coordenadaY,
            capacidad,
            estaOperativa,
            productos
        );

        System.out.println("Máquina cargada correctamente desde JSON.");

        reader.close();
    }
    
    public boolean notificarReposicion(Maquina m, Producto p) {
    	if(Estimacion.calcularEstimacion(m, p) <2) {
    		return true;
    	}else {
    		return false;
    	}
    }
    private void crearAlmacen(String nombre, ArrayList<Producto> productos) {
    	this.almacenes.add(new Almacen(nombre, productos));
    }
    private void reponerMaquina(String almacen, Maquina maquina, Producto producto) {
    	for(Almacen a: this.almacenes) {
    		if(a.getNombre().equals(almacen)) {
    			a.reponerMaquina(maquina, producto);
    		}
    
    
    public void listarMaquinasReposicion() {
    	for(Maquina m:maquinas) {
    		m.comprobarNecesidadReposicion();
    	}
    }
}
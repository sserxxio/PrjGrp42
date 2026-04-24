package gestores;

import maquinas.Maquina;
import maquinas.Producto;
import maquinas.Estimacion;

import java.util.ArrayList;
import java.util.Scanner;

public class Gestor {

    private ArrayList<Maquina> maquinas;

    public Gestor() {
        this.maquinas = new ArrayList<>();
    }

    public void registrarMaquina(int id, float coordX, float coordY, int capacidad, int slots, boolean estaOperativa) {
        Maquina m = new Maquina(id, coordX, coordY, capacidad, slots, estaOperativa);
        m.inicializarStock();
        maquinas.add(m);
    }
    public void eliminarMaquina(int id) {
    	this.maquinas.remove(buscarMaquina(id));
    }

    public Maquina buscarMaquina(int id) {
        for (Maquina m : maquinas) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }
    
    public void mostrarInformacion(int id) {
    	
    	Maquina m = buscarMaquina(id);
    	
    	if (m == null) {
            System.out.println("Error: no existe una máquina con ID " + id);
        } else {
            System.out.println("Máquina encontrada:");
            m.informacion();
        }
    }
    public boolean notificarReposicion(Maquina m, Producto p) {
    	if(Estimacion.calcularEstimacion(m, p) <2) {
    		return true;
    	}else {
    		return false;
    	}
    }
}
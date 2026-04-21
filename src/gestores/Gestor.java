package gestores;
import maquinas.Maquina;

import java.util.ArrayList;

public class Gestor {
	private ArrayList<Maquina> maquinas;


	public Gestor() {
		this.maquinas=new ArrayList<>();
	}
	
	public void registrarMaquina(int id, ArrayList<Integer> coord, int capacidad, Boolean estaOperativa) {
		Maquina m = new Maquina(id, coord, capacidad, estaOperativa);
		maquinas.add(m);
	}
}

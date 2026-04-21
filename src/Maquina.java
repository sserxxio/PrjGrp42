//PRUEBA PARA VER QUE SE SUBE
import java.util.ArrayList;

public class Maquina {
	private int id;
	private ArrayList cooredendas;
	private int capacidad;
	private Boolean estaOperativa;
}

public Maquina(int id, ArrayList coordenadas, int capacidad, Boolean estaOperativa) {
	this.id=id;
	this.cooredendas=coordenadas;
	this.capacidad=capacidad;
	this.estaOperativa=estaOperativa;
}

package maquinas;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Estimacion {

	
	
	public static int calcularEstimacion(Maquina maquina, Producto producto) {
		 LocalDate hoy = LocalDate.now();
		 
		 //Recuperamos el tiempo desde la última reposición
		 LocalDate ultimaReposicion = maquina.getFechaReposicion();
		 
		 //Calculamos el promedio de producto vendido al día
		 float dias = (float) (ChronoUnit.DAYS.between(ultimaReposicion, hoy)+0.01f);
		 float numVentas = maquina.getVentas().getOrDefault(producto.getNombre(),0);
		 if(numVentas==0) return 15; //Número arbitrariamente elevado para indicar que no hay prisa por reponer 
		 
		 float consumoDia = numVentas/dias;
		 int diasRestantes = (int) Math.floor(maquina.getInventario().get(producto.getNombre())/consumoDia);
		
		return diasRestantes;
	}
}

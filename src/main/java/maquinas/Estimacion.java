package maquinas;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Estimacion {

	public static int calcularEstimacion(Maquina maquina, Producto producto) {
		 if (maquina.getInventario() == null) 
			 throw new IllegalArgumentException("El inventario no puede ser nulo");
		 if (maquina.getFechaReposicion() == null) 
		     throw new IllegalArgumentException("La fecha de reposición no puede ser nula");
		 if (maquina.getVentas() == null) 
		     throw new IllegalArgumentException("Las ventas no pueden ser nulas");
		 if (producto.getNombre() == null || producto.getNombre().isEmpty()) 
		     throw new IllegalArgumentException("El nombre del producto no puede ser nulo o vacío");
		
		 LocalDate hoy = LocalDate.now();
		 
		 //Recuperamos el tiempo desde la última reposición
		 LocalDate ultimaReposicion = maquina.getFechaReposicion();
		 
		 //Calculamos el promedio de producto vendido al día
		 float dias = (float) (ChronoUnit.DAYS.between(ultimaReposicion, hoy)+0.01f);
		 if(dias < 1) {
			 dias = 1;
		 }
		 float numVentas = maquina.getVentas().getOrDefault(producto.getNombre(),0);
		 if(numVentas==0) return 15; //Número arbitrariamente elevado para indicar que no hay prisa por reponer 
		 
		 float consumoDia = numVentas/dias;
		 int diasRestantes = (int) Math.floor(maquina.getInventario().get(producto.getNombre())/consumoDia);
		
		return diasRestantes;
	}
}

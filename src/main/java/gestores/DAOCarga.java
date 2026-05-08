package gestores;

import java.io.FileReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import maquinas.Producto;

public class DAOCarga {

	public DAOCarga() {
		
	}
	
	public void cargarMaquinaDesdeJSON(Gestor gestor, String rutaArchivo) throws Exception {

        URL resource = getClass().getClassLoader().getResource(rutaArchivo);
        String ruta = Paths.get(resource.toURI()).toFile().getAbsolutePath();
        FileReader reader = new FileReader(ruta);
        JSONTokener tokener = new JSONTokener(reader);
        JSONArray maquinasJSON = new JSONArray(tokener);

        for (int j = 0; j < maquinasJSON.length(); j++) {

            JSONObject obj = maquinasJSON.getJSONObject(j);

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

            gestor.registrarMaquina(
                id,
                coordenadaX,
                coordenadaY,
                capacidad,
                estaOperativa,
                productos
            );
        }

        System.out.println("Máquinas cargadas correctamente desde JSON.");

        reader.close();
    }
}

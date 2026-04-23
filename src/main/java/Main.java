import java.util.ArrayList;
import java.util.Scanner;
import gestores.Gestor;

public class Main {

	public static void main(String[] args) {

		Gestor gestor = new Gestor();
		
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Sistema de gestion de máquinas ===");
        System.out.println("Escribe 'help' o 'h' para ver los comandos.");

        while (true) {

            //Leemos la linea por consola
            System.out.print("> ");
            String linea = sc.nextLine().trim();

            if (linea.isEmpty()) {
                continue;
            }

            String[] partes = linea.split("\\s+");
            String comando = partes[0].toLowerCase();

            //Exit para salir
            if (comando.equals("exit") || comando.equals("e")) {
                System.out.println("Saliendo...");
                break;
            }

            //Help para ver lso comandos y como usarlos
            else if (comando.equals("help") || comando.equals("h")) {

                System.out.println("\nComandos disponibles:");
                System.out.println("-----------------------------------");
                System.out.println("r | registrar <id> <x> <y> <capacidad> <operativa>");
                System.out.println("    Ejemplo: registrar 1 10 20 500 true");
                System.out.println();
                System.out.println("v | ver <id>");
                System.out.println("    Ejemplo: ver 1");
                System.out.println();
                System.out.println("e | exit");
                System.out.println("-----------------------------------\n");
            }

            //Registrar una maquina
            else if (comando.equals("r") || comando.equals("registrar")) {

                try {
                    if (partes.length != 6) {
                        System.out.println("Error: numero incorrecto de argumentos.");
                        System.out.println("Uso: registrar <id> <x> <y> <capacidad> <operativa>");
                        continue;
                    }

                    int id = Integer.parseInt(partes[1]);
                    int x = Integer.parseInt(partes[2]);
                    int y = Integer.parseInt(partes[3]);
                    int capacidad = Integer.parseInt(partes[4]);
                    boolean operativa = Boolean.parseBoolean(partes[5]);

                    ArrayList<Integer> coord = new ArrayList<>();
                    coord.add(x);
                    coord.add(y);

                    gestor.registrarMaquina(id, coord, capacidad, operativa);

                    System.out.println("Máquina registrada correctamente.");

                } catch (Exception e) {
                    System.out.println("Error al registrar la máquina: " + e.getMessage());
                }
            }

            //Ver la informacion de una maquina
            else if (comando.equals("v") || comando.equals("ver")) {

                try {

                    if (partes.length != 2) {
                        System.out.println("Error: numero incorrecto de argumentos.");
                        System.out.println("Uso: ver <id>");
                        continue;
                    }

                    int id = Integer.parseInt(partes[1]);
                    gestor.mostrarInformacion(id);

                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            //Cualquier otra string
            else {
                System.out.println("Comando desconocido. Usa 'help' o 'h'.");
            }
        }

        sc.close();
    }
}



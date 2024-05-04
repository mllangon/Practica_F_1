import java.io.*;
import java.util.Scanner;

public class GestorArchivo {
    private static String archivoActual = null;

    public static void guardarExperimento(Experimento experimento) {
        if (archivoActual != null) {
            guardar(experimento, archivoActual);
        }
    }

    public static void guardarComo(Experimento experimento, String nombreArchivo) {
        guardar(experimento, nombreArchivo);
        archivoActual = nombreArchivo;
    }

    private static void guardar(Experimento experimento, String nombreArchivo) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(nombreArchivo))) {
            out.print(experimento.toString());
            System.out.println("Experimento guardado correctamente en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    public static Experimento cargarExperimento(String nombreArchivo) {
        try (Scanner in = new Scanner(new FileInputStream(nombreArchivo))) {
            Experimento experimento = new Experimento();
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (!line.trim().isEmpty()) {
                    try {
                        PoblacionBacterias poblacion = PoblacionBacterias.fromString(line);
                        experimento.agregarPoblacion(poblacion);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error al procesar la línea '" + line + "': " + e.getMessage());
                    }
                }
            }
            archivoActual = nombreArchivo;
            System.out.println("Experimento cargado correctamente desde: " + nombreArchivo);
            return experimento;
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
            return null;
        }
    }

    public static String getArchivoActual() {
        return archivoActual;
    }
}

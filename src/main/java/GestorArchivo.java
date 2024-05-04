import java.io.*;

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
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            out.writeObject(experimento);
            System.out.println("Experimento guardado correctamente en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    public static Experimento cargarExperimento(String nombreArchivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            Experimento experimento = (Experimento) in.readObject();
            archivoActual = nombreArchivo;
            System.out.println("Experimento cargado correctamente desde: " + nombreArchivo);
            return experimento;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
            return null;
        }
    }

    public static String getArchivoActual() {
        return archivoActual;
    }
}

import java.util.List;
import java.util.ArrayList;

public class Experimento {
    private List<PoblacionBacterias> poblaciones;

    public Experimento() {
        this.poblaciones = new ArrayList<>();
    }

    public void agregarPoblacion(PoblacionBacterias poblacion) {
        this.poblaciones.add(poblacion);
    }

    public void eliminarPoblacion(String nombre) {
        this.poblaciones.removeIf(p -> p.getNombre().equals(nombre));
    }

    public List<PoblacionBacterias> getPoblaciones() {
        return poblaciones;
    }

    public PoblacionBacterias getPoblacion(String nombre) {
        return poblaciones.stream()
                .filter(p -> p.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    public void guardar(String nombreArchivo) {
        GestorArchivo.guardarExperimento(this, nombreArchivo);
    }

    public static Experimento cargar(String nombreArchivo) {
        return GestorArchivo.cargarExperimento(nombreArchivo);
    }
}

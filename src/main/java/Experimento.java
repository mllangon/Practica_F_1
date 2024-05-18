import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Experimento implements Serializable {
    private List<PoblacionBacterias> poblaciones;

    public Experimento() {
        poblaciones = new ArrayList<>();
    }

    public void agregarPoblacion(PoblacionBacterias poblacion) {
        poblaciones.add(poblacion);
    }

    public void eliminarPoblacion(String nombre) {
        poblaciones.removeIf(p -> p.getNombre().equals(nombre));
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

    public void ordenarPorNombre() {
        poblaciones.sort((p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()));
    }

    public void ordenarPorFecha() {
        poblaciones.sort((p1, p2) -> p1.getFechaInicio().compareTo(p2.getFechaInicio()));
    }

    public void ordenarPorNumeroBacterias() {
        poblaciones.sort((p1, p2) -> Integer.compare(p1.getNumeroInicialBacterias(), p2.getNumeroInicialBacterias()));
    }

    @Override
    public String toString() {
        return poblaciones.stream()
                .map(PoblacionBacterias::toString)
                .collect(Collectors.joining("\n"));
    }
}

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    }

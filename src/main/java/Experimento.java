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
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (PoblacionBacterias poblacion : poblaciones) {
                sb.append(poblacion.getNombre()).append(",");
                sb.append(poblacion.getFechaInicio()).append(",");
                sb.append(poblacion.getFechaFin()).append(",");
                sb.append(poblacion.getNumeroInicialBacterias()).append(",");
                sb.append(poblacion.getTemperatura()).append(",");
                sb.append(poblacion.getLuminosidad()).append(",");
                for (int dosis : poblacion.getDosisComida()) {
                    sb.append(dosis).append(",");
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

public class PoblacionBacterias implements Serializable {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int numeroInicialBacterias;
    private double temperatura;
    private String luminosidad;
    private int[] dosisComida;
    private String patronComida;

    public PoblacionBacterias(String nombre, LocalDate fechaInicio, LocalDate fechaFin, int numeroInicialBacterias, double temperatura, String luminosidad, int[] dosisComida) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numeroInicialBacterias = numeroInicialBacterias;
        this.temperatura = temperatura;
        this.luminosidad = luminosidad;
        this.dosisComida = dosisComida;
    }

    public static PoblacionBacterias fromString(String str) {
        String[] parts = str.split(",");
        String nombre = parts[0];
        LocalDate fechaInicio = LocalDate.parse(parts[1]);
        LocalDate fechaFin = LocalDate.parse(parts[2]);
        int numeroInicialBacterias = Integer.parseInt(parts[3]);
        double temperatura = Double.parseDouble(parts[4]);
        String luminosidad = parts[5];
        int[] dosisComida = Arrays.stream(parts, 6, parts.length).mapToInt(Integer::parseInt).toArray();
        return new PoblacionBacterias(nombre, fechaInicio, fechaFin, numeroInicialBacterias, temperatura, luminosidad, dosisComida);
    }

    public void setFoodPattern(String patron, int comidaInicial, int comidaIncremento, int comidaFinal) {
        this.patronComida = patron;
        switch (patron) {
            case "Constant":
                Arrays.fill(dosisComida, comidaInicial);
                break;
            case "Linear Increase":
                for (int i = 0; i < dosisComida.length; i++) {
                    dosisComida[i] = comidaInicial + (comidaFinal - comidaInicial) * i / (dosisComida.length - 1);
                }
                break;
            case "Alternating":
                for (int i = 0; i < dosisComida.length; i++) {
                    dosisComida[i] = (i % 2 == 0) ? comidaInicial : 0;
                }
                break;
            case "Incremental":
                Arrays.fill(dosisComida, comidaInicial);
                for (int i = 0; i < dosisComida.length; i += comidaIncremento) {
                    if (i < dosisComida.length) {
                        dosisComida[i] = comidaIncremento;
                    }
                }
                dosisComida[dosisComida.length - 1] = comidaFinal;
                break;
            default:
                throw new IllegalArgumentException("Patrón de comida no reconocido.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public int getNumeroInicialBacterias() {
        return numeroInicialBacterias;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public String getLuminosidad() {
        return luminosidad;
    }

    public int[] getDosisComida() {
        return dosisComida;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Fecha de inicio: ").append(fechaInicio).append("\n");
        sb.append("Fecha de fin: ").append(fechaFin).append("\n");
        sb.append("Número inicial de bacterias: ").append(numeroInicialBacterias).append("\n");
        sb.append("Temperatura: ").append(temperatura).append("\n");
        sb.append("Luminosidad: ").append(luminosidad).append("\n");
        sb.append("Dosis de comida:\n");
        for (int i = 0; i < dosisComida.length; i++) {
            sb.append("Día ").append(i + 1).append(": ").append(dosisComida[i] * 1000).append(" µg\n"); // Convert back to µg
        }
        return sb.toString();
    }
}

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Arrays;

public class PoblacionBacterias implements Serializable {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int numeroInicialBacterias;
    private double temperatura;
    private String luminosidad;
    private int[] dosisComida;

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
        int[] dosisComida = new int[parts.length - 6];
        for (int i = 0; i < dosisComida.length; i++) {
            dosisComida[i] = Integer.parseInt(parts[6 + i]);
        }
        return new PoblacionBacterias(nombre, fechaInicio, fechaFin, numeroInicialBacterias, temperatura, luminosidad, dosisComida);
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
            sb.append("Día ").append(i + 1).append(": ").append(dosisComida[i]).append("\n");
        }
        return sb.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getLuminosidad() {
        return luminosidad;
    }

    public void setLuminosidad(String luminosidad) {
        this.luminosidad = luminosidad;
    }

    public int[] getDosisComida() {
        return dosisComida;
    }

    public void setDosisComida(int[] dosisComida) {
        this.dosisComida = dosisComida;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getNumeroInicialBacterias() {
        return numeroInicialBacterias;
    }

    public void setNumeroInicialBacterias(int numeroInicialBacterias) {
        this.numeroInicialBacterias = numeroInicialBacterias;
    }

    public void setDuration(int days) {
        this.fechaFin = this.fechaInicio.plusDays(days);
    }

    public void setFoodPattern(String pattern, int initialAmount, int incrementAmount, int finalAmount) {
        switch (pattern) {
            case "Constant":
                dosisComida = new int[getDuration()];
                Arrays.fill(dosisComida, initialAmount);
                break;
            case "Linear Increase":
                dosisComida = new int[getDuration()];
                int increment = (finalAmount - initialAmount) / (getDuration() - 1);
                for (int i = 0; i < dosisComida.length; i++) {
                    dosisComida[i] = initialAmount + (increment * i);
                }
                break;
            case "Alternating":
                dosisComida = new int[getDuration()];
                for (int i = 0; i < dosisComida.length; i++) {
                    dosisComida[i] = (i % 2 == 0) ? initialAmount : 0;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid food pattern");
        }
    }

    private int getDuration() {
        return (int) (fechaFin.toEpochDay() - fechaInicio.toEpochDay());
    }
}
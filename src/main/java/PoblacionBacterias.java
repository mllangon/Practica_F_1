import java.time.LocalDate;
import java.io.Serializable;

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
        if (parts.length < 36) {
            throw new IllegalArgumentException("La cadena de texto tiene " + parts.length + " campos, pero se esperaban 36");
        }
        try {
            String nombre = parts[0];
            if (nombre == null || nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre de la población no puede ser null o vacío");
            }
            LocalDate fechaInicio = LocalDate.parse(parts[1]);
            LocalDate fechaFin = LocalDate.parse(parts[2]);
            int numeroInicialBacterias = Integer.parseInt(parts[3]);
            double temperatura = Double.parseDouble(parts[4]);
            String luminosidad = parts[5];
            int[] dosisComida = new int[30];
            for (int i = 0; i < 30; i++) {
                dosisComida[i] = Integer.parseInt(parts[6 + i]);
            }
            return new PoblacionBacterias(nombre, fechaInicio, fechaFin, numeroInicialBacterias, temperatura, luminosidad, dosisComida);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al procesar la cadena de texto: " + e.getMessage(), e);
        }
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
}
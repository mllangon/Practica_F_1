import java.time.LocalDate;

public class PoblacionBacterias {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int numeroInicialBacterias;
    private double temperatura;
    private String luminosidad;
    private int[] dosisComida;

    public PoblacionBacterias(String nombre, LocalDate fechaInicio, LocalDate fechaFin,
                              int numeroInicialBacterias, double temperatura, String luminosidad,
                              int comidaInicial, int diaIncremento, int comidaIncremento, int comidaFinal) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numeroInicialBacterias = numeroInicialBacterias;
        this.temperatura = temperatura;
        this.luminosidad = luminosidad;
        this.dosisComida = new int[30];
        calcularDosisComida(comidaInicial, diaIncremento, comidaIncremento, comidaFinal);
    }

    private void calcularDosisComida(int comidaInicial, int diaIncremento, int comidaIncremento, int comidaFinal) {
        for (int i = 0; i < 30; i++) {
            if (i < diaIncremento) {
                dosisComida[i] = comidaInicial + (comidaIncremento - comidaInicial) / diaIncremento * i;
            } else {
                dosisComida[i] = comidaIncremento - (comidaIncremento - comidaFinal) / (29 - diaIncremento) * (i - diaIncremento);
            }
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

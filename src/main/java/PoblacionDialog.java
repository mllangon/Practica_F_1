import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;

public class PoblacionDialog extends JDialog {
    private JTextField nombreField, temperaturaField, comidaInicialField, comidaFinalField, diaIncrementoField, comidaIncrementoField;
    private JComboBox<String> luminosidadCombo, duracionCombo, patronCombo;
    private PoblacionBacterias resultado;

    public PoblacionDialog(Frame owner) {
        super(owner, "Datos de la Población", true);
        setLayout(new GridLayout(0, 2));
        agregarComponentes();
        pack();
    }

    private void agregarComponentes() {
        add(new JLabel("Nombre:"));
        nombreField = new JTextField(10);
        add(nombreField);

        add(new JLabel("Temperatura:"));
        temperaturaField = new JTextField(5);
        add(temperaturaField);

        add(new JLabel("Duración (días):"));
        duracionCombo = new JComboBox<>(new String[]{"15", "30", "45"});
        add(duracionCombo);

        add(new JLabel("Patrón de comida:"));
        patronCombo = new JComboBox<>(new String[]{"Constant", "Linear Increase", "Alternating"});
        add(patronCombo);

        add(new JLabel("Comida Inicial (microgramos):"));
        comidaInicialField = new JTextField(5);
        add(comidaInicialField);

        add(new JLabel("Comida Final (microgramos):"));
        comidaFinalField = new JTextField(5);
        add(comidaFinalField);

        add(new JLabel("Día de Incremento:"));
        diaIncrementoField = new JTextField(5);
        add(diaIncrementoField);

        add(new JLabel("Cantidad de Incremento:"));
        comidaIncrementoField = new JTextField(5);
        add(comidaIncrementoField);

        add(new JLabel("Luminosidad:"));
        luminosidadCombo = new JComboBox<>(new String[]{"Alta", "Media", "Baja"});
        add(luminosidadCombo);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(e -> aceptar());
        add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> cancelar());
        add(btnCancelar);
    }

    private void aceptar() {
        try {
            String nombre = nombreField.getText();
            double temperatura = Double.parseDouble(temperaturaField.getText());
            int duracion = Integer.parseInt((String) duracionCombo.getSelectedItem());
            String patronComida = (String) patronCombo.getSelectedItem();
            int comidaInicial = Integer.parseInt(comidaInicialField.getText());
            int comidaFinal = Integer.parseInt(comidaFinalField.getText());
            int diaIncremento = Integer.parseInt(diaIncrementoField.getText());
            int comidaIncremento = Integer.parseInt(comidaIncrementoField.getText());
            String luminosidad = (String) luminosidadCombo.getSelectedItem();

            if (comidaInicial >= 300000 || comidaIncremento >= 300000 || comidaFinal >= 300000) {
                throw new IllegalArgumentException("Las cantidades de comida deben ser valores enteros menores que 300,000");
            }

            PoblacionBacterias nuevaPoblacion = new PoblacionBacterias(nombre, LocalDate.now(), LocalDate.now().plusDays(duracion), 1000, temperatura, luminosidad, new int[duracion]);
            nuevaPoblacion.setFoodPattern(patronComida, comidaInicial, comidaIncremento, comidaFinal);
            resultado = nuevaPoblacion;
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en el formato de los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar() {
        resultado = null;
        dispose();
    }

    public PoblacionBacterias mostrarDialogo() {
        setVisible(true);
        return resultado;
    }
}
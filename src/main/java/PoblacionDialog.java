import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;

public class PoblacionDialog extends JDialog {
    private JTextField nombreField, temperaturaField, comidaInicialField, diaIncrementoField, comidaIncrementoField, comidaFinalField;
    private JComboBox<String> luminosidadCombo;
    private PoblacionBacterias resultado;

    public PoblacionDialog(Frame owner) {
        super(owner, "Datos de la Población", true);
        setLayout(new GridLayout(0, 2));
        agregarComponentes();
        pack();
    }

    private void agregarComponentes() {
        add(new JLabel("Nombre: (Nombre de la colonia de bacterias)"));
        nombreField = new JTextField(10);
        add(nombreField);

        add(new JLabel("Temperatura: (Temperatura del ambiente en grados Celsius)"));
        temperaturaField = new JTextField(5);
        add(temperaturaField);

        add(new JLabel("Comida Inicial: (Cantidad inicial de comida para las bacterias)"));
        comidaInicialField = new JTextField(5);
        add(comidaInicialField);

        add(new JLabel("Día Incremento: (Día en el que se incrementará la comida)"));
        diaIncrementoField = new JTextField(5);
        add(diaIncrementoField);

        add(new JLabel("Comida en Incremento: (Cantidad de comida que se añadirá en el día de incremento)"));
        comidaIncrementoField = new JTextField(5);
        add(comidaIncrementoField);

        add(new JLabel("Comida Final: (Cantidad final de comida para las bacterias)"));
        comidaFinalField = new JTextField(5);
        add(comidaFinalField);

        add(new JLabel("Luminosidad: (Nivel de luminosidad del ambiente: Alta, Media, Baja)"));
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
            int comidaInicial = Integer.parseInt(comidaInicialField.getText());
            int diaIncremento = Integer.parseInt(diaIncrementoField.getText());
            int comidaIncremento = Integer.parseInt(comidaIncrementoField.getText());
            int comidaFinal = Integer.parseInt(comidaFinalField.getText());
            String luminosidad = (String) luminosidadCombo.getSelectedItem();

            if (comidaInicial >= 300 || comidaIncremento >= 300 || comidaFinal >= 300) {
                throw new IllegalArgumentException("Las cantidades de comida deben ser valores enteros menores que 300");
            }

            int[] dosisComida = new int[30]; // Assuming the array size is 30
            Arrays.fill(dosisComida, comidaInicial);
            for (int i = diaIncremento; i < dosisComida.length; i += diaIncremento) {
                dosisComida[i] = comidaIncremento;
            }
            dosisComida[dosisComida.length - 1] = comidaFinal;

            resultado = new PoblacionBacterias(nombre, LocalDate.now(), LocalDate.now().plusDays(30), 1000,
                    temperatura, luminosidad, dosisComida);
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

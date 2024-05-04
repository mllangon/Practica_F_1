import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

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
        add(new JLabel("Nombre:"));
        nombreField = new JTextField(10);
        add(nombreField);

        add(new JLabel("Temperatura:"));
        temperaturaField = new JTextField(5);
        add(temperaturaField);

        add(new JLabel("Comida Inicial:"));
        comidaInicialField = new JTextField(5);
        add(comidaInicialField);

        add(new JLabel("Día Incremento:"));
        diaIncrementoField = new JTextField(5);
        add(diaIncrementoField);

        add(new JLabel("Comida en Incremento:"));
        comidaIncrementoField = new JTextField(5);
        add(comidaIncrementoField);

        add(new JLabel("Comida Final:"));
        comidaFinalField = new JTextField(5);
        add(comidaFinalField);

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
            int comidaInicial = Integer.parseInt(comidaInicialField.getText());
            int diaIncremento = Integer.parseInt(diaIncrementoField.getText());
            int comidaIncremento = Integer.parseInt(comidaIncrementoField.getText());
            int comidaFinal = Integer.parseInt(comidaFinalField.getText());
            String luminosidad = (String) luminosidadCombo.getSelectedItem();

            resultado = new PoblacionBacterias(nombre, LocalDate.now(), LocalDate.now().plusDays(30), 1000,
                    temperatura, luminosidad, comidaInicial, diaIncremento,
                    comidaIncremento, comidaFinal);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en el formato de los datos.", "Error", JOptionPane.ERROR_MESSAGE);
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

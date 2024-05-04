import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class ExperimentoGUI extends JFrame {
    private Experimento experimentoActual;
    private JTextField nombrePoblacionField;
    private JTextArea detallesArea;
    private JList<String> listaPoblaciones;
    private DefaultListModel<String> modeloLista;

    public ExperimentoGUI() {
        super("Gestor de Experimentos de Bacterias");
        experimentoActual = new Experimento();
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Lista de poblaciones
        modeloLista = new DefaultListModel<>();
        listaPoblaciones = new JList<>(modeloLista);
        listaPoblaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPoblaciones.addListSelectionListener(e -> mostrarDetallesPoblacion());
        JScrollPane scrollPane = new JScrollPane(listaPoblaciones);
        panel.add(scrollPane, BorderLayout.WEST);

        // Área de detalles
        detallesArea = new JTextArea(10, 30);
        detallesArea.setEditable(false);
        JScrollPane scrollDetalles = new JScrollPane(detallesArea);
        panel.add(scrollDetalles, BorderLayout.CENTER);

        // Panel de control
        JPanel controlPanel = new JPanel();
        JButton btnAgregar = new JButton("Agregar Población");
        btnAgregar.addActionListener(e -> agregarPoblacion());
        JButton btnEliminar = new JButton("Eliminar Población");
        btnEliminar.addActionListener(e -> eliminarPoblacion());
        JButton btnGuardar = new JButton("Guardar Experimento");
        btnGuardar.addActionListener(e -> guardarExperimento());
        JButton btnCargar = new JButton("Cargar Experimento");
        btnCargar.addActionListener(e -> cargarExperimento());
        controlPanel.add(btnAgregar);
        controlPanel.add(btnEliminar);
        controlPanel.add(btnGuardar);
        controlPanel.add(btnCargar);
        panel.add(controlPanel, BorderLayout.SOUTH);

        this.add(panel);
    }

    private void configurarVentana() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null); // Centrar la ventana
        this.setVisible(true);
    }

    private void agregarPoblacion() {
        // Aquí iría la lógica para mostrar un diálogo y obtener datos para una nueva población
        String nombre = JOptionPane.showInputDialog(this, "Nombre de la población:");
        // Aquí se añadirían más diálogos para obtener el resto de la información
        // Asumiendo que se obtienen todas las variables necesarias:
        LocalDate fechaInicio = LocalDate.now(); // Simplemente como ejemplo
        LocalDate fechaFin = LocalDate.now().plusDays(30);
        int numeroInicial = 1000;
        double temperatura = 37.0;
        String luminosidad = "Alta";
        int comidaInicial = 10;
        int diaIncremento = 15;
        int comidaIncremento = 50;
        int comidaFinal = 5;

        PoblacionBacterias poblacion = new PoblacionBacterias(nombre, fechaInicio, fechaFin, numeroInicial, temperatura,
                luminosidad, comidaInicial, diaIncremento, comidaIncremento, comidaFinal);
        experimentoActual.agregarPoblacion(poblacion);
        modeloLista.addElement(nombre);
    }

    private void eliminarPoblacion() {
        String nombre = listaPoblaciones.getSelectedValue();
        if (nombre != null) {
            experimentoActual.eliminarPoblacion(nombre);
            modeloLista.removeElement(nombre);
            detallesArea.setText("");
        }
    }

    private void guardarExperimento() {
        String nombreArchivo = JOptionPane.showInputDialog(this, "Guardar como (nombre del archivo):");
        if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
            experimentoActual.guardar(nombreArchivo);
        }
    }

    private void cargarExperimento() {
        String nombreArchivo = JOptionPane.showInputDialog(this, "Nombre del archivo para cargar:");
        if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
            experimentoActual = Experimento.cargar(nombreArchivo);
            actualizarLista();
        }
    }

    private void nuevoExperimento() {
        experimentoActual = new Experimento();
        modeloLista.clear();
    }

    private void actualizarListaPoblaciones() {
        modeloLista.clear();
        for (PoblacionBacterias poblacion : experimentoActual.getPoblaciones()) {
            modeloLista.addElement(poblacion.getNombre());
        }
    }

    private void mostrarDetallesPoblacion() {
        String seleccionado = listaPoblaciones.getSelectedValue();
        if (seleccionado != null && !seleccionado.isEmpty()) {
            PoblacionBacterias poblacion = experimentoActual.getPoblacion(seleccionado);
            detallesArea.setText(poblacion.toString()); // Implementar adecuadamente toString en PoblacionBacterias
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExperimentoGUI());
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ExperimentoGUI extends JFrame {
    private Experimento experimentoActual;
    private JList<String> listaPoblaciones;
    private DefaultListModel<String> modeloLista;
    private JTextArea detallesArea;

    public ExperimentoGUI() {
        super("Gestor de Experimentos de Bacterias");
        experimentoActual = new Experimento();
        inicializarComponentes();
        configurarVentana();
        configurarMenu();
    }

    private void configurarMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem menuItemAbrir = new JMenuItem("Abrir");
        menuItemAbrir.addActionListener(e -> cargarExperimento());

        JMenuItem menuItemGuardar = new JMenuItem("Guardar");
        menuItemGuardar.addActionListener(e -> guardarExperimento());

        JMenuItem menuItemNuevo = new JMenuItem("Nuevo Experimento");
        menuItemNuevo.addActionListener(e -> nuevoExperimento());

        menuArchivo.add(menuItemNuevo);
        menuArchivo.add(menuItemAbrir);
        menuArchivo.add(menuItemGuardar);
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);
    }

    private void inicializarComponentes() {
        modeloLista = new DefaultListModel<>();
        listaPoblaciones = new JList<>(modeloLista);
        listaPoblaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPoblaciones.addListSelectionListener(e -> mostrarDetallesPoblacion());

        detallesArea = new JTextArea(10, 30);
        detallesArea.setEditable(false);

        JButton btnAgregar = new JButton("Agregar Población");
        btnAgregar.addActionListener(e -> agregarPoblacion());

        JButton btnEliminar = new JButton("Eliminar Población");
        btnEliminar.addActionListener(e -> eliminarPoblacion());

        JPanel controlPanel = new JPanel();
        controlPanel.add(btnAgregar);
        controlPanel.add(btnEliminar);

        add(new JScrollPane(listaPoblaciones), BorderLayout.WEST);
        add(new JScrollPane(detallesArea), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void agregarPoblacion() {
        PoblacionDialog dialog = new PoblacionDialog(this);
        PoblacionBacterias poblacion = dialog.mostrarDialogo();
        if (poblacion != null) {
            experimentoActual.agregarPoblacion(poblacion);
            modeloLista.addElement(poblacion.getNombre());
        }
    }

    private void eliminarPoblacion() {
        String seleccionado = listaPoblaciones.getSelectedValue();
        if (seleccionado != null && !seleccionado.isEmpty()) {
            experimentoActual.eliminarPoblacion(seleccionado);
            modeloLista.removeElement(seleccionado);
            detallesArea.setText("");
        }
    }

    private void guardarExperimento() {
        if (experimentoActual != null) {
            String nombreArchivo = JOptionPane.showInputDialog(this, "Nombre del archivo para guardar:");
            if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
                experimentoActual.guardar(nombreArchivo);
            }
        }
    }

    private void cargarExperimento() {
        String nombreArchivo = JOptionPane.showInputDialog(this, "Nombre del archivo para cargar:");
        if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
            experimentoActual = Experimento.cargar(nombreArchivo);
            actualizarListaPoblaciones();
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

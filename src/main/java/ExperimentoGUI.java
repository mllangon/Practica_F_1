import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;

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
        Color lightGreen = new Color(169, 244, 169);
        this.getContentPane().setBackground(lightGreen);
        detallesArea.setBackground(lightGreen);
    }

    private void configurarMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("[Archivo ∨]");

        JMenuItem menuItemNuevo = new JMenuItem("Nuevo Experimento");
        JMenuItem menuItemAbrir = new JMenuItem("Abrir Experimento");
        JMenuItem menuItemGuardar = new JMenuItem("Guardar");
        JMenuItem menuItemGuardarComo = new JMenuItem("Guardar como...");
        JMenuItem menuItemCrearPoblacion = new JMenuItem("Crear Población");
        JMenuItem menuItemBorrarPoblacion = new JMenuItem("Borrar Población");
        JMenuItem menuItemOrdenarNombre = new JMenuItem("Ordenar por Nombre");
        JMenuItem menuItemOrdenarFecha = new JMenuItem("Ordenar por Fecha");
        JMenuItem menuItemOrdenarNumero = new JMenuItem("Ordenar por Número de Bacterias");
        JMenuItem menuItemSimular = new JMenuItem("Simular Experimento");

        menuItemCrearPoblacion.addActionListener(e -> crearPoblacion());
        menuItemBorrarPoblacion.addActionListener(e -> borrarPoblacion());
        menuItemNuevo.addActionListener(e -> nuevoExperimento());
        menuItemAbrir.addActionListener(e -> cargarExperimento());
        menuItemGuardar.addActionListener(e -> guardarExperimento());
        menuItemGuardarComo.addActionListener(e -> guardarExperimentoComo());
        menuItemOrdenarNombre.addActionListener(e -> ordenarPoblacionesPorNombre());
        menuItemOrdenarFecha.addActionListener(e -> ordenarPoblacionesPorFecha());
        menuItemOrdenarNumero.addActionListener(e -> ordenarPoblacionesPorNumero());
        menuItemSimular.addActionListener(e -> simularExperimento());

        menuArchivo.add(menuItemNuevo);
        menuArchivo.add(menuItemAbrir);
        menuArchivo.add(menuItemGuardar);
        menuArchivo.add(menuItemGuardarComo);
        menuArchivo.add(menuItemCrearPoblacion);
        menuArchivo.add(menuItemBorrarPoblacion);
        menuArchivo.add(menuItemOrdenarNombre);
        menuArchivo.add(menuItemOrdenarFecha);
        menuArchivo.add(menuItemOrdenarNumero);
        menuArchivo.add(menuItemSimular);

        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);
    }

    private void inicializarComponentes() {
        modeloLista = new DefaultListModel<>();
        listaPoblaciones = new JList<>(modeloLista);
        detallesArea = new JTextArea(10, 30);
        detallesArea.setEditable(false);

        add(new JScrollPane(listaPoblaciones), BorderLayout.WEST);
        add(new JScrollPane(detallesArea), BorderLayout.CENTER);

        pack();

        listaPoblaciones.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    mostrarDetallesPoblacion();
                }
            }
        });
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void crearPoblacion() {
        PoblacionDialog dialogo = new PoblacionDialog(this);
        PoblacionBacterias nuevaPoblacion = dialogo.mostrarDialogo();
        if (nuevaPoblacion != null) {
            experimentoActual.agregarPoblacion(nuevaPoblacion);
            actualizarListaPoblaciones();
        }
    }

    private void borrarPoblacion() {
        String seleccionado = listaPoblaciones.getSelectedValue();
        if (seleccionado != null) {
            experimentoActual.eliminarPoblacion(seleccionado);
            actualizarListaPoblaciones();
        }
    }

    private void nuevoExperimento() {
        experimentoActual = new Experimento();
        modeloLista.clear();
        detallesArea.setText("");
    }

    private void guardarExperimento() {
        if (experimentoActual != null && GestorArchivo.getArchivoActual() != null) {
            GestorArchivo.guardarExperimento(experimentoActual);
        } else {
            guardarExperimentoComo();
        }
    }

    private void guardarExperimentoComo() {
        String nombreArchivo = JOptionPane.showInputDialog(this, "Ingrese el nombre del archivo para guardar:");
        if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
            GestorArchivo.guardarComo(experimentoActual, nombreArchivo);
        }
    }

    private void cargarExperimento() {
        String nombreArchivo = JOptionPane.showInputDialog(this, "Ingrese el nombre del archivo para cargar:");
        if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
            experimentoActual = GestorArchivo.cargarExperimento(nombreArchivo);
            actualizarListaPoblaciones();
        }
    }

    private void actualizarListaPoblaciones() {
        if (experimentoActual != null) {
            modeloLista.clear();
            experimentoActual.getPoblaciones().forEach(p -> modeloLista.addElement(p.getNombre()));
            if (!modeloLista.isEmpty()) {
                listaPoblaciones.setSelectedIndex(0);
                mostrarDetallesPoblacion();
            }
        }
    }

    private void mostrarDetallesPoblacion() {
        if (!listaPoblaciones.isSelectionEmpty()) {
            String seleccionado = listaPoblaciones.getSelectedValue();
            PoblacionBacterias poblacion = experimentoActual.getPoblacion(seleccionado);
            detallesArea.setText(poblacion.toString());
            detallesArea.append("\nDosis de comida para cada día:\n");
            int[] dosisComida = poblacion.getDosisComida();
            for (int i = 0; i < dosisComida.length; i++) {
                detallesArea.append("Día " + (i + 1) + ": " + dosisComida[i] + "\n");
            }
        }
    }

    private void ordenarPoblacionesPorNombre() {
        experimentoActual.ordenarPorNombre();
        actualizarListaPoblaciones();
    }

    private void ordenarPoblacionesPorFecha() {
        experimentoActual.ordenarPorFecha();
        actualizarListaPoblaciones();
    }

    private void ordenarPoblacionesPorNumero() {
        experimentoActual.ordenarPorNumeroBacterias();
        actualizarListaPoblaciones();
    }

    private void simularExperimento() {
        String seleccionado = listaPoblaciones.getSelectedValue();
        if (seleccionado != null) {
            PoblacionBacterias poblacion = experimentoActual.getPoblacion(seleccionado);
            PlatoDeCultivo plato = new PlatoDeCultivo();
            plato.inicializarPlato(poblacion.getNumeroInicialBacterias(), 40000); // Example initial food amount

            for (int i = 0; i < poblacion.getDosisComida().length; i++) {
                plato.simularDia(poblacion.getDosisComida()[i]);
            }

            mostrarResultadoSimulacion(plato);
        }
    }

    private void mostrarResultadoSimulacion(PlatoDeCultivo plato) {
        int[][] bacteriaGrid = plato.getBacteriaGrid();
        JFrame frame = new JFrame("Resultado de la Simulación");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int cellSize = getWidth() / 20;
                for (int i = 0; i < 20; i++) {
                    for (int j = 0; j < 20; j++) {
                        int bacteriaCount = bacteriaGrid[i][j];
                        Color color;
                        if (bacteriaCount >= 20) {
                            color = Color.RED;
                        } else if (bacteriaCount >= 15) {
                            color = new Color(128, 0, 128); // Purple
                        } else if (bacteriaCount >= 10) {
                            color = Color.ORANGE;
                        } else if (bacteriaCount >= 5) {
                            color = Color.YELLOW;
                        } else if (bacteriaCount >= 1) {
                            color = Color.GREEN;
                        } else {
                            color = Color.WHITE;
                        }
                        g.setColor(color);
                        g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    }
                }
            }
        };

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExperimentoGUI());
    }
}
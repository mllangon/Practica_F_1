import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class ExperimentoGUI extends JFrame {
    private Experimento experimentoActual;
    private JList<String> listaPoblaciones;
    private DefaultListModel<String> modeloLista;
    private JTextArea detallesArea;

    // Input fields for population creation and simulation parameters
    private JTextField nombreField;
    private JTextField temperaturaField;
    private JSlider initialFoodSlider;
    private JSlider comidaFinalSlider;
    private JSlider diaIncrementoSlider;
    private JSlider comidaIncrementoSlider;
    private JComboBox<String> foodPatternCombo;
    private JComboBox<String> durationCombo;
    private JComboBox<String> luminosityCombo;

    private static final int SCALE_FACTOR = 1000; // Scale factor for internal representation

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
        JMenuItem menuItemOrdenarNombre = new JMenuItem("Ordenar por Nombre");
        JMenuItem menuItemOrdenarFecha = new JMenuItem("Ordenar por Fecha");
        JMenuItem menuItemOrdenarNumero = new JMenuItem("Ordenar por Número de Bacterias");
        JMenuItem menuItemSimular = new JMenuItem("Simular Experimento");

        menuItemNuevo.addActionListener(e -> nuevoExperimento());
        menuItemAbrir.addActionListener(e -> cargarExperimento());
        menuItemGuardar.addActionListener(e -> guardarExperimento());
        menuItemGuardarComo.addActionListener(e -> guardarExperimentoComo());
        menuItemOrdenarNombre.addActionListener(e -> ordenarPoblacionesPorNombre());
        menuItemOrdenarFecha.addActionListener(e -> ordenarPoblacionesPorFecha());
        menuItemOrdenarNumero.addActionListener(e -> ordenarPoblacionesPorNumero());
        menuItemSimular.addActionListener(e -> iniciarSimulacion());

        menuArchivo.add(menuItemNuevo);
        menuArchivo.add(menuItemAbrir);
        menuArchivo.add(menuItemGuardar);
        menuArchivo.add(menuItemGuardarComo);
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

        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        nombreField = new JTextField(10);
        controlPanel.add(nombreField, gbc);

        gbc.gridx = 2;
        controlPanel.add(new JLabel("Temperatura:"), gbc);
        gbc.gridx = 3;
        temperaturaField = new JTextField(5);
        controlPanel.add(temperaturaField, gbc);

        // Row 1
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(new JLabel("Luminosidad:"), gbc);
        gbc.gridx = 1;
        luminosityCombo = new JComboBox<>(new String[]{"Alta", "Media", "Baja"});
        controlPanel.add(luminosityCombo, gbc);

        gbc.gridx = 2;
        controlPanel.add(new JLabel("Initial Food (µg):"), gbc);
        gbc.gridx = 3;
        initialFoodSlider = new JSlider(0, 300000, 40000);
        initialFoodSlider.setMajorTickSpacing(50000);
        initialFoodSlider.setPaintTicks(true);
        initialFoodSlider.setPaintLabels(true);
        initialFoodSlider.setPreferredSize(new Dimension(300, 50));
        controlPanel.add(initialFoodSlider, gbc);

        // Row 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("Comida Final (µg):"), gbc);
        gbc.gridx = 1;
        comidaFinalSlider = new JSlider(0, 300000, 10000);
        comidaFinalSlider.setMajorTickSpacing(50000);
        comidaFinalSlider.setPaintTicks(true);
        comidaFinalSlider.setPaintLabels(true);
        comidaFinalSlider.setPreferredSize(new Dimension(300, 50));
        controlPanel.add(comidaFinalSlider, gbc);

        gbc.gridx = 2;
        controlPanel.add(new JLabel("Día Incremento:"), gbc);
        gbc.gridx = 3;
        diaIncrementoSlider = new JSlider(1, 30, 1);
        diaIncrementoSlider.setMajorTickSpacing(5);
        diaIncrementoSlider.setPaintTicks(true);
        diaIncrementoSlider.setPaintLabels(true);
        diaIncrementoSlider.setPreferredSize(new Dimension(300, 50));
        controlPanel.add(diaIncrementoSlider, gbc);

        // Row 3
        gbc.gridx = 0;
        gbc.gridy = 3;
        controlPanel.add(new JLabel("Cantidad Incremento (µg):"), gbc);
        gbc.gridx = 1;
        comidaIncrementoSlider = new JSlider(0, 300000, 10000);
        comidaIncrementoSlider.setMajorTickSpacing(50000);
        comidaIncrementoSlider.setPaintTicks(true);
        comidaIncrementoSlider.setPaintLabels(true);
        comidaIncrementoSlider.setPreferredSize(new Dimension(300, 50));
        controlPanel.add(comidaIncrementoSlider, gbc);

        gbc.gridx = 2;
        controlPanel.add(new JLabel("Food Pattern:"), gbc);
        gbc.gridx = 3;
        foodPatternCombo = new JComboBox<>(new String[]{"Constant", "Linear Increase", "Alternating", "Incremental"});
        controlPanel.add(foodPatternCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        controlPanel.add(new JLabel("Duration (days):"), gbc);
        gbc.gridx = 1;
        durationCombo = new JComboBox<>(new String[]{"15", "30", "45"});
        controlPanel.add(durationCombo, gbc);

        JButton btnAgregarPoblacion = new JButton("Agregar Población");
        btnAgregarPoblacion.addActionListener(e -> agregarPoblacion());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        controlPanel.add(btnAgregarPoblacion, gbc);

        add(controlPanel, BorderLayout.NORTH);
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
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void agregarPoblacion() {
        try {
            String nombre = nombreField.getText();
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío.");
            }
            double temperatura = Double.parseDouble(temperaturaField.getText());
            int duracion = Integer.parseInt((String) durationCombo.getSelectedItem());
            String patronComida = (String) foodPatternCombo.getSelectedItem();
            int comidaInicial = initialFoodSlider.getValue() / SCALE_FACTOR;
            int comidaFinal = comidaFinalSlider.getValue() / SCALE_FACTOR;
            int diaIncremento = diaIncrementoSlider.getValue();
            int comidaIncremento = comidaIncrementoSlider.getValue() / SCALE_FACTOR;
            String luminosidad = (String) luminosityCombo.getSelectedItem();

            if (comidaInicial >= 300 || comidaIncremento >= 300 || comidaFinal >= 300) {
                throw new IllegalArgumentException("Las cantidades de comida deben ser valores enteros menores que 300.");
            }

            PoblacionBacterias nuevaPoblacion = new PoblacionBacterias(nombre, LocalDate.now(), LocalDate.now().plusDays(duracion), 1000, temperatura, luminosidad, new int[duracion]);
            nuevaPoblacion.setFoodPattern(patronComida, comidaInicial, comidaIncremento, comidaFinal);
            experimentoActual.agregarPoblacion(nuevaPoblacion);
            actualizarListaPoblaciones();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en el formato de los datos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                detallesArea.append("Día " + (i + 1) + ": " + dosisComida[i] * SCALE_FACTOR + " µg\n"); // Convert back to µg for display
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

    private void iniciarSimulacion() {
        String seleccionado = listaPoblaciones.getSelectedValue();
        if (seleccionado != null) {
            PoblacionBacterias poblacion = experimentoActual.getPoblacion(seleccionado);
            int initialFood = initialFoodSlider.getValue() / SCALE_FACTOR;
            String foodPattern = (String) foodPatternCombo.getSelectedItem();
            int duration = Integer.parseInt((String) durationCombo.getSelectedItem());

            PlatoDeCultivo plato = new PlatoDeCultivo();
            plato.inicializarPlato(poblacion.getNumeroInicialBacterias(), initialFood);
            poblacion.setFoodPattern(foodPattern, initialFood, comidaIncrementoSlider.getValue() / SCALE_FACTOR, comidaFinalSlider.getValue() / SCALE_FACTOR);

            JFrame frame = new JFrame("Resultado de la Simulación");
            frame.setSize(500, 500);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            SimulationPanel simulationPanel = new SimulationPanel(plato, poblacion.getDosisComida());
            frame.add(simulationPanel);
            frame.setVisible(true);

            Timer timer = new Timer(1000, new ActionListener() {
                private int day = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (day < poblacion.getDosisComida().length) {
                        plato.simularDia(poblacion.getDosisComida()[day]);
                        simulationPanel.repaint();
                        day++;
                    } else {
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            timer.start();
        }
    }

    private class SimulationPanel extends JPanel {
        private final PlatoDeCultivo plato;
        private final int[] dosisComida;

        public SimulationPanel(PlatoDeCultivo plato, int[] dosisComida) {
            this.plato = plato;
            this.dosisComida = dosisComida;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int cellSize = getWidth() / 20;
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    int bacteriaCount = plato.getBacteriaGrid()[i][j];
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExperimentoGUI());
    }
}

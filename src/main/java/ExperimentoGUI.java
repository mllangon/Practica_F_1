import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

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

        JMenuItem menuItemNuevo = new JMenuItem("Nuevo Experimento");
        JMenuItem menuItemAbrir = new JMenuItem("Abrir...");
        JMenuItem menuItemGuardar = new JMenuItem("Guardar");
        JMenuItem menuItemGuardarComo = new JMenuItem("Guardar como...");
        JMenuItem menuItemCrearPoblacion = new JMenuItem("Crear Población");
        JMenuItem menuItemBorrarPoblacion = new JMenuItem("Borrar Población");

        ActionListener crearPoblacionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPoblacion();
            }
        };
        ActionListener borrarPoblacionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarPoblacion();
            }
        };

        menuItemCrearPoblacion.addActionListener(crearPoblacionListener);
        menuItemBorrarPoblacion.addActionListener(borrarPoblacionListener);
        menuItemNuevo.addActionListener(e -> nuevoExperimento());
        menuItemAbrir.addActionListener(e -> cargarExperimento());
        menuItemGuardar.addActionListener(e -> guardarExperimento());
        menuItemGuardarComo.addActionListener(e -> guardarExperimentoComo());

        menuArchivo.add(menuItemNuevo);
        menuArchivo.add(menuItemAbrir);
        menuArchivo.add(menuItemGuardar);
        menuArchivo.add(menuItemGuardarComo);
        menuArchivo.add(menuItemCrearPoblacion);
        menuArchivo.add(menuItemBorrarPoblacion);


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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExperimentoGUI());
    }
}

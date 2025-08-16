package src.vistas;

import javax.swing.*;
import javax.swing.table.*; // Se usan: DefaultTableModel y TableRowSorter

import src.controlador.*;
import src.modelo.ResultadoScanner; // Se va a utilizar para agregar filas a la tabla

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ventana_principal extends JFrame implements ActionListener {
    private Controlador controller;

    private JTextField ip_inicio = new JTextField(6);
    private JTextField ip_final = new JTextField(6);
    private JTextField tiempoTimeout = new JTextField("1000", 6); // ms

    private JButton scan = new JButton("Escanear");
    private JButton clean = new JButton("Limpiar");
    private JButton save = new JButton("Guardar");

    private JComboBox orderTable = new JComboBox<>();
    private JComboBox filterTable = new JComboBox<>();

    private JTable tablaEquiposRed = new JTable();
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    private TableRowSorter<DefaultTableModel> sorter;

    public ventana_principal(Controlador controller) {
        setTitle("Escaner de Red");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        this.controller = controller;

        JPanel lamina = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;

        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel textoIntro = new JLabel("Ingrese los siguientes campos de información para poder hacer el proceso");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        lamina.add(textoIntro, gbc);

        // --------------- PRIMERA PARTE DEL PROGRAMA: Inputs Superiores
        // ------------------------

        // JPanel panelInputs = new JPanel(new GridBagLayout());

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        lamina.add(new JLabel("IP de inicio: "), gbc);

        gbc.gridx = 2;

        lamina.add(ip_inicio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        lamina.add(new JLabel("IP final: "), gbc);

        gbc.gridx = 2;
        lamina.add(ip_final, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        lamina.add(new JLabel("Tiempo de espera (ms):"), gbc);
        gbc.gridx = 2;
        lamina.add(tiempoTimeout, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); // 50 px de espacio entre botones
        scan.addActionListener(this);
        clean.addActionListener(this);
        save.addActionListener(this);

        panelBotones.add(scan);
        panelBotones.add(clean);
        panelBotones.add(save);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;

        lamina.add(panelBotones, gbc);

        JLabel presentarTabla = new JLabel("Tabla de información; todos los resultados van a aparecer a continuación");

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridy = 5;

        lamina.add(presentarTabla, gbc);

        modeloTabla = new DefaultTableModel();
        modeloTabla.setColumnIdentifiers(
                new String[] { "Dirección de IP", "Nombre de equipo", "Conectado", "Tiempo (ms)" });

        tablaEquiposRed = new JTable(modeloTabla);

        sorter = new TableRowSorter<>(modeloTabla);
        tablaEquiposRed.setRowSorter(sorter);

        sorter.setComparator(0, (ip1, ip2) -> {
            String[] partes1 = ((String) ip1).split("\\.");
            String[] partes2 = ((String) ip2).split("\\.");
            for (int i = 0; i < 4; i++) {
                int n1 = Integer.parseInt(partes1[i]);
                int n2 = Integer.parseInt(partes2[i]);
                if (n1 != n2)
                    return Integer.compare(n1, n2);
            }
            return 0;
        });

        sorter.setComparator(1, (n1, n2) -> {
            boolean esIp1 = ((String) n1).matches("\\d+\\.\\d+\\.\\d+\\.\\d+");
            boolean esIp2 = ((String) n2).matches("\\d+\\.\\d+\\.\\d+\\.\\d+");
            if (esIp1 && !esIp2)
                return 1; // nombres primero
            if (!esIp1 && esIp2)
                return -1;
            return ((String) n1).compareToIgnoreCase((String) n2);
        });

        sorter.setComparator(3, (t1, t2) -> {
            int n1 = Integer.parseInt(((String) t1).replace("ms", ""));
            int n2 = Integer.parseInt(((String) t2).replace("ms", ""));
            return Integer.compare(n1, n2);
        });

        scrollTabla = new JScrollPane(tablaEquiposRed);
        scrollTabla.setPreferredSize(new Dimension(680, 260));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 6;
        gbc.gridwidth = 3;

        lamina.add(scrollTabla, gbc);

        orderTable.setModel(
                new DefaultComboBoxModel(
                        new String[] { "--- Ordenar por ---", "IP", "Nombre", "Estado (conectado o no)", "Tiempo" }));

        orderTable.setBounds(10, 36, 191, 20);
        orderTable.setSelectedIndex(0);
        orderTable.addActionListener(this);

        gbc.fill = GridBagConstraints.EAST;
        gbc.gridy = 7;
        gbc.gridwidth = 1;

        lamina.add(orderTable, gbc);

        filterTable.setModel(
                new DefaultComboBoxModel(
                        new String[] { "--- Filtrar por ---", "Todos", "Solo conectados", "Solo desconectados" }));

        filterTable.setBounds(10, 36, 191, 20);
        filterTable.setSelectedIndex(0);
        filterTable.addActionListener(this);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.EAST;

        lamina.add(filterTable, gbc);

        add(lamina);
    }

    public void agregarFila(ResultadoScanner equipo) {
        modeloTabla.addRow(new Object[] { equipo.getIpResult(), equipo.getNombreEquipo(), equipo.isConectado(),
                equipo.getTiempoRespuesta() + "ms" });
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    private void guardarEnArchivo(File archivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            // Encabezados
            for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                pw.print(modeloTabla.getColumnName(i));
                if (i < modeloTabla.getColumnCount() - 1)
                    pw.print(",");
            }
            pw.println();

            // Filas
            for (int fila = 0; fila < modeloTabla.getRowCount(); fila++) {
                for (int col = 0; col < modeloTabla.getColumnCount(); col++) {
                    pw.print(modeloTabla.getValueAt(fila, col));
                    if (col < modeloTabla.getColumnCount() - 1)
                        pw.print(",");
                }
                pw.println();
            }

            JOptionPane.showMessageDialog(this, "Resultados guardados en: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar archivo: " + e.getMessage());
        }
    }

    private void guardarResultados() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar resultados");
        fileChooser.setSelectedFile(new File("resultados.csv")); // nombre por defecto

        int opcion = fileChooser.showSaveDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            guardarEnArchivo(archivo);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Object source = e.getSource();

            String ipInicioIngre = ip_inicio.getText();
            String ipFinalIngre = ip_final.getText();
            int tiempo_espera = Integer.parseInt(tiempoTimeout.getText());

            if (source == scan) {
                if (ipInicioIngre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar más información");
                }

                else {
                    controller.startScan(ipInicioIngre, ipFinalIngre, tiempo_espera);
                }
            }

            if (source == clean) {
                controller.clearList();
            }

            if (source == save) {
                guardarResultados();
            }

            String opcionOrdenar = (String) orderTable.getSelectedItem();

            if (opcionOrdenar.equals("--- Ordenar por ---")) {
                sorter.setSortKeys(null);
            }

            else if (opcionOrdenar.equals("IP")) {
                sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
            }

            else if (opcionOrdenar.equals("Nombre")) {
                sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
            }

            else if (opcionOrdenar.equals("Estado (conectado o no)")) {
                sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(2, SortOrder.DESCENDING)));
            }

            else if (opcionOrdenar.equals("Tiempo")) {
                sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(3, SortOrder.ASCENDING)));
            }

            String opcionFiltrar = (String) filterTable.getSelectedItem();
            if (opcionFiltrar.equals("--- Filtrar por ---")) {
                return;
            }

            else if (opcionFiltrar.equals("Todos")) {
                sorter.setRowFilter(null); // Saca los filtros
            }

            else if (opcionFiltrar.equals("Solo conectados")) {
                sorter.setRowFilter(RowFilter.regexFilter("true", 2));
            }

            else if (opcionFiltrar.equals("Solo desconectados")) {
                sorter.setRowFilter(RowFilter.regexFilter("false", 2));
            }
        }

        catch (Exception a) {
            JOptionPane.showMessageDialog(this, "Tuvo un error en su programa.\n" + a);
        }

        // TODO Auto-generated method stub
    }
}

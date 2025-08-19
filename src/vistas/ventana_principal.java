package src.vistas;

import javax.swing.*; // Desarrolla una GUI
import javax.swing.table.*; // Se usan: DefaultTableModel y TableRowSorter

import src.controlador.*; // Se va a llamar al controlador para acceder a sus funciones
import src.modelo.ResultadoScanner; // Se va a utilizar para agregar filas a la tabla

import java.awt.*; // Importa componentes para la vista
import java.awt.event.ActionEvent; // Sirve para recibir una acción en algun elemento de la interfaz, como un botón
import java.awt.event.ActionListener; // Identifica cuando se haya llamado a algún componente de la interfaz, y asi llama a una función Override ActionPerformed

import java.io.File; // Representa archivos y directorios
import java.io.FileWriter; // Procesa de texto a carácteres de un archivo
import java.io.IOException; // Excepciones de Input/Output
import java.io.PrintWriter; // Escribe texto en un archivo o salida de forma más cómoda

public class ventana_principal extends JFrame implements ActionListener {
    private Controlador controller;

    // Campos para ingresar texto (IP inicial, IP final, tiempo de espera y
    // dirección DNS)
    private JTextField ip_inicio = new JTextField(6);
    private JTextField ip_final = new JTextField(6);
    private JTextField tiempoTimeout = new JTextField("1000", 6); // ms
    private JTextField direccionDNS = new JTextField(6);

    // Botones
    private JButton scan = new JButton("Escanear");
    private JButton clean = new JButton("Limpiar");
    private JButton save = new JButton("Guardar");

    // "Caja" que contiene múltiples opciones (Drop-down)
    private JComboBox orderTable = new JComboBox<>();
    private JComboBox filterTable = new JComboBox<>();

    // Componentes para una tabla, como una barra para scrollear entre los
    // resultados, definir el modelo de tabla y la filtración
    private JTable tablaEquiposRed = new JTable();
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    private TableRowSorter<DefaultTableModel> sorter;

    public ventana_principal(Controlador controller) {

        // Componentes importantes de la interfaz (nombre, tamaño, etc)

        setTitle("Escaner de Red");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Para cerrar la ventana, se aprieta la cruz de arriba a la
                                                        // derecha y se cierra de forma automática
        setSize(800, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Identifica al controlador en el constructor de la ventana principal

        this.controller = controller;

        // Estilo de la ventana (se agregan sus componentes, ya sean textos planos,
        // botones y más)

        JPanel lamina = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(); // El GridBagConstraints sirve para ordenar cada componente
                                                           // de la interfaz de forma customizada por el programador
        gbc.fill = GridBagConstraints.CENTER;

        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel textoIntro = new JLabel("Ingrese los siguientes campos de información para poder hacer el proceso");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        lamina.add(textoIntro, gbc);

        ip_inicio.setInputVerifier(new InputVerifier() { // Verificación de dirección en el campo de IP inicial
            @Override
            public boolean verify(JComponent input) {
                String texto = ((JTextField) input).getText().trim();

                if (texto.isEmpty()) {
                    return true; // Este campo puede quedar vacío si el usuario lo permite
                }

                if (texto.matches("^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$")) {
                    return true; // Se identifica si el campo ingresado es una dirección IP y si es así sigue con el programa
                }

                else {
                    JOptionPane.showMessageDialog(null, "IP inválida: " + texto, "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // Si llega a ser dirección DNS o algún texto erróneo, no se puede seguir con la compilación
                }
            }
        });

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        lamina.add(new JLabel("IP inicial:"), gbc);

        gbc.gridx = 2;
        lamina.add(ip_inicio, gbc);

        ip_final.setInputVerifier(new InputVerifier() { // Verificación de dirección en el campo de IP final
            @Override
            public boolean verify(JComponent input) {
                String texto = ((JTextField) input).getText().trim();

                if (texto.isEmpty()) {
                    return true; // Este campo puede quedar vacío si el usuario lo permite
                }

                if (texto.matches("^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$")) {
                    return true; // Se identifica si el campo ingresado es una dirección IP y si es así sigue con el programa
                } else {
                    JOptionPane.showMessageDialog(null,
                            "IP inválida: " + texto,
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // Si llega a ser dirección DNS o algún texto erróneo, no se puede seguir con la compilación
                }
            }
        });

        gbc.gridy = 2;
        gbc.gridx = 0;
        lamina.add(new JLabel("IP final:"), gbc);
        gbc.gridx = 2;
        lamina.add(ip_final, gbc);

        direccionDNS.setInputVerifier(new InputVerifier() { // Verificación de dirección de dominio en el campo de DNS
            @Override
            public boolean verify(JComponent input) {
                String texto = ((JTextField) input).getText().trim();
                if (texto.isEmpty()){
                    return true; // vacío = válido
                }

                if (texto.matches("^[a-zA-Z0-9.-]+$")) { // letras, números, puntos y guiones
                    return true; // Si es DNS, sigue con el programa
                }
                
                else {
                    JOptionPane.showMessageDialog(null,
                            "DNS inválido: " + texto,
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // Si no lo es, se cancela la compilación hasta que se ingrese correctamente
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        lamina.add(new JLabel("Dirección DNS: "), gbc);

        gbc.gridx = 2;

        lamina.add(direccionDNS, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        lamina.add(new JLabel("Tiempo de espera (ms)"), gbc);
        gbc.gridx = 2;
        lamina.add(tiempoTimeout, gbc);

        // Panel de botones

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); // 50 px de espacio entre botones
        scan.addActionListener(this);
        clean.addActionListener(this);
        save.addActionListener(this);

        // En este caso, el ActionListener sirve para que cada uno de los botones, cada
        // vez que ea presionado, devuelve un valor de que se activó

        panelBotones.add(scan);
        panelBotones.add(clean);
        panelBotones.add(save);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;

        lamina.add(panelBotones, gbc);

        JLabel presentarTabla = new JLabel("Tabla de información; todos los resultados van a aparecer a continuación");

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridy = 6;

        lamina.add(presentarTabla, gbc);

        // Comienza la estructura de la JTable

        modeloTabla = new DefaultTableModel();
        modeloTabla.setColumnIdentifiers( // Se identifica cada columna en la parte superior de la tabla
                new String[] { "Dirección de IP", "Nombre de equipo", "Conectado", "Tiempo (ms)" });

        tablaEquiposRed = new JTable(modeloTabla); // Se aplica el modelo a la tabla creada

        sorter = new TableRowSorter<>(modeloTabla);
        tablaEquiposRed.setRowSorter(sorter); // Sirve para filtrar la información, si es que queremos hacerlo

        // Se identifica un comparador en cada columna para identificar cuando sea de forma ascendente o descendente
        
        // ---- Primera columna: Direcciones IP ----

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

        // ---- Segunda columna: Nombres de cada equipo ----

        sorter.setComparator(1, (n1, n2) -> {
            boolean esIp1 = ((String) n1).matches("\\d+\\.\\d+\\.\\d+\\.\\d+");
            boolean esIp2 = ((String) n2).matches("\\d+\\.\\d+\\.\\d+\\.\\d+");
            if (esIp1 && !esIp2)
                return 1; // nombres primero
            if (!esIp1 && esIp2)
                return -1;
            return ((String) n1).compareToIgnoreCase((String) n2);
        });

        // ---- Cuarta columna: Tiempo de espera ----

        sorter.setComparator(3, (t1, t2) -> {
            int n1 = Integer.parseInt(((String) t1).replace("ms", ""));
            int n2 = Integer.parseInt(((String) t2).replace("ms", ""));
            return Integer.compare(n1, n2);
        });

        // Configuración de la barra de scroll en la tabla para visualizar todos los resultados

        scrollTabla = new JScrollPane(tablaEquiposRed);
        scrollTabla.setPreferredSize(new Dimension(680, 260));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 7;
        gbc.gridwidth = 3;

        // Se agrega la tabla con el JScrollPane implementado

        lamina.add(scrollTabla, gbc);

        // Organización de los JComboBox de opciones

        // Primero se hace el que ordena los resultados

        orderTable.setModel(
                new DefaultComboBoxModel(
                        new String[] { "--- Ordenar por ---", "IP", "Nombre", "Estado (conectado o no)", "Tiempo" }));

        orderTable.setBounds(10, 36, 191, 20);
        orderTable.setSelectedIndex(0); // Hace que esté puesto de forma predeterminada "--- Ordenar por ---"
        orderTable.addActionListener(this);

        gbc.fill = GridBagConstraints.EAST;
        gbc.gridy = 8;
        gbc.gridwidth = 1;

        lamina.add(orderTable, gbc);

        // Ahora se hace el que filtra los resultados

        filterTable.setModel(
                new DefaultComboBoxModel(
                        new String[] { "--- Filtrar por ---", "Todos", "Solo conectados", "Solo desconectados" }));

        filterTable.setBounds(10, 36, 191, 20);
        filterTable.setSelectedIndex(0); // Hace que esté puesto de forma predeterminada "--- Filtrar por ---"
        filterTable.addActionListener(this);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.EAST;

        lamina.add(filterTable, gbc);

        add(lamina); // Agrega todos los componentes del JPanel "lamina", el cual es donde se agregaron todas estas modalidades gráficas

    }

    public void agregarFila(ResultadoScanner equipo) { // Función que sólamente llama a ResultadoScanner para obtener la información de cada equipo que esté en la lista. Además, hace esto porque se llama este método en Controlador que obtiene cada equipo de red 
        modeloTabla.addRow(new Object[] { equipo.getIpResult(), equipo.getNombreEquipo(), equipo.isConectado(),
                equipo.getTiempoRespuesta() + "ms" });
    }

    public void limpiarTabla() { // Elimina los registros de la tabla
        modeloTabla.setRowCount(0);
    }

    private void guardarEnArchivo(File archivo) { // Escribe la información de cada fila y columna para guardar en un archivo de texto
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
        JFileChooser fileChooser = new JFileChooser(); // Aparece como una ventana secundaria un conjunto de directorios de tu dispositivo, y nos permite elegir un sitio para guardar esa información en un archivo
        fileChooser.setDialogTitle("Guardar resultados");
        fileChooser.setSelectedFile(new File("resultados.csv")); // Nombre por defecto. También se puede cambiar el tipo de archivo.

        int opcion = fileChooser.showSaveDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            guardarEnArchivo(archivo);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Método para identificar lo que pasa con cada botón/JComboBox cuando se presionan o se usan
        try {
            Object source = e.getSource(); // Obtiene el ActionListener de alguno de los componentes de la ventana principal

            String ipInicioIngre = ip_inicio.getText();
            String ipFinalIngre = ip_final.getText();
            String direDNSingre = direccionDNS.getText();

            int tiempo_espera = Integer.parseInt(tiempoTimeout.getText());

            if (source == scan) { // Si se presiona el botón de escanear, se va a identificar si se ingresó una dirección DNS o un rango de direcciones IP, o si no se ingresó nada

                if (!direDNSingre.isEmpty()) {
                    if (ipInicioIngre.isEmpty() && ipFinalIngre.isEmpty()) {
                        controller.startScanDNS(direDNSingre, tiempo_espera);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Si ingresa DNS, los campos de IP deben estar vacíos",
                                "Error de validación", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    if (ipInicioIngre.isEmpty() && !ipFinalIngre.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Debe ingresar más información", "Mensaje de alerta",
                                JOptionPane.WARNING_MESSAGE);
                    }

                    else {
                        try {
                            controller.startScan(ipInicioIngre, ipFinalIngre, tiempo_espera);
                        }

                        catch (IllegalArgumentException a) {
                            JOptionPane.showMessageDialog(this, "Error de cálculo con las IPs: " + a,
                                    "Error encontrado",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }

            }

            if (source == clean) { // Si se presiono este botón, se van a eliminar todos los resultados de la tabla
                controller.clearList();
            }

            if (source == save) { // Si se presiona este botón, va a aparecer la ventana secundaria JFileChooser (solo si hay registros en la tabla)
                if (modeloTabla.getRowCount() == 0){
                    JOptionPane.showMessageDialog(this, "No tiene ningún registro para guardar en un archivo", "Error encontrado", JOptionPane.ERROR_MESSAGE);
                }

                else{
                    guardarResultados();
                }
            }

            String opcionOrdenar = (String) orderTable.getSelectedItem(); // Se obtiene la opción elegida para ordenar la información

            if (opcionOrdenar.equals("--- Ordenar por ---")) {
                sorter.setSortKeys(null);
            }

            else if (opcionOrdenar.equals("IP")) {
                sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING))); // Se elige la columna y el tipo de ordenamiento en cada opción
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

            String opcionFiltrar = (String) filterTable.getSelectedItem(); // Se obtiene la opción elegida para filtrar la información
            if (opcionFiltrar.equals("--- Filtrar por ---")) {
                return;
            }

            else if (opcionFiltrar.equals("Todos")) {
                sorter.setRowFilter(null); // Saca los filtros
            }

            else if (opcionFiltrar.equals("Solo conectados")) {
                sorter.setRowFilter(RowFilter.regexFilter("true", 2)); // Muestra solo conectados
            }

            else if (opcionFiltrar.equals("Solo desconectados")) {
                sorter.setRowFilter(RowFilter.regexFilter("false", 2)); // Muestra solo desconectados
            }
        }

        catch (Exception a) { // En caso de que el actionPerformed salga mal
            JOptionPane.showMessageDialog(this, "Tuvo un error en su programa.\n" + a, "Error encontrado",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

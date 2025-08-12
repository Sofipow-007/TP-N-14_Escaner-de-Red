package src.vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ventana_principal extends JFrame implements ActionListener{

    private JTextField ip_inicio = new JTextField(10);
    private JTextField ip_final = new JTextField(10);
    private JButton scan = new JButton("Escanear");
    private JButton clean = new JButton("Limpiar");
    private JTable tablaEquiposRed = new JTable();
    private DefaultTableModel modeloTabla;
    
    public ventana_principal(){
        setTitle("Escaner de Red");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel lamina = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel textoIntro = new JLabel("Ingrese los siguientes campos de información para poder hacer el proceso");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        lamina.add(textoIntro, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
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

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); // 50 px de espacio entre botones
        scan.addActionListener(this);
        clean.addActionListener(this);
        panelBotones.add(scan);
        panelBotones.add(clean);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;

        lamina.add(panelBotones, gbc);

        JLabel presentarTabla = new JLabel("Tabla de información; todos los resultados van a aparecer a continuación");
        
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridy = 4;

        lamina.add(presentarTabla, gbc);

        modeloTabla = new DefaultTableModel();
        modeloTabla.setColumnIdentifiers(new String[] { "Dirección de IP", "Nombre de equipo", "Conectado", "Tiempo de respuesta" });
        modeloTabla.addRow(new Object[]{ "127.0.0.1", "localhost", true, "1 ms"});

        tablaEquiposRed = new JTable(modeloTabla);

        JScrollPane scrollTabla = new JScrollPane(tablaEquiposRed);
        scrollTabla.setPreferredSize(new Dimension(500, 80));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 5;
        gbc.gridwidth = 3;

        lamina.add(scrollTabla, gbc);

        add(lamina);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        String ipInicioIngre = ip_inicio.getText();
        String ipFinalIngre = ip_final.getText();

        if (source == scan){
            if (ipInicioIngre.isEmpty() || ipFinalIngre.isEmpty()){
                JOptionPane.showMessageDialog(this, "Debe ingresar más información");
            }
            else{
                ventanaCargar loading = new ventanaCargar(this);
                loading.setVisible(true);
            }
        }

        // if (source == clean){

        // }
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}

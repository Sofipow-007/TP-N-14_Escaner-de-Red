package src.vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.modelo.ComienzoScanner;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ventana_principal extends JFrame implements ActionListener{

    private JTextField ip_inicio = new JTextField(6);
    private JTextField ip_final = new JTextField(6);
    private JTextField tiempoTimeout = new JTextField("1000", 6); // ms

    private JButton scan = new JButton("Escanear");
    private JButton clean = new JButton("Limpiar");
    private JButton save = new JButton("Guardar");

    private JTable tablaEquiposRed = new JTable();
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    
    public ventana_principal(){
        setTitle("Escaner de Red");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 620);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel lamina = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel textoIntro = new JLabel("Ingrese los siguientes campos de información para poder hacer el proceso");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        lamina.add(textoIntro, gbc);

        
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
        modeloTabla.setColumnIdentifiers(new String[] { "Dirección de IP", "Nombre de equipo", "Conectado", "Tiempo (ms)" });
        modeloTabla.addRow(new Object[]{ "127.0.0.1", "localhost", true, "1 ms"});

        tablaEquiposRed = new JTable(modeloTabla);

        scrollTabla = new JScrollPane(tablaEquiposRed);
        scrollTabla.setPreferredSize(new Dimension(680, 260));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 6;
        gbc.gridwidth = 3;

        lamina.add(scrollTabla, gbc);

        add(lamina);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            Object source = e.getSource();
    
            String ipInicioIngre = ip_inicio.getText();
            String ipFinalIngre = ip_final.getText();
            int tiempo_espera = Integer.parseInt(tiempoTimeout.getText());
    
            if (source == scan){
                if (ipInicioIngre.isEmpty() || ipFinalIngre.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Debe ingresar más información");
                }
                else{
                    ventanaCargar loading = new ventanaCargar(this);
                    loading.setVisible(true);
                    
                }
            }
        }
        catch (Exception a){
            System.out.println("Tuvo un error en su programa");
        }

        // if (source == clean){

        // }
        // TODO Auto-generated method stub
    }
}

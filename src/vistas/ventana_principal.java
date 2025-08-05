package src.vistas;

import javax.swing.*;
import java.awt.*;

public class ventana_principal extends JFrame{

    private JTextField ip_inicio = new JTextField(10);
    private JTextField ip_final = new JTextField(10);
    private JButton scan = new JButton("Escanear");
    private JButton clean = new JButton("Limpiar");

    
    public ventana_principal(){
        setTitle("Escaner de Red");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel lamina = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
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

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 3;
        lamina.add(scan, gbc);

        gbc.gridx = 2;
        lamina.add(clean, gbc);

        add(lamina);
        
    }

    public static void main(String[] args) {
        ventana_principal vent_princ = new ventana_principal();
        vent_princ.setVisible(true);
    }
    

}

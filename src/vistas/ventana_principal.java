package src.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ventana_principal extends JFrame implements ActionListener{

    private JTextField ip_inicio = new JTextField(10);
    private JTextField ip_final = new JTextField(10);
    private JButton scan = new JButton("Escanear");
    private JButton clean = new JButton("Limpiar");
    
    public ventana_principal(){
        setTitle("Escaner de Red");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
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

        setPlaceholder(ip_inicio, "Ingrese la IP: ");
        setPlaceholder(ip_final, "Ingrese la IP:");

        add(lamina);
        
    }

    private void setPlaceholder(JTextField field, String placeholder) {
        field.setForeground(Color.GRAY);
        field.setText(placeholder);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == scan){
            ventanaCargar loading = new ventanaCargar(this);
            loading.setVisible(true);
        }

        // if (source == clean){
            
        // }
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}

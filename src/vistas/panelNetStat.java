package src.vistas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import src.controlador.*;

public class panelNetStat extends JFrame implements ActionListener{
    private Controlador controller;

    private JTextArea infoComando;
    private JButton conexionesActivas = new JButton("Ver Conexiones Activas");

    public panelNetStat(Controlador controller) {
        setTitle("Panel de herramientas NetStat");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        this.controller = controller;

        JPanel laminaNet = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel introduccion = new JLabel("Bienvenido a la ventana de comandos NetStat. A continuación, elija alguno de los botones para mostrar estadísticas de red");
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        laminaNet.add(introduccion, gbc);

        infoComando = new JTextArea(20, 50);
        infoComando.setEditable(false);
        JScrollPane scroll = new JScrollPane(infoComando);

        gbc.gridy = 1;
        laminaNet.add(scroll, gbc);

        conexionesActivas.addActionListener(this);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        panelBotones.add(conexionesActivas);
        gbc.gridy = 2;
        laminaNet.add(panelBotones, gbc);

        add(laminaNet);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Object source = e.getSource();
            if (source == conexionesActivas) {
                String resultado = controller.commandNetStat();
                infoComando.setText(resultado);
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Tuvo un error en su programa.\n" + ex, "Error encontrado", JOptionPane.ERROR_MESSAGE);
        }
    }
}

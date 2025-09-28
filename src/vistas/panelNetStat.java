package src.vistas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import src.controlador.*;

public class panelNetStat extends JFrame implements ActionListener { // Ventana que va a mostrar nueva información en la
                                                                     // aplicación
    private ControladorNetStat controller;

    private JTextArea infoComando;

    private JButton conexionesActivas = new JButton("Ver Conexiones Activas");
    private JButton infoRouter = new JButton("Mostrar estadísticas de routing");
    private JButton protocolosStats = new JButton("Mostrar protocolos de red");
    private JButton limpiarVentana = new JButton("Limpiar área de texto");

    public panelNetStat() {
        setTitle("Panel de herramientas NetStat");
        setSize(1020, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        controller = new ControladorNetStat(this);

        JPanel laminaNet = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel introduccion = new JLabel(
                "Bienvenido a la ventana de comandos NetStat. A continuación, elija alguno de los botones para mostrar estadísticas de red");

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        laminaNet.add(introduccion, gbc);

        infoComando = new JTextArea(20, 50);
        infoComando.setEditable(false); // No se puede modificar
        JScrollPane scroll = new JScrollPane(infoComando);

        gbc.gridy = 1;
        laminaNet.add(scroll, gbc);

        conexionesActivas.addActionListener(this);
        infoRouter.addActionListener(this);
        protocolosStats.addActionListener(this);
        limpiarVentana.addActionListener(this);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        panelBotones.add(conexionesActivas);
        panelBotones.add(infoRouter);
        panelBotones.add(protocolosStats);
        panelBotones.add(limpiarVentana);

        gbc.gridy = 2;
        laminaNet.add(panelBotones, gbc);

        add(laminaNet);

    }

    public JButton getBtnConexiones() {
        return conexionesActivas;
    }

    public void setResultado(String texto) {
        infoComando.setText(texto);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == conexionesActivas) {
            controller.mostrarConexiones();
        }

        if (source == infoRouter) {
            controller.mostrarRouting();
        }

        if (source == protocolosStats) {
            controller.mostrarProtocolos();
        }

        if (source == limpiarVentana) {
            infoComando.setText("");
        }

    }
}

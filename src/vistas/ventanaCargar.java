package src.vistas;

import javax.swing.*;
import java.awt.*;

public class ventanaCargar extends JDialog{
    
    private JProgressBar barraProgreso;
    private Runnable onFinish;

    public ventanaCargar(JFrame ventanFrame, Runnable onFinish){
        this.onFinish = onFinish;

        setTitle("Proceso de búsqueda");
        setSize(400, 150);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel laminaLoading = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel textLoading = new JLabel("Calculando los resultados. Buscando si esta dirección existe...");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        laminaLoading.add(textLoading, gbc);

        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setPreferredSize(new Dimension(300, 25));
        barraProgreso.setStringPainted(true);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        laminaLoading.add(barraProgreso, gbc);

        add(laminaLoading);

        inicioCarga();

    }

    private void inicioCarga() {
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(50); // simula tiempo de carga
                    publish(i);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                barraProgreso.setValue(chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
                dispose(); // cerrar ventana al terminar
                if (onFinish != null){
                    onFinish.run();
                }
            }
        };

        worker.execute();
    }
}
package src.vistas;

import javax.swing.*; // Es una librería gráfica de Java que proporciona objetos importantes en la sección visual (objetos, tablas, etc)

import java.awt.*; // Se importa un paquete que sirve para desarrollar una interfaz gráfica de usuario (GUI)

import java.util.function.Consumer; // Interfaz funcional que representa a una operación que recibe un parámetro y no devuelve nada, usado en lambdas o métodos que reciben callbacks

public class ventanaCargar extends JDialog{ // Se crea como JDialog para que no sea una ventana tan importante, como un JOptionPane
    
    private JProgressBar barraProgreso; // Barra de carga que va del 0% hasta el 100%. Identifica el tiempo de espera para calcular

    public ventanaCargar(JFrame ventanFrame){

        // Componentes importantes de la interfaz

        setTitle("Proceso de búsqueda");
        setSize(400, 150);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Estilo de la ventana (se agregan sus componentes, en este caso una barra de progreso)

        JPanel laminaLoading = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(); // El GridBagConstraints sirve para ordenar cada componente de la interfaz de forma customizada por el porgramador
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel textLoading = new JLabel("Calculando los resultados. Buscando si esta dirección existe...");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        laminaLoading.add(textLoading, gbc);

        // Identificación de la JProgressBar

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
        // Este método utiliza un SwingWorker, que es una clase especial de Java que sirve para hacer tareas en segundo plano sin congelar la interfaz gráfica (UI)
        SwingWorker<Void, Integer> worker = new SwingWorker<>() { // Void: Tipo de retorno de la tarea; Integer: Tipo de datos que se publican durante la ejecución
            // Primero se realizan cálculos, escaneos de red, etc.
            
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(50); // Simula tiempo de carga
                    publish(i);              // Publica el progreso
                }
                return null;
            }

            // Ahora se ejecuta el hilo de la interfaz gráfica, actualizando la barra de progreso con el último valor recibido

            @Override
            protected void process(java.util.List<Integer> chunks) {
                barraProgreso.setValue(chunks.get(chunks.size() - 1));
            }

            // Y la última función se ejecuta cuando terminó el doInBackground(), cerrando la ventana de carga

            @Override
            protected void done() {
                dispose(); // cerrar ventana al terminar
            }
        };

        worker.execute();
    }

    
    public Consumer<Integer> getActualizarProgreso() { // Devuelve una función que recibe un Integer. Se usa desde otros hilos para actualizar la barra de progreso de forma segura
        return (porcentaje) -> SwingUtilities.invokeLater(() -> { // Asegura que el cambio de la barra ocurra en el UI
            barraProgreso.setValue(porcentaje);
            if (porcentaje >= 100) {
                dispose(); // Cuando llega al 100%, se cierra automáticamente
            }
        });
    }
}
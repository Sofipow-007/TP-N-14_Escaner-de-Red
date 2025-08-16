package src.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress; //Librería utilizada para poder usar el protocolo IP
import java.net.UnknownHostException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ComienzoScanner {
    private String ipInicio;
    private String ipFinal;
    private int cantidadEquiposRespuesta; // Contador de equipos que responden
    private ExecutorService pool;
    private boolean cancelado = false; 

    private listaEquiposRed listaResultados = new listaEquiposRed(); // Lista de los resultados que se van a mostrar

    public ComienzoScanner(String ipInicio, String ipFinal) {
        this.ipInicio = ipInicio;
        this.ipFinal = ipFinal;
    }

    public int getCantidadEquiposRespuesta() {
        return cantidadEquiposRespuesta;
    }

    // Verifica si es una dirección IP o dominio válido

    public boolean esValida(String direccion_ip) {
        try {
            InetAddress address = InetAddress.getByName(direccion_ip);
            System.out.println(address);
            return address.isReachable(1000);
        }

        catch (Exception e) {
            return false; // IP o DNS inválido/fallido
        }
    }

    public boolean hacerPing(String ip, int timeout) {
        try {
            if (Thread.currentThread().isInterrupted() || cancelado) return false;
            
            boolean responde = InetAddress.getByName(ip).isReachable(timeout);
            if (responde == true) {
                cantidadEquiposRespuesta++;
            }

            return responde;
        }
        
        catch (IOException e) {
            return false;
        }
    }

    // Código basado en el comando nslookup: De esta forma va a devolver el nombre
    // de host con su dirección IP
    // Se puede usar con direcciones URL (como www.google.com) o con direcciones IP
    // (como 127.0.0.1)

    public String[] obtenerNombreIP(String ip) {
        try {
            InetAddress idHost = InetAddress.getByName(ip);

            String nombreHost = idHost.getCanonicalHostName();
            String direccionIP = idHost.getHostAddress();

            if (direccionIP.equals("127.0.0.1")) {
                nombreHost = "localhost";
            }

            return new String[] {
                    nombreHost,
                    direccionIP
            };
        } catch (UnknownHostException e) {
            return new String[] { "Nombre de Host desconocido", "Dirección IP desconocida" };
        }
    }

    public int obtenerTiempoPing(String host, int timeout) {
        try {
            if (Thread.currentThread().isInterrupted() || cancelado) return 0;
            // Comando ping para Windows
            ProcessBuilder pb = new ProcessBuilder("ping", "-n", "1", "-w", String.valueOf(timeout), host);
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.matches(".*\\d+ms.*")) {
                        String t = line.replaceAll(".*?(\\d+)ms.*", "$1");
                        return Integer.parseInt(t.trim());
                    }
                }
            }
            process.waitFor();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return timeout; // fallback si falla
    }

    public void cancelar() {
        cancelado = true;
        if (pool != null && !pool.isShutdown()) {
            pool.shutdownNow(); // Esta función va a interrumpir los hilos que estén en ejecución
        }
    }

    public listaEquiposRed escaneoEntreIPs(int timeout, Consumer<Integer> actualizarProgreso) {
        cantidadEquiposRespuesta = 0;
        listaResultados.limpiarLista();

        cancelado = false; // reset

        // Caso en que haya IP única o dirección DNS

        if (ipFinal.isEmpty() || !ipFinal.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
            if (!cancelado){
                boolean responde = hacerPing(ipInicio, timeout);
                String[] datos = obtenerNombreIP(ipInicio);
                int tiempoRespuesta = obtenerTiempoPing(ipInicio, timeout);
    
                listaResultados.agregarEquipo(new ResultadoScanner(datos[1], datos[0], responde, tiempoRespuesta));
                actualizarProgreso.accept(100); // progreso completo
            }
            return listaResultados;
        }

        String[] inicioPartes = ipInicio.split("\\.");
        String[] finPartes = ipFinal.split("\\.");

        int base1 = Integer.parseInt(inicioPartes[0]);
        int base2 = Integer.parseInt(inicioPartes[1]);
        int base3 = Integer.parseInt(inicioPartes[2]);
        int inicio = Integer.parseInt(inicioPartes[3]);
        int fin = Integer.parseInt(finPartes[3]);

        int totalIPs = fin - inicio + 1;
        AtomicInteger procesadas = new AtomicInteger(0);

        pool = Executors.newFixedThreadPool(20); // 20 hilos simultáneos
        // CountDownLatch latch = new CountDownLatch(fin - inicio + 1); // (corregir)

        for (int i = inicio; i <= fin; i++) {

            String ip = base1 + "." + base2 + "." + base3 + "." + i;

            pool.submit(() -> {

                if (cancelado || Thread.currentThread().isInterrupted()) return;

                boolean responde = hacerPing(ip, timeout);

                if (cancelado || Thread.currentThread().isInterrupted()) return;

                String[] datos = obtenerNombreIP(ip);
                int tiempoRespuesta = obtenerTiempoPing(ip, timeout);

                if (cancelado || Thread.currentThread().isInterrupted()) return;

                synchronized (listaResultados) {
                    listaResultados.agregarEquipo(new ResultadoScanner(
                            datos[1], datos[0], responde, tiempoRespuesta));
                }

                int hechas = procesadas.incrementAndGet();
                int porcentaje = (int) ((hechas / (double) totalIPs) * 100);
                actualizarProgreso.accept(porcentaje);
            });
        }

        pool.shutdown();

        try {
            pool.awaitTermination(2, TimeUnit.MINUTES);
        }

        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return listaResultados;
    }
}
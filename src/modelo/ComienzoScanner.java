package src.modelo;

import java.io.IOException;
import java.net.InetAddress; //Librería utilizada para poder usar el protocolo IP
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComienzoScanner {
    private String ipInicio;
    private String ipFinal;
    private int cantidadEquiposRespuesta; // Contador de equipos que responden

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
            InetAddress.getByName(direccion_ip);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    public boolean hacerPing(String ip, int timeout) {
        try {
            boolean responde = InetAddress.getByName(ip).isReachable(timeout);
            if (responde == true) {
                cantidadEquiposRespuesta++;
            }

            return responde;
        } catch (IOException e) {
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

    public listaEquiposRed escaneoEntreIPs(int timeout) {
        cantidadEquiposRespuesta = 0;
        listaResultados.limpiarLista();

        String[] inicioPartes = ipInicio.split("\\.");
        String[] finPartes = ipFinal.split("\\.");

        int base1 = Integer.parseInt(inicioPartes[0]);
        int base2 = Integer.parseInt(inicioPartes[1]);
        int base3 = Integer.parseInt(inicioPartes[2]);
        int inicio = Integer.parseInt(inicioPartes[3]);
        int fin = Integer.parseInt(finPartes[3]);

        // Corregir mañana 15/08: Tiempo de ejecucion

        ExecutorService pool = Executors.newFixedThreadPool(20); // 20 hilos simultáneos (corregir)
        CountDownLatch latch = new CountDownLatch(fin - inicio + 1); // (corregir)

        for (int i = inicio; i <= fin; i++) {
            String ip = base1 + "." + base2 + "." + base3 + "." + i;
            
            String[] datos = obtenerNombreIP(ip);

            boolean responde = hacerPing(ip, timeout);

            pool.submit(() -> {
                if (responde) {
                    System.out.println("✔ Responde: " + ip);
                    synchronized (listaResultados){
                        listaResultados.agregarEquipo(new ResultadoScanner(datos[1], datos[0], true, timeout));
                    }
                } 
                else{
                    synchronized (listaResultados){
                        listaResultados.agregarEquipo(new ResultadoScanner(datos[1], datos[0], false, timeout));
                    }
                }
                latch.countDown();
            });
        }
        try { // (corregir)
            latch.await(); // Espera a que terminen todos los hilos
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        pool.shutdown();
        return listaResultados;
    }
}
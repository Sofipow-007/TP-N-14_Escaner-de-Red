package src.modelo;

import java.io.IOException;
import java.net.InetAddress; //Librería utilizada para poder usar el protocolo IP
import java.net.UnknownHostException;
import java.util.ArrayList;

import src.vistas.*;

public class ComienzoScanner {
    private String ipInicio;
    private String ipFinal;
    private int cantidadEquiposRespuesta = 0; // Contador de equipos que responden
    
    private listaEquiposRed listaResultados = new listaEquiposRed(); // Lista de los resultados que se van a mostrar
    private ArrayList<String> listaIPs; // Lista de distintas IPs calculadas por una sola IP
    
    public ComienzoScanner(String ipInicio, String ipFinal){
        this.ipInicio = ipInicio;
        this.ipFinal = ipFinal;
        // listaIPs = generarListaIPs(ipInicio, ipFinal);
    }
    
    // public ArrayList<String> generarListaIPs(String ipInicio, String ipFinal) {
    //     ArrayList<String> listaIPs = new ArrayList<>();
        
    //     String[] partesInicio = ipInicio.split("\\.");
    //     String[] partesFinal = ipFinal.split("\\.");

    //     int base1 = Integer.parseInt(partesInicio[0]);
    //     int base2 = Integer.parseInt(partesInicio[1]);
    //     int base3 = Integer.parseInt(partesInicio[2]);
    //     int inicio = Integer.parseInt(partesInicio[3]);
    //     int fin = Integer.parseInt(partesFinal[3]);

    //     for (int i = inicio; i <= fin; i++) {
    //         listaIPs.add(base1 + "." + base2 + "." + base3 + "." + i);
    //     }

    //     return listaIPs;
    // }


    public int getCantidadEquiposRespuesta(){
        return cantidadEquiposRespuesta;
    }

    // Verifica si es una dirección IP o dominio válido

    public boolean esValida(String direccion_ip){
        try{
            InetAddress.getByName(direccion_ip);
            return true;
        }
        catch (UnknownHostException e){
            return false;
        }
    }

    public boolean hacerPing(String ip, int timeout){
        try{
            boolean responde = InetAddress.getByName(ip).isReachable(timeout);
            if (responde == true){
                cantidadEquiposRespuesta++;
            }

            return responde;
        }
        catch (IOException e){
            return false;
        }
    }

    // Código basado en el comando nslookup: De esta forma va a devolver el nombre de host con su dirección IP
    // Se puede usar con direcciones URL (como www.google.com) o con direcciones IP (como 127.0.0.1)

    public String[] obtenerNombreIP(String ip){
        try{
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
        }
        catch (UnknownHostException e){
            return new String[] { "Nombre de Host desconocido", "Dirección IP desconocida" };
        }
    }
}
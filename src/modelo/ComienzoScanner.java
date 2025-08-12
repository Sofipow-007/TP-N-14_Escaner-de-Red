package src.modelo;

import java.io.IOException;
import java.net.InetAddress; //Librería utilizada para poder usar el protocolo IP
import java.net.UnknownHostException;

import src.vistas.*;

public class ComienzoScanner {
    private String ipInicio;
    private String ipFinal;

    public ComienzoScanner(String ipInicio, String ipFinal){
        this.ipInicio = ipInicio;
        this.ipFinal = ipFinal;
    }

    public boolean hacerPing(String ip){
        try{
            return InetAddress.getByName(ip).isReachable(1000);
        }
        catch (IOException e){
            return false;
        }
    }

    // Código basado en el comando nslookup: De esta forma va a devolver el nombre de host con su dirección IP
    // Se puede usar con direcciones URL (como www.google.com) o con direcciones IP (como 127.0.0.1)

    public void obtenerNombreIP(String ip){
        try{
            InetAddress ipEnd = InetAddress.getByName(ip);
            System.out.println("Nombre de host: " + ipEnd.getCanonicalHostName());
            System.out.println("Dirección IP: " + ipEnd.getHostAddress());
        }
        catch (UnknownHostException e){
            e.printStackTrace();
        }
    }
}

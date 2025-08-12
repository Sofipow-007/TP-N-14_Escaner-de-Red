package src.modelo;

import java.io.IOException;
import java.net.InetAddress; //Librería utilizada para poder usar el protocolo IP
import java.net.UnknownHostException;

import src.vistas.*;

public class ComienzoScanner {
    private String ipInicio;
    private String ipFinal;
    private int cantidadEquiposRespuesta = 0; // Contador de equipos que responden

    public ComienzoScanner(String ipInicio, String ipFinal){
        this.ipInicio = ipInicio;
        this.ipFinal = ipFinal;
    }

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

    public boolean hacerPing(String ip){
        try{
            boolean responde = InetAddress.getByName(ip).isReachable(1000);
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

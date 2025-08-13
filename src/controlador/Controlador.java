package src.controlador;

import java.util.ArrayList;

import src.modelo.*;
import src.vistas.*;

public class Controlador {
    private ventana_principal ventanaPrinc = new ventana_principal();
    private ComienzoScanner scanner;
    private listaEquiposRed listaResultados;

    public Controlador(){
        listaResultados = new listaEquiposRed();
    }

    public void startScan(String ipInicio, String ipFinal, int timeout){
        scanner = new ComienzoScanner(ipInicio, ipFinal);

        boolean responde1 = scanner.esValida(ipInicio);
        boolean responde2 = scanner.esValida(ipFinal);

        if (responde1 && responde2){
            boolean resultado = scanner.hacerPing(ipInicio, timeout);

            String[] hostAndIPfinal = scanner.obtenerNombreIP(ipFinal);

            System.out.println("Nombre de host: " + hostAndIPfinal[0]);
            System.out.println("Direccion IP: " + hostAndIPfinal[1]);

            // Agregar los resultados dichos a la lista de los equipos de red, proximamente

        }
    }

    public void clearList(){ // Elimina los resultados 
        listaResultados.limpiarLista();
    }

}

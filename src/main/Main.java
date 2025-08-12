package src.main;

import src.vistas.*;
import src.controlador.*;
import src.modelo.ComienzoScanner;

public class Main {
    public static void main(String[] args) {
        Controlador controller = new Controlador();
        ventana_principal vent_princ = new ventana_principal();
        vent_princ.setVisible(true);

        ComienzoScanner scanner = new ComienzoScanner("127.0.0.1", "www.google.com");
        boolean resultado = scanner.hacerPing("127.0.0.1"); //Prueba de Ping
        System.out.println("Ping a 127.0.0.1 " + resultado);
        scanner.obtenerNombreIP("www.google.com"); //Prueba de nslookup
    }
}

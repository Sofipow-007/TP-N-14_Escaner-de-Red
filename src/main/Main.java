package src.main;

import src.vistas.*;
import src.controlador.*;
import src.modelo.ComienzoScanner;

public class Main {
    public static void main(String[] args) {
        Controlador controller = new Controlador();
        ventana_principal vent_princ = new ventana_principal();
        vent_princ.setVisible(true);

        // ComienzoScanner scanner = new ComienzoScanner("127.0.0.1", "www.google.com");
        
        // boolean valido = scanner.esValida("8.8.8.8");
        
        // if (valido == true){
        //     boolean resultado = scanner.hacerPing("8.8.8.8", 3000); // Prueba de Ping
        //     System.out.println("Ping a 8.8.8.8 " + resultado);
        //     String[] listaNslookup = scanner.obtenerNombreIP("8.8.8.8"); // Prueba de nslookup
        //     System.out.println(listaNslookup[0] + " - " + listaNslookup[1]);
        //     System.out.println("Cantidad de equipos que respondieron: " + scanner.getCantidadEquiposRespuesta());
        // }

        // else{
        //     System.out.println("Esta dirección de IP no existe. Vuelva a intentarlo");
        // }
        
    }
}

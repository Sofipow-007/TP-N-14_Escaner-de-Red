package src.main;

import src.vistas.*;
import src.controlador.*;
import src.modelo.ComienzoScanner;

public class Main {
    public static void main(String[] args) {
        Controlador controller = new Controlador();
        ventana_principal vent_princ = new ventana_principal();
        vent_princ.setVisible(true);
   
        // boolean valido = scanner.esValida("8.8.8.8");
        
        // if (valido){
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

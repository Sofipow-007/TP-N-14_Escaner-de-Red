package src.main;

import src.vistas.*;
import src.controlador.*;

public class Main {
    public static void main(String[] args) {
        Controlador controller = new Controlador();
        ventana_principal vent_princ = new ventana_principal();
        vent_princ.setVisible(true);
    }
}

package src.vistas;

import javax.swing.JFrame;
import java.awt.*;

public class ventana_principal extends JFrame{
    
    public ventana_principal(){
        setTitle("Escaner de Red");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }


    public static void main(String[] args) {
        ventana_principal vent_princ = new ventana_principal();
        vent_princ.setVisible(true);
    }

}

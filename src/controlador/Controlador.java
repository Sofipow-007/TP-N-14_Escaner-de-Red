package src.controlador;

import javax.swing.JOptionPane;

import src.modelo.*;
import src.vistas.*;

public class Controlador {
    private ventana_principal ventanaPrinc;
    private ComienzoScanner scanner;

    private listaEquiposRed listaResultados;

    public Controlador(){
        listaResultados = new listaEquiposRed(); // Se crea una lista de resultados para poder guardar la cantidad de equipos que responden a la IP
        ventanaPrinc = new ventana_principal(this);
        ventanaPrinc.setVisible(true); // Al llamar al constructor del controlador, se crea la vista principal y se hace visible
    }

    public void startScan(String ipInicio, String ipFinal, int timeout){
        scanner = new ComienzoScanner(ipInicio, ipFinal);

        boolean responde1 = scanner.esValida(ipInicio);
        boolean responde2 = scanner.esValida(ipFinal);

        if (responde1 && responde2){
            listaResultados = scanner.escaneoEntreIPs(timeout);

            mostrarEquiposEnVista();

            JOptionPane.showMessageDialog(ventanaPrinc, "Se encontró correctamente la dirección IP, con un cálculo de " + scanner.getCantidadEquiposRespuesta() + " equipos de red encontrados");

        }

        else{
            JOptionPane.showMessageDialog(ventanaPrinc, "Error de compilación. Ingrese una mejor IP");
            return;
        }
    }

    private void mostrarEquiposEnVista() {
        ventanaPrinc.limpiarTabla();

        for (ResultadoScanner equipo : listaResultados.getListaEquipos()){
            ventanaPrinc.agregarFila(equipo);
        }
    }


    public void clearList(){ // Elimina los resultados 
        listaResultados.limpiarLista();
        ventanaPrinc.limpiarTabla();
    }

}

package src.controlador;

import javax.swing.JOptionPane;

import src.modelo.*;
import src.vistas.*;

public class Controlador {
    private ventana_principal ventanaPrinc;
    private ComienzoScanner scanner;

    private listaEquiposRed listaResultados;
    private ResultadoScanner equipoRedResult;

    public Controlador(){
        listaResultados = new listaEquiposRed();
        ventanaPrinc = new ventana_principal(this);
        ventanaPrinc.setVisible(true);
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

            equipoRedResult = new ResultadoScanner(hostAndIPfinal[1], hostAndIPfinal[0], resultado, timeout);

            listaResultados.agregarEquipo(equipoRedResult);

            System.out.println(listaResultados.getListaEquipos());

            mostrarEquiposEnVista();

        }

        // else{
        //     JOptionPane.showMessageDialog("Esta dirección de IP no existe. Vuelva a intentarlo");
        // }
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

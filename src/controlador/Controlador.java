package src.controlador;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import src.modelo.*;
import src.vistas.*;

public class Controlador {
    private ventana_principal ventanaPrinc;
    private ComienzoScanner scanner;

    private listaEquiposRed listaResultados;

    public Controlador() {
        listaResultados = new listaEquiposRed(); // Se crea una lista de resultados para poder guardar la cantidad de
                                                 // equipos que responden a la IP
        ventanaPrinc = new ventana_principal(this);
        ventanaPrinc.setVisible(true); // Al llamar al constructor del controlador, se crea la vista principal y se
                                       // hace visible
    }

    public void startScan(String ipInicio, String ipFinal, int timeout) {
        scanner = new ComienzoScanner(ipInicio, ipFinal);

        boolean responde1 = scanner.esValida(ipInicio);
        boolean responde2 = scanner.esValida(ipFinal);

        if (responde1 && responde2) {
            try{
                ventanaCargar ventana = new ventanaCargar(ventanaPrinc);
                ventana.setModal(false);
                ventana.setVisible(true);
    
                // Ejecutar en hilo aparte para no bloquear la UI
                new Thread(() -> {
                    listaResultados = scanner.escaneoEntreIPs(timeout, ventana.getActualizarProgreso());
    
                    SwingUtilities.invokeLater(() -> {
                        mostrarEquiposEnVista();
                        JOptionPane.showMessageDialog(ventanaPrinc,
                                "Escaneo completado. " + scanner.getCantidadEquiposRespuesta() + " equipo(s) encontrado(s).");
                    });
                }).start();

            }

            catch (Exception e){
                JOptionPane.showMessageDialog(ventanaPrinc, "Ocurrió un error en su cálculo.");
            }

        }
        
        else {
            JOptionPane.showMessageDialog(ventanaPrinc, "Error. IP inválida identificada, intente de nuevo.");
        }
    }

    private void mostrarEquiposEnVista() {
        ventanaPrinc.limpiarTabla();

        for (ResultadoScanner equipo : listaResultados.getListaEquipos()) {
            ventanaPrinc.agregarFila(equipo);
        }
    }

    public void clearList() { // Elimina los resultados
        listaResultados.limpiarLista();
        ventanaPrinc.limpiarTabla();
    }

}

package src.controlador;

import javax.swing.JOptionPane;

import src.modelo.ComandosNetStat;
import src.vistas.panelNetStat;

public class ControladorNetStat { // Esta clase nos va a permitir hacer la conexión entre la vista "panelNetStat" y la clase lógica "ComandosNetStat"

    private panelNetStat vista;
    private ComandosNetStat modelo;

    public ControladorNetStat(panelNetStat vista) {
        this.vista = vista;       // ahora el controlador conoce la vista que va a mostrar la información de los comandos NetStat
        this.modelo = new ComandosNetStat();
    }

    // Método que la vista llama cuando se aprieta el botón
    public void mostrarConexiones() {
        String resultado = modelo.mostrarConexionesActivas();
        vista.setResultado(resultado);
        JOptionPane.showMessageDialog(vista, "Se encontraron las conexiones correctamente", "Búsqueda finalizada", JOptionPane.PLAIN_MESSAGE);
    }

    public void mostrarRouting() {
        String resultado = modelo.mostrarTablaRouting();
        vista.setResultado(resultado);
        JOptionPane.showMessageDialog(vista, "Tabla de routing completa", "Búsqueda finalizada", JOptionPane.PLAIN_MESSAGE);
    }

    public void mostrarProtocolos() {
        String resultado = modelo.muestraEstadisticasProtocolos();
        vista.setResultado(resultado);
        JOptionPane.showMessageDialog(vista, "Estadísticas de protocolos calculadas", "Búsqueda finalizada", JOptionPane.PLAIN_MESSAGE);
    }

    public String commandNetStat() {
        return modelo.mostrarConexionesActivas();
    }
}

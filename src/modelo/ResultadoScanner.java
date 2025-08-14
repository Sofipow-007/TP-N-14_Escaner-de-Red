package src.modelo;

public class ResultadoScanner { // En esta clase se van a guardar los resultados de nuestro escáner de red, devolviendonos la ip encontrada (la que se ingresa como ip final) con su nombre de equipo y si está conectado.
    // Además, se va a mostrar el tiempo de respuesta para que esta solicitud se cumpla

    private String ipResult; // Direccion IP
    private String nombreEquipo; // Nombre de host del equipo
    private boolean conectado; // Si está conectado o no
    private int tiempoRespuesta; // Tiempo de respuesta (1 ms)

    // Elaboración del constructor de ResultadoScanner

    public ResultadoScanner(String ipResult, String nombreEquipo, boolean conectado, int tiempoRespuesta){
        this.ipResult = ipResult; 
        this.nombreEquipo = nombreEquipo; 
        this.conectado = conectado; 
        this.tiempoRespuesta = tiempoRespuesta; 
    }

    // Elaboración de los getters y setters de la clase ResultadoScanner

    public String getIpResult(){
        return ipResult;
    }

    public void setIpResult(String ipResult){
        this.ipResult = ipResult;
    }

    public String getNombreEquipo(){
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo){
        this.nombreEquipo = nombreEquipo;
    }

    public boolean isConectado(){
        return conectado;
    }

    public int getTiempoRespuesta(){
        return tiempoRespuesta;
    }

    public void setTiempoRespuesta(int tiempoRespuesta){
        this.tiempoRespuesta = tiempoRespuesta;
    }

    @Override
    public String toString() {
        return "IP: " + this.ipResult + ", Host: " + this.nombreEquipo + ", Responde: " + this.conectado + ", Timeout: " + this.tiempoRespuesta;
    }
}

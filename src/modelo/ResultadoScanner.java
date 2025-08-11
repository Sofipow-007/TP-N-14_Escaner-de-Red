package src.modelo;

public class ResultadoScanner { // En esta clase se van a guardar los resultados de nuestro estáner de red, devolviendonos la ip encontrada (la que se ingresa como ip final) con su nombre de equipo y si está conectado
    private String ipResult;
    private String nombreEquipo;
    private boolean conectado;
    private int tiempoRespuesta;

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
}

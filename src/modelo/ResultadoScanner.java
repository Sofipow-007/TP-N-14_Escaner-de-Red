package src.modelo;

public class ResultadoScanner { // En esta clase se van a guardar los resultados de nuestro escáner de red, devolviendonos el equipo con la ip encontrada, su nombre de equipo y si está conectado.
    // Además, se va a mostrar el tiempo de respuesta para que esta solicitud se cumpla

    private String ipResult; // Direccion IP
    private String nombreEquipo; // Nombre de host del equipo
    private boolean conectado; // Si está conectado o no (true o false)
    private int tiempoRespuesta; // Tiempo de respuesta (por ejemplo, 1ms)

    // Elaboración del constructor de ResultadoScanner

    public ResultadoScanner(String ipResult, String nombreEquipo, boolean conectado, int tiempoRespuesta){
        this.ipResult = ipResult; 
        this.nombreEquipo = nombreEquipo; 
        this.conectado = conectado; 
        this.tiempoRespuesta = tiempoRespuesta; 
    }

    // Elaboración de los getters y setters de la clase ResultadoScanner

    public String getIpResult(){ // Devuelve la IP del equipo
        return ipResult;
    }

    public void setIpResult(String ipResult){ // Modifica la IP del equipo
        this.ipResult = ipResult;
    }

    public String getNombreEquipo(){ // Devuelve el nombre del equipo
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo){ // Modifica el nombre del equipo
        this.nombreEquipo = nombreEquipo;
    }

    public boolean isConectado(){ // Verifica el estado del booleano
        return conectado;
    }

    public int getTiempoRespuesta(){ // Devuelve el tiempo de respuesta
        return tiempoRespuesta;
    }

    public void setTiempoRespuesta(int tiempoRespuesta){ // Modifica el tiempo de respuesta
        this.tiempoRespuesta = tiempoRespuesta;
    }

    @Override
    public String toString() {
        return "IP: " + this.ipResult + ", Host: " + this.nombreEquipo + ", Responde: " + this.conectado + ", Timeout: " + this.tiempoRespuesta;
    }
}

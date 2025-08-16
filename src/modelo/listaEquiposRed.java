package src.modelo;

import java.util.ArrayList; // Se importa la librería java.util mencionando a los métodos ArrayList
// Esto nos va a servir para que se guarden en una lista interna cada equipo que se calcule en el escáner

public class listaEquiposRed { // Esta clase va a servir para poder mantener un control de todos los equipos que se guarden en una lista
    private ArrayList<ResultadoScanner> listaEquipos;

    public listaEquiposRed(){
        listaEquipos = new ArrayList<>(); // Se declara la ArrayList listaEquipos
    }
    
    public void agregarEquipo(ResultadoScanner equipoRed){ // Función que va a servir para  agregar equipos a la lista
        listaEquipos.add(equipoRed);
    }

    public ArrayList<ResultadoScanner> getListaEquipos(){ // Devuelve los componentes de la lista
        return listaEquipos;
    }

    public void limpiarLista(){ // Limpia todos los resultados de la lista para agregar nuevos equipos de red dentro de ella
        listaEquipos.clear();
    }
}

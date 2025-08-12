package src.modelo;

import java.util.ArrayList; // Lista que va a servis para guardar los equipos de red si el usuario lo solicita

public class listaEquiposRed { // Esta clase va a servir para poder mantener un control de todos los equipos que se guarden en una lista
    private ArrayList<ResultadoScanner> listaEquipos;

    public listaEquiposRed(){
        listaEquipos = new ArrayList<>();
    }
    
    public void agregarEquipo(ResultadoScanner equipoRed){
        listaEquipos.add(equipoRed);
    }

    public void limpiarLista(){
        listaEquipos.clear();
    }
}

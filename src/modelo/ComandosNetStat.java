package src.modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ComandosNetStat { // Esta clase del paquete "modelo" nos va a serviv para almacenar las funciones netstat (nueva actualización de la aplicación)
    
    // 1) Conexiones activas
    public String mostrarConexionesActivas() {
        try {
            ProcessBuilder pb = new ProcessBuilder("netstat", "-ano"); // Muestra conexiones con PID
            Process process = pb.start(); // Sector del código que va a emplear el comando específico

            StringBuilder resultado = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                resultado.append("Conexiones activas:\n");

                while ((line = reader.readLine()) != null) {
                    resultado.append(line).append("\n");
                }
            }

            process.waitFor();
            return resultado.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return("Error al ejecutar netstat -ano");
        }
    }

    // 2) Información del routing
    public String mostrarTablaRouting() {
        try {
            ProcessBuilder pb = new ProcessBuilder("netstat", "-r"); // Sector del código que va a emplear el comando específico
            Process proceso = pb.start();

            StringBuilder resultadoComando = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                resultadoComando.append("Lista de routers:\n");
                while ((linea = reader.readLine()) != null) {
                    resultadoComando.append(linea).append("\n");
                    System.out.println(linea);
                }
            }
            proceso.waitFor();
            return resultadoComando.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return("Error al ejecutar el comando 'netstat -r'");
        }
    }

    // 3) Recuperar estadísticas de protocolos de red importantes (TCP, IP, UDP)
    public String muestraEstadisticasProtocolos() {
        try {
            ProcessBuilder pb = new ProcessBuilder("netstat", "-s"); // Sector del código que va a emplear el comando específico
            Process proceso = pb.start();

            StringBuilder resultadoComando = new StringBuilder();
            try (BufferedReader read = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String line;
                resultadoComando.append("Estadísticas de protocolos de red importantes\n");
                System.out.println("Estadísticas de protocolos:");
                while ((line = read.readLine()) != null) {
                    resultadoComando.append(line).append("\n");
                    System.out.println(line);
                }
            }
            proceso.waitFor();
            return resultadoComando.toString();
        }
        
        catch (Exception e) {
            e.printStackTrace();
            return("Error al ejecutar el comando 'netstat -s'");
        }
    }
}

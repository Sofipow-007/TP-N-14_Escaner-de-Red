package src.modelo;

import java.io.*; // Paquete importado que sirve para trabajar con la entrada y salida de datos (Input/Output)

// Las clases que vamos a importar para utilizarlos en esta clase son:

// - BufferedReader: Se trata de una lectura más rápida gracias al uso de buffers
// - IOException: Identifica las excepciones que ocurren por IO.
// - InputStreamReader: Se usa como un puente entre el flujo de bytes (InputStream) y flujos de carácteres (Reader)

import java.net.*; // Paquete que sirve para implementar otras aplicaciones en el código, como APIs o IPs. Las clases importadas por este paquete son:

// - InetAddress: Utilizado para poder usar el protocolo IP
// - UnknownHostException: Indica cuando una dirección IP de un host no fue encontrada

import java.util.concurrent.*; // Este paquete nos va a servir para el manejo de hilos, la sincronización y la ejecución de tareas.

import java.util.concurrent.atomic.AtomicInteger; // En este código nos va a permitir implementar el manejo de números enteros de forma segura en entornos concurrentes (varios hilos que modifican una misma variable)

import java.util.function.Consumer; // Se trata de una interfaz funcional que representa a una operación que recibe un parámetro y no devuelve nada, usado en lambdas o métodos que reciben callback
 
public class ComienzoScanner {
    private String ipInicio; // IP inicial
    private String ipFinal; // IP final
    private int cantidadEquiposRespuesta; // Contador de equipos que responden
    private ExecutorService pool; // Manejo de hilos

    private listaEquiposRed listaResultados = new listaEquiposRed(); // Lista de los resultados que se van a mostrar

    public ComienzoScanner(String ipInicio, String ipFinal) { // Este constructor va a recibir los valores de IP inicial e IP final que van a servir en distintas funciones dentro de esta clase.
        this.ipInicio = ipInicio;
        this.ipFinal = ipFinal;
    }

    public int getCantidadEquiposRespuesta() { // Devuelve la cantidad de equipos que respondieron (que estén conectados)
        return cantidadEquiposRespuesta;
    }


    // ! ------------------- Funciones utilizadas ----------------------- !

    // Verifica si es una dirección IP o dominio válido.

    public boolean esValida(String direccion_ip) {
        try {
            InetAddress address = InetAddress.getByName(direccion_ip);
            System.out.println(address);
            return address.isReachable(1000);
        }

        catch (Exception e) {
            return false; // IP o DNS inválido/fallido
        }
    }

    // Confirma si los equipos de red vinculados estan conectados o no. Si lo están, se agregan al contador de equipos que respondieron.

    public boolean hacerPing(String ip, int timeout) {
        try {
            boolean responde = InetAddress.getByName(ip).isReachable(timeout);
            if (responde == true) {
                cantidadEquiposRespuesta++;
            }

            return responde;
        }

        catch (IOException e) {
            System.err.println("Error al hacer ping a: " + ip);
            return false;
        }
    }

    // Código basado en el comando nslookup: De esta forma va a devolver el nombre
    // de host con su dirección IP, los cuales devuelve en una lista tipo String[]

    // Se puede usar con direcciones URL (como www.google.com) o con direcciones IP
    // (como 127.0.0.1)

    public String[] obtenerNombreIP(String ip) {
        try {
            InetAddress idHost = InetAddress.getByName(ip);

            String nombreHost = idHost.getCanonicalHostName();
            String direccionIP = idHost.getHostAddress();

            if (direccionIP.equals("127.0.0.1")) {
                nombreHost = "localhost";
            }

            return new String[] {
                    nombreHost,
                    direccionIP
            };
        }

        catch (UnknownHostException e) {
            System.err.println("Nombre no encontrado");
            return new String[] { "Nombre de Host desconocido", "Dirección IP desconocida" };
        }
    }

    // Se devuelve el tiempo de respuesta en tiempo real de la solicitud del ping, tanto para direcciones IP como DNS.

    public int obtenerTiempoPing(String host, int timeout) {
        try {
            // Comando ping para Windows
            ProcessBuilder pb = new ProcessBuilder("ping", "-n", "1", "-w", String.valueOf(timeout), host); // Se ejecuta el comando ping de Windows, cosa que se hace en 1 intento, menciona la dirección de host que hace ping
            Process process = pb.start(); // Crea un proceso con el sistema operativo y lo ejecuta

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) { // Se calcula la salida del comando ping
                String line;
                while ((line = reader.readLine()) != null) { // Se recorre línea por línea hasta encontrar un valor que contenga ms
                    if (line.matches(".*\\d+ms.*")) { // Detecta si la línea tiene un número seguido de ms
                        String t = line.replaceAll(".*?(\\d+)ms.*", "$1"); // Extrae solo el número

                        return Integer.parseInt(t.trim()); // Lo convierte en int y luego lo devuelve
                    }
                }
            }
            process.waitFor(); // Espera a que el proceso ping finalice antes de seguir
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        catch (InterruptedException a){
            a.printStackTrace();
        }

        return timeout; // fallback si falla
    }

    // Esta función va a devolver una lista de los equipos de red que respondan o no en un rango de números identificados en dos IPs distintas.
    // También sirve si se utiliza una sola IP o dirección DNS

    public listaEquiposRed escaneoEntreIPs(int timeout, Consumer<Integer> actualizarProgreso) {
        cantidadEquiposRespuesta = 0; // Desde esta función se carga la cantidad total de dispositivos que respondan

        listaResultados.limpiarLista(); // Además, se actualiza la lista para cargar nuevos resultados, llamando a una función específica de la clase listaEquiposRed()

        // Caso en que haya IP única o dirección DNS

        if (ipFinal.isEmpty() || !ipFinal.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {

            boolean responde = hacerPing(ipInicio, timeout);
            String[] datos = obtenerNombreIP(ipInicio);
            int tiempoRespuesta = obtenerTiempoPing(ipInicio, timeout);

            listaResultados.agregarEquipo(new ResultadoScanner(datos[1], datos[0], responde, tiempoRespuesta));
            actualizarProgreso.accept(100); // progreso completo

            return listaResultados; // Devuelve la lista con un solo equipo calculado
        }

        String[] inicioPartes = ipInicio.split("\\.");
        String[] finPartes = ipFinal.split("\\.");

        // En esta sección se dividen las dos IP ingresadas en cuatro partes:

        // base1 viene a ser el primer dígito de la IP inicial hasta el primer punto. Por ejemplo, si tenemos una dirección local (127.0.0.1) el valor sería 127
        // base2 es el segundo dígito de la IP hasta el segundo punto que la separa del resto de dígitos
        // base3 es el tercer dígito de la IP hasta el tercer punto
        // inicio es el cuarto dígito de la IP y con el que se va a calcular el contador del rango, junto con la variable "fin"
        // fin se trata del cuarto dígito de la IP final y es el fin del rango para calcular los equipos e red

        int base1 = Integer.parseInt(inicioPartes[0]);
        int base2 = Integer.parseInt(inicioPartes[1]);
        int base3 = Integer.parseInt(inicioPartes[2]);
        int inicio = Integer.parseInt(inicioPartes[3]);
        int fin = Integer.parseInt(finPartes[3]);

        if (inicio > fin) { // Si inicio es mayor a fin, devuelve una excepción ya que así no se puede calcular ningún rango
            throw new IllegalArgumentException("La IP final no puede ser menor que la inicial");
        }

        else{
            int totalIPs = fin - inicio + 1;
            AtomicInteger procesadas = new AtomicInteger(0);
    
            pool = Executors.newFixedThreadPool(20); // 20 hilos simultáneos
    
            for (int i = inicio; i <= fin; i++) {
    
                String ip = base1 + "." + base2 + "." + base3 + "." + i;
    
                pool.submit(() -> { // Se recibe un Runnable de forma que se identifique en un lambda, ejecutandose en un hilo separado
                    // No se va a interrumpir la interfaz gráfica

                    boolean responde = hacerPing(ip, timeout);
    
                    String[] datos = obtenerNombreIP(ip);
                    int tiempoRespuesta = obtenerTiempoPing(ip, timeout);
    
                    synchronized (listaResultados) { // Se asegura que solo un hilo a la vez modifique la lista
                        listaResultados.agregarEquipo(new ResultadoScanner(
                                datos[1], datos[0], responde, tiempoRespuesta)); // Se crea un objeto ResultadoScanner. Se guarda en la lista
                    }
    
                    int hechas = procesadas.incrementAndGet(); // Suma un hilo de forma segura para poder utilizar múltiples hilos
                    int porcentaje = (int) ((hechas / (double) totalIPs) * 100); // Se calcula el porcentaje de IPs procesadas
                    actualizarProgreso.accept(porcentaje); // Avisa a la interfaz gráfica de usuario cuánto progreso hay
                });
            }
    
            pool.shutdown(); // No acepta tareas nuevas pero espera a que las actuales se terminen
    
            try {
                pool.awaitTermination(2, TimeUnit.MINUTES); // Le agrega al programa un tiempo de espera para que todas las tareas se puedan terminar
            }
    
            catch (InterruptedException e) { // Captura la excepción si el hilo principal que estaba esperando fue interrumpido
                e.printStackTrace();
            }
    
            return listaResultados;
        }
    }
}
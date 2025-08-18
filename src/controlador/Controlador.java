package src.controlador;

import javax.swing.*; // Desde este paquete Swing se van a importar dos métodos importantes:

// 1- JOptionPane: Se va a utilizar para poder crear cajas de dialogo que sirven como pop ups. En este código se van a usar tanto para indicar que se cumplió el escaneo como para indicar que hay errores
// 2- SwingUtilities: Gracias a esto se van a poder ejecutar métodos de utilidad para usar en Swing, relacionandolo con la interfaz gráfica del usuario, ejecutando las operaciones mediante hilos

import src.modelo.*; // Se llaman a todas las clases lógicas importando el paquete "modelo"
import src.vistas.*; // Se llaman a todas las clases visuales importando el paquete "vistas"

public class Controlador { // La clase Controlador va a servir como una colección de funciones que conecten
                           // tanto la parte lógica como la visual, pero que su recorrido sea este:

    // Modelo -> Controlador -> Vistas

    // De esta forma, es como se implementa el patrón de diseño MVC, haciendo que el
    // paquete "modelo" tenga la parte de los datos, y que el paquete "vistas" se
    // encargue de la interacción con el usuario

    private ventana_principal ventanaPrinc; // Se crea una variable que sea la vista principal para ejecutarla en el
                                            // controlador

    private ComienzoScanner scanner; // La clase ComienzoScanner contiene todas las funciones que vayan a servir en
                                     // la ejecución del programa (comandos ping, nslookup, funciones de rango entre
                                     // IPs y más)

    private listaEquiposRed listaResultados; // Se crea una variable de una lista que va a guardar los equipos de red
                                             // encontrados

    public Controlador() {
        listaResultados = new listaEquiposRed(); // Se crea una lista de resultados para poder guardar la cantidad de
                                                 // equipos que responden a la IP

        ventanaPrinc = new ventana_principal(this);
        ventanaPrinc.setVisible(true); // Al llamar al constructor del controlador, se crea la vista principal y se
                                       // hace visible
    }

    public void startScan(String ipInicio, String ipFinal, int timeout) { // Función que va a llamar al resto de
                                                                          // funciones que esten en la clase
                                                                          // ComienzoScanner
        scanner = new ComienzoScanner(ipInicio, ipFinal);

        boolean responde1 = scanner.esValida(ipInicio);
        boolean responde2 = scanner.esValida(ipFinal);

        // Se verifica que ambas IPs ingresadas sean válidas (que respondan) para seguir
        // con el programa

        if (responde1 && responde2) {
            ventanaCargar ventanaLoad = new ventanaCargar(ventanaPrinc);
            ventanaLoad.setModal(false);
            ventanaLoad.setVisible(true); // Se llama y se hace visible una ventana extra que va a servir para mostrar
                                          // el "proceso de carga" de la ejecución

            // Ejecutar en hilo aparte para no bloquear la UI
            new Thread(() -> {
                try {
                    listaResultados = scanner.escaneoEntreIPs(timeout, ventanaLoad.getActualizarProgreso());

                    // El método SwingUtilities.invokeLater() va a servir para ejecutar subprocesos
                    // múltiples en aplicaciones de Java
                    // En este caso se ejecuta con Swing para tener una referencia de la interfaz
                    // gráfica de aplicaciones

                    SwingUtilities.invokeLater(() -> {
                        mostrarEquiposEnVista();
                        JOptionPane.showMessageDialog(ventanaPrinc,
                                "Escaneo completado. " + scanner.getCantidadEquiposRespuesta()
                                        + " equipo(s) encontrado(s).");
                    });

                    // En este caso se ejecutó la función mostrarEquiposEnVista(), la cual va a
                    // servir para insertar los datos en el JTable de la ventana principal, y luego
                    // aparece un JOptionPane indicando que el escaneo se completó sin errores
                    // De esta forma se comprueba que con un invokeLater() y creando una función
                    // lambda dentro de él (para agregar los procesos simultáneos) se puede hacer la
                    // ejecución múltiple
                }

                catch (IllegalArgumentException ex) {
                    // Si se llega a encontrar un error de argumento en el bloque try anterior, se
                    // va a utilizar otro invokeLater() para terminar con la ventana dde carga y
                    // para ejecutar un pop-up de error encontrado

                    SwingUtilities.invokeLater(() -> {
                        ventanaLoad.dispose();
                        JOptionPane.showMessageDialog(ventanaPrinc,
                                "Error en el rango de IPs ingresados", "Error encontrado", JOptionPane.ERROR_MESSAGE);
                    });
                }
            }).start();

        }

        else {
            // Si alguna de las IP ingresadas no es válida, se ejecuta un pop-up que explica
            // ese error y aclara que se ingresen de nuevo en la interfaz para seguir con el
            // flujo

            JOptionPane.showMessageDialog(ventanaPrinc, "Error. IP inválida identificada, intente de nuevo.",
                    "Error encontrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void startScanDNS(String dns, int timeout){
        scanner = new ComienzoScanner(dns, ""); // se pasa dns como ipInicio y vacío en ipFinal

        if (scanner.esValida(dns)){
            ventanaCargar loading = new ventanaCargar(ventanaPrinc);
            loading.setModal(false);
            loading.setVisible(true);

            new Thread(() -> {
                try{
                    listaResultados = scanner.escaneoEntreIPs(timeout, loading.getActualizarProgreso());

                    SwingUtilities.invokeLater(() -> {
                        mostrarEquiposEnVista();
                        JOptionPane.showMessageDialog(ventanaPrinc,
                            "Escaneo completado. " + scanner.getCantidadEquiposRespuesta()
                                    + " equipo(s) encontrado(s).");
                    });
                }

                catch (IllegalArgumentException ex) {
                    SwingUtilities.invokeLater(() -> {
                        loading.dispose();
                        JOptionPane.showMessageDialog(ventanaPrinc,
                                "Error en el escaneo de la dirección DNS", "Error encontrado", JOptionPane.ERROR_MESSAGE);
                    });
                }
            }).start();
        }

        else{
            JOptionPane.showMessageDialog(ventanaPrinc,
                "Error. DNS inválido o no responde, intente de nuevo.",
                "Error encontrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarEquiposEnVista() {
        // Esta función, como se explicó anteriormente, va a servir para vincular la
        // lista de equipos de red calculada en la función startScan() con una función
        // creada en la vista principal
        // Gracias a esto se va a poder mostrar cada equipo calculado y aceptado en los
        // cálculos de la clase ComienzoScanner, sin que haya ningún error

        ventanaPrinc.limpiarTabla(); // Antes de comenzar se actualiza la tabla para agregar nueva información en
                                     // ella

        for (ResultadoScanner equipo : listaResultados.getListaEquipos()) {
            ventanaPrinc.agregarFila(equipo); // Para cada equipo en la lista (que se basa en la clase ResultadoScanner)
                                              // se van a ir agregando en otra función creada en la vista principal para
                                              // agregar los equipos
        }
    }

    public void clearList() { // Elimina los resultados si se apreta el boton "Limpiar" en la lista principal

        listaResultados.limpiarLista(); // Función para eliminar los resultados en la lista

        ventanaPrinc.limpiarTabla(); // Función para eliminar los resultados en la tabla
    }
}
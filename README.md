# TP-N-14_Escaner-de-Red

## Descripción del proyecto

Este proyecto consiste en el desarrollo de una herramienta de red que pueda escanear direcciones IP en un rango predeterminado. Estos datos los va a mostrar mediante una interfaz gráfica que va a poder identificar los equipos que estarán conectados/activados en la red, obteniendo como información útil el nombre del equipo y su tiempo de respuesta.

## Funcionalidades

Crear una aplicación que pueda realizar un escaneo de red, que pueda cumplir con:

- Poder escanear mediante un rango de IP
- Hacer un ping y obtener cada dirección IP, el nombre del equipo y su tiempo de respuesta
- Mostrar sus resultados en una interfaz gráfica que pueda estar ordenada
- Si el usuario lo desea, puede guardar los resultados

## Uso del programa

1. El usuario tendrá que ingresar un rango de IPs de inicio y final mediante la interfaz (si es necesario va a configurar un tiempo de espera), y luego presionar un botón para que comience con el escaneo

2. Cuando se van detectando los dispositivos, la interfaz irá mostrando los resultados, y si se autoriza, los resultados se guardaran.

## División del programa

El proyecto está dividido en diferentes sectores importantes para que pueda utilizarse correctamente:

#### En la carpeta "src" tenemos:

- *Paquete "modelo"*: Donde se guardan todos los archivos que sirven para la parte lógica del escáner, ya sea para hacer el procedimiento o para calcular los resultados

- *Paquete "vistas"*: En esta sección se encuentran los códigos que van a servir para la parte visual del programa, siendo donde se va a ingresar y mostrar toda la información calculada por el escáner

- *Paquete "controlador"*: Es donde va a estar la clase intermediaria entre el modelo y la vista, para que sea posible la división entre métodos que se van a llamar en el main para poder realizar sus funciones y saber identificar cuando se necesitan

- *Paquete "main"*: Donde se encuentra la clase utilizada para correr el programa final

#### En la carpeta "docs" tenemos:

- *Archivo PDF sobre la documentación del programa*: En este documento se van a tomar en cuenta muchos detalles, ya sea para que va a servir el programa, como se arma el sistema, una forma más detallada de los métodos utilizados, tecnologías elegidas, problemas y posibles soluciones que se podrían hacer en un futuro.

- *Archivo PDF "Manual para el usuario"*: Se trata de un documento donde se va a dar información de cómo poner el práctica el programa viniendo de una visión más dirigida al usuario. Ahí se van a explicar los pasos para poder instalarlo, como utilizarlo, detalles para solucionar problemas y más-

## Tecnologías a utilizar

- Llamadas al sistema con 'nslookup' y 'ping'
- Java, utilizando la programación orientada a objetos (separando la lógica y la vista)
- Git para controlar versiones

## Orden de entrega

Esta sección se trata sobre todo lo realizado en cada entrega parcial (luego incluída la entrega final), ya sea con cosas en proceso o terminadas

- Martes 5 de Agosto: Se creó el repositorio en GitHub, con una parte del README hecho y la creación de la ventana principal de este proyecto.

- Martes 12 de Agosto: Entrega con la carpeta scr actualizada, incluyendo una división de paquetes para diferenciar la parte lógica, la visual y la que controla estas. En ese momento la interfaz gráfica estaba en proceso con muchos adelantos pero algunas cosas restantes (como agregar resultados a la tabla de ventana principal junto con el ordenamiento y más).
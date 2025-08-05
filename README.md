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

## Tecnologías a utilizar

- Llamadas al sistema con 'nslookup' y 'ping'
- Java, utilizando la programación orientada a objetos (separando la lógica y la vista)
- Git para controlar versiones
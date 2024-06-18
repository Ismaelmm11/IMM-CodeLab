# Tienda POO

Este proyecto aún sigue en desarrollo, pues falta ordenar el código. Consiste en una tienda virtual desarrollada en Java utilizando Eclipse, donde podemos encontrar
un registro, login, login como invitado, 3 tipos de producto y un carrito con el sistema de compra, además de una interfaz gráfica. Ahora se detalla la estructura del proyecto:

## Estructura del Proyecto
- `Carpeta src`: Carpeta que contiene el código fuente del proyecto.
- `Carpeta fotos`: Carpeta que contiene las imágenes necesarias para el proyecto.
- `Archivo clientes.txt`: Archivo que contiene los datos de los clientes en formato JSON.
- `Archivo gson-2.8.1.jar`: Librería necesaria para el manejo de JSON.
- `Archivo .project`: Contiene la configuración del proyecto de Eclipse.
- `Archivo .classpath`: Contiene la configuración de las rutas de clase de Eclipse.


A continuación, se detallan las instrucciones necesarias para asegurar el correcto funcionamiento del proyecto.


## Instrucciones de Configuración

### 1. Importar la Librería GSON

Para poder registrar e iniciar sesión correctamente, es necesario importar la librería `gson-2.8.1.jar` como un External JAR en tu proyecto. Sigue estos pasos:

1. Haz clic derecho en tu proyecto en Eclipse.
2. Selecciona `Build Path` > `Add External Archives...`.
3. Navega hasta la ubicación de `gson-2.8.1.jar` y selecciónalo.
4. Haz clic en `Open`.

### 2. Configurar las Rutas en `CRegistrado.java`

1. Abre la clase `CRegistrado.java` que se encuentra en el paquete `es.poo.cliente`.
2. Cambia la ruta de la variable `FOTO_DEF` (corresponde a la foto de perfil por defecto) a la ruta donde se encuentra la imagen en tu sistema.
3. Cambia la ruta de la variable `RUTA_JSON` a la ruta donde se encuentra el archivo `clientes.txt`.

```java
// Ejemplo:
private static final String FOTO_DEF = "ruta....\\perfil_defecto.jpg";
private static final String RUTA_JSON = "ruta...\\clientes.txt";
````
### 3.Configurar las rutas de las fotos en el resto de clases
1. Abrir las clases `PAlimentacion.java`, `PDiscos.java`, `Plibros.java` en el paquete `es.poo.producto` y cambiar las rutas de las fotos.
2. Abrir las clases `MenuBarFactory.java`, `VentanaMain.java`, `VentanaTienda.java` en el paquete `Ventanas` y cambiar las rutas de las fotos.

Todas las rutas de fotos y archivos se encuentran al principio de las clases mencionadas en variables constantes de tipo String.

## Ejecución del Proyecto
1. Asegúrate de que todas las rutas están correctamente configuradas como se indica arriba.
2. Importa el proyecto en Eclipse si aún no lo has hecho.
3. Importa la librería gson-2.8.1.jar como se indica en la sección de configuración.
4. Ejecuta la clase main del proyecto desde Eclipse.
5. Disfruta de la Tienda Virtual POO.

Si tienes alguna duda o encuentras algún problema, no dudes en contactar conmigo.
¡Gracias por usar la tienda POO!

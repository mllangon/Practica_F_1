# Practica_F_1
Mario Llansó-->https://github.com/mllangon/Practica_F_1.git

Resumen General
La aplicación ExperimentoGUI es una interfaz gráfica de usuario (GUI) en Java Swing para gestionar experimentos de poblaciones de bacterias. La aplicación permite crear, visualizar, simular y guardar datos de diferentes poblaciones de bacterias. También incluye una pantalla de inicio de sesión para la autenticación de usuarios.

Funcionalidades Principales
Inicio de Sesión:

Al iniciar la aplicación, se muestra un cuadro de diálogo de inicio de sesión. El usuario debe ingresar el nombre de usuario y la contraseña ("usuario" y "password") para acceder a la aplicación principal.
El cuadro de diálogo de inicio de sesión tiene una imagen de fondo (fondo.png).
Gestión de Poblaciones:

La aplicación permite al usuario agregar nuevas poblaciones de bacterias mediante la interfaz gráfica.
Los datos ingresados incluyen nombre, temperatura, comida inicial, comida final, día de incremento de comida, cantidad de incremento de comida, patrón de comida, duración del experimento, y luminosidad.
Visualización y Ordenación:

Las poblaciones de bacterias se muestran en una lista que puede ser ordenada por nombre, fecha de inicio, o número de bacterias.
Al seleccionar una población de la lista, se muestran los detalles de la población en un área de texto.
Simulación de Experimentos:

La aplicación incluye la funcionalidad para simular la evolución de las bacterias en un plato de cultivo utilizando un modelo de simulación de Monte Carlo.
La simulación se representa gráficamente, mostrando cómo las bacterias se mueven y proliferan en el plato de cultivo día a día.
Guardado y Carga de Experimentos:

Los datos del experimento pueden ser guardados en un archivo y cargados desde un archivo.
La funcionalidad de guardado incluye opciones para "Guardar" y "Guardar como...".
Detalles Técnicos
LoginPanel:

Una clase que muestra un panel de inicio de sesión con un fondo de imagen.
Incluye campos de texto para el nombre de usuario y la contraseña, y un botón de inicio de sesión.
Verifica las credenciales ingresadas y permite el acceso a la aplicación principal si son correctas.
ExperimentoGUI:

La clase principal que extiende JFrame y maneja la interfaz gráfica de la aplicación.
Inicializa y configura los componentes de la GUI, incluyendo menús, paneles de control, y áreas de visualización.
Incluye métodos para agregar poblaciones, mostrar detalles, ordenar listas, y realizar simulaciones.
Simulación:

La simulación de Monte Carlo se realiza en un PlatoDeCultivo, que modela un plato de cultivo de 20x20 celdas.
Las bacterias comienzan en el centro del plato y se mueven, proliferan o mueren según reglas específicas de la simulación.
Los resultados de la simulación se actualizan y se muestran en un panel gráfico.

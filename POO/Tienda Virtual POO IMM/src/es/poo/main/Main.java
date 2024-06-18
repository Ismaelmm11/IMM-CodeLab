package es.poo.main;

import es.poo.cliente.*;

import Ventanas.*;

/**
 * Clase principal (Main) que contiene el método principal (main) del programa.
 */
public class Main {
	/**
     * Carga datos existentes y muestra la ventana principal de la aplicación.
     * @param args Argumentos de línea de comandos (no se utilizan en este caso).
     */
	public static void main(String[] args) {
		CRegistrado.load();
		VentanaMain miMain = VentanaMain.getVentanaMain();
		miMain.setVisible(true);
	}
}
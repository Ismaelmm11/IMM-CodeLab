package es.poo.entrada;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Teclado implements KeyListener{

	private boolean[] teclas = new boolean[256];
	
	public static boolean UP, IZQ, DER, DOWN;
	
	public Teclado() {
		UP = false;
		IZQ = false;
		DER = false;
		DOWN = false;
	}
	
	public void actualizar() {
		UP = teclas[KeyEvent.VK_UP];
		IZQ = teclas[KeyEvent.VK_LEFT];
		DER = teclas[KeyEvent.VK_RIGHT];
		DOWN = teclas[KeyEvent.VK_DOWN];
	}
	
	
	/**
	 * Código Teclas:
	 * Flecha Izquierda(37), Flecha Arriba(38), Flecha Derecha(39), Flecha Abajo(40)
	 * Espacio (32)		
	 */
	public void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyCode()); Imprime el valor de la tecla presionada.
		
		teclas[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		teclas[e.getKeyCode()] = false;
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

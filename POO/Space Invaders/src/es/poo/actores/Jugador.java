package es.poo.actores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import es.poo.entrada.Teclado;
import es.poo.math.Vector2D;

public class Jugador extends Figura {

	public Jugador(Vector2D posicion, BufferedImage imagen) {
		super(posicion, imagen);
		// TODO Auto-generated constructor stub
	}

	//Mover la nave usando las teclas.
	public void actualizar() {
		if(Teclado.DER == true) {
			posicion.setX(posicion.getX() + 2);
		}
		if(Teclado.IZQ == true) {
			posicion.setX(posicion.getX() - 2);
		}
		if(Teclado.UP == true) {
			posicion.setY(posicion.getY() - 2);
		}
		if(Teclado.DOWN == true) {
			posicion.setY(posicion.getY() + 2);
		}
	}

	public void dibujar(Graphics g) {
		//IMPORTANTE: Parsear a int la posicion x e y.
		g.drawImage(imagen, (int)posicion.getX(), (int)posicion.getY(), null);
	}
	
}

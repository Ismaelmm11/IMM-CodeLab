package es.poo.actores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import es.poo.math.Vector2D;

public abstract class Figura {

	protected BufferedImage imagen;
	
	/**
	 * IMPORTANTE: Aquí el sistema de coordenadas que conocemos es diferente el convencional.
	 * El eje Y está invertido, es decir cuando más alto tenga la posición Y un objeto más abajo estará,
	 * y cuando más bajo más arriba estará.
	 * 
	 * La coordenada (0, 0) se encuentra en la esquina superior izquierda, por lo tanto cualquier objeto
	 * que tenga su X o Y negativos, se saldrá del JFrame.
	 */

	protected Vector2D posicion;
	
	public Vector2D getPosicion() {
		return posicion;
	}

	public void setPosicion(Vector2D posicion) {
		this.posicion = posicion;
	}

	public Figura(Vector2D posicion, BufferedImage imagen) {
		this.posicion = posicion;
		this.imagen = imagen;
	}
	
	public abstract void actualizar();
	public abstract void dibujar(Graphics g);
	
	
}

package es.poo.actores;


import java.awt.image.BufferedImage;

import es.poo.estado.EstadoJuego;
import es.poo.math.Vector2D;
import es.poo.ventana.Ventana;

public class Disparo extends FiguraMovil{
	
	private Vector2D vectorVelocidad;
	
	private FiguraMovil lanzador;

	public Disparo(Vector2D posicion, Vector2D direccion, double velocidad, BufferedImage imagen, 
			BufferedImage[] frames, FiguraMovil lanzador, EstadoJuego estado) {
		super(posicion, direccion, velocidad, imagen, frames, estado);
		this.lanzador = lanzador;
		this.vectorVelocidad = direccion.multiplicar(velocidad);
		
		setFPS(50);
	}

	@Override
	public void actualizar() {
		super.actualizar();
		
		if(esDisparoNave()) {
			posicion = posicion.subY(vectorVelocidad);
			//Cuando el disparo salga de la ventana hay que eliminarlo, para que deje de consumir recursos.
			if(posicion.getY() <= 0) {
				kill();
			}
		}
		else {
			posicion = posicion.addY(vectorVelocidad);
			//Cuando el disparo salga de la ventana hay que eliminarlo, para que deje de consumir recursos.
			if(posicion.getY() >= Ventana.ALTO - 50) {
				kill();
			}
		}
		
		colision();
	}

	//Método Sobreescrito
	public Vector2D getCentro() {
		return new Vector2D(posicion.getX() + ancho/2, posicion.getY() + ancho/2); 
	}
	
	public boolean esDisparoNave() {
		if(lanzador instanceof Jugador) {
			return true;
		}
		return false;
	}
}

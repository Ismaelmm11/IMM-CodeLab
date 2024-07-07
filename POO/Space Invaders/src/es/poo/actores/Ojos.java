package es.poo.actores;

import java.awt.image.BufferedImage;

import es.poo.estado.EstadoJuego;
import es.poo.math.Vector2D;
import es.poo.ventana.Ventana;

public class Ojos extends FiguraMovil{
	
	private Vector2D vectorVelocidad;
	
	private int sentido;
	
	private static final int PUNTUACION = 500;

	public Ojos(Vector2D posicion, Vector2D direccion, double velocidad, BufferedImage imagen, BufferedImage[] frames, EstadoJuego estado, int sentido) {
		super(posicion, direccion, velocidad, imagen, frames, estado);
		
		vectorVelocidad = direccion.multiplicar(velocidad);
		
		this.sentido = sentido; 	// Sentido = 0(derecha) o otro(izquierda).
		if(sentido == 0) {
			indice = 4;
		}
		else {
			indice = 0;
		}
		
	}

	@Override
	public void actualizar() {
		
		if(temporizador.getActivo() == false) {
			indice++;
			if(indice >= 8 && sentido == 0) {
				indice = 4;
			}
			else if(indice >= 4 && sentido != 0){
				indice = 0;
			}
			temporizador.iniciar(FPS);
		}
		
		temporizador.actualizar();
	
		if(sentido == 0) {
			posicion = posicion.addX(vectorVelocidad);
			if(posicion.getX() > Ventana.ANCHO) {
				estado.getFigurasMoviles().remove(this);
			}
		}
		else {
			posicion = posicion.subX(vectorVelocidad);
			
			if(posicion.getX() < 0) {
				estado.getFigurasMoviles().remove(this);
			}
		}
		
		colision();
	}
	
	public void kill() {
		estado.puntuar(PUNTUACION, posicion);
		super.kill();
	}
}

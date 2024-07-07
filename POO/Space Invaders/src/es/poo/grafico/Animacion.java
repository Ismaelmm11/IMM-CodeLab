package es.poo.grafico;

import java.awt.image.BufferedImage;

import es.poo.math.Vector2D;

public class Animacion {

	private BufferedImage[] frames;
	
	private int velocidad;
	private int indice;
	
	private long tiempo, lastTime;
	
	private Vector2D posicion;
	
	private boolean animado;
	
	public Animacion(BufferedImage[] frames, int velocidad, Vector2D posicion) {
		this.frames = frames;
		this.velocidad = velocidad;
		this.posicion = posicion;
		
		indice = 0;
		tiempo = 0;
		lastTime = System.currentTimeMillis();
		
		animado = true;
	}
	
	public void actualizar() {
		
		tiempo += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(tiempo > velocidad) {
			tiempo = 0;
			indice++;
			if(indice == frames.length) {
				
				animado = false;
			}
		}
	}
	
	public boolean getAnimado() {
		return animado;
	}
	
	public Vector2D getPosicion() {
		return posicion;
	}
	
	public BufferedImage getCurrentFrame() {
		if(animado == true) {
			return frames[indice];
		}
		return null;
	}
}

package es.poo.actores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import es.poo.estado.EstadoJuego;
import es.poo.math.Cronometro;
import es.poo.math.Vector2D;

public abstract class FiguraMovil extends Figura {
	
	
	protected int FPS = 125;
	
	protected BufferedImage[] frames;
	
	protected Vector2D direccion;
	protected double velocidad;
	protected double angulo;
	
	protected int alto;
	protected int ancho;
	protected int indice;
	
	Cronometro temporizador;
	
	protected EstadoJuego estado;
	
	public FiguraMovil(Vector2D posicion, Vector2D direccion, double velocidad, BufferedImage imagen, BufferedImage[] frames, EstadoJuego estado) {
		super(posicion, imagen);
		this.frames = frames;
		this.direccion = direccion;
		this.velocidad = velocidad;
		this.estado = estado;
		alto = imagen.getHeight();
		ancho = imagen.getWidth();
		angulo = 0;
		indice = 0;
		
		temporizador = new Cronometro();
		temporizador.iniciar(FPS);
	}
	
	protected void setFPS(int FPS) {
		this.FPS = FPS;
	}
	
	public void actualizar() {
		
		if(temporizador.getActivo() == false) {
			indice++;
			if(indice >= frames.length) {
				indice=0;
			}
			temporizador.iniciar(FPS);
		}
		
		temporizador.actualizar();
	}
	
	public void dibujar(Graphics g) {
		
		g.drawImage(frames[indice], (int)posicion.getX(), (int)posicion.getY(), null); //1.25

	}
	
	protected Vector2D getCentro() {
		return new Vector2D(posicion.getX() + ancho/2, posicion.getY() + alto/2);
	}
	
	protected void colision() {
		
		ArrayList<FiguraMovil> figurasMoviles = estado.getFigurasMoviles();
		
		for(int i = 0; i < figurasMoviles.size(); i++) {
			FiguraMovil figura = figurasMoviles.get(i);
			
			if(figura.equals(this)) {	//Evitar comprobar la colision con el objeto mismo.
				continue;
			}
			
			double distancia = figura.getCentro().getDistancia(getCentro()).getMagnitud();
		
			if(distancia < figura.ancho/2.5 + ancho/2.5 && figurasMoviles.contains(this)) {
				colisionObjetos(figura, this);
			}
		}	
	}
	
	private void colisionObjetos(FiguraMovil a, FiguraMovil b) {
		
		if(a instanceof Jugador && ((Jugador) a).getInmortal() == true) {
			return;
		}
		
		if(b instanceof Jugador && ((Jugador) b).getInmortal() == true) {
			return;
		}

		
		if((a instanceof Disparo && b instanceof Jugador) || (a instanceof Jugador && b instanceof Disparo)) {
			if((a instanceof Disparo && ((Disparo) a).esDisparoNave()) || (b instanceof Disparo && ((Disparo) b).esDisparoNave())) {
				return;
			}
		}
		
		if((a instanceof Disparo && b instanceof Alien) || (a instanceof Alien && b instanceof Disparo)) {
			if((a instanceof Disparo && !((Disparo) a).esDisparoNave()) || (b instanceof Disparo && !((Disparo) b).esDisparoNave())) {
				return;
			}
		}
		
		if((a instanceof Disparo && b instanceof Ojos) || (a instanceof Ojos && b instanceof Disparo)) {
			if((a instanceof Disparo && !((Disparo) a).esDisparoNave()) || (b instanceof Disparo && !((Disparo) b).esDisparoNave())) {
				return;
			}
		}
		
		if(a instanceof Disparo && b instanceof Disparo) {
			return;
		}
		
		if(a instanceof Ojos && b instanceof Ojos) {
			return;
		}
		
		
		estado.addExplosion(a.getCentro());
		a.kill();
		b.kill();
	}
	
	protected void kill() {
		estado.getFigurasMoviles().remove(this);
	}
}

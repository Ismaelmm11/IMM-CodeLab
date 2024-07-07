package es.poo.actores;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import es.poo.estado.EstadoJuego;
import es.poo.grafico.Recursos;
import es.poo.grafico.Sonido;
import es.poo.math.Vector2D;

import java.util.Random;

public class Alien extends FiguraMovil{
	
	private Random aleatorio = new Random();
	
	private static final int MIN_CADENCIA = 200;
    private static final int MAX_CADENCIA = 500;
	
	private static final int[] VEL_DISPARO = {5, 5, 5, 5, 5, 7, 10};
	
	private static final int[] COOR_X_OG = {100, 150, 450, 900};
	private static final int[] COOR_X_SEP = {100, 120, 150, 170, 200, 300, 400};
	private static final int[] COOR_Y = {50, 125, 200, 275, 370};
	
	private static final int PUNTUACION = 250;
	
	
	
	private ArrayList<Vector2D> camino = new ArrayList<Vector2D>();
	
	private Vector2D vectorVelocidad;
	private Vector2D centroAbajo;
	
	private Sonido sonidoDisparo;
	
	private int contador;
	private int cadencia;
	private int velocidad;
	private int i = 0;
	

	public Alien(Vector2D posicion, Vector2D direccion, double velocidad, BufferedImage imagen, BufferedImage[] frames,
			Vector2D origen, Vector2D destino, EstadoJuego estado) {
		super(posicion, direccion, velocidad, imagen, frames, estado);

		vectorVelocidad = direccion.multiplicar(velocidad);
		centroAbajo = getCentro().subX(direccion.multiplicar(ancho/4.5));
		
		this.velocidad = getVelocidadRandom();
		
		indice = 0;
		contador = 0;

		camino.add(destino);
		camino.add(origen);
		
		cadencia = getNumeroRandom();
		
		//sonidoDisparo = new Sonido(Recursos.sonidoDisparoAlien);
	}
	
	public static ArrayList<Alien> fabricaDeAliens(int numFila, int numAliens, EstadoJuego esta){
		ArrayList<Alien> aliens = new ArrayList<Alien>();
		
		int n, x, y, separacion;
		
		switch(numFila) {
			case 1:
				y = COOR_Y[0];
				break;
			case 2:
				y = COOR_Y[1];
				break;
			case 3:
				y = COOR_Y[2];
				break;
			case 4:
				y = COOR_Y[3];
				break;
			case 5:
				y = COOR_Y[4];
				break;
			default:
				y = COOR_Y[2];
				break;
		}
		
		if(numAliens == 1) {
			Alien alien = new Alien(new Vector2D(COOR_X_OG[2], y), new Vector2D(1, 0), 2, Recursos.flame[0], Recursos.flame, new Vector2D(COOR_X_OG[0], y), new Vector2D(COOR_X_OG[3], y), esta);
			aliens.add(alien);
			
			return aliens;
		}
		else if(numAliens == 2) {
			n = 2;
			x = COOR_X_OG[1];
			separacion = COOR_X_SEP[6];
		}
		else if(numAliens == 3) {
			n = 3;
			x = COOR_X_OG[0];
			separacion = COOR_X_SEP[5];
		}
		else if(numAliens == 4) {
			n = 4;
			x = COOR_X_OG[1];
			separacion = COOR_X_SEP[4];
		}
		else if(numAliens == 5) {
			n = 5;
			x = COOR_X_OG[1];
			separacion = COOR_X_SEP[3];
		}
		else if(numAliens == 6) {
			n = 6;
			x = COOR_X_OG[0];
			separacion = COOR_X_SEP[2];
		}
		else if(numAliens == 7) {
			n = 7;
			x = COOR_X_OG[0];
			separacion = COOR_X_SEP[1];
		}
		else if(numAliens == 8) {
			n = 8;
			x = COOR_X_OG[1];
			separacion = COOR_X_SEP[0];
		}
		else if(numAliens == 9) {
			n = 9;
			x = COOR_X_OG[0];
			separacion = COOR_X_SEP[0];
		}
		else {
			n = 6;
			x = COOR_X_OG[0];
			separacion = COOR_X_SEP[2];
		}
		
		
		for(int i = 1; i <= n; i++) {
			Alien alien = new Alien(new Vector2D(x+(separacion*(i-1)), y), new Vector2D(1, 0), 2, Recursos.flame[0], Recursos.flame, new Vector2D(x + (separacion*(i-1)), y), new Vector2D(x + (separacion*i), y), esta);
			aliens.add(alien);
		}

		
		return aliens;
	}

	public void actualizar() {
		
		super.actualizar();
		
		contador++;
		
		if(contador >= cadencia && cadencia != 0) {
			
			centroAbajo = getCentro().subX(direccion.multiplicar(ancho/4.5));
			
			Disparo disparo = new Disparo(
					centroAbajo,
					new Vector2D(0, 1),
					velocidad,
					Recursos.disparoEvil[0],
					Recursos.disparoEvil,
					this,
					estado);
			
			//sonidoDisparo.reproducir();
			estado.getFigurasMoviles().add(0, disparo);
			
			contador = 0;
		}
		
		
		double distancia = camino.get(0).getDistancia(posicion).getMagnitud();
		double distancia2 = camino.get(1).getDistancia(posicion).getMagnitud();
		
		if (distancia == 0) {
			i = 1;
		}
		else if(distancia2 == 0){
			i = 0;
		}
		
		if(i%2 == 0) {
			posicion = posicion.addX(vectorVelocidad);
		}
		else {
			posicion = posicion.subX(vectorVelocidad);
		}
		
		colision();
	}
	
	public void kill() {
		estado.puntuar(PUNTUACION, posicion);
		super.kill();
	}
	
	private int getNumeroRandom() {
		return aleatorio.nextInt(MAX_CADENCIA - MIN_CADENCIA - 1) + MIN_CADENCIA;
	}
	
	private int getVelocidadRandom() {
		return VEL_DISPARO[(aleatorio.nextInt(VEL_DISPARO.length))];
	}
}

package es.poo.estado;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import es.poo.actores.*;
import es.poo.grafico.*;
import es.poo.math.Cronometro;
import es.poo.math.Vector2D;


import java.util.Random;


public class EstadoJuego {
	
	//Coordenadas de origen que pueden tomar los ojos:
	private static final int OJO_ARR = 500;
	private static final int OJO_MEDIO = 550;
	private static final int OJO_ABA = 600;
	
	private static final int OJO_DER = 1175;
	private static final int OJO_IZQ = -50;
	
	private static final int RATIO_SPAWN_OJOS = 6000;
	
	private static final int FPS_VIDA = 120;
	
	private static final int[] VEL_OJOS = {4, 5, 6, 8, 10, 20};;
	
	private Jugador jugador;
	
	private ArrayList<FiguraMovil> figurasMoviles = new ArrayList<FiguraMovil>();
	private ArrayList<Animacion> explosiones = new ArrayList<Animacion>();
	private ArrayList<Texto> mensajes = new ArrayList<Texto>();
	
	private Cronometro temporizadorOjos;
	
	private Cronometro temporizadorVida;
	
	private Random aleatorio = new Random();
	
	private boolean oleadaPro = false;
	
	private int puntaje;
	
	private int numVidas;
	
	private int indiceVida;
	
	public EstadoJuego() {
		
		temporizadorOjos = new Cronometro();
		temporizadorVida = new Cronometro();
		
		temporizadorOjos.iniciar(RATIO_SPAWN_OJOS);
		temporizadorVida.iniciar(FPS_VIDA);
		
		puntaje = 0;
		indiceVida = 0;
		
		
		jugador = new Jugador(new Vector2D(550, 550), new Vector2D(0, 1), Recursos.jugador, this);
		figurasMoviles.add(jugador);
		
		numVidas = jugador.getNumVidas();
		
		ArrayList<Alien> aliens = Alien.fabricaDeAliens(1, 9, this);
		for(int i = 0; i < 9; i++) {
			figurasMoviles.add(aliens.get(i));
		}
		aliens = Alien.fabricaDeAliens(2, 8, this);
		for(int i = 0; i < 8; i++) {
			figurasMoviles.add(aliens.get(i));
		}
		aliens = Alien.fabricaDeAliens(3, 7, this);
		for(int i = 0; i < 7; i++) {
			figurasMoviles.add(aliens.get(i));
		}
		aliens = Alien.fabricaDeAliens(4, 6, this);
		for(int i = 0; i < 6; i++) {
			figurasMoviles.add(aliens.get(i));
		}
		aliens = Alien.fabricaDeAliens(5, 5, this);
		for(int i = 0; i < 5; i++) {
			figurasMoviles.add(aliens.get(i));
		}
	}
	
	public void puntuar(int valor, Vector2D posicion) {
		puntaje += valor;
		mensajes.add(new Texto(this, posicion, Color.WHITE, true, Recursos.fuenteM, "+"+valor+" PUNTOS"));
	}
	
	private void oleada() {
		double x, y;
		
		int random, sentido;
		
		random = aleatorio.nextInt(3);
		
		if(random == 1) {
			y = OJO_ARR;
		}
		else if(random == 2) {
			y = OJO_MEDIO;
		}
		else {
			y = OJO_ABA;
		}
		
		random = aleatorio.nextInt(2);
		
		if(random == 1) {
			sentido = 1;
			x = OJO_DER;
		}
		else {
			sentido = 0;
			x = OJO_IZQ;
		}
		
		Ojos ojo = new Ojos(
				new Vector2D(x, y),
				new Vector2D(1,0),
				4,
				Recursos.ojos[0],
				Recursos.ojos,
				this,
				sentido);
		
		figurasMoviles.add(ojo);
	}
	
	private void oleada2(double yJug) {
		double x, y;
		
		int random, sentido, velocidad;

		velocidad = getVelocidadRandom();
		
		if(yJug >= 500 && yJug <= 540) {
			y = OJO_ARR;
		}
		else if(yJug > 540 && yJug <= 580) {
			y = OJO_MEDIO;
		}
		else {
			y = OJO_ABA;
		}
		
		random = aleatorio.nextInt(2);
		
		if(random == 1) {
			sentido = 1;
			x = OJO_DER;
		}
		else {
			sentido = 0;
			x = OJO_IZQ;
		}
		
		Ojos ojo = new Ojos(
				new Vector2D(x, y),
				new Vector2D(1,0),
				velocidad,
				Recursos.ojos[0],
				Recursos.ojos,
				this,
				sentido);
		
		figurasMoviles.add(ojo);
	}
	
	public void addExplosion(Vector2D posicion) {
		explosiones.add(new Animacion(
				Recursos.explosion,
				100,
				posicion.restar(new Vector2D(Recursos.explosion[0].getWidth()/2, Recursos.explosion[0].getWidth()/2))
				));
		
		//Recursos.sonidoExpl.reproducir();
	}
	
	
	public void actualizar() {
		
		double yJug = 550;
		
		for(int i = 0; i < figurasMoviles.size(); i++) {
			FiguraMovil figura = figurasMoviles.get(i);
			figura.actualizar();
			if(figura instanceof Jugador) {
				numVidas = ((Jugador) figura).getNumVidas();
				yJug = ((Jugador) figura).getPosicion().getY();
			}
		}
		
		if(temporizadorOjos.getActivo() == false) {
			oleada();
			if(oleadaPro == true) {
				oleada2(yJug);
			}
			oleadaPro = !oleadaPro;
			temporizadorOjos.iniciar(RATIO_SPAWN_OJOS);
		}
			
		if(numVidas == 4) {
			if(temporizadorVida.getActivo() == false) {
				indiceVida++;
				if(indiceVida >= Recursos.barra3HP.length) {
					indiceVida = 0;
				}
				temporizadorVida.iniciar(FPS_VIDA);
			}
			
			temporizadorVida.actualizar();
		}
		
		for(int i = explosiones.size() - 1; i >= 0; i--) {
			Animacion explosion = explosiones.get(i);
			explosion.actualizar();
			if(explosion.getAnimado() == false) {
				explosiones.remove(i);
			}
		}
		
		temporizadorOjos.actualizar();
	}
	
	public void dibujar(Graphics g) {
		for(int i = 0; i < figurasMoviles.size(); i++) {
			FiguraMovil figura = figurasMoviles.get(i);
			figura.dibujar(g);
		}
		
		for(int i = 0; i < explosiones.size(); i++) {
			Animacion explosion = explosiones.get(i);
			g.drawImage(explosion.getCurrentFrame(), (int)explosion.getPosicion().getX(), (int)explosion.getPosicion().getY(), null);
		}
		
		for(int i = 0; i < mensajes.size(); i++) {
			Texto txt = mensajes.get(i);
			txt.dibujarTxtDesvanecer((Graphics2D)g);
		}
		
		dibujarPuntuacion(g);
		
		if(numVidas == 1) {
			g.drawImage(Recursos.barra0HP, -5, -15, null);
		}
		else if(numVidas == 2) {
			g.drawImage(Recursos.barra1HP, -5, -15, null);
		}
		else if(numVidas == 3) {
			g.drawImage(Recursos.barra2HP, -5, -15, null);
		}
		else if(numVidas == 4){
			g.drawImage(Recursos.barra3HP[indiceVida], -5, -15, null);
		}
		
	}
	
	private void dibujarPuntuacion(Graphics g) {
		
		int x, y;
		
		x = 1050;
		y = 10;
		
		String cadenaPuntuacion = Integer.toString(puntaje);
		
		for(int i = 0; i < cadenaPuntuacion.length(); i++) {
			
			g.drawImage(Recursos.numeros[Integer.parseInt(cadenaPuntuacion.substring(i, i+1))], x, y, null);
			x += 20; 
		}
		
	}
	
	public ArrayList<FiguraMovil> getFigurasMoviles(){
		return figurasMoviles;
	}
	
	public ArrayList<Texto> getMensajes(){
		return mensajes;
	}
	
	private int getVelocidadRandom() {
		return VEL_OJOS[(aleatorio.nextInt(VEL_OJOS.length))];
	}
	
}


/**
 * Armonia Absoluta:
 * alien1 = new Alien(new Vector2D(100, 50), new Vector2D(1, 0), 2, Recursos.flame[0], Recursos.flame, new Vector2D(100, 50), new Vector2D(1000, 50),  this);
		alien2 = new Alien(new Vector2D(100, 125), new Vector2D(1, 0), 3, Recursos.flame[0], Recursos.flame, new Vector2D(100, 125), new Vector2D(1000, 125),  this);
		alien3 =new Alien(new Vector2D(100, 200), new Vector2D(1, 0), 4, Recursos.flame[0], Recursos.flame, new Vector2D(100, 200), new Vector2D(1000, 200),  this);
		alien4 = new Alien(new Vector2D(100, 275), new Vector2D(1, 0), 5, Recursos.flame[0], Recursos.flame, new Vector2D(100, 275), new Vector2D(1000, 275),  this);
		alien5 = new Alien(new Vector2D(100, 370), new Vector2D(1, 0), 6, Recursos.flame[0], Recursos.flame, new Vector2D(100, 370), new Vector2D(1000, 370),  this);
		figurasMoviles.add(jugador);
		figurasMoviles.add(alien1);
		figurasMoviles.add(alien2);
		figurasMoviles.add(alien3);
		figurasMoviles.add(alien4);
		figurasMoviles.add(alien5);
 */

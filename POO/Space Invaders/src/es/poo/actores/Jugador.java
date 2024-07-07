package es.poo.actores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import es.poo.entrada.Teclado;
import es.poo.estado.EstadoJuego;
import es.poo.grafico.Recursos;
import es.poo.grafico.Sonido;
import es.poo.math.Cronometro;
import es.poo.math.Vector2D;
import es.poo.ventana.Ventana;

public class Jugador extends FiguraMovil {

	//Parámetros del disparo de la nave:
	private static final int CADENCIA = 800;
	private static final int VEL_DISP = 5;
	
	//Bordes del rango por donde se puede mover el jugador.
	private static final int BORDE_SUP = 500;
	private static final int BORDE_DER = Ventana.ANCHO-80;
	private static final int BORDE_IZQ = 0;
	private static final int BORDE_INF = Ventana.ALTO-90;
	
	//Ventana alto(700) y ancho (1200)
	
	private Cronometro temporizadorDisparo = new Cronometro();
	
	private Cronometro temporizadorInmortal = new Cronometro();
	
	private Cronometro temporizadorTICKS = new Cronometro();
	
	private Sonido sonidoDisparo;
	
	private boolean inmortal;
	
	private boolean visible;
	
	private int contador;			//Contador para controlar la animación del fuego.
	
	private int numVidas;
	

	public Jugador(Vector2D posicion, Vector2D direccion, BufferedImage imagen, EstadoJuego estado) {
		//La nave no necesita de velocidad, ni maxVel por eso se lo iniciamos a 0.
		super(posicion, direccion, 0, imagen, null,estado);
		
		numVidas = 4;
		contador = 1;
		
		inmortal = false;
		visible = true;
		
		sonidoDisparo = new Sonido(Recursos.sonidoDisparo);
		
		temporizadorDisparo.iniciar(CADENCIA);
	}

	//Mover la nave usando las teclas.
	public void actualizar() {
		
		//Establecemos la cadencia del disparo según los ms pasados entre disparo y disparo.
		
		if(temporizadorInmortal.getActivo() == false) {
			inmortal = false;
			visible = true; 
		}
		
		if(inmortal == true) {
			
			if(temporizadorTICKS.getActivo() == false) {
				temporizadorTICKS.iniciar(200);
				visible = !visible;
			}
		}
		
		if(Teclado.DER == true) {
			posicion.setX(posicion.getX() + 4);
		}
		if(Teclado.IZQ == true) {
			posicion.setX(posicion.getX() - 4);
		}
		if(Teclado.UP == true) {
			posicion.setY(posicion.getY() - 4);
		}
		if(Teclado.DOWN == true) { 
			posicion.setY(posicion.getY() + 4);
		}
		//Cada 400ms se podrá lanzar un disparo.
		if(Teclado.SHOOT == true && temporizadorDisparo.getActivo() == false && inmortal == false) {
			Disparo disparo = new Disparo(
					getCentro().restar(new Vector2D(1,1).multiplicar(alto/3)),
					direccion,
					VEL_DISP,
					Recursos.disparo[0],
					Recursos.disparo,
					this,
					estado
					);
			
			estado.getFigurasMoviles().add(0, disparo);
			sonidoDisparo.reproducir();
			temporizadorDisparo.iniciar(CADENCIA);
		}
		
		//Limitamos el rango de movimiento de la nave:
		if(posicion.getX() >= BORDE_DER) {
			posicion.setX(BORDE_DER);
		}
		if(posicion.getX() <= BORDE_IZQ) {
			posicion.setX(BORDE_IZQ);
		}
		if(posicion.getY() >= BORDE_INF) {
			posicion.setY(BORDE_INF);
		}
		if(posicion.getY() <= BORDE_SUP) {
			posicion.setY(BORDE_SUP);
		}
		
		temporizadorDisparo.actualizar();
		temporizadorInmortal.actualizar();
		temporizadorTICKS.actualizar();
		colision();
	}

	public void dibujar(Graphics g) {
		
		if(visible == false) {
			return;
		}
		
		//IMPORTANTE: Parsear a int la posicion x e y:
		g.drawImage(imagen, (int)posicion.getX(), (int)posicion.getY(), null);
		
		if(contador >= 1 && contador <= 7) {
			g.drawImage(Recursos.fuego1, (int)posicion.getX() + (int)(ancho/2.6), (int)posicion.getY() + (int)(alto/1.25), null); //1.25
		}
		else if(contador >= 8 && contador <= 15) {
			g.drawImage(Recursos.fuego2, (int)posicion.getX() + (int)(ancho/2.6), (int)posicion.getY() + (int)(alto/1.3), null); //1.3			
		}
		else if(contador >= 16 && contador <= 23) {
			g.drawImage(Recursos.fuego3, (int)posicion.getX() + (int)(ancho/2.6), (int)posicion.getY() + (int)(alto/1.36), null); //1.36	
		}
		else if(contador >= 24) {
			g.drawImage(Recursos.fuego4, (int)posicion.getX() + (int)(ancho/2.6), (int)posicion.getY() + (int)(alto/1.33), null); //1.33
			if(contador == 30) {
				contador = 0;
			}
		}
		contador++;
	}
	
	public void kill() {
		if(inmortal == true) {
			return;
		}
		numVidas--;
		inmortal = true;
		temporizadorInmortal.iniciar(3000);
		if(numVidas == 0) {
			super.kill();
		}
	}
	
	public void setNumVidas(int numVidas) {
		this.numVidas = numVidas;
	}
	
	public int getNumVidas() {
		return numVidas;
	}
	
	public boolean getInmortal() {
		return inmortal;
	}
	
}

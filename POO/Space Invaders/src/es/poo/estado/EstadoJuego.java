package es.poo.estado;

import java.awt.Graphics;

import es.poo.actores.Jugador;
import es.poo.grafico.Recursos;
import es.poo.math.Vector2D;

public class EstadoJuego {

	private Jugador jugador;
	
	
	public EstadoJuego() {
		jugador = new Jugador(new Vector2D(100, 250), Recursos.jugador);
	}
	
	
	public void actualizar() {
		jugador.actualizar();
	}
	
	public void dibujar(Graphics g) {
		jugador.dibujar(g);
	}
	
}

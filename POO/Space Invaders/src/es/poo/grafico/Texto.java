package es.poo.grafico;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import es.poo.estado.EstadoJuego;
import es.poo.math.Vector2D;

public class Texto {
	
	private static final float VEL_DESVANECER = 0.01f;
	
	private Vector2D vectorVelocidad = new Vector2D(0, 1);
	
	private EstadoJuego esta;
	private float transparente; //Para hacer el texto transparente.
	private Vector2D posicion;
	private Color color;
	private boolean desvanecer;
	private Font fuente;
	private String texto;
	
	public Texto(EstadoJuego esta, Vector2D posicion, Color color, boolean desvanecer, Font fuente, String texto) {
		this.esta = esta;
		this.posicion = posicion;
		this.color = color;
		this.desvanecer = desvanecer;
		this.fuente = fuente;
		this.texto = texto;
		
		if(desvanecer == true) {
			transparente = 1;
		}
		else {
			transparente = 0;
		}
	}
	
	private void dibujarTxt(Graphics g) {
		
		g.setColor(color);
		g.setFont(fuente);
		
		Vector2D posicionAux = new Vector2D(posicion.getX(), posicion.getY());
		
		FontMetrics metricasFuente = g.getFontMetrics();
		posicionAux.setX(posicionAux.getX() - metricasFuente.stringWidth(texto)/2);
		posicionAux.setY(posicionAux.getY() - metricasFuente.getHeight()/2);
		
		
		g.drawString(texto, (int)posicionAux.getX(), (int)posicion.getY());
	}
	
	public void dibujarTxtDesvanecer(Graphics2D g2d) {
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  transparente));
		
		dibujarTxt(g2d);
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  1));
	
		posicion = posicion.subY(vectorVelocidad);
		
		if(desvanecer == true) {
			transparente = transparente - VEL_DESVANECER;
		}
		else {
			transparente += VEL_DESVANECER;
		}
		
		if((desvanecer == true && transparente <= 0) || (desvanecer == false && transparente >= 1)) {
			esta.getMensajes().remove(this);
		}
	}
}

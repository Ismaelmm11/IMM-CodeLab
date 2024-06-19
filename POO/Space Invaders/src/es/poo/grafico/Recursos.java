package es.poo.grafico;

import java.awt.image.BufferedImage;

public class Recursos {
	
	
	
	
	public static BufferedImage jugador;
	
	public static void inicializar(){
		jugador = Figura.CargarImagen("/naves/space_ship.png");
	}
	
}

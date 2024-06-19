package es.poo.grafico;

import java.awt.image.BufferedImage;

public class Recursos {
	
	
	
	
	public static BufferedImage jugador;
	
	public static void inicializar(){
		jugador = Imagen.CargarImagen("/naves/space_ship.png");
	}
	
}

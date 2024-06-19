package es.poo.grafico;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Figura {
	
	
	public static BufferedImage CargarImagen(String ruta) {
		
		try {
			return ImageIO.read(Figura.class.getResourceAsStream(ruta));
		} catch (IOException e) {
			e.printStackTrace();	//Imprimir el error en caso de que surja.
		}
		
		return null;	
	}
}

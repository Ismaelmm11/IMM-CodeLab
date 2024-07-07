package es.poo.grafico;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Multimedia {
	
	
	public static BufferedImage CargarImagen(String ruta) {
		
		try {
			return ImageIO.read(Multimedia.class.getResourceAsStream(ruta));
		} catch (IOException e) {
			e.printStackTrace();	//Imprimir el error en caso de que surja.
		}
		
		return null;	
	}
	
	public static Font CargarFuente(String ruta, int tamano) {
		
		try {
			return Font.createFont(Font.TRUETYPE_FONT, Multimedia.class.getResourceAsStream(ruta)).deriveFont(Font.PLAIN, tamano);
		}
		catch(FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Clip CargarSonido(String ruta) {
				
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Multimedia.class.getResource(ruta)));
			
			return clip;
		}
		catch(LineUnavailableException e) {
			e.printStackTrace();
		}
		catch(UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	
		return null;
	}
}



package es.poo.grafico;

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

public class Recursos {
	
	
	
	
	public static BufferedImage jugador;
	
	public static BufferedImage fuego1, fuego2, fuego3, fuego4;
	
	public static BufferedImage[] ojos = new BufferedImage[8];
	
	public static BufferedImage[] disparo = new BufferedImage[6];
	
	public static BufferedImage[] disparoEvil = new BufferedImage[6];
	
	public static BufferedImage[] explosion = new BufferedImage[8];
	
	public static BufferedImage[] flame = new BufferedImage[4];
	
	public static BufferedImage[] numeros = new BufferedImage[10];
	
	public static BufferedImage laserBlue;
	
	public static BufferedImage barra0HP;
	
	public static BufferedImage barra1HP;
	
	public static BufferedImage barra2HP;
	
	public static BufferedImage[] barra3HP = new BufferedImage[6];
	
	public static Font fuenteG;
	
	public static Font fuenteM;
	
	public static Clip sonidoDisparo;
	
	public static Clip sonidoExplosion;
	
	public static Sonido sonidoExpl;
	
	public static void inicializar(){
		jugador = Multimedia.CargarImagen("/naves/space_ship.png");
		fuego1 = Multimedia.CargarImagen("/efectos/fuego1.png");
		fuego2 = Multimedia.CargarImagen("/efectos/fuego2.png");
		fuego3 = Multimedia.CargarImagen("/efectos/fuego3.png");
		fuego4 = Multimedia.CargarImagen("/efectos/fuego4.png");
		
		laserBlue = Multimedia.CargarImagen("/disparos/laserblue.png");
		
		barra0HP = Multimedia.CargarImagen("/vida/BARRA_0_HP.png");
		barra1HP = Multimedia.CargarImagen("/vida/BARRA_1_HP.png");
		barra2HP = Multimedia.CargarImagen("/vida/BARRA_2_HP.png");
		
		for(int i = 0; i < barra3HP.length; i++) {
			barra3HP[i] = Multimedia.CargarImagen("/vida/BARRA_3_HP_000"+(i + 1)+".png");
		}
		
		for(int i = 0; i < ojos.length; i++) {
			ojos[i] = Multimedia.CargarImagen("/meteoros/METEORO_000" + (i + 1) + ".png");
		}
		
		for(int i = 0; i < disparo.length; i++) {
			disparo[i] = Multimedia.CargarImagen("/disparos/DISPARO_LASER_000"+ (i + 1) +".png");
			disparoEvil[i] = Multimedia.CargarImagen("/disparos/DISPARO_EVIL_000"+ (i + 1) +".png");
		}
		
		
		for(int i = 0; i < flame.length; i++) {
			flame[i] = Multimedia.CargarImagen("/aliens/FIRE_000" + (i + 1) + ".png");
		}
		
		for(int i = 0; i < disparo.length; i++) {
			explosion[i] = Multimedia.CargarImagen("/explosiones/EXPLOSION_000"+ (i + 1) +".png"); 
		}
		
		for(int i = 0; i < numeros.length; i++) {
			numeros[i] = Multimedia.CargarImagen("/numeros/" + i +".png"); 
		}
		
		fuenteG = Multimedia.CargarFuente("/fuente/Pixel NES.otf", 42);
		fuenteM = Multimedia.CargarFuente("/fuente/Pixel NES.otf", 20);
	
		sonidoDisparo = Multimedia.CargarSonido("/sonido/DISPARO_LASER.wav");
		sonidoExplosion = Multimedia.CargarSonido("/sonido/EXPLOSION.wav");
		
		sonidoExpl = new Sonido(sonidoExplosion);
	}
	
}

package es.poo.grafico;

import javax.sound.sampled.Clip;

public class Sonido {
	
	private Clip clip;
	
	public Sonido(Clip clip) {
		this.clip = clip;
	}
	
	public void reproducir() {
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void parar() {
		clip.stop();
	}
}

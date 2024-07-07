package es.poo.math;

public class Cronometro {
	
	private long lastTime;
	
	private long tiempo;
	
	private int alarma;
	
	private boolean activo;
	
	public Cronometro() {
		
		tiempo = 0;
		
		lastTime = 0;
		
		activo = false;
	}
	
	public void iniciar(int alarma) {
		
			activo = true;
			
			this.alarma = alarma;
	}
	
	public void actualizar() {
		
		if(activo == true) {
			tiempo += System.currentTimeMillis() - lastTime;
		}
		
		if(tiempo >= alarma) {
			activo = false;
			tiempo = 0;
		}
		
		lastTime = System.currentTimeMillis();
	}
	
	public boolean getActivo() {
		return activo;
	}

}

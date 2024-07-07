package es.poo.math;

public class Vector2D {
	/**
	* Vector: Posición en el espacio del sistema de coordenadas de 2 dimensiones(X e Y).
	* Dirección: Esta representa por un angulo y su magnitud.
	* Magnitud: Es una cantidad representa la longitud de un vector.
	*/
	
	private double x, y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	//Método Sobrecargado
	public Vector2D() {
		x = 0;
		y = 0;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public Vector2D addY(Vector2D v) {
		return new Vector2D(x, y + v.getY());
	}
	
	public Vector2D subY(Vector2D v) {
		return new Vector2D(x, y - v.getY());
	}
	
	public Vector2D addX(Vector2D v) {
		return new Vector2D(x + v.getX(), y);
	}
	
	public Vector2D subX(Vector2D v) {
		return new Vector2D(x - v.getX(), y);
	}
	
	public Vector2D restar(Vector2D v) {
		return new Vector2D(x - v.getX(), y - v.getY());
	}
	
	public Vector2D multiplicar(double valor) {
		return new Vector2D(x * valor, y * valor);
	}
	
	public Vector2D limite(double valor) {
		if(getMagnitud() > valor) {
			return this.normalizar().multiplicar(valor);
		}
		return this;
	}
	
	public Vector2D normalizar() {
		
		double magnitudAux = getMagnitud();
		
		return new Vector2D(x / magnitudAux, y / magnitudAux);
	}
	
	public double getMagnitud() {
		return Math.sqrt(x*x + y*y);
	}
	
	public Vector2D getDistancia(Vector2D v) {
		return new Vector2D(x - v.getX(), y - v.getY());
	}
	
	public Vector2D setDireccion(double angulo) {
		double magnitudAux = getMagnitud();
		
		return new Vector2D(Math.cos(angulo)*magnitudAux, Math.sin(angulo)*magnitudAux);
	}
}

package es.poo.math;

public class Vector2D {
	
	//Vector: Posición en el espacio del sistema de coordenadas de 2 dimensiones(X e Y).
	
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
}

package es.poo.pedido;

import es.poo.producto.Producto;

public class LineaPedido {

	private Producto producto;
	private int cantidad = 0;
	private double precioNeto, impuestos, precioTotal;

	/**
	 * Método que devuelve el producto asociado a la instancia LineaPedido.
	 * @return producto que es una instancia de la Clase Producto.
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * Método que devuelve la cantidad del producto que hay en la instancia de LineaPedido.
	 * @return cantidad que es int.
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Método que devuelve el precio total del objeto LineaPedido.
	 * @return precioTotal que es un double.
	 */
	public double getPrecioTotal() {
		setPrecioTotal();
		return precioTotal;
	}

	/**
	 * Método que devuelve los impuestos totales de la instancia de LineaPedido.
	 * @return impuestos que es un double.
	 */
	public double getImpuestos() {
		setImpuestos();
		return impuestos;
	}

	/**
	 * Método que devuelve el precio neto total de la instancia LineaPedido.
	 * @return precioNeto que es un double.
	 */
	public double getPrecioNeto() {
		setPrecioNeto();
		return precioNeto;
	}
	
	/**
	 * Método con el que se calcula el precio neto total.
	 */
	private void setPrecioNeto() {
		precioNeto = producto.getPrecioNeto() * cantidad;
	}

	/**
	 *Método que calcula los impuestos totales. 
	 */
	private void setImpuestos() {
		impuestos = producto.getImpuestos() * cantidad;
	}
	
	/**
	 * Método que devuelve el total del importe final.
	 */
	private void setPrecioTotal() {
		precioTotal = producto.getPrecioFinal() * cantidad;
	}

	/**
	 * Método que asigna la cantidad de productos a una instancia de LineaPedido.
	 * Además aprovecha para realizar el calculo del precio total tras el cambio.
	 * @param cantidad que es un int que representa la cantidad.
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
		setPrecioTotal();
	}
	
	/**
	 * Método que asigna un producto a la instancia de LineaPedido.
	 * Además aprovecha para realizar el calculo del precio total tras el cambio.
	 * @param producto que es una instancia de la Clase Producto.
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
		setPrecioTotal();
	}

	/**
	 * Constructor de la clase LineaPedido que inicializa una instancia de LineaPedido e inicializa los valores.
	 * @param producto instancia del producto asociado a la línea de pedido.
	 * @param cantidad int que representa la cantidad de unidades solicitadas.
	 */
	public LineaPedido(Producto producto, int cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
		setPrecioTotal();
	}
	
	/**
	 * Método aumentar la cantidad de una instancia de LineaPedido.
	 * Además aprovecha para realizar el calculo del precio total tras el cambio.
	 * @param cantidad que es un int.
	 */
	public void addCantidad(int cantidad) {
		this.cantidad += cantidad;
		setPrecioTotal();
	}

}

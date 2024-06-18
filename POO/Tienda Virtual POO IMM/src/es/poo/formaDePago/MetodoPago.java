package es.poo.formaDePago;

import es.poo.main.IMostrable;

public class MetodoPago implements IMostrable {
	
	protected String descripcion;
	
	/**
	 * Constructor de la clase MetodoPago que inicializa un objeto MetodoPago asignandole una descripción.
	 * @param descripcion String con la descripcion del metodo.
	 */
	protected MetodoPago(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/**
	 * Método que devuelve la descripción de un método de pago.
	 * Es un método implementado de la forma de envío.
	 * @return descripicion String con la descripcion del metodo.
	 */
	public String dameCadena() {
		return descripcion;
	}
}

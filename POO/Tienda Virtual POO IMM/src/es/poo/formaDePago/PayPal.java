package es.poo.formaDePago;

import java.time.LocalDate;

import es.poo.FuncionesUtiles.Fecha;

public class PayPal extends MetodoPago {

	private final static String descripcionCompleta = "Plataforma de pago en línea para realizar transacciones y transferencias electrónicas.";
	
	private String usuario;
	private LocalDate fechaAlta;
	
	/**
	 * Método que devuelve la fecha de alta de un PayPal.
	 * @return fechaAlta que es un String con la fecha de alta.
	 */
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * Método que devuelve el usuario de un PayPal.
	 * @return usuario que es un String con el usuario de PayPal.
	 */
	public String getUsuario() {
		return usuario;
	}
	
	/**
	 * Constructor de la clase PayPal que inicializa un objeto de la clase PayPal inicializando la descripción
	 * usando la descripcionCompleta que comparten todos los PayPal.
	 */
	public PayPal() {
		super(descripcionCompleta);
	}
	
	/**
	 * Método para asignar un usuario al objeto PayPal.
	 * @param usuario String con el usuario a asignar.
	 * @throws IllegalArgumentException Si el usuario es nulo o está en blanco.
	 */
	public void setUsusario(String usuario) throws IllegalArgumentException {
		if(usuario.isBlank()) {
			throw new IllegalArgumentException("El campo de usuario es obligatorio de rellenar.");
		}
		
		this.usuario = usuario;
	}
	
	/**
	 * Método para asignar la fecha de alta de un objeto PayPal.
	 * @param fecha String con la fecha de alta.
	 * @throws IllegalArgumentException Si la fecha no es válida y no cumple ciertos requisitos.
	 */
	public void setFechaAlta(String fecha) throws IllegalArgumentException{
		this.fechaAlta = Fecha.setFecha(fecha);
	}
}

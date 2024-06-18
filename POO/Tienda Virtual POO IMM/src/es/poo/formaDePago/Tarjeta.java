package es.poo.formaDePago;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.poo.FuncionesUtiles.Fecha;

public class Tarjeta extends MetodoPago {

	private final static String descripcionCompleta = "Instrumento crediticio para realizar compras y transacciones con pago posterior.";

	private String numTarjeta, CVC, fechaCaducidad, titular;

	public enum tipoTarjeta {
		VISA, CUATROB, EURO600, AMERICAN_EXPRESS;
	}

	private tipoTarjeta tipo;

	/**
	 * Método que devuelve el titular de la tarjeta.
	 * 
	 * @return titular que es un String que contiene el titular.
	 */
	public String getTitular() {
		return titular;
	}

	/**
	 * Método que devuelve el tipo de una tarjeta.
	 * 
	 * @return Tipo de la tarjeta.
	 */
	public tipoTarjeta getTipo() {
		return tipo;
	}

	/**
	 * Método que devuelve el nº de la tarjeta de crédito.
	 * 
	 * @return numTarjeta que es un String con el nº de tarjeta.
	 */
	public String getNumTarjeta() {
		return numTarjeta;
	}

	/**
	 * Método que devuelve el nº de tarjeta cifrado, ocultando los 3 primeros
	 * cuartetos de números.
	 * 
	 * @return numTarjeta cifrado.
	 */
	public String getNumTarjetaCifrado() {
		// Dividimos el nº de tarjeta en cuartetos.
		String[] cuartetos = numTarjeta.split(" ");

		// Sustituimos los 3 primeros cuartetos por 'XXXX'.
		for (int i = 0; i < 3; i++) {
			cuartetos[i] = "XXXX";
		}

		return String.join(" ", cuartetos);
	}

	/**
	 * Método que devuelve el CVC de una tarjeta.
	 * 
	 * @return CVC que es un String con el CVC de la tarjeta.
	 */
	public String getCVC() {
		return CVC;
	}

	/**
	 * Método que devuelve la fecha de caducidad de la Tarjeta.
	 * 
	 * @return fecahCaducidad que es un String con la fecha de caducidad
	 */
	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	/**
	 * Método que asigna el nº de tarjeta según el índice.
	 * 
	 * @param indice Es un int que representa el tipo de tarjeta(1 = VISA, 2 = 4B, 3 =
	 *               Euro600, 4 = American Express).
	 */
	public void setTipo(int indice) {
		if (indice == 1) {
			this.tipo = tipoTarjeta.VISA;
		} else if (indice == 2) {
			this.tipo = tipoTarjeta.CUATROB;
		} else if (indice == 3) {
			this.tipo = tipoTarjeta.EURO600;
		} else if (indice == 4) {
			this.tipo = tipoTarjeta.AMERICAN_EXPRESS;
		}
	}

	/**
	 * Método que asigna la fecha de caducidad de la tarjeta.
	 * @param fecha String que contiene la fecha de caducidad en formato(MM/YY).
	 */
	public void setFechaCaducidad(String fecha) {
		try {
			this.fechaCaducidad = Fecha.setCaducidadTarjeta(fecha);
		}
		//Si la fecha no es válida se anula la asignación y se le asigna null.
		catch(IllegalArgumentException e) {
			this.fechaCaducidad = null;
		}
	}

	/**
	 * Método para asignar un nº de tarjeta a la Tarjeta, siempre que tenga el formato correcto.
	 * @param numTarjeta String con el nº de tarjeta a asignar
	 * @throws IllegalArgumentException Si el nº de tarjeta no tiene el formato correcto
	 */
	public void setNumTarjeta(String numTarjeta) throws IllegalArgumentException {
		// Expresión regular para validar que la cadena está en el formato: "XXXX XXXX XXXX XXXX".
		String patronTarjeta = "^\\d{4} \\d{4} \\d{4} \\d{4}$";

		Pattern patron = Pattern.compile(patronTarjeta);

		Matcher coincidencia = patron.matcher(numTarjeta);

		if (!coincidencia.matches()) {
			throw new IllegalArgumentException("El formato de la tarjeta de crédito no es válido.\n"
					+ "Por favor, use el formato: XXXX XXXX XXXX XXXX");
		}

		this.numTarjeta = numTarjeta;
	}
	
	/**
	 * Método para asignar el CVC de la tarjeta.
	 * @param CVC String con el CVC a asignar.
	 * @throws IllegalArgumentException Si el CVC no tiene una longitud de 3 carácteres.
	 */
	public void setCVC(String CVC) throws IllegalArgumentException {
		if ((CVC.length()) != 3) {
			throw new IllegalArgumentException("El CVC ha de ser de 3 números.");
		}

		this.CVC = CVC;
	}
	
	
	/**
	 * Método para asignar el titular de la tarjeta.
	 * @param titular String con el titular a asignar.
	 * @throws IllegalArgumentException Si el titular es nulo o está en blanco.
	 */
	public void setTitular(String titular) throws IllegalArgumentException {
		if (titular.isBlank()) {
			throw new IllegalArgumentException("El campo del titular no puede estar vacío.");
		}
		this.titular = titular;
	}
	
	/**
	 * Constructor de la clase Tarjeta que inicializa un objeto de la clase Tarjeta inicializando la descripción
	 * usando la descripcionCompleta que comparten todos las Tarjetas.
	 */
	public Tarjeta() {
		super(descripcionCompleta);
	}
}

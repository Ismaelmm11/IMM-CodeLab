package es.poo.cliente;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.poo.main.IMostrable;

public class Cliente implements IMostrable{
	  
	protected String correo, nombre, apellidos;
	private String direccion;
	
	//Getters:
	
	/**
	 * Método que devuelve el correo eléctronico del cliente.
	 * @return String que es el correo del cliente.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Método que devuelve el nombre del cliente.
	 * @return String que es el nombre del cliente.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que devuelve los apellidos del cliente.
	 * @return String que son los apellidos del cliente.
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Método que devuelve la dirección del cliente.
	 * @return String que es la dirección del cliente.
	 */
	public String getDireccion() {
		return direccion;
	}
	
	//Setters:
	/**
	 * Método que valida el correo introducido y si es válido se lo asigna al Cliente.
	 * @param correo String con el correo a asignar.
	 * @throws IllegalArgumentException Si el correo no cumple el patrón especificado.
	 * @throws NullPointerException Si el correo es null o está en blanco.
	 */
	public void setCorreo(String correo) throws IllegalArgumentException, NullPointerException {
		validarCorreo(correo);
		this.correo = correo;
	}

	/**
	 * Método que verifica si el nombre está vacío, si no lo está lo asigna al Cliente.
	 * @param nombre String con el nombre a asignar.
	 * @throws NullPointerException Si el nombre es null o está en blanco.
	 */
	public void setNombre(String nombre) throws NullPointerException {
		if(nombre.isBlank()) {
			throw new NullPointerException("El campo de nombre es obligatorio rellenarlo.\n");
		}
		this.nombre = nombre;
	}

	/**
	 * Método que verifica si los apellidos están vacío, si no lo están los asigna al Cliente.
	 * @param nombre String con los apellidos a asignar.
	 * @throws NullPointerException Si los apellidos son null o están en blanco.
	 */
	public void setApellidos(String apellidos) throws NullPointerException {
		if(apellidos.isBlank()) {
			throw new NullPointerException("El campo de nombre es obligatorio rellenarlo.\n");
		}
		this.apellidos = apellidos;
	}

	/**
	 * Método para asignar una dirección al Cliente.
	 * @param direccion String con la dirección a asignar.
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	/**
	 * Constructor de Cliente que inicializa un objeto Cliente con correo, nombre y apellidos.
	 * @param correo String con el correo del Cliente.
	 * @param nombre String con el nombre del Cliente.
	 * @param apellidos String con el apellidos del Cliente.
	 * @throws IllegalArgumentException Si el correo no cumple con el patrón específico.
	 */
	public Cliente(String correo, String nombre, String apellidos) throws IllegalArgumentException {
		validarCorreo(correo);
		this.correo = correo;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}
	
	/**
	 * Contructor de Cliente que inicializa un objeto Cliente sin asignarle ningún valor.
	 */
	public Cliente() {
		
	}
	
	/**
	 * Método que valida el correo usando un patrón específico. Básicamente acepatará cualquier correo
	 * que tenga caracteres delante y detrás del "@" y del "." .
	 * @param correo String con el correo a validar
	 * @throws IllegalArgumentException Si el correo no cumple el patrón especificado.
	 * @throws NullPointerException Si el correo es null o está en blanco.
	 */
	protected void validarCorreo(String correo) throws IllegalArgumentException, NullPointerException {
		//Este es un patrón poco restrictivo que permite cualquier correo que cumpla lo siguiente.
		//Verficará si hay carácteres antes y después de la '@' y del '.' .
		if(correo.isBlank()) {
			throw new NullPointerException("El campo de correo electrónico es obligatorio de rellenar.");
		}
		String patronExtensiones = "^.+@.+\\..+$"; 
		
		//Patron se usa para representar un patrón compilando el patronExtensiones.
		Pattern patron = Pattern.compile(patronExtensiones);
		
		//Coincidencia la usaremos para buscar coincidencias entre el patrón y una cadena, en este caso el correo.
		Matcher coincidencia = patron.matcher(correo);
		
		if(!coincidencia.matches()) {
			throw new IllegalArgumentException("El formato del correo electrónico es inválido.\n");
		}
	}
	
	/**
	 * Método que obtiene una cadena de texto formateada con la información del Cliente.
	 * Es un método implementado de la forma de envío.
	 * @return String que contiene el nombre, apellidos y correo del Cliente.
	 */
	public String dameCadena() {
		//Con StringBuilder podemos crear una cadena.
		StringBuilder salida = new StringBuilder("Nombre y Apellidos: ");
		salida.append(apellidos).append(" ").append(nombre).append("\n");
		salida.append("Correo electrónico: ").append(correo).append("\n");
		
		String cadena = salida.toString();
		
		return cadena;
	}
}



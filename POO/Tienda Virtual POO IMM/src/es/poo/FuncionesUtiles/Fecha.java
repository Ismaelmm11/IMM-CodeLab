package es.poo.FuncionesUtiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Clase utilitario para el manejo de fechas y todas sus validaciones.
 */
public class Fecha {
	
	//FORMATOS DE FECHA ADMITIDOS:
	private static final String FORMATO1 = "d/M/yyyy";
	private static final String FORMATO2 = "dd/M/yyyy";
	private static final String FORMATO3 = "d/MM/yyyy";
	private static final String FORMATO4 = "dd/MM/yyyy";
	
	/**
	 * Método que verifica la fecha que no es del futuro y la devuelve como un LocalDate
	 * @param fechaString String con la fecha a ser verificada.
	 * @return fecha que es LocalDate
	 * @throws IllegalArgumentException Si la fecha no cumple algún requisito
	 */
	public static LocalDate setFecha(String fechaString) throws IllegalArgumentException {
		if(fechaString.isBlank()) {
			throw new NullPointerException("El campo de fecha es obligatorio de rellenar.");
		}
		LocalDate fecha;
		boolean clave = validarFecha(fechaString);
		if(clave == false) {
			throw new IllegalArgumentException("El fecha no es correcta, por favor escriba una válida.\n");
		}
		
		fecha = parsearFecha(fechaString);
		if(fecha == null) {
			throw new IllegalArgumentException("El formato de la fecha no es correcto, por favor use 'dd/mm/yyyy'.");
		}
		clave = esFuturo(fecha);
		if(clave == false) {
			throw new IllegalArgumentException("La fecha ha de ser anterior al día de hoy.\n");
		}
		
		return fecha;
	}
	
	/**
	 * Método que valida una fecha de caducidad y la parsea a LocalDate
	 * @param fechaString String con la fecha de caducidad a parsear.
	 * @return fecha que es un LocalDate.
	 * @throws IllegalArgumentException Si la fecha no es válida o está en el formato incorrecto
	 */
	public static LocalDate setFechaCaducidad(String fechaString) throws IllegalArgumentException {
		LocalDate fecha;
		boolean clave = validarFecha(fechaString);
		if(clave == false) {
			throw new IllegalArgumentException("La fecha no es válida.\n");
		}
		
		fecha = parsearFecha(fechaString);
		if(fecha == null) {
			throw new IllegalArgumentException("El formato de la fecha no es correcto, por favor use 'dd/mm/yyyy'.\n");
		}

		return fecha;
	}
	
	/**
	 * Método que verifica una fecha de caducidad de una Tarjeta y la devuelve.
	 * @param fechaString String con la fecha de caducidad de la tarjeta.
	 * @return fechaString que es un String con fecha de caducidad. 
	 * @throws IllegalArgumentException Si la fecha no es válida.
	 */
	public static String setCaducidadTarjeta(String fechaString) throws IllegalArgumentException{
		boolean clave = validarFecha(fechaString);
		if(clave == false) {
			throw new IllegalArgumentException("El formato de la fecha no es correcto, por favor use 'mm/yy'.\n");
		}
		
		return fechaString;
	}
	
	/**
	 * Método que genera y devuelve una fecha aleatoria entre (01/05/2024 y 15/07/2025). 
	 * @return Un objeto LocalDate con una fecha aleatoriamente generada.
	 */
	public static LocalDate getFechaRandom() {
		LocalDate fechaIni = LocalDate.of(2024, 5, 1);
		LocalDate fechaFin = LocalDate.of(2025, 7, 15);
		
		long diaMin = fechaIni.toEpochDay();
		long diaMax = fechaFin.toEpochDay();
		long diaRandom = ThreadLocalRandom.current().nextLong(diaMin, diaMax + 1);
		
		return LocalDate.ofEpochDay(diaRandom);
	}
	
	/**
	 * Método que parsea un LocalDate a un String con formato con 'DD/MM/YY'.
	 * @param fecha LocalDate con la fecha a ser parseado.
	 * @return fechaFormateada que es un String con la fecha parseada.
	 */
	public static String parseFecha(LocalDate fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
		String fechaformateada = fecha.format(formatter);
		
		return fechaformateada;
	}
	
	/**
	 * Verificar que la fecha es real, según su formato si es 'MM/YY' o 'dd/MM/YYYY'.
	 * @param fecha String con la fecha a ser verificada.
	 * @return True si la fecha es valida, sino devolverá false.
	 */
	private static boolean validarFecha(String fecha) {
		int dia, mes, anio;
		String[] tokens = fecha.split("/");
		
		//Verificar si la fecha tiene 3 partes (dia, mes ,año):
		if(tokens.length == 3) {
			//Parsear los Strings a ints:
			try {
				dia = Integer.parseInt(tokens[0]);
				mes = Integer.parseInt(tokens[1]);
				anio = Integer.parseInt(tokens[2]);
			}
			catch(NumberFormatException e) {
				return false;
			}
			
			//Verificar si el dia, mes y año son válidos:
			if(comprobarAnio(anio) == true && comprobarMes(mes) == true && comprobarDia(dia, mes ,anio) == true) {
				return true;
			}
			else {
				return false;
			}
		}
		else if(tokens.length == 2) {
			try {
				mes = Integer.parseInt(tokens[0]);
				anio = Integer.parseInt(tokens[1]);
			}
			catch(NumberFormatException e) {
				return false;
			}
			
			if(comprobarAnioTarjeta(anio) == true && comprobarMes(mes) == true) {
				return true;
			}
			else if(anio > 42) {
				return false;
			}
			else {
				return false;
			}
		}
		return false;
		
	}
	
	/**
	 * Método que verifica si el año proporcionado es válido.
	 * Permitiendo que esté entre el rango de 0 a 2024.
	 * @param anio int con el año a ser verificado.
	 * @return True si el año es válido, false en otro caso.
	 */
	private static boolean comprobarAnio(int anio)
	{
		if (anio >= 0 && anio < 2042) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Método que verifica si el año proporcionado para la fecha de caducidad de una tarjeta es válido.
	 * Permitiendo que esté entre el rango de 24 a 99.
	 * @param anio int con el año a ser verificado.
	 * @return True si el año es válido, false en otro caso.
	 */
	private static boolean comprobarAnioTarjeta(int anio)
	{
		if (anio >= 24 && anio < 100) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Método para verificar si el mes es válido, es decir si está entre 1 y 12.
	 * @param mes int con el mes a verificar.
	 * @return True si el mes es válido, false en otro caso.
	 */
	private static boolean comprobarMes(int mes)
	{
		if(mes >= 1 && mes <= 12) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Método que verifica si un día dado es válido para un mes y año específicos.
	 * @param dia El día a ser verificado.
	 * @param mes El mes para el cual se verifica el día.
	 * @param anio El año para el cual se verifica el día.
	 * @return true si el día es válido, false de lo contrario.
	 */
	private static boolean comprobarDia(int dia, int mes, int anio) 
	{
		if(dia < 1 || dia >31) {
			return false;
		}
		
		//Obtener el nº de días de un mes.
		int diaMax = getDiasMes(anio, mes);
		//Comprobar que nop supera ese día.
		if(dia <= diaMax) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Método para obtener el nº de días de un mes en específico.
	 * @param anio int que representa el año.
	 * @param mes int que representa el mes.
	 * @return El nº de días que tiene un mes.
	 */
	private static int getDiasMes(int anio, int mes) 
	{
		switch(mes) {
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
				
			case 2:
				//Operador ternario, es como un if-else pero en una sola línea.
				//En este caso si el año es bisiesto devolverá 29, sino devolverá 28.
				return esAnioBisiesto(anio) ? 29 : 28;
			
			default:
				return 31;
		}
	}
	
	/**
	 * Método para verificar si un año es bisiesto.
	 * @param anio int con el año a verificar.
	 * @return True si es bisiesto, false si no lo es.
	 */
	private static boolean esAnioBisiesto(int anio)
	{
		if((anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Método para parsear un String con una fecha a un LocalDate.
	 * @param fechaNac String con la fecha a parsear.
	 * @return Un LocalDate fecha en caso de que la fecha esté en un formato admitido, null en otro caso.
	 */
	private static LocalDate parsearFecha(String fechaNac)
	{
		String[] formatos = {FORMATO1, FORMATO2, FORMATO3, FORMATO4};
		LocalDate fecha;
		
		//Al haber validado ya la fecha, lo único que vamos a buscar es el formato correcto de la fecha.
		//Intentamos parsear la fecha con los diferentes formatos disponibles.
		for(int i = 0; i < formatos.length; i++) {
			try {
				DateTimeFormatter formato = DateTimeFormatter.ofPattern(formatos[i]);
				
				fecha = LocalDate.parse(fechaNac, formato);
				return fecha;
			}
			//Si no es válido probará con otro.
			catch(DateTimeParseException e){
			}
		}
		
		return null;
	}
	
	/**
	 * Método para comprobar si la fecha es una fecha del futuro comparada con la de hoy.
	 * @param fecha
	 * @return False si la fecha es posterior a la de hoy, true en caso contrario.
	 */
	private static boolean esFuturo(LocalDate fecha) 
	{
		LocalDate hoy = LocalDate.now();
		//Ver si la fecha es posterior a la de hoy.
		if(fecha.isAfter(hoy)) {
			return false;
		}
		return true;
	}
}

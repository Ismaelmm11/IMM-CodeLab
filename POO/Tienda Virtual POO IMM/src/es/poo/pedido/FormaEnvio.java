package es.poo.pedido;

import java.util.ArrayList;

import es.poo.main.IMostrable;

public class FormaEnvio implements IMostrable {
	
	private String descripcion, descripcionDet;
	private double coste;
	private int plazo;
public static ArrayList<FormaEnvio> formasEnvio;
	
	/**
	 * Método para obtener la desripción de la forma de envío.
	 * @return descripcion que es un String.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método que devuelve el plazo de entrega de la forma de envío.
	 * @return plazo que es un int que representa los días que va tardar en llegar el pedido.
	 */
	public int getPlazo() {
		return plazo;
	}
	
	/**
	 * Método que devuelve el coste de la forma de envío.
	 * @return coste que es un double que representa el precio del envío.
	 */
	public double getCoste() {
		return coste;
	}

	/**
	 * Método que devuelve una lista con las formas de envío disponibles.
	 * @return formasEnvio que es un ArrayList con las formas de envio.
	 */
	public static ArrayList<FormaEnvio> getFormasEnvio() {
		return formasEnvio;
	}
	/**
	 * Método que rellena un Array de String con la descripción detallada de las formas de envío
	 * que hay en el ArrayLsit formasEnvio.
	 * @param opcionesEnvio Array de String a rellenar.
	 */
	public static void getFormasEnvio(String[] opcionesEnvio) {
		instanciarFormasEnvio();
		
		for(int i = 0; i < formasEnvio.size(); i++) {
			opcionesEnvio[i] = formasEnvio.get(i).dameCadena();
		}
	}
	
	/**
	 * Método que devuelve una forma de envío del ArrayList formasEnvio según la descripción detallada
	 * que se le envía.
	 * @param clave String con la descripción detallada de una forma de envío.
	 * @return formaEnvío que es una instancia de FormaEnvio.
	 */
	public static FormaEnvio getFormaEnvio(String clave) {
		for(FormaEnvio formaEnvio : formasEnvio) {
			if(formaEnvio.dameCadena().equals(clave)) {
				return formaEnvio;
			}
		}
		return null;
	}

	/**
	 * Método para establecer el coste de una forma de envío.
	 * @param coste Es un double con el precio del envío.
	 */
	public void setCoste(double coste) {
		this.coste = coste;
	}
	
	/**
	 * Constructor de la clase FormaEnvio que inicializa una instancia de FormaEnvio e inicializa
	 * sus valores.
	 * @param descripcion String con la descripción de la forma de envío.
	 * @param coste Double con el coste asociado a la forma de envío.
	 * @param plazo Int con el plazo de entrega en días.
	 * @param descripcionDet String con la descripción detallada de la forma de envío.
	 */
	public FormaEnvio(String descripcion, double coste, int plazo, String descripcionDet) {
		this.descripcion = descripcion;
		this.coste = coste;
		this.plazo = plazo;
		this.descripcionDet = descripcionDet;
	}
	
	/**
	 * Método para inicializar el ArrayList formasEnvio. Además instancia 4 formas de envio 
	 * predeterminadas y las añade al ArrayList.
	 */
	private static void instanciarFormasEnvio()
	{
		formasEnvio = new ArrayList<FormaEnvio>();
		
		FormaEnvio metodo1 = new FormaEnvio("Entrega hoy", 9.99, 0, "Entrega hoy(9,99€, mismo día).");
		FormaEnvio metodo2 = new FormaEnvio("Envío 1 día", 5, 1, "Envío 1 día(5€, al siguiente día).");
		FormaEnvio metodo3 = new FormaEnvio("Envío exprés", 3.45, 3, "Envío exprés(3.45€, 3 días laborales).");
		FormaEnvio metodo4 = new FormaEnvio("Envío estándar", 0, 5, "Envío estándar(Gratis, 5 días laborales).");
		
		formasEnvio.add(metodo1);
		formasEnvio.add(metodo2);
		formasEnvio.add(metodo3);
		formasEnvio.add(metodo4);
	}
	
	/**
	 * Método que devuelve la descripcion detallada de la forma de envio.
	 * Es un método implementado de la forma de envío.
	 * @return descripcionDet que es un String con la descripcion detallada.
	 */
	@Override
	public String dameCadena() {
		return descripcionDet;
	}
}

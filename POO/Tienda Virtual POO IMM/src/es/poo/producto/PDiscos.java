package es.poo.producto;

import java.util.ArrayList;

import es.poo.main.IMostrable;

public class PDiscos extends Producto implements IMostrable {
	
	private static final String FOTO_THRI = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\thriller.jpg";
	private static final String FOTO_STIC = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\fingers.jpg";
	private static final String FOTO_CANT = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\cantajuegos.jpg";
	private static final String FOTO_LEMO = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\lemonade.jpg";
	private static final String FOTO_DISC = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\discovery.jpg";
	
	private String artista;
	private static final double IVA = 0.21;
	private tipoGenero genero;
	
	/**
	 * Enumeración que define los posibles géneros de música para los discos.
	 */
	public enum tipoGenero
	{
		INFANTIL, POP, ROCK, DANCE; 
	}
	
	/**
     * Constructor de la clase PDiscos que instancia un objeto de PDiscos e inicializa sus valores.
     *
     * @param codigo       String con el código del disco.
     * @param nombre       String con el nombre del disco.
     * @param precioSinIVA Double con el precio sin IVA del disco.
     * @param descripcion  String con el descripción del disco.
     * @param artista      String con el artista del disco.
     * @param genero       String con el género del disco.
     */
	public PDiscos(String codigo, String nombre, double precioSinIVA, String descripcion, String artista, tipoGenero genero, String rutaFoto) {
		super(codigo, nombre, precioSinIVA, descripcion, rutaFoto);
		this.artista = artista;
		this.genero = genero;
	}
	
	/**
	 * Método que calcula los impuestos aplicados al disco.
	 * @return Impuestos calculados.
	 */
	@Override
	protected double calcularImpuestos() {
		double precio = super.precioNeto * IVA;
		return precio;
	}
	
	/**
	 * Método que inicializa las instancias de discos y las agrega a la lista de productos.
	 * @param productos ArrayList de productos donde se agregarán las instancias de discos.
	 */
	public static void dameInstancias(ArrayList<Producto> productos)
	{
		Producto disco1 = new PDiscos("PD105", "Thriller", 10.2, "THRILLER", "Michael Jackson", tipoGenero.POP, FOTO_THRI);
		Producto disco2 = new PDiscos("PD121", "Sticky Fingers", 15.8, "STICKY FINGERS", "The Rolling Stones", tipoGenero.ROCK, FOTO_STIC);
		Producto disco3 = new PDiscos("PD132", "CantaJuego", 11.3, "CANTAJUEGOS Vol.1", "CantaJuego", tipoGenero.INFANTIL, FOTO_CANT);
		Producto disco4 = new PDiscos("PD154", "Lemonade", 8.26, "LEMONADE", "Beyoncé", tipoGenero.POP, FOTO_LEMO);
		Producto disco5 = new PDiscos("PD173", "Discovery", 12, "DISCOVERY", "Daft Punk", tipoGenero.DANCE, FOTO_DISC);
		
		productos.add(disco1);
		productos.add(disco2);
		productos.add(disco3);
		productos.add(disco4);
		productos.add(disco5);
	}
	
	/**
	 * Método que retorna una representación en cadena del disco.
	 * @return Cadena con información detallada del disco.
	 */
	public String dameCadena() {
		double iva = IVA * 100;
		
		StringBuilder sb = new StringBuilder("Código: ").append(this.codigo).append("\n");
		sb.append("Canción: ").append(this.nombre).append("\n");
		sb.append("Artista: ").append(this.artista).append("\n");
		sb.append("Género: ").append(this.genero).append("\n");
		sb.append("Precio sin IVA: ").append(String.format("%.2f", this.precioNeto)).append("€").append("\n");
		sb.append("IVA: ").append(iva).append("%").append("\n");
		sb.append("Impuestos: ").append(String.format("%.2f", this.impuestos)).append("€").append("\n");
		sb.append("Precio Total: ").append(String.format("%.2f", this.precioFinal)).append("€").append("\n");
	
		return sb.toString();
		
	}
	
}

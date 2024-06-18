package es.poo.producto;

import java.util.ArrayList;

import es.poo.main.IMostrable;

public class PLibros extends Producto implements IMostrable {
	
	private static final String DESCR_HARRY_POTTER = "La mágica entrada de Harry Potter al mundo de la brujería"
													+ "\ny sus inicios en Hogwarts.";
	private static final String DESCR_DON_QUIJOTE = "Las extravagantes aventuras de un caballero soñador y"
													+ "\nsu leal escudero.";
	private static final String DESCR_SENYOR_LUNA = "Por las calles de Madrid se entrecruzarán las vidas"
													+ "\nde un asesino, un adolescente y una latinoamericana.";
	private static final String DESCR_RONCERO = "Momentos grabados en el corazón de una madridista.";
	private static final String DESCR_GOGGINS = "Relata la increíble e inspiradora historia del ex navy seal"
												+ "\nDavid Goggins.";

	private static final String FOTO_HARRY = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\harry potter.jpg";
	private static final String FOTO_QUIJ = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\don quixot.jpg";
	private static final String FOTO_LUNA = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\lluna.jpg";
	private static final String FOTO_RONC = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\real madrid.jpg";
	private static final String FOTO_GOGG = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\goggins.jpg";
	
	private int ISBN;
	private String titulo, autor, editorial;
	private static final double IVA = 0.1;
	
	/**
	 * Constructor de la clase PLibros que instancia un objeto PLibros e inicializa sus valores.
	 * @param codigo      String con el código del libro.
	 * @param nombre      String con el nombre del libro.
	 * @param precioNeto  Double con el precio neto del libro.
	 * @param descripcion String con la descripción del libro.
	 * @param ISBN        Int con el número de ISBN del libro.
	 * @param titulo      String con e tlítulo del libro.
	 * @param autor       String con el autor del libro.
	 * @param editorial   String con la editorial del libro.
	 * */
	public PLibros(String codigo, String nombre,  double precioNeto, String descripcion, int ISBN, String titulo, String autor, String editorial, String rutaFoto) {
		super(codigo, nombre, precioNeto, descripcion, rutaFoto);
		this.ISBN = ISBN;
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
	}
	
	/**
	 * Método que obtiene el título del libro.
	 * @return Título del libro.
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * Método que calcula los impuestos aplicados al libro.
	 * @return Impuestos calculados.
	 */
	@Override
	protected double calcularImpuestos() {
		double precio = super.precioNeto * IVA;
		return precio;
	}
	
	/**
	 * Método que inicializa las instancias de libros y las agrega a la lista de productos.
	 * @param productos ArrayList de productos donde se agregarán las instancias de libros.
	 */
	public static void dameInstancias(ArrayList<Producto> productos) 
	{
		Producto libro1 = new PLibros("PL200", "HARRY POTTER", 15, DESCR_HARRY_POTTER, 978054, "Harry Potter y la piedra filosofal", "J. K. Rowling", "SALAMANDRA", FOTO_HARRY);
		Producto libro2 = new PLibros("PL219", "QUIJOTE",10.3, DESCR_DON_QUIJOTE, 978848, "Don Quixote de la Mancha", "Miguel de Cervantes", "Urbano Manini", FOTO_QUIJ);
		Producto libro3 = new PLibros("PL266", "Sr. LUNA",12.2, DESCR_SENYOR_LUNA, 875621, "L'ultima feina del senyor Luna", "Cesár Mallorquí", "EDEBE", FOTO_LUNA);
		Producto libro4 = new PLibros("PL277", "REAL MADRID",18.9, DESCR_RONCERO, 978841, "Eso no estaba en mi libro del Real Madrid", "Tomás Roncero", "CÓRDOBA", FOTO_RONC);
		Producto libro5 = new PLibros("PL286", "CAN'T HURT ME",25.2, DESCR_GOGGINS, 978154, "CAN'T HURT ME", "David Goggins", "Lioncrest", FOTO_GOGG);
		
		productos.add(libro1);
		productos.add(libro2);
		productos.add(libro3);
		productos.add(libro4);
		productos.add(libro5);
	}
	
	/**
	 * Método que retorna una representación en cadena del libro.
	 * @return Cadena con información detallada del libro.
	 */
	public String dameCadena() {
		double iva = IVA * 100;
		
		StringBuilder sb = new StringBuilder("Código: ").append(this.codigo).append("\n");
		sb.append("Título: ").append(this.titulo).append("\n");
		sb.append("Autor: ").append(this.autor).append("\n");
		sb.append("ISBN: ").append(this.ISBN).append("\n");
		sb.append("Editorial: ").append(this.editorial).append("\n");
		sb.append("Precio sin IVA: ").append(String.format("%.2f", this.precioNeto)).append("€").append("\n");
		sb.append("IVA: ").append(iva).append("%").append("\n");
		sb.append("Impuestos: ").append(String.format("%.2f", this.impuestos)).append("€").append("\n");
		sb.append("Precio Total: ").append(String.format("%.2f", this.precioFinal)).append("€").append("\n");
		sb.append("Descripción: ").append(descripcion);
		
		return sb.toString();
	}
}

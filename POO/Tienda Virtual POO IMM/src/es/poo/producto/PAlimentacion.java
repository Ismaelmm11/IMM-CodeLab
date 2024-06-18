package es.poo.producto;

import java.util.ArrayList;
import java.time.LocalDate;

import es.poo.FuncionesUtiles.Fecha;
import es.poo.main.IMostrable;

public class PAlimentacion extends Producto implements IMostrable {
	
	private static final String FOTO_JAMO = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\jamon.png";
	private static final String FOTO_KIND =	"C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\kinder.png";
	private static final String FOTO_PECH = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\pechuga.png";
	private static final String FOTO_CHOK = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\choco.png";
	private static final String FOTO_COOK = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\cookies.png";
	
	private static final double IVA = 0.04;
	private LocalDate fechaCaducidad;
	
	
	/**
	 * Método que asigna una fecha de caducidad aleatoria para el producto alimenticio.
	 */
	private void setFechaRandom() {
		this.fechaCaducidad = Fecha.getFechaRandom();
	}
	
	/**
	 * Constructor de la clase PAlimentacion que instancia un objeto de Palimnetacion e incia sus valores.
	 * @param codigo         String con el código del producto.
	 * @param nombre         String con el nombre del producto.
	 * @param precioSinIVA   Double con el precio sin incluir el IVA.
	 * @param descripcion    String con el descripción del producto.
	 */

	public PAlimentacion(String codigo, String nombre, double precioSinIVA, String descripcion, String rutaFoto) {
		super(codigo, nombre, precioSinIVA, descripcion, rutaFoto);
		setFechaRandom();
	}
	
	/**
	 * Método que calcula los impuestos aplicados al alimentos.
	 * @return Impuestos calculados.
	 */
	@Override
	protected double calcularImpuestos() {
		double precio = super.precioNeto * IVA;
		return precio;
	}
	
	/**
	 * Método que inicializa las instancias de alimentos y las agrega a la lista de productos.
	 * @param productos ArrayList de productos donde se agregarán las instancias de alimentos.
	 */
	public static void dameInstancia(ArrayList<Producto> productos)
	{
		
		Producto producto1 = new PAlimentacion("PA23", "JAMON", 42.5, "El jamón de bellota 100% ibérico entregado directamente desde \nnuestras bodegas centenarias.", FOTO_JAMO);
		Producto producto2 = new PAlimentacion("PA45", "KINDER", 1.2, "Huevo compuesto por una capa interna de chocolate blanco y \nuna externade chocolate con leche, en su interior te sorprenderá una \ncápsula de plástico.", FOTO_KIND);
		Producto producto3 = new PAlimentacion("PA13", "PECHUGA", 7.8 , "No puede faltar en ninguna dieta equilibrada. Versátil y fácil de cocinar.", FOTO_PECH);
		Producto producto4 = new PAlimentacion("PA01", "CHOKOFLAKES", 9, "Los Choco Flakes de Cuétara son brutales, crujientes, \nsabrosos... Y tan bestiales como su nueva mascota. ¡Te volverás muy \nCRAZY! Con Flakes cada desayuno es una fiesta. Galleta, arroz \ninflado con chocolate.", FOTO_CHOK);
		Producto producto5 = new PAlimentacion("PA98", "COOKIES", 2.37, "Deliciosa galleta de avena con chispas de chocolate, \narándanos y nueces. La combinación perfecta para disfrutar de una \ncookie auténticamente americana.", FOTO_COOK);
		
		productos.add(producto1);
		productos.add(producto2);
		productos.add(producto3);
		productos.add(producto4);
		productos.add(producto5);
	}
	
	/**
	 * Método que retorna una representación en cadena del alimento.
	 * @return Cadena con información detallada del alimento.
	 */
	public String dameCadena() {
		double iva = IVA * 100;
		
		StringBuilder sb = new StringBuilder("Código: ").append(this.codigo).append("\n");
		sb.append("Producto: ").append(this.nombre).append("\n");
		sb.append("Precio sin IVA: ").append(String.format("%.2f", this.precioNeto)).append("€").append("\n");
		sb.append("Porcentaje de IVA: ").append(iva).append("%").append("\n");
		sb.append("Impuestos: ").append(String.format("%.2f", this.impuestos)).append("€").append("\n");
		sb.append("Precio Total: ").append(String.format("%.2f", this.precioFinal)).append("€").append("\n");
		sb.append("Fecha de Caducidad: ").append(this.fechaCaducidad).append("\n");
		sb.append("Descripción: ").append(descripcion);
		
		return sb.toString();
	}
	
}

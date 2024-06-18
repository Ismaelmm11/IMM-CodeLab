package es.poo.producto;

import java.util.ArrayList;

import es.poo.main.IMostrable;

public abstract class Producto implements IMostrable{

		protected double precioNeto, impuestos, precioFinal;
		protected String codigo, nombre, descripcion, rutaFoto;

		private static ArrayList<Producto> productos;
		
		/**
		 * Método abstracto que debe ser implementado por los hijos para calcular los impuestos.
		 * @return double que serán los impuestos calculados.
		 */
		protected abstract double calcularImpuestos();
		
		/**
		 * Método que devuelve la ruta de la foto asignada a cada Producto.
		 * @return rutaFoto que es un String que contiene el path de la foto.
		 */
		public String getRutaFoto() {
			return rutaFoto;
		}
		
		/**
		 * Método para obtener la lista de productos disponibles.
		 * @return productos que es un ArrayList de Productos.
		 */
		public static ArrayList<Producto> getProductos() {
			return productos;
		}
		
		/**
		 * Método para obtener el código del Producto.
		 * @return codigo que es un String que representa el codigo.
		 */
		public String getCodigo() {
			return codigo;
		}
		
		/**
		 * Método que devuelve el nombre del Producto.
		 * @return nombre que es un String que contiene el nombre.
		 */
		public String getNombre() {
			return nombre;
		}
		
		/**
		 * Método que devuelve la descripcion del Producto.
		 * @return descripicion que es un String que tiene la descripción.
		 */
		public String getDescripcion() {
			return descripcion;
		}
		
		/**
		 * Método que devuelve el precio neto del Producto
		 * @return precioNeto que es un double que representa el precio sin impuestos del Producto.
		 */
		public double getPrecioNeto() {
			return precioNeto;
		}
		
		/**
		 * Método que devuelve los impuestos asociados al Producto.
		 * @return impuestos que es un double que representa los impuestos del Producto.
		 */
		public double getImpuestos() {
			return impuestos;
		}
		
		/**
		 * Método que devuelve el importe final(precio neto + impuestos) del Producto.
		 * @return precioFinal que es un double que representa el precio total del Producto.
		 */
		public double getPrecioFinal() {
			return precioFinal;
		}
		
		/**
		 * Método para calcular el precio final del Producto y lo asigna.
		 */
		protected void setPrecioFinal() {
			this.impuestos = calcularImpuestos();
			this.precioFinal = precioNeto + impuestos;
		}
		
		/**
		 * Método para asignar la ruta de la foto de un Producto.
		 * @param rutaFoto String que contiene el path de la foto.
		 */
		protected void setRutaFoto(String rutaFoto) {
			this.rutaFoto = rutaFoto;
		}
		
		/**
		 * Constructor de la clase Producto que inicializa la instancia de Producto e inicializa sus valores.
		 * @param codigo String que es el código único del producto.
		 * @param nombre String que es el nombre del producto.
		 * @param precioNeto Double que es precio neto del producto.
		 * @param descripcion String que es la descripción del producto.
		 */
		protected Producto (String codigo, String nombre, double precioNeto, String descripcion, String rutaFoto) {
			this.codigo = codigo;
			this.nombre = nombre;
			this.precioNeto = precioNeto;
			setPrecioFinal();
			this.descripcion = descripcion;
			this.rutaFoto = rutaFoto;
		}
		
		/**
		 * Método que devuelve un producto del ArrayList según el indice(Alimento, Disco o Libro) 
		 * y la elección(orden del tipo de producto al insertarlo en el ArrayList).
		 * Ej: Indice = 'D' y eleccion = 2 -> Se obtendrá el segundo PDisco introducido en el ArrayList.
		 * @param indice String que representa el tipo de Producto.
		 * @param eleccion Int que indica el orden de inserción en el Array.
		 * @return producto si existe, null si no existe.
		 */
		public static Producto getProducto(String indice, int eleccion) {
			int contador = 1;
			
			for(Producto producto : productos) {
				if(indice.equals(producto.tipoProducto())) {
					if(contador == eleccion) {
						return producto;
					}
					contador++;
				}
			}
			return null;
		}
		
		/**
		 * Inicializa la lista de productos con instancias de las subclases de Producto.
		 * Se encarga de llamar a los métodos estáticos que obtienen instancias de las subclases
		 * (PAlimentacion, PDiscos, PLibros) y las agregan a la lista de productos.
		 */
		public static void inicializarLista()
		{
			productos = new ArrayList<Producto>();
			
			//Obtener instancias de las subclases.
			PAlimentacion.dameInstancia(productos);
			PDiscos.dameInstancias(productos);
			PLibros.dameInstancias(productos);
			
		}
		
		
		
		/**
		 * Método que devuelve un String con toda la información al detalle de un producto.
		 * @param producto Instancia de Proucto de la cual se va a obtener el String.
		 * @return String con la información detallada de un Producto.
		 */
		public String dameCadena(Producto producto) {
			//Lo interesante de este método es que estamos usando el método en el padre, pero 
			//como los hijos tienen ese método sobreescrito se usará el del Padre.
			if(producto instanceof PAlimentacion) {
				return producto.dameCadena();
			}
			else if(producto instanceof PDiscos) {
				return producto.dameCadena();
			}
			else if(producto instanceof PLibros) {
				return producto.dameCadena();
			}
			return null;
		}
		
		/**
		 * Método que devuelve el tipo de un Producto('A' = Alimento, 'D' = Disco y 'L' = Libro).
		 * @return String con el tipo del producto.
		 */
		private String tipoProducto()
		{
			if(this instanceof PAlimentacion) {
				return "A";
			}
			else if(this instanceof PDiscos) {
				return "D";
			}
			else if(this instanceof PLibros) {
				return "L";
			}
			else {
				return null;
			}
		}
		
}

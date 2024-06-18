package es.poo.pedido;

import java.util.ArrayList;

import es.poo.cliente.CRegistrado;
import es.poo.cliente.Cliente;
import es.poo.formaDePago.MetodoPago;
import es.poo.main.IMostrable;
import es.poo.producto.Producto;

public class Pedido implements IMostrable {
	
	private int numPedido;
	private String direccion;
	private double precioNeto, impuestos, precioTotal, costeEnvio;
	
	private MetodoPago formaPago;
	private FormaEnvio formaEnvio;
	private Cliente cliente;
	
	
	private ArrayList<LineaPedido> carrito = new ArrayList<LineaPedido>();
	
	/**
	 * Método que devuelve la dirección asignada al Pedido.
	 * @return direccion que es un String que contiene la direccion.
	 */
	public String getDireccion() {
		return direccion;
	}
	
	/**
	 * Método que devuelve la forma de pago asociada al Pedido.
	 * @return formaPago que es una instancia de la clase MetodoPago.
	 */
	public MetodoPago getFormaPago() {
		return formaPago;
	}
	
	/**
	 * Método que devuelve el nº de pedido que tiene el Pedido.
	 * @return numPedido que es un int que identifica al Pedido.
	 */
	public int getNumPedido() {
		return numPedido;
	}
	
	/**
	 * Método que devuelve el cliente asociado al Pedido.
	 * @return cliente que es una instancia de la clase Cliente.
	 */
	public Cliente getCliente() {
		return cliente;
	}
	
	/**
	 * Método que devuelve la forma de envio asociada al pedido.
	 * @return que es una instancia de la clase FormaEnvio.
	 */
	public FormaEnvio getFormaEnvio(){
		return formaEnvio;
	}
	
	/**
	 * Método que devuelve el carrito que hay dentro del Pedido.
	 * @return carrito que es un ArrayList que contiene las LineaPedido del Pedido.
	 */
	public ArrayList<LineaPedido> getCarrito() {
		return carrito;
	}
	
	/**
	 * Método que devuelve la cantidad total de productos del Pedido.
	 * @return cantidadTotal que es un int que representa la cantidad total de productos.
	 */
	public int getCantidadProductos() {
		int cantidadTotal = 0;
		for(LineaPedido linea : carrito) {
			cantidadTotal += linea.getCantidad();
		}
		return cantidadTotal;
	}
	
	/**
	 * Método que devuelve el precio neto total de todos productos del Pedido.
	 * @return precioNeto es un double que representa el precio neto del Pedido.
	 */
	public double getPrecioNeto() {
		setPrecioNeto();
		return precioNeto;
	}
	
	/**
	 * Método que devuelve el impuestos totales de todos productos del Pedido.
	 * @return impuestos es un double que representa los impuestos del Pedido.
	 */
	public double getImpuestos() {
		setImpuestos();
		return impuestos;
	}
	
	/**
	 * Método que devuelve el precio total del Pedido.
	 * @return precioTotal es un double que representa el precio total del Pedido.
	 */
	public double getPrecioTotal() {
		setPrecioTotal();
		return precioTotal;
	}
	
	/**
	 * Método que devuelve el precio del envío del Pedido.
	 * @return costeEnvio es un double que representa el precio del envío del Pedido.
	 */
	public double getCosteEnvio() {
		setCosteEnvio();
		return costeEnvio;
	}
	
	/**
	 * Método para asignar una dirección de envío al Pedido.
	 * @param direccion String que contiene la dirección de envío.
	 * @throws IllegalArgumentException Si la dirección es nula o si está en blanco.
	 */
	public void setDireccion(String direccion) throws IllegalArgumentException {
		if(direccion.isBlank()) {
			throw new IllegalArgumentException("El campo de dirección es obligatorio rellenarlo.\n");
		}
		
		this.direccion = direccion;
	}
	
	/**
	 * Método para asignar la forma de pago del Pedido.
	 * @param formaPago Instancia de la clase MetodoPago.
	 */
	public void setFormaPago(MetodoPago formaPago) {
		this.formaPago = formaPago;
	}
	
	/**
	 * Método para asignar el cliente asociado al cliente.
	 * @param cliente Instancia de la clase Cliente.
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	/**
	 * Método para asignar la forma envío del Pedido.
	 * @param formaEnvio Instancia de la clase FormaEnvio.
	 */
	public void setFormaEnvio(FormaEnvio formaEnvio) {
		this.formaEnvio = formaEnvio;
	}
	
	/**
	 * Constructor de la clase Pedido que inicializa una instancia de Pedido e inicializa su Cliente.
	 * @param cliente Instancia de Cliente que se asigna al Pedido.
	 */
	public Pedido(Cliente cliente) {
		this.numPedido = setNumPedido();
		this.cliente = cliente;	
	}
	
	/**
	 * Constructor de copia de la clase Pedido
	 * @param pedido Objeto de la clase Pedido que se va a copiar.
	 */
	public Pedido(Pedido pedido) {
		this.numPedido = pedido.numPedido;
		this.direccion = pedido.direccion;
		this.precioNeto = pedido.precioNeto;
		this.impuestos = pedido.impuestos;
		this.costeEnvio = pedido.costeEnvio;
		this.precioTotal = pedido.precioTotal;
		this.formaEnvio = pedido.formaEnvio;
		this.formaPago = pedido.formaPago;
		this.cliente = pedido.cliente;
		
		//Copia del carrito.
		this.carrito = new ArrayList<LineaPedido>();
		for(int i = 0; i < pedido.carrito.size(); i++) {
			//Se copia cada linea de pedido en el pedidoCopia.
			this.carrito.add(pedido.carrito.get(i));
		}
	}
	
	/**
	 * Método para descartar el Pedido, restableciendo sus variables a sus valores predeterminado.
	 *Se usa para cancelar o reiniciar un Pedido.
	 */
	public void descartarPedido() {
		this.numPedido = setNumPedido();
		this.direccion = null;
		this.formaEnvio = null;
		this.formaPago = null;
		
		this.precioNeto = 0;
		this.costeEnvio = 0;
		this.impuestos = 0;
		this.precioTotal = 0;
		
		vaciarCarrito();
	}
	
	/**
	 * Método para verificar si el carrito está vacío.
	 * @return True si el carrito está vacío, false si no lo está.
	 */
	public boolean carritoVacio() {
		if(carrito.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Método para vaciar(eliminar todos los productos) el carrito.
	 */
	public void vaciarCarrito() {
		carrito.clear();
	}
	
	/**
	 * Método que agrega un producto.
	 * Si el producto no existe en el carrito se crea una LineaPedido y se añade al pedido.
	 * Si el pruducto ya existe en el carrito, simplemente se le incrementa la cantidad a su LineaPedido. 
	 * @param producto
	 * @param cantidad
	 */
	public void addProducto(Producto producto, int cantidad) {
		//Busca una LineaPedido que contenga el producto en el carrito. 
		LineaPedido lineaExistente = buscarLinea(producto);
		
		//Si lo encuentra incrementa la cantidad.
		if(lineaExistente != null) {
			lineaExistente.addCantidad(cantidad);
		}
		//En otro caso crea una nueva LineaPedido y se añade al carrito.
		else {
			LineaPedido lineaNueva = new LineaPedido(producto, cantidad);
			carrito.add(lineaNueva);
		}
	}
	
	/**
	 * Método que devuelve una cadena con la información detallada del carrito(codigo, nombre, precio neto,
	 * impuestos, cantidad y precio final) y además añade el precio total del pedido.
	 * @return cadena con la información del pedido.
	 */
	public String dameCadena() {
	    String formatCabecera = "%-10s %-20s %-20s %-20s %-20s %-1s";
	    String formatoImporte = "%-55s %-16s %-1.2f %-1s";

	    StringBuilder sb = new StringBuilder();
	    sb.append(String.format(formatCabecera, "Código", "Precio Neto(€)", "Impuestos(€)", "Cantidad", "Precio Final(€)", "Producto\n"));
	    sb.append("----------------------------------------------------------------------------------------------------------------------------------\n");
	    for (LineaPedido linea : carrito) {
	        Producto producto = linea.getProducto();
	        sb.append(String.format("%-25s %-25.2f %-23.2f %-30d %-20.2f %-35s\n", producto.getCodigo(),
	                producto.getPrecioNeto(), producto.getImpuestos(),
	                linea.getCantidad(), linea.getPrecioTotal(), producto.getNombre()));
	    }
	    sb.append("    ----------------------------------------------------------------------------------------------------------------------------------\n");
	    sb.append(String.format(formatoImporte, " ", "IMPORTE TOTAL:", precioTotal, "€"));

	    return sb.toString();
	}
	
	/**
	 * Método para buscar una LineaPedido en el carrito, según el producto proporcionado.
	 * @param producto Instancia de la clase Producto que se va usar para buscar la linea.
	 * @return LineaPedido si la encuentra o null si no la encuentra.
	 */
	private LineaPedido buscarLinea(Producto producto) {
		//Si el carrito está vacío no hace falta ni que busque.
		if(carritoVacio()) {
			return null;
		}
		for(LineaPedido linea : carrito) {
			if(linea.getProducto().equals(producto)) {
				return linea;
			}
		}
		return null;
	}

	/**
	 * Método para calcular y asignar el precio neto total del Pedido. Se logra realizando un sumatorio
	 * del precio neto de todas las LineaPedido del carrito.
	 */
	private void setPrecioNeto() {
		if(carritoVacio()) {
			precioNeto = 0;
			return;
		}
		precioNeto = 0;
		for(LineaPedido linea : carrito) {
			precioNeto += linea.getPrecioNeto();
		}
	}
	
	
	/**
	 * Método para calcular y asignar los impuestos totales del Pedido. Se logra realizando un sumatorio
	 * de los impuestos de todas las LineaPedido del carrito.
	 */
	private void setImpuestos() {
		if(carritoVacio()) {
			impuestos = 0;
			return;
		}
		impuestos = 0;
		for(LineaPedido linea : carrito) {
			impuestos += linea.getImpuestos();
		}
	}
	
	
	/**
	 * Método para calcular y asignar el precio total del Pedido. Se logra realizando un sumatorio
	 * del precio total de todas las LineaPedido del carrito.
	 */
	private void setPrecioTotal() {
		if(carritoVacio()) {
			precioTotal = 0;
			return;
		}
		
		precioTotal = 0;
		for(LineaPedido linea : carrito) {
			precioTotal += linea.getPrecioTotal();
		}
		//Así este método sirve para definir el importeFinal antes y después de elegir el envío. 
		if(formaEnvio != null) {
			precioTotal += costeEnvio;
		}	
	}
	
	/**
	 * Método para asignar el coste de envío del Pedido.
	 */
	private void setCosteEnvio() {
		costeEnvio = formaEnvio.getCoste();
	}
	
	/**
	 * Método que genera un número de pedido único.
	 * @return num que es el int que representa el nº de pedido.
	 */
	private int setNumPedido() {
		boolean clave = true;
		int num = 0;
		
		while(clave == true) {
			num = generarNumAleatorio();
			clave = numRepetido(num);
		}
		return num;
	}
	
	/**
	 * Método que genera un número aleatorio de 3 números con un rango desde 100 hasta el 999.
	 * @return num que es un int aleatorio.
	 */
	private int generarNumAleatorio() {
		int num = (int)(Math.random() * (999 - 100 +1) + 100);
		return num;
	}
	
	
	//Una pena que no se pueda aprovechar al máximo este método porque la persistencia del Pedido
	//en el cliente no funciona.
	/**
	 * Método que verifica si el nº de pedido ya ha sido generado anteriormente en otro pedido.
	 * @param num Es un int que representa el nº de pedido.
	 * @return True si el nº de Pedido ha sido generado anteriormente, falso en caso de que nunca haya sido generado. 
	 */
	private boolean numRepetido(int num) {
		
		ArrayList<CRegistrado> clientes = CRegistrado.getClientesVip();
		
		
		for(int i = 0; i < clientes.size(); i++) {
			CRegistrado cliente = clientes.get(i);
			ArrayList<Pedido> pedidos = cliente.getListaPedidos();
			if(pedidos == null) {
				continue;
			}
			for(int j = 0; j < pedidos.size(); j++) {
				Pedido pedido = pedidos.get(j);
				int numAux = pedido.getNumPedido();
				if(num == numAux) {
					return true;
				}
			}
		}
		
		return false;
	}

}

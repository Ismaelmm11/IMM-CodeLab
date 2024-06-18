package es.poo.cliente;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import es.poo.pedido.Pedido;
import es.poo.formaDePago.*;
import es.poo.main.IMostrable;
import es.poo.FuncionesUtiles.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CRegistrado extends Cliente implements IMostrable {
	
	private static final String FOTO_DEF = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\perfil_defecto.png";
	private static final String RUTA_JSON = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\clientes.txt";
	
	
	private String password, rutaFoto;
	private LocalDate fechaNac;
	private ArrayList<Pedido> listaPedidos;
	private ArrayList<MetodoPago> formasPago;
	private ArrayList<String> direcciones;

	private static ArrayList<CRegistrado> clientesVip;

	/**
	 * Método que devuelve la ruta de la foto de perfil de un CRegistrado.
	 * @return rutaFoto que es un String con el path de la foto.
	 */
	public String getRutaFoto() {
		return rutaFoto;
	}

	/**
	 * Método que devuelve la lista de pedidos realizados por un CRegistrado.
	 * @return listaPedidos que es un ArrayList de pedidos realizados por
	 *         CRegistrado.
	 */
	public ArrayList<Pedido> getListaPedidos() {
		return listaPedidos;
	}
	
	/**
	 * Método que devuelve la lista de formas de pago usadas por un CRegistrado.
	 * @return listaPedidos que es un ArrayList de formas de pago usadas por
	 *         CRegistrado.
	 */
	public ArrayList<MetodoPago> getFormasPago() {
		return formasPago;
	}
	
	/**
	 * Método que devuelve la lista de direcciones asociadas al CRegistrado.
	 * @return listaPedidos que es un ArrayList de direcciones asociadas al CRegistrado.
	 */
	public ArrayList<String> getDirecciones() {
		return direcciones;
	}

	/**
	 * Método que devuelve la fecha de nacimiento de un CRegistrado.
	 * @return fechaNac que es un LocalDate con la fecha de nacimiento.
	 */
	public LocalDate getFechaNac() {
		return fechaNac;
	}

	/**
	 * Método que devuelve la fecha de nacimiento en forma de String de un
	 * CRegistrado.
	 * @return fecha que es un String con la fecha de nacimiento.
	 */
	public String getFechaNacString() {
		String fecha = Fecha.parseFecha(fechaNac);
		return fecha;
	}
	
	/**
	 * Método que devuelve la lista de clientesVip(clientes registrados).
	 * @return La lista de clientesVip(registrados).
	 */
	public static ArrayList<CRegistrado> getClientesVip() {
		return clientesVip;
	}

	/**
	 * Método para asignar la ruta de la foto de perfil
	 * @param rutaFoto que es el String con el path a asignar.
	 */
	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

	/**
	 * Método para establecer la fecha de nacimiento del CRegistrado
	 * @param fechaNac String con la fecha que será parseada a LocalDate.
	 * @throws IllegalArgumentException Si la fecha no cumple con los requisitos.
	 * @throws NullPointerException     Si la fecha es nula.
	 */
	public void setFechaNac(String fechaNac) throws IllegalArgumentException, NullPointerException {
		this.fechaNac = Fecha.setFecha(fechaNac);
	}

	/**
	 * Método que hashea y establece la contraseña del CRegistrado, siempre que sea
	 * válida.
	 * @param password String con la contraseña a asignar.
	 * @throws IllegalArgumentException Si la contraseña no cumple con los
	 *                                  requisitos mínimos.
	 */
	public void setPassword(String password) throws IllegalArgumentException {
		validarPassword(password);
		String passHasheado = aplicarHash(password);
		this.password = passHasheado;
	}
	
	/**
	 * Método que asigna la lista de pedidos del CRegistrado.
	 * @param listaPedidos ArrayLsit de pedidos.
	 */
	public void setListaPedidos(ArrayList<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

	/**
	 * Método que asigna la lista de formas de pago del CRegistrado.
	 * @param listaPedidos ArrayLsit de MetodoPago.
	 */
	public void setFormasPago(ArrayList<MetodoPago> formasPago) {
		this.formasPago = formasPago;
	}

	/**
	 * Método que asigna la lista de direcciones del CRegistrado.
	 * @param listaPedidos ArrayLsit de pedidos.
	 */
	public void setDirecciones(ArrayList<String> direcciones) {
		this.direcciones = direcciones;
	}

	/**
	 * Constructor de la clase CRegistrado que inicializa un objeto de CRegistrado.
	 * Llama al constructor del padre, estable una ruta de foto por defecto e
	 * inicializa los ArrayList vacíos que contendrán la lista de direcciones,
	 * formas de pago y los pedidos.
	 */
	public CRegistrado() {
		super();
		// Establecer como foto de perfil una por defecto.
		rutaFoto = FOTO_DEF;

		// Inicializar los ArrayList.
		iniciarListas();
	}
	
	/**
	 * Método que realiza un resumen hash a la contraseña usando el algoritmo SHA-256.
	 * @param password Es el String con la contraseña a hashear.
	 * @return El resumen hash de la contraseña.
	 */
	private String aplicarHash(String password) {
		try {
			//Obtenemos una instancia del algoritmo SHA-256
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			//Convertimos la contraseña en un array de bytes y realizamos el hash de esos bytes.
			byte[] resumenHash = sha256.digest(password.getBytes());
			//Usamos StringBuilder para juntar todos los bytes en una sola cadena.
			StringBuilder cadenaHexadecimal = new StringBuilder();
			//Convertir cada byte en una cadena hexadecimal y la añadimos al StringBuilder.
			for (byte b : resumenHash) {
				String hex = Integer.toHexString(0xff & b);
				//Aseguramos que cada byte siempre se convierta en 2 carácteres,
				//por eso se añade un 0 en caso de que solo salga un carácter del byte.
				if (hex.length() == 1) {
					cadenaHexadecimal.append('0');
				}
				cadenaHexadecimal.append(hex);
			}

			//Devolvemos la cadena hexadecimal en formato de String.
			return cadenaHexadecimal.toString();
		//En caso de que el algoritmo especificado no exista se devolverá null.
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	/**
	 * Método que añade un pedido a la lista de Pedidos, evitando que se repitan pedidos.
	 * @param pedido Objeto Pedido a agregar.
	 */
	public void addPedido(Pedido pedido) {
		boolean clave = false;
		for (int i = 0; (i < listaPedidos.size()) && clave == false; i++) {
			Pedido pedidoAux = listaPedidos.get(i);
			if (pedidoAux.equals(pedido)) {
				clave = true;
			}
		}
		if (clave == false) {
			listaPedidos.add(pedido);
		}
	}

	/**
	 * Método que añade un Método de Pago a la lista de formas de pago.
	 * @param formaPago Objeto MetodoPago a agregar.
	 */
	public void addFormasPago(MetodoPago formaPago) {
		formasPago.add(formaPago);
	}

	/**
	 * Método que añade una dirección a la lista de direcciones.
	 * @param pedido String con la dirección a agregar.
	 */
	public void addDirecciones(String direccion) {
		direcciones.add(direccion);
	}
	
	/**
	 * Método con el que se realiza el registro de los Clientes. Básicamente registra(añade) el CRegistrado
	 * al array estático de Clientes Vip. Evitamos que se dupliquen comparando los correos electrónicos.
	 * @param cliente Objeto CRegistrado a añadir al Array.
	 * @throws IllegalArgumentException Si ya existe un CRegistrado con el mismo correo en la lista.
	 */
	public static void addCliente(CRegistrado cliente) throws IllegalArgumentException {
		boolean clave = false;
		//Recorremos la lista de clientesVip comparando los correos.
		for (int i = 0; i < clientesVip.size() && clave == false; i++) {
			CRegistrado clienteAux = clientesVip.get(i);
			clave = sonCorreosIguales(cliente, clienteAux);
		}
		
		if (clave == false) {
			clientesVip.add(cliente);
		} else {
			throw new IllegalArgumentException("Ya existe un usuario con ese correo.");
		}
	}
	
	/**
	 * Método que compara los correos de 2 objetos CRegistrado.
	 * @param cliente1 1º CRegistrado a comparar.
	 * @param cliente2 2ª CRegistrado a comparar.
	 * @return True si los correos son iguales, false en caso contrario.
	 */
	private static boolean sonCorreosIguales(CRegistrado cliente1, CRegistrado cliente2) {
		if (cliente1.getCorreo().equals(cliente2.getCorreo())) {
			return true;
		}
		return false;
	}

	public void cambiarPassword(String newPassword, String oldPassword)
			throws IllegalArgumentException, NullPointerException {
		if (newPassword.isBlank()) {
			throw new NullPointerException("El campo de la contraseña nueva es obligatorio de rellenar.");
		} else if (oldPassword.isBlank()) {
			throw new NullPointerException("El campo de la contraseña antigua es obligatorio de rellenar.");
		}
		String passHasheado = aplicarHash(oldPassword);
		if (!(passHasheado.equals(this.password))) {
			throw new IllegalArgumentException("La contraseña antigua no coincide con la actual.");
		}
		validarPassword(newPassword);
		String newPassHasheado = aplicarHash(newPassword);
		if (newPassHasheado.equals(this.password)) {
			throw new IllegalArgumentException("La nueva contraseña no puede ser igual que la anterior.");
		}
		this.password = newPassHasheado;
	}

	/**
	 * Método para iniciar sesión como un CRegistrado de la lista. Compara el correo y la contraseña
	 * para asegurar un inicio de sesión correcto.
	 * @param correo String con el correo del CRegistrado.
	 * @param password String con la contraseña del CRegistrado.
	 * @return Una instancia de CRegistrado de la lista si el inicio de sesión ha sido correcto o null en caso contrario.
	 */
	public static CRegistrado inicioSesión(String correo, String password) {
		for (int i = 0; i < clientesVip.size(); i++) {
			CRegistrado cliente = clientesVip.get(i);
			if (cliente.verificarCredenciales(correo, password)) {
				return cliente;
			}
		}
		return null;
	}

	/**
	 * Método para validar las credenciales del CRegistrado comparando el correo y la contraseña.
	 * @param correo String con el correo a verificar.
	 * @param password String con la contraseña a verificar.
	 * @return True si las credenciales coinciden con las del CRegistrado o null en caso contrario.
	 */
	private boolean verificarCredenciales(String correo, String password) {
		String passHasheado = aplicarHash(password);
		if (correo.equals(this.correo) && passHasheado.equals(this.password)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Método para validar si la contraseña cumple con los requisitos mínimos.
	 * @param password String con la contraseña a validar.
	 * @throws IllegalArgumentException Si la contraseña no cumple con los requisitos.
	 * @throws NullPointerException Si la contraseña es nula o está en blanco.
	 */
	private void validarPassword(String password) throws IllegalArgumentException, NullPointerException {
		if (password.isBlank()) {
			throw new NullPointerException("El campo de contraseña es obligatorio de rellenar.");
		} else if (password.length() < 9 || !tiene_Numero(password) || !tiene_Simbolo(password)
				|| !tiene_Mayus(password) || !tiene_Minus(password)) {
			throw new IllegalArgumentException("La contraseña no cumple los requisitos.\n"
					+ "Debe tener al menos 1 mayúscula, 1 minúscula, 1 símbolo,"
					+ " 1 número y debe superar los 8 carácteres de longitud.\n");
		}
	}

	// Las siguientes funciones verificarán si la contraseña contiene al menos 1
	// número, 1 símbolo, 1 mayúsucula y 1 minúscula:
	
	/**
	 * Verifica si la contraseña contiene al menos un dígito.
	 *
	 * @param password La contraseña a verificar.
	 * @return True si la contraseña contiene al menos un dígito, false en caso contrario.
	 */
	private boolean tiene_Numero(String password) {
		// Esta expresión regular verifica si hay algún número en la cadena.
		return password.matches(".*\\d.*");
	}
	
	/**
	 * Verifica si la contraseña contiene al menos un símbolo que no sea alfanumérico.
	 *
	 * @param password La contraseña a verificar.
	 * @return True si la contraseña contiene al menos un símbolo, false en caso contrario.
	 */
	private boolean tiene_Simbolo(String password) {
		// Esta verifica si la cadena contiene algún símbolo.
		return password.matches(".*[^a-zA-Z0-9].*");
	}

	/**
	 * Verifica si la contraseña contiene al menos una letra mayúscula.
	 *
	 * @param password La contraseña a verificar.
	 * @return True si la contraseña contiene al menos una letra mayúscula, false en caso contrario.
	 */
	private boolean tiene_Mayus(String password) {
		// Verificará si hay al menos una máyuscula.
		return password.matches(".*[A-Z].*");
	}
	
	/**
	 * Verifica si la contraseña contiene al menos una letra minúscula.
	 *
	 * @param password La contraseña a verificar.
	 * @return True si la contraseña contiene al menos una letra minúscula, false en caso contrario.
	 */
	private boolean tiene_Minus(String password) {
		// Verifica si la cadena tiene una minúscula.
		return password.matches(".*[a-z].*");
	}
	
	//Los 2 siguientes métodos son para permitir la persistencia en los cambios del perfil, ya que si se intenta
	//persistir estas listas el programa deja de funcionar.
	
	/**
	 * Método para inicializar listas.
	 */
	public void iniciarListas() {
		direcciones = new ArrayList<String>();
		listaPedidos = new ArrayList<Pedido>();
		formasPago = new ArrayList<MetodoPago>();
	}
	
	/**
	 * Método para settear las listas a null.
	 */
	public void setNull() {
		listaPedidos = null;
		formasPago = null;
		direcciones = null;
	}

	@Override
	/**
	 * Método que devuelve una cadena con la información del cliente incluyendo su fecha de nacimiento.
	 * Es un método implementado de la forma de envío.
	 * @return cadena que es un String con la información del CRegistrado.
	 */
	public String dameCadena() {
		String cadena = super.dameCadena();
		StringBuilder salida = new StringBuilder(cadena);
		salida.append("Fecha de nacimiento: ").append(fechaNac).append("\n");

		cadena = salida.toString();

		return cadena;
	}
	
	/**
	 * Método para guardar la lista de clientesVip en archivo en formato JSON.
	 */
	public static void save() {
		//Configuramos JSON con un adaptador personalizado para que persista LocalDate y serialice los nulos.
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).serializeNulls()
				.create();

		//Convertir la lista de clientesVip en una cadena en formato JSON.
		String json = gson.toJson(clientesVip);

		try {
			//Escribimos el JSON en el archivo especificado.
			FileWriter fileWriter = new FileWriter(RUTA_JSON);
			fileWriter.write(json);
			fileWriter.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("El archivo no ha podido ser guardado.");
		}
	}

	/**
	 * Carga la lista desde un archivo en formato JSON.
	 */
	public static void load() {
		// Configuración de Gson con un adaptador personalizado para LocalDate
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

		String json = null;

		try {
			//Leer el contenido del fichero.
			FileReader fileReader = new FileReader(RUTA_JSON);
			StringBuilder sb = new StringBuilder();
			int character; 

			while ((character = fileReader.read()) != -1) {
				sb.append((char) character); 
			}
			fileReader.close();
			
			//Convertir el contenido del fichero en un archivo JSON.
			json = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Definirl el tipo de lista para Gson.
		Type type = new TypeToken<ArrayList<CRegistrado>>() {
		}.getType();
		
		//Deserializar JSON.
		clientesVip = gson.fromJson(json, type);
		if(clientesVip == null) {
			clientesVip = new ArrayList<CRegistrado>();
		}
	}
}

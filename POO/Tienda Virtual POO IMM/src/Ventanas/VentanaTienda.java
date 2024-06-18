package Ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import static javax.swing.JOptionPane.showMessageDialog;

import es.poo.cliente.*;
import es.poo.pedido.Pedido;
import es.poo.producto.*;

@SuppressWarnings("serial")
public class VentanaTienda extends JFrame implements CambioPanelListener {
	
	private final static String FOTO_COMPRA = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\barcelona_escudo.png";
	
	private String panelActual;
	
	private Color moradoFuerte = new Color(255, 0, 255).brighter();
	private Color azulClaroNeon = new Color(0, 191, 255).brighter();
	private Color rosaClarito = new Color(255, 193, 211);
	private Color beigeSuave = new Color(255, 255, 240);
	
	public static VentanaTienda ventana;
	private VentanaPedido ventanaPed;
	
	private Pedido pedido;
	
	private JPanel panelMain, panelContenedor, panelEleccion, panelAlimentos, panelDiscos, panelLibros, panelPerfil;
	
	private CardLayout cardLayout;
	
	public VentanaTienda(Cliente cliente, JFrame ventana) {
		//Inicializamos la ventana con su propio título.
		super("TIENDA POO");
		setSize(1000, 625);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		VentanaTienda.ventana = this;
		
		//Inicializamos la lista de productos.
		Producto.inicializarLista();
		
		//Asignamos el cliente al pedido.
		pedido = new Pedido(cliente);
		
		//Colocamos la barra horizontal arriba de todo.
		setJMenuBar(MenuBarFactory.iniciarMenuBar(pedido.getCliente()));
		
		//Inicializamos todos los paneles.
		inicializarVentanaMain();		//El panel principal.
		inicializarMenuEleccion();		//El menú para escoger el tipo de producto a comprar.
		inicializarMenuAlimentos("A");	//El menú de productos alimentos.
		inicializarMenuDiscos("D");		//El menú de productos discos.
		inicializarMenuLibros("L");		//El menú de productos libros.
		
		//Inicializamos el panel contenedor y el cardlayout para movernos entre paneles.
		panelContenedor = new JPanel();
		cardLayout = new CardLayout();
		panelContenedor.setLayout(cardLayout);
		
		//Asignamos un nombre a cada panel.
		//Es importante porque así tenemos una referencia desde que panel se ha llamado al panelPerfil,
		//para así cuando queramos volver volveremos a ese panel.
		panelMain.setName("panelMain");
		panelEleccion.setName("panelEleccion");
		panelAlimentos.setName("panelAlimento");
		panelDiscos.setName("panelDisco");
		panelLibros.setName("panelLibro");
		
		//Añadimos todos los paneles al panelContenedor.
		panelContenedor.add(panelMain, "panelMain");
		panelContenedor.add(panelEleccion, "panelEleccion");
		panelContenedor.add(panelAlimentos, "panelAlimento");
		panelContenedor.add(panelDiscos, "panelDisco");
		panelContenedor.add(panelLibros, "panelLibro");
		
		//Solamente si el cliente es un cliente registrado tendrá acceso al panel de Perfil.
		if(pedido.getCliente() instanceof CRegistrado) {
			CRegistrado clienteVip = (CRegistrado) pedido.getCliente();
			
			panelPerfil = MenuBarFactory.iniciarPanelPerfil(clienteVip);
			panelContenedor.add(panelPerfil, "panelPerfil");
			panelPerfil.setName("panelPerfil");
		}
		
		//Añadir el panelContenedor al JFrame.
		getContentPane().add(panelContenedor);
		
		//Asignamos esta ventana como un listener de la interfaz CambioPanelListener.
		//De manera que podrá realizar acciones si ocurre algún evento en el Menú que hay en la parte superior.
		MenuBarFactory.setCambioPanelListener(this);
		
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
	private void inicializarVentanaMain() {
		//Bordes:
		Border borde = BorderFactory.createLineBorder(Color.BLACK, 3);
		Border bordeRecuadro = BorderFactory.createLineBorder(moradoFuerte, 5);
		
		//Inicializamos el panel principal
		panelMain = new JPanel();
		panelMain.setLayout(null);
		panelMain.setBackground(rosaClarito);
		
		
		JPanel panelRecuadro = new JPanel();
		panelRecuadro.setLayout(null);
		panelRecuadro.setBounds(345, 80, 300, 350);
		panelRecuadro.setBackground(beigeSuave);
		panelRecuadro.setBorder(bordeRecuadro);
		
        //----------------------------------------------------------------------------------------------------
		//Configuración de los botones.
		
		//Botón para dirigirnos al menú de los productos.
        JButton btnProd = new JButton("Ver Productos");
        btnProd.setBounds(55, 75, 200, 35);
        btnProd.setBackground(azulClaroNeon);
        btnProd.setBorder(borde);
        btnProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cardLayout.show(panelContenedor, "panelEleccion");
            }
        });
        
        //Botón para dirigirnos al panel del Pedido.
        JButton btnCarr = new JButton("Ver Carrito");
        btnCarr.setBounds(55, 130, 200, 35);
        btnCarr.setBackground(azulClaroNeon);
        btnCarr.setBorder(borde);
        btnCarr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
    			ventanaPed = new VentanaPedido(pedido, ventana);
    	       	ventanaPed.setLocation(ventana.getLocation());
    	       	ventana.setVisible(false);
            }
        });
        
        //Botón para salir del programa.
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(55, 185, 200, 35);
        btnSalir.setBackground(azulClaroNeon);
        btnSalir.setBorder(borde);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showMessageDialog(panelContenedor, "MUCHISIMAS GRACIAS POR VISITAR NUESTRA TIENDA.\n"
            			+ "ESPERAMOS VERLE DE NUEVO POR AQUÍ.");
            	
            	System.exit(0);
            }
        });
        
        //Añadir componentes al panelRecuadro.
        panelRecuadro.add(btnProd);
        panelRecuadro.add(btnCarr);
        panelRecuadro.add(btnSalir);
        
          
        panelMain.add(panelRecuadro);
        
	}
	
	private void inicializarMenuEleccion() {
		//Bordes:
		Border borde = BorderFactory.createLineBorder(moradoFuerte, 5);
		
		//Inicializamos el panel para escoger el tipo de producto.
		panelEleccion = new JPanel();
		panelEleccion.setLayout(null);
		panelEleccion.setBackground(rosaClarito);
		
		JPanel panelRecuadro = new JPanel();
		panelRecuadro.setLayout(null);
		panelRecuadro.setBounds(345, 80, 300, 350);
		panelRecuadro.setBackground(beigeSuave);
		panelRecuadro.setBorder(borde);
		
        //Etiqueta:
        JLabel etiqueta = new JLabel("Seleccione una opción:");
        etiqueta.setBounds(55, 50, 200, 20);
        panelRecuadro.add(etiqueta);
        
        //------------------------------------------------------------------------------------------------
        //Configuración de botones:
        
        //Botón para dirigirnos al panel de alimentos.
        JButton btnAlimentos = new JButton("Alimentación");
        btnAlimentos.setBounds(55, 75, 200, 35);
        btnAlimentos.setBackground(new Color(50, 205, 50));
        btnAlimentos.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        btnAlimentos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cardLayout.show(panelContenedor, "panelAlimento");
            }
        });
        
        //Botón para dirigirnos al panel de los Libros.
        JButton btnLibros = new JButton("Libros");
        btnLibros.setBounds(55, 130, 200, 35);
        btnLibros.setBackground(new Color(120, 120, 255));
        btnLibros.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        btnLibros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cardLayout.show(panelContenedor, "panelLibro");
            }
        });
        
        //Botón para dirigirnos al panel de los Discos.
        JButton btnDiscos = new JButton("Discos");
        btnDiscos.setBounds(55, 185, 200, 35);
        btnDiscos.setBackground(Color.LIGHT_GRAY);
        btnDiscos.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        btnDiscos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cardLayout.show(panelContenedor, "panelDisco");
            }
        });
        
        //Botón para volver al panel principal.
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(55, 240, 200, 35);
        btnVolver.setBackground(Color.RED);
        btnVolver.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContenedor, "panelMain");
            }
        });
        
        //Añadir componentes al panelRecuadro.
        panelRecuadro.add(btnAlimentos);
        panelRecuadro.add(btnLibros);
        panelRecuadro.add(btnDiscos);
        panelRecuadro.add(btnVolver);
                        
        panelEleccion.add(panelRecuadro);
	}
	
	private void inicializarMenuAlimentos(String indice) {
		
		//Inicializamos el panel de los Alimentos con el siguiente diseño que van a compartir los 3 paneles(Alimentos, Discos y Libros):
		//Van ser 2 filas las cuales contienen 3 columnas, por lo tanto va a haber 6 celdas.
		//Entre cada celda hay unos 10 pixeles de separación.
		panelAlimentos = new JPanel(new GridLayout(2, 3, 10, 10));
		inicializarMenuProducto(indice, panelAlimentos);
		
		//Botón para volver al panel de eleccion.
		//No hay que asignarle ni dimensiones ni tamaño porque de eso se encarga el GridLayout.
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 105, 180).brighter());
        btnVolver.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContenedor, "panelEleccion");
            }
        });
        
        //Añadir botón al panel.
        panelAlimentos.add(btnVolver);	
	}
	
	private void inicializarMenuLibros(String indice) {
		
		//Inicializar el panel de libros, el cual va a tener el mismo diseño que el de alimentos y discos..
		panelLibros = new JPanel(new GridLayout(2, 3, 10, 10));
		inicializarMenuProducto(indice, panelLibros);
		
		//Botón para volver al panel de eleccion.
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 105, 180).brighter());
        btnVolver.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContenedor, "panelEleccion");
            }
        });
        //Añadir botón al panel.
        panelLibros.add(btnVolver);	
	}
	
	private void inicializarMenuDiscos(String indice) {
	
		//Inicializar el panel de Discos, el cual va a tener el mismo diseño que el de alimentos y libro..
		panelDiscos = new JPanel(new GridLayout(2, 3, 10, 10));
		inicializarMenuProducto(indice, panelDiscos);
		
		//Botón para volver al panel de elección.
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(255, 105, 180).brighter());
        btnVolver.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContenedor, "panelEleccion");
            }
        });
        
        //Añadir el botón al panel.
        panelDiscos.add(btnVolver);
	
	}
	
	private void inicializarMenuProducto(String indice, JPanel panelProductos) {
		
		ImageIcon icono = new ImageIcon(FOTO_COMPRA);
		
		panelProductos.setBackground(rosaClarito);
		
		//Bucle que va a iterar sobre todos los productos disponibles.
		for(int i = 0; i <= 5; i++) {
			//Aquí solo se obtendrán productos dependiendo del panel que haya llamado al método.
			//Por ejemplo si lo llama el panel Alimentos devolverá solo productos que sean alimentos.
			Producto producto = Producto.getProducto(indice, i);
			
			//Si devuelve null se omite el resto del bucle.
			if(producto == null) {
				continue;
			}
			
			//Creamos el panel donde se colocarán distintos paneles con la información del objeto.
			//El diseño BorderLayout nos va a servir ya que si lo añadimos sin especificar colocará el componente en el centro.
			JPanel casillaProducto = new JPanel(new BorderLayout());
            casillaProducto.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            
            //Panel que organiza el resto de paneles.
            JPanel panelProductoInfo = new JPanel(new BorderLayout());
            panelProductoInfo.setBackground(new Color(245, 245, 220));
            
            //Panel que tendrá la imagen.
            JPanel panelImagen = new JPanel(new BorderLayout());
            panelImagen.setBounds(5, 0, 160, 280);
            //Hacemos que el panel sea transparente, así muestra el color de fondo del panelProductoInfo.
            panelImagen.setOpaque(false);
            
            //Panel con la información del objeto(Nombre y Precio).
            JPanel panelInfo = new JPanel();
            panelInfo.setLayout(null);
            panelInfo.setBounds(150, 0, 160, 250);
            panelInfo.setOpaque(false);
            
            //Panel con el nombre, se usa este panel para que el texto no llegue al borde de panelProductoInfo.
			JPanel panelNombre = new JPanel();
			panelNombre.setLayout(null);
			panelNombre.setBounds(35, 40, 125, 100);
			panelNombre.setOpaque(false);
			
			//Panel donde se colocarán los botones con diseño de celdas, donde habrá 1 fila con 2 columnas.
			JPanel panelBotones = new JPanel(new GridLayout(1, 2));
            panelBotones.setBounds(0, 228, 318, 30);
            
            //Se formatea el precio para que se imprima con 2 decimales.
            JLabel precio = new JLabel("Precio: " + String.format("%.2f", producto.getPrecioFinal()) + "€");
            precio.setBounds(40, 110, 150, 30);
            
            //Imprime el nombre de cada producto, en caso de que sea libro imprimirá el título.
            JLabel nombre = new JLabel();
            nombre.setBounds(0, 10, 125, 50);
            if(producto instanceof PLibros) {
            	PLibros libro = (PLibros) producto;
            	//Las etiquetas html sirven para que el texto haga un salto de línea cuando llegue al borde del panel.
            	nombre.setText("<html>" + libro.getTitulo() + "</html>");
            }
            else {
            	nombre.setText(producto.getNombre());
            }
            
            //Botón para mostrar los detalles del objeto(información completa).
            JButton btnDetalles = new JButton("Detalles");
            btnDetalles.setBackground(new Color(255, 105, 180).brighter());
            btnDetalles.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            btnDetalles.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	
                	showMessageDialog(panelProductos, producto.dameCadena(producto));
                }
            });
            
            //Botón para comprar el producto e insertarlo en el pedido.
            JButton btnComprar = new JButton("Comprar");
            btnComprar.setBackground(new Color(255, 105, 180).brighter());
            btnComprar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            btnComprar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	//Pedir la cantidad del producto que el usuario desea.
                	String cantidadTexto = (String) JOptionPane.showInputDialog(null, "Ingrese la cantidad de productos que desea comprar:",
                            "Input", JOptionPane.QUESTION_MESSAGE, icono, null, "");
                	
                	
                	//Introducir producto en el pedido y manejo de excepciones.
                	try {
                		int cantidad = Integer.parseInt(cantidadTexto);
                		esNegativo(cantidad);
                		pedido.addProducto(producto, cantidad);
                	}
                	catch(NumberFormatException ex) {
                		showMessageDialog(panelProductos , "En este campo es obligatorio usar números.", "Error", JOptionPane.ERROR_MESSAGE);
                	}
                	catch(IllegalArgumentException ex) {
                		showMessageDialog(panelProductos , ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                	}
                }
            });
            
            //Tomar la foto de cada producto y redimensionarla.
            String rutaFoto = producto.getRutaFoto();
			ImageIcon foto = MenuBarFactory.Redimensionar(rutaFoto, 150);
			JLabel imagen = new JLabel(foto);
			imagen.setBounds(0, 0, 165, 230);
            
			
			//Añadimos los componentes a sus respectivos paneles.
			
            panelNombre.add(nombre);
            
            panelInfo.add(panelNombre);
            panelInfo.add(precio);
            
            panelImagen.add(imagen);
            
            panelBotones.add(btnComprar);
            panelBotones.add(btnDetalles);
            
            //Añadir imagen a la izquierda.
            panelProductoInfo.add(panelImagen, BorderLayout.WEST);
            //Añadir info al centro, pero como esta la imagen ocupando se queda en realidad con la derecha.
            panelProductoInfo.add(panelInfo, BorderLayout.CENTER);
            //Añadir botones en la parte inferior.
            panelProductoInfo.add(panelBotones, BorderLayout.SOUTH);
            
            casillaProducto.add(panelProductoInfo);
            
            panelProductos.add(casillaProducto);
		}
	}
	
	private boolean esNegativo(int cantidad) throws IllegalArgumentException {
		if(cantidad > 0) {
			return true;
		}
		else {
			throw  new IllegalArgumentException("La cantidad de productos ha de ser superior a 0.");
		}
	}
	
	public void cambiarAPerfil() {
		//Obtenemos el nombre del panel actual, para luego poder volver.
		try {
			panelActual = getPanelActual();
		}
		catch(IllegalArgumentException e) {
			
		}
		//Cambiamos al perfil.
		cardLayout.show(panelContenedor, "panelPerfil");
	}
	
	public void cerrarSesion() {
		VentanaMain ventana = VentanaMain.getVentanaMain();
		ventana.setVisible(true);
		this.setVisible(false);
	}
	
	public void regresarAPanelAnterior() {
		//Volver al panel anterior.
		cardLayout.show(panelContenedor, panelActual);
	}
	
	public static void mostrarVentana() {
		ventana.setVisible(true);
	}
	
	private String getPanelActual() throws IllegalArgumentException{
		String nombrePanel;
    	Component [] componentes = panelContenedor.getComponents();
    	for(Component componente : componentes) {
    		if(componente.isVisible()) {
    			nombrePanel = componente.getName();
    			//Nos interesa recibir el nombre del panel anterior al perfil.
    			//Si estamos en perfil y volvemos a entrar a perfil, se asignará como panel anterior y ya
    			//no podremos salir de ahí. Es por es que en este caso se excluye. 
    			if(nombrePanel.equals("panelPerfil")) {
    				//Lanzamos excepcion para salir de la función sin la necesidad de devolver null.
    				throw new IllegalArgumentException();
    			}
    			return nombrePanel;
    		}
    	}
    	return null;
    }	
}

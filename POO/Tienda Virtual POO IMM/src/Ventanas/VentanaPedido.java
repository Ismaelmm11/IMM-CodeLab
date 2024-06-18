package Ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import es.poo.cliente.CRegistrado;
import es.poo.cliente.Cliente;
import es.poo.pedido.*;
import es.poo.producto.*;
import es.poo.formaDePago.*;

import static javax.swing.JOptionPane.showMessageDialog;
//implements ActionListener 

@SuppressWarnings("serial")
public class VentanaPedido extends JFrame implements CambioPanelListener{
	
	private String panelActual;
	
	private Color moradoFuerte = new Color(255, 0, 255).brighter();
	private Color azulClaroNeon = new Color(0, 191, 255).brighter();
	private Color rosaClarito = new Color(255, 193, 211);
	private Color beigeSuave = new Color(255, 255, 240);
	
	
	private Pedido pedido;
	
	private JPanel panelContenedor, panelPedido, panelFormulario, panelConfirmacion, panelDatos, panelPerfil;
	
	private CardLayout cardLayout;
	
	public VentanaPedido(Pedido pedido, VentanaTienda ventana){
		super("TIENDA POO");
		setSize(1000, 625);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.pedido = pedido;
		
		//Colocar la barra horizontal arriba del todo.
		setJMenuBar(MenuBarFactory.iniciarMenuBar(pedido.getCliente()));
		
		
		inicializarPedido(ventana);
		inicializarFormulario(ventana);
		inicializarDatosInvitado(pedido.getCliente(), ventana);
		
		
		panelContenedor = new JPanel();
		cardLayout = new CardLayout();
		panelContenedor.setLayout(cardLayout);
		
		panelPedido.setName("panelPedido");
		panelFormulario.setName("panelFormulario");
		panelDatos.setName("panelDatos");
		
		panelContenedor.add(panelPedido, "panelPedido");
		panelContenedor.add(panelFormulario, "panelFormulario");
		panelContenedor.add(panelDatos, "panelDatos");
		
		if(pedido.getCliente() instanceof CRegistrado) {
			CRegistrado clienteVip = (CRegistrado) pedido.getCliente();
			
			panelPerfil = MenuBarFactory.iniciarPanelPerfil(clienteVip);
			panelContenedor.add(panelPerfil, "panelPerfil");
			panelPerfil.setName("panelPerfil");
		}
		
		MenuBarFactory.setCambioPanelListener(this);
		
		getContentPane().add(panelContenedor);
		
		this.setVisible(true);
		this.setResizable(false);
	}
	
	private void inicializarPedido(VentanaTienda ventana) {
		int contador = 0;
		int limite = 5;
		int aumentos = 1;
		int margenAncho = 0;
		int margenAlto = 0;
		
		
		JPanel panelProd = new JPanel();
		panelProd.setLayout(null);
		panelProd.setBackground(rosaClarito);
		
		Border borde = BorderFactory.createLineBorder(Color.BLACK, 3);
		Border bordeBtn = BorderFactory.createLineBorder(moradoFuerte, 4);
		
		ArrayList<LineaPedido> carrito = pedido.getCarrito();
		
		for(LineaPedido linea : carrito) {
			
			
			if(limite == contador) {
        		panelProd.setPreferredSize(new Dimension(900, (650 + (200 * aumentos))));
        		limite += 2;
        		aumentos++;
        	}
        	
        	if(contador % 2 == 0 && contador != 0) {
        		margenAncho = 0;
        		margenAlto += 190;
        	}
        	else if(contador != 0){
        		margenAncho = 430;
        	}
        	
        	JPanel miniPan = new JPanel();
        	miniPan.setLayout(null);
			miniPan.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
			miniPan.setBackground(beigeSuave);
			miniPan.setBounds((20 + margenAncho), (10 + margenAlto), 400, 180);
			
        	Producto producto = linea.getProducto();
			int cantidad = linea.getCantidad();
        	
			//new GridLayout(3, 1)
			JPanel infoPan = new JPanel();
			infoPan.setLayout(null);
			infoPan.setOpaque(false);
			infoPan.setBounds(0, 0, 225, 150);
        	
        	JLabel nombre = new JLabel("<html>" + producto.getNombre() + "</html>");
        	nombre.setBounds(20, 20, 225, 20);
        	JLabel cantidadP = new JLabel("Cantidad: " + cantidad);
        	cantidadP.setBounds(20, 50, 150, 20);
			JLabel precio = new JLabel("Precio: " + String.format("%.2f", linea.getPrecioTotal()));
			precio.setBounds(20, 80, 150, 20);
			
			JButton btn = new JButton("Detalles");
			btn.setBackground(azulClaroNeon);
			btn.setBorder(borde);
			btn.setBounds(0, 150, 400, 30);
			btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	
                	showMessageDialog(panelPedido, producto.dameCadena(producto));
                }
            });
			
			ImageIcon icono = MenuBarFactory.Redimensionar(producto.getRutaFoto(), 140);
	        JLabel imagenLabel = new JLabel(icono);
	        imagenLabel.setBounds(230, 10, 150, 130);
	        
	        infoPan.add(nombre);
	        infoPan.add(cantidadP);
	        infoPan.add(precio);
	        
	        miniPan.add(btn);
	        miniPan.add(infoPan);
	        miniPan.add(imagenLabel);
	        
	        panelProd.add(miniPan);
	       
	        contador++;
		} 
	       
	
		JScrollPane barraDesl = new JScrollPane();
		barraDesl.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		barraDesl.setViewportView(panelProd);
		
		panelProd.revalidate();
		panelProd.repaint();
		barraDesl.setViewportView(panelProd);
		
		
		JPanel panelNigga = new JPanel(new GridLayout(1, 2));
		//panelNigga.setBackground(Color.BLACK);
        panelNigga.setPreferredSize(new Dimension(0, 50));
        
        
        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setLayout(null);
        panelIzquierda.setBackground(Color.BLACK);
        panelNigga.add(panelIzquierda);
        
        JLabel precioLabel = new JLabel("Importe total: " + String.format("%.2f", pedido.getPrecioTotal()) + "€");
        Font fuente = precioLabel.getFont();
        Font fuenteGrande = new Font(fuente.getName(), Font.PLAIN, 18);
        precioLabel.setFont(fuenteGrande);
        precioLabel.setForeground(Color.WHITE);
        precioLabel.setBounds(50, 10, 350, 30);
        
        panelIzquierda.add(precioLabel);
        
        JPanel panelDerecha = new JPanel();
        panelDerecha.setBackground(Color.BLACK);
        panelDerecha.setLayout(null);
        
        JButton btnComprar = new JButton("Formalizar Compra");
        btnComprar.setBounds(0, 10, 120, 30);
        btnComprar.setBackground(rosaClarito);
        btnComprar.setBorder(bordeBtn);
        btnComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(pedido.carritoVacio() == true) {
            		showMessageDialog(panelContenedor, "No se puede formalizar la compra porque el carrito está vacío.");
            		return;
            	}
            	cardLayout.show(panelContenedor, "panelFormulario");
            }
        });
        JButton btnDescarte = new JButton("Descartar Carrito");
        btnDescarte.setBounds(140, 10, 120, 30);
        btnDescarte.setBackground(rosaClarito);
        btnDescarte.setBorder(bordeBtn);
        btnDescarte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(pedido.carritoVacio()) {
    				showMessageDialog(panelContenedor, "No se puede vaciar el carrito porque no tiene productos.");
    				return;
    			}
    			pedido.vaciarCarrito();
    			showMessageDialog(panelContenedor, "El carrito ha sido vaciado correctamente");
    			cambiarVentana(ventana);
            }
        });
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(280, 10, 120, 30);
        btnVolver.setBackground(rosaClarito);
        btnVolver.setBorder(bordeBtn);
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cambiarVentana(ventana);
            }
        });

        // Estilo de los botones

        panelDerecha.add(btnComprar);
        panelDerecha.add(btnDescarte);
        panelDerecha.add(btnVolver);

        panelNigga.add(panelDerecha);
        
        panelPedido = new JPanel(new BorderLayout());
        panelPedido.add(barraDesl, BorderLayout.CENTER);
        panelPedido.add(panelNigga, BorderLayout.SOUTH);
        
        int margenHorizontal = 50;
        MatteBorder margen = new MatteBorder(0, margenHorizontal, 0, margenHorizontal, beigeSuave);
        
        panelPedido.setBorder(margen);
		
        panelPedido.revalidate();
        panelPedido.repaint();
	}
	
	private void inicializarFormulario(VentanaTienda ventana){
		
		//Bordes:
		Border BordeRecuadro = BorderFactory.createLineBorder(moradoFuerte, 5);
		Border BordeContenido = BorderFactory.createLineBorder(azulClaroNeon, 5);
		
		//Creamos un vector MetodoPago, para poder usar las formasPago(paypal y tarjeta) en el ActionListener.
		final MetodoPago[] formasPago = new MetodoPago[2];
		MetodoPago formaPago1 = new Tarjeta();
		MetodoPago formaPago2 = new PayPal();
		
		//Array de String que tiene la descripción de las diferentes formas de envío.
		String[] formasEnvio = new String[4];
		FormaEnvio.getFormasEnvio(formasEnvio);
		
		//Inicializamos el panel del formulario.
		panelFormulario = new JPanel();
		panelFormulario.setLayout(null);
		panelFormulario.setBackground(rosaClarito);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(5, 60, 5, 60));
        
        JPanel panelRecuadro = new JPanel();
        panelRecuadro.setLayout(null);
        panelRecuadro.setBounds(280, 20, 440, 500);
        panelRecuadro.setBackground(beigeSuave);
        panelRecuadro.setBorder(BordeRecuadro);
        
        
        //Etiqueta forma de envío.
        JLabel labelenvio = new JLabel("Forma de Envio");
        labelenvio.setBounds(60, 15, 180, 20);
        
        //Lista desplegable de las formas de envío.
        JComboBox<String> selector = new JComboBox<String>(formasEnvio);
        selector.setBounds(60, 40, 320, 20);

        // Campo de texto y etiqueta para la direccion.
        JTextField campoTexto = new JTextField();
        campoTexto.setBounds(125, 80, 240, 20);
        JLabel labelDireccion = new JLabel("Dirección:");
        labelDireccion.setBounds(60, 80, 210, 20);

        //Selector con 2 opciones:
        JLabel pagoLabel = new JLabel("Método de pago: ");
        pagoLabel.setBounds(60, 120, 220, 20);
        //Configurar los botones.
        ButtonGroup opciones = new ButtonGroup();
        JRadioButton opcion1 = new JRadioButton();
        opcion1.setBounds(180, 150, 20, 20);
        opcion1.setBackground(beigeSuave);
        JRadioButton opcion2 = new JRadioButton();
        opcion2.setBounds(60, 150, 20, 20);
        opcion2.setBackground(beigeSuave);
        
        //Colocar los botones en el grupo de botones.
        opciones.add(opcion1);
        opciones.add(opcion2);
       
        //Etiqueta Tarjeta y PayPal que van a acompañar a los botones del buttonGroup.
        JLabel labelTarjeta = new JLabel("Tarjeta");
        labelTarjeta.setBounds(200, 150, 100, 20);
        //Con esto conseguimos mostrar la descripción de los métodos de pago cuando el cursor está encima.
        labelTarjeta.setToolTipText(formaPago1.dameCadena());
        JLabel labelPayPal = new JLabel("PayPal");
        labelPayPal.setBounds(80, 150, 100, 20);
        labelPayPal.setToolTipText(formaPago2.dameCadena());
        
        //Creamos un panel que va a contener campos a rellenar según si se elige PayPal o Tarjeta.
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(null);
        panelContenido.setBounds(40, 180, 370, 250);
        panelContenido.setBackground(beigeSuave);
        panelContenido.setBorder(BordeContenido);
        //Comienza siendo invisible, pero cuando se escoja una opcion aparecerá mágicamente.
        panelContenido.setVisible(false);
        
        //Componentes Tarjeta:
        //Selector del tipo de Tarjeta.
        JLabel tarjetaLabel = new JLabel("Tipo de Tarjeta: ");
        tarjetaLabel.setBounds(20, 20, 100, 20);
        ButtonGroup opcions = new ButtonGroup();
        JRadioButton opc1 = new JRadioButton("VISA");
        opc1.setBounds(20, 45, 100, 20);
        opc1.setBackground(beigeSuave);
        JRadioButton opc2 = new JRadioButton("4B");
        opc2.setBounds(125, 45, 100, 20);
        opc2.setBackground(beigeSuave);
        JRadioButton opc3 = new JRadioButton("EURO600");
        opc3.setBounds(20, 70, 100, 20);
        opc3.setBackground(beigeSuave);
        JRadioButton opc4 = new JRadioButton("AMERICAN EXPRESS");
        opc4.setBounds(125, 70, 150, 20);
        opc4.setBackground(beigeSuave);
        
        opcions.add(opc1);
        opcions.add(opc2);
        opcions.add(opc3);
        opcions.add(opc4);
        
        //Etiquetas y campos para Tarjeta:
        JLabel titularLabel = new JLabel("Titular: ");
        JTextField campoTitular = new JTextField();
        titularLabel.setBounds(20, 100, 100, 20);
        campoTitular.setBounds(85, 100, 210, 20);
        
        JLabel numTarjetaLabel = new JLabel("Nº Tarjeta: ");
        numTarjetaLabel.setBounds(20, 125, 100, 20);
        JTextField tarjetaCampo = new JTextField();
        tarjetaCampo.setBounds(85, 125, 210, 20);
        
        JLabel CVCLabel = new JLabel("CVC: ");
        JTextField campoCVC = new JTextField();
        CVCLabel.setBounds(20, 150, 100, 20);
        campoCVC.setBounds(85, 150, 210, 20);
        
        JLabel labelFechaCad = new JLabel("Fecha de caducidad:");
        labelFechaCad.setBounds(20, 175, 150, 20);
        JFormattedTextField campoFechaCad = MenuBarFactory.createDateTextField2_0();
        campoFechaCad.setBounds(140, 175, 35, 20);
        
        //Componentes PayPal:
        //Etiqueta y campo de fecha:
        JLabel userLabel = new JLabel("Usuario: ");
        userLabel.setBounds(20, 20, 50, 20);
        JTextField campoUser = new JTextField();
        campoUser.setBounds(70, 20, 210, 20);
        
        JLabel fechaLabel =  new JLabel("Fecha de alta:");
        fechaLabel.setBounds(20, 45, 100, 20);
        JFormattedTextField campoFechaAlta = MenuBarFactory.createDateTextField();
        campoFechaAlta.setBounds(105, 45, 75, 20);
   
        //Creamos un ActionListener para gestionar la selección de opciones:
        ActionListener listenerOpciones = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Acciones según la opción seleccionada
                if (opcion1.isSelected()) {
                	//Quitamos los componentes y los cambiamos por los de Tarjeta.
                    panelContenido.removeAll();
                    panelContenido.setVisible(true);
                    panelContenido.add(tarjetaLabel);
                    panelContenido.add(opc1);
                    panelContenido.add(opc2);
                    panelContenido.add(opc3);
                    panelContenido.add(opc4);
                    panelContenido.add(titularLabel);
                    panelContenido.add(campoTitular);
                    panelContenido.add(numTarjetaLabel);
                    panelContenido.add(tarjetaCampo);
                    panelContenido.add(CVCLabel);
                    panelContenido.add(campoCVC);
                    panelContenido.add(labelFechaCad);
                    panelContenido.add(campoFechaCad);
                    
                } else if (opcion2.isSelected()) {
                	//Quitamos los componentes y los cambiamos por los de PayPal.
                    panelContenido.removeAll();
                    panelContenido.setVisible(true);
                    panelContenido.add(userLabel);
                    panelContenido.add(campoUser);
                    panelContenido.add(fechaLabel);
                    panelContenido.add(campoFechaAlta);
                   
                }
                // Actualizar el panel.
                panelContenido.repaint();
                panelContenido.revalidate();
            }
        };

        //Asignamos el ActionListener que acabamos de crear a las 2 opciones.
        opcion1.addActionListener(listenerOpciones);
        opcion2.addActionListener(listenerOpciones);
        
        //Botón para continuar hacia la confirmación del pedido.
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.setBounds(100, 440, 100, 30);
        btnContinuar.setBackground(azulClaroNeon);
        btnContinuar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        btnContinuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                if(opcion1.isSelected()) {
                	Tarjeta tarjeta = (Tarjeta) formaPago1;
                	
                	if(opc1.isSelected()) {
                		tarjeta.setTipo(1);
                	}
                	else if(opc2.isSelected()) {
                		tarjeta.setTipo(2);
                	}
                	else if(opc3.isSelected()) {
                		tarjeta.setTipo(3);
                	}
                	else if(opc4.isSelected()) {
                		tarjeta.setTipo(4);
                	}
                	else{
                		showMessageDialog(panelFormulario, "Por favor, asegurese de indicar el tipo de tarjeta.");
                		tarjeta = null;
                		return;
                	}
                	
                	
                	String titular = campoTitular.getText();
                	String numTarjeta = tarjetaCampo.getText();
                	String CVC = campoCVC.getText();
                	
                	try {
                		tarjeta.setTitular(titular);
                		tarjeta.setNumTarjeta(numTarjeta);
                		tarjeta.setCVC(CVC);
                	}
                	catch(IllegalArgumentException ex) {
                		showMessageDialog(panelFormulario, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                		tarjeta = null;
                		return;
                	}
                	
                	formasPago[0] = tarjeta;
                	pedido.setFormaPago(formasPago[0]);
                }
                else if(opcion2.isSelected()) {
                	PayPal paypal = (PayPal) formaPago2;
                	
                	String fecha = campoFechaAlta.getText();
                	String usuario = campoUser.getText();
                	
                	try {
                		paypal.setUsusario(usuario);
                		paypal.setFechaAlta(fecha);
                	}
                	catch(IllegalArgumentException ex){
                		showMessageDialog(panelFormulario, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                		paypal = null;
                		return;
                	}
                	formasPago[1] = paypal;
                	pedido.setFormaPago(formasPago[1]);
                }
                else {
                	showMessageDialog(panelFormulario ,"Por favor escoja un método de pago.");
                	return;
                }
                
                String direccion = campoTexto.getText();
                //No hace falta controlar excepciones(ej: si está vacío).
            	String formaEnvioString = (String) selector.getSelectedItem();
                
                try {
                	pedido.setDireccion(direccion);
                }
                catch(IllegalArgumentException ex) {
                	showMessageDialog(panelFormulario, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                	return;
                }
                
                FormaEnvio formaEnvio = FormaEnvio.getFormaEnvio(formaEnvioString);
                pedido.setFormaEnvio(formaEnvio);
                
                
                Cliente cliente = pedido.getCliente();
                
                if(!(cliente instanceof CRegistrado)) {
                	inicializarDatosInvitado(cliente, ventana);
                	cardLayout.show(panelContenedor, "panelDatos");
                }
                else {
                	//Para inicializar el siguiente panel se hace aquí por que los datos anteriores son necesarios.
                	inicializarConfirmacion(ventana);
                	panelConfirmacion.setName("panelConfirmacion");
            		panelContenedor.add(panelConfirmacion, "panelConfirmacion");
                    cardLayout.show(panelContenedor, "panelConfirmacion");
                }
           }        });
        
        //Botón para volver al panel de pedido.
        JButton btnVolver2 = new JButton("Volver");
        btnVolver2.setBounds(240, 440, 100, 30);
        btnVolver2.setBackground(azulClaroNeon);
        btnVolver2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        btnVolver2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayout.show(panelContenedor, "panelPedido");
        	}
        });
        
        //Añadir todos los componentes al panel.
        panelRecuadro.add(labelenvio);
        panelRecuadro.add(selector);
        panelRecuadro.add(labelDireccion);
        panelRecuadro.add(campoTexto);
        panelRecuadro.add(pagoLabel);
        panelRecuadro.add(opcion1);
        panelRecuadro.add(opcion2);
        panelRecuadro.add(labelTarjeta);
        panelRecuadro.add(labelPayPal);
        panelRecuadro.add(panelContenido);
        panelRecuadro.add(btnContinuar);
        panelRecuadro.add(btnVolver2);
        
        
        panelFormulario.add(panelRecuadro);

        setLocationRelativeTo(null);
        setVisible(true);
        
	}
	
	private void inicializarDatosInvitado(Cliente cliente, VentanaTienda ventana) {
		//Borde: 
		Border BordeRecuadro =  BorderFactory.createLineBorder(moradoFuerte, 5);
		
		//Inicializamos el panel de datos del invitado.
		panelDatos = new JPanel();
		panelDatos.setLayout(null);
		panelDatos.setBorder(BorderFactory.createEmptyBorder(5, 60, 5, 60));
		panelDatos.setBackground(rosaClarito);
        
        JPanel panelRecuadro = new JPanel();
        panelRecuadro.setLayout(null);
        panelRecuadro.setBounds(350, 120, 300, 270);
		panelRecuadro.setBackground(beigeSuave);
        panelRecuadro.setBorder(BordeRecuadro);
        
        //Etiquetas y campos para pedir los datos mínimos:
        JLabel nombreLabel = new JLabel("Nombre: ");
        nombreLabel.setBounds(20, 40, 60, 20);
        JTextField nombreCampo = new JTextField();
        nombreCampo.setBounds(80, 40, 190, 20);
        
        JLabel apellidoLabel = new JLabel("Apellidos: ");
        apellidoLabel.setBounds(20, 80, 60, 20);
        JTextField apellidoCampo = new JTextField();
        apellidoCampo.setBounds(80, 80, 190, 20);
        
        JLabel correoLabel = new JLabel("Correo: ");
        correoLabel.setBounds(20, 120, 60, 20);
        JTextField correoCampo = new JTextField();
        correoCampo.setBounds(80, 120, 190, 20);
        
        //Botón para registrar las respuestas y avanzar en caso de ser correctos.
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.setBounds(40, 200, 90, 25);
        btnContinuar.setBackground(new Color(255, 105, 180).brighter());
        btnContinuar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btnContinuar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String nombre = nombreCampo.getText();
        		String apellidos = apellidoCampo.getText();
        		String correo = correoCampo.getText();
        		
        		//Settear valores y manejo de excepciones.
        		try {
        			cliente.setNombre(nombre);
        			cliente.setApellidos(apellidos);
        			cliente.setCorreo(correo);
        		}
        		catch(IllegalArgumentException ex) {
        			showMessageDialog(panelContenedor, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		catch(NullPointerException ex) {
        			showMessageDialog(panelContenedor, ex.getMessage());
        			return;
        		}
        		
        		inicializarConfirmacion(ventana);
        		panelContenedor.add(panelConfirmacion, "panelConfirmacion");
                cardLayout.show(panelContenedor, "panelConfirmacion");;
        	}
        });
        //Botón para volver al formulario.
        JButton btnVolverr = new JButton("Volver");
        btnVolverr.setBounds(170, 200, 90, 25);
        btnVolverr.setBackground(new Color(255, 105, 180).brighter());
        btnVolverr.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btnVolverr.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayout.show(panelContenedor, "panelFormulario");
        	}
        });
        
        //Añadir todos los componentes al panel.
        panelRecuadro.add(nombreLabel);
        panelRecuadro.add(nombreCampo);
        panelRecuadro.add(apellidoLabel);
        panelRecuadro.add(apellidoCampo);
        panelRecuadro.add(correoLabel);
        panelRecuadro.add(correoCampo);
        panelRecuadro.add(btnContinuar);
        panelRecuadro.add(btnVolverr);
        
        
        panelDatos.add(panelRecuadro);
        
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	private void inicializarConfirmacion(VentanaTienda ventana) {
		//Bordes:
		Border BordeRecuadro = BorderFactory.createLineBorder(moradoFuerte, 5);
		Border Linea = BorderFactory.createLineBorder(azulClaroNeon, 2);
		Border BordeBoton = BorderFactory.createLineBorder(Color.BLACK, 3);
		
		//Sacamos de Pedido los objetos para poder imprimir su informacion.
		Cliente cliente = pedido.getCliente();
		MetodoPago formaPago = pedido.getFormaPago();
		FormaEnvio formaEnvio = pedido.getFormaEnvio();
		String direccion = pedido.getDireccion();
		
		//Creamos un panel donde se concentrará el resto de paneles.
		panelConfirmacion = new JPanel();
		panelConfirmacion.setLayout(null);
        panelConfirmacion.setBackground(Color.WHITE);
        
        JPanel panelRecuadro = new JPanel();
        panelRecuadro.setLayout(null);
        //Se le pone el -10 en margen vertical, para que no se vean sus bordes superiores e inferiores.
        panelRecuadro.setBounds(200, -10, 600, 700);
        panelRecuadro.setBackground(beigeSuave);
        panelRecuadro.setBorder(BordeRecuadro);
        
        //Panel con la información del Pedido(Sobre los productos comprados).
        JPanel panelPedido = new JPanel();
        panelPedido.setLayout(null);
        panelPedido.setBounds(75, 40, 450, 170);
        panelPedido.setBackground(rosaClarito);
        panelPedido.setBorder(BordeRecuadro);
        
        JLabel pedidoLabel = new JLabel("PEDIDO");
        pedidoLabel.setBounds(195, 10, 90, 20);
        Font fuenteActual = pedidoLabel.getFont();
        //Crear una nueva fuente con un tamaño mayor
        Font fuenteGrande = new Font(fuenteActual.getName(), fuenteActual.getStyle(), 16);		
        pedidoLabel.setFont(fuenteGrande);
        
        //Etiquetas con la información del pedido.
        JLabel numPedidoLabel = new JLabel("Nº Pedido: " + pedido.getNumPedido());
        numPedidoLabel.setBounds(40, 32, 100, 40);
        JLabel cantidadLabel = new JLabel("Cantidad Productos: " + pedido.getCantidadProductos());
        cantidadLabel.setBounds(220, 32, 160, 40);
        JLabel precioNetoLabel = new JLabel("Precio neto: " + String.format("%.2f", pedido.getPrecioNeto()));
        precioNetoLabel.setBounds(40, 70, 150, 20);
        JLabel impuestosLabel = new JLabel("Impuestos: " + String.format("%.2f", pedido.getImpuestos()));
        impuestosLabel.setBounds(220, 70, 100, 20);
        JLabel costeEnvioLabel = new JLabel("Gastos de envío: " + String.format("%.2f", pedido.getCosteEnvio()));
        costeEnvioLabel.setBounds(40, 98, 150, 20);
        JLabel precioTotalLabel = new JLabel("IMPORTE TOTAL: " + String.format("%.2f", pedido.getPrecioTotal()));
        precioTotalLabel.setBounds(220, 98, 210, 20);
        
        //Botón para imprimir el desglose del pedido(Productos comprados, cantidad, precio de cada uno)
        JButton btnDetalles = new JButton("DETALLES");
        btnDetalles.setBounds(0, 140, 450, 30);
        btnDetalles.setBorder(BordeRecuadro);
        btnDetalles.setBackground(new Color(255, 105, 180).brighter());
        btnDetalles.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		showMessageDialog(panelContenedor, pedido.dameCadena());
        	}
        });
        
        //Añadimos los componentes al PanelPedido.
        panelPedido.add(pedidoLabel);
        panelPedido.add(numPedidoLabel);
        panelPedido.add(cantidadLabel);
        panelPedido.add(precioNetoLabel);
        panelPedido.add(impuestosLabel);
        panelPedido.add(costeEnvioLabel);
        panelPedido.add(precioTotalLabel);
        panelPedido.add(btnDetalles);
        
        //Panel con más información del pedido(usuario que compra, direccion, forma de pago...)
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(null);
        panelDatos.setBounds(75, 230, 450, 155);
        panelDatos.setBackground(rosaClarito);
        panelDatos.setBorder(BordeRecuadro);
        
      //Etiquetas con info del usuario:
        JLabel userLabel = new JLabel("Usuario: " + cliente.getApellidos() + " " + cliente.getNombre());
        userLabel.setBounds(20, 15, 200, 20);
        JLabel correoLabel = new JLabel("Correo electrónico: " + cliente.getCorreo());
        correoLabel.setBounds(20, 40, 400, 20);
        
        //Etiquetas con la informacion de la forma de pago.
        JLabel formaPagoLabel = new JLabel();
        formaPagoLabel.setBounds(20, 65, 150, 20);
		JLabel identificadorPago = new JLabel();
        identificadorPago.setBounds(200, 65, 200, 20);
        if(formaPago instanceof Tarjeta) {
        	Tarjeta tarjeta = (Tarjeta) formaPago;
        	formaPagoLabel.setText("Método de pago: Tarjeta");
        	identificadorPago.setText("Nº Tarjeta: " + tarjeta.getNumTarjetaCifrado());
        }
        else if(formaPago instanceof PayPal) {
        	PayPal paypal = (PayPal) formaPago;
        	formaPagoLabel.setText("Método de pago: PayPal");
        	identificadorPago.setText("Usuario: " + paypal.getUsuario());
        }
        
        
        //Etiqueta con la forma de envío y direccion.
        JLabel metodoEnvio = new JLabel(formaEnvio.dameCadena());
        metodoEnvio.setBounds(20, 90, 250, 20);
        JLabel direccionLabel = new JLabel("Direccion de entrega: " + direccion);
        direccionLabel.setBounds(20, 115, 400, 20);
        
        //Separadores:
        JLabel separador = new JLabel();
        separador.setBorder(Linea);
        separador.setBounds(15, 61, 420, 2);
        JLabel separador2 = new JLabel();
        separador2.setBorder(Linea);
        separador2.setBounds(15, 87, 420, 2);       
        JLabel separador3 = new JLabel();
        separador3.setBorder(Linea);
        separador3.setBounds(15, 112, 420, 2);
        
        //Añadimos todo al panel de datos.
        panelDatos.add(userLabel);
        panelDatos.add(correoLabel);
        panelDatos.add(separador);
        panelDatos.add(formaPagoLabel);
        panelDatos.add(identificadorPago);
        panelDatos.add(separador2);
        panelDatos.add(metodoEnvio);
        panelDatos.add(separador3);
        panelDatos.add(direccionLabel);
        
        //-------------------------------------------------------------------------------------------------
        //Copnfiguración botones:
        
        //Botón para finalizar el pedido.
        JButton btnConfirmar = new JButton("Realizar Pedido");
        btnConfirmar.setBounds(75, 400, 130, 40);
        btnConfirmar.setBorder(BordeBoton);
        btnConfirmar.setBackground(azulClaroNeon);
        btnConfirmar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		showMessageDialog(panelConfirmacion, "PEDIDO REALIZADO CORRECTAMENTE\n"
        				+ "ESPEREMOS QUE HAYA DISFRUTADO DE LA EXPERIENCIA COMPRANDO EN NUESTRA TIENDA\n"
        				+ "QUE TENGA BUEN DÍA Y ESPERAMOS VERLE PRONTO EN NUESTRA TIENDA");
        		
        		//Si es un cliente registrado le guardamos la información sobre el pedido.
        		if(cliente instanceof CRegistrado) {
        			//CRegistrado clienteVip = (CRegistrado) cliente;
        			//clienteVip.addDirecciones(direccion);
        			//clienteVip.addFormasPago(formaPago);
        			//Pedido pedidoCopia = new Pedido(pedido);
        			//clienteVip.addPedido(pedidoCopia);
        			//Comentado porque sino el programa peta, esta parte de la herencia no funciona.
        			//CRegistrado.save();
        		}
        		//Descartamos el pedido y volvemos al panel de Tienda.
        		pedido.descartarPedido();
        		cambiarVentana(ventana);
        		
        	}
        });
        
        //Botón para descartar el pedido.
        JButton btnDescartar = new JButton("Descartar Pedido");
        btnDescartar.setBounds(235, 400, 130, 40);
        btnDescartar.setBorder(BordeBoton);
        btnDescartar.setBackground(azulClaroNeon);
        btnDescartar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		showMessageDialog(panelConfirmacion, "El pedido ha sido descartado correctamente.");
        		pedido.descartarPedido();
        		cambiarVentana(ventana);
        	}
        });
        
        //Botón para volver al panel del formulario.
        JButton btnBack = new JButton("Volver");
        btnBack.setBounds(395, 400, 130, 40);
        btnBack.setBorder(BordeBoton);
        btnBack.setBackground(azulClaroNeon);
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cardLayout.show(panelContenedor, "panelFormulario");
        	}
        });
        
        //Añadimos los paneles y botones al panelRecuadro.
        panelRecuadro.add(panelPedido);
        panelRecuadro.add(panelDatos);
        panelRecuadro.add(btnConfirmar);
        panelRecuadro.add(btnDescartar);
        panelRecuadro.add(btnBack);
        
        panelConfirmacion.add(panelRecuadro);
	}
	
	public void cambiarAPerfil() {
		try {
			panelActual = getPanelActual();
		}
		catch(IllegalArgumentException e) {
			
		}
		cardLayout.show(panelContenedor, "panelPerfil");
	}
	
	public void regresarAPanelAnterior() {
		cardLayout.show(panelContenedor, panelActual);
	}
	
	public void cerrarSesion() {
		VentanaMain ventana = VentanaMain.getVentanaMain();
		ventana.setVisible(true);
		this.setVisible(false);
	}
	
	private String getPanelActual() throws IllegalArgumentException{
		String nombrePanel;
    	Component [] componentes = panelContenedor.getComponents();
    	for(Component componente : componentes) {
    		if(componente.isVisible()) {
    			nombrePanel = componente.getName();
    			if(nombrePanel.equals("panelPerfil")) {
    				throw new IllegalArgumentException();
    			}
    			return nombrePanel;
    		}
    	}
    	return null;
    }
	
	private void cambiarVentana(VentanaTienda ventana) {
		ventana.setLocation(this.getLocation());
		ventana.setVisible(true);
		this.setVisible(false);
	}
	
	
}

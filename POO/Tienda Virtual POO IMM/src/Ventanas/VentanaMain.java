package Ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;
import javax.swing.border.Border;
import static javax.swing.JOptionPane.showMessageDialog;

import es.poo.cliente.*;

@SuppressWarnings("serial")
public class VentanaMain extends JFrame {
	
	private static final String FOTO_OJO1 = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\ojo abierto.png";
	private static final String FOTO_OJO2 = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\ojo cerrado.png";


	private Cliente cliente = null;
	
	private static VentanaMain ventana = new VentanaMain();
	
	private Color moradoFuerte = new Color(255, 0, 255).brighter();
	private Color azulClaroNeon = new Color(0, 191, 255).brighter();
	private Color rosaClarito = new Color(255, 193, 211);
	private Color beigeSuave = new Color(255, 255, 240);
	
	
	private JPanel panelContenedor, panelPortal, panelInicio, panelDatos;
	private CardLayout cardLayout;
	
	private VentanaMain() 
	{
		//Inicializamos la ventana con su propio título.
		super("TIENDA POO");
		setSize(1000, 625);
		
		//Indicamos que la aplicacion debe cerrarse por completo si se pulsa el boton de cerrar.
		//Así evitamos que pueda quedarse ejecutando en segundo plano.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Colocamos una barra horizontal en la parte superior del frame.
		setJMenuBar(MenuBarFactory.iniciarMenuBar(cliente));
		
		//Inicializar el panel de inicio.
		inicializarVentana();
		//Inicializar el panel de inicio de sesion.
		inicializarInicio();
		//Inicializar el panel de registro de usuario.
		inicializarRegistro();
		
		//Creamos el panel Contenedor, que es un panel que contendrá al resto de paneles.
		//Con cardLayout podremos manejar que panel mostramos dentro del panelContenedor.
		panelContenedor = new JPanel();
		cardLayout = new CardLayout();
		panelContenedor.setLayout(cardLayout);
		
		//Les damos nombre para poder movernos entre ellos.
		panelContenedor.add(panelPortal, "panelPortal");
		panelContenedor.add(panelInicio, "panelInicio");
		panelContenedor.add(panelDatos, "panelDatos");
		
		//Añadimos el panelContenedor al JFrame.
		getContentPane().add(panelContenedor);
		
		//Para mostrar la ventana justo en el centro de la pantalla.
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	
	private void inicializarVentana() 
	{
		//Creamos un borde dandole color y grosor.
		Border borde = BorderFactory.createLineBorder(moradoFuerte, 5);
		Border borde2 = BorderFactory.createLineBorder(Color.BLACK, 3);
		
		//Inicializar portal de la app.
		panelPortal = new JPanel();
		//Con esto se añade color al componente.
		panelPortal.setBackground(rosaClarito);
		//IMPORTANTÍSIMO: Nos permite colocar manualmente cualquier componente del panel.
		panelPortal.setLayout(null);
        
		//Panel que va a contener los componentes.
        JPanel panelRecuadro = new JPanel();
        panelRecuadro.setBackground(beigeSuave);
        //Añadimos borde.
		panelRecuadro.setBorder(borde);
		//Específicamos su posicion dentro de panelPortal y su tamaño.
		panelRecuadro.setBounds(345, 80, 300, 350);
		//Con esto añadimos un diseño donde separamos a los componentes en celdas.
		panelRecuadro.setLayout(new GridBagLayout());
        
		//Configurar el diseño de celdas
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;	//Posicion horizontal.
        gbc.gridy = 0;	//Posicion vertical.
        gbc.anchor = GridBagConstraints.CENTER;	//Para que el componente se ancle al centro de la celda.
        gbc.insets = new Insets(10, 0, 10, 0);	//Margen entre la celda y el componente.
        
        
        //----------------------------------------------------------------------------------------------------
        //Configuración botones:
        
        //Botón para ir al registro de usuario.
        JButton btnRegistro = new JButton("Registro de Usuario");
        //Cuando el seetLayout del panel no es null toca usar esto para establecer las dimensiones del componente.
        btnRegistro.setPreferredSize(new Dimension(175, 35));
        btnRegistro.setBackground(azulClaroNeon);
        btnRegistro.setBorder(borde2);
        //Configuramos el botón para que cuando se le de click ejecute una acción.
        btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//Esto es la gran clave del programa.
            	//Oculta el panel actual y muestra el que se le indique.
            	cardLayout.show(panelContenedor, "panelDatos");
            }
        });
        //Colocamos el botón en la segunda celda.
        gbc.gridy = 1;
        //Añadir componente al panel usando la configuracion de la celda.
        panelRecuadro.add(btnRegistro, gbc);
        
        //Botón para ir al inicio de sesión.
        JButton btnIS = new JButton("Inicio de Sesión");
        btnIS.setPreferredSize(new Dimension(175, 35));
        btnIS.setBackground(azulClaroNeon);
        btnIS.setBorder(borde2);
        gbc.gridy = 2;
        panelRecuadro.add(btnIS, gbc);
        btnIS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cardLayout.show(panelContenedor, "panelInicio");
            }
        });
        
        //Botón para entrar como usuario invitado.
        JButton btnInv = new JButton("Entrar como invitado");
        btnInv.setPreferredSize(new Dimension(175, 35));
        btnInv.setBackground(azulClaroNeon);
        btnInv.setBorder(borde2);
        gbc.gridy = 3;
        panelRecuadro.add(btnInv, gbc);
        btnInv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cliente = new Cliente();
    			cliente.setNombre("Invitado");
    			pasarVentana();
            }
        });
        
        //Botón para salir de la aplicación.
        JButton btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(175, 35));
        btnSalir.setBackground(azulClaroNeon);
        btnSalir.setBorder(borde2);
        gbc.gridy = 4;
        panelRecuadro.add(btnSalir, gbc);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showMessageDialog(panelContenedor, "MUCHISIMAS GRACIAS POR VISITAR NUESTRA TIENDA.\n"
            			+ "ESPERAMOS VERLE DE NUEVO POR AQUÍ.");
            	System.exit(0);
            }
        });
		
        //Añadimos el panel al panel principal y añadimos un borde negro que queda bien.
        panelPortal.add(panelRecuadro);
        panelPortal.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	private void inicializarInicio()
	{
		//Redimensionamos las imagenes y las asignamos a un ImageIcon.
		ImageIcon icono = MenuBarFactory.Redimensionar(FOTO_OJO1, 30);
		ImageIcon icono2 =  MenuBarFactory.Redimensionar(FOTO_OJO2, 30);
		
		//Bordes:
		Border borde = BorderFactory.createLineBorder(Color.BLACK, 3);
		Border bordeRecuadro = BorderFactory.createLineBorder(moradoFuerte, 5);
		
		//Iniciar el inicio de sesión.
		panelInicio = new JPanel();
		panelInicio.setBackground(rosaClarito);
		panelInicio.setLayout(null);
		
		JPanel panelRecuadro = new JPanel();
		panelRecuadro.setBackground(beigeSuave);
		panelRecuadro.setBorder(bordeRecuadro);
		panelRecuadro.setBounds(325, 80, 320, 350);
		panelRecuadro.setLayout(null);
        
		//El Label es una etiqueta que servirá para mostrar texto.
        JLabel labelCorreo = new JLabel("Correo electrónico: ");
        labelCorreo.setBounds(35, 50, 150, 20);
        JLabel labelPassword = new JLabel("Contraseña: ");
        labelPassword.setBounds(35, 115, 150, 20);
        
        //El TextField es un campo de texto donde el usuario podrá introducir información.
        JTextField campoCorreo = new JTextField();
        campoCorreo.setBounds(35, 80, 240, 30);
        //El PasswordField es un campo de contraseña donde el texto introducido aparecerá en forma de *.
        JPasswordField campoPassword = new JPasswordField();
        campoPassword.setBounds(35, 145, 220, 30);
        
        
        //---------------------------------------------------------------------------------------------------
        //Configuración botones.
        
        //Botón para iniciar sesión.
        JButton btnIS = new JButton("Iniciar Sesión");
        btnIS.setBounds(85, 230, 150, 30);
        btnIS.setBackground(azulClaroNeon);
        btnIS.setBorder(borde);
        btnIS.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String correo = campoCorreo.getText();
        		//El passwordField solo nos devuelve un array de chars.
        		char[] password = campoPassword.getPassword();
        		//Por eso hay que convertirlo a un String.
                String passwordString = new String(password);
        		
        		
        		
        		//Por si se deja algun campo vacío.
        		if(correo.isBlank()) {
        			showMessageDialog(panelInicio, "Por favor, rellene el campo de 'Correo'.");
        		}
        		else if(passwordString.isBlank()) {
        			showMessageDialog(panelInicio, "Por favor, rellene el campo de 'Contraseña'.");
        		}
        		//Inicio de sesión.
        		cliente = CRegistrado.inicioSesión(correo, passwordString);
        		if(cliente != null) {
        			//En caso de inicio correcto Pasamos a la siguiente ventana y limpiar campos.
        			pasarVentana();
        			campoPassword.setText("");
        			campoCorreo.setText("");
        		}
        		else {
        			showMessageDialog(panelInicio, "Inicio de sesión fallido.");
        		}
        	}
        });
        
        //Botón para volver al panel anterior.
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(azulClaroNeon);
        btnVolver.setBounds(112, 280, 100, 30);
        btnVolver.setBorder(borde);
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//Limpiar Campos
            	campoPassword.setText("");
    			campoCorreo.setText("");
            	
            	cardLayout.show(panelContenedor, "panelPortal");
            }
        });
        
        //Este es de mis favoritos :)
        //Botón para mostrar la constraseña.
        JButton btnVer = new JButton();
        btnVer.setBackground(azulClaroNeon);
        btnVer.setBounds(265, 145, 30, 30);
        btnVer.setBorder(borde);
        //Asignamos imagen al botón.
        btnVer.setIcon(icono2);
        btnVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//Gestión de visibilidad del texto, estableciendo el caracter que se usará representar el pass:
            	final char passVisible = (char) 0;	//Este char sirve para mostrar el pass.
            	final char passOculta = '\u2022';	//Este char sirve para en vez de mostrar el pass muestra una
            										//bolita negra(*) por cada caracter.
            	
            	//Alternar entre texto visible y texto oculto, aprovechamos para modificar el botón según el texto:
            	//Poner el texto visible:
                if(campoPassword.getEchoChar() == passOculta) {
                	campoPassword.setEchoChar(passVisible);
                	btnVer.setBackground(moradoFuerte);
                	btnVer.setIcon(icono);
                }
                //Poner el texto oculto:
                else {
                	campoPassword.setEchoChar(passOculta);
                	btnVer.setBackground(azulClaroNeon);
                	btnVer.setIcon(icono2);
                }
            }
        });
        
        
        //Añadir todos los componentes al panel.
        panelRecuadro.add(labelCorreo);
        panelRecuadro.add(campoCorreo);
        panelRecuadro.add(labelPassword);
        panelRecuadro.add(campoPassword);
        panelRecuadro.add(btnVolver);
        panelRecuadro.add(btnIS);
        panelRecuadro.add(btnVer);
                           
		panelInicio.add(panelRecuadro);
	}
	
	private void inicializarRegistro() 
	{
		
		//Borde:
		Border borde = BorderFactory.createLineBorder(Color.BLACK, 3);
		Border bordeRecuadro = BorderFactory.createLineBorder(moradoFuerte, 5);
		
		//Iniciar el registro de usuario.
		panelDatos = new JPanel();
		panelDatos.setLayout(null);
		panelDatos.setBorder(BorderFactory.createEmptyBorder(5, 60, 5, 60));
		panelDatos.setBackground(rosaClarito);
        
        JPanel panelRecuadro = new JPanel();
        panelRecuadro.setLayout(null);
        panelRecuadro.setBorder(bordeRecuadro);
        panelRecuadro.setBounds(345, 40, 300, 450);
        panelRecuadro.setBackground(beigeSuave);
        
        //Etiquetas y campos de texto:
        JLabel nombreLabel = new JLabel("Nombre: ");
        nombreLabel.setBounds(25, 30, 150, 20);
        JTextField nombreCampo = new JTextField();
        nombreCampo.setBounds(25, 60, 240, 30);
        JLabel apellidoLabel = new JLabel("Apellidos: ");
        apellidoLabel.setBounds(25, 95, 150, 20);
        JTextField apellidoCampo = new JTextField();
        apellidoCampo.setBounds(25, 125, 240, 30);
        JLabel correoLabel = new JLabel("Correo electrónico: ");
        correoLabel.setBounds(25, 160, 150, 20);
        JTextField correoCampo = new JTextField();
        correoCampo.setBounds(25, 190, 240, 30);
        JLabel passLabel = new JLabel("Contraseña: ");
        passLabel.setBounds(25, 225, 150, 20);
        JTextField passCampo = new JTextField();
        passCampo.setBounds(25, 255, 240, 30);
        JLabel fechaLabel = new JLabel("Fecha de nacimiento: ");
        fechaLabel.setBounds(25, 310, 150, 20);
        JFormattedTextField fechaCampo = MenuBarFactory.createDateTextField();
        fechaCampo.setBounds(155, 305, 70, 30);
        
        //----------------------------------------------------------------------------------------------
        //Configuración botones:
        
        //Botón para confirmar el registro.
        JButton btnContinuar = new JButton("Registrar");
        btnContinuar.setBounds(30, 380, 100, 30);
        btnContinuar.setBorder(borde);
        btnContinuar.setBackground(azulClaroNeon);
        btnContinuar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Obtener el texto de cada campo.
        		String nombre = nombreCampo.getText();
        		String apellido = apellidoCampo.getText();
        		String correo = correoCampo.getText();
        		String password = passCampo.getText();
        		String fecha = fechaCampo.getText();
        		
        		CRegistrado clienteVip = new CRegistrado();
        		
        		//Comprobar que todos los campos están correctos y manejar las excepciones de los errores posibles.
        		try {
        			clienteVip.setNombre(nombre);
        			clienteVip.setApellidos(apellido);
        			clienteVip.setCorreo(correo);
        			clienteVip.setPassword(password);
        			clienteVip.setFechaNac(fecha);
        		}
        		catch(NullPointerException ex) {
        			showMessageDialog(panelContenedor, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		catch(IllegalArgumentException ex) {
        			showMessageDialog(panelContenedor, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		
        		//Una vez todo está correcto añadimos el cliente al array, guardamos y actualizamos.
        		try {
        			CRegistrado.addCliente(clienteVip);
            		CRegistrado.save();
            		CRegistrado.load();
        		}
        		catch(IllegalArgumentException ex) {
        			showMessageDialog(panelContenedor, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		
        		//Si todo ha funcionado limpiamos los campos de texto y volvemos al Panel Portal.
        		nombreCampo.setText("");
        		apellidoCampo.setText("");
        		correoCampo.setText("");
        		passCampo.setText("");
        		fechaCampo.setText("");
        		
        		cardLayout.show(panelContenedor, "panelPortal");
        	}
        });
        
        //Botón para volver al panel del protal.
        JButton btnVolverr = new JButton("Volver");
        btnVolverr.setBounds(160, 380, 100, 30);
        btnVolverr.setBorder(borde);
        btnVolverr.setBackground(azulClaroNeon);
        btnVolverr.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Limpiar Campos.
        		nombreCampo.setText("");
        		apellidoCampo.setText("");
        		correoCampo.setText("");
        		passCampo.setText("");
        		fechaCampo.setText("");
        		
        		cardLayout.show(panelContenedor, "panelPortal");
        	}
        });
        
        //Añadir todos componentes.
        panelRecuadro.add(nombreLabel);
        panelRecuadro.add(nombreCampo);
        panelRecuadro.add(apellidoLabel);
        panelRecuadro.add(apellidoCampo);
        panelRecuadro.add(correoLabel);
        panelRecuadro.add(correoCampo);
        panelRecuadro.add(passLabel);
        panelRecuadro.add(passCampo);
        panelRecuadro.add(fechaLabel);
        panelRecuadro.add(fechaCampo);
        panelRecuadro.add(btnContinuar);
        panelRecuadro.add(btnVolverr);       
        
        panelDatos.add(panelRecuadro);
        
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	public static VentanaMain getVentanaMain() {
		return ventana;
	}
	
	
	private void pasarVentana()
	{
		//De esta manera se manda un mensaje al usuario y aparecerá en el centro del panel que le indiquemos
		showMessageDialog(panelInicio, "Bienvenido de vuelta " + cliente.getNombre() + ".");
		VentanaTienda miVentana = new VentanaTienda(cliente, ventana);
		//Así la ubicacion de la nueva ventana se establece en la ubicación de la actual.
		miVentana.setLocation(this.getLocation());
		miVentana.setVisible(true);
		this.setVisible(false);
		cardLayout.show(panelContenedor, "panelPortal");
	}
}


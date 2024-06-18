package Ventanas;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;

import es.poo.cliente.*;
import es.poo.formaDePago.MetodoPago;
import es.poo.pedido.Pedido;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public abstract class MenuBarFactory {
	
	private static final String FOTO_LOGO = "C:\\Users\\ismael.mm\\Desktop\\Tienda POO\\Tienda Virtual POO IMM\\fotos\\logo.png";
	
	public static CambioPanelListener cambioPanelListener;
	public static JMenuBar menuBar;
	public static JPanel panelPerfil;
	public static JMenu menu;
	private static JLabel iconoPerfil;
	
	private static ArrayList<Pedido> listaPedidosAux = new ArrayList<Pedido>();
	private static ArrayList<MetodoPago> formasPagoAux = new ArrayList<MetodoPago>();
	private static ArrayList<String> direccionesAux = new ArrayList<String>();

	public static JMenuBar iniciarMenuBar(Cliente cliente) {
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 0, 255).brighter());
		menuBar.setLayout(new BorderLayout());

		ImageIcon icono = new ImageIcon(FOTO_LOGO);
		JLabel iconoLabel = new JLabel(icono);

		if (cliente != null && cliente instanceof CRegistrado) {
			CRegistrado clienteVip = (CRegistrado) cliente;

			String rutaFoto = clienteVip.getRutaFoto();

			ImageIcon iconoEscalado = Redimensionar(rutaFoto, 50);

			menu = new JMenu();
			JMenuItem menuItem = new JMenuItem("VER PERFIL");
			JMenuItem menuItem2 = new JMenuItem("CERRAR SESION");
			menuItem2.setForeground(Color.RED);
			menu.setIcon(iconoEscalado);

			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (cambioPanelListener != null) {
						cambioPanelListener.cambiarAPerfil();
					}
				}
			});
			
			menuItem2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(cambioPanelListener != null) {
						cambioPanelListener.cerrarSesion();
					}
				}
			});

			menu.add(menuItem);
			menu.add(menuItem2);

			menuBar.add(menu, BorderLayout.EAST);
		}
		MatteBorder borde = new MatteBorder(0, 0, 5, 0, Color.BLACK);

		menuBar.setBorder(borde);
		menuBar.add(iconoLabel, BorderLayout.WEST);

		return menuBar;
	}

	public static JPanel iniciarPanelPerfil(CRegistrado cliente) {
		panelPerfil = new JPanel();
		panelPerfil.setLayout(null);

		Border borde = BorderFactory.createLineBorder(Color.BLACK, 2);

		panelPerfil.setBorder(BorderFactory.createEmptyBorder(5, 60, 5, 60));
		panelPerfil.setBackground(new Color(255, 193, 211));

		JPanel panelBorde = new JPanel();
		panelBorde.setLayout(new BorderLayout());
		panelBorde.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 255).brighter(), 5));
		panelBorde.setBounds(290, 20, 365, 485);

		JPanel panelRecuadro = new JPanel();
		panelRecuadro.setLayout(null);
		panelRecuadro.setPreferredSize(new Dimension(355, 490));
		panelRecuadro.setBackground(new Color(255, 255, 240));

		JPanel panelFoto = new JPanel();
		panelFoto.setLayout(null);
		panelFoto.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 255).brighter(), 7));
		panelFoto.setBackground(Color.WHITE);
		panelFoto.setBounds(125, 10, 110, 110);

		String rutaFoto = cliente.getRutaFoto();
		ImageIcon iconoFoto = Redimensionar(rutaFoto, 100);
		iconoPerfil = new JLabel();
		iconoPerfil.setIcon(iconoFoto);
		iconoPerfil.setBounds(5, 5, 100, 100);

		panelFoto.add(iconoPerfil);

		panelRecuadro.add(panelFoto);

		JLabel nombreLabel = new JLabel("Nombre: ");
		nombreLabel.setBounds(25, 170, 150, 20);
		JLabel nombreCliente = new JLabel(cliente.getNombre());
		nombreCliente.setBounds(25, 185, 240, 30);
		JLabel apellidoLabel = new JLabel("Apellidos: ");
		apellidoLabel.setBounds(25, 225, 150, 20);
		JLabel apellidoCliente = new JLabel(cliente.getApellidos());
		apellidoCliente.setBounds(25, 240, 240, 30);
		JLabel correoLabel = new JLabel("Correo electrónico: ");
		correoLabel.setBounds(25, 280, 150, 20);
		JLabel correoCliente = new JLabel(cliente.getCorreo());
		correoCliente.setBounds(25, 295, 240, 30);
		JLabel fechaLabel = new JLabel("Fecha de Nacimiento: ");
		fechaLabel.setBounds(25, 335, 150, 20);
		JLabel fechaCliente = new JLabel("01/01/2003");
		fechaCliente.setBounds(25, 350, 240, 30);

		JLabel separador = new JLabel();
		separador.setBorder(BorderFactory.createLineBorder(new Color(0, 191, 255).brighter(), 2));
		separador.setBounds(15, 215, 310, 2);
		JLabel separador2 = new JLabel();
		separador2.setBorder(BorderFactory.createLineBorder(new Color(0, 191, 255).brighter(), 2));
		separador2.setBounds(15, 270, 310, 2);
		JLabel separador3 = new JLabel();
		separador3.setBorder(BorderFactory.createLineBorder(new Color(0, 191, 255).brighter(), 2));
		separador3.setBounds(15, 325, 310, 2);
		JLabel separador4 = new JLabel();
		separador4.setBorder(BorderFactory.createLineBorder(new Color(0, 191, 255).brighter(), 2));
		separador4.setBounds(15, 380, 310, 2);
		JLabel separador5 = new JLabel();
		separador5.setBorder(BorderFactory.createLineBorder(new Color(0, 191, 255).brighter(), 2));
		separador5.setBounds(15, 445, 310, 2);
		JLabel separador6 = new JLabel();

		JButton btnCambioNombre = new JButton("Cambiar Nombre");
		btnCambioNombre.setBounds(180, 175, 120, 30);
		btnCambioNombre.setBackground(new Color(0, 191, 255).brighter());
		btnCambioNombre.setBorder(borde);
		btnCambioNombre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombreNuevo = JOptionPane.showInputDialog(panelPerfil, "Ingrese el nuevo nombre: ");

				try {
					cliente.setNombre(nombreNuevo);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(panelPerfil, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(panelPerfil, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				nombreCliente.setText(cliente.getNombre());
				
				guardar(cliente);
			}
		});

		JButton btnCambioApellido = new JButton("Cambiar Apellidos");
		btnCambioApellido.setBounds(180, 230, 120, 30);
		btnCambioApellido.setBackground(new Color(0, 191, 255).brighter());
		btnCambioApellido.setBorder(borde);
		btnCambioApellido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String apellidosNuevo = JOptionPane.showInputDialog(panelPerfil, "Ingrese los nuevos apellidos: ");

				try {
					cliente.setApellidos(apellidosNuevo);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(panelPerfil, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(panelPerfil, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				apellidoCliente.setText(cliente.getApellidos());
				
				guardar(cliente);
			}
		});

		JButton btnCambioCorreo = new JButton("Cambiar Correo");
		btnCambioCorreo.setBounds(180, 285, 120, 30);
		btnCambioCorreo.setBackground(new Color(0, 191, 255).brighter());
		btnCambioCorreo.setBorder(borde);
		btnCambioCorreo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String correoNuevo = JOptionPane.showInputDialog(panelPerfil, "Ingrese el nuevo correo electrónico: ");

				try {
					cliente.setCorreo(correoNuevo);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(panelPerfil, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(panelPerfil, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				correoCliente.setText(cliente.getCorreo());
				
				guardar(cliente);
			}
		});

		JButton btnCambioPass = new JButton("Cambiar Contraseña");
		btnCambioPass.setBounds(90, 400, 160, 30);
		btnCambioPass.setBackground(new Color(0, 191, 255).brighter());
		btnCambioPass.setBorder(borde);
		btnCambioPass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String passAntiguo = JOptionPane.showInputDialog(panelPerfil, "Ingrese la antigua contrasena: ");
				String passNuevo = JOptionPane.showInputDialog(panelPerfil, "Ingrese la nueva contraseña: ");
				try {
					cliente.cambiarPassword(passNuevo, passAntiguo);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(panelPerfil, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(panelPerfil, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				guardar(cliente);
			}
		});

		JButton btnCambioFoto = new JButton("Cambiar");
		btnCambioFoto.setBounds(125, 118, 110, 30);
		btnCambioFoto.setBackground(new Color(255, 193, 211));
		btnCambioFoto.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 255).brighter(), 5));
		btnCambioFoto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String rutaFoto = getFoto();
				if (rutaFoto != null) {
					ImageIcon icono;
					cliente.setRutaFoto(rutaFoto);

					icono = Redimensionar(rutaFoto, 50);
					menu.setIcon(icono);

					icono = Redimensionar(rutaFoto, 100);
					iconoPerfil.setIcon(icono);
					
					guardar(cliente);
				}
			}
		});

		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(110, 460, 120, 30);
		btnVolver.setBackground(new Color(0, 191, 255).brighter());
		btnVolver.setBorder(borde);
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cambioPanelListener != null) {
					cambioPanelListener.regresarAPanelAnterior();
				}
			}
		});

		panelRecuadro.add(nombreLabel);
		panelRecuadro.add(nombreCliente);
		panelRecuadro.add(apellidoLabel);
		panelRecuadro.add(apellidoCliente);
		panelRecuadro.add(correoLabel);
		panelRecuadro.add(correoCliente);
		panelRecuadro.add(fechaLabel);
		panelRecuadro.add(fechaCliente);

		panelRecuadro.add(separador);
		panelRecuadro.add(separador2);
		panelRecuadro.add(separador3);
		panelRecuadro.add(separador4);
		panelRecuadro.add(separador5);
		panelRecuadro.add(separador6);

		panelRecuadro.add(btnCambioNombre);
		panelRecuadro.add(btnCambioApellido);
		panelRecuadro.add(btnCambioCorreo);
		panelRecuadro.add(btnCambioPass);
		panelRecuadro.add(btnCambioFoto);
		panelRecuadro.add(btnVolver);

		JScrollPane barraDesl = new JScrollPane(panelRecuadro);
		barraDesl.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		barraDesl.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		panelBorde.add(barraDesl, BorderLayout.CENTER);

		panelPerfil.add(panelBorde);

		return panelPerfil;
	}

	public static JFormattedTextField createDateTextField() {
		MaskFormatter mask = null;
		try {
			mask = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JFormattedTextField textField = new JFormattedTextField(mask);
		textField.setColumns(10);

		return textField;
	}

	public static JFormattedTextField createDateTextField2_0() {
		MaskFormatter mask = null;
		try {
			mask = new MaskFormatter("##/##");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JFormattedTextField textField = new JFormattedTextField(mask);
		textField.setColumns(10);

		return textField;
	}

	public static ImageIcon Redimensionar(String rutaFoto, int dimension) {
		ImageIcon icono2 = new ImageIcon(rutaFoto);
		Image imagenOriginal = icono2.getImage();
		Image imagenEscalada = imagenOriginal.getScaledInstance(dimension, dimension, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

		return iconoEscalado;
	}

	private static String getFoto() {
		JFileChooser selectorArchiu = new JFileChooser();
		selectorArchiu.setDialogTitle("Por favor arrastre una imagen");

		selectorArchiu.setFileFilter(
				new javax.swing.filechooser.FileNameExtensionFilter("Archivos de imagen", "jpg", "jpeg", "png", "gif"));

		int seleccion = selectorArchiu.showOpenDialog(panelPerfil);

		if (seleccion == JFileChooser.APPROVE_OPTION) {
			String rutaFoto = selectorArchiu.getSelectedFile().getAbsolutePath();
			return rutaFoto;
		}
		return null;
	}
	
	public static void setCambioPanelListener(CambioPanelListener listener) {
		cambioPanelListener = listener;
	}

	private static void guardar(CRegistrado cliente) {
		listaPedidosAux = cliente.getListaPedidos();
		formasPagoAux = cliente.getFormasPago();
		direccionesAux = cliente.getDirecciones();
		
		cliente.setNull();
		CRegistrado.save();
		cliente.iniciarListas();
		
		cliente.setListaPedidos(listaPedidosAux);
		cliente.setFormasPago(formasPagoAux);
		cliente.setDirecciones(direccionesAux);
	}

	

}

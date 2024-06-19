package es.poo.ventana;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Ventana extends JFrame implements Runnable{
	
	private static final int ANCHO = 1200;
	private static final int ALTO = 700;
	
	private Canvas canvas;		//Es un lienzo donde vamos a dibujar.
	
	//Necesitamos de un hilo para que el programa pueda realizar varias tareas simultáneamente-
	private Thread hilo;
	
	private boolean ejecucion = false;
	
	private BufferStrategy buffer;		//Para manipular la memoria tras la pantalla(2º plano).
	private Graphics g;					
	
	public Ventana() {
		
   //-------------------------------Crear Ventana----------------------------------------------------//
		
		setTitle("Space Invaders");		//Dar título a la ventana.
		
		setSize(ANCHO, ALTO);			//Establecer su tamaño.
		setResizable(false);			//Para que no se pueda modificar el tamaño.
		setLocationRelativeTo(null);	//La ventana inicia en el centro.
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		canvas = new Canvas();
		
		//Fijamos las dimensiones del Canvas:
		canvas.setPreferredSize(new Dimension(ANCHO, ALTO));
		canvas.setMaximumSize(new Dimension(ANCHO, ALTO));
		canvas.setMinimumSize(new Dimension(ANCHO, ALTO));
		
		canvas.setFocusable(true);		//Hacemos que el canvas pueda recibir eventos del teclado.
		
		
		add(canvas);
	}
	
	//Con esta funcion se irá actualizando el valor de los elementos del canvas.
	int x = 0;
	private void actualizar() {
		x++;
	}
	
	private void dibujar() {
		
		buffer = canvas.getBufferStrategy();	//Al principio retorna nulo, porque no hemos asignado ningun buffer al canvas.
		
		//Le asignamos 3 buffers al canvas, uno que ya está listo para ser mostrado, otro que está esperando
		//a ser mostrado y uno que está dibujando en la imagen.
		if(buffer == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		
		g = buffer.getDrawGraphics();
		
		//----------------Comienzo Dibujo-----------------------------//
		
		g.clearRect(0, 0, ANCHO, ALTO);
		g.drawRect(x, 0, 100, 100);
		
		
		//--------------------Fin Dibujo------------------------------//
		
		g.dispose();
		buffer.show();
		
	}
	
	

	//Sobreescribir método de la interfaz Runnable.
	//Esto es la putisima ejecución del programa.
	public void run() {
		
		while(ejecucion) {
			actualizar();
			dibujar();
		}
		
		
		fin();
	}
	
	//Con esto iniciamos el programa.
	public void iniciar() {
		
		hilo = new Thread(this);		
		hilo.start();
		ejecucion = true;
	}
	
	//Con esto finalizo el programa.
	private void fin() {
		try {
			hilo.join();
			ejecucion = false;
		}
		catch(InterruptedException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}

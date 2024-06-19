package es.poo.ventana;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import es.poo.grafico.Recursos;

@SuppressWarnings("serial")
public class Ventana extends JFrame implements Runnable{
	
	private static final int ANCHO = 1200;
	private static final int ALTO = 700;
	
	private static final int FPS = 60;						//Establecemos los FPS a los que va a funcionar el juego.
	private static final double REFRESH = 1000000000/FPS; 	//Establecemos la tasa de refresco (ns).
	
	
	private Canvas canvas;		//Es un lienzo donde vamos a dibujar.
	
	//Necesitamos de un hilo para que el programa pueda realizar varias tareas simultáneamente/concurrentemente
	private Thread hilo;
	
	private boolean ejecucion = false;
	
	private BufferStrategy buffer;		//Para manipular la memoria tras la pantalla(2º plano).
	private Graphics g;		
	
	private double clk = 0;				//Variable para alamcenar el tiempo que va pasando.
	private int avgFPS = FPS; 			//Nos va a permitir saber a cuantos FPS va el juego.

	
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
	private void actualizar() {
		
	}
	
	private void dibujar() {
		
		buffer = canvas.getBufferStrategy();	//Al principio retorna nulo, porque no hemos asignado ningun buffer al canvas.
		
		//Le asignamos 3 buffers al canvas, uno que ya está listo para ser mostrado, otro que está esperando
		//a ser mostrado y uno que está dibujando en la imagen. Así se consigue fluidez y eficacia.
		if(buffer == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		
		g = buffer.getDrawGraphics();
		
		//----------------Comienzo Dibujo-----------------------------//
		g.setColor(Color.BLACK);
		g.fillRect(0,  0,  ANCHO, ALTO);
		
		
		g.drawImage(Recursos.jugador, 250, 250, null);
		
		
		//--------------------Fin Dibujo------------------------------//
		
		g.dispose();
		buffer.show();
		
	}
	
	
	private void inicializar() {
		Recursos.inicializar();
	}
	

	//Este método se encarga de la ejecución del programa.
	public void run() {
		
		long ahora = 0;						//Registra el tiempo
		long lastTime = System.nanoTime(); 	//Registra la hora actual del sistema en ns.
		
		//Para registrar los FPS actuales.
		int frames = 0;
		long tiempo = 0;
		
		
		inicializar();
		
		/*Haciendo uso de las variables de tiempo marcamos la ejecución a 60 FPS,
	 	 *Esto se debe a que solo se actualiza cuando CLK vale 1, es decir cuando 
		 *se cumple la tasa de refresco.
		 */
		while(ejecucion) {
			
			ahora = System.nanoTime();
			clk += (ahora - lastTime)/REFRESH;
			
			tiempo += (ahora - lastTime);
			
			lastTime = ahora;
			
			if(clk >= 1) {
				actualizar();
				dibujar();
				clk--;				//Reiniciamos clk para el siguiente fotograma.
				frames++;
				System.out.println(frames);
			}
			
			if(tiempo >= 1000000000) {
				avgFPS = frames;
				
				frames = 0;
				tiempo = 0;
			}
			
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

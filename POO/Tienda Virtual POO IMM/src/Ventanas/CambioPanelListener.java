
package Ventanas;

/**
 * Interfaz CambioPanelListener que define un contrato para las clases de manera, que las que la implementen
 * tengan que crean un método para poder cambiar al perfil, volver del perfil al panel donde se encontraba
 * antes y poder cerrar la sesión.
 */
public interface CambioPanelListener {
	/**
	 * Método que permite cambiar del panel actual al panel del perfil.
	 */
	void cambiarAPerfil();
	/**
	 * Método que permite regresar del perfil al panel anterior.
	 */
    void regresarAPanelAnterior();
    /**
     * Método que permite cerrar la sesión y volver al panel del inicio.
     */
    void cerrarSesion();
}

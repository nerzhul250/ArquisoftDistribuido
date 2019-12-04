/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: ProcesadorImagen.java 1207 2019-05-05 07:00:03Z s.n $
 * Universidad ICESI (Cali - Colombia)
 * Licenciado bajo el esquema Academic Free License version 2.1 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package co.edu.icesi.logicaMatriz;

import java.awt.image.BufferedImage;
/**
 * Representación de los servicios que deben implementar 
 */
public interface AlgoritmosMatriz {
	/**
	 * Representa el servicio para rotar una imagen phi grados, mediante multiplicación de matrices.
	 * @param image imagen a rotar.
	 * @param phi grados a rotar.
	 * @return imagen rotada.
	 */
	public BufferedImage rotateMatrizByAngle(BufferedImage image, double phi);
}

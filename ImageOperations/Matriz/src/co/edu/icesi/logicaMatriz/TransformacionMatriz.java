/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: ProcesadorImagen.java 1207 2019-05-05 07:00:03Z s.n $
 * Universidad ICESI (Cali - Colombia)
 * Licenciado bajo el esquema Academic Free License version 2.1 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package co.edu.icesi.logicaMatriz;

import java.awt.image.BufferedImage;

import co.edu.icesi.ProcesadorImagen;
/**
 * Representa la operaciones lineales con matrices.
 */
public class TransformacionMatriz implements AlgoritmosMatriz{

	@Override
	public BufferedImage rotateMatrizByAngle(BufferedImage image, double phi) {
		long startTime = System.nanoTime();
		int width = image.getWidth();
		int height = image.getHeight();
		int midW = Math.round(width/2);
		int midH = Math.round(height/2);
		
		BufferedImage rotatedImage = new BufferedImage(width, height,
		        BufferedImage.TYPE_INT_RGB);
		double[][] rotateMatrix = calculateRotateMatrix(phi);
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				int cI = i-midW;
				int cJ = j-midH;
				
				int x1 = (int) Math.round(cI*rotateMatrix[0][0] + cJ*rotateMatrix[0][1]) + midW; 
				int y1 = (int) Math.round(cI*rotateMatrix[1][0] + cJ*rotateMatrix[1][1]) + midH;
								
				if(x1 > -1 && y1 > -1 && x1 < width && y1 < height) {
					rotatedImage.setRGB(i, j, image.getRGB(x1, y1));
				}
			}
		}
		long endTime = System.nanoTime();
		ProcesadorImagen.addString((endTime-startTime)+",");
		return rotatedImage;
	}
	/**
	 * Calcula la matriz de rotación con un ángulo phi.
	 * @param phi ángulo de rotación.
	 * @return matriz de rotación.
	 */
	private double[][] calculateRotateMatrix(double phi) {
		double cosPhi = Math.cos(Math.toRadians(phi));
		double sinPhi = Math.sin(Math.toRadians(phi));
		return new double[][] {{cosPhi, -sinPhi},
			{sinPhi, cosPhi}};
	}
}
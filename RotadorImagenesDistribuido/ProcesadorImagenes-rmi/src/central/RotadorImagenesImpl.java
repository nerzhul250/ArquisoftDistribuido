package central;

import java.io.Serializable;

import org.osoa.sca.annotations.Init;

import servicios.RotadorImagenes;

public class RotadorImagenesImpl implements RotadorImagenes, Runnable,Serializable{
	
	private static final long serialVersionUID = 30L; 

	private Pixel[] imagen;
	private double angulo;
	private int midy;
	private int midx;
	private Controlador controller;
	int startPos;
	
    // --------------------------------------------------------------------------
	// Default constructor
	// --------------------------------------------------------------------------

	public RotadorImagenesImpl(Pixel[] imagen, double angulo, int midy, int midx, Controlador controlador, int startPos) {
		this.imagen = imagen;
		this.angulo = angulo;
		this.midy = midy;
		this.midx = midx;
		this.controller = controlador;
		this.startPos = startPos;
	}
	
    @Init
    public final void init(){
        System.out.println("Controlador RMI initialized");
    }
	
	
    
    // --------------------------------------------------------------------------
	// Implementation of the RotadorImagenes interface
	// --------------------------------------------------------------------------
    
	@Override
	public Pixel[] rotarImagen(Pixel[] imagen, double angulo, int midy, int midx) {
		
		double [][] rotateMatrix = calculateRotateMatrix(angulo);
		
		for (int i = 0; i < imagen.length; i++) {
			Pixel act = imagen[i];
			if (act != null) {
				int x = act.getX();
				int y = act.getY();
				
				int cX = x - midx;
				int cY = y - midy;
				
				int x1 = (int) Math.round(cX * rotateMatrix[0][0] + cY * rotateMatrix[0][1]) + midx;
				int y1 = (int) Math.round(cX * rotateMatrix[1][0] + cY * rotateMatrix[1][1]) + midy;
				
				act.setX(x1);
				act.setY(y1);				
			}
		}
		
		return imagen;
	}
	
	
	
	private double[][] calculateRotateMatrix(double phi) {
		double cosPhi = Math.cos(Math.toRadians(phi));
		double sinPhi = Math.sin(Math.toRadians(phi));
		return new double[][] {{cosPhi, -sinPhi},
			{sinPhi, cosPhi}};
	}

	@Override
	public void run() {
		Pixel [] result = rotarImagen(imagen, angulo, midy, midx);
		for (int i = 0; i < result.length; i++) {
			controller.setPosPixel(startPos + i, result[i]);
		}
		
	}

}

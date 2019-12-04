package app;

import org.osoa.sca.annotations.Init;

/**
 * Controlador
 */
public class Controlador implements RotadorImagenes{

	int components;
	Pixel[] imagen;


    // --------------------------------------------------------------------------
	// Default constructor
	// --------------------------------------------------------------------------

    public Controlador(){
        System.out.println("Controlador Created");
    }

    @Init
    public final void init(){
        System.out.println("Controlador RMI initialized");
    }


    public void setComponents (int components) {
    	this.components = components;
    }
    
    // --------------------------------------------------------------------------
	// Implementation of the RotadorImagenes interface
	// --------------------------------------------------------------------------
    @Override
    public Pixel[] rotarImagen(Pixel[] imagen, double angulo, int midy, int midx) {
    	this.imagen = imagen;
    	int length = imagen.length;
    	int lastLimit = 0;
    	Thread [] threads = new Thread [components];
        for (int i = 0; i < components; i++) {
        	int limR = length * ((i+1)/components);
        	int newLength = limR - lastLimit;
        	Pixel [] toPass = new Pixel [newLength];
        	for (int j = 0; j < newLength; j++) {
        		toPass[j] = imagen[j + lastLimit];
        	}
        	Thread thread = new Thread(new RotadorImagenesImpl(toPass, angulo, midy, midx, this, lastLimit));
        	threads[i] = thread;
        	thread.start();
        	lastLimit = limR + 1;
        }
        
        for (Thread thread: threads) {
        	try {
				thread.join();
			} catch (InterruptedException e) {
				System.out.println("THREAD INTERRUPTED :( :( :( ");
				e.printStackTrace();
			}
        }
        return this.imagen;
    }
    
    public void setPosPixel (int i, Pixel pixel) {
    	imagen[i] = pixel;
    }

    
}
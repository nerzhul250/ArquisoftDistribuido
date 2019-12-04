package central;

import java.io.Serializable;

import org.osoa.sca.annotations.Init;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Scope;

import servicios.CoordinadorServices;
import servicios.RotadorImagenes;

@Scope("COMPOSITE")
public class Coordinador implements CoordinadorServices, Serializable, Runnable,RotadorImagenes{

	private static final long serialVersionUID = 5L;
	
	// --------------------------------------------------------------------------
	// SCA Reference
	// --------------------------------------------------------------------------
	
	@Reference(name="rotarImagen")
	private RotadorImagenes rotadorImagenes;
	
	public void setRotadorImagenes (RotadorImagenes rotadorImagenes) {
		this.rotadorImagenes = rotadorImagenes;
	}
	
	

    // --------------------------------------------------------------------------
	// Default constructor
	// --------------------------------------------------------------------------
	public Coordinador() {
		System.out.println("Coordinador Created");
	}
	
	@Init
	public final void init() {
		System.out.println("Coordinador RMI initialized");
	}
	

	@Override
	public void notificarDisponible() {
		System.out.println("Coordinador checkpoint 1");
		
		Pixel [] imagen = new Pixel [] {
				new Pixel(0, 0, 1000), new Pixel(0, 1, 1000)
		};
		
		imagen=rotadorImagenes.rotarImagen(imagen, 10, 1, 0);
		System.out.println(imagen[1].getX()+" "+imagen[1].getY());
		System.out.println("Coordinador checkpoint 2");
		
		System.out.println("Chauuuuuu");
	}

	@Override
	public void run() {
		System.out.println(rotadorImagenes != null);
		notificarDisponible();
		
	}



	@Override
	public Pixel[] rotarImagen(Pixel[] imagen, double angulo, int midy, int midx) {
		return rotadorImagenes.rotarImagen(imagen, angulo, midy, midx);
	}

}

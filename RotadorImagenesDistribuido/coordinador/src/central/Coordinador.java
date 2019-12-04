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
	
	@Reference(name="rotarImagen2")
	private RotadorImagenes rotadorImagenes2;
	
	public void setRotadorImagenes (RotadorImagenes rotadorImagenes) {
		this.rotadorImagenes = rotadorImagenes;
	}
	
	public void setRotadorImagenes2 (RotadorImagenes rotadorImagenes2) {
		this.rotadorImagenes2 = rotadorImagenes2;
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
//		System.out.println(rotadorImagenes != null);
//		notificarDisponible();
		
	}



	@Override
	public Pixel[] rotarImagen(Pixel[] imagen, double angulo, int midy, int midx) {
		int mid = imagen.length / 2;
		Pixel [] mid1 = new Pixel [mid];
		int length2 = imagen.length - (mid);
		Pixel [] mid2 = new Pixel [length2];
		for (int i = 0; i < Math.max(mid, length2); i++) {
			if (i < mid) 
				mid1[i] = imagen[i];
			if (i < length2)
				mid2[i] = imagen [mid + i];
		}
		
		mid1 = rotadorImagenes.rotarImagen(mid1, angulo, midy, midx);
		mid2 = rotadorImagenes2.rotarImagen(mid2, angulo, midy, midx);
		for (int i = 0; i < mid1.length; i++)
			imagen[i] = mid1[i];
		
		for (int i = 0; i < mid2.length; i++) {
			imagen[mid + i] = mid2[i];
		}
		return imagen;
	}

}

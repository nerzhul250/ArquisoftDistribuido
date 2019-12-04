package coordinador;

import org.osoa.sca.annotations.Init;
import org.osoa.sca.annotations.Reference;

public class CoordinadorImpl implements Coordinador, RotadorImagenes{

	
	
	// --------------------------------------------------------------------------
	// SCA Reference
	// --------------------------------------------------------------------------
	
	private RotadorImagenes rotadorImagenes;
	
	@Reference
	public void setRotadorImagenes (RotadorImagenes rotadorImagenes) {
		this.rotadorImagenes = rotadorImagenes;
	}
	
	

    // --------------------------------------------------------------------------
	// Default constructor
	// --------------------------------------------------------------------------
	public CoordinadorImpl() {
		System.out.println("Coordinador Created");
	}
	
	@Init
	public final void init() {
		System.out.println("Coordinador RMI initialized");
	}
	
	
    // --------------------------------------------------------------------------
	// Implementation of interfaces
	// --------------------------------------------------------------------------
	@Override
	public Pixel[] rotarImagen(Pixel[] imagen, double angulo, int midy, int midx) {
		rotadorImagenes.rotarImagen(imagen, angulo, midy, midx);
		return imagen;
	}

	@Override
	public void notificarDisponible() {
//		System.out.println("Coordinador checkpoint 1");
//		
//		Pixel [] imagen = new Pixel [] {
//				new Pixel(0, 0, 1000), new Pixel(0, 1, 1000)
//		};
//		
//		rotarImagen(imagen, 10, 1, 0);
//		System.out.println("Coordinador checkpoint 2");
	}

}

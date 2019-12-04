package coordinador;

import org.osoa.sca.annotations.Init;
import org.osoa.sca.annotations.Reference;

import servicios.CoordinadorServices;
import servicios.RotadorImagenes;

public class Coordinador implements CoordinadorServices{

	
	
	// --------------------------------------------------------------------------
	// SCA Reference
	// --------------------------------------------------------------------------
	
	@Reference(name="rmirotarimagen")
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
		
			rotadorImagenes.rotarImagen(imagen, 10, 1, 0);
		System.out.println("Coordinador checkpoint 2");
	}

}

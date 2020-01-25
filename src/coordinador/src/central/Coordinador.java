package central;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.osoa.sca.annotations.Init;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Scope;

import servicios.CoordinadorServices;
import servicios.RotadorImagenes;

@Scope("COMPOSITE")
public class Coordinador implements CoordinadorServices, Serializable, Runnable,RotadorImagenes{

	private static final long serialVersionUID = 5L;
	
	private static final int ACTIVE_DEVICES = 1;
	// --------------------------------------------------------------------------
	// SCA Reference
	// --------------------------------------------------------------------------
	
	@Reference(name="rotarImagen")
	private RotadorImagenes rotadorImagenes;
	
	@Reference(name="rotarImagen2")
	private RotadorImagenes rotadorImagenes2;
	
	@Reference(name="rotarImagen3")
	private RotadorImagenes rotadorImagenes3;
	
	@Reference(name="rotarImagen4")
	private RotadorImagenes rotadorImagenes4;
	
	@Reference(name="rotarImagen5")
	private RotadorImagenes rotadorImagenes5;
	
	@Reference(name="rotarImagen6")
	private RotadorImagenes rotadorImagenes6;
	
	public void setRotadorImagenes (RotadorImagenes rotadorImagenes) {
		this.rotadorImagenes = rotadorImagenes;
	}
	
	public void setRotadorImagenes2 (RotadorImagenes rotadorImagenes2) {
		this.rotadorImagenes2 = rotadorImagenes2;
	}
	
	public void setRotadorImagenes3 (RotadorImagenes rotadorImagenes3) {
		this.rotadorImagenes3 = rotadorImagenes3;
	}
	
	public void setRotadorImagenes4 (RotadorImagenes rotadorImagenes4) {
		this.rotadorImagenes4 = rotadorImagenes4;
	}
	
	public void setRotadorImagenes5 (RotadorImagenes rotadorImagenes5) {
		this.rotadorImagenes5 = rotadorImagenes5;
	}
	
	public void setRotadorImagenes6 (RotadorImagenes rotadorImagenes6) {
		this.rotadorImagenes6 = rotadorImagenes6;
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
		System.out.println(ACTIVE_DEVICES);
	}
	

	@Override
	public void notificarDisponible() {
		
		Pixel [] imagen = new Pixel [] {
				new Pixel(0, 0, 1000), new Pixel(0, 1, 1000)
		};
		
		imagen=rotadorImagenes.rotarImagen(imagen, 10, 1, 0);
		System.out.println(imagen[1].getX()+" "+imagen[1].getY());
	}

	@Override
	public void run() {
//		System.out.println(rotadorImagenes != null);
//		notificarDisponible();
		
	}



	@Override
	public Pixel[] rotarImagen(Pixel[] imagen, double angulo, int midy, int midx) {
		final RotadorImagenes [] rotadores = {rotadorImagenes, rotadorImagenes2, rotadorImagenes3, 
				rotadorImagenes4, rotadorImagenes5, rotadorImagenes6
		};
		
		ExecutorService executor = Executors.newFixedThreadPool(ACTIVE_DEVICES);
		
		final double ang = angulo;
		final int midyFinal = midy;
		final int midxFinal = midx;
		
		Future<Pixel[]> [] futures = new Future [ACTIVE_DEVICES];
		int lastStart = 0;
		int length = imagen.length;
		int ind = 0;
		for (int i = 0; i < ACTIVE_DEVICES; i++) {
			int end =(int) (length * ((i+1)/(double)ACTIVE_DEVICES));
			int realLength = end - lastStart;
			final Pixel[] imagenFinal = new Pixel[realLength];
			for (int j = 0; j < realLength; j++) {
				imagenFinal[j] = imagen[j + lastStart];
			}
			final int index = ind;
			futures[i] = executor.submit(new Callable<Pixel[]>() {
			@Override
			public Pixel[] call() throws Exception {
				// TODO Auto-generated method stub
				return rotadores[index].rotarImagen(imagenFinal, ang, midyFinal, midxFinal);
			}
		});
			
			lastStart = end + 1;
			ind++;
		}
		
		
		executor.shutdown();
		
		int lastIndex = 0;
		for (int i = 0; i < ACTIVE_DEVICES; i++) {
			try {
				Pixel [] result = futures[i].get();
				int lengthRes = result.length;
				for (int j = 0; j < lengthRes; j++) {
					imagen[j + lastIndex] = result[j];
				}
				lastIndex += lengthRes;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return imagen;
	}

}

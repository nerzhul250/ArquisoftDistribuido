package servicios;

import org.osoa.sca.annotations.Service;

import app.Pixel;

@Service
public interface RotadorImagenes {
	public Pixel[] rotarImagen(Pixel[] imagen,double angulo,int midy,int midx);
}

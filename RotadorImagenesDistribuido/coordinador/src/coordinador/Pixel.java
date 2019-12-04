package coordinador;

import java.io.Serializable;

public class Pixel implements Serializable {
	private int x,y;
	private int rgb;
	
	public Pixel(int x, int y, int rgb) {
		this.x = x;
		this.y = y;
		this.rgb = rgb;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getRgb() {
		return rgb;
	}
}

package central;

import java.io.Serializable;

public class Pixel implements Serializable {
	
	private static final long serialVersionUID = 25L; 
	
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
	
	public void setX (int x) {
		this.x = x;
	}
	
	public void setY (int y) {
		this.y = y;
	}
}

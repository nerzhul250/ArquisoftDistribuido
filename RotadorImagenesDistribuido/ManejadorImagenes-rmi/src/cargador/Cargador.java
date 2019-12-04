package cargador;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.osoa.sca.annotations.Init;
import org.osoa.sca.annotations.Reference;

public class Cargador {
	
	private RotadorImagenes coordinator;
	
	@Reference
	public void setCoordinatorService(RotadorImagenes coordinator) {
		this.coordinator=coordinator;
	}
	
	public static void main (String [] args) throws IOException {
		BufferedReader br =new BufferedReader(new FileReader(new File("comandos.txt")));
		String rutaImagenes=br.readLine().split("\\s+")[1];
		String outputImagenes=br.readLine().split("\\s+")[1];
		String line=br.readLine();
		ArrayList<Double> anglesToRotate=new ArrayList<Double>();
		while(line!=null && !line.isEmpty()) {
			double angle=Double.parseDouble(line.split("\\s+")[1]);
			anglesToRotate.add(angle);
			line=br.readLine();
		}
		File folder = new File("./"+rutaImagenes);

		String[] files = folder.list();
		for (String file : files)
		{
			for (double angles : anglesToRotate) {
				String imageRoute="./"+rutaImagenes+"/"+file;
				Rectangle sourceRegion = new Rectangle(x, y, w, h); // The region you want to extract

				ImageInputStream stream = ImageIO.createImageInputStream(input); // File or input stream
				Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);

				if (readers.hasNext()) {
				    ImageReader reader = readers.next();
				    reader.setInput(stream);

				    ImageReadParam param = reader.getDefaultReadParam();
				    param.setSourceRegion(sourceRegion); // Set region

				    BufferedImage image = reader.read(0, param); // Will read only the region specified
				}
			}
		}
    }
}

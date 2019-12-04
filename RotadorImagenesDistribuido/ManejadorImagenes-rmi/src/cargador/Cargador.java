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
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.osoa.sca.annotations.Init;
import org.osoa.sca.annotations.Reference;

public class Cargador {
	
	private static RotadorImagenes coordinator;
	
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
				File imageRoute=new File("./"+rutaImagenes+"/"+file);
				File outImageRoute=new File("./"+outputImagenes+"/out"+file);
				
				ImageInputStream stream = ImageIO.createImageInputStream(imageRoute); // File or input stream
				ImageOutputStream streamOut=ImageIO.createImageOutputStream(outImageRoute);
				
				Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
				Iterator<ImageWriter> readersOut = ImageIO.getImageWriter(streamOut);
				if (readers.hasNext()) {
				    ImageReader reader = readers.next();
				    reader.setInput(stream);
				    int height = reader.getHeight(0);
				    int width  = reader.getWidth(0) ;
				    double midH=height/2;
				    double midW=width/2;
				    int RectangleHeights=height/10;
				    for(int y=0;y<height;y+=RectangleHeights) {
				    	Rectangle sourceRegion = new Rectangle(0,y,width,RectangleHeights); // The region you want to extract
				    	ImageReadParam param = reader.getDefaultReadParam();
				    	param.setSourceRegion(sourceRegion); // Set region
				    	BufferedImage image = reader.read(0, param); // Will read only the region specified
				    	Pixel[] part=new Pixel[width*RectangleHeights];
				    	for(int i=0;i<RectangleHeights;i++) {
				    		for(int j=0;j<width;j++) {
				    			Pixel p=new Pixel();
				    			p.x=j;
				    			p.y=height-y-i;
				    			p.rgb=image.getRGB(j,i);
				    			part[i*RectangleHeights+j]=p;
				    		}
				    	}
				    	part=coordinator.rotarImagen(part,angles,midH,midW);
				    	/*System.out.println(file);
				    	File destiny=new File("./"+outputImagenes+"/"+file.split("\\.")[0]+""+y);
				    	ImageIO.write(image,"jpg",destiny);*/
				    }
				    
				}
			}
		}
    }
}

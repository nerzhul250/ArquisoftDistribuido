package central;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.osoa.sca.annotations.Init;
import org.osoa.sca.annotations.Reference;

import servicios.RotadorImagenes;


public class Cargador implements Runnable, Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5000L;
	@Reference(name="coordinator")
	private RotadorImagenes coordinator;
	
	
	public void setCoordinatorService(RotadorImagenes coordinator) {
		this.coordinator=coordinator;
	}

	@Override
	public void run() {
	try {
		System.out.println("EXECUTING");
		System.out.println(System.getProperty("user.dir"));
		BufferedReader br =new BufferedReader(new FileReader(new File("./ManejadorImagenes-rmi/comandos.txt")));
		String rutaImagenes=br.readLine().split("\\s+")[1];
		String outputImagenes=br.readLine().split("\\s+")[1];
		String line=br.readLine();
		ArrayList<Double> anglesToRotate=new ArrayList<Double>();
		while(line!=null && !line.isEmpty()) {
			double angle=Double.parseDouble(line.split("\\s+")[1]);
			anglesToRotate.add(angle);
			line=br.readLine();
		}
		File folder = new File("./ManejadorImagenes-rmi/"+rutaImagenes);

		String[] files = folder.list();
		for (String file : files)
		{
			for (double angles : anglesToRotate) {
				File imageRoute=new File("./ManejadorImagenes-rmi/"+rutaImagenes+"/"+file);
				File outImageRoute=new File("./ManejadorImagenes-rmi/"+outputImagenes+"/out"+Math.round(angles)+file);
				ImageInputStream stream = ImageIO.createImageInputStream(imageRoute); // File or input stream
				Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
				if (readers.hasNext()) {
				    ImageReader reader = readers.next();
				    reader.setInput(stream);
				    int height = reader.getHeight(0);
				    int width  = reader.getWidth(0) ;
				    int midH=height/2;
				    int midW=width/2;
				    int RectangleHeights=height/10;
					BufferedImage imageOut = 
				    new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);		
				    for(int y=0;y<height;y+=RectangleHeights) {
				    	Rectangle sourceRegion = new Rectangle(0,y,width,RectangleHeights); // The region you want to extract
				    	ImageReadParam param = reader.getDefaultReadParam();
				    	param.setSourceRegion(sourceRegion); // Set region
				    	BufferedImage image = reader.read(0, param); // Will read only the region specified
				    	Pixel[] part=new Pixel[width*RectangleHeights];
				    	for(int i=0;i<image.getHeight();i++) {
				    		for(int j=0;j<image.getWidth();j++) {
				    			Pixel p=new Pixel(j,height-y-i,image.getRGB(j,i));
				    			part[i*width+j]=p;
				    		}
				    	}
				    	 part=coordinator.rotarImagen(part,angles,midH,midW);
				    	 
				    	 for (int i = 0; i < part.length; i++) {
				    		 if(part[i]!=null && part[i].getX()<width && part[i].getX()>=0
				    				 && height-part[i].getY()<height &&height-part[i].getY()>=0) {
				    			 imageOut.setRGB(part[i].getX(),height-part[i].getY(),part[i].getRgb());
				    		 }
						}
				    	 	
				    }
				    
				    ImageIO.write(imageOut,"jpg",outImageRoute);
				}
			}
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
}

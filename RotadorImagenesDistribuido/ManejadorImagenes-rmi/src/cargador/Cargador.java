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

import com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriter;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriterSpi;

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
				Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
				if (readers.hasNext()) {
				    ImageReader reader = readers.next();
				    reader.setInput(stream);
				    int height = reader.getHeight(0);
				    int width  = reader.getWidth(0) ;
				    double midH=height/2;
				    double midW=width/2;
				    int RectangleHeights=height/10;
					BufferedImage imageOut = 
				    new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR);		
				    for(int y=0;y<height;y+=RectangleHeights) {
				    	Rectangle sourceRegion = new Rectangle(0,y,width,RectangleHeights); // The region you want to extract
				    	ImageReadParam param = reader.getDefaultReadParam();
				    	param.setSourceRegion(sourceRegion); // Set region
				    	BufferedImage image = reader.read(0, param); // Will read only the region specified
				    	Pixel[] part=new Pixel[width*RectangleHeights];
				    	for(int i=0;i<image.getHeight();i++) {
				    		for(int j=0;j<image.getWidth();j++) {
				    			Pixel p=new Pixel();
				    			p.x=j;
				    			p.y=height-y-i;
				    			p.rgb=image.getRGB(j,i);
				    			part[i*RectangleHeights+j]=p;
				    		}
				    	}
				    	 part=coordinator.rotarImagen(part,angles,midH,midW);
				    	
				    	 Rectangle destinyRegion = new Rectangle(0,y,width,RectangleHeights); // The region you want to extract
				    	 
				    	 ImageIO.write(image,"TIFF",outImageRoute);
				    	 
				    	 System.out.println(file);
	    	    		 File destiny=new File("./"+outputImagenes+"/"+file.split("\\.")[0]+""+y);
				    	 ImageIO.write(image,"jpg",destiny);
				    }
				    
				}
			}
		}
    }
}

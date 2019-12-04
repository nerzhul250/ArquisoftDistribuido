/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: ProcesadorImagen.java 1207 2019-05-05 07:00:03Z s.n $
 * Universidad ICESI (Cali - Colombia)
 * Licenciado bajo el esquema Academic Free License version 2.1 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package co.edu.icesi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import co.edu.icesi.servicios.ServicioImagen;

/**
 * Representa el entrypoint con el que se gestiona los flujos de sistema para procesar imágenes.
 */
public class ProcesadorImagen {
	/**
	 * Builder para la creación del archivo(CSV) con los tiempos de ejecución.
	 */
	private static StringBuilder stringBuilder = new StringBuilder();
	/**
	 * Ejecuta la aplicación creando la instancia de los servicios, <br>
	 * ejecutando el servicio de rotar imagen con la dirección de la <br>
	 * imagen a rotar y un angulo aleatorio. Por último, crea el archivo <br>
	 * donde se deja los resultados de la ejecución. 
	 * @param args
	 */
	public static void main(String[] args) {
		ServicioImagen test = new ServicioImagen();
		int numIterations = 2;
		Random r = new Random();
		ProcesadorImagen.addString("image,angle,timeLoad,timeRotate,timeSave\n");
		for (int i = 0; i <= numIterations; i++) {
			int angle = r.nextInt(361);
			ProcesadorImagen.addString("imagen1,"+angle+",");
			test.rotarImagen("/home/davidsalgado/Pictures/result.tif", angle);
		}
		ProcesadorImagen.createCSV("/home/davidsalgado/Pictures/test.csv");
		System.out.println("FIN");
	}
	
	/**
	 * Permite agregar al Builder un registro de tiempo.
	 * @param data registro de tiempo.
	 */
	public static void addString(String data) {
		stringBuilder.append(data);
	}
	
	/**
	 * Permite crear un archivo CSV con los tiempos de ejecución.
	 * @param path dirección donde se creará el archivo.
	 */
	public static void createCSV(String path) {
		try (PrintWriter writer = new PrintWriter(
				new File(path))) {
		      writer.write(stringBuilder.toString());
		      writer.close();
	    } catch (FileNotFoundException e) {
	      System.out.println(e.getMessage());
	    }
	}
}
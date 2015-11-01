package boot;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import presenter.Properties;
import presenter.ServerProperties;

public class startProperies {

	public static void main(String[] args) throws FileNotFoundException {
		try {
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("properties.xml")));
			int numofThreads = 30;
			String deafultAlgorithm = "My3dGenerator";
			String defaultSolver ="astar";
			encoder.writeObject(new Properties(numofThreads, deafultAlgorithm, defaultSolver,"CLI"));
			encoder.flush();
			encoder.close();
			@SuppressWarnings({ "unused", "resource" })
			XMLEncoder encoder2 = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("ServerProperties.xml")));
			int numofThreads2 = 30;
			int port = 12345;
			encoder.writeObject(new ServerProperties(numofThreads2,port));
			encoder.flush();
			encoder.close();
		} catch (Exception e) {
			System.out.println("problem with writing XML");
		}
		
		
}
	
}

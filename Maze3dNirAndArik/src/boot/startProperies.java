package boot;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import presenter.Properties;

public class startProperies {

	public static void main(String[] args) {
		try {
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("properties.xml")));
			int numofThreads = 30;
			String deafultAlgorithm = "My3dGenerator";
			String defaultSolver ="astar";
			encoder.writeObject(new Properties(numofThreads, deafultAlgorithm, defaultSolver,"CLI"));
			encoder.flush();
			encoder.close();
		} catch (Exception e) {
			System.out.println("problem with writing XML");
		}
		
		
}
	
}

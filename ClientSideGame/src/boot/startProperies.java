package boot;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import presenter.ClientProperties;
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
		
		try {
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("ClientProperties.xml")));
			int port = 12345;
			InetAddress Address = InetAddress.getLocalHost();
			System.out.println(Address.toString());
			String HostAdress = Address.getHostAddress();
			String HostName =Address.getHostName();
			encoder.writeObject(new ClientProperties(HostName,HostAdress,port));
			encoder.flush();
			encoder.close();
		} catch (Exception e) {
			System.out.println("problem with writing XML");
		}
		
		
}
	
}

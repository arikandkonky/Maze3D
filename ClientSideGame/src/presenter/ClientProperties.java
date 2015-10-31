package presenter;

import java.io.Serializable;


/**
 * 
 * @author Nir Konky and Arik Bidny
 * <h1>ClientProperties<h1>
 * ClientProperties implements Serializable and must use hes all Function
 */
@SuppressWarnings("serial")
public class ClientProperties implements Serializable{

	protected int port;
	String HostAdress ;
	String HostName;
	
	/**
	 * empty Constructor
	 * 	
	 */
	public ClientProperties(){}
			
	
	/**
	 * Constructor ,initialize all the parameters
	 * @param HostName host name of the server
	 * @param HostAdress host address of the server
	 * @param port port of the server
	 */
	public ClientProperties(String HostName,String HostAdress,int port){
		this.HostAdress = HostAdress;
		this.HostName = HostName;
		this.port= port;}

	/** 
	 * @return return true or false if it equals or not.
	 * @param obj gets object
	 */
	public boolean equals(Object obj){return this.toString().equals(obj.toString());}
			
	/**
	 * the Properties as to string with all is parameters
	 */
	public String toString(){return "Server Properties: \n"
	+ "On Host Name: "+ this.HostName + " On Adress: "+ this.HostAdress + " On port: "+ this.port;}

	/**
	 * 
	 * @return port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * set the port
	 * @param port 
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 
	 * @return the host Address
	 */
	public String getHostAdress() {
		return HostAdress;
	}
	
	/**
	 * set the host address
	 * @param hostAdress
	 */
	public void setHostAdress(String hostAdress) {
		HostAdress = hostAdress;
	}

	/**
	 * 
	 * @return the host name
	 */
	public String getHostName() {
		return HostName;
	}

	/**
	 * set the host name
	 * @param hostName
	 */
	public void setHostName(String hostName) {
		HostName = hostName;
	}

		
	


}

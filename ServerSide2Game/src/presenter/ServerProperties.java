package presenter;

/**
 * 
 * @author nir konky and arik bidny
 * <h1>ServerProperties<h1>
 * mean the server properties
 */
public class ServerProperties {

		protected int numofThreads;
		protected int port;
		
		/**
		 * empy constructor
		 */
		public ServerProperties(){}
		
		/**
		 * constructor,get numofThreads and port number
		 * @param numofThreads int
		 * @param port int
		 */
		public ServerProperties(int numofThreads,int port){
			this.port = port;
			this.numofThreads= numofThreads;}
		
		/**
		 * get port number
		 * @return port int 
		 */
		public int getPort() {
			return port;
		}
		
		/**
		 * sets port number
		 * @param port int
		 */
		public void setPort(int port) {
			this.port = port;
		}

		/**
		 * get num of threads int
		 * @return numofThreads int
		 */
		public int getNumofThreads() {return numofThreads;}
		
		/**
		 * set num of threads int
		 * @param numofThreads int
		 */
		public void setNumofThreads(int numofThreads) {this.numofThreads = numofThreads;}
			
		/**
		 * equals server properties to another with to string 
		 */
		public boolean equals(Object obj){return this.toString().equals(obj.toString());}
		
		/**
		 * server properties to string with hes fields
		 * @return String
		 */
		public String toString(){return "Server Properties: \n "
				+ "numThreads: "+numofThreads + " Port Number: "+ port;}
		
}



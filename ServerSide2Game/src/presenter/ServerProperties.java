package presenter;

public class ServerProperties {

		protected int numofThreads;
		protected int port;
		
		public ServerProperties(){}
		
		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public ServerProperties(int numofThreads,int port){
			this.port = port;
			this.numofThreads= numofThreads;}

		public int getNumofThreads() {return numofThreads;}

		public void setNumofThreads(int numofThreads) {this.numofThreads = numofThreads;}
		
		public boolean equals(Object obj){return this.toString().equals(obj.toString());}
		
		public String toString(){return "Server Properties: \n "
				+ "numThreads: "+numofThreads + " Port Number: "+ port;}
		
}



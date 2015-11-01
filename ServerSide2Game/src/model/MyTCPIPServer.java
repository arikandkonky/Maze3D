package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Nir Konky And Arik Bidny
 * @version 1.0
 * <h1>MyTCPIPServer</h1>
 * open socket and wait for connections.
 */
public class MyTCPIPServer {

	private static final String GET_SOLVE = "get solve";
	private int port;
	private Executor executer;
	private ServerSocket server;
	private boolean killServer = true;
	Socket someClient;
	int numOfClients=0;

	/**
	 * constructor get the port to listen on
	 * @param port int
	 */
	public MyTCPIPServer(int port) {
		this.port = port;
	
	}
	/**
	 * start up the server and wait for connections
	 * @param numOfClients int
	 */
	void startServer(int numOfClients){
		executer = Executors.newFixedThreadPool(numOfClients);
		try 
		{
			server=new ServerSocket(this.port);
			
			while(killServer)
			{
				System.out.println("before the try!");
				try {
					someClient = server.accept();
					System.out.println(someClient.getLocalAddress()+" ," + someClient.getOutputStream().toString());
				} catch (Exception e) {
					System.out.println("Server Closed!");
				}
				this.numOfClients++;
				//someClient.setSoTimeout(600000*80);
				System.out.println("in opening the Server");
				ObjectOutputStream output=new ObjectOutputStream(someClient.getOutputStream());
				ObjectInputStream input=new ObjectInputStream(someClient.getInputStream());
				@SuppressWarnings("unchecked")
				ArrayList<Object> line =  (ArrayList<Object>) input.readObject();
				if(line.get(0).equals(GET_SOLVE)){
					System.out.println("im here");
					executer.execute(new Thread(new ClientHandel(someClient,input,output)));
				}
				if (!(someClient.isConnected()))
				{
					this.numOfClients--;
				}
				
			}
			System.out.println("Close Server");
			if(!(server.isClosed()))
				server.close();
				((ExecutorService)executer).shutdown();
		}
		catch (Exception e)
		{
			System.out.println("tiered of waiting for connection");
		}
		finally
		{
			((ExecutorService)executer).shutdown();
		}		
	}
	
	/**
	 * stop the server.
	 */
	public void stopServer(){
		killServer = false;
		System.out.println("in the StopServer");
		try {
			if(!(server.isClosed()))
				System.out.println("Closing Server!");
				server.close();
		} catch (IOException e) {
		}
	}
	
	/**
	 * get the number of the clients online now.
	 * @return numOfClients int
	 */
	public int getNumOfClients() {
		return numOfClients;
	}

	/**
	 * set the number of clients online.
	 * @param numOfClients int
	 */
	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}

	
}
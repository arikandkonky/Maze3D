package model;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyTCPIPServer {

	private static final String GET_SOLVE = "get solve";
	private int port;
	private Executor executer;
	@SuppressWarnings("unused")
	private ServerSocket server;
	private boolean killServer = true;
	Socket someClient;
	int numOfClients=0;

	public MyTCPIPServer(int port) {
		this.port = port;
	
	}

	@SuppressWarnings("resource")
	public void startServer(int numOfClients){
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
	
	public int getNumOfClients() {
		return numOfClients;
	}

	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}

	
}
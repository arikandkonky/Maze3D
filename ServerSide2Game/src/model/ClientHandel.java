package model;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.text.Position;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;
import presenter.Properties;
import presenter.ServerProperties;

public class ClientHandel implements Runnable{

	private MyModel model;
	private Socket someClient;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	@SuppressWarnings("resource")
	public ClientHandel(Socket someClient,ObjectInputStream input, ObjectOutputStream output)  throws Exception {
		this.someClient = someClient;		
		this.input = input;
		this.output=output;
		XMLDecoder decoder=null;
		decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("properties.xml")));
		Properties properties=(Properties)decoder.readObject();
		
		XMLDecoder decoder2=null;
		decoder2=new XMLDecoder(new BufferedInputStream(new FileInputStream("ServerProperties.xml")));
		ServerProperties sp =(ServerProperties)decoder2.readObject();
		System.out.println("im here");
		model = new MyModel(someClient, properties, sp);
	}

	@Override
	public void run() {
		try {
			Maze3d ian = readMazeFromClient();
			System.out.println(ian.toString());
			String defSolver =  readDefSolverFromClient();
			System.out.println("def solver is: "+ defSolver);
			Solution<algorithms.mazeGenerators.Position> sol = model.solveMazeUser(ian, defSolver);
			output.writeObject(sol);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}

	/**
	 * This method Sends notification on the image conversion ends
	 * @param bos
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private void sendClientDoneConversion(BufferedOutputStream bos) throws IOException {
		bos.write("done".getBytes());
		bos.write("\n".getBytes());
		bos.flush();
	}

	/**
	 * This methods reads image from client output stream
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	private String readDefSolverFromClient() throws ClassNotFoundException, IOException{
		String solver = (String)input.readObject();
		return solver;
	}
	
	private Maze3d readMazeFromClient() throws IOException, ClassNotFoundException {
		System.out.println("im in readmazefromClient func");
		Maze3d maze =  (Maze3d) input.readObject();
		System.out.println(maze.toString());

		return maze;
	}
	}



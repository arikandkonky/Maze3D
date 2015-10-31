package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


/**
 * @author Nir Konky And Arik Bidny
 * @version 1.0
 * <h1>Model</h1>
 * This interface Model represent an Model in MVP design
 * all implements classes will have to implement All hes methods.
 */
public interface Model {

	/**
	 * exiting the program
	 */
	void exit();
	
	/**
	 * gets the data from the Model.
	 * @return MVP Object Data
	 */
	public Object getData();
	
	/**
	 * Get the Model Command for the MVP
	 * @return int model Completed
	 */
	int getModelCompletedCommand();

	/**
	 * return the solution of the maze
	 * @param maze maze instance
	 * @param defSolver String default solvr
	 * @return Solution<Position> Solution instance
	 * @throws IOException exception
	 * @throws ClassNotFoundException exception
	 * @throws InterruptedException exception
	 * @throws ExecutionException excepiton
	 */
	Solution<Position> solveMazeUser(Maze3d maze,String defSolver) throws IOException, ClassNotFoundException, InterruptedException, ExecutionException;

	/**
	 * return the Clients
	 */
	public void NumOfClients();
	
	/**
	 * Start the server.
	 * @param Port
	 * @param NumOfClients
	 */
	void startServer(String Port, String NumOfClients);
	
	


	
	




	
}

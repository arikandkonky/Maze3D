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

public interface Model {

	void exit();
	
	
	public Object getData();
	
	
	int getModelCompletedCommand();

	Solution<Position> solveMazeUser(Maze3d maze,String defSolver) throws IOException, ClassNotFoundException, InterruptedException, ExecutionException;

	public void NumOfClients();

	void startServer(String Port, String NumOfClients);
	
	


	
	




	
}

package model;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Model {

	void exit();
	
	
	public Object getData();
	
	
	int getModelCompletedCommand();
	
	
	
	
	
	void solveMaze(final String name, final String algorithm);
	
	
	//void getSolution(String name);


	void changeXmlFile(String filename) throws FileNotFoundException;


	void solveMazeUser(String string, String string2, String string3, String string4);


	public void solveMazeUserOnepoint(String string);


	void startServer(String numOfPlayers);
	
	


	
	




	
}

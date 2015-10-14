package model;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Model {

	void exit();
	
	
	public Object getData();
	
	
	int getModelCompletedCommand();
	
	
	void dir(String dir) throws NullPointerException;
	
	
	void generatemazewithname(final String name, final String generator , final String floor, final String line, final String col);
	
	
	void getMazeBygivenName(String name);

	
	void getcrossby(String xyz, String index,String For,String name);

	
	void saveMazeToFile(String name, String filename) throws IOException;

	
	void loadMazeToFile(String filename, String name) throws IOException;
	
	
	void MazeSize(String name);
	
	
	void fileSize(String filename);
	
	
	void solveMaze(final String name, final String algorithm);
	
	
	void getSolution(String name);


	void changeXmlFile(String filename) throws FileNotFoundException;


	void solveMazeUser(String string, String string2, String string3, String string4);


	public void solveMazeUserOnepoint(String string);
	
	


	
	




	
}

package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;


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
	 * @return int 
	 */
	int getModelCompletedCommand();
	
	/**
	 * return to the MVP the files on the dir path
	 * @param dir gets dir path
	 * @throws NullPointerException nullpointerexception
	 */
	void dir(String dir) throws NullPointerException;
	
	/**
	 * generate the maze as instance of Maze3d and return it to the MVP
	 * @param name maze name
	 * @param generator generator name
	 * @param floor floor of the maze
	 * @param line line of the maze
	 * @param col col of the maze
	 */
	void generatemazewithname(final String name, final String generator , final String floor, final String line, final String col);
	
	/**
	 * return the instance maze as Maze3d to the MVP
	 * @param name the maze name
	 */
	void getMazeBygivenName(String name);

	/**
	 * return the cross section to the MVP
	 * @param xyz cross section by x/y/z
	 * @param index cross section x on index(i)
	 * @param For Nothing(Stay on For) Dont change it!
	 * @param name maze name
	 */
	void getcrossby(String xyz, String index,String For,String name);

	/**
	 * save the instance Maze3d to the filename with name: maze name
	 * @param name maze name
	 * @param filename file name
	 * @throws IOException ioException
	 */
	void saveMazeToFile(String name, String filename) throws IOException;

	/**
	 * load maze from file name exists as Maze3d instance.
	 * @param filename file name
	 * @param name maze name
	 * @throws IOException ioException
	 */
	void loadMazeToFile(String filename, String name) throws IOException;
	
	/**
	 * return the size as bytes of the maze3d instance to MVP
	 * @param name maze name
	 */
	void MazeSize(String name);
	
	/**
	 * return the size of the file name as bytes
	 * @param filename file name
	 */
	void fileSize(String filename);
	
	/**
	 * solve the (instance of) the maze name with algorithm
	 * @param name maze name
	 * @param algorithm algorithm name
	 */
	void solveMaze(final String name, final String algorithm);
	
	/**
	 * return the solution to the MVP of the maze name
	 * @param name maze name
	 */
	void getSolution(String name);

	/**
	 * change the XML file
	 * @param filename file name
	 * @throws FileNotFoundException notfileexception
	 */
	void changeXmlFile(String filename) throws FileNotFoundException;

	/**
	 * solve the maze for the MVP (GUI)
	 * @param string maze name
	 * @param string2 x- floor of the maze
	 * @param string3 y - line of the maze
	 * @param string4 z - col of the maze
	 */
	void solveMazeUser(String string, String string2, String string3, String string4);

	/**
	 * return the solution to the MVP(GUI)
	 * @param string name of the maze name
	 */
	public void solveMazeUserOnepoint(String string);

	/**
	 * connect to the server and ask him to solve the maze, when finished, he return the solution to the MVP
	 * @param string maze name
	 * @param string2 x - floor of the maze
	 * @param string3 y - line of the maze
	 * @param string4 z - col of the maze
	 * @param Port server port
	 * @param HostName server host name
	 * @param algorithm solve algorithm
	 * @throws UnknownHostException noHostException
	 * @throws IOException ioException
	 * @throws ClassNotFoundException classnotFoundException
	 */
	void remoteSolveModel(String string, String string2, String string3, String string4,String Port,String HostName,String algorithm) throws UnknownHostException, IOException, ClassNotFoundException;
	
	


	
	




	
}

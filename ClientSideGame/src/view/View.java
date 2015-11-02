package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * @author Nir Konky and arik bidny
 * @version 1.0
 * <h1> View </h1>
 * This interface View represent an view that will talk with the client.
 * all implements classes will have to implement 
 */
public interface View {

	/**
	 * set the user command
	 * @param i int
	 */
	void setUserCommand(int i);
	
	/**
	 * return the dir path to the Out instance
	 * @param dirpath string
	 */
	void Userdir(String dirpath);
	
	/**
	 * return the error string to the Out instance
	 * @param string string
	 */
	void errorNoticeToUser(String string);
	
	/**
	 * return to the Out that the maze is ready
	 * @param name string
	 */
	void userMazeReady(String name);
	
	/**
	 * print to out, the maze as to string
	 * @param maze3dName Maze3d
	 * @param name string
	 */
	void userprintMazetouser(Maze3d maze3dName, String name);
	
	/**
	 * print to out, the Cross section of the parameters
	 * @param section int[][]
	 * @param xyz string
	 * @param index string
	 * @param name string
	 */
	void userprintCrossBySection(int[][] section, String xyz, String index, String name);
	
	/**
	 * print to out, the solve is ready.
	 * @param filename string
	 * @param name string
	 */
	void usermazeSaveToUser(String filename, String name);
	
	/**
	 * print to out, that maze has been loaded from filename
	 * @param filename string
	 * @param name string
	 */
	void userMazeLoaded(String filename, String name);
	
	/**
	 * print to out the size of the maze
	 * @param name string
	 * @param s double
	 */
	void userSizeOfMaze(String name, Double s);
	
	/**
	 * print to out the size of the file
	 * @param filename string
	 * @param s double
	 */
	void userSizeOfFile(String filename, Double s);
	
	/**
	 * print to out the solution of the maze name is ready
	 * @param name string
	 */
	void userSolutionReady(String name);
	
	/**
	 * print to out the solution of the maze name
	 * @param name string
	 * @param solution solution
	 */
	void userprintSolution(String name, Solution<Position> solution);
	
	/**
	 * 
	 * @return int
	 */
	int getUserCommand();

	/**
	 * print to out that the XML file changed.
	 * @param string string
	 * @param name String
	 * @param floor String
	 * @param line String
	 * @param col string
	 */
	void printXMLfieds(String string,String name,String floor,String line,String col);

	/**
	 * print to out the solution of the maze name
	 * @param string string
	 * @param solution solution
	 */
	void oneStateDisplay(String string, Solution<Position> solution); 

}

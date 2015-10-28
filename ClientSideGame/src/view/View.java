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
	 * @param i
	 */
	void setUserCommand(int i);
	
	/**
	 * return the dir path to the Out instance
	 * @param dirpath
	 */
	void Userdir(String dirpath);
	
	/**
	 * return the error string to the Out instance
	 * @param string
	 */
	void errorNoticeToUser(String string);
	
	/**
	 * return to the Out that the maze is ready
	 * @param name
	 */
	void userMazeReady(String name);
	
	/**
	 * print to out, the maze as to string
	 * @param maze3dName
	 * @param name
	 */
	void userprintMazetouser(Maze3d maze3dName, String name);
	
	/**
	 * print to out, the Cross section of the parameters
	 * @param section
	 * @param xyz
	 * @param index
	 * @param name
	 */
	void userprintCrossBySection(int[][] section, String xyz, String index, String name);
	
	/**
	 * print to out, the solve is ready.
	 * @param filename
	 * @param name
	 */
	void usermazeSaveToUser(String filename, String name);
	
	/**
	 * print to out, that maze has been loaded from filename
	 * @param filename
	 * @param name
	 */
	void userMazeLoaded(String filename, String name);
	
	/**
	 * print to out the size of the maze
	 * @param name
	 * @param s
	 */
	void userSizeOfMaze(String name, Double s);
	
	/**
	 * print to out the size of the file
	 * @param filename
	 * @param s
	 */
	void userSizeOfFile(String filename, Double s);
	
	/**
	 * print to out the solution of the maze name is ready
	 * @param name
	 */
	void userSolutionReady(String name);
	
	/**
	 * print to out the solution of the maze name
	 * @param name
	 * @param solution
	 */
	void userprintSolution(String name, Solution<Position> solution);
	
	/**
	 * 
	 * @return the usercommand
	 */
	int getUserCommand();

	/**
	 * print to out that the XML file changed.
	 * @param string
	 * @param string2
	 * @param i
	 * @param string3
	 */
	void printXMLfieds(String string, String string2, int i, String string3);

	/**
	 * print to out the solution of the maze name
	 * @param string
	 * @param solution
	 */
	void oneStateDisplay(String string, Solution<Position> solution); 

}

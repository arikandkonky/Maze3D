package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface View {

	void setUserCommand(int i);
	
	
	void Userdir(String dirpath);
	
	
	void errorNoticeToUser(String string);
	
	
	void userMazeReady(String name);
	
	
	void userprintMazetouser(Maze3d maze3dName, String name);
	
	
	void userprintCrossBySection(int[][] section, String xyz, String index, String name);
	
	
	void usermazeSaveToUser(String filename, String name);
	
	
	void userMazeLoaded(String filename, String name);
	
	
	void userSizeOfMaze(String name, Double s);
	
	
	void userSizeOfFile(String filename, Double s);
	
	
	void userSolutionReady(String name);
	
	
	void userprintSolution(String name, Solution<Position> solution);
	
	
	int getUserCommand();


	void printXMLfieds(String string, String string2, int i, String string3);


	void oneStateDisplay(String string, Solution<Position> solution); 










}

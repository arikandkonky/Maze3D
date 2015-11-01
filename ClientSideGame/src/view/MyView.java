package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

//***
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Command;

/**
 * @author Nir Konky and arik bidny
 * @version 1.0
 * <h1> MyView </h1>
 * This Class MyView implements view and extends Observable.
 * all implements classes will have to implement 
 * all extends classes will have to do the methods
 */
public class MyView extends Observable implements View {
	CLI cli;
	HashMap<String, Command> StringtoCommand;
	BufferedReader in;
	PrintWriter out;
	int userCommand =0;
	
	/**
	 * empty constructor
	 */
	public MyView(){super();}
	
	/**
	 * initialize in and out instances
	 * @param in BufferedReader
	 * @param out PrintWriter
	 */
	public MyView(BufferedReader in, PrintWriter out)
	{
		super();
		this.in = in;
		this.out = out;
	}
	
	/**
	 * initialize in, out and hash map {String, Command}
	 * @param in BufferedReader
	 * @param out printWriter
	 * @param stringtoCommand hashmap
	 */
	public MyView(BufferedReader in, PrintWriter out,HashMap<String,Command> stringtoCommand)
	{
		super();
		cli = new CLI(in,out,StringtoCommand);
	}
	
	/**
	 * start the CLI(command line)
	 */
	public void start() {cli.start();}
	
	/**
	 * set the hash map string to command and make instance of the CLI
	 * @param stringtoCommand hashmap
	 */
	public void setStringtoCommand(HashMap<String, Command> stringtoCommand) {
		this.StringtoCommand = stringtoCommand;
		cli = new CLI(in,out,StringtoCommand);
	}
	/**
	 * @return the user command
	 */
	public int getUserCommand() {return userCommand;}

	/**
	 * set the user command
	 */
	public void setUserCommand(int userCommand) 
	{
		this.setChanged();
		this.userCommand = userCommand;
	}
	
	/**
	 * private method, print the Cross Arr of the maze
	 * @param arr int[][]
	 */
	public void printArr(int[][] arr)
	{
		String strOfMazeMatrix="";
		for (int i=0;i<arr.length;i++)
		{
			strOfMazeMatrix+="{";
			for(int j=0;j<arr[0].length;j++){strOfMazeMatrix+=arr[i][j];}
			strOfMazeMatrix+="}\n";
		}
		out.println(strOfMazeMatrix);
		out.flush();
	}

	/**
	 * return the dir path to the Out instance
	 * @param dirpath string
	 */
	public void Userdir(String dirpath)
	{
		out.println("Files and Dir in: "+dirpath+"\n");
		out.flush();
	}
	/**
	 * return to the Out that the maze is ready
	 * @param name string
	 */
	public void userMazeReady(String name)
	{
		out.println("View: \n Maze "+ name + "Ready!!");
		out.flush();
	}
	
	/**
	 * print to out, the maze as to string
	 * @param maze3dName Maze3d
	 * @param name string
	 */
	public void userprintMazetouser(Maze3d maze3dName, String name)
	{
		out.println("View: \n Maze: "+ name + "\n" + maze3dName.toString());
		out.flush();
	}
	
	/**
	 * print to out, the Cross section of the parameters
	 * @param section int[][]
	 * @param xyz string
	 * @param index string
	 * @param name string
	 */
	public void userprintCrossBySection(int[][] section, String xyz, String index, String name)
	{
		out.println("View: \n Cross section maze: " + name + " by X/Y/Z "+ xyz + " in index: "+ index);
		out.flush();
		printArr(section);
	}
	
	/**
	 * print to out, the solve is ready.
	 * @param filename string
	 * @param name string
	 */
	public void usermazeSaveToUser(String filename, String name)
	{
		out.println("View: \n The Maze: "+name+" saved on file name:"+ filename);
		out.flush();
	}
	
	/**
	 * print to out, that maze has been loaded from filename
	 * @param filename string
	 * @param name string
	 */
	public void userMazeLoaded(String filename, String name)
	{
		out.println("View: \n The Maze: "+name+ " has been loaded from file name: "+ filename);
		out.flush();
	}
	
	/**
	 * print to out the size of the maze
	 * @param name string
	 * @param s double
	 */
	public void userSizeOfMaze(String name, Double s)
	{
		out.println("View: \n The Size of maze: "+ name+ " in bytes: " + s);
		out.flush();
	}
	
	
	/**
	 * print to out the size of the file
	 * @param filename string
	 * @param s double
	 */
	public void userSizeOfFile(String filename, Double s)
	{
		out.println("View: \n The Size of file name: "+ filename + " in bytes: " + s);
		out.flush();
	}
	

	/**
	 * print to out the solution of the maze name is ready
	 * @param name string
	 */
	public void userSolutionReady(String name)
	{
		out.println("View: \n The Solution of the maze :" + name + " is Ready!");
		out.flush();
	}
	
	/**
	 * print to out the solution of the maze name
	 * @param name string
	 * @param solution Solution
	 */
	public void userprintSolution(String name, Solution<Position> solution)
	{
		out.println("View: \n Solution of maze :" + name + "\n");
		out.flush();
		for(State<Position> p :solution.getSolution())
		{
			out.println(p.getCamefrom() + " " + p.getActionName() + " to: " + p.toString());
			out.flush();
		}
	}
	
	/**
	 * 
	 * @return in instance
	 */
	public BufferedReader getIn() {return in;}

	/**
	 * sets the in instance
	 * @param in BufferedReader
	 */
	public void setIn(BufferedReader in) {this.in = in;}

	/**
	 * @return out instance
	 */
	public PrintWriter getOut() {return out;}

	/**
	 * sets the out instance
	 * @param out printWriter
	 */
	public void setOut(PrintWriter out) {this.out = out;}

	/**
	 * 
	 * @return hash map string to command
	 */
	public HashMap<String, Command> getStringtoCommand() {return StringtoCommand;}

	/**
	 * print to out the Error 
	 */
	@Override
	public void errorNoticeToUser(String string) {out.println("Notiflication: \n" + string);}

	/**
	 * print to out the XML fields
	 */
	@Override
	public void printXMLfieds(String string, String string2, int i, String string3) {out.println("XML fieds: \n Defualt algorithm: "+ string + "Defualt solver: "+ string2 + "Num of threads: "+ i+ "Default UI: "+ string3);}

	/**
	 * for MVP design
	 */
	@Override
	public void oneStateDisplay(String string, Solution<Position> solution) {}
	
	
	
}

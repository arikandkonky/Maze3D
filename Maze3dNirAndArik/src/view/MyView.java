package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import Controller.Controller;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Command;

public class MyView extends Observable implements View {
	Controller controller;
	CLI cli;
	HashMap<String, Command> StringtoCommand;
	BufferedReader in;
	PrintWriter out;
	int userCommand =0;
	
	public MyView()
	{
		super();
	}
	
	public MyView(BufferedReader in, PrintWriter out)
	{
		super();
		this.in = in;
		this.out = out;
	}
	
	public MyView(Controller controller)
	{
		super();
		this.controller = controller;
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.out = new PrintWriter(System.out);
	}
	
	public MyView(Controller controller, BufferedReader in, PrintWriter out,HashMap<String,Command> stringtoCommand)
	{
		super();
		this.controller = controller;
		cli = new CLI(in,out,StringtoCommand);
	}
	public void start() {cli.start();}
	

	public void setStringtoCommand(HashMap<String, Command> stringtoCommand) {
		this.StringtoCommand = stringtoCommand;
		cli = new CLI(in,out,StringtoCommand);
	}

	public int getUserCommand() {return userCommand;}

	public void setUserCommand(int userCommand) 
	{
		this.setChanged();
		this.userCommand = userCommand;
	}
	
	
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

	public void Userdir(String dirpath)
	{
		out.println("Files and Dir in: "+dirpath+"\n");
		out.flush();
	}
	
	public void userMazeReady(String name)
	{
		out.println("View: \n Maze "+ name + "Ready!!");
		out.flush();
	}
	
	public void userprintMazetouser(Maze3d maze3dName, String name)
	{
		out.println("View: \n Maze: "+ name + "\n" + maze3dName.toString());
		out.flush();
	}
	
	public void userprintCrossBySection(int[][] section, String xyz, String index, String name)
	{
		out.println("View: \n Cross section maze: " + name + " by X/Y/Z "+ xyz + " in index: "+ index);
		out.flush();
		printArr(section);
	}
	
	public void usermazeSaveToUser(String filename, String name)
	{
		out.println("View: \n The Maze: "+name+" saved on file name:"+ filename);
		out.flush();
	}
	
	public void userMazeLoaded(String filename, String name)
	{
		out.println("View: \n The Maze: "+name+ " has been loaded from file name: "+ filename);
		out.flush();
	}
	
	public void userSizeOfMaze(String name, Double s)
	{
		out.println("View: \n The Size of maze: "+ name+ " in bytes: " + s);
		out.flush();
	}
	
	public void userSizeOfFile(String filename, Double s)
	{
		out.println("View: \n The Size of file name: "+ filename + " in bytes: " + s);
		out.flush();
	}
	
	public void userSolutionReady(String name)
	{
		out.println("View: \n The Solution of the maze :" + name + " is Ready!");
		out.flush();
	}
	
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
	
	
	
	
	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public HashMap<String, Command> getStringtoCommand() {
		return StringtoCommand;
	}

	@Override
	public void errorNoticeToUser(String string) {
		out.println("Notiflication: \n" + string);
	}

	@Override
	public void printXMLfieds(String string, String string2, int i, String string3) {
		out.println("XML fieds: \n Defualt algorithm: "+ string + 
				"Defualt solver: "+ string2 + "Num of threads: "+ i+ 
				"Default UI: "+ string3);
		
	}
	
	
	
}

package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Command;

public class MyView extends Observable implements View {
	CLI cli;
	HashMap<String, Command> StringtoCommand;
	BufferedReader in;
	PrintWriter out;
	int userCommand =0;
	
	
	public MyView(BufferedReader in, PrintWriter out)
	{
		super();
		this.in = in;
		this.out = out;
	}
	
	public MyView()
	{
		super();
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.out = new PrintWriter(System.out);
	}
	
	public MyView( BufferedReader in, PrintWriter out,HashMap<String,Command> stringtoCommand)
	{
		super();
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
	public void serverStarted(String port,String numOfClients) {
		System.out.println("Server is Up!");
		//out.print("Server is Up! With Num of Clients: "+ data);
		
	}

	@Override
	public void serverStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void NumOfClients(int clients) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

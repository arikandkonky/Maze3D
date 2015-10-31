package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

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
	 * initialize in and out instances
	 * @param in PrintWriter
	 * @param out BufferedReader
	 */
	public MyView(BufferedReader in, PrintWriter out)
	{
		super();
		this.in = in;
		this.out = out;
	}
	
	/**
	 * empty constructor
	 */
	public MyView()
	{
		super();
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.out = new PrintWriter(System.out);
	}
	
	/**
	 * initialize in, out and hash map {String, Command}
	 * @param in BufferedReader
	 * @param out PrintWriter
	 * @param stringtoCommand hashmap
	 */
	public MyView( BufferedReader in, PrintWriter out,HashMap<String,Command> stringtoCommand)
	{
		super();
		cli = new CLI(in,out,StringtoCommand);
	}
	
	/**
	 * start the cli
	 */
	public void start() {cli.start();}
	
	/**
	 * set hash map string to command and make CLI object with the parameters he need.
	 * @param stringtoCommand hashmap
	 */
	public void setStringtoCommand(HashMap<String, Command> stringtoCommand) {
		this.StringtoCommand = stringtoCommand;
		cli = new CLI(in,out,StringtoCommand);
	}

	/**
	 * get user command
	 * @return userCommand int
	 */
	public int getUserCommand() {return userCommand;}

	/**
	 * set user command
	 * @param userCommand int
	 */
	public void setUserCommand(int userCommand) 
	{
		this.setChanged();
		this.userCommand = userCommand;
	}
	
	/**
	 * get in object
	 * @return in object
	 */
	public BufferedReader getIn() {
		return in;
	}

	/**
	 * set in object
	 * @param in BufferedReader
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}

	/**
	 * get out object
	 * @return out PrintWriter
	 */
	public PrintWriter getOut() {
		return out;
	}

	/**
	 * set out object
	 * @param out PrintWriter
	 */
	public void setOut(PrintWriter out) {
		this.out = out;
	}

	/**
	 * get hash map string > command
	 * @return stringtoCommand hashmap
	 */
	public HashMap<String, Command> getStringtoCommand() {
		return StringtoCommand;
	}

	@Override
	/**
	 * set hash map string > command
	 * @param String string
	 */
	public void errorNoticeToUser(String string) {
		out.println("Notiflication: \n" + string);
	}

	@Override
	/**
	 * start the server in GUI
	 * @param port string
	 * @param numOfClients string
	 */
	public void serverStarted(String port,String numOfClients) {
		System.out.println("Server is Up!");
		//out.print("Server is Up! With Num of Clients: "+ data);
		
	}

	@Override
	/**
	 * stop the server in the GUI and close all the threads.
	 */
	public void serverStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * print to out the number of clients that now online.
	 * @param clients int
	 */
	public void NumOfClients(int clients) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

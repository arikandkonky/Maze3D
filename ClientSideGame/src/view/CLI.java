package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import presenter.Command;


/**
 * @author Nir Konky and arik bidny
 * @version 1.0
 * <h1>CLI</h1>
 * This class defining the start of the project, implements Runnable.
 */
public class CLI implements Runnable {

	BufferedReader in; 
	PrintWriter out; 
	HashMap<String,Command> stringtoCommand;
	HashSet<String> commandsStrings; 
	private Scanner scanner;

	/**
	 * This Constractor get 3 parameters : BufferedReader in, PrintWriter out, hashMap<String,Command> Stringtocommands.
	 *@param BufferedReader in represent from what the view will get the parameters from the client.
	 *@param PrintWriter out represent to where the view will print hes methods.
	 *@param hashMap<String,Command> Stringtocommands represent the commands that he will work on him.
	 *@return CLI As instance with the given BufferedReader in, PrintWriter out, hashMap<String,Command> Stringtocommands.
    */
	public CLI(BufferedReader in, PrintWriter out,HashMap<String, Command> stringtoCommand2) { 
		super(); 
		this.in = in; 
		this.out = out; 
		this.stringtoCommand = stringtoCommand2;
		commandsStrings = new HashSet<>(stringtoCommand2.keySet()); 
		} 

	/**
	 * The goal is to start the project with the CLI parameters
	 */
	public void start(){
		scanner = new Scanner(in);
		String inputString = "";
		String[] inputStringAsArray = {};
		
		
		while(inputString.length()==0){
			inputString = scanner.nextLine();
			inputString = inputString.toLowerCase().replaceAll("\\s+", " ").trim();
			inputStringAsArray = inputString.split(" ");
		}
		
		while(!inputString.startsWith("exit"))
		{
			String commandString = new String(inputString);
			String commandArgs = new String();
			int i=0;
			
			boolean commandFound = false;
			while(!commandFound)
			{
				if(stringtoCommand.containsKey(commandString))
				{
					
					commandFound = true;
					commandArgs = commandArgs.trim();
					if(commandArgs.length()>0)
					{
		
						List<String> list =  Arrays.asList(commandArgs.split(" "));
						Collections.reverse(list);
						String[] arg_string_to_command = (String[])list.toArray();
						stringtoCommand.get(commandString).doCommand(arg_string_to_command);
						
					}
					else if(commandArgs.length() ==0)
					{
						stringtoCommand.get(commandString).doCommand(new String[1]);
					}
				}
				else if (!stringtoCommand.containsKey(commandString))
				{
					if(commandString.length()-inputStringAsArray[inputStringAsArray.length-1].length()-1 <=-1){
						break;
					}
					else
					{
						i++;
						commandString = commandString.substring(0, commandString.length()-inputStringAsArray[inputStringAsArray.length-i].length()-1);
						commandArgs += inputStringAsArray[inputStringAsArray.length-i]+ " ";
					}
						
				}
			}
		
			if(!stringtoCommand.containsKey(commandString))
			{
				out.println("Enter a valid command.");
				out.flush();
			}
			inputString = "";
			while(inputString.length() == 0 ){
				inputString = scanner.nextLine(); 
				inputString = inputString.toLowerCase().replaceAll("\\s+", " ").trim(); 
				inputStringAsArray = inputString.split(" "); 
			}
		}
		stringtoCommand.get(inputStringAsArray[0]).doCommand(inputStringAsArray); 
		out.println("Exiting from the program...Bye!"); 
		out.flush(); 
		try {in.close();} catch (IOException e) {e.printStackTrace();}out.close();
		
	}
	
	/**
	 * print to out that the CLI class Running and start the start func
	 */
	@Override
	public void run() {
		out.println("Running Run method of CLI class");
		out.flush();
		start();
	}
	
	/**
	 * 
	 * @return in instance
	 */
	public BufferedReader getIn() {return in;}

	/**
	 * sets in instance
	 * @param in
	 */
	public void setIn(BufferedReader in) {this.in = in;}

	/**
	 * 
	 * @return out instance
	 */
	public PrintWriter getOut() {return out;}

	/**
	 * sets the out instance
	 * @param out
	 */
	public void setOut(PrintWriter out) {this.out = out;}

	/**
	 * @return the hash map string to command
	 */
	public HashMap<String, Command> getStringtoCommand() {return stringtoCommand;}

	/**
	 * sets the hash map string to command
	 * @param stringtoCommand
	 */
	public void setStringtoCommand(HashMap<String, Command> stringtoCommand) {
		this.stringtoCommand = stringtoCommand;
		commandsStrings = new HashSet<>(stringtoCommand.keySet()); 
	}
	
}

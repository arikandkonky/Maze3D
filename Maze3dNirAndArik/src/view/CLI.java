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

public class CLI implements Runnable {

	BufferedReader in; 
	PrintWriter out; 
	HashMap<String,Command> stringtoCommand;
	HashSet<String> commandsStrings; 
	private Scanner scanner;

	public CLI(BufferedReader in, PrintWriter out,HashMap<String, Command> stringtoCommand2) { 
		super(); 
		this.in = in; 
		this.out = out; 
		this.stringtoCommand = stringtoCommand2;
		commandsStrings = new HashSet<>(stringtoCommand2.keySet()); 
		} 


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
	@Override
	public void run() {
		out.println("Running Run method of CLI class");
		out.flush();
		start();

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
		return stringtoCommand;
	}

	public void setStringtoCommand(HashMap<String, Command> stringtoCommand) {
		this.stringtoCommand = stringtoCommand;
		commandsStrings = new HashSet<>(stringtoCommand.keySet()); 
	}
	
}

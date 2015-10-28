package presenter;


import java.util.HashMap;
import java.util.Observer;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

import java.util.Observable;
import model.Model;
import view.View;

public class Presenter implements Observer {
	View view;
	Model model;
	public HashMap<String,Command> stringtoCommand;
	
	public Presenter(final View view, final Model model){
		super();
		this.view=view;
		this.model = model;
		stringtoCommand = new HashMap<String,Command>();
		
		stringtoCommand.put("start server", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try{
					System.out.println("--->Im in the Command Map Doing Start Server Command!");
					view.setUserCommand(1);
					((Observable)view).notifyObservers(args);
				}catch (NullPointerException e){e.printStackTrace();}
				
			}
		});
		
		
		stringtoCommand.put("Num Of Clients", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try{
					view.setUserCommand(2);
					((Observable)view).notifyObservers(args);
				}catch (NullPointerException e){e.printStackTrace();}
				
			}
		});
				
		stringtoCommand.put("Solve Maze Point", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try{
					view.setUserCommand(2);
					((Observable)view).notifyObservers(args);
				}catch (NullPointerException e){e.printStackTrace();}
				
			}
		});
		
		
		stringtoCommand.put("display solution one", new Command() 
		{
			
			@Override
			public void doCommand(String[] args) 
			{
				try {
					view.setUserCommand(3);
					((Observable)view).notifyObservers(args);
				} catch (NullPointerException e) {e.printStackTrace();}
			}
		});
		
		stringtoCommand.put("exit", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				view.setUserCommand(4);
				((Observable)view).notifyObservers(args);
			}
		});
		
	}
	
	
	
	
	public View getView() {
		return view;
	}




	public void setView(View view) {
		this.view = view;
	}




	public Model getModel() {
		return model;
	}




	public void setModel(Model model) {
		this.model = model;
	}




	public HashMap<String, Command> getStringtoCommand() {
		return stringtoCommand;
	}




	public void setStringtoCommand(HashMap<String, Command> stringtoCommand) {
		this.stringtoCommand = stringtoCommand;
	}




	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if(o==view)
		{
			String[] args = ((String[])arg).clone();
			int input = view.getUserCommand();
			switch (input) {	
			case 1:
				try {
					System.out.println("--->Trying to do model.startServer with the given arguments!");
					model.startServer(args[0],args[1]);
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Error opening the Server");
				}
				break;
			case 2:
				
				try{model.NumOfClients();}
				catch(Exception e){e.printStackTrace();}
				
				break;
			case 4:
				try {model.exit();
					
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Error: bad Args");
				}
				break;

			default:
				break;
			}
		}
		else if(o==model)
		{
			int modelNum  = model.getModelCompletedCommand();
			Object[] dataSet;
			switch (modelNum) {
			case 1:
				dataSet = (Object[]) model.getData();
				System.out.println((String)dataSet[0]+" , "+(String)dataSet[1]);
				view.serverStarted((String)dataSet[0],(String)dataSet[1]);
				break;
			case -1:
				view.errorNoticeToUser((String)model.getData());
				break;
			case 2:
				int a = (int) model.getData();
				view.NumOfClients(a);
			case 11:
				view.serverStop();
			default:
				break;

			}
		}
		
		
	}
	
	
}

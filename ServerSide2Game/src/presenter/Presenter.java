package presenter;


import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

/**
 * @author Nir Konky And Arik Bidny
 * @version 1.0
 * <h1>Presenter</h1>
 * This class Presenter implements Observer and represent an Presenter in MVP design
 * Must Implements all the Observer Function
 */
public class Presenter implements Observer {
	View view;
	Model model;
	public HashMap<String,Command> stringtoCommand;
	
	/**
	 * Constructor, do Super() and initialize the parameters
	 * @param view View object
	 * @param model model object
	 */
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
	
	
	
	/**
	 * get view object
	 * @return view object
	 */
	public View getView() {
		return view;
	}

	/**
	 * sets view object
	 * @param view View object
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * get model object
	 * @return model object
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * sets model object
	 * @param model object
	 */
	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * get hash map String to Command
	 * @return stringtocommand hashmap
	 */
	public HashMap<String, Command> getStringtoCommand() {
		return stringtoCommand;
	}
	
	/**
	 * set hash map string to command 
	 * @param stringtoCommand hashmap
	 */
	public void setStringtoCommand(HashMap<String, Command> stringtoCommand) {
		this.stringtoCommand = stringtoCommand;
	}

	@Override
	/**
	 * update function, MVP design.
	 * @param Observable o
	 * @param Object arguments
	 */
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

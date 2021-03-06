package presenter;


import java.util.HashMap;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
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
		
		stringtoCommand.put("Start Server", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try{
					view.setUserCommand(1);
					((Observable)view).notifyObservers(args);
				}catch (NullPointerException e){e.printStackTrace();}
				
			}
		});
		
		
		stringtoCommand = new HashMap<String,Command>();
		
		stringtoCommand.put("Solve Maze Point", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try{
					view.setUserCommand(13);
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
					view.setUserCommand(14);
					((Observable)view).notifyObservers(args);
				} catch (NullPointerException e) {e.printStackTrace();}
			}
		});
		
		stringtoCommand.put("exit", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				view.setUserCommand(11);
				((Observable)view).notifyObservers(args);
				model.exit();}
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
				try {model.startServer(args[0]);
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			/*case 9:
				try {model.solveMaze(args[0], args[1]);
					
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Error: bad Args");
				}
				break;
				*/
			case 11:
				try {model.exit();
					
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Error: bad Args");
				}
				break;
			
			case 12:
				try{model.changeXmlFile(args[0]);
				
				}catch(Exception e){
					e.printStackTrace();
					view.errorNoticeToUser("Error: Not XML file");
				}
			case 13:
				try{model.solveMazeUser(args[0],args[1],args[2],args[3]);
				
				}catch(Exception e){
					e.printStackTrace();
					view.errorNoticeToUser("Error: ...need to fix it!");
					
				}
			case 14:
				try{model.solveMazeUserOnepoint(args[0]);
				
				}catch(Exception e){
					e.printStackTrace();
					view.errorNoticeToUser("Error: ...need to fix it!");
					
				}
				
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
				view.serverStarted((String) model.getData());
				break;

			case 2:
				try {
					view.userMazeReady((String) model.getData());

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case 3:
				dataSet = (Object[]) model.getData();
				view.userprintMazetouser((Maze3d)dataSet[0], (String) dataSet[1]);
				break;
				
			case 4:
				dataSet = (Object[]) model.getData();
				view.userprintCrossBySection((int[][])dataSet[0],(String)dataSet[1],(String)dataSet[2],(String)dataSet[3]);
				break;
				
			case 5:
				dataSet = (Object[]) model.getData();
				view.usermazeSaveToUser((String)dataSet[0],(String) dataSet[1]);
				break;
				
			case 6:
				dataSet = (Object[]) model.getData();
				view.userMazeLoaded((String)dataSet[0],(String) dataSet[1]);
				break;
				
			case 7:
				dataSet = (Object[]) model.getData();
				view.userSizeOfMaze((String) dataSet[0],(Double) dataSet[1]);
				break;
				
			case 8:	
				dataSet = (Object[]) model.getData();
				view.userSizeOfFile((String) dataSet[0],(Double) dataSet[1]);
				break;
			
			case 9:
				
				view.userSolutionReady((String)model.getData());
				break;
				
			case 10:
				dataSet = (Object[]) model.getData();
				System.out.println(dataSet[0]+""+dataSet[1]);
				view.userprintSolution((String)dataSet[0], (Solution<Position>)dataSet[1]);
				break;
				
			case 11:
				dataSet = (Object[])model.getData();
				view.printXMLfieds((String)dataSet[0],(String)dataSet[1],(int)dataSet[2],(String)dataSet[3]);
				break;
			case 12:
				dataSet = (Object[])model.getData();
				view.oneStateDisplay((String)dataSet[0], (Solution<Position>)dataSet[1]);
			case -1:
				view.errorNoticeToUser((String)model.getData());
				break;
				
				
			default:
				break;

			}
		}
		
		
	}
	
	
}

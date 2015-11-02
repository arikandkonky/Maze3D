package presenter;


import java.util.HashMap;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

import java.util.Observable;
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
		
		
		stringtoCommand.put("Remote Solve", new Command() 
		{
			
			@Override
			public void doCommand(String[] args) 
			{
				try
				{
					view.setUserCommand(15);
					((Observable)view).notifyObservers(args);
				}catch (NullPointerException e){e.printStackTrace();}
				
			}
		});
		
		
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
		
		stringtoCommand.put("dir", new Command() 
		{
			
			@Override
			public void doCommand(String[] args) 
			{
				try {
					view.setUserCommand(1);
					((Observable)view).notifyObservers(args);
				} catch (NullPointerException e) {e.printStackTrace();}
			}
		});
		
		stringtoCommand.put("generate 3d maze", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try {
					view.setUserCommand(2);
					((Observable)view).notifyObservers(args);
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Wrong parameters, use: generate 3d maze <name> <generator> <floor> <line> <col>");
				}
			}
		});
		
		stringtoCommand.put("display", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try {
					view.setUserCommand(3);
					((Observable)view).notifyObservers(args);
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Error: There is no maze in this name");
				}
			}
		});
		
		stringtoCommand.put("display cross section by", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try {
					view.setUserCommand(4);
					((Observable)view).notifyObservers(args);
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Wrong parameters, use: display cross section by {floor-X,line-Y,col-Z} <index> for <name> ");
				}
			}
		});
		
		stringtoCommand.put("save maze", new Command() {
			@Override
			public void doCommand(String[] args) {
				try {
					view.setUserCommand(5);
					((Observable)view).notifyObservers(args);
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Wrong parameters, use: save maze <name> <filename>");
				}
			}
		});
		
		stringtoCommand.put("load maze", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try {
					view.setUserCommand(6);
					((Observable)view).notifyObservers(args);
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Wrong parameters, use: load maze <filename> <name>");
				}
			}
		});
		
		stringtoCommand.put("maze size", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try {
					view.setUserCommand(7);
					((Observable)view).notifyObservers(args);
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Wrong parameters, use: maze size <name>");
				}
			}
		});
		
		stringtoCommand.put("change xml", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try {
					System.out.println("in the Command map doing usercommand 12!");
					view.setUserCommand(12);
					((Observable)view).notifyObservers(args);
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Wrong parameters, use: file size <filename>");
				}
			}
		});
		
		stringtoCommand.put("file size", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try {
					view.setUserCommand(8);
					((Observable)view).notifyObservers(args);
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Wrong parameters, use: file size <filename>");
				}
			}
		});
		
		stringtoCommand.put("solve", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try {
					view.setUserCommand(9);
					((Observable)view).notifyObservers(args);
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Wrong parameters, use: solve <name> <algorithm>");
				}
			}
		});
		
		stringtoCommand.put("display solution", new Command() {
			
			@Override
			public void doCommand(String[] args) {
				try {
					view.setUserCommand(10);
					((Observable)view).notifyObservers(args);
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Wrong parameters, use: display solution <name>");
				}
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
	
	
	
	/**
	 * 
	 * @return the view object
	 */
	public View getView() {return view;}

	/**
	 * set the View instance
	 * @param view View
	 */
	public void setView(View view) {this.view = view;}

	/**
	 * 
	 * @return model instance
	 */
	public Model getModel() {return model;}

	/**
	 * set the Model instance
	 * @param model Model
	 */
	public void setModel(Model model) {this.model = model;}

	/**
	 * @return the Hash map {String,Command}
	 */
	public HashMap<String, Command> getStringtoCommand() {return stringtoCommand;}

	/**
	 * set the Hash map {String,Command)
	 * @param stringtoCommand hashmap
	 */
	public void setStringtoCommand(HashMap<String, Command> stringtoCommand) {this.stringtoCommand = stringtoCommand;}

	/**
	 * update function, MVP design.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if(o==view)
		{
			String[] args = ((String[])arg).clone();
			int input = view.getUserCommand();
			switch (input) {
			case 1:
				try{model.dir(args[0]);}
				catch(Exception e){view.errorNoticeToUser("Error: illigal path.");}
				break;
				
			case 2:
				try {model.generatemazewithname(args[0], args[1], args[2], args[3], args[4]);
					
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Error: use parameters :generate 3d maze <name> <my3dgenerator/simple3dgenerator> <floor> <line> <col>");
				}
				break;
			case 3:
				model.getMazeBygivenName(args[0]);	
				break;
				
			case 4:
				try {model.getcrossby(args[0], args[1], args[2],args[3]);
					
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Error: bad Args");
				}
				break;
				
			case 5:
				try {model.saveMazeToFile(args[0], args[1]);
					
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Error: bad Args");
				}
				break;
				
			case 6:
				try {model.loadMazeToFile(args[0], args[1]);
					
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Error: bad Args");
				}
				break;
				
			case 7:
				try {model.MazeSize(args[0]);
					
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Error: bad Args");
				}
				break;
				
			case 8:
				try {model.fileSize(args[0]);
					
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Error: bad Args");
				}
				break;
				
			case 9:
				try {model.solveMaze(args[0], args[1]);
					
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Error: bad Args");
				}
				break;
				
			case 10:
				try {model.getSolution(args[0]);
					
				} catch (Exception e) {
					//System.out.println(args[0]+"badd");
					e.printStackTrace();
					view.errorNoticeToUser("Error: bad Args");
				}
				break;
				
			case 11:
				try {model.exit();
					
				} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Error: bad Args");
				}
				break;
			
			case 12:
				System.out.println("in case 12! in properties!");
				try{model.changeXmlFile(args[0]);
				
				}catch(Exception e){
					e.printStackTrace();
					view.errorNoticeToUser("Error: Not XML file");
				}
				break;
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
				break;
			case 15:
				System.out.println(view.getUserCommand());
				
				try{model.remoteSolveModel(args[0],args[1],args[2],args[3],args[4],args[5], args[6]);
					
				}catch(Exception e){
					e.printStackTrace();
					view.errorNoticeToUser("Error: ...need to fix it!");
					
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
				view.Userdir((String) model.getData());
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
				view.userprintSolution((String)dataSet[0], (Solution<Position>)dataSet[1]);
				break;
				
			case 11:
				dataSet = (Object[])model.getData();
				view.printXMLfieds((String)dataSet[0],(String)dataSet[1],(String)dataSet[2],(String)dataSet[3],(String)dataSet[4]);
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

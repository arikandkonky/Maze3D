package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Command;
import presenter.Controller;

public class MazeWindow extends BasicWindow {

	Timer timer;
	TimerTask task;
	HashMap<String, Command> viewCommandMap;
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	PrintWriter out=new PrintWriter(System.out);
	int userCommand=0;
	private CLI cli;
	private Controller controller;
	Maze3d AllMaze;
	Maze3d mazeData;
	int[][] crossedArr;
	MazeDisplayer maze;
	Label label = new Label(shell, SWT.NONE);
	boolean started =false;
	int currentFloor;
	Menu menuBar, fileMenu, helpMenu;
	MenuItem fileMenuHeader, helpMenuHeader;
	MenuItem fileExitItem, fileSaveItem, helpGetHelpItem;
	
	
	public MazeWindow(String title, int width, int height,HashMap<String, Command> viewCommandMap) {
		super(title, width, height);
		this.viewCommandMap=viewCommandMap;
		// TODO Auto-generated constructor stub
	}
	
	  //!---------------Function---------------------! 
	/*private void randomWalk(MazeDisplayer maze){
		Random r=new Random();
		boolean b1,b2;
		b1=r.nextBoolean();
		b2=r.nextBoolean();
		if(b1&&b2)
			maze.moveUp();
		if(b1&&!b2)
			maze.moveDown();
		if(!b1&&b2)
			maze.moveRight();
		if(!b1&&!b2)
			maze.moveLeft();
		
		maze.redraw();
	}
	*/
	public void initKeyListeners()
	{
		maze.addKeyListener(new KeyListener() 
		{
            boolean pageDownKey = false;
            boolean PageUp = false;
            
			@Override
			public void keyReleased(KeyEvent e) 
			{
				if(started == true)
				{
					System.out.println(e.toString());
					switch (e.keyCode) 
					{
						case SWT.ARROW_DOWN:	maze.moveDown();
							break;
						case SWT.ARROW_UP:		maze.moveUp();
							break;
						case SWT.ARROW_LEFT:	maze.moveLeft();
							break;
						case SWT.ARROW_RIGHT:	maze.moveRight();
							break;
						case SWT.PAGE_DOWN:
							if(pageDownKey)
							{
								maze.moveDown();
								pageDownKey=false;
							}
							break;
						case SWT.PAGE_UP:
							if(PageUp)
							{
								maze.moveUp();
								PageUp=false;
							}
							break;
		              }
				}
		        else
                {    
		        	switch (e.keyCode) 
                    {
                         case SWT.PAGE_DOWN:	pageDownKey=false;
                        	 break;
                         case SWT.PAGE_UP:		PageUp=false;
                        	 break;
                    }
                }
			}
		
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(started)
				{
					switch (e.keyCode) 
					{
                        case SWT.PAGE_DOWN:	pageDownKey=PageUpDown("DOWN");
                        	break;
                        case SWT.PAGE_UP:	PageUp=PageUpDown("UP");
                        	break;
					}
				}
			}
		});
	}
	
	public boolean PageUpDown(String go)
	{
		if (go.toUpperCase().equals("UP"))
		{
			if(currentFloor>=0 && currentFloor<(this.mazeData.getMaze().length-1))
			{
				System.out.println("Prepering to go up from floor: "+ currentFloor);
				System.out.println(this.mazeData.getMaze().length-1 + "," + currentFloor);
				int[][] crossedArrToCheck = this.mazeData.getCrossSectionByX(currentFloor+1);
				System.out.println("The next floor is: ");
				printMatrix(crossedArrToCheck);
				System.out.println("Char position: "+ maze.getCharacterX() + "," + maze.getCharacterY());
				if(crossedArrToCheck[maze.getCharacterX()][maze.getCharacterY()]==0)
				{
					this.crossedArr = crossedArrToCheck;
					currentFloor++;
					maze.setCurrentFloor(currentFloor);
					maze.mazeData = crossedArr;
					return true;
				}
				else
				{
					System.out.println("The cell is 1!!!");
					return false;
				}
			}
			else
			{
				System.out.println("not illigel");
			}
		}
		else if (go.toUpperCase().equals("DOWN"))
		{
			if(currentFloor>=1 && currentFloor<this.mazeData.getMaze().length-1)
			{
				System.out.println("Prepering to go down from floor: "+ currentFloor);
				int[][] crossedArrToCheck = this.mazeData.getCrossSectionByX(currentFloor-1);
				System.out.println("The next floor is: ");
				printMatrix(crossedArrToCheck);
				System.out.println("Char position: "+ maze.getCharacterX() + "," + maze.getCharacterY());
				if(crossedArrToCheck[maze.getCharacterX()][maze.getCharacterY()]==0)
				{
					this.crossedArr = crossedArrToCheck;
					currentFloor++;
					maze.setCurrentFloor(currentFloor);
					maze.mazeData = crossedArr;
					return true;
				}
				else
				{
					System.out.println("The cell is 1!!!");
					return false;
				}
			}
			else
			{
				System.out.println("not illigel");
			}
		}
		return false;
	}
	
	@Override
	public void initWidgets() {
	    shell.setText("Maze 3D Game By Arik and Nir");
	    shell.setSize(300, 200);
	    initMaze();
	    initKeyListeners();
		shell.setLayout(new GridLayout(2,false));		
		
		//!--------------- Initial the Menu Bar---------------------! 
		menuBar = new Menu(shell, SWT.BAR);//menu bar
		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    fileMenuHeader.setText("&File");
	    
	    helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    helpMenuHeader.setText("&Help");
	    
	    fileMenu = new Menu(shell, SWT.DROP_DOWN);
	    fileMenuHeader.setMenu(fileMenu);
	    
	    helpMenu = new Menu(shell, SWT.DROP_DOWN);
	    helpMenuHeader.setMenu(helpMenu);
	    
	    
	  //!--------------- Initial the Menu in the Menu Bar---------------------! 
	    
	    
	    MenuItem openProperties = new MenuItem(fileMenu,SWT.PUSH);
	    openProperties.setText("&Open Properties");

	    fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileExitItem.setText("&Exit");


	    helpGetHelpItem = new MenuItem(helpMenu, SWT.PUSH);
	    helpGetHelpItem.setText("&Get Help");
	    
	    //!--------------- Initial StartBottom---------------------! 
	  	final Button startButton=new Button(shell, SWT.PUSH);
	  	startButton.setText("Start");
	  	startButton.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));
	  				
	  	//!--------------- Initial Stop Bottom---------------------! 
	  	final Button stopButton=new Button(shell, SWT.PUSH);
	  	stopButton.setText("Stop");
	  	stopButton.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));
	  	stopButton.setEnabled(false);	
	    
		//!---------------startButton Listener---------------------! 
	  	startButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				timer=new Timer();
				task=new TimerTask() {
					@Override
					public void run() {
						display.syncExec(new Runnable() {
							@Override
							public void run() {
								started = true;
							}
						});
					}
				};				
				timer.scheduleAtFixedRate(task, 0, 100);				
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
	  	
		//!---------------stopButton Listener---------------------! 
		stopButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				task.cancel();
				timer.cancel();
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
	  	
		//!---------------Properties Listener---------------------! 
	    openProperties.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Open");
		        fd.setFilterPath("C:/");
		        String[] filterExt = {"*.xml"};
		        fd.setFilterExtensions(filterExt);
		        String selected = fd.open();
		        System.out.println(selected);
		        userCommand = 12;
		        String[] args = {selected};
		        notifyObservers(args);
				//Need to move it on..
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    
		  //!---------------exit Listener---------------------! 
	    fileExitItem.addSelectionListener(new SelectionListener() {
			
	    	public void widgetSelected(SelectionEvent e) 
	    	{
	 	       	MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION
	 	       	| SWT.YES | SWT.NO);
	 	        messageBox.setMessage("Do you really want to exit?");
	 	        messageBox.setText("Exiting Application");
	 	        int response = messageBox.open();
	 	        if (response == SWT.YES)
	 	        	System.exit(0);
	 	    }
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
	      });
		  //!---------------Image background---------------------! 
	    //Image image = new Image(display,"C:\file.png");
	    //label.setImage(image);
	    
	    shell.setMenuBar(menuBar);
	}
	
	private void initMaze() 
	{
		maze=new Maze3D(shell, SWT.BORDER);
		String[] mazeArgs =  {"nir","My3dGenerator","2","8","8"};
		this.viewCommandMap.get("generate 3d maze").doCommand(mazeArgs);
        maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
	}

	
	public void printMatrix(int[][] arr)
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
	
	public void printToUserCrossedArray(int[][] crossedArr, String axe, String index, String name) {
		System.out.println("Crossed Arr!!!");
		this.crossedArr = crossedArr;
		out.println("Crossed maze: "+name+ " by axe: "+axe+" with index: "+index);
		out.flush();
		printMatrix(crossedArr);
	}
	

	
	//**********************************View Methods**************************************//
	@Override
	public void Userdir(String dirpath) {
		out.println("Files and Dir in: "+dirpath+"\n");
		out.flush();
	}
	@Override
	public void errorNoticeToUser(String string) {out.println(string);}

	@Override
	public void userMazeReady(String name) {
		String[] a = new String[1];
		out.println("Maze: "+ name+ "Ready!");
		a[0] = name;
		System.out.println("usermazeisready");
		this.viewCommandMap.get("display").doCommand(a);
		}

	@Override
	public void userprintMazetouser(Maze3d maze3dName, String name) {
		out.println("Maze name: "+ name + "\n" + maze3dName.toString());
		this.maze.setCharacterX(maze3dName.getGoalPosition().getY());
		this.maze.setCharacterY(maze3dName.getGoalPosition().getZ());
		//this.maze.setCharZ(maze3dName.getGoalPosition().getZ());
		System.out.println("im here");
		this.AllMaze = maze3dName;
		this.mazeData = maze3dName;
		this.crossedArr = this.mazeData.getCrossSectionByX(this.mazeData.getStartPosition().getX());
		maze.mazeData = crossedArr;
		maze.setCharacterPosition(this.mazeData.getStartPosition().getY(), this.mazeData.getStartPosition().getZ());
		this.currentFloor = this.mazeData.getStartPosition().getX();
		out.println("Maze: "+name+"\n"+maze3dName.toString());
		out.flush();
		
		}

	@Override
	public void userprintCrossBySection(int[][] section, String xyz, String index, String name) {
		out.println("Maze name: "+ name + " get cross section by: " + xyz + " in index " + index);
		out.println("This is the cross Maze: ");
		printMatrix(section);}

	@Override
	public void usermazeSaveToUser(String filename, String name) {out.println("the Maze: " + name + " saved on file name: "+ filename);}

	@Override
	public void userMazeLoaded(String filename, String name) {out.println("from file name: " + filename + " has beed loaded maze name: " + name);}

	@Override
	public void userSizeOfMaze(String name, Double s) {out.println("The size of the maze name: "+ name + " by bytes is: "+ s);}

	@Override
	public void userSizeOfFile(String filename, Double s) {out.println("The file size of the file name: "+ filename + " by bytes: "+ s);}

	@Override
	public void userSolutionReady(String name) {out.println("The solution of maze name: "+ name + " is Ready!");}

	@Override
	public void userprintSolution(String name, Solution<Position> solution) {
		out.println("View: \n Solution of maze :" + name + "\n");
		out.flush();
		for(State<Position> p :solution.getSolution())
		{
			out.println(p.getCamefrom() + " " + p.getActionName() + " to: " + p.toString());
			out.flush();
		}
		
	}
	
	
	//**********************************Getters And Setters********************************************//
	
	
	
	public Maze3d getMazeData() {
		return mazeData;
	}

	public void setMazeData(Maze3d mazeData) {
		this.mazeData = mazeData;
	}
	
	@Override
	public void setUserCommand(int i) {
		this.userCommand = i;
		this.setChanged();
		}
	

	@Override
	public int getUserCommand() {
		return this.userCommand;
	}

	@Override
	public void printXMLfieds(String string, String string2, int i, String string3) {
		// TODO Auto-generated method stub
		
	}
	
	public HashMap<String, Command> getViewCommandMap() {
		return viewCommandMap;
	}

	public void setViewCommandMap(HashMap<String, Command> viewCommandMap) {
		this.viewCommandMap = viewCommandMap;
	}
	
}

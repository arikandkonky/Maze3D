package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Controller;

public class MazeWindow extends BasicWindow {

	Timer timer;
	TimerTask task;
	HashMap<String, Command> viewCommandMap;
	private String cliMenu;
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	PrintWriter out=new PrintWriter(System.out);
	int userCommand=0;
	private CLI cli;
	private Controller controller;
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
                        case SWT.PAGE_DOWN:	//pageDownKey=getFloorUpCrossedArr("DOWN");
                        	break;
                        case SWT.PAGE_UP:	//PageUp=getFloorUpCrossedArr("UP");
                        	break;
					}
				}
			}
		});
	}
	
	@Override
	public void initWidgets() {
	    shell.setText("Maze 3D Game By Arik and Nir");
	    shell.setSize(300, 200);
		
		shell.setLayout(new GridLayout(2,false));
		initKeyListeners();
		//MazeDisplayer maze=new Maze2D(shell, SWT.BORDER);		
		final MazeDisplayer maze=new Maze3D(shell, SWT.BORDER);
		maze.setMazeData(crossedArr);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
		
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
	
	@Override
	public void setUserCommand(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Userdir(String dirpath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void errorNoticeToUser(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userMazeReady(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userprintMazetouser(Maze3d maze3dName, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userprintCrossBySection(int[][] section, String xyz, String index, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void usermazeSaveToUser(String filename, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userMazeLoaded(String filename, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userSizeOfMaze(String name, Double s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userSizeOfFile(String filename, Double s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userSolutionReady(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userprintSolution(String name, Solution<Position> solution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getUserCommand() {
		// TODO Auto-generated method stub
		return 0;
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

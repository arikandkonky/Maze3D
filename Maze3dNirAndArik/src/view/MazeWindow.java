package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.FontData;
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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Command;

public class MazeWindow extends BasicWindow {

	Timer timer;
	TimerTask task;
	TabItem tab1,tab2,tabitem;
	TabFolder tab;
	public HashMap<String, Command> viewCommandMap;
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	PrintWriter out=new PrintWriter(System.out);
	int userCommand=0;
	Maze3d AllMaze,mazeData;
	int[][] crossedArr;
	MazeDisplayer maze;
	int currentFloor,FloorGoalPosition;
	Menu menuBar, fileMenu, helpMenu;
	MenuItem fileMenuHeader, helpMenuHeader,fileExitItem, fileSaveItem, helpGetHelpItem,openProperties;
	String MatrixName;
	Solution<Position> SolutionHelp;
	boolean flag = true, hint = false,started =false;
	Text txtFloor,txtLine,txtCol,txtMatrixName;
	GridData grid;
	SashForm sashForm2,sashForm;
	Image bGImage;
	Button startButton,stopButton,randomGenerator,helpsolvemazeItem,enterButton,hintButton;
	Label lbFloor,lbMatrixName,lbCol,lbLine;

	

	public MazeWindow(String title, int width, int height,HashMap<String, Command> viewCommandMap) {
		super(title, width, height);
		this.viewCommandMap=viewCommandMap;
	}

	
	@Override
	public void initWidgets() {
		FontData defaultFont = new FontData("Castellar",10,SWT.BOLD);
		org.eclipse.swt.graphics.Font boldFont = new org.eclipse.swt.graphics.Font(display, defaultFont);
	    shell.setText("Maze 3D Game By Arik and Nir");
	    shell.setSize(800,600);
		shell.setLayout(new GridLayout(2,false));
		//Hey
		
		
		GridData stretchGridData = new GridData();

		
		tab = new TabFolder(shell, SWT.NULL);
		tab.setLayoutData(stretchGridData);
		
		tab1 = new TabItem(tab, SWT.FILL);
	    tab1.setText("Generate");
	    tab2 = new TabItem(tab, SWT.None);
	    tab2.setText("    Maze    ");
		
	    sashForm = new SashForm(tab, SWT.VERTICAL);
		bGImage = new Image(display, "C:/Maze.png");
	    sashForm.setBackgroundImage(bGImage);

		randomGenerator = new Button(sashForm,SWT.PUSH);
		randomGenerator.setText("Generate");
		randomGenerator.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		randomGenerator.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));
		randomGenerator.setFont(boldFont);
		randomGenerator.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				initMaze();
				
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    
	    //!--------------- Initial StartBottom---------------------! 
	  	startButton=new Button(sashForm, SWT.PUSH);
	  	startButton.setText("Start");
	  	startButton.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_BLACK));
	  	startButton.setFont(boldFont);
	  	startButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
	  				
	  	//!--------------- Initial Stop Bottom---------------------! 
	  	stopButton=new Button(sashForm, SWT.PUSH);
	  	stopButton.setText("Stop");
	  	stopButton.setFont(boldFont);
	  	stopButton.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_BLACK));

	  	stopButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
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
				started = false;
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		helpsolvemazeItem = new Button(sashForm,SWT.PUSH);
		helpsolvemazeItem.setText("Solve");
		helpsolvemazeItem.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		helpsolvemazeItem.setFont(boldFont);
		//!---------------get solve Listener---------------------! 
		helpsolvemazeItem.addSelectionListener(new SelectionListener() {		
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					hint = false;
					int newStartPositionY = maze.getCharacterX();
					int newStartPositionZ = maze.getCharacterY();
					int FloorX = currentFloor;
					helpSolveUserMazefromPoint(MatrixName,FloorX,newStartPositionY,newStartPositionZ);
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	    

	    sashForm2 = new SashForm(tab, SWT.VERTICAL);
        //**************Initail Name/Floors/Line/Col***********************/
		
		grid = new GridData();
		grid.grabExcessHorizontalSpace = false;
		grid.horizontalAlignment = GridData.CENTER;
		
		lbMatrixName = new Label(sashForm2, SWT.NULL);
	    lbMatrixName.setText("Maze Name:");
	    lbMatrixName.setFont(boldFont);
	    
	    txtMatrixName = new Text(sashForm2, SWT.BORDER);
	    txtMatrixName.setLayoutData(grid);
	    txtMatrixName.setText("Maze Name?");
	    
		lbFloor = new Label(sashForm2, SWT.NONE);
	    lbFloor.setText("Floors:");
	    lbFloor.setFont(boldFont);

	    txtFloor = new Text(sashForm2, SWT.BORDER);
	    txtFloor.setLayoutData(grid);
	    txtFloor.setText("Numbers Of Floors?");
	    
	    lbLine = new Label(sashForm2, SWT.NONE);
	    lbLine.setText("Lines:");
	    lbLine.setFont(boldFont);
	    
	    txtLine = new Text(sashForm2, SWT.BORDER);
	    txtLine.setLayoutData(grid);
	    txtLine.setText("Number of Lines?");
	     
	     lbCol = new Label(sashForm2, SWT.NONE);
		 lbCol.setText("Col:");
		 lbCol.setFont(boldFont);
		 
		 txtCol = new Text(sashForm2, SWT.BORDER);
		 txtCol.setLayoutData(grid);
		 txtCol.setText("Number of Columns?");
		 

		 //************Initial Enter Button*******************/
		 enterButton=new Button(sashForm2, SWT.PUSH);
		 enterButton.setText("Enter");
		 enterButton.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));
		 enterButton.setFont(boldFont);
		 
		 
		//!--------------- Initial the Enter Listener---------------------! 
		 enterButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Integer intFloor = new Integer(txtFloor.getText().toString());
				Integer intLine = new Integer(txtLine.getText().toString());
				Integer intCol = new Integer(txtCol.getText().toString());
				if((!txtFloor.getText().toString().isEmpty() && intFloor<0) || ((!txtLine.getText().toString().isEmpty()) && intLine<0 ) || ((!txtCol.getText().toString().isEmpty())&& intCol <0))
				{
					MessageBox ErrorBox = new MessageBox(shell);
					ErrorBox.setText("Error!");
					if(txtFloor.getText().toString().isEmpty() || intFloor<0 || txtLine.getText().toString().isEmpty() || intLine<0 || txtCol.getText().toString().isEmpty() || intCol<0)
					if(txtFloor.getText().toString().isEmpty() || intFloor<0)
					{
						ErrorBox.setMessage("Error: Floor = "+ txtFloor.getText().toString() + " Not Illigel only >0!");
					}
					if(txtLine.getText().toString().isEmpty() || intLine<0)
					{
						ErrorBox.setMessage("Error: Line = "+ txtLine.getText().toString() + " Not Illigel only >0!");
					}
					if(txtCol.getText().toString().isEmpty() || intCol<0)
					{
						ErrorBox.setMessage("Error: Col = "+ txtCol.getText().toString() + " Not Illigel only >0!");
					}
					ErrorBox.setText("Error!");
					ErrorBox.open();
				}
				else if(txtFloor.getText().toString().isEmpty() || (txtLine.getText().toString().isEmpty()) || (txtCol.getText().toString().isEmpty()))
				{
					MessageBox ErrorBox = new MessageBox(shell);
					ErrorBox.setText("Error!");
					if(txtFloor.getText().toString().isEmpty())
						ErrorBox.setMessage("Error: Floor is empty!");
					if(txtLine.getText().toString().isEmpty())
						ErrorBox.setMessage("Error: Line is empty!");
					if(txtCol.getText().toString().isEmpty())
						ErrorBox.setMessage("Error: Col is empty!");
					ErrorBox.open();	
				}
				else
				{
					MessageBox ErrorBox = new MessageBox(shell);
					ErrorBox.setText("Generating Maze!");
					ErrorBox.setMessage("Generating...");
					ErrorBox.open();
					initMaze(txtMatrixName.getText().toString(),txtFloor.getText().toString(),txtLine.getText().toString(),txtCol.getText().toString());
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	 
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
	    
	    openProperties = new MenuItem(fileMenu,SWT.PUSH);
	    openProperties.setText("&Open Properties");

	    fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileExitItem.setText("&Exit");


	    hintButton = new Button(sashForm, SWT.PUSH);
	    hintButton.setText("Hint");
	    hintButton.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_BLACK));
	    hintButton.setFont(boldFont);
		//!---------------get Hint Listener---------------------! 
	    hintButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
						hint = true;
						int newStartPositionY = maze.getCharacterX();
						int newStartPositionZ = maze.getCharacterY();
						int FloorX = currentFloor;
						helpSolveUserMazefromPoint(MatrixName,FloorX,newStartPositionY,newStartPositionZ);
						MessageBox ErrorBox = new MessageBox(shell);
						ErrorBox.setText("Won!!! :)");
						ErrorBox.setMessage("Nice you solve the Maze With little Help :P");
						ErrorBox.open();
						//shell.layout();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
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
				
			}
	      });
	    sashForm.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_BLUE));
	    sashForm2.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_BLUE));

		tab1.setControl(sashForm2);
	    shell.setMenuBar(menuBar);
	    tab2.setControl(sashForm);
	}
	
	//**********************************View Methods**************************************//
	private void initMaze() 
	{
		try {
			
			String[] mazeArgs =  {"newMaze","My3dGenerator","3","17","17"};
			this.viewCommandMap.get("generate 3d maze").doCommand(mazeArgs);
			this.MatrixName = mazeArgs[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void initMaze(String Name,String Floor, String Line, String Col)
	{
		if (maze==null){
			maze=new Maze3D(shell, SWT.BORDER);
			initKeyListeners();
		}
		String[] mazeArgs =  {Name,"My3dGenerator",Floor,Line,Col};
		this.viewCommandMap.get("generate 3d maze").doCommand(mazeArgs);
		this.MatrixName = mazeArgs[0];
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
		this.crossedArr = crossedArr;
		out.println("Crossed maze: "+name+ " by axe: "+axe+" with index: "+index);
		out.flush();
		printMatrix(crossedArr);
	}
	

	
	
	
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
		this.MatrixName = name;
		out.println("Maze: "+ name+ "Ready!");
		a[0] = name;
		this.viewCommandMap.get("display").doCommand(a);
		}

	@Override
	public void userprintMazetouser(final Maze3d maze3dName, final String name) {
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
					if(maze==null)
					{
						maze=new Maze3D(shell, SWT.BORDER);
						initKeyListeners();
					}
					maze.setIsWon(false);
			        maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
					FloorGoalPosition=maze3dName.getGoalPosition().getX();
					maze.setExitX(maze3dName.getGoalPosition().getY());
					maze.setExitY(maze3dName.getGoalPosition().getZ());
					maze.setfloorExit(FloorGoalPosition);
					AllMaze = maze3dName;
					mazeData = maze3dName;
					crossedArr = maze3dName.getCrossSectionByX(maze3dName.getStartPosition().getX());
					maze.mazeData = crossedArr;
					printMatrix(maze.mazeData);
					maze.setCharacterPosition(maze3dName.getStartPosition().getY(), maze3dName.getStartPosition().getZ());
					currentFloor = maze3dName.getStartPosition().getX();
					out.println("Maze: "+name+"\n"+maze3dName.toString());
					out.flush();
					maze.redraw();
					shell.layout();
		    }
		});

	
		}
	public void helpSolveUserMazefromPoint(String MatrixName,int X, int Y,int Z)
	{
		int stringX = X;
		String strX = "" + stringX;
		int stringY = Y;
		String strY = "" + stringY;
		int stringZ = Z;
		String strZ = "" + stringZ;
		String[] mazeToSolve =  {MatrixName,strX,strY,strZ};
		this.viewCommandMap.get("Solve Maze Point").doCommand(mazeToSolve);
	}
	

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
						case SWT.ARROW_DOWN:	
							maze.moveDown();
							System.out.println("("+ maze.getCharacterX() + "," + maze.getCharacterY());
							break;
						case SWT.ARROW_UP:
							System.out.println("("+ maze.getCharacterX() + "," + maze.getCharacterY());
							maze.moveUp();
							break;
						case SWT.ARROW_LEFT:
							System.out.println("("+ maze.getCharacterX() + "," + maze.getCharacterY());
							maze.moveLeft();
							break;
						case SWT.ARROW_RIGHT:
							System.out.println("("+ maze.getCharacterX() + "," + maze.getCharacterY());
							maze.moveRight();
							break;
						case SWT.PAGE_DOWN:
							if(pageDownKey)
							{
								maze.moveFloorDown();
								pageDownKey=false;
							}
							break;
						case SWT.PAGE_UP:
							if(PageUp)
							{
								maze.moveFloorUp();
								PageUp=false;
							}
							break;
		              }
					if(maze.getCharacterX()==maze.getExitX() && maze.getCharacterY()==maze.getExitY()&&currentFloor==maze.getfloorExit())
					{
						MessageBox ErrorBox = new MessageBox(shell);
						ErrorBox.setText("Won!!! :)");
						ErrorBox.setMessage("Nice you solve the Maze Good Job!");
						ErrorBox.open();
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
                        printMatrix(crossedArr);
                        	break;
                        case SWT.PAGE_UP:	PageUp=PageUpDown("UP");
                        printMatrix(crossedArr);
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
			System.out.println("CurrentFloor: "+ currentFloor + " MazeLength: " + (this.mazeData.getMaze().length) );

			if(currentFloor>=0&&currentFloor<(this.mazeData.getMaze().length-1))
			{
				int[][] crossedArrToCheck = this.mazeData.getCrossSectionByX(currentFloor+1);
				printMatrix(crossedArrToCheck);
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
				System.out.println("not illigel..");
			}
		}
		else if (go.toUpperCase().equals("DOWN"))
		{
			System.out.println("CurrentFloor: "+ currentFloor + " MazeLength: " + (this.mazeData.getMaze().length) );
			if(currentFloor>=1&&currentFloor<=(this.mazeData.getMaze().length-1))
			{
				int[][] crossedArrToCheck = this.mazeData.getCrossSectionByX(currentFloor-1);
				if(crossedArrToCheck[maze.getCharacterX()][maze.getCharacterY()]==0)
				{
					this.crossedArr = crossedArrToCheck;
					currentFloor--;
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
	public void userSolutionReady(String name) {
		String[] displaySolutionString = {name};
		this.viewCommandMap.get("display solution").doCommand(displaySolutionString);
	}

	public void userprintSolution(final String name, final Solution<Position> solution)
	{
		Thread t = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
				if (hint == false)
				{
					for(State<Position> p :solution.getSolution())
					{
						System.out.println(p.getActionName().toString());
						switch (p.getActionName()) 
						{
						case "FloorUp":PageUpDown("Up");maze.moveFloorUp();
							break;
						case "FloorDown":PageUpDown("Down");maze.moveFloorDown();
							break;
						case "LineForward":maze.moveDown();
						
							break;
						case "LineBackward":maze.moveUp();
						
							break;
						case "ColRight":maze.moveRight();
						
							break;
						case "ColLeft":maze.moveLeft();						
							break;
						default:
							break;
						}
						try 
						{
							Thread.sleep(250);                 //250 milliseconds is one second.
						} catch(InterruptedException ex){ex.printStackTrace();}
					}
				}
				else if (hint == true)
				{
					ArrayList<State<Position>> p = solution.getSolution();	
					System.out.println(p.get(0).getActionName().toString());
					switch (p.get(0).getActionName().toString()) 
					{
						case "FloorUp":
							PageUpDown("Up");
							System.out.println("Position At: ("+ maze.getCharacterX() +","+maze.getCharacterY()+")");
							maze.moveFloorUp();
							break;
						case "FloorDown":
							PageUpDown("Down");
							System.out.println("Position At: ("+ maze.getCharacterX() +","+maze.getCharacterY()+")");
							maze.moveFloorDown();
							break;
						case "LineForward":
							maze.moveDown();
							System.out.println("Position At: ("+ maze.getCharacterX() +","+maze.getCharacterY()+")");
							break;
						case "LineBackward":
							maze.moveUp();
							System.out.println("Position At: ("+ maze.getCharacterX() +","+maze.getCharacterY()+")");
							break;
						case "ColRight":
							maze.moveRight();
							System.out.println("Position At: ("+ maze.getCharacterX() +","+maze.getCharacterY()+")");
							break;
						case "ColLeft":
							maze.moveLeft();
							System.out.println("Position At: ("+ maze.getCharacterX() +","+maze.getCharacterY()+")");
							break;
						default:
							break;
					}
				}
		}
		});
		t.start();
	}

	
		
	
	
	//**********************************Getters And Setters + View Methods*********************//
	
	
	
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
	
	public void isWon(boolean a){
		this.flag= a;
	}
	
	
	public MazeDisplayer getMaze() {
		return maze;
	}

	public void setMaze(MazeDisplayer maze) {
		this.maze = maze;
	}

	@Override
	public void oneStateDisplay(final String name, final Solution<Position> solution) {
	}
	
	
}





		
	
	
	


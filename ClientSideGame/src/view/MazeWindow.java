package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Command;

/**
 * @author Nir Konky and arik bidny
 * @version 1.0
 * <h1> MazeWindow </h1>
 * This Class MazeWindow extends Observable.
 * all extends classes will have to do the methods
 */
public class MazeWindow extends BasicWindow {

	Timer timer;
	TimerTask task;
	public HashMap<String, Command> viewCommandMap;
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	PrintWriter out=new PrintWriter(System.out);
	int userCommand=0;
	Maze3d AllMaze,mazeData;
	int[][] crossedArr;
	MazeDisplayer maze;
	int currentFloor,FloorGoalPosition;
	Menu menuBar, fileMenu, helpMenu;
	MenuItem fileMenuHeader, helpMenuHeader,fileExitItem, fileSaveItem, helpGetHelpItem,openProperties,fileAboutItem ;
	Solution<Position> SolutionHelp;
	boolean started = false,flag = false, hint = false;
	String matrixName;
	

	/**
	 * with the given parameters do the maze window instance
	 * @param title string
	 * @param width int
	 * @param height int
	 * @param viewCommandMap hashmap
	 */
	public MazeWindow(String title, int width, int height,HashMap<String, Command> viewCommandMap) {
		super(title, width, height);
		this.viewCommandMap=viewCommandMap;
	}

	/**
	 * init all the widgets of the maze window
	 */
	@Override
	public void initWidgets() {
	    shell.setText("Maze 3D Game By Arik and Nir");
	    
	    GridLayout layout = new GridLayout();
	    layout.numColumns=2;
	    layout.makeColumnsEqualWidth=false;
	    shell.setLayout(layout);
	    
	    //Set Icon to Server Shell
	    shell.setImage(new Image(display, "C:/pictures/maze.ico"));
	    
	    //Set BackGround Image to Client Shell
	    shell.setSize(650,450);
	    createContents(shell);
	    shell.open();
	    
	    //MENU
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
	    
	    fileAboutItem = new MenuItem(helpMenu, SWT.PUSH);
	    fileAboutItem.setText("&About");
	    

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
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
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
	    
	    ////!---------------About Listener---------------------! 
	    fileAboutItem.addListener(SWT.Selection, new Listener() {
	        
	    	public void handleEvent(Event event) {
	          MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING);
	          
	          messageBox.setText("About");
	          messageBox.setMessage("Made By: Nir Konky and Arik Bidny");
	          messageBox.open();
	          }
	          });
	    
	    shell.setMenuBar(menuBar);
		
	    
	}
	
	/**
	 * Creates the contents
	 * 
	 * @param shell the parent shell
	 */
	private void createContents(Shell shell)
	{
		//Create the containing tab folder
		final TabFolder tabFolder = new TabFolder(shell,SWT.NONE);
		
		//Create each tab and set its text, tool tip text,
		//image, and control
		TabItem one = new TabItem(tabFolder,SWT.NONE);
		one.setText("Server");
		one.setToolTipText("Server Options");
		one.setControl(getTabOneControl(tabFolder));
		TabItem two = new TabItem(tabFolder,SWT.NONE);
		two.setText("Options");
		two.setToolTipText("Maze Options");
		two.setControl(getTabTwoControl(tabFolder));
		
		//Add an event listener to write the selected tab to Console
		tabFolder.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
		        System.out.println(tabFolder.getSelection()[0].getText() + " selected");
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
		/**
		 * Gets the control for tab one
		 *
		 * @param tabFolder the parent tab folder
		 * @return Control
		*/
	private Control getTabOneControl(TabFolder tabFolder) {  
			Composite composite = new Composite(tabFolder, SWT.NONE);
			GridLayout layout = new GridLayout();
		    composite.setLayout(layout);
		    layout.numColumns = 2;
		    layout.makeColumnsEqualWidth = true;
		    composite.setLayout(layout);
		    // LABEL 1 = PORT
			GridData data = new GridData(GridData.FILL_BOTH);
			Label label = new Label(composite, SWT.NONE);
			label.setText("Port :");
				           
			// TEXT 1 = PORT

			// Create a single-line text field
			data = new GridData(GridData.FILL_BOTH);
			Text txtPort = new Text(composite, SWT.BORDER);
			txtPort.setToolTipText("Enter A Port Number");
				           
			// LABEL 2 = Host Name
				           
			data = new GridData(GridData.FILL_BOTH);
			Label label1 = new Label(composite, SWT.NONE);
			label1.setText("Host Name :");
				                      
			// TEXT 2 = Host Name
				           
			// Create a single-line text field
			data = new GridData(GridData.FILL_BOTH);
			Text txtHostName = new Text(composite, SWT.BORDER);
			txtHostName.setToolTipText("Enter A Host Name");
		   
			data = new GridData(GridData.FILL_BOTH);
	 
			// LABEL 6 = Solving Algorithm
	           
		    data = new GridData(GridData.FILL_BOTH);
		    Label label6 = new Label(composite, SWT.NONE);
		    label6.setText("Solve Algorithm :");
		       
			
			Combo solDropDown = new Combo(composite, SWT.DROP_DOWN | SWT.BORDER);
		    solDropDown.setToolTipText("Choose a Solving Algorithm");
		    solDropDown.add("A*");
		    solDropDown.add("BFS");
		
			// BUTTOMS
				                   
			// Create the 2 long buttons across the bottom
			data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			data.horizontalSpan = 3;
			data.heightHint = 50;
				           
			// Button 1 - Generate -- Done
			Button one = new Button(composite, SWT.PUSH);
			one.setText("Generate");
			one.setLayoutData(data);
			Image generateImage = new Image(display, "C:/pictures/generate.png");
			one.setImage(generateImage);
			
			one.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
							initMaze();
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
				           
			// Button 2 - Solve -- Done
			Button two = new Button(composite, SWT.PUSH);
			two.setText("Solve");
			two.setLayoutData(data);
			Image solveImage = new Image(display, "C:/pictures/solve.png");
			two.setImage(solveImage);
			
			two.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					String sCurrentFloor = currentFloor+"";
					String sCharX = maze.getCharacterX()+"";
					String sCharY = maze.getCharacterY()+"";
					String[] mazeArgs =  {matrixName,sCurrentFloor,sCharX,sCharY,txtPort.getText().toString(),txtHostName.getText().toString(),solDropDown.getText().toString()};
					viewCommandMap.get("Remote Solve").doCommand(mazeArgs);	
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
				 
			// Button 3 - Exit
			Button three = new Button(composite, SWT.PUSH);
			three.setText("Exit");
			three.setLayoutData(data);
			Image exitImage = new Image(display, "C:/pictures/exit.png");
			three.setImage(exitImage);
				            
			//exit function to exit button
			three.addListener(SWT.Selection, event -> {
				  System.exit(0);
			});
			return composite;
		  }

		/**
		 * Gets the control for tab two
		 *
		 * @param tabFolder the parent tab folder
		 * @return Control
		*/
		  private Control getTabTwoControl(TabFolder tabFolder) {
		         
			  Composite composite = new Composite(tabFolder, SWT.NONE);
		      GridLayout layout = new GridLayout();
		      composite.setLayout(layout);   
		           
		      layout.numColumns = 2;
		      layout.makeColumnsEqualWidth = true;
		      composite.setLayout(layout);
		           
		      // LABEL 1 = Maze Name
		           
		      GridData data = new GridData(GridData.FILL_BOTH);
		      Label label = new Label(composite, SWT.NONE);
		      label.setText("Name :");
		           
		      // TEXT 1 = Maze Name  
		           
		      // Create a single-line text field
		      data = new GridData(GridData.FILL_BOTH);
		      Text txtMatrixName = new Text(composite, SWT.BORDER);
		      txtMatrixName.setToolTipText("Enter A Maze Name");
		      //AllMaze.setMazeName(txtMatrixName.getText().toString());
		      //matrixName = txtMatrixName.getText().toString();
		           
		      // LABEL 2 = Floors
		           
		      data = new GridData(GridData.FILL_BOTH);
		      Label label1 = new Label(composite, SWT.NONE);
		      label1.setText("Floors :");
		           
		      // TEXT 2 = Floors
		           
		      // Create a single-line text field
		      data = new GridData(GridData.FILL_BOTH);
		      Text txtFloor = new Text(composite, SWT.BORDER);
		      txtFloor.setToolTipText("Enter Number Of Floors");
		           
		      // LABEL 3 = Rows
		           
		      data = new GridData(GridData.FILL_BOTH);
		      Label label3 = new Label(composite, SWT.NONE);
		      label3.setText("Rows :");
		           
		           
		      // TEXT 3 = Rows
		           
		      // Create a single-line text field
		      data = new GridData(GridData.FILL_BOTH);
		      Text txtLine = new Text(composite, SWT.BORDER);
		      txtLine.setToolTipText("Enter Number Of Rows");
		           
		      // LABEL 4 = Columns
		           
		      data = new GridData(GridData.FILL_BOTH);
		      Label label4 = new Label(composite, SWT.NONE);
		      label4.setText("Columns :");
		       
		      // Create a single-line text field
		      data = new GridData(GridData.FILL_BOTH);
		      Text txtCol  = new Text(composite, SWT.BORDER);
		      txtCol.setToolTipText("Enter Number Of Columns");
		         
		      // LABEL 5 = Generation Algorithm
		           
		      data = new GridData(GridData.FILL_BOTH);
		      Label label5 = new Label(composite, SWT.NONE);
		      label5.setText("Gen Algorithm :");
		           
		           
		      // DropDown 5 = Generation Algorithm
		           
		      // Create a single-line text field
		      data = new GridData(GridData.FILL_BOTH);
		           
		      Combo genDropDown = new Combo(composite, SWT.DROP_DOWN | SWT.BORDER);
		      genDropDown.setToolTipText("Choose a Generation Algorithm");
		      genDropDown.add("Simple");
		      genDropDown.add("DFS");
		               
		      // DropDown 6 - Solving Algorithm
		           
		      // Create a single-line text field
		      data = new GridData(GridData.FILL_BOTH);
		           
		     //Combo solDropDown = new Combo(composite, SWT.DROP_DOWN | SWT.BORDER);
		     //solDropDown.setToolTipText("Choose a Solving Algorithm");
		     //solDropDown.add("A*");
		     //solDropDown.add("BFS");
		                
		      // Create the 2 long buttons across the bottom
		      data = new GridData();
		      data.horizontalAlignment = GridData.FILL;
		      data.grabExcessHorizontalSpace = true;
		      data.horizontalSpan = 3;
		      data.heightHint = 50;
		           
		      // Button 1 - Generate  
		      Button one = new Button(composite, SWT.PUSH);
		      one.setText("Generate");
		      one.setLayoutData(data);
		      
		      one.addSelectionListener(new SelectionListener() {
				
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
		 
		      Image generateImage = new Image(display, "C:/pictures/generate.png");
		      one.setImage(generateImage);
		                 
		      // Button 2 - Start -- Done
		      Button two = new Button(composite, SWT.PUSH);
		      two.setText("Start");
		      two.setLayoutData(data);
		      
		      two.addSelectionListener(new SelectionListener() {
				
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
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		           
		      Image startImage = new Image(display, "C:/pictures/start.png");
		      two.setImage(startImage);
		           
		      // Button 3 - Hint  
		      Button three = new Button(composite, SWT.PUSH);
		      three.setText("Hint");
		      three.setLayoutData(data);
		           
		      Image hintImage = new Image(display, "C:/pictures/hint.png");
		      three.setImage(hintImage);
		      
		      three.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						hint = true;
						int newStartPositionY = maze.getCharacterX();
						int newStartPositionZ = maze.getCharacterY();
						int FloorX = currentFloor;
						helpSolveUserMazefromPoint(matrixName,FloorX,newStartPositionY,newStartPositionZ);
						
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
		 
		      // Button 3 - Solve -- Done
		      Button four = new Button(composite, SWT.PUSH);
		      four.setText("Solve");
		      four.setLayoutData(data);
		      four.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					hint = false;
					int newStartPositionY = maze.getCharacterX();
					int newStartPositionZ = maze.getCharacterY();
					int FloorX = currentFloor;
					helpSolveUserMazefromPoint(matrixName,FloorX,newStartPositionY,newStartPositionZ);
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		      
		           
		      Image solveImage = new Image(display, "C:/pictures/solve.png");
		      four.setImage(solveImage);
		           
		      return composite;
		  }
		 
		
	
	
	//**********************************View Methods**************************************//
	/**
	 * init the maze
	 */
	private void initMaze() 
	{
		try {
			
			String[] mazeArgs =  {"newMaze","My3dGenerator","3","17","17"};
			this.viewCommandMap.get("generate 3d maze").doCommand(mazeArgs);
			this.matrixName = mazeArgs[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * init the maze by given parameters
	 * @param Name
	 * @param Floor
	 * @param Line
	 * @param Col
	 */
	private void initMaze(String Name,String Floor, String Line, String Col)
	{
		if (maze==null){
			maze=new Maze3D(shell, SWT.BORDER);
			initKeyListeners();
		}
		String[] mazeArgs =  {Name,"My3dGenerator",Floor,Line,Col};
		this.viewCommandMap.get("generate 3d maze").doCommand(mazeArgs);
		this.matrixName = mazeArgs[0];
        maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
	}
	
	/**
	 * print to out the cross Array
	 * @param arr int[][]
	 */
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
	
	/**
	 * print to out, the Cross section of the parameters
	 * @param crossedArr int[][]
	 * @param axe String
	 * @param index String 
	 * @param name String
	 */
	public void printToUserCrossedArray(int[][] crossedArr, String axe, String index, String name) {
		this.crossedArr = crossedArr;
		out.println("Crossed maze: "+name+ " by axe: "+axe+" with index: "+index);
		out.flush();
		printMatrix(crossedArr);
	}
	

	
	
	/**
	 * return the dir path to the Out instance
	 * @param dirpath string
	 */
	@Override
	public void Userdir(String dirpath) {
		out.println("Files and Dir in: "+dirpath+"\n");
		out.flush();
	}
	
	/**
	 * return the error string to the Out instance
	 */
	@Override
	public void errorNoticeToUser(String string) {out.println(string);}

	
	/**
	 * return to the Out that the maze is ready
	 * @param name string
	 */
	@Override
	public void userMazeReady(String name) {
		
		String[] a = new String[1];
		this.matrixName = name;
		out.println("Maze: "+ name+ "Ready!");
		a[0] = name;
		this.viewCommandMap.get("display").doCommand(a);
		}

	
	/**
	 * print to out, the maze as to string
	 * @param maze3dName Maze3d
	 * @param name string
	 */
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
	/**
	 * private method for the maze window
	 * @param MatrixName string
	 * @param X int
	 * @param Y int
	 * @param Z int
	 */
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
	
	/**
	 * init the key listeners
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
	
	/**
	 * check if you can go up/down in the maze
	 * @param go string
	 * @return boolean 
	 */
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

	/**
	 * print to out, the Cross section of the parameters
	 * @param section int[][]
	 * @param xyz string
	 * @param index string
	 * @param name string
	 */
	@Override
	public void userprintCrossBySection(int[][] section, String xyz, String index, String name) {
		out.println("Maze name: "+ name + " get cross section by: " + xyz + " in index " + index);
		out.println("This is the cross Maze: ");
		printMatrix(section);}

	/**
	 * print to out, the solve is ready.
	 * @param filename string
	 * @param name string
	 */
	@Override
	public void usermazeSaveToUser(String filename, String name) {out.println("the Maze: " + name + " saved on file name: "+ filename);}

	/**
	 * print to out, that maze has been loaded from filename
	 * @param filename string
	 * @param name string
	 */
	@Override
	public void userMazeLoaded(String filename, String name) {out.println("from file name: " + filename + " has beed loaded maze name: " + name);}

	/**
	 * print to out the size of the maze
	 * @param name string
	 * @param s double
	 */
	@Override
	public void userSizeOfMaze(String name, Double s) {out.println("The size of the maze name: "+ name + " by bytes is: "+ s);}

	/**
	 * print to out the size of the file
	 * @param filename string
	 * @param s double
	 */
	@Override
	public void userSizeOfFile(String filename, Double s) {out.println("The file size of the file name: "+ filename + " by bytes: "+ s);}

	/**
	 * print to out the solution of the maze name is ready
	 * @param name string
	 */
	@Override
	public void userSolutionReady(String name) {
		String[] displaySolutionString = {name};
		System.out.println("im here!!");
		this.viewCommandMap.get("display solution").doCommand(displaySolutionString);
	}

	/**
	 * print to out the solution of the maze name
	 * @param name string
	 * @param solution solution
	 */
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
	
	
	/**
	 * 
	 * @return the maze instance
	 */
	public Maze3d getMazeData() {
		return mazeData;
	}

	/**
	 * set the maze instance
	 * @param mazeData Maze3d
	 */
	public void setMazeData(Maze3d mazeData) {
		this.mazeData = mazeData;
	}
	
	/**
	 * set the user command
	 * @param i int
	 */
	@Override
	public void setUserCommand(int i) {
		this.userCommand = i;
		this.setChanged();
		}
	
	/**
	 * @return userCommand
	 */
	@Override
	public int getUserCommand() {
		return this.userCommand;
	}
	
	

	/**
	 * print to out that the XML file changed.
	 * @param string string
	 * @param string2 string
	 * @param i int
	 * @param string3 string
	 */
	@Override
	public void printXMLfieds(String string, String string2, int i, String string3) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @return Command map hash map
	 */
	public HashMap<String, Command> getViewCommandMap() {
		return viewCommandMap;
	}

	/**
	 * sets the command hash map
	 * @param viewCommandMap hashmap
	 */
	public void setViewCommandMap(HashMap<String, Command> viewCommandMap) {
		this.viewCommandMap = viewCommandMap;
	}
	
	/**
	 * sets if the user won or not
	 * @param a boolean
	 */
	public void isWon(boolean a){
		this.flag= a;
	}
	
	/**
	 * return the MazeDisplayer instance
	 * @return maze
	 */
	public MazeDisplayer getMaze() {
		return maze;
	}

	/**
	 * set the MazeDisplayer instance
	 * @param maze MazeDisplayer
	 */
	public void setMaze(MazeDisplayer maze) {
		this.maze = maze;
	}
	
	/**
	 * print to out the solution of the maze name
	 * @param name String
	 * @param solution Solution
	 */
	@Override
	public void oneStateDisplay(final String name, final Solution<Position> solution) {
	}
	
	
}





		
	
	
	


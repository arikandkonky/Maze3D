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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
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

	public HashMap<String, Command> viewCommandMap;
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	PrintWriter out=new PrintWriter(System.out);
	int userCommand=0;
	
	public MazeWindow(String title, int width, int height,HashMap<String, Command> viewCommandMap) {
		super(title, width, height);
		this.viewCommandMap=viewCommandMap;
	}



	@Override
	void initWidgets() {
		FontData defaultFont = new FontData("Script MT Bold",13,SWT.BOLD);
		org.eclipse.swt.graphics.Font boldFont = new org.eclipse.swt.graphics.Font(display, defaultFont);
	    shell.setText("Server Side Maze");
	    shell.setSize(300,300);
		shell.setLayout(new GridLayout(2,false));
		
		shell.setImage(new Image(display, "C:/pictures/serverIcon.ico"));
		
		shell.setBackgroundImage(new Image(display, "C:/pictures/MazeServer.png"));
		
		GridLayout layout = new GridLayout();
	    layout.numColumns = 2;
	    layout.makeColumnsEqualWidth = true;
	    shell.setLayout(layout);
	    
	    // MENU
	    
	    Menu menu = new Menu(shell, SWT.BAR);
	    MenuItem fileItem = new MenuItem(menu, SWT.CASCADE);
	    fileItem.setText("File");
	    Menu fileMenu = new Menu(menu);
	    fileItem.setMenu(fileMenu);
	    MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
	    exitItem.setText("Exit");
	    shell.setMenuBar(menu);
	    
	    //exit function to exit menu button
	    exitItem.addListener(SWT.Selection, event-> {
	        shell.getDisplay().dispose();
	        System.exit(0);        
	    });
	    
	 // LABEL 1 = PORT
	    
	    GridData data = new GridData(GridData.FILL_BOTH);
	    Label label = new Label(shell, SWT.NONE);
	    label.setText("Port :");
	   
	    // TEXT 1 = PORT
	   
	    // Create a single-line text field
	    data = new GridData(GridData.FILL_BOTH);
	    Text txtPort = new Text(shell, SWT.BORDER);
	    txtPort.setToolTipText("Enter Port Number");
	   
	    // LABEL 2 = Max Clients
	   
	    data = new GridData(GridData.FILL_BOTH);
	    Label label1 = new Label(shell, SWT.NONE);
	    label1.setText("Max Clients :");
	   
	   
	    // TEXT 1 = PORT
	   
	    // Create a single-line text field
	    data = new GridData(GridData.FILL_BOTH);
	    Text txtMaxClients = new Text(shell, SWT.BORDER);
	    txtMaxClients.setToolTipText("Enter Max Of Clients");
	   
	    // BUTTOMS
	   
	    // Button 1 - Start Server
	    data = new GridData(GridData.FILL_BOTH);
	    Button one = new Button(shell, SWT.PUSH);
	    one.setText("Server - Start");
	    one.setLayoutData(data);
	    
	    one.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String[] serverArg = {txtPort.getText().toString(),txtMaxClients.getText().toString()};
				System.out.println("Get into the View Command Map!");
				viewCommandMap.get("start server").doCommand(serverArg);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	 
	    // Button 2 - Stop Server
	    data = new GridData(GridData.FILL_BOTH);
	    Button two = new Button(shell, SWT.PUSH);
	    two.setText("Server - Stop");
	    two.setLayoutData(data);
	    
	    two.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String[] a = {""};
				viewCommandMap.get("exit").doCommand(a);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	 
	   
	    // Create the 2 long buttons across the bottom
	    data = new GridData();
	    data.horizontalAlignment = GridData.FILL;
	    data.grabExcessHorizontalSpace = true;
	    data.horizontalSpan = 3;
	    data.heightHint = 50;
	   
	    // Button 3 - Show number of connected clients
	    Button three = new Button(shell, SWT.PUSH);
	    three.setText("Num Of Connected Clients");
	    three.setLayoutData(data);
	   
	    three.addListener(SWT.Selection, new Listener() {
	    public void handleEvent(Event event) {
	    	String[] a = {""};
	       viewCommandMap.get("Num Of Clients").doCommand(a);
	       }
	    });
	   
	   Button four = new Button(shell, SWT.PUSH);
	   four.setText("Exit");
	   four.setLayoutData(data);
	   
	   //exit function to exit button
	   four.addListener(SWT.Selection, event -> {
	   System.exit(0);
	   });
	}

	
	//**********************************Getters And Setters + View Methods*********************//
	
	
	@Override
	public void setUserCommand(int i) {
		this.userCommand = i;
		this.setChanged();
		}
	

	@Override
	public int getUserCommand() {
		return this.userCommand;
	}
	
	public HashMap<String, Command> getViewCommandMap() {
		return viewCommandMap;
	}

	public void setViewCommandMap(HashMap<String, Command> viewCommandMap) {
		this.viewCommandMap = viewCommandMap;
	}
	

	


	@Override
	public void errorNoticeToUser(String string) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void serverStarted(String port,String NumOfClients) {
		MessageBox box = new MessageBox(shell);
		box.setMessage("Server is Up!");
		box.setText("Clients: "+ NumOfClients + " On Port: "+ port);
		box.open();
	}


	@Override
	public void serverStop() 
	{
		//MessageBox box = new MessageBox(shell);
		//box.setMessage("Stoped the Server");
		//box.open();
		
	}



	@Override
	public void NumOfClients(int clients) {
		MessageBox box = new MessageBox(shell);
		box.setText("Number Of Clients Online!");
		box.setMessage("Clients online:"+clients);
		box.open();
		
	}
	
	
}





		
	
	
	


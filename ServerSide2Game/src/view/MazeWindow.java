package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import presenter.Command;

/**
 * @author Nir Konky and arik bidny
 * @version 1.0
 * <h1> MazeWindow </h1>
 * This Class MazeWindow extends Observable.
 * all extends classes will have to do the methods
 */
public class MazeWindow extends BasicWindow {

	public HashMap<String, Command> viewCommandMap;
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	PrintWriter out=new PrintWriter(System.out);
	int userCommand=0;
	
	/**
	 * with the given parameters do the maze window instance
	 * @param title String
	 * @param width int
	 * @param height int
	 * @param viewCommandMap hashmap
	 */
	public MazeWindow(String title, int width, int height,HashMap<String, Command> viewCommandMap) {
		super(title, width, height);
		this.viewCommandMap=viewCommandMap;
	}


	/**
	 * init all the widgets of the server window
	 */
	@Override
	void initWidgets() {
		FontData defaultFont = new FontData("Script MT Bold",13,SWT.BOLD);
		@SuppressWarnings("unused")
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
	
	/**
	 * set user command
	 * @param i int
	 */
	@Override
	public void setUserCommand(int i) {
		this.userCommand = i;
		this.setChanged();
		}
	

	/**
	 * get user command
	 * @return userCommand int
	 */
	@Override
	public int getUserCommand() {
		return this.userCommand;
	}
	
	/**
	 * get hash map string> command
	 * @return viewCommandMap hashmap
	 */
	public HashMap<String, Command> getViewCommandMap() {
		return viewCommandMap;
	}

	/**
	 * set hash map string to command
	 * @param viewCommandMap hashmap
	 */
	public void setViewCommandMap(HashMap<String, Command> viewCommandMap) {
		this.viewCommandMap = viewCommandMap;
	}
	

	
	@Override
	/**
	 * error notice to user 
	 * @param String string
	 */
	public void errorNoticeToUser(String string) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	/**
	 * start the server in the GUI
	 * @param String port
	 * @param String NumOfClients
	 */
	public void serverStarted(String port,String NumOfClients) {
		MessageBox box = new MessageBox(shell);
		box.setMessage("Server is Up!");
		box.setText("Clients: "+ NumOfClients + " On Port: "+ port);
		box.open();
	}

	/**
	 * STOP THE Server
	 */
	@Override
	public void serverStop() 
	{
		
	}

	@Override
	/**
	 * print the num of clients to out.
	 * @param client int
	 */
	public void NumOfClients(int clients) {
		MessageBox box = new MessageBox(shell);
		box.setText("Number Of Clients Online!");
		box.setMessage("Clients online:"+clients);
		box.open();
		
	}
	
	
}





		
	
	
	


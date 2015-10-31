package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;



/**
 * @author Nir Konky and arik bidny
 * @version 1.0
 * <h1> BasicWondow </h1>
 * This abstract Class BasicWindow implements view,Runnable and extends Observable.
 * all implements classes will have to implement 
 * all extends classes will have to do the methods
 */
public abstract class BasicWindow extends Observable implements Runnable,View{	
	Display display;
	Shell shell;
	
	/**
	 * do basicWindow instance with the given parameters
	 * @param title string
	 * @param width int
	 * @param height int
	 */
 	public BasicWindow(String title, int width,int height) {
 		display=new Display();
 		shell  = new Shell(display);
 		shell.setSize(width,height);
 		shell.setText(title);
	}
 	
 	/**
 	 * init all the widgets
 	 */
 	abstract void initWidgets();

 	/**
 	 *  run the basic window 
 	 */
	@Override
	public void run() {
		initWidgets();
		Image image = new Image(display, "C:/pictures/maze.jpg");

        shell.setBackgroundImage(image);
        shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.open();
		//System.out.println("im here");
		 while(!shell.isDisposed()){ // while window isn't closed

		    if(!display.readAndDispatch()){ 	// if the queue is empty
		       display.sleep(); 			// sleep until an event occurs 
		    }

		 } // shell is disposed
		 setUserCommand(11);
		 String[] args = {"Exit"};
		 System.out.println("Exiting now");
		 
		 notifyObservers(args);
		 display.dispose(); // dispose OS components
	}
	
	/**
	 * boolean true - won false - not
	 * @param a boolean
	 */
	public abstract void isWon(boolean a);

}

package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class BasicWindow extends Observable implements Runnable,View{	
	Display display;
	Shell shell;
	
 	public BasicWindow(String title, int width,int height) {
 		display=new Display();
 		shell  = new Shell(display);
 		shell.setSize(width,height);
 		shell.setText(title);
	}
 	
 	abstract void initWidgets();

	@Override
	public void run() {
		initWidgets();
		Image image = new Image(display, "C:/Maze.png");

        shell.setBackgroundImage(image);
        shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.open();
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
	
	public abstract void isWon(boolean a);

}

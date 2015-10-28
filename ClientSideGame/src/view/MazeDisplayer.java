package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Nir Konky and arik bidny
 * @version 1.0
 * <h1> MazeDisplayer </h1>
 * This abstract class MazeDisplayer extends Canvas
 * all extends classes will have to do the methods
 */
public abstract class MazeDisplayer extends Canvas {

	int[][] mazeData;
	private int ExitX;
	private int ExitY;
	@SuppressWarnings("unused")
	private int floorExit;
	BasicWindow bs;
	boolean won;
	
	/**
	 * do instance of MazeDisplayer with the given parameters
	 * @param parent
	 * @param style
	 */
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	/**
	 * abstract method
	 */
	public abstract void moveFloorUp();
	
	/**
	 * abstract method
	 */
	public abstract void moveFloorDown();
	
	/**
	 * abstract method
	 */
	public abstract void moveUp();

	/**
	 * abstract method
	 */
	public abstract  void moveDown();

	/**
	 * abstract method
	 */
	public abstract  void moveLeft();

	/**
	 * abstract method
	 */
	public  abstract void moveRight();
	
	//*******************Getters And Setters!*********************************//
	
	/**
	 * return the BasicWindow instance
	 * @return 
	 */
	public BasicWindow getBs() {return bs;}

	/**
	 * @return boolean win / not
	 */
	public boolean getIsWon(){return won;}
	
	/**
	 * set the true / false
	 * @param is
	 */
	public void setIsWon(boolean is){this.won = is;}
	
	/**
	 * set the Basic Window instance
	 * @param bs
	 */
	public void setBs(BasicWindow bs) {this.bs = bs;}
	
	/**
	 * set the maze data
	 * @param mazeData
	 */
	public void setMazeData(int[][] mazeData){this.mazeData=mazeData;}
	
	/**
	 * abstract method
	 */
	public abstract  void setCharacterPosition(int row,int col);
	/**
	 * abstract method
	 */
	public abstract void setCharacterX(int characterX);
	/**
	 * abstract method
	 */
	public abstract int getCharacterX();
	/**
	 * abstract method
	 */
	public abstract void setCharacterY(int characterY);
	/**
	 * abstract method
	 */
	public abstract int getCharacterY();

	/**
	 * abstract method
	 */
	public abstract void setfloorExit(int exit);
	/**
	 * abstract method
	 */
	public abstract int getfloorExit();
	
	/**
	 * set the Exit x of the maze
	 * @param x
	 */
	public void setExitX(int x) {this.ExitX= x;}
	
	/**
	 * get the exit x of the maze
	 * @return
	 */
	public int getExitX() {return ExitX;}
	
	/**
	 * set the Exit y of the maze
	 * @param y
	 */
	public void setExitY(int y) {this.ExitY = y;}
	
	/**
	 * get the exit y of the maze
	 * @return
	 */
	public int getExitY() {return ExitY;}

	/**
	 * set the CurrentFloor
	 * @param currentFloor
	 */
	public void setCurrentFloor(int currentFloor) {}
	
	
}

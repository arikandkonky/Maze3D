package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public abstract class MazeDisplayer extends Canvas {

	int[][] mazeData;
	private int ExitX;
	private int ExitY;
	@SuppressWarnings("unused")
	private int floorExit;
	BasicWindow bs;
	boolean won;

	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public abstract void moveFloorUp();
	
	public abstract void moveFloorDown();
	
	public abstract void moveUp();

	public abstract  void moveDown();

	public abstract  void moveLeft();

	public  abstract void moveRight();
	
	//*******************Getters And Setters!*********************************//
	

	public BasicWindow getBs() {return bs;}

	public boolean getIsWon(){return won;}
	
	public void setIsWon(boolean is){this.won = is;}
	
	public void setBs(BasicWindow bs) {this.bs = bs;}
	
	public void setMazeData(int[][] mazeData){this.mazeData=mazeData;}
	
	public abstract  void setCharacterPosition(int row,int col);
	
	public abstract void setCharacterX(int characterX);

	public abstract int getCharacterX();
	
	public abstract void setCharacterY(int characterY);

	public abstract int getCharacterY();


	public abstract void setfloorExit(int exit);
	
	public abstract int getfloorExit();
	
	public void setExitX(int x) {this.ExitX= x;}
	
	public int getExitX() {return ExitX;}
	
	public void setExitY(int y) {this.ExitY = y;}
	
	public int getExitY() {return ExitY;}


	public void setCurrentFloor(int currentFloor) {}
	
	
}

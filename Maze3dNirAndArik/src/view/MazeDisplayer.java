package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public abstract class MazeDisplayer extends Canvas {

	int[][] mazeData;
	private int ExitX;
	private int ExitY;
	private int setExitZ;
	
	
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}


	public void setMazeData(int[][] mazeData){
		this.mazeData=mazeData;
	}
	
	public abstract  void setCharacterPosition(int row,int col);

	
	public abstract void moveUp();

	public abstract  void moveDown();

	public abstract  void moveLeft();

	public  abstract void moveRight();
	
	public abstract void setCharacterX(int characterX);

	public abstract int getCharacterX();
	
	public abstract void setCharacterY(int characterY);

	public abstract int getCharacterY();



	public void setExitX(int x) {
		this.ExitX= x;
	}


	public void setExitY(int y) {
		this.ExitY = y;		
	}
	
	public int getExitX() {
		return ExitX;}


	public int getExitY() {
		return ExitY;}



	public void setExitZ(int z) {
		this.setExitZ = z;
		
	}


	public void setCurrentFloor(int currentFloor) {
		// TODO Auto-generated method stub
		
	}
	
	
}

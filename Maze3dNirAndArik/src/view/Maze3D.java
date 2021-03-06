package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class Maze3D extends MazeDisplayer {

		public int characterX=0;
		public int characterY=2;
		public int exitX=0;
		public int exitY=2;
		public int floorExit;
		private int currentFloor;
		
		private void paintCube(double[] p,double h,PaintEvent e){
	        int[] f=new int[p.length];
	        for(int k=0;k<f.length;f[k]=(int)Math.round(p[k]),k++);
	        
	        e.gc.drawPolygon(f);
	        
	        int[] r=f.clone();
	        for(int k=1;k<r.length;r[k]=f[k]-(int)(h),k+=2);
	        

	        int[] b={r[0],r[1],r[2],r[3],f[2],f[3],f[0],f[1]};
	        e.gc.drawPolygon(b);
	        int[] fr={r[6],r[7],r[4],r[5],f[4],f[5],f[6],f[7]};
	        e.gc.drawPolygon(fr);
	        
	        e.gc.fillPolygon(r);
			
		}
		public Maze3D(Composite parent, int style) {
			super(parent, style);
			
			@SuppressWarnings("unused")
			final Color white=new Color(null, 255, 255, 255);
			@SuppressWarnings("unused")
			final Color black=new Color(null, 150,150,150);
			Image bGImage = new Image(getDisplay(), "C:/Maze.png");
			setBackgroundImage(bGImage);
			addPaintListener(new PaintListener() {
				
				@Override
				public void paintControl(PaintEvent e) {
					   e.gc.setForeground(new Color(null,0,0,0));
					   e.gc.setBackground(new Color(null,0,0,0));
				
					   int width=getSize().x;
					   int height=getSize().y;
					   
					   int mx=width/2;

					   double w=(double)width/mazeData[0].length;
					   double h=(double)height/mazeData.length;

					   for(int i=0;i<mazeData.length;i++){
						   double w0=0.7*w +0.3*w*i/mazeData.length;
						   double w1=0.7*w +0.3*w*(i+1)/mazeData.length;
						   double start=mx-w0*mazeData[i].length/2;
						   double start1=mx-w1*mazeData[i].length/2;
					      for(int j=0;j<mazeData[i].length;j++){
					          double []dpoints={start+j*w0,i*h,start+j*w0+w0,i*h,start1+j*w1+w1,i*h+h,start1+j*w1,i*h+h};
					          double cheight=h/2;
					          if(mazeData[i][j]!=0)
					        	  paintCube(dpoints, cheight,e);
					          if(j==characterY && i==characterX && exitX==characterX &&exitY ==characterY&& floorExit == currentFloor)
					          {
					        	  /* Draw Exit & Character at the same place. */ 
					        	  e.gc.setBackground(new Color(null,200,100,0));
					        	  e.gc.fillOval((int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), (int)Math.round((w0+w1)/2), (int)Math.round(h));
					        	  e.gc.setBackground(new Color(null,0,255,255));
					        	  e.gc.fillOval((int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));
					        	  e.gc.setBackground(new Color(null,0,0,0));
					          }
					          
					          else
					          {
					        	  if (j==characterY && i==characterX)
					        	  {
								   e.gc.setBackground(new Color(null,200,0,0));
								   e.gc.fillOval((int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), (int)Math.round((w0+w1)/2), (int)Math.round(h));
								   e.gc.setBackground(new Color(null,255,0,0));
								   e.gc.fillOval((int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));
								   e.gc.setBackground(new Color(null,0,0,0));
					        	  }
					        	  /* Draw Exit */ 
						          if(i==exitX && j==exitY && floorExit == currentFloor){
						        	  e.gc.setBackground(new Color(null,0,255,255));
						        	  e.gc.fillRectangle((int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), (int)Math.round((w0+w1)/2), (int)Math.round(h));
						        	  e.gc.setBackground(new Color(null,0,100,200));
						        	  e.gc.fillRectangle((int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));
						        	  e.gc.setBackground(new Color(null,0,0,0));
						        	  won = true;
						        	  
						          }
					          }
					      }

					   }
					   
					   
					
				}
			});
		}

		private void moveCharacter(int x,int y){
			if(y>=0 && y<mazeData[0].length && x>=0 && x<mazeData.length && mazeData[x][y]==0){
				characterX=x;
				characterY=y;
				getDisplay().syncExec(new Runnable() {
					
					@Override
					public void run() {
						redraw();
					}
				});
			}
		}
		

		public void moveFloorDown(){
			moveCharacter(characterX, characterY);
			
		}
		public void moveFloorUp(){
			moveCharacter(characterX, characterY);
			
		}
		@Override
		public void moveUp() {
			int x=characterX;
			int y=characterY;
			x=x-1;
			moveCharacter(x,y);
		}

		
		@Override
		public void moveDown() {
			int x=characterX;
			int y=characterY;
			x=x+1;
			moveCharacter(x, y);


		}
		@Override
		public void moveLeft() {
			int x=characterX;
			int y=characterY;
			y=y-1;
			moveCharacter(x, y);

		}

		@Override
		public void moveRight() {
			int x=characterX;
			int y=characterY;
			y=y+1;
			moveCharacter(x, y);

		}
		
		@Override
		public void setCharacterPosition(int row, int col) {
			characterX=row;
			characterY=col;
			moveCharacter(row,col);
		}
		
		//*******************Getters And Setters!*********************************//
		public int getCharacterX() {return this.characterX;}
		
		public void setCharacterX(int characterX) {this.characterX = characterX;}
		
		public int getCharacterY() {return this.characterY;}
		
		public void setCharacterY(int characterY) {this.characterY = characterY;}
		
		public int getExitX() {return exitX;}
		
		public void setExitX(int exitX) {this.exitX = exitX;}
		
		public int getExitY() {return exitY;}
		
		public void setExitY(int exitY) {this.exitY = exitY;}
		
		public int getCurrentFloor() {return currentFloor;}
		
		public void setCurrentFloor(int currentFloor) {this.currentFloor = currentFloor;}
		
		@Override
		public void setfloorExit(int exit) {this.floorExit = exit;}
		
		public int getfloorExit(){return floorExit;}

	}


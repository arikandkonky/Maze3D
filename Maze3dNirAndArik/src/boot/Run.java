package boot;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Tests.AstarTest;
import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import view.Maze3D;
import view.MazeDisplayer;
import view.MazeWindow;
import view.MyView;
import view.View;

public class Run {
	public static void main(String[] args) throws Exception {
		XMLDecoder decoder=null;
		decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("properties.xml")));
		Properties properties=(Properties)decoder.readObject();
		/*
		System.out.println(properties);
		try {
			System.out.println(properties);	
			MyView view = new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
			MyModel model = new MyModel(properties);
			Presenter presenter = new Presenter(view, model);
			view.setStringtoCommand(presenter.stringtoCommand);
			view.addObserver(presenter);
			model.addObserver(presenter);
			view.start();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: properties.xml not found");
		}*/
		//if(properties.getUI().equals("GUI"))
		//{
			MyModel model = new MyModel(properties);
			MazeWindow view=new MazeWindow("3D Maze Game", 500, 300,null);
			Presenter presenter = new Presenter(view, model);
			//view.setMazeData(mazeData);
			view.setViewCommandMap((presenter.getStringtoCommand()));
			view.addObserver(presenter);
			model.addObserver(presenter);
			view.run();
		//}
	}
	}




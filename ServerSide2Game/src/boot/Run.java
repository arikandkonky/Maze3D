package boot;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import presenter.ServerProperties;
import view.MazeWindow;
import view.MyView;

public class Run {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		XMLDecoder decoder=null;
		decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("properties.xml")));
		Properties properties=(Properties)decoder.readObject();
		
		XMLDecoder decoder2=null;
		decoder2=new XMLDecoder(new BufferedInputStream(new FileInputStream("ServerProperties.xml")));
		ServerProperties sp =(ServerProperties)decoder2.readObject();
		
		
		if(properties.getUI().equals("CLI"))
		{
			try {
				
				System.out.println(properties);	
				System.out.println(sp);
				MyView view = new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
				MyModel model = new MyModel(properties,sp);
				Presenter presenter = new Presenter(view, model);
				view.setStringtoCommand(presenter.stringtoCommand);
				view.addObserver(presenter);
				model.addObserver(presenter);
				view.start();
				}
			catch (FileNotFoundException e) 
				{
					System.out.println("ERROR: properties.xml not found");
				}
		}
		if(properties.getUI().equals("GUI"))
		{
			MyModel model = new MyModel(properties,sp);
			MazeWindow view=new MazeWindow("3D Server Side", 500, 300,null);
			Presenter presenter = new Presenter(view, model);
			view.setViewCommandMap((presenter.getStringtoCommand()));
			view.addObserver(presenter);
			model.addObserver(presenter);
			view.run();
	
		}
	}
	
}



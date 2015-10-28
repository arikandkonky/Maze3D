package model;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.AstarCommonSearcher;
import algorithms.search.BfsCommonSearcher;
import algorithms.search.Manhattandistance;
import algorithms.search.Maze3DSolution;
import algorithms.search.Maze3dSearch;
import algorithms.search.Searchable;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Properties;
import presenter.ServerProperties;

public class MyModel extends Observable implements Model{

	Object data;
	Properties p;
	int modelCompletedCommand=0;
	ExecutorService c ;
	Maze3d maze;
	ObjectInputStream input;
	HashMap<Maze3d, Solution<Position>> solutionMap = new HashMap<Maze3d, Solution<Position>>();
	MyTCPIPServer server;
	Socket someClient;
	ServerProperties sp;
	boolean flag = false;
	
	public MyModel(){
		this.c = Executors.newFixedThreadPool(30);
	}
	@SuppressWarnings("unchecked")
	public MyModel(Properties p,ServerProperties sp) throws Exception
	{
		super();
		this.p = p;
		this.sp = sp;
		this.c = Executors.newFixedThreadPool(p.getNumofThreads());
		File sol = new File("solutionMap.txt");
		if(sol.exists())
		{
			ObjectInputStream solLoader;
			try
			{
				solLoader = new ObjectInputStream(new GZIPInputStream(new FileInputStream(new File("solutionMap.txt"))));
				solutionMap = (HashMap<Maze3d, Solution<Position>>) solLoader.readObject();
				solLoader.close();
				this.p = read("Properties.xml");
			}
			catch(FileNotFoundException e){errorNoticeToController("Error: problem with solution file");}
			catch(IOException e){errorNoticeToController("Error: IO exeption");}
			catch(ClassNotFoundException e){errorNoticeToController("Error: problem with class");}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public MyModel(Socket someClient, Properties p,ServerProperties sp) throws Exception
	{
		super();
		this.sp =sp;
		System.out.println(sp.getNumofThreads());
		this.p = p;
		this.someClient = someClient;
		this.c = Executors.newFixedThreadPool(p.getNumofThreads());
		File sol = new File("solutionMap.txt");
		if(sol.exists())
		{
			ObjectInputStream solLoader;
			try
			{
				solLoader = new ObjectInputStream(new GZIPInputStream(new FileInputStream(new File("solutionMap.txt"))));
				solutionMap = (HashMap<Maze3d, Solution<Position>>) solLoader.readObject();
				solLoader.close();
				this.p = read("Properties.xml");
			}
			catch(FileNotFoundException e){errorNoticeToController("Error: problem with solution file");}
			catch(IOException e){errorNoticeToController("Error: IO exeption");}
			catch(ClassNotFoundException e){errorNoticeToController("Error: problem with class");}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public MyModel(Socket someClient, Properties p) throws Exception
	{
		super();
		this.p = p;
		this.someClient = someClient;
		this.c = Executors.newFixedThreadPool(p.getNumofThreads());
		File sol = new File("solutionMap.txt");
		if(sol.exists())
		{
			ObjectInputStream solLoader;
			try
			{
				solLoader = new ObjectInputStream(new GZIPInputStream(new FileInputStream(new File("solutionMap.txt"))));
				solutionMap = (HashMap<Maze3d, Solution<Position>>) solLoader.readObject();
				solLoader.close();
				this.p = read("Properties.xml");
			}
			catch(FileNotFoundException e){errorNoticeToController("Error: problem with solution file");}
			catch(IOException e){errorNoticeToController("Error: IO exeption");}
			catch(ClassNotFoundException e){errorNoticeToController("Error: problem with class");}
		}
	}
	
	@SuppressWarnings("unchecked")
	public MyModel(Properties p) throws Exception
	{
		super();
		this.p = p;
		this.c = Executors.newFixedThreadPool(p.getNumofThreads());
		File sol = new File("solutionMap.txt");
		if(sol.exists())
		{
			ObjectInputStream solLoader;
			try
			{
				solLoader = new ObjectInputStream(new GZIPInputStream(new FileInputStream(new File("solutionMap.txt"))));
				solutionMap = (HashMap<Maze3d, Solution<Position>>) solLoader.readObject();
				solLoader.close();
				this.p = read("Properties.xml");
			}
			catch(FileNotFoundException e){errorNoticeToController("Error: problem with solution file");}
			catch(IOException e){errorNoticeToController("Error: IO exeption");}
			catch(ClassNotFoundException e){errorNoticeToController("Error: problem with class");}
		}
	}
	
	
	@Override
	public void startServer(String port, String numOfClients)  
	{
		Thread t = new Thread(new Runnable() {
			public void run() {
				Integer intPort = new Integer(port);
				Integer intClient = new Integer(numOfClients);
				server = new MyTCPIPServer(intPort);
				System.out.println("Before the start server!");
				server.startServer(intClient);
				System.out.println("server started alrdy.");
				modelCompletedCommand=1;
				

				
			}
		});
		c.execute(t);
		
	}
	
	public void errorNoticeToController(String s)
	{
		modelCompletedCommand=-1;
		data = s;
		this.setChanged();
		notifyObservers();
	}
	
	public void NumOfClients(){
		data = server.getNumOfClients();
		modelCompletedCommand=2;
		this.setChanged();
		notifyObservers();
		
	}
	
	public void exit()
	{

		c.shutdown();
		ObjectOutputStream save;
		try {
			save = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(new File("solutionMap.txt"))));
			save.writeObject(solutionMap);
			System.out.println("stopServer.");
			server.stopServer();
			save.flush();
			save.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public static Properties read(String filename) throws Exception {
        XMLDecoder decoder =new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        Properties properties = (Properties)decoder.readObject();
        decoder.close();
        return properties;
    }

	@Override
	public Solution<Position> solveMazeUser(Maze3d maze, String defSolver) throws IOException, ClassNotFoundException, InterruptedException, ExecutionException {

		if(solutionMap.containsKey(maze))
		{
			this.modelCompletedCommand=9;
			this.setData(maze.getMazeName());
			Solution<Position> a = solutionMap.get(maze);
			return a;
		}
		else
		{
		flag = true;
		Future<Solution<Position>> f= c.submit(new Callable<Solution<Position>>() {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Solution<Position> call() throws Exception 
			{	
				Searchable<Position> s = new Maze3dSearch(maze);
				Maze3DSolution solution = new Maze3DSolution();
				if(defSolver.equals("bfs"))
				{
					System.out.println("Solve with defualt bfs");
					BfsCommonSearcher Bfs = new BfsCommonSearcher(solution);
					Bfs.setSolution(solution);
					return (Maze3DSolution)Bfs.Search(s);
				}
				else if(defSolver.equals("astar"))
				{
					try 
					{
						System.out.println("solve with default astar, Manhetthen distance");
						Manhattandistance h2 = new Manhattandistance();
						AstarCommonSearcher Astar = new AstarCommonSearcher(h2, solution, s);
						Astar.setSolution(solution);
						return (Maze3DSolution)Astar.Search(s);
					}
					catch (Exception e)
					{
						errorNoticeToController(e.getMessage());
						e.printStackTrace();
					}
							
				}
				return solution;
			}
		});
		Solution<Position> a = f.get();
		System.out.println("Send this massage back to the client: ");
		for (State<Position> sol: a.getSolution())
		{
			System.out.println(sol.getActionName().toString());
		}
		solutionMap.put(maze, f.get());
		return a;
		}
	}
		
	
	//*************************Getters and Setters Methonds***********************************//
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getModelCompletedCommand() {
		return modelCompletedCommand;
	}

	public void setModelCompletedCommand(int modelCompletedCommand) {
		this.modelCompletedCommand = modelCompletedCommand;
	}

}
	






	


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
import algorithms.mazeGenerators.Maze3dPosition;
import algorithms.mazeGenerators.Position;
import algorithms.search.AstarCommonSearcher;
import algorithms.search.BfsCommonSearcher;
import algorithms.search.Manhattandistance;
import algorithms.search.Maze3DSolution;
import algorithms.search.Maze3dSearch;
import algorithms.search.Searchable;
import algorithms.search.Solution;
import presenter.Properties;

public class MyModel extends Observable implements Model{

	Object data;
	Properties p;
	int modelCompletedCommand=0;
	ExecutorService c ;
	HashMap<String, Maze3d> stringtoMaze3d = new HashMap<String, Maze3d>();
	HashMap<Maze3d, Solution<Position>> solutionMap = new HashMap<Maze3d, Solution<Position>>();
	MyTCPIPServer server;
	
	public MyModel(){
		this.c = Executors.newFixedThreadPool(30);
		//Constractor with 30 threads in the thread pool.
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
	public void startServer(String numOfPlayers) {
		Integer numOfPlayersInt = new Integer(numOfPlayers);
		MyTCPIPServer server = new MyTCPIPServer(12345);
		server.startServer(numOfPlayersInt);
		this.modelCompletedCommand=1;
		this.setData(numOfPlayers);
		setChanged();
		notifyObservers();
		
	}
	
	public void solveMaze(final String name, final String algorithm)
	{
		if(stringtoMaze3d.containsKey(name))
		{
			if(solutionMap.containsKey(stringtoMaze3d.get(name)))
			{
				System.out.println("alrdy solved this maze, wont do it again..");
				this.modelCompletedCommand=9;
				this.setData(name);
				setChanged();
				notifyObservers();
			}
			else
			{
				Callable<Solution<Position>> mazeSolver =new Callable<Solution<Position>>() 
				{
					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public Solution<Position> call() throws Exception 
					{
						Maze3d maze = stringtoMaze3d.get(name);
						Searchable<Position> s = new Maze3dSearch(maze);
						Maze3DSolution solution = new Maze3DSolution();
						if(algorithm.equals("bfs"))
						{
							System.out.println("Solve with User bfs");
							BfsCommonSearcher Bfs = new BfsCommonSearcher(solution);
							Bfs.setSolution(solution);							
							solutionMap.put(stringtoMaze3d.get(name), (Maze3DSolution)Bfs.Search(s));
							solveMazeByBfs(name);
						}
						else if(p.getDefSolver().equals("bfs"))
						{
							System.out.println("Solve with defualt bfs");
							BfsCommonSearcher Bfs = new BfsCommonSearcher(solution);
							Bfs.setSolution(solution);							
							solutionMap.put(stringtoMaze3d.get(name), (Maze3DSolution)Bfs.Search(s));
							solveMazeByBfs(name);
						}
						else if(algorithm.equals("astar") || algorithm.equals("A*") || algorithm.equals("Astar")){
							System.out.println("solve with user astar, Manhetthen distance");
							Manhattandistance h2 = new Manhattandistance();
							AstarCommonSearcher Astar = new AstarCommonSearcher<>(h2, solution, s);
							Astar.setSolution(solution);
							solutionMap.put(stringtoMaze3d.get(name), (Maze3DSolution)Astar.Search(s));
							solveMazeByAstar(name);
						}
						else if(p.getDefSolver().equals("astar"))
						{
							System.out.println("solve with default astar, Manhetthen distance");
							Manhattandistance h2 = new Manhattandistance();
							AstarCommonSearcher Astar = new AstarCommonSearcher<>(h2, solution, s);
							Astar.setSolution(solution);
							solutionMap.put(stringtoMaze3d.get(name), (Maze3DSolution)Astar.Search(s));
							solveMazeByAstar(name);
						}
							return null;
					}
				};c.submit(mazeSolver);
			}
		}
	}
	
	/*public void getSolution(String name)
	{
		if(stringtoMaze3d.containsKey(name))
		{
			if(solutionMap.containsKey(stringtoMaze3d.get(name)))
			{
				this.modelCompletedCommand = 10;
				Object[] dataSet = new Object[2];
				dataSet[0] = name;
				dataSet[1] = solutionMap.get(stringtoMaze3d.get(name));
				setChanged();
				this.setData(dataSet);
				notifyObservers();
			}
			else{errorNoticeToController("this maze didnt solve yet");}
		}
		else{errorNoticeToController("this maze not exists");}
	}*/
	
	@Override
	public void changeXmlFile(String filename) throws FileNotFoundException {
		File file = new File(filename);
		if (file.exists())
		{
			this.modelCompletedCommand = 12;
			@SuppressWarnings("resource")
			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
			p.setDefAlgorithm((String) decoder.readObject());
			p.setDefSolver((String) decoder.readObject());
			p.setNumofThreads((int) decoder.readObject());
			p.setUI((String) decoder.readObject());
			Object[] dataSet = new Object[4];
			dataSet[0] = p.getDefAlgorithm();
			dataSet[1] = p.getDefSolver();
			dataSet[2] = p.getNumofThreads();
			dataSet[3] = p.getUI();
			setChanged();
			this.setData(dataSet);
			notifyObservers();
		}
		else
			errorNoticeToController("Xml file Error");
		
	}
	private void solveMazeByBfs(String name) {
		this.modelCompletedCommand= 9;
		setChanged();
		setData(name);
		notifyObservers();
	}
	
	private void solveMazeByAstar(String name) {
		this.modelCompletedCommand= 9;
		setChanged();
		setData(name);
		notifyObservers();
	}
	
	public void errorNoticeToController(String s)
	{
		modelCompletedCommand=-1;
		data = s;
		this.setChanged();
		notifyObservers();
	}
	
	

	
	public void exit()
	{
		try{
			this.modelCompletedCommand =11;
			c.shutdown();
			ObjectOutputStream save = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(new File("solutionMap.txt"))));
			save.writeObject(this.solutionMap);
			save.flush();
			save.close();
		}catch(Exception e){
			errorNoticeToController("Thread Pool didnt exit");
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
	public void solveMazeUser(final String name, final String x, final String y, final String z) {
		int X = new Integer(x);
		int Y = new Integer(y);
		int Z = new Integer(z);
		final String mazenewStart = name+x+y+z;
		Maze3dPosition mazeStartPosition = new Maze3dPosition(X, Y, Z);
		final Maze3d maze = stringtoMaze3d.get(name);
		maze.setStartPosition(mazeStartPosition);
		stringtoMaze3d.put(mazenewStart, maze);
			if(solutionMap.containsKey(maze))
			{
				this.modelCompletedCommand=9;
				this.setData(mazenewStart);
				setChanged();
				notifyObservers();
			}
			else
			{
				
				Future<Solution<Position>> f= c.submit(new Callable<Solution<Position>>() {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public Solution<Position> call() throws Exception 
					{
						Searchable<Position> s = new Maze3dSearch(maze);
						Maze3DSolution solution = new Maze3DSolution();
						if(p.getDefSolver().equals("bfs"))
						{
							System.out.println("Solve with defualt bfs");
							BfsCommonSearcher Bfs = new BfsCommonSearcher(solution);
							Bfs.setSolution(solution);
							//solveMazeByBfs(mazenewStart);
							return (Maze3DSolution)Bfs.Search(s);
						}
						else if(p.getDefSolver().equals("astar"))
						{
							try {
								System.out.println("solve with default astar, Manhetthen distance");
								Manhattandistance h2 = new Manhattandistance();
								AstarCommonSearcher Astar = new AstarCommonSearcher(h2, solution, s);
								Astar.setSolution(solution);
								System.err.println(solution.toString());
								return (Maze3DSolution)Astar.Search(s);
							} catch (Exception e) {
								errorNoticeToController(e.getMessage());
								e.printStackTrace();
							}
							
						}
						return solution;
					}
				});
				try
				{
					solutionMap.put(maze, f.get());
					solveMazeByAstar(mazenewStart);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	@Override
	public void solveMazeUserOnepoint(final String name) {
		{
				if(solutionMap.containsKey(stringtoMaze3d.get(name)))
				{
					this.modelCompletedCommand = 10;
					Object[] dataSet = new Object[2];
					dataSet[0] = name;
					dataSet[1] = solutionMap.get(stringtoMaze3d.get(name));
					System.out.println("The maze is solved alrdy.");
					setChanged();
					this.setData(dataSet);
					notifyObservers();
				}
				else{errorNoticeToController("this maze didnt solve yet");}
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



	


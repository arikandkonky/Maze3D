package model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
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
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Maze3dPosition;
import algorithms.mazeGenerators.My3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.AstarCommonSearcher;
import algorithms.search.BfsCommonSearcher;
import algorithms.search.Manhattandistance;
import algorithms.search.Maze3DSolution;
import algorithms.search.Maze3dSearch;
import algorithms.search.Searchable;
import algorithms.search.Solution;
import algorithms.search.State;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.Properties;

public class MyModel extends Observable implements Model{

	Object data;
	Properties p;
	int modelCompletedCommand=0;
	ExecutorService c ;
	HashMap<String, Maze3d> stringtoMaze3d = new HashMap<String, Maze3d>();
	HashMap<Maze3d, Solution<Position>> solutionMap = new HashMap<Maze3d, Solution<Position>>();
	
	public MyModel(){
		this.c = Executors.newFixedThreadPool(30);
		//Constractor with 30 threads in the thread pool.
	}
	
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
	
	
	
	
	public void dir(String dir) throws NullPointerException
	{
		if(dir!=null)
		{
			File file = new File(dir);
			if(file.exists())
			{
				String stringofdir = " ";
				for(String fileorDir : file.list())
				{
					stringofdir += fileorDir+ " ; ";
				}
				this.data = stringofdir;
				this.modelCompletedCommand=1;
				this.setChanged();
				notifyObservers();
			}
			else
				errorNoticeToController("Illegal Path");
			
		}
		else
			errorNoticeToController("Illegal Path");
	}

	public synchronized void generatemazewithname(final String name, final String generator , final String floor, final String line, final String col)
	{
		if(floor.isEmpty()||line.isEmpty()||col.isEmpty()){errorNoticeToController("Wrong parameters use :generate 3d maze <name> <my3dgenerator/simple3dgenerator> <floor> <line> <col>" );}
		final Future<Maze3d> futuremaze  = c.submit(new Callable<Maze3d>() {
			@Override
			public Maze3d call() throws Exception 
			{
				Maze3dGenerator maze;
				if(generator.equals("my3dgenerator"))
				{
					maze = new My3dGenerator();
					errorNoticeToController("User: Generating maze with My3dGenerator with the given parameters");							
						System.out.println("Generating");
						return maze.generate(new Integer(floor),new Integer(line),new Integer(col));	
				}
				else if(generator.equals("simplemaze3dgenerator"))
				{
					System.out.println("Heyy i get in!");
					maze = new SimpleMaze3dGenerator();
					errorNoticeToController("User: Generating maze with SimpleMaze3dGenerator with the given parameters");
						System.out.println("Generating");
						return maze.generate(new Integer(floor),new Integer(line),new Integer(col));
						
				}
				else if(p.getDefAlgorithm().equals("My3dGenerator"))
				{
					maze = new My3dGenerator();
					errorNoticeToController("Defualt: Generating maze with My3dGenerator with the given parameters");
						System.out.println("Generating");
						return maze.generate(new Integer(floor),new Integer(line),new Integer(col));
				}
				else if(p.getDefAlgorithm().equals("simplemaze3dgenerator"))
				{
					maze = new SimpleMaze3dGenerator();
					errorNoticeToController("Default: Generating maze with SimpleMaze3dGenerator with the given parameters");
			
						System.out.println("Generating");
						return maze.generate(new Integer(floor),new Integer(line),new Integer(col));
					
				}
			return null;
			}
		});

			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
					Maze3d maze = futuremaze.get();
					//maze = futuremaze.get();
					stringtoMaze3d.put(name, maze);
					setChanged();
					modelCompletedCommand=2;
					setData(name);
					notifyObservers();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		
			t.start();
	

	}

	public synchronized void getMazeBygivenName(String name)
	{
		if(stringtoMaze3d.containsKey(name))
		{
			this.modelCompletedCommand=3;
			Object[] dataSet = new Object[2];
			dataSet[0] = stringtoMaze3d.get(name);
			dataSet[1] = name;
			data = dataSet;
			setChanged();
			notifyObservers();
		}
	}
	
	public synchronized void getcrossby(String xyz, String index,String For,String name) {
		int[][] returnto = null;
		if(stringtoMaze3d.containsKey(name))
		{
			Maze3d a = stringtoMaze3d.get(name);
			//Floors
			if(xyz.equals("x"))
			{
				if(new Integer(index)>0 && new Integer(index)< a.getFloor()){
				returnto = a.getCrossSectionByX(new Integer(index));
				}
				else
				{
					errorNoticeToController("bad Section by :"+ xyz + "try between 0-" + a.getFloor());
				}
			}
			//Lines
			else if(xyz.equals("y"))
			{
				if(new Integer(index)>0 && new Integer(index)< a.getLine())
				{
					returnto = a.getCrossSectionByY(new Integer(index));
				}
				else
				{
					errorNoticeToController("bad Section by :"+ xyz + "try between 0-" + a.getLine());
				}
			}
			//Cols
			else if(xyz.equals("z"))
			{
				if(new Integer(index)>0 && new Integer(index)< a.getCol())
				{
					returnto = a.getCrossSectionByZ(new Integer(index));
				}
				else
				{
					errorNoticeToController("bad Section by :"+ xyz  + "try between 0-" + a.getCol());
				}
			}
			else 
			{
				errorNoticeToController("bad xyz, try: x,y,z...");
			}
			
		}
		else if(!stringtoMaze3d.containsKey(name))
		{
			errorNoticeToController("there is no maze in this name: " +name );
		}
		if(returnto!=null)
		{
			Object[] dataSet = new Object[4];
			dataSet[0] = (int[][])returnto;
			dataSet[1] = (String)xyz;
			dataSet[2] = (String)index;
			dataSet[3] = (String)name;
			this.data = dataSet;
			this.setData(dataSet);
			this.modelCompletedCommand = 4;
			setChanged();
			notifyObservers();
		}
		else{errorNoticeToController("Problems with the Args");}
	}
	
	public void saveMazeToFile(String name, String filename) throws IOException{
		if(filename.isEmpty()||name.isEmpty()){errorNoticeToController("Cannot resolve filename\\maze name");}
		else
		{
			if(stringtoMaze3d.containsKey(name))
			{
				File fileCreator = new File(filename);
				if(fileCreator.exists())
				{
					OutputStream out=new MyCompressorOutputStream(new FileOutputStream(filename));
					out.write(stringtoMaze3d.get(name).toByteArray());
					out.flush();
					out.close();
					String[] datatToSet = new String[2];
					datatToSet[0] = name;
					datatToSet[1] = filename;
					data = datatToSet;
					this.modelCompletedCommand=5;
					this.setChanged();
					this.notifyObservers();
				}
				else if(!fileCreator.exists())
				{
					if(fileCreator.createNewFile())
					{
						OutputStream out=new MyCompressorOutputStream(new FileOutputStream(filename));
						out.write(stringtoMaze3d.get(name).toByteArray());
						out.flush();
						out.close();
						String[] datatToSet = new String[2];
						datatToSet[0] = name;
						datatToSet[1] = filename;
						data = datatToSet;
						this.modelCompletedCommand=5;
						this.setChanged();
						this.notifyObservers();
					}
					else{errorNoticeToController("It seems that file exists/Cannot create file.");}
				}
			}
			else
			{
				errorNoticeToController("The name is incorrect");
				throw new NullPointerException("There is no maze " +name);
			}
		}
	}
	public void loadMazeToFile(String filename, String name) throws IOException{
		File f = new File(filename);
		if (f.exists()){
			FileInputStream fileIn = new FileInputStream(filename);
			byte[] buffer = new byte[12];
			fileIn.read(buffer, 0, 12);
			byte[] buffer4 = new byte[4];
			for(int i=0;i<4;i++){buffer4[i]=buffer[i];}
				int floor = ByteBuffer.wrap(buffer4).getInt();
		
			for(int i=0;i<4;i++){buffer4[i]=buffer[i+4];}
				int line = ByteBuffer.wrap(buffer4).getInt();
		
			for(int i=0;i<4;i++){buffer4[i]=buffer[i+8];}
				int col = ByteBuffer.wrap(buffer4).getInt();
			System.out.println("floor: " + floor + " line: " + line + " col: " + col);
			byte b[]=new byte[(floor*line*col) + 36];
            InputStream in=new MyDecompressorInputStream(new FileInputStream(filename));
			in.read(b);
			in.close();
			System.out.println("everyting good until here");
			Maze3d maze = new Maze3d(b);
			stringtoMaze3d.put(name, maze);
			String[] dataSet = new String[2];
			dataSet[0] = filename;
			dataSet[1] = name;
			data = dataSet;
			this.modelCompletedCommand = 6;
			this.setChanged();
			this.notifyObservers();
		}
		else{
			errorNoticeToController("file not found, try another file");
			throw new FileNotFoundException("File Not Found");
			}
	}
	
	
	public void MazeSize(String name)
	{
		if(stringtoMaze3d.containsKey(name))
		{
			Object[] dataSet = new Object[2];
			dataSet[0] = name;
			dataSet[1] = new Double(stringtoMaze3d.get(name).toByteArray().length);
			this.modelCompletedCommand=7;
			this.setChanged();
			this.setData(dataSet);
			notifyObservers();
		}
		else
			errorNoticeToController("there is no maze name: "+ name);
	}
	
	public void fileSize(String filename)
	{
		File file = new File(filename);
		if(file.exists())
		{
			Object[] dataSet = new Object[2];
			dataSet[0] = filename;
			dataSet[1] = new Double(file.length());
			this.modelCompletedCommand=8;
			this.setData(dataSet);
			this.setChanged();
			notifyObservers();
		}
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
				Callable<Solution<Position>> mazeSolver =new Callable<Solution<Position>>() {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public Solution<Position> call() throws Exception 
					{
						System.out.println(algorithm);
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
	
	public void getSolution(String name)
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
		else{errorNoticeToController("this maze didnt solve yet");}
	}
	
	@Override
	public void changeXmlFile(String filename) throws FileNotFoundException {
		File file = new File(filename);
		if (file.exists())
		{
			this.modelCompletedCommand = 12;
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
		//Solution<Position> solution = solutionMap.get(stringtoMaze3d.get(name));
		this.modelCompletedCommand= 9;
		setChanged();
		setData(name);
		notifyObservers();
	}
	
	private void solveMazeByAstar(String name) {
		//Solution<Position> solution = solutionMap.get(stringtoMaze3d.get(name));
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
		Maze3d maze = stringtoMaze3d.get(name);
		Maze3dPosition mazeStartPosition = new Maze3dPosition(X, Y, Z);
		maze.setStartPosition(mazeStartPosition);
		stringtoMaze3d.put((name+""+x+""+y+""+z), maze);
		if(solutionMap.containsKey(stringtoMaze3d.get(name+""+x+""+y+""+z))){
		Solution<Position> Solutionmap = solutionMap.get(stringtoMaze3d.get(name+""+x+""+y+""+z));
		
		for(State<Position> aa : Solutionmap.getSolution())
		{
			System.out.println(aa.getActionName().toString());
		}
		}
		if(stringtoMaze3d.containsKey(name+""+x+""+y+""+z))
		{
			if(solutionMap.containsKey(stringtoMaze3d.get(name+""+x+""+y+""+z)))
			{
				System.out.println("alrdy solved this maze, wont do it again..");
				this.modelCompletedCommand=9;
				this.setData(name);
				System.out.println(stringtoMaze3d.get(name+""+x+""+y+""+z).getGoalPosition().toString());
				System.out.println("GOAL POSITION#$%#$%$#%$#%: "+ solutionMap.get(stringtoMaze3d.get(name+""+x+""+y+""+z)).toString());
				setChanged();
				notifyObservers();
			}
			else
			{
				Callable<Solution<Position>> mazeSolver =new Callable<Solution<Position>>() {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public Solution<Position> call() throws Exception 
					{
						Maze3d maze = stringtoMaze3d.get((name+""+x+""+y+""+z));
						Searchable<Position> s = new Maze3dSearch(maze);
						System.out.println("GOAL POSITION#$%#$%$#%$#%: "+ stringtoMaze3d.get(name+""+x+""+y+""+z).getGoalPosition().toString());
						Maze3DSolution solution = new Maze3DSolution();
						if(p.getDefSolver().equals("bfs"))
						{
							System.out.println("Solve with defualt bfs");
							BfsCommonSearcher Bfs = new BfsCommonSearcher(solution);
							Bfs.setSolution(solution);
							
							solutionMap.put(stringtoMaze3d.get(name), (Maze3DSolution)Bfs.Search(s));
							solveMazeByBfs(name);
						}
						else if(p.getDefSolver().equals("astar"))
						{
							System.out.println("solve with default astar, Manhetthen distance");
							Manhattandistance h2 = new Manhattandistance();
							AstarCommonSearcher Astar = new AstarCommonSearcher<>(h2, solution, s);
							Astar.setSolution(solution);
							System.err.println(solution.toString());
							solutionMap.put(stringtoMaze3d.get(name), (Maze3DSolution)Astar.Search(s));
							solveMazeByAstar(name);
						}
							return null;
					}
				};c.submit(mazeSolver);
			}
		}
		
	}
	
	@Override
	public void solveMazeUserOnepoint(final String name) {
		{
			if(stringtoMaze3d.containsKey(name))
			{
				if(solutionMap.containsKey(stringtoMaze3d.get(name)))
				{
					this.modelCompletedCommand = 12;
					Object[] dataSet = new Object[2];
					dataSet[0] = name;
					dataSet[1] = solutionMap.get(stringtoMaze3d.get(name));
					setChanged();
					this.setData(dataSet);
					notifyObservers();
				}
				else{errorNoticeToController("this maze didnt solve yet");}
			}
			else{errorNoticeToController("this maze didnt solve yet");}
		}
		/*
		int X = new Integer(x);
		int Y = new Integer(y);
		int Z = new Integer(z);
		Maze3d maze = stringtoMaze3d.get(name);
		Maze3dPosition mazeStartPosition = new Maze3dPosition(X, Y, Z);
		maze.setStartPosition(mazeStartPosition);
		stringtoMaze3d.put((name+""+x+""+y+""+z), maze);
		if(solutionMap.containsKey(stringtoMaze3d.get(name+""+x+""+y+""+z))){
		Solution<Position> Solutionmap = solutionMap.get(stringtoMaze3d.get(name+""+x+""+y+""+z));
		
		for(State<Position> aa : Solutionmap.getSolution())
		{
			System.out.println(aa.getActionName().toString());
		}
		}
		if(stringtoMaze3d.containsKey(name+""+x+""+y+""+z))
		{
			if(solutionMap.containsKey(stringtoMaze3d.get(name+""+x+""+y+""+z)))
			{
				System.out.println("alrdy solved this maze, wont do it again..");
				this.modelCompletedCommand=12;
				this.setData(name);
				System.out.println(stringtoMaze3d.get(name+""+x+""+y+""+z).getGoalPosition().toString());
				System.out.println("GOAL POSITION#$%#$%$#%$#%: "+ solutionMap.get(stringtoMaze3d.get(name+""+x+""+y+""+z)).toString());
				setChanged();
				notifyObservers();
			}
			else
			{
				Callable<Solution<Position>> mazeSolver =new Callable<Solution<Position>>() {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public Solution<Position> call() throws Exception 
					{
						Maze3d maze = stringtoMaze3d.get((name+""+x+""+y+""+z));
						Searchable<Position> s = new Maze3dSearch(maze);
						System.out.println("GOAL POSITION#$%#$%$#%$#%: "+ stringtoMaze3d.get(name+""+x+""+y+""+z).getGoalPosition().toString());
						Maze3DSolution solution = new Maze3DSolution();
						if(p.getDefSolver().equals("bfs"))
						{
							System.out.println("Solve with defualt bfs");
							BfsCommonSearcher Bfs = new BfsCommonSearcher(solution);
							Bfs.setSolution(solution);
							
							solutionMap.put(stringtoMaze3d.get(name), (Maze3DSolution)Bfs.Search(s));
							OneStatesolveMazeByBfs(name);
						}
						else if(p.getDefSolver().equals("astar"))
						{
							System.out.println("solve with default astar, Manhetthen distance");
							Manhattandistance h2 = new Manhattandistance();
							AstarCommonSearcher Astar = new AstarCommonSearcher<>(h2, solution, s);
							Astar.setSolution(solution);
							System.err.println(solution.toString());
							solutionMap.put(stringtoMaze3d.get(name), (Maze3DSolution)Astar.Search(s));
							OneStatesolveMazeByAstar(name);
						}
							return null;
					}
				};c.submit(mazeSolver);
			}
		}
		*/
	}
	
	private void OneStatesolveMazeByAstar(String name) {
		//Solution<Position> solution = solutionMap.get(stringtoMaze3d.get(name));
		this.modelCompletedCommand= 12;
		setChanged();
		setData(name);
		notifyObservers();
	}
	private void OneStatesolveMazeByBfs(String name) {
		//Solution<Position> solution = solutionMap.get(stringtoMaze3d.get(name));
		this.modelCompletedCommand= 12;
		setChanged();
		setData(name);
		notifyObservers();
	}
		
	}



	


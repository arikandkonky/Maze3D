package model;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
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
import presenter.ClientProperties;
import presenter.Properties;


/**
 * @author Nir Konky And Arik Bidny
 * @version 1.0
 * <h1>MyModel</h1>
 * This class MyModel implements Model and represent an Model in MVP design
 * Must Implements all the Model Func
 */
public class MyModel extends Observable implements Model{

	Object data;
	Properties p;
	ClientProperties cp;
	int modelCompletedCommand=0;
	ExecutorService c ;
	HashMap<String, Maze3d> stringtoMaze3d = new HashMap<String, Maze3d>();
	HashMap<Maze3d, Solution<Position>> solutionMap = new HashMap<Maze3d, Solution<Position>>();
	
	/**
	 * Constructor, initilize the Thread Pull to 30 
	 */	
	public MyModel(){
		this.c = Executors.newFixedThreadPool(30);
	}
	
	/**
	 * Constructor, do Super(), trying to read the Solutions from the solution file.
	 * @param p properties
	 * @param cp ClientProperties
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public MyModel(Properties p,ClientProperties cp) throws Exception
	{
		super();
		this.cp = cp;
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
	
	/**
	 * Constructor do super()
	 * @param p properties
	 * @throws Exception
	 */
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
	
	
	
	/**
	 * return to the MVP the files on the dir path
	 * @param dir gets dir path
	 * @throws NullPointerException
	 */
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

	/**
	 * generate the maze as instance of Maze3d and return it to the MVP
	 * @param name maze name
	 * @param generator generator name
	 * @param floor floor of the maze
	 * @param line line of the maze
	 * @param col col of the maze
	 */
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
						return maze.generate(new Integer(floor),new Integer(line),new Integer(col));	
				}
				else if(generator.equals("simplemaze3dgenerator"))
				{
					System.out.println("Heyy i get in!");
					maze = new SimpleMaze3dGenerator();
					errorNoticeToController("User: Generating maze with SimpleMaze3dGenerator with the given parameters");
						return maze.generate(new Integer(floor),new Integer(line),new Integer(col));
						
				}
				else if(p.getDefAlgorithm().equals("My3dGenerator"))
				{
					maze = new My3dGenerator();
					errorNoticeToController("Defualt: Generating maze with My3dGenerator with the given parameters");
						return maze.generate(new Integer(floor),new Integer(line),new Integer(col));
				}
				else if(p.getDefAlgorithm().equals("simplemaze3dgenerator"))
				{
					maze = new SimpleMaze3dGenerator();
					errorNoticeToController("Default: Generating maze with SimpleMaze3dGenerator with the given parameters");			
						return maze.generate(new Integer(floor),new Integer(line),new Integer(col));
					
				}
			return null;
			}
		});

			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
					Maze3d maze = futuremaze.get();
					stringtoMaze3d.put(name, maze);
					setChanged();
					modelCompletedCommand=2;
					setData(name);
					notifyObservers();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			});
		
			t.start();
	

	}
	/**
	 * return the instance maze as Maze3d to the MVP
	 * @param namev the maze name
	 */
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
	/**
	 * return the cross section to the MVP
	 * @param xyz cross section by x/y/z
	 * @param index cross section x on index(i)
	 * @param For Nothing(Stay on For) Dont change it!
	 * @param name maze name
	 */
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
	/**
	 * save the instance Maze3d to the filename with name: maze name
	 * @param name maze name
	 * @param filename file name
	 * @throws IOException 
	 */
	public void saveMazeToFile(String name, String filename) throws IOException{
		if(filename.isEmpty()||name.isEmpty())
		{
			errorNoticeToController("File Name Empty / Maze Name Empty");
		}
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
					else{errorNoticeToController("Cannot Create The File");}
				}
			}
			else
			{
				errorNoticeToController("The Maze Name Not Exists");
				throw new NullPointerException("There is no maze " +name);
			}
		}
	}
	/**
	 * load maze from file name exists as Maze3d instance.
	 * @param filename file name
	 * @param name maze name
	 * @throws IOException
	 */
	public void loadMazeToFile(String filename, String name) throws IOException{
		File f = new File(filename);
		if (f.exists()){
			@SuppressWarnings("resource")
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
			errorNoticeToController("File Not Found,Try Another Name");
			throw new FileNotFoundException("File Not Found");
			}
	}
	
	/**
	 * return the size as bytes of the maze3d instance to MVP
	 * @param name maze name
	 */
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
			errorNoticeToController("Maze Not Exists with name: "+ name);
	}
	
	/**
	 * return the size of the file name as bytes
	 * @param filename file name
	 */
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
	
	/**
	 * solve the (instance of) the maze name with algorithm
	 * @param name maze name
	 * @param algorithm algorithm name
	 */
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
							solveMazeByAstar(name);
						}
						else if(p.getDefSolver().equals("bfs"))
						{
							System.out.println("Solve with defualt bfs");
							BfsCommonSearcher Bfs = new BfsCommonSearcher(solution);
							Bfs.setSolution(solution);							
							solutionMap.put(stringtoMaze3d.get(name), (Maze3DSolution)Bfs.Search(s));
							solveMazeByAstar(name);
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
	
	/**
	 * return the solution to the MVP of the maze name
	 * @param name maze name
	 */
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
	
	/**
	 * change the XML file
	 * @param filename file name
	 * @throws FileNotFoundException
	 */
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
	/**
	 * private method 
	 * @param name maze name
	 */
	private void solveMazeByAstar(String name) {
		this.modelCompletedCommand= 9;
		setChanged();
		setData(name);
		notifyObservers();
	}
	/**
	 * return to the MVP the error
	 * @param s string error
	 */
	public void errorNoticeToController(String s)
	{
		modelCompletedCommand=-1;
		data = s;
		this.setChanged();
		notifyObservers();
	}
	
	

	/**
	 * exiting the program
	 */
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
	
	/**
	 * private method to the MyModel Class.
	 * @param filename file name
	 * @return Properties Object
	 * @throws Exception
	 */
	public static Properties read(String filename) throws Exception {
        XMLDecoder decoder =new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        Properties properties = (Properties)decoder.readObject();
        decoder.close();
        return properties;
    }

	/**
	 * solve the maze for the MVP (GUI)
	 * @param string maze name
	 * @param string2 x- floor of the maze
	 * @param string3 y - line of the maze
	 * @param string4 z - col of the maze
	 */
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
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
	

	/**
	 * return the solution to the MVP(GUI)
	 * @param string name of the maze name
	 */
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
	/**
	 * connect to the server and ask him to solve the maze, when finished, he return the solution to the MVP
	 * @param string maze name
	 * @param string2 x - floor of the maze
	 * @param string3 y - line of the maze
	 * @param string4 z - col of the maze
	 * @param Port server port
	 * @param HostName server host name
	 * @param algorithm solve algorithm
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Override
	public void remoteSolveModel(String name, String x, String y, String z,String port,String hostName,String algorithm) throws IOException, ClassNotFoundException {
		
		int X = new Integer(x);
		int Y = new Integer(y);
		int Z = new Integer(z);
		final String mazenewStart = name+x+y+z;
		Maze3dPosition mazeStartPosition = new Maze3dPosition(X, Y, Z);
		final Maze3d maze = stringtoMaze3d.get(name);
		maze.setMazeName(mazenewStart);
		maze.setStartPosition(mazeStartPosition);
		stringtoMaze3d.put(mazenewStart, maze);
		Integer intPort = new Integer(port);
		InetSocketAddress local = new InetSocketAddress(hostName, intPort);
		InetAddress localaddr = InetAddress.getLocalHost();
		System.out.println(localaddr.getHostAddress().toString());
		Socket myServer = new Socket(local.getAddress(),intPort);
		ObjectOutputStream output=new ObjectOutputStream(myServer.getOutputStream());
		ObjectInputStream input=new ObjectInputStream(myServer.getInputStream());
		@SuppressWarnings("unused")
		String stringToServer = "get solve";
		ArrayList<Object> obj = new ArrayList<Object>();
		obj.add("get solve");
		obj.add(maze);
		obj.add(algorithm);
		output.writeObject(obj);
		output.flush();
		output.writeObject(maze);
		output.flush();
		String def = p.getDefSolver();
		output.writeObject(def);
		output.flush();
		@SuppressWarnings("unchecked")
		Solution<Position> a = (Solution<Position>) input.readObject();
		solutionMap.put(maze, a);
		myServer.close();
		this.modelCompletedCommand=9;
		this.setData(mazenewStart);
		setChanged();
		notifyObservers();
		
	}
	
	
	//*************************Getters and Setters Methonds***********************************//
	/**
	 * return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * initlize the data
	 * @param data 
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * Get the Model Command for the MVP
	 * @return
	 */
	public int getModelCompletedCommand() {
		return modelCompletedCommand;
	}
	/**
	 * intilize the modelCompletedCommand
	 * @param modelCompletedCommand
	 */
	public void setModelCompletedCommand(int modelCompletedCommand) {
		this.modelCompletedCommand = modelCompletedCommand;
	}


}



	


package Tests;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.My3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AstarCommonSearcher;
import algorithms.search.Manhattandistance;
import algorithms.search.Maze3DSolution;
import algorithms.search.Maze3dSearch;
import algorithms.search.Searchable;
import algorithms.search.Solution;
public class AstarTest {

	/**
	 * @throws java.lang.Exception
	 */
	public static void setUpBeforeClass() throws Exception {
		Maze3DSolution solution;
		Manhattandistance h2;
		My3dGenerator mg;
		
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}
	public void test() {
		System.out.println("Test");
		Maze3DSolution solution = new Maze3DSolution();
		Manhattandistance h2 = new Manhattandistance();
		My3dGenerator mg  = new My3dGenerator();
		Maze3d oneOnOne = mg.generate(1, 1, 1);
		Maze3d oneOnTwo = mg.generate(1, 1, 2);
		Maze3d emptyMaze = new Maze3d(2,2, 2);
		Maze3d zeroMaze = new Maze3d(0,0,0);
		
		
		//oneOnone Test:
		Searchable<Position> s = new Maze3dSearch(oneOnOne);
		AstarCommonSearcher Astar = new AstarCommonSearcher<>(h2, solution, s);
		Astar.setSolution(solution);
		assert(oneOnOne.getStartPosition() == (Position)Astar.Search(s));
		
		//OneOnTwo Test:
		Searchable<Position> s1 = new Maze3dSearch(oneOnTwo);
		AstarCommonSearcher Astar1 = new AstarCommonSearcher<>(h2, solution, s1);
		Astar1.setSolution(solution);
		assert( null != (Solution<Position>)Astar1.Search(s));
		
		
		//OneOnTwo Test:
		Searchable<Position> s2 = new Maze3dSearch(zeroMaze);
		AstarCommonSearcher Astar2 = new AstarCommonSearcher<>(h2, solution, s2);
		Astar2.setSolution(solution);
		assert( null == (Solution<Position>)Astar2.Search(s));
		
		
		
		
		
		
		
		
		
		Maze3dSearch realMazeToCheck = new Maze3dSearch(mg.generate(3, 3, 3));
		
		assert(1==0);
		//assertTrue("aaa",1==0);
		
	//	fail("Not yet implemented");
	}
}

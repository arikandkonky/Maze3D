package presenter;

import java.io.Serializable;

@SuppressWarnings("serial")
/**
 * @author Nir Konky And Arik Bidny
 * @version 1.0
 * <h1>Properties</h1>
 * This class Properties implements Serializable
 * Must Implements all the Serializable function
 */
public class Properties implements Serializable {
	
	protected int numofThreads;
	protected String defAlgorithm;
	protected String defSolver;
	protected String UI;
	
	/**
	 * empty constructor
	 */
	public Properties(){}
	
	/**
	 * Constructor, initialize the parameters
	 * @param numofThreads int 
	 * @param Algorithm algorithm
	 * @param solver algorithm
	 * @param ui ui
	 */
	public Properties(int numofThreads,String Algorithm, String solver, String ui)
	{
		this.numofThreads= numofThreads;
		this.defAlgorithm = Algorithm;
		this.defSolver = solver;
		this.UI = ui;
	}

	/**
	 * get num of threads
	 * @return numofThreads int
	 */
	public int getNumofThreads() {
		return numofThreads;
	}

	/**
	 * set num of threads
	 * @param numofThreads int
	 */
	public void setNumofThreads(int numofThreads) {
		this.numofThreads = numofThreads;
	}

	/**
	 * get default algorithm
	 * @return defAlgorithm string
	 */
	public String getDefAlgorithm() {
		return defAlgorithm;
	}

	/**
	 * set default algorithm
	 * @param defAlgorithm string
	 */
	public void setDefAlgorithm(String defAlgorithm) {
		this.defAlgorithm = defAlgorithm;
	}

	/**
	 * get default solve algorithm
	 * @return defSolver string
	 */
	public String getDefSolver() {
		return defSolver;
	}

	/**
	 * set default solve algorithm
	 * @param defSolver string
	 */
	public void setDefSolver(String defSolver) {
		this.defSolver = defSolver;
	}

	/**
	 * get UI Command or Gui
	 * @return UI string
	 */
	public String getUI() {
		return UI;
	}

	/**
	 * Set UI Command or Gui
	 * @param uI String
	 */
	public void setUI(String uI) {
		UI = uI;
	}
	
	/**
	 * equals to obj
	 */
	public boolean equals(Object obj)
	{
		return this.toString().equals(obj.toString());
	}
	
	/**
	 * to string properties file
	 * @return String
	 */
	public String toString(){
		return "Properties: \n numThreads: "+numofThreads+" default Algorithm: "+ defAlgorithm +" defualt solver: "+ defSolver + " default UI: "+ UI;
	}
	
}

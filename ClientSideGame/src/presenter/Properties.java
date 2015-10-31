package presenter;

import java.io.Serializable;

/**
 * @author Nir Konky And Arik Bidny
 * @version 1.0
 * <h1>Properties</h1>
 * This class Properties implements Serializable
 * Must Implements all the Serializable function
 */
@SuppressWarnings("serial")
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
	 * @param numofThreads Number of the threads 
	 * @param Algorithm algorithm name
	 * @param solver solve algorithm name
	 * @param ui ui={GUI/CLI}
	 */
	public Properties(int numofThreads,String Algorithm, String solver, String ui)
	{
		this.numofThreads= numofThreads;
		this.defAlgorithm = Algorithm;
		this.defSolver = solver;
		this.UI = ui;
	}

	
	/**
	 * 
	 * @return the Number of the threads
	 */
	public int getNumofThreads() {return numofThreads;}

	/**
	 * sets the number of the threads
	 * @param numofThreads int
	 */
	public void setNumofThreads(int numofThreads) {this.numofThreads = numofThreads;}

	/**
	 * @return the algorithm(Genenrate algorithm)
	 */
	public String getDefAlgorithm() {return defAlgorithm;}

	/**
	 * sets the algorithm(Generate algorithm)
	 * @param defAlgorithm string
	 */
	public void setDefAlgorithm(String defAlgorithm) {this.defAlgorithm = defAlgorithm;}

	/**
	 * 
	 * @return the solver algorithm
	 */
	public String getDefSolver() {return defSolver;}

	/**
	 * set the solver algorithm
	 * @param defSolver string
	 */
	public void setDefSolver(String defSolver) {this.defSolver = defSolver;}

	/**
	 * @return the UI instance
	 */
	public String getUI() {return UI;}

	/**
	 * sets the UI instance
	 * @param uI string
	 */
	public void setUI(String uI) {UI = uI;}
	
	/**
	 * equals function
	 * @return true/false boolean
	 */
	public boolean equals(Object obj)
	{
		return this.toString().equals(obj.toString());
	}
	
	/**
	 * to string function 
	 */
	public String toString(){
		return "Properties: \n numThreads: "+numofThreads+" default Algorithm: "+ defAlgorithm +" defualt solver: "+ defSolver + " default UI: "+ UI;
	}
	
}

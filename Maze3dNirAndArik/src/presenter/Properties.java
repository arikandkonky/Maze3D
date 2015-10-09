package presenter;

import java.io.Serializable;

public class Properties implements Serializable {
	
	protected int numofThreads;
	protected String defAlgorithm;
	protected String defSolver;
	protected String UI;
	public Properties(){}
	
	public Properties(int numofThreads,String Algorithm, String solver, String ui)
	{
		this.numofThreads= numofThreads;
		this.defAlgorithm = Algorithm;
		this.defSolver = solver;
		this.UI = ui;
	}

	public int getNumofThreads() {
		return numofThreads;
	}

	public void setNumofThreads(int numofThreads) {
		this.numofThreads = numofThreads;
	}

	public String getDefAlgorithm() {
		return defAlgorithm;
	}

	public void setDefAlgorithm(String defAlgorithm) {
		this.defAlgorithm = defAlgorithm;
	}

	public String getDefSolver() {
		return defSolver;
	}

	public void setDefSolver(String defSolver) {
		this.defSolver = defSolver;
	}

	public String getUI() {
		return UI;
	}

	public void setUI(String uI) {
		UI = uI;
	}
	
	
	public boolean equals(Object obj)
	{
		return this.toString().equals(obj.toString());
	}
	
	public String toString(){
		return "Properties: numThreads= "+numofThreads+" default Algorithm: "+ defAlgorithm +" defualt solver: "+ defSolver + "default UI: "+ UI;
	}
	
}

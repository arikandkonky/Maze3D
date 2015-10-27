package presenter; 
 
 
 import model.Model; 
 
 
public abstract class AbsCommand implements Command { 
 	 
 	 
protected Model model; 
 
 	 
 	 
 	 
public AbsCommand(Model model) { 
	super(); 
 	this.model = model; 
	} 
 
 
 
 
 
 
 	@Override 
 	public abstract void doCommand(String[] args); 
 
 
 } 


package presenter; 
 
 
 import model.Model; 
 
 /**
  * @author Nir Konky And Arik Bidny
  * @version 1.0
  * <h1>AbsCommand</h1>
  * This Abstract class AbsCommand implements Command
  * Must Implements all the Command Function
  */
public abstract class AbsCommand implements Command { 
 	 	 
	protected Model model; 
  
	 
	/**
 	* Constructor
 	* @param model Model model
 	*/
	public AbsCommand(Model model) { 
	super(); 
 	this.model = model; 
	} 
 
 	@Override 
 	/**
 	 * abstract method
 	 */
 	public abstract void doCommand(String[] args); 
 
 
 } 



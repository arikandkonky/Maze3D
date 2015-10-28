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
Controller controller; 
 	 
 	 
 /**
  * Constructor
  * @param model Model model
  * @param controller Controller controller
  */
public AbsCommand(Model model, Controller controller) { 
	super(); 
 	this.model = model; 
    this.controller = controller; 
	} 
 
 
 
 
 
 	/**
 	 * abstract method.
 	 */
 	@Override 
 	public abstract void doCommand(String[] args); 
 
 
 } 


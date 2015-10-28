package presenter;

/**
 * @author Nir Konky And Arik Bidny
 * @version 1.0
 * <h1>Command</h1>
 * This Interface class AbsCommand implements Command
 * All Classes who implements him must use is functions.
 */
public interface Command { 
	
/**
 * do command of the arguments []
 * @param args
 */
void doCommand(String[] args); 
} 


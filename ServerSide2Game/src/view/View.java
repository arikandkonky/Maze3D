package view;


/**
 * @author Nir Konky and arik bidny
 * @version 1.0
 * <h1> View </h1>
 * This interface View represent an view that will talk with the client.
 * all implements classes will have to implement 
 */
public interface View {

	/**
	 * set the user command
	 * @param i int
	 */
	void setUserCommand(int i);
	
	/**
	 * return the error string to the Out instance
	 * @param String string
	 */
	void errorNoticeToUser(String string);
	
	/**
	 * 
	 * @return the usercommand
	 */
	int getUserCommand();
	
	/**
	 * start the server in the GUI
	 * @param data String
	 * @param dataSet String
	 */
	void serverStarted(String data, String dataSet);

	/**
	 * stop the server in the gui
	 */
	void serverStop(); 

	/**
	 * print to out the number of the clients online
	 * @param clients int
	 */
	void NumOfClients(int clients);








}

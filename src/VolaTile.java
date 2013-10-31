import java.io.IOException;

/**    
 * The Main Class for VolTile.
 * This is the first class to be called 
 * when executing the VolTile programm.
 * 
 * @author	Chirag Barot
 * @version	1.0
 */
public class VolaTile{

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException{
		// TODO Auto-generated method stub	

		/**
		 * Calling the Configuration class and also add 
		 * profile method and select file method.
		 */       
		Configuration config=new Configuration();
		config.addProfiles();
		config.selectFile();

	}


}


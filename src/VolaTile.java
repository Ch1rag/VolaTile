/**    
 * A concise description of the class (including invariants if any)
 *    
 * @
 * PUBLIC FEATURES:
 * // Constructors
 *    each constructor should be listed here
 * // Methods
 *    The signature and a brief comment (if needed)
 *    In alphabetic order
 *
 * COLLABORATORS:
 *    Names of classes (other than System and java.lang)
 *
 * MODIFIED:
 * @version number, date last changed and author�s initials &/or what changed (very brief)
 * @author    (can have multiple authors)
 */
 
import java.io.IOException;

public class VolaTile{

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	
	
	public static void main(String[] args) throws IOException, InterruptedException{
		// TODO Auto-generated method stub	
	
		//find OS
		String os=null;
		String user=null;
		System.getProperties().list(System.out);
		
		Configuration config=new Configuration();
		config.addOs();
		config.selectFile();
		//ImageInfo info=new ImageInfo();
		//info.readFile();
		
	
		
	
		/*ThreadExecutor te=new ThreadExecutor();		
		te.executor();*/
		//new Thread (new ProcessBuilderClass("pslist")).start();
		/*GUI frame=new GUI(dumpFile);
		frame.storeOS(os);
		frame.storeCmd_Mac();
		frame.storeCmd_Win();
		frame.makeFrame();*/
		
		
	}
		
		
	}


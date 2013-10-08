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
		os=System.getProperties().getProperty("os.name");
		//System.out.println(os);
		ThreadExecutor te=new ThreadExecutor();
		te.executor();
		
		GUI frame=new GUI();
		frame.storeOS(os);
		frame.storeCmd_Mac();
		frame.storeCmd_Win();
		frame.makeFrame();
		
		
		
		
		
	}

}
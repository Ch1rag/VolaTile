import java.io.IOException;

public class VolaTile{

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws IOException, InterruptedException{
		// TODO Auto-generated method stub	
	 
		// Call threader
		ThreadExecutor te=new ThreadExecutor();
		te.executor();
		
		GUI frame=new GUI();
		
		frame.storeOs();
		frame.storeCmd_Mac();
		frame.storeCmd_Win();
		frame.makeFrame();
		
		
	}

}
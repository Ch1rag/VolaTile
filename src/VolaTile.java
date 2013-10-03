import java.io.IOException;

public class VolaTile{

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub	
		processBuilder pb=new processBuilder();
	 
		GUI frame=new GUI();
		
		frame.storeOs();
		frame.storeCmd_Mac();
		frame.storeCmd_Win();
		frame.makeFrame();		
		
	}

}
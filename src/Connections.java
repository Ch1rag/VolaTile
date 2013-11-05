import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**    
 * This class is used to deal with the connections tab of the GUI,
 * choosing a particular PID from the pslist will show 
 * whether there are connections available of that particular PID
 * 
 * @author	Chirag Barot
 * @version	1.0
 */
public class Connections implements Callable<Object> {

	private List<String> list = new ArrayList<String>();
	private String PID;
	private FileReader fr = null;
	private ArrayList<String> connections = new ArrayList<String>();
	private String command="connections";
	private String profile;
	private String dumpFile;
	private String vol;
	private String volPath;
	
	

	
	// Overloaded constructor
	Connections(String pid) {
		PID = pid;
	}

	/**
     * The overloaded constructor for Connections Class.
     * This is the constructor that used to display the information 
	 * on the connections of the PID selected
	 *
	 * @param	dumpFile	Memory dump file
	 * @param	profile		Profile Name of the Memory Dump
	 * @param	vol			vol.py
	 * @param	volPath		path of the vol.py file
     */	
	public Connections(String dumpFile,String profile,String vol,String volPath){
		this.profile=profile;
		this.dumpFile=dumpFile;
		this.vol=vol;
		this.volPath=volPath;
	}

 	/**
	 * The Future Method of Connections class is defined here,
	 * this is useful when using multi threading. 
	 * <p>
	 * When the user clicks "Open Volatile" four processes runs
	 * consecutively including connections process
	 */     
	public Future<?> call() {
		
		
		try {
			list.add("python");
			list.add(vol);
			list.add("--profile="+profile);
			list.add("-f");
			list.add(dumpFile);
			list.add("--output-file="+command+".txt");
			list.add(command);

			ProcessBuilder process = new ProcessBuilder(list);
			//System.out.println("Executing Process Two(Connections)");

			Process p2 = process.start();

			// get the working directory for volatility folder..using
			// .directory..
			process.directory(new File(volPath));
			
			//System.out.println("DIR =>" + process.directory());
			//System.out.println("List of commands" + process.command());

			p2 = process.start();

			InputStream is = p2.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			String line1;

			while ((line1 = br.readLine()) != null) {
				System.out.println(line1);
			}
			br.close();
			p2.destroy();
			System.out.println("Process two is completed!");

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Reading information from the buffer reader and 
	 * pass them into connections.txt file where vol.py file exists
	 * @throws IOException 
	 * 
	 * @Exception	File not found
	 */
	public ArrayList<String> readFile(String volPath) throws IOException {
		BufferedReader br = null;
		try {
			String s=File.separator;
			fr = new FileReader(volPath+s+command+".txt");
			br = new BufferedReader(fr);

			String line;
			int i = 0;
			do {
				line = br.readLine();
				
				connections.add(" " + line + "\n");
				i++;
			} while (i != 2);
		
			while ((line = br.readLine()) != null) {
				if (line.matches(".*\\b" + PID + "\\b.*") == true) {
					
					connections.add(line + "\n");
				}
			}
			
		}  catch (Exception e) {
			System.err.println("File not found" + e.getMessage());
			
		}
		finally {
			br.close();
		}
		return connections;
	}

	@Override
	public String toString() {

		return "" + connections;

	}

}

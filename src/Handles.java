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
 * This class is used to deal with the handles tab of the GUI,
 * choosing a particular PID from the pslist will show 
 * whether there are handles available of that particular PID
 * 
 * @author	Chirag Barot
 * @version	1.0
 */
public class Handles implements Callable<Object> {

	private List<String> list = new ArrayList<String>();
	private String PID;
	private FileReader fr = null;
	private ArrayList<String> handles = new ArrayList<String>();
	private String command="handles";
	private String profile;
	private String dumpFile;
	private String vol;
	private String volPath;

	

	// Overloaded constructor
	Handles(String pid) {
		PID = pid;
	}
	
	/**
     * The overloaded constructor for Handles Class.
     * This is the constructor that used to display the information 
	 * on the handles of the PID selected
	 * <p>
	 *
	 * @param	dumpFile	Memory dump file
	 * @param	profile		Profile Name of the Memory Dump
	 * @param	vol			vol.py
	 * @param	volPath		path of the vol.py file
     */	
	public Handles(String dumpFile,String profile,String vol,String volPath){
		this.profile=profile;
		this.dumpFile=dumpFile;	
		this.vol=vol;
		this.volPath=volPath;
	}

	/**
	 * Get Process Id method
	 */	
	public String getPID() {
		return PID;
	}

	/**
	 * The Future Method of Handles class is defined here,
	 * this is useful when using multi threading. 
	 * <p>
	 * When the user clicks "Open Volatile" five processes runs
	 * consecutively including handles process
	 */	
	public Future<?> call() {
		// process five
		try {
			list.add("python");
			list.add(vol);
			list.add("--profile="+profile);
			list.add("-f");
			list.add(dumpFile);
			list.add("--output-file="+command+".txt");
			list.add(command);

			ProcessBuilder process = new ProcessBuilder(list);
			//System.out.println("Executing Process Five(Handles)");

			Process p2 =process.start();

			// get the working directory for volatility folder..using
			// .directory..
			process.directory(new File(volPath));

			// System.out.println("DIR=>" + process.directory());

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
			System.out.println("Process Five is completed!");
			
			

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Reading information from the buffer reader and 
	 * pass them into handles.txt file where vol.py file exists
	 * 
	 * @Exception	File not found
	 */
	public ArrayList<String> readFile(String volPath) throws IOException {
		BufferedReader br = null;
		try {
			String s=File.separator;
			fr = new FileReader(volPath+s+command+".txt");
			br = new BufferedReader(fr);
            int i=0;
			String line;
			do{
				line = br.readLine();
				//System.out.println(line);
		        handles.add(" "+line+"\n");
				i++;
			}while(i!=2);
	
			while ((line = br.readLine()) != null) {
				//Matcher m = p.matcher(line);
				if (line.matches(".*\\b" + PID + "\\b.*") == true) {
						//System.out.println(line);
						handles.add(line+"\n");
					}
			}
		} catch (Exception e) {
			System.err.println("File not found!!" + e.getMessage());
			
		} finally {
			br.close();
		}
		return handles;
	}

	@Override
	public String toString() {
		
		
		return ""+handles;

	}
}

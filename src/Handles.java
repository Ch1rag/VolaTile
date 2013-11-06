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
 * @
 * PUBLIC FEATURES:
 * // Constructors
 *    each constructor should be listed here
 * Handles(String pid)
 * Handles(String dumpFile,String profile,String vol,String volPath)
 * // Methods
 *    The signature and a brief comment (if needed)
 *    In alphabetic order
 * String getPID()
 * Future<?> call()
 * ArrayList<String> readFile(String volPath)
 * 
 * @author	Chirag Barot
 * @version	1.0
 *
 * 20131107 Updated comments - AN.
 * 20131106 Added Method Comments - Sri
 * 20131106 Original code - CB 
 */
public class Handles implements Callable<Object> {

	private List<String> list = new ArrayList<String>();	// argument list for process builder.
	private String PID;					// which process ID are we interested in.
	private FileReader fr = null;		// used to read results from file.
	private ArrayList<String> handles = new ArrayList<String>();
	private String command="handles";	// volatility command used to find "handles" in process.
	private String profile;				// which operating system profile to be used.
	private String dumpFile;			// the memory image 
	private String vol;					// name of volatility program. script or exe.
	private String volPath;				// folder to find volatility.

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
	 * @return process ID being used.
	 */	
	public String getPID() {
		return PID;
	}

	/**
	 * The Future Method of Handles class is defined here,
	 * this is useful when using multi threading. 
	 * <p>
	 * Calls volatility to find all Handles currently being used by process identified in PID.f
	 * 
	 */	
	public Future<?> call() {
		// Handles process five
		try {
			// create argument list of process builder.
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
			System.out.println("Handles process (Five) has completed!");
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Reading information from the buffer reader and 
	 * pass them into handles.txt file where vol.py file exists
	 * 
	 * @param volPath Folder to find the output file in.
	 * @return handles being used by process in an arraylist
	 * @throws IOException if problem reading from file.
	 */
	public ArrayList<String> readFile(String volPath) throws IOException {
		BufferedReader br = null;
		try {
			String s=File.separator;
			fr = new FileReader(volPath+s+command+".txt");
			br = new BufferedReader(fr);
            int i=0;
			String line;
			
			// add each handle matching our process id to results.
			do
			{
				line = br.readLine();
				//System.out.println(line);
		        handles.add(" "+line+"\n");
				i++;
			}while(i!=2);	// only include first 2 headers.
	
			while ((line = br.readLine()) != null) {
				//Matcher m = p.matcher(line);
				if (line.matches(".*\\b" + PID + "\\b.*") == true) {
						//System.out.println(line);
						handles.add(line+"\n");		// add handle to result collection.
					}
			}
		} catch (Exception e) {
			System.err.println("File not found!!" + e.getMessage());
			
		} finally {
			br.close();
		}
		return handles;	// return collection of handles used by our process ID.
	}

	/**
	 * toString
	 * <p>
	 * return the arraylist of handles that were found in the process.
	 * 
	 * @return Handles used by the process.
	 */
	@Override
	public String toString() {
		return ""+handles;
	}
}

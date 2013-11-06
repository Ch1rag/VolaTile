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
 * This class is used to deal with the threads tab of the GUI,
 * choosing a particular PID from the pslist will show 
 * whether there are threads available of that particular PID
 * @
 * PUBLIC FEATURES:
 * // Constructors
 * Threads(String pid)
 * Threads(String dumpFile,String profile,String vol,String volPath)
 * // Methods
 * Future<?> call()
 * String getPID()
 * ArrayList<String> readFile(String volPath)
 * String toString()
 *
 * @author	Chirag Barot
 * @version	1.0
 *
 * 20131107 Updated comments - AN.
 * 20131106 Added Method Comments - Sri
 * 20131106 Original code - CB 
 */
public class Threads implements Callable<Object> {

	private List<String> list = new ArrayList<String>();	// list of arguments used by process builder
	private String PID;		// process ID that we are interested in.
	private FileReader fr = null;	// used to read results file 
	private ArrayList<String> threads = new ArrayList<String>();	// collection of matching threads
	private String command="threads";	// command issued to volatility.
	private String profile;				// operating system profile to be used.
	private String dumpFile;			// memory image we are using.
	private String vol;					// name of volatility command (.py or .exe)
	private String volPath;				// folder to find volatility in.

	// Overloaded constructor
	Threads(String pid) {
		PID = pid;
	}

	/**
     * The overloaded constructor for Threads Class.
     * This is the constructor that used to display the information 
	 * on the threads of the PID selected
	 *
	 * @param	dumpFile	Memory dump file
	 * @param	profile		Profile Name of the Memory Dump
	 * @param	vol			vol.py
	 * @param	volPath		path of the vol.py file
     */
	public Threads(String dumpFile,String profile,String vol,String volPath){
		this.profile=profile;
		this.dumpFile=dumpFile;	
		this.vol=vol;
		this.volPath=volPath;
	}
	
	/**
	 * Get Process ID method of Threads class
	 * @return PID process ID that we are interested in.
	 */
	public String getPID() {
		return PID;
	}

	 /**
	 * The Future Method of Threads class is defined here,
	 * this is useful when using multi threading. 
	 * <p>
	 * When the user clicks "Open Volatile" five processes runs
	 * consecutively including threads process
	 */  
	public Future<?> call() {
		// Threads process four
		try {
			// create argument list for process builder.
			list.add("python");
			list.add(vol);
			list.add("--profile="+profile);
			list.add("-f");
			list.add(dumpFile);
			list.add("--output-file="+command+".txt");
			list.add(command);
			
			ProcessBuilder process = new ProcessBuilder(list);
			//System.out.println("Executing Process Four(Threads)");

			Process p2 = process.start();

			// get the working directory for volatility folder..using
			// .directory..
			process.directory(new File(volPath));

			// System.out.println("DIR=>" + process.directory());
			// System.out.println("List of commands" + process.command());

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
			System.out.println("Thread process (four) has completed.");

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Reading information from the buffer reader and 
	 * pass them into threads.txt file where vol.py file exists
	 * 
	 * @param volPath path to find results file in.
	 * @throws IOException if problem reading file.
	 */		
	public ArrayList<String> readFile(String volPath) throws IOException {
		BufferedReader br = null;
		try {
			String s=File.separator;
			fr = new FileReader(volPath+s+command+".txt");
			br = new BufferedReader(fr);
			String line;
			
			// add results to our thread collection.
			while ((line = br.readLine()) != null) {
				if (line.matches(".*\\b" + PID + "\\b.*") == true) {
					System.out.println(line);
					threads.add(line + "\n");
					while ((line = br.readLine()) != null
							&& (!line.contains("ETHREAD") == true)) {
						System.out.println(line);
						threads.add(line + "\n");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("File not found!! "+e.getMessage());
		} finally {
			br.close();
		}
		return threads;	// return matching thread entries.
	}

	/**
	 * Updated toString operator.
	 * @return threads being used by pid.
	 */
	@Override
	public String toString() {
		return "" + threads;
	}
}

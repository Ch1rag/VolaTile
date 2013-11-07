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

<<<<<<< HEAD
/**
 * This class is used to deal with the sockets tab of the GUI, choosing a
 * particular PID from the pslist will show whether there are sockets available
 * of that particular PID
 * 
 * @author Chirag Barot
 * @version 1.0
 */
public class Sockets implements Callable<Object> {

	private List<String> list = new ArrayList<String>();
	private String PID;
	private FileReader fr = null;
	private ArrayList<String> sockets = new ArrayList<String>();
	private String command = "sockets";
	private String profile;
	private String dumpFile;
	private String vol;
	private String volPath;
=======
/**    
 * This class is used to deal with the sockets tab of the GUI,
 * choosing a particular PID from the pslist will show 
 * whether there are sockets available of that particular PID
 * <p>
 * @
 * PUBLIC FEATURES:
 * // Constructors
 * Sockets(String pid)
 * Sockets(String dumpFile,String profile,String vol,String volPath)
 * <p>
 * // Methods
 * Future<?> call()
 * String getPID()
 * ArrayList<String> readFile(String volPath)
 * String toString()
 * <p>
 * @author Chirag Barot
 * @version	1.0
 * 20131107 Updated comments - AN.
 * 20131106 Added method headers - Sri
 * 20131106 Original code - CB 
 */
public class Sockets implements Callable<Object> {

	private List<String> list = new ArrayList<String>();	// argument list used by process builder.
	private String PID;			// process ID that we are interested in.
	private FileReader fr = null;	// used to read results file.
	private ArrayList<String> sockets = new ArrayList<String>();	// collection of sockets used by process.
	private String command="sockets";		// sockets command used by volatility.
	private String profile;		// operating system profile to be used.
	private String dumpFile;	// memory image
	private String vol;			// name of volatility commadn (vol.py or vol.exe)
	private String volPath;		// folder to find volatility in.
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185

	// Overloaded constructor
	Sockets(String pid) {
		PID = pid;
	}

	/**
<<<<<<< HEAD
	 * The overloaded constructor for Sockets Class. This is the constructor
	 * that used to display the information on the sockets of the PID selected
	 * 
	 * @param dumpFile
	 *            Memory dump file
	 * @param profile
	 *            Profile Name of the Memory Dump
	 * @param vol
	 *            vol.py
	 * @param volPath
	 *            path of the vol.py file
	 */
	public Sockets(String dumpFile, String profile, String vol, String volPath) {
		this.profile = profile;
		this.dumpFile = dumpFile;
		this.vol = vol;
		this.volPath = volPath;
=======
     * The overloaded constructor for Sockets Class.
     * This is the constructor that used to display the information 
	 * on the sockets of the PID selected
	 * <p>
	 * @param	dumpFile	Memory dump file
	 * @param	profile		Profile Name of the Memory Dump
	 * @param	vol			vol.py
	 * @param	volPath		path of the vol.py file
     */
	public Sockets(String dumpFile,String profile,String vol,String volPath){
		this.profile=profile;
		this.dumpFile=dumpFile;	
		this.vol=vol;
		this.volPath=volPath;
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
	}

	/**
	 * Get ProcessID method of Sockets Class
	 */
	public String getPID() {
		return PID;
	}

	/**
	 * The Future Method of Sockets class is defined here, this is useful when
	 * using multi threading.
	 * <p>
	 * When the user clicks "Open Volatile" five processes runs consecutively
	 * including sockets process
	 */
	public Future<?> call() {
		// Socket process three
		try {
			// create argument list for process builder.
			list.add("python");
			list.add(vol);
			list.add("--profile=" + profile);
			list.add("-f");
			list.add(dumpFile);
			list.add("--output-file=" + command + ".txt");
			list.add(command);

			ProcessBuilder process = new ProcessBuilder(list);
			// System.out.println("Executing Process Three(Sockets)");

			Process p2 = process.start();

			// get the working directory for volatility folder..using
			// .directory..
			process.directory(new File(volPath));

			// System.out.println("DIR=>" + process.directory());

			// System.out.println("List of commands" + process.command());

			/**
			 * Initializing Process two to be start
			 */
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
<<<<<<< HEAD
			System.out.println("Process three is completed!");

=======
			System.out.println("Sockets process (three) has completed.");
			
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
<<<<<<< HEAD
	 * Reading information from the buffer reader and pass them into sockets.txt
	 * file where vol.py file exists
	 * 
	 * @throws IOException
	 */
=======
	 * Reading information from the buffer reader and 
	 * pass them into sockets.txt file where vol.py file exists
	 * <p>
	 * @param volPath Folder containing output file.
	 * @return sockets used by pid in an ArrayList<String>
	 * @throws IOException if problem reading from file.
	 */	
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
	public ArrayList<String> readFile(String volPath) throws IOException {
		BufferedReader br = null;
		try {
			String s = File.separator;
			fr = new FileReader(volPath + s + command + ".txt");
			br = new BufferedReader(fr);

			// create our results list.
			// include first 2 headers in our list.
			String line;
			int i = 0;
			do {
				line = br.readLine();
				sockets.add(" " + line + "\n");
				i++;
<<<<<<< HEAD
			} while (i != 2);

			while ((line = br.readLine()) != null) {
				if (line.matches(".*\\b" + PID + "\\b.*") == true) {
					sockets.add(line + "\n");
				}
=======
			}while(i!=2);	// include first 2 header lines.
	
			// Find all lines that match the PID that we are interested in.
			while ((line = br.readLine()) != null) {
				if (line.matches(".*\\b" + PID + "\\b.*") == true) {
						sockets.add(line +"\n");	// add matching line to our collection.
					}
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("File not found!! "+e.getMessage());
		} finally {
			br.close();
		}
		return sockets;	// return the list of matching sockets 
	}

	/**
	 * Return list of sockets being used by process.
	 * <p>
	 * @return Collection of sockets used by process.	
	 */
	
	@Override
	public String toString() {
<<<<<<< HEAD

		return "" + sockets;

=======
		return ""+sockets;
>>>>>>> 5fa5305112beb35f0a4326348418ca0e5fde3185
	}
}

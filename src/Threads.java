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
 * @version number, date last changed and author’s initials &/or what changed (very brief)
 * @author    (can have multiple authors)
 * http://swinbrain.ict.swin.edu.au/wiki/Swinburne_Java_Coding_Standard
 */
 
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
import java.util.regex.Pattern;

public class Threads implements Callable<Object> {

	private List<String> list = new ArrayList<String>();
	private String PID;
	private FileReader fr = null;
	private ArrayList<String> threads = new ArrayList<String>();
	private String command="threads";
	private String path;
	private String profile;
	private String dumpFile;

	// Deafult Constructor
	/*Threads() {
	}*/

	// Overloaded constructor
	Threads(String pid) {
		PID = pid;
	}
	public Threads(String dumpFile,String profile, String path){
		this.profile=profile;
		this.dumpFile=dumpFile;
		this.path=path;	
	}

	public String getPID() {
		return PID;
	}

	public Future<?> call() {
		// process two..
		try {
			list.add("python");
			list.add("vol.py");
			list.add("--profile="+profile);
			list.add("-f");
			list.add(dumpFile);
			list.add("--output-file="+command+".txt");
			list.add(command);
			
			ProcessBuilder process = new ProcessBuilder(list);
			System.out.println("Executing Process Four(Threads)");

			Process p2 = process.start();

			// get the working directory for volatility folder..using
			// .directory..
			process.directory(new File(path));

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
			System.out.println("Process four is completed!");

			// Process two psxview

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> readFile() throws IOException {
		BufferedReader br = null;
		try {
			fr = new FileReader("/Users/chiragbarot/volatility/Threads.txt");
			br = new BufferedReader(fr);
			String line;

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
			System.out.println("File not found!!");
		} finally {
			br.close();
		}
		return threads;
	}

	@Override
	public String toString() {

		return "" + threads;

	}
}

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
import java.util.regex.Matcher;

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

	// Deafult Constructor
	/*Handles() {
	}*/

	// Overloaded constructor
	Handles(String pid) {
		PID = pid;
	}
	
	public Handles(String dumpFile,String profile,String vol,String volPath){
		this.profile=profile;
		this.dumpFile=dumpFile;	
		this.vol=vol;
		this.volPath=volPath;
	}

	public String getPID() {
		return PID;
	}

	public Future<?> call() {
		// process two..
		try {
			list.add("python");
			list.add(vol);
			list.add("--profile="+profile);
			list.add("-f");
			list.add(dumpFile);
			list.add("--output-file="+command+".txt");
			list.add(command);

			ProcessBuilder process = new ProcessBuilder(list);
			System.out.println("Executing Process Five(Handles)");

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
			
			//Process two psxview
			

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> readFile(String volPath) throws IOException {
		BufferedReader br = null;
		try {
			File file=null;
			String s=file.separator;
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
			System.err.println("File not found: " + e.getMessage());
			
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

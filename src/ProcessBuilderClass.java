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
 * @version number, date last changed and author�s initials &/or what changed (very brief)
 * @author    (can have multiple authors)
 * http://swinbrain.ict.swin.edu.au/wiki/Swinburne_Java_Coding_Standard
 */
 
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class ProcessBuilderClass implements Runnable{
	private List<String> list = new ArrayList<String>();
	private String command;
	private String dumpFile;
	private String profile;
	private String user;
	
	public ProcessBuilderClass(String command,String dumpFile,String profile, String user){
		this.profile=profile;
		this.dumpFile=dumpFile;
		this.command=command;
		this.user=user;
	}

	public void run() {

		try{
			list.add("python");
			list.add("vol.py");
			list.add("--profile="+profile);
			list.add("-f");
			list.add(dumpFile);
			list.add(command);
			list.add("--output-file="+command+".txt");
			
			ProcessBuilder process = new ProcessBuilder(list);
			
			System.out.println("Executing Process one(pslist)");

			Process p1=process.start();

			// get the working directory for volatility folder..using .directory..
			process.directory(new File(user+"/volatility"));

			System.out.println("DIR=>" + process.directory());
			System.out.println("List of commands" + process.command());
			p1=process.start();

			InputStream is = p1.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			String line;

			while ((line = br.readLine()) != null) {
				System.out.println(line.split("\\s+"));

			}
			br.close();
			p1.destroy();
			p1.waitFor();
			p1.getErrorStream();
			String done="Process one is completed!";
			System.out.println(done);
       
			// Clear command..
			list.clear();
			
		}

		catch(Throwable e){
			e.printStackTrace();
		}

	}



}




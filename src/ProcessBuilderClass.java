import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**    
 * This is the Process Builder Class.
 * This class is used to retrieve pslist 
 * information from the memory dump.
 * 
 * @author	Chirag Barot
 * @version	1.0
 */
public class ProcessBuilderClass implements Callable<Object>{
	private List<String> list = new ArrayList<String>();
	private String command;
	private String dumpFile;
	private String profile;
	private String vol;
	private String volPath;
	
	/**
     * The overloaded constructor for Process Builder Class.
     * This is the constructor that used to display the information 
	 * on the connections of the PID selected
	 *
	 * @param	dumpFile	Memory dump file
	 * @param	profile		Profile Name of the Memory Dump
	 * @param	vol			vol.py
	 * @param	volPath		path of the vol.py file
     */		
	public ProcessBuilderClass(String command,String dumpFile,String profile,String vol,String volPath){
		this.profile=profile;
		this.dumpFile=dumpFile;
		this.command=command;
		this.vol=vol;
		this.volPath=volPath;
		
	}

	public Future<?> call() {

		try{
			list.add("python");
			list.add(vol);
			list.add("--profile="+profile);
			list.add("-f");
			list.add(dumpFile);
			list.add(command);
			list.add("--output-file="+command+".txt");
			
			ProcessBuilder process = new ProcessBuilder(list);
			
			System.out.println("Executing Process one("+command+")");

			Process p1=process.start();

			// get the working directory for volatility folder..using .directory..
			process.directory(new File(volPath));

			//System.out.println("DIR=>" + process.directory());
			//System.out.println("List of commands" + process.command());
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
			list.clear();	
		}

		catch(Throwable e){
			System.out.println("File not found..."+e.getMessage());
		}
		return null;

	}



}




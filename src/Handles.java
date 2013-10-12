
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Handles implements Runnable {

	private List<String> list = new ArrayList<String>();
	private String PID;
	private FileReader fr = null;
	private ArrayList<String> handles = new ArrayList<String>();

	// Deafult Constructor
	Handles() {
	}

	// Overloaded constructor
	Handles(String pid) {
		PID = pid;
	}

	public String getPID() {
		return PID;
	}

	public void run() {
		// process two..
		try {
			list.add("python");
			list.add("vol.py");
			list.add("--profile=WinXPSP2x86");
			list.add("-f");
			list.add("ram_dump.vmem");
			list.add("--output-file=Handles.txt");
			list.add("handles");

			ProcessBuilder process = new ProcessBuilder(list);
			System.out.println("Executing Process Five(Handles)");

			Process p2 =process.start();

			// get the working directory for volatility folder..using
			// .directory..
			process.directory(new File("/Users/chiragbarot/volatility"));

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
	}

	public ArrayList<String> readFile() throws IOException {
		BufferedReader br = null;
		try {
			fr = new FileReader("/Users/chiragbarot/volatility/Handles.txt");
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

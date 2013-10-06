import java.io.*;
import java.util.*;


public class ProcessBuilderClass implements Runnable{
	private List<String> list = new ArrayList<String>();
	private List<String> clm = new ArrayList<String>();
	
	private File file = new File("/Users/chiragbarot/volatilityFinal/os.txt");


	public void List() {
		list.add("python");
		list.add("vol.py");
		list.add("--profile=Mac10_8_4_64bitx64");
		list.add("-f");
		list.add("ram_dump.mach-o");
		list.add("--output-file=os.txt");
	}

	public void editList(String s) {
		
		list.add(s);
	}
	

	public void run() {
		try{
			
		// Create and probuilder object
			
		ProcessBuilder process = new ProcessBuilder(list);
		System.out.println("Executing Task One");
		
		 System.out.println("List of commands" + process.command());
		Process p1=process.start();
		
		
		// get the working directory for volatility folder..using .directory..
		process.directory(new File("/Users/chiragbarot/volatilityFinal"));

		// System.out.println("DIR=>" + process.directory());

		
		String s = "Process is running..";
		System.out.println(s);
		p1=process.start();
		
		String text = "Please wait..";
		
		System.out.println(text);

		InputStream is = p1.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line;

		while ((line = br.readLine()) != null) {
			System.out.println(line.split("\\s+"));
		
		}
		br.close();
		p1.destroy();
		Thread.sleep(1000);
		System.out.println("Done!");
		p1.waitFor();
		p1.getErrorStream();
		System.out.println("Click 'Load' to check the output");
				
		// Clear command..
		list.clear();
		}
		
		catch(Throwable e){
			e.printStackTrace();
		}
		
		
		
	}
	@Override
	public String toString() {
		return "DoProcessBuilder [list=" + list + ", clm=" + clm + ", file="
				+ file + "]";
	}

	public void print(){
		System.out.println(toString());
	}
}

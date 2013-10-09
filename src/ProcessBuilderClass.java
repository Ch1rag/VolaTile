import java.io.*;
import java.util.*;


public class ProcessBuilderClass implements Runnable{
	private List<String> list = new ArrayList<String>();
	private List<String> clm = new ArrayList<String>();
	private String profile;
	private File file = new File("/Users/chiragbarot/volatility/output.txt");
	
	public ProcessBuilderClass(String profile){
		this.profile=profile;
	}


	public void run() {
		
		try{
			
			list.add("python");
			list.add("vol.py");
			list.add("--profile=WinXPSP2x86");
			list.add("-f");
			list.add("ram_dump.vmem");
			list.add("--output-file=output.txt");
			
		// Create and probuilder object
			list.add(profile);
		ProcessBuilder process = new ProcessBuilder(list);
		System.out.println("Executing Process one(pslist)");
		
		Process p1=process.start();
		
		// get the working directory for volatility folder..using .directory..
		process.directory(new File("/Users/chiragbarot/volatility"));

		// System.out.println("DIR=>" + process.directory());
	
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
		System.out.println("Process one is completed!");
				
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

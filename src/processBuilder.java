import java.io.*;
import java.util.*;
import java.util.concurrent.RunnableFuture;

public class processBuilder{
	private List<String> list = new ArrayList<String>();
	private List<String> clm = new ArrayList<String>();
	
	private File file = new File("/Users/chiragbarot/volatilityFinal/os.txt");


	public void List() {
		list.add("python");
		list.add("vol.py");
		list.add("--profile=Mac10_8_4_64bitx64");
		list.add("-f");
		list.add("/Users/chiragbarot/Mem/ram_dump.mach-o");
		list.add("--output-file=os.txt");
	}

	public void editList(String s) {
		
		list.add(s);
	}
	

	public void process() throws IOException, InterruptedException {

		// Create and probuilder object
		
		ProcessBuilder process = new ProcessBuilder(list);
		
		Process p1=process.start();
		
		
		// get the working directory for volatility folder..using .directory..
		process.directory(new File("/Users/chiragbarot/volatilityFinal"));

		// System.out.println("DIR=>" + process.directory());

		// System.out.println("List of commands" + process.command());
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
		System.out.println("Done!");
		p1.waitFor();
		p1.getErrorStream();
		System.out.println("Click 'Load' to check the output");
				
		// Clear command..
		list.clear();
		
		
		//process two..
		list.add("python");
		list.add("vol.py");
		list.add("--profile=Mac10_8_4_64bitx64");
		list.add("-f");
		list.add("/Users/chiragbarot/Mem/ram_dump.mach-o");
		list.add("--output-file=lsof.txt");
		list.add("mac_lsof");
		
		process = new ProcessBuilder(list);
		Process p2=process.start();
		
		// get the working directory for volatility folder..using .directory..
				process.directory(new File("/Users/chiragbarot/volatilityFinal"));

				// System.out.println("DIR=>" + process.directory());

				System.out.println("List of commands" + process.command());
				String s1 = "Process two is running..";
				System.out.println(s1);
				p2=process.start();
				
				String text1 = "Please wait....";
				System.out.println(text1);

				InputStream is1 = p2.getInputStream();
				InputStreamReader isr1 = new InputStreamReader(is1);
				BufferedReader br1 = new BufferedReader(isr1);

				String line1;

				while ((line1 = br1.readLine()) != null) {
					System.out.println(line1);
				
				}
				br1.close();
				p2.destroy();
				System.out.println("Process two completed..Done!");
				
				p2.getErrorStream();
				System.out.println("File written no errors..");
		
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

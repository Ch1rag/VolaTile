package kunday;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ThreadedLsofProcessBuilder extends ThreadedProcessBuilder {

	private List<String> list = new ArrayList<String>();
	
	public ThreadedLsofProcessBuilder(List<String> list2) {
		 this.list = list2;
	}

	@Override
	public Object call() throws Exception {

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
		// TODO Auto-generated method stub
		return null;
	}

}

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


public class ImageInfo {
	private List<String> list = new ArrayList<String>();

	public ImageInfo(){}
	public ImageInfo(String fileName) throws IOException, InterruptedException {
		if(fileName.contains("mach-o")){
			list.add("python");
			list.add("vol.py");
			list.add("--info");
			list.add("|");
			list.add("grep");
			list.add("Mac");
			//list.add("imageinfo");

			ProcessBuilder process = new ProcessBuilder(list);
			System.out.println("Executing Process (Mac image)");

			Process p1=process.start();

			// get the working directory for volatility folder..using .directory..
			process.directory(new File("/Users/chiragbarot/volatility"));

			//System.out.println("DIR=>" + process.directory());
			System.out.println("List of commands" + process.command());
			p1=process.start();

			InputStream is = p1.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;

			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			br.close();
			p1.destroy();
			p1.waitFor();
			p1.getErrorStream();
			String done="Process imageinfo is completed!";
			System.out.println(done);

			// Clear command..
			list.clear();
		}
		else{
			try{
				list.add("python");
				list.add("vol.py");
				list.add("-f");
				list.add(fileName);
				list.add("--output-file=imageinfo.txt");
				list.add("imageinfo");

				ProcessBuilder process = new ProcessBuilder(list);
				System.out.println("Executing Process (imageinfo)");

				Process p1=process.start();

				// get the working directory for volatility folder..using .directory..
				process.directory(new File("/Users/chiragbarot/volatility"));

				//System.out.println("DIR=>" + process.directory());
				System.out.println("List of commands" + process.command());
				p1=process.start();

				InputStream is = p1.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line;

				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
				br.close();
				p1.destroy();
				p1.waitFor();
				p1.getErrorStream();
				String done="Process imageinfo is completed!";
				System.out.println(done);

				// Clear command..
				list.clear();

			}

			catch(Exception e){
				System.out.println("File not found!"+e.getMessage());
			}


		}
	}

}

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**    
 * This class is used to retrieve the information of the profile.
 * Once a User clicks on Detect Profile information of the 
 * image (memory dump) will be displayed.
 * 
 * @
 * PUBLIC FEATURES:
 * // Constructors
 * ImageInfo()
 * ImageInfo(String fileName, String volPath, String vol)
			throws IOException, InterruptedException
 * // Methods
 *
 *
 * @author	Chirag Barot
 * @version	1.0
 */
public class ImageInfo {
	private List<String> list = new ArrayList<String>();

	/**
	 * Default constructor for ImageInfo class
	 */
	public ImageInfo() {
	}

	/**
	 * The overloaded constructor for ImageInfo class is defined here.
	 * 
	 * @param	fileName	Profile Name of the Memory Dump
	 * @param	path		path to vol.py
	 * @param	vol			vol.py
	 *
	 * @exception	IOException		File Not Found
	 */
	public ImageInfo(String fileName, String volPath, String vol)
			throws IOException, InterruptedException {
		
		/**
		 * The following IF condition states if imageinfo 
		 * contains mach-o, it will display the information 
		 * of the Mac OS.
		 */		
		if (fileName.contains("mach-o")) {
			list.add("python");
			list.add(vol);
			list.add("--info");
			list.add("|");
			list.add("grep");
			list.add("Mac");
			// list.add("imageinfo");

			ProcessBuilder process = new ProcessBuilder(list);
			System.out.println("Executing Process (Mac image)");

			Process p1 = process.start();

			/**
			 * Get the working directory for volatility folder..using .directory..
			 */	
			process.directory(new File(volPath));

			// System.out.println("DIR=>" + process.directory());
			System.out.println("List of commands" + process.command());
			p1 = process.start();

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
			String done = "Process imageinfo is completed!";
			System.out.println(done);

			// Clear command..
			list.clear();
		} 
		
		/**
		 * The following else condition states if imageinfo 
		 * contains anything else other than mach-o, it 
		 * will display the information 
		 * of the relevent image. 
		 */		
		else {
			try {
				list.add("python");
				list.add(vol);
				list.add("-f");
				list.add(fileName);
				list.add("--output-file=imageinfo.txt");
				list.add("imageinfo");

				ProcessBuilder process = new ProcessBuilder(list);
				System.out.println("Executing Process (imageinfo)");

				Process p1 = process.start();

				/**
				 * Get the working directory for volatility folder..using .directory..
				 */
				process.directory(new File(volPath));

				// System.out.println("DIR=>" + process.directory());
				System.out.println("List of commands" + process.command());
				p1 = process.start();

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
				String done = "Process imageinfo is completed!";
				System.out.println(done);

				// Clear command..
				list.clear();

			}

			catch (Exception e) {
				System.out.println("File not found!" + e.getMessage());
			}

		}
	}

}

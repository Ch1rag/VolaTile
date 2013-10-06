import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;

import java.util.List;

public class LsofThread implements Runnable {

	private List<String> list = new ArrayList<String>();
	private String PID;
	private FileReader fr = null;
	private ArrayList<String> connections = new ArrayList<String>();

	// Deafult Constructor
	LsofThread() {
	}

	// Overloaded constructor
	LsofThread(String pid) {
		PID = pid;
		System.out.println("PID is:" + PID);
	}

	public String getPID() {
		return PID;
	}

	public void run() {
		// process two..
		try {
			list.add("python");
			list.add("vol.py");
			list.add("--profile=Mac10_8_4_64bitx64");
			list.add("-f");
			list.add("ram_dump.mach-o");
			list.add("--output-file=lsof.txt");
			list.add("mac_lsof");

			ProcessBuilder process = new ProcessBuilder(list);
			System.out.println("Executing Task Two");

			Process p2 = process.start();

			// get the working directory for volatility folder..using
			// .directory..
			process.directory(new File("/Users/chiragbarot/volatilityFinal"));

			// System.out.println("DIR=>" + process.directory());

			System.out.println("List of commands" + process.command());
			String s1 = "Process two is running..";
			System.out.println(s1);
			p2 = process.start();

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
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> readFile() throws IOException {
		BufferedReader br = null;
		try {
			fr = new FileReader("/Users/chiragbarot/volatilityFinal/lsof.txt");
			br = new BufferedReader(fr);

			String line;
			//int new_pid=Integer.parseInt(PID);
			

			while ((line = br.readLine()) != null) {

				if (line.contains(PID) == true) {
					System.out.println(line);
					connections.add(line+"\n");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("File not found, Please wait..");
		} finally {
			br.close();
		}
		return connections;
	}

	@Override
	public String toString() {
		
		return "PID"+"\t" + "File Descriptor"+"\t" + "Path" + "\n"
				+ "-------------------------------------------------" + "\n"
				+ connections;

	}
}

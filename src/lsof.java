import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class lsof implements runnable {
	// private List<String> lsof = new ArrayList<String>();
	private String PID;
	private FileReader fr = null;
	private ArrayList<String> connections = new ArrayList<String>();

	// Deafult Constructor
	lsof() {
	}

	// Overloaded constructor
	lsof(String pid) {
		PID = pid;
		System.out.println("PID is:" + PID);
	}

	public String getPID() {
		return PID;
	}

	public ArrayList<String> readFile() throws IOException {
		System.out.println("PID is METHOD:" + PID);
		fr = new FileReader("/Users/chiragbarot/volatilityFinal/lsof.txt");
		BufferedReader br = new BufferedReader(fr);

		String line;

		while ((line = br.readLine()) != null) {

			if (line.matches(PID) == true) {
				System.out.println(line);
				connections.add(line);
			}

		}
		br.close();
		return connections;
	}

	public void readConnections() {
		Iterator<String> iterator = connections.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	/*
	 * lsof.add("lsof"); lsof.add("-i"); lsof.add("-p"); lsof.add(PID); //
	 * Create and probuilder object
	 * 
	 * 
	 * 
	 * ProcessBuilder process1 = new ProcessBuilder(lsof); String s =
	 * "Process is running..stage1"; System.out.println(s); Process
	 * p1=process1.start();
	 * 
	 * 
	 * // get the working directory for volatility folder..using .directory..
	 * //process.directory(new File("/Users/chiragbarot/volatilityFinal"));
	 * 
	 * // System.out.println("DIR=>" + process.directory());
	 * 
	 * System.out.println("List of commands" + process1.command());
	 * 
	 * System.out.println(s);
	 * 
	 * p1=process1.start();
	 * 
	 * String text = "Please wait..stage 2"; System.out.println(text);
	 * 
	 * InputStream is = p1.getInputStream(); InputStreamReader isr = new
	 * InputStreamReader(is); BufferedReader br = new BufferedReader(isr);
	 * 
	 * String line;
	 * 
	 * while ((line = br.readLine()) != null) { System.out.println(line);
	 * 
	 * } br.close(); p1.destroy(); System.out.println("Done!"); try {
	 * p1.waitFor(); } catch (InterruptedException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } p1.getErrorStream();
	 * System.out.println("Click 'Load' to check the output");
	 * 
	 * // Clear command.. lsof.clear();
	 */

	@Override
	public String toString() {

		return "Connections:" + connections;

	}

}
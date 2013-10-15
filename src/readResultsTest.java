import java.util.*;

class readResultsDemo
{
	
	
	public static void main(String args[])
	{
		String f;
		readResults r;
		
		f = "test.txt";
		r = new readResults(f);
		System.out.println("Read results says " + r);
		
		r = null;
		f = null;
		
		f = "test22.txt";
		r = new readResults(f);
		System.out.println("Read results says " + r);
	
	}

}
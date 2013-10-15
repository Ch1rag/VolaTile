import java.io.File;
import java.io.*;
import java.lang.*;

class readResults
{
	File f;
	// constructor
	public readResults(String s) // throws Exception
	{
		
		try 
		{
			f = new File(s);	// attempt to load file.
		}
		/*catch(FileNotFoundException ex)
		{
			System.out.println("Error: file not found... " + s);
		}*/
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		System.out.println("readResults says File f " + f.getAbsolutePath());
		
		if ( f.exists() )
		{
			System.out.println(f + " does exist");
		}
		else
		{
			System.out.println(f + " does not exist");
		}
	
		
		try
		{
			if(f.canRead()) 
			{
				System.out.println("Success, can read " + f);
			}
			else
			{
				System.out.println("Fail, unable to read " + f);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error, cant open "		);
			ex.printStackTrace();
		}
	
	}

}
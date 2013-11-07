import java.io.File;
import java.io.*;
import java.lang.*;

/** 
 * ReadResults
 * <p>
 * This class was developed to open files with better exception handling.
 * @
 * PUBLIC FEATURES:
 * // Constructors
 *    ReadResults(String s)
 * // Methods
 * none.
 *
 * @author Anthony Nowlan
 * @version 20131107
 */
class ReadResults
{
	File f;
	boolean readable = false;	// assume the worst.
	boolean exists = false;
	
	// constructor
	public ReadResults(String s) // throws Exception
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
		
		System.out.println("ReadResults says File f " + f.getAbsolutePath());
		
		// Check to make sure that the file exists.
		if ( f.exists() )
		{
			System.out.println(f + " does exist");
			exists = true;
		}
		else
		{
			System.out.println(f + " does not exist");
		}

		try
		{
			// Test if we can open the file.
			if(f.canRead()) 
			{
				System.out.println("Success, can read " + f);
				readable = true;
			}
			else
			{
				System.out.println("Fail, unable to read " + f);
			}
		}
		// If any problems, catch exception and display Error message.
		catch(Exception ex)
		{
			System.out.println("Error, cant open "		);
			ex.printStackTrace();
		}
	
	}
	
	@Override
	public String toString() 
	{
		String a = (exists) ? " does exist" : " doesn't exist"; 
		String b = (readable) ? " is readable" : " is unreadable";
		return f.getAbsolutePath() + a + " and " +  b ;
			
			
	}

}
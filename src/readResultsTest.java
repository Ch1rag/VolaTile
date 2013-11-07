import java.util.*;

/** 
 * Test tat ReadResults class actually works.
 * @
 * PUBLIC FEATURES:
 * // Constructors
 *    main(String args[])
 * // Methods
 * none.
 *
 * @author Anthony Nowlan
 * @version 20131107
 */
 
class ReadResultsTest
{
	// demonstrate using ReadResults class.
	public static void main(String args[])
	{
		String f;
		ReadResults r;
		
		// for each argument on command line, pass to ReadResults.
		for (String s: args)	
		{
			f = s;		// filename is argument
			r = new ReadResults(f);	// attempt to open file
			
			System.out.println("Read results says " + r + "\n");
			
			r = null;
			f = null;
		}
	}
}
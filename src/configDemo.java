//
// Demonstate how to use Config class to retrieve paths to Python and Volatility.
// 1.0 Anthony Nowlan, 15 Oct 2013.

class configDemo
{
	public static void main(String args[])
	{
		Config c = Config.getInstance();		// create a new Instance of Config class.
		System.out.println(c.getPython());		// display python path
		System.out.println(c.getVolatility());	// display volatility path.
	
	}

}
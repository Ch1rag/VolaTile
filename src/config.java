// singleton config
public class Config
{
	// alternatively, we can eagerly create instance 
	private volatile static Config uniqueInstance;
	private String volatilityPath;
	private String pythonPath;
	
	private Config()
	{
		
		// should read these strings from file.
		volatilityPath	= "c:\\vol\\vol.py";
		pythonPath 		= "c:\\python\\python";
	
	}
	
	// sychronize force every thread to wait its turn before entering.
	public static Config getInstance()	
	{
		if (uniqueInstance == null)		// check if instance already running.
		{
			synchronized (Config.class)	// if not synchronize threads first time through.
			{
				if (uniqueInstance == null)	// double check that still null inside sync block
				{
					uniqueInstance = new Config();	// create a new instance of class.
				}
			}
		}
		return uniqueInstance;
	}
	
	public String getPython()
	{
		return pythonPath;
	}
	
	public String getVolatility()
	{
		return volatilityPath;
	}

}
// singleton config
public class Config
{
	// alternatively, we can eagerly create instance 
	private static Config uniqueInstance;
	private String volatilityPath;
	private String pythonPath;
	
	private Config()
	{
		
	
		// should read these strings from file.
		volatilityPath	= "c:\\vol\\vol.py";
		pythonPath 		= "c:\\python\\python";
	
	}
	
	// sychronize force every thread to wait its turn before entering.
	public static synchronized Config getInstance()	
	{
		if (uniqueInstance == null)
		{
			uniqueInstance = new Config();
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
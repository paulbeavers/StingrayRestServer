package stingraypackage;


//--------------------------------------------------------------------------
// GlobalConfig is a singleton instance for the purpose of managing the 
// global configuration variables for the entire micro-service.
// 
// Configuration is loaded upon startup
//---------------------------------------------------------------------------

public class GlobalConfig {
	
	//---------------------------------------------------------------------------
	// class variables for the singleton
	//---------------------------------------------------------------------------
	
	private static int referenceCounter;
	private static String postgresHostname;
	private static int postgresPortNumber;
	private static GlobalConfig instance = null;
	
	//-------------------------------------------------------
	// Exists only to defeat instantiation.
	//-------------------------------------------------------
	protected GlobalConfig() 
	{
	      // Exists only to defeat instantiation.
	}
	
	//------------------------------------------------------------------------------
	// getInstance()
	//------------------------------------------------------------------------------	
	public static GlobalConfig getInstance() 
	{
		if(instance == null) 
		{
	         instance = new GlobalConfig();
	         referenceCounter = 0;
	    }
	    return instance;
	}
	
	//------------------------------------------------------------------------------
	// incrementReferenceCpunter()
	//------------------------------------------------------------------------------	
	public int incrementReferenceCounter()
	{
		referenceCounter++;
		return(referenceCounter);
	}
	
	//-------------------------------------------------------------------------
	// Getter and Setter for postgresHostname;
	//-------------------------------------------------------------------------
	public String getPostgresHostname()
	{
		return(postgresHostname);
	}
	
	public void setPostgresHostname(String passedHostname)
	{
		postgresHostname = passedHostname;
		return;
	}
	
	//-------------------------------------------------------------------------
	// Getter and Setter for postgresPortNumber;
	//-------------------------------------------------------------------------
	public int getPostgresPortNumber()
	{
		return(postgresPortNumber);
	}
	
	public void setPostgresPortNumber(int passedPortNumber)
	{
		postgresPortNumber = passedPortNumber;
		return;
	}
}

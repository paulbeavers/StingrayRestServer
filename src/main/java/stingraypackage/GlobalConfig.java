package stingraypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	private static String postgresSystemUser;
	private static String postgresSystemPassword;


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
	         loadTestConfigData();
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
	
	public static void setPostgresHostname(String passedHostname)
	{
		postgresHostname = passedHostname;
		return;
	}
	
	//-------------------------------------------------------------------------
	// Getter and Setter for postgresPortNumber;
	//-------------------------------------------------------------------------
	public  int getPostgresPortNumber()
	{
		return(postgresPortNumber);
	}
	
	public static void setPostgresPortNumber(int passedPortNumber)
	{
		postgresPortNumber = passedPortNumber;
		return;
	}
	
	//-------------------------------------------------------------------------
	// Getter and Setter for postgresSystemUser;
	//-------------------------------------------------------------------------
	public String getPostgresSystemUser()
	{
		return(postgresSystemUser);
	}
		
	public static void setPostgresSystemUser(String passedPostgresSystemUser)
	{
		postgresSystemUser = passedPostgresSystemUser;
		return;
	}
		
	//-------------------------------------------------------------------------
	// Getter and Setter for postgresSystemPassword;
	//-------------------------------------------------------------------------
	public String getPostgresSystemPassword()
	{
		return(postgresSystemPassword);
	}
			
	public static void setPostgresSystemPassword(String passedPostgresSystemPassword)
	{
		postgresSystemPassword = passedPostgresSystemPassword;
		return;
	}
		
	//-------------------------------------------------------------------------
    // loadTestConfigData
	//-------------------------------------------------------------------------
	public static void loadTestConfigData()
	{
		setPostgresHostname("192.168.156.194");
		setPostgresPortNumber(5432);
		setPostgresSystemUser("stingray_user");
		setPostgresSystemPassword("stingraypw");
	}
	
	//-------------------------------------------------------------------------
    // loadConfigData
	//-------------------------------------------------------------------------
	public static void loadConfigData()
	{
		setPostgresHostname("localhost");
		setPostgresPortNumber(5432);
		setPostgresSystemUser("STRINGRAY");
		setPostgresSystemUser("stringraypw");
	}
	
	//----------------------------------------------------------------------------------
	// Check to make sure this user is allowed to act on behalf of the specified tenant
	//----------------------------------------------------------------------------------
	
	boolean validForTenant(String username, String tenant_name)
	{
		boolean success = false;
		
		String connectString = "jdbc:postgresql://" + this.getPostgresHostname() + 
				":" + this.getPostgresPortNumber() + "/stingraydb";
		
		try {
			Connection connection = null;
			
			connection = DriverManager.getConnection(connectString, this.getPostgresSystemUser(),
					this.getPostgresSystemPassword());
			
			if (connection == null)
			{
				System.out.println("connection is null");
			}
			else
			{
				Statement stmt = connection.createStatement();
				ResultSet rs = 
						stmt.executeQuery("SELECT tenant_role FROM stingray_tenant_users where user_email = '" 
								+ username + "' and tenant_name = '" + tenant_name + "'");
				
				if (rs.next() ) 
				{
					success = true;
					System.out.println("Tenant is valid.");
				}
				else
				{
					success = false;
					System.out.println("Tenant is invalid.");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return success;
	}
	
	
}

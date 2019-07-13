package stingraypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Heartbeat {
	
	private String name;
	private String uuid;
	private String hostname;
	private String message;
	private String systemtime;
	private String posttime;
	
	Heartbeat() {}

	void setName(String name)
	{
		this.name = name;
	}
	
	void setHostname(String hostname)
	{
		this.hostname = hostname;
	}
	
	void setUuid(String uuid)
	{
		this.uuid = uuid;
	}
	
	void setMessage(String message)
	{
		this.message = message;
	}
	
	void setSystemtime(String systemtime)
	{
		this.systemtime = systemtime;
	}
	
	void setPosttime(String posttime)
	{
		this.posttime = posttime;
	}
	
	String getName()
	{
		return this.name;
	}
	
	String getHostname()
	{
		return this.hostname;
	}
	
	String getUuid()
	{
		return this.uuid;
	}
	
	String getMessage()
	{
		return this.message;
	}
	
	String getSystemtime()
	{
		return this.systemtime;
	}
	
	String getPosttime()
	{
		return this.posttime;
	}
	
	//---------------------------------------------------------------
    // check to see if this is a valid user account
    //---------------------------------------------------------------
    public boolean postHeartbeat2()
    {
    	boolean success = false;
    	
    	GlobalConfig gConfig;
		gConfig = GlobalConfig.getInstance();
		
		String connectString = "jdbc:postgresql://" + gConfig.getPostgresHostname() + 
				":" + gConfig.getPostgresPortNumber() + "/stingraydb";
		
		try {
			Connection connection = null;
			
			connection = DriverManager.getConnection(connectString, gConfig.getPostgresSystemUser(),
					gConfig.getPostgresSystemPassword());
			
			if (connection == null)
			{
				System.out.println("connection is null");
				success = false;
			}
			else
			{
				Statement stmt = connection.createStatement();
				
				String sqlText = "INSERT into stingray_heartbeat (hostname, uuid, message, systemtime, tenant_name) ";
				sqlText = sqlText + "VALUES (" + "'" + hostname + "',";
				sqlText = sqlText + "'" + uuid +"',";
				sqlText = sqlText + "'" + message +"',";
				sqlText = sqlText + "'" + systemtime +"',";
				sqlText = sqlText + "'" + "tenant" +"')";
				
				System.out.println(sqlText);
				
				stmt.executeQuery(sqlText);
				
				success = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		}
    
    	return(success);
    }
    
    public boolean postHeartbeat()
    {
    	boolean success = false;
    	
    	GlobalConfig gConfig;
		gConfig = GlobalConfig.getInstance();
		
		String connectString = "jdbc:postgresql://" + gConfig.getPostgresHostname() + 
				":" + gConfig.getPostgresPortNumber() + "/stingraydb";
    	
    	String SQL = "INSERT INTO stingray_heartbeat(hostname, uuid, message, systemtime, tenant_name) "
                + "VALUES(?,?,?,?,?)";
 
        try {
        		Connection connection = null;
        		
        		connection = DriverManager.getConnection(connectString, gConfig.getPostgresSystemUser(),
        				gConfig.getPostgresSystemPassword());
        		
                PreparedStatement pstmt = connection.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);
 
                pstmt.setString(1, this.hostname);
                pstmt.setString(2, this.uuid);
                pstmt.setString(3, this.message);
                pstmt.setString(4, this.systemtime);
                pstmt.setString(5, "tenant");
 
                int affectedRows = pstmt.executeUpdate();
              
                if (affectedRows > 0) {
                	success = true;
                }
             
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            success = false;
        }
        
        
        return success;
    	
    	
    	
    }
				
}


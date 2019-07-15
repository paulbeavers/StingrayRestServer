package stingraypackage;

import java.sql.Connection;
import java.sql.DriverManager;
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
	private String tenant_name;
	
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
	
	void setTenant_name(String tenant_name)
	{
		this.tenant_name = tenant_name;
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
	
	String getTenant_name()
	{
		return this.tenant_name;
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
       		pstmt.setString(5, this.tenant_name);
 
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


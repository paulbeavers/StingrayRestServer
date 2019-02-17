package stingraypackage;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class StatusController {

    //--------------------------------------------------------------
	// handle the status request for the REST API server
	//--------------------------------------------------------------
	

    @RequestMapping("/status")
    public Status status() 
    {
    		GlobalConfig gConfig;
			gConfig = GlobalConfig.getInstance();
			gConfig.incrementReferenceCounter();
			gConfig.incrementReferenceCounter();
    		System.out.println("Reference Counter = " +  gConfig.incrementReferenceCounter());
    		Status returnStatus = new Status("OK", "REST API server is functionng.");
			return returnStatus;
    }
    
    //--------------------------------------------------------------
  	// handle the status request for the postgres connectivity
  	//--------------------------------------------------------------
    @RequestMapping("/sqlstatus")
    public Status sqlstatus()
    {	
    	GlobalConfig gConfig;
		gConfig = GlobalConfig.getInstance();
		
		String connectString = "jdbc:postgresql://" + gConfig.getPostgresHostname() + 
				":" + gConfig.getPostgresPortNumber() + "/stingraydb";
		
    	Status returnStatus = null;
		try {
			Connection connection = null;
			
			connection = DriverManager.getConnection(connectString, gConfig.getPostgresSystemUser(),
					gConfig.getPostgresSystemPassword());
			
			if (connection == null)
			{
				System.out.println("connection is null");
			}
			else
			{
				Statement stmt = connection.createStatement();
				ResultSet rs = 
				stmt.executeQuery("SELECT user_id, password FROM stingray_users");
				
				while (rs.next()) {
					  String userID = rs.getString("user_id");
					  String password = rs.getString("password");
					  System.out.println(userID);
					  System.out.println(password);
				}
				
				System.out.println("Connection is not null.");
				returnStatus = new Status("OK", "Postgres server is functioning.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnStatus = new Status("ERROR", "Cannot connect to database.");
		}
	
		return returnStatus;
    }
    
    
    
    
}

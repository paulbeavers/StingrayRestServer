package stingraypackage;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;

@RestController
public class StatusController {

    // deleted some files

    @RequestMapping("/status")
    public String responder()
        {
    		
    		try {
    			Connection connection = null;
				connection = DriverManager.getConnection(
						"jdbc:postgresql://127.0.0.1:5432/stingraydb", "stingray_user",
						"stingraypw");
				
				
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
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	
    	
    	
    	
                return("OK");
        }
}

package stingraypackage;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    
    public Authentication authenticate(Authentication auth) 
      throws AuthenticationException {
    	
    	//
    	// TODO: Add code to validate user against the postgres DB here
    	//
    	
        String username = auth.getName();
        String password = auth.getCredentials()
            .toString();
 
        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        //
        // When converting roles to auths, spring expects "ROLE_" at front of role name
        //
        
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        if (checkUserInDatabase(username, password))
        {
        	return new UsernamePasswordAuthenticationToken
                    (username, password, authorities);	
        }
        else {
            throw new
              BadCredentialsException("External system authentication failed");
        }
    }
 
    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    //---------------------------------------------------------------
    // check to see if this is a valid user account
    //---------------------------------------------------------------
    public boolean checkUserInDatabase(String userEmail, String password)
    {
    	boolean validUser = false;
    	
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
			}
			else
			{
				Statement stmt = connection.createStatement();
				ResultSet rs = 
						stmt.executeQuery("SELECT user_email, password FROM stingray_users where user_email = '" 
								+ userEmail + "' and password = '" + password + "'");
				
				if (rs.next() ) 
				{
					validUser = true;
					System.out.println("User is valid.");
				}
				else
				{
					validUser = false;
					System.out.println("User is invalid.");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	return(validUser);
    }
 
    
    
}


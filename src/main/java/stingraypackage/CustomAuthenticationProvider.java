package stingraypackage;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
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
        
        if ("externaluser".equals(username) && "pass".equals(password)) {
            return new UsernamePasswordAuthenticationToken
              (username, password, authorities);
        } else {
            throw new
              BadCredentialsException("External system authentication failed");
        }
    }
 
    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
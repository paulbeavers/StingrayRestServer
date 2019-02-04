package stingraypackage;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// Authentication : User --> Roles
	
	
	@Override
	
	protected void configure(AuthenticationManagerBuilder auth) 
		      throws Exception {
		        
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();        
		String userName = "User1";
		        
		auth.inMemoryAuthentication()        
			.withUser(userName)
    		.password(encoder.encode("secret"))
	  		.roles("USER");	       
	}
	
	
	@Override
	// Authorization : Role -> Access
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests().antMatchers("/status/**")
				.hasRole("USER").antMatchers("/**").hasRole("ADMIN").and()
				.csrf().disable().headers().frameOptions().disable();
	}
	

	
	
	

}

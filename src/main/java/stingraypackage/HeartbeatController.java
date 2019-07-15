package stingraypackage;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatController {

    //--------------------------------------------------------------
	// Post the event
	//--------------------------------------------------------------
	
	@PostMapping("/heartbeat")
	String postHeartbeat(@RequestBody Heartbeat newHeartbeat) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName(); //get logged in username
		      
		GlobalConfig gConfig;
		gConfig = GlobalConfig.getInstance();
				
		System.out.println(newHeartbeat.getSystemtime());
		System.out.println(newHeartbeat.getHostname());
		System.out.println(newHeartbeat.getUuid());
		System.out.println(newHeartbeat.getName());
		System.out.println(newHeartbeat.getTenant_name());
		System.out.println(newHeartbeat.getMessage());
				
		if (gConfig.validForTenant(username, newHeartbeat.getTenant_name()) == false)
		{
			return "ERROR: Bad Tenant";
		}
		else
		{
			if (newHeartbeat.postHeartbeat())
			{
				return "OK";
			}
			else
			{
				return "ERROR";
			}
		}
	}
}

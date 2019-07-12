package stingraypackage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;

@RestController
public class HeartbeatController {

    //--------------------------------------------------------------
	// Post the event
	//--------------------------------------------------------------
	
	@PostMapping("/heartbeat")
	  String postHeartbeat(@RequestBody Heartbeat newHeartbeat) {
		System.out.println(newHeartbeat.getSystemtime());
		System.out.println(newHeartbeat.getHostname());
		System.out.println(newHeartbeat.getUuid());
		System.out.println(newHeartbeat.getName());
		System.out.println(newHeartbeat.getMessage());
		
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

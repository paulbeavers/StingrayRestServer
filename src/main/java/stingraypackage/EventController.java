package stingraypackage;


import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@RestController
public class EventController {

    //--------------------------------------------------------------
	// Post the event
	//--------------------------------------------------------------
	
	@RequestMapping(value = "/event", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String eventJson(HttpEntity<String> httpEntity) throws ParseException {
		
	    String jsonString = httpEntity.getBody();
	    
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName(); //get logged in username
		
		GlobalConfig gConfig;
		gConfig = GlobalConfig.getInstance();
	    
	    JSONParser parser = new JSONParser();
	    JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
	    
	    String tenant_name = (String) jsonObject.get("tenant_name");  
	    System.out.println(tenant_name);
	    
	    if (gConfig.validForTenant(username, tenant_name) == false)
		{
			return "ERROR: Bad Tenant";
		}
	    
	    
	    return "OK";
	}
}

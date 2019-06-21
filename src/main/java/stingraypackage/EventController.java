package stingraypackage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;

@RestController
public class EventController {

    //--------------------------------------------------------------
	// Post the event
	//--------------------------------------------------------------
	
	@RequestMapping(value = "/event", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String eventJson(HttpEntity<String> httpEntity) {
	    String json = httpEntity.getBody();
	    
	    return json;
	}
}

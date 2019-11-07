package stingraypackage;


import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpEntity;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class EntityController {

    //--------------------------------------------------------------
	// Post a new entity
	//--------------------------------------------------------------
	@RequestMapping(value = "/entity", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String postEntity(HttpEntity<String> httpEntity) throws ParseException {
		
		boolean success = true;
	    String jsonString = httpEntity.getBody();
	    
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName(); //get logged in username
		
		GlobalConfig gConfig;
		gConfig = GlobalConfig.getInstance();
	    
	    JSONParser parser = new JSONParser();
	    JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
	    
	    Iterator iterator = jsonObject.keySet().iterator();
	    while(iterator.hasNext())
	    {
	    	String key = (String) iterator.next();
	        System.out.println(key + ":" +  jsonObject.get(key));	
	    }
	    	    
	    String tenant_name = (String) jsonObject.get("tenant_name"); 
	    String entity_id = (String) jsonObject.get("entity_id");
	    String entity_description = (String) jsonObject.get("entity_description");
	    String entity_parent_id = (String) jsonObject.get("entity_parent_id");
	    String entity_type = (String) jsonObject.get("entity_type");
	    
	    System.out.println(tenant_name);
	    
	    if (gConfig.validForTenant(username, tenant_name) == false)
		{
			return "ERROR: Bad Tenant";
		}
	    
	    
	    String connectString = "jdbc:postgresql://" + gConfig.getPostgresHostname() + 
				":" + gConfig.getPostgresPortNumber() + "/stingraydb";
    	
		String SQL = "INSERT INTO stingray_entity(tenant_name, entity_id, entity_type, entity_description, entity_parent_id) "
                + "VALUES(?,?,?,?,?)";
 
		try {
			Connection connection = null;
       		
       		connection = DriverManager.getConnection(connectString, gConfig.getPostgresSystemUser(),
       				gConfig.getPostgresSystemPassword());
        		
       		PreparedStatement pstmt = connection.prepareStatement(SQL,
       				Statement.RETURN_GENERATED_KEYS);
 
       		pstmt.setString(1, tenant_name);
       		pstmt.setString(2, entity_id);
       		pstmt.setString(3, entity_type);
       		pstmt.setString(4, entity_description);
       		pstmt.setString(5, entity_parent_id);
 
       		int affectedRows = pstmt.executeUpdate();
              
       		if (affectedRows > 0) {
               	success = true;
       		}
             
		} catch (SQLException ex) {
				System.out.println(ex.getMessage());
				success = false;
		}
	    
	    return "OK";
	}
	
	//--------------------------------------------------------------
	// Get an entity 
	//--------------------------------------------------------------
	@RequestMapping(value = "/entity", method = RequestMethod.GET)
    public ResponseEntity <Entity> GetEntity(@RequestParam(value="entity_id", defaultValue="all") String entity_id)
    {
		Entity myEntity = null;
		
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
				String selectClause = "select tenant_name, entity_id, entity_description, " +
						"entity_parent_id, entity_type, create_time from stingray_entity " +
						"where entity_id = " + "'" + entity_id + "'";
						
				ResultSet rs = 
				stmt.executeQuery(selectClause);
				
				if (rs.next()) {
					  myEntity = new Entity(rs.getString("tenant_name"), rs.getString("entity_id"),
							  rs.getString("entity_description"), rs.getString("entity_parent_id"),
									  rs.getString("entity_type"), rs.getString("create_time"));
				}
				else
				{
					
				}
				
				System.out.println("Connection is not null.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Responded", "MyController");
		
		return ResponseEntity.accepted().headers(headers).body(myEntity);
    }
	
	//--------------------------------------------------------------
	// Delete an entity 
	//--------------------------------------------------------------
	@RequestMapping(value = "/entity", method = RequestMethod.DELETE)
    public String DeleteEntity(@RequestParam(value="entity_id", defaultValue="all") String entity_id)
    {
		
		int i = 0;
		i = i + 1;
		
		return "ok";
    }
	
	//--------------------------------------------------------------
	// Get a list of entities an entity 
	//--------------------------------------------------------------
	@RequestMapping(value = "/entities", method = RequestMethod.GET)
	public String GetEntities(@RequestParam(value="entity_id", defaultValue="all") String entity_id)
	{
			
			
		return "ok";
	}

}
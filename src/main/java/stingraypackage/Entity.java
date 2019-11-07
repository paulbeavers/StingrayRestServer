package stingraypackage;

public class Entity {
	
    private String tenant_name;
    private String entity_id;
    private String entity_description;
    private String entity_parent_id;
    private String entity_type;
    private String create_time;
    
    public Entity(String in_tenant_name, String in_entity_id, String in_entity_description, 
    			String in_entity_parent_id, String in_entity_type, String in_create_time) {
    	
        this.tenant_name = in_tenant_name;
        this.entity_id = in_entity_id;
        this.entity_description = in_entity_description;
        this.entity_parent_id = in_entity_parent_id;
        this.entity_type = in_entity_type;
        this.create_time = in_create_time;        
    }
    
    public String getTenant_name()
    {
    	return(tenant_name);
    }
    
    public String getEntity_id()
    {
    	return(entity_id);
    }
    
    public String getEntity_description()
    {
    	return(entity_description);
    }
    
    public String getEntity_parent_id()
    {
    	return(entity_parent_id);
    }
    
    public String getEntity_type()
    {
    	return(entity_type);
    }
    
    public String getCreate_time()
    {
    	return(create_time);
    }

}
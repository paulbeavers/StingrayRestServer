package stingraypackage;

public class Status {

    private String code;
    private String text;
    

    public Status(String inCode, String inText) {
        this.code = inCode;
        this.text = inText;
    }
    
    public String getCode()
    {
    	return(code);
    }
    
    public String getText()
    {
    	return(text);
    }

}
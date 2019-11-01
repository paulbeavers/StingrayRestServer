package stingraypackage;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StingrayRestServerApplication {
	
	private static void LoadInitialConfig()
	{
		GlobalConfig gConfig;
		gConfig = GlobalConfig.getInstance();
		gConfig.LoadProperties();
		
		return;
	}

	public static void main(String[] args) {
		
		ApplicationContext context = SpringApplication.run(StingrayRestServerApplication.class, args);
		GlobalConfig gConfig;
		gConfig = GlobalConfig.getInstance();
		
		gConfig.setAppContext(context);
		
		LoadInitialConfig();
		
	}

}


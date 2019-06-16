package stingraypackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StingrayRestServerApplication {
	
	private static void LoadInitialConfig()
	{
		GlobalConfig gConfig;
		gConfig = GlobalConfig.getInstance();
		return;
	}

	public static void main(String[] args) {
		
		LoadInitialConfig();
		
		SpringApplication.run(StingrayRestServerApplication.class, args);
	}

}


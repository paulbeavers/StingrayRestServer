package stingraypackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StingrayRestServerApplication {
	
	 

	public static void main(String[] args) {
	
		GlobalConfig gConfig;
		gConfig = GlobalConfig.getInstance();
		gConfig.incrementReferenceCounter();
		gConfig.incrementReferenceCounter();
		
		SpringApplication.run(StingrayRestServerApplication.class, args);
	}

}


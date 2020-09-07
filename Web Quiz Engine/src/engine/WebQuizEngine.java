package engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebQuizEngine {

	public final static Logger logger = LoggerFactory.getLogger(WebQuizEngine.class);

	public static void main(String[] args) {
		SpringApplication.run(WebQuizEngine.class, args);
	}

}

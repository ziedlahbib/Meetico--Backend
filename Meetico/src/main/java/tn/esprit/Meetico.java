package tn.esprit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableScheduling
@SpringBootApplication
@EnableSwagger2
@EnableJpaAuditing
public class Meetico {

	public static void main(String[] args) {
		SpringApplication.run(Meetico.class, args);
		
		/*String input="1 4 9 @";
		String output = BadWordFilter.getCensoredText(input);
		System.out.println(output);*/
		
		
	}
	
	
	
	
	
	

}

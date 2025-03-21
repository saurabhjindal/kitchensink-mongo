package org.jboss.as.quickstarts.kitchensink;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootApplication
@ComponentScan(basePackages = "org.jboss.as.quickstarts.kitchensink")
public class Application {


	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

}

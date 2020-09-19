package br.com.acme.sample.encriptedservice;

import br.com.acme.sample.security.cript.ArsenalEnableFullEncript;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ArsenalEnableFullEncript
public class EncriptedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncriptedServiceApplication.class, args);
	}

}

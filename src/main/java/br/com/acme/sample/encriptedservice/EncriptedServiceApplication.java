package br.com.acme.sample.encriptedservice;

import br.com.acme.sample.security.cript.ArsenalEnableFullEncript;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("br.com.acme.sample")
@ArsenalEnableFullEncript
public class EncriptedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncriptedServiceApplication.class, args);
	}

}

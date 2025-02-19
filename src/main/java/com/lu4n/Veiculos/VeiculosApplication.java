package com.lu4n.Veiculos;

import com.lu4n.Veiculos.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VeiculosApplication implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {
		Principal menu = new Principal();
		menu.menu();
	}

	public static void main(String[] args) {
		SpringApplication.run(VeiculosApplication.class, args);
	}

}

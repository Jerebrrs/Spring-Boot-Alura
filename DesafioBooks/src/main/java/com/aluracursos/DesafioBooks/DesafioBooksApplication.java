package com.aluracursos.DesafioBooks;

import com.aluracursos.DesafioBooks.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioBooksApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafioBooksApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();

		principal.muestraElMenu();
	}
}

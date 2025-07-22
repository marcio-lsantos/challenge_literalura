package br.com.alura.challenge_literalura;

import br.com.alura.challenge_literalura.principal.Principal;
import br.com.alura.challenge_literalura.repository.IAutoresRepository;
import br.com.alura.challenge_literalura.repository.ILivrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraluraApplication implements CommandLineRunner {

	@Autowired
	private IAutoresRepository autoresRepository;

	@Autowired
	private ILivrosRepository livrosRepository;

	public static void main(String[] args) {

		SpringApplication.run(ChallengeLiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autoresRepository, livrosRepository);
		principal.mostrarMenu();
	}

}

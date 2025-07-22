package br.com.alura.challenge_literalura.repository;

import br.com.alura.challenge_literalura.models.Livros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILivrosRepository extends JpaRepository<Livros, Long> {
    Livros findByTitulo(String titulo);

    List<Livros> findByIdiomaContaining(String idioma);
}

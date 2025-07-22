package br.com.alura.challenge_literalura.repository;

import br.com.alura.challenge_literalura.models.Autores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAutoresRepository extends JpaRepository<Autores, Long> {
    Autores findByNameIgnoreCase(String nome);

    List<Autores> findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(int anoInicial, int anoFinal);
}

package br.com.alura.challenge_literalura.models;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Dados(
        @JsonAlias("results") List<DadosLivros> resultados
) {
}

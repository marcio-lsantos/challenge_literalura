package br.com.alura.challenge_literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DadosAutores> autor,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") double numeroDeDownloads
) {
}

package br.com.alura.challenge_literalura.service;

public interface IConversorDados {
    <T> T obterDados(String json, Class<T> classe);

}

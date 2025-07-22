package br.com.alura.challenge_literalura.models;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autores autor;

    @Column(name = "nome_autor")
    private String nomeAutor;

    @Column(name = "idioma")
    private String idioma;
    private double numeroDeDownloads;

    public Livros() {}

    public Livros(DadosLivros dadosLivros, Autores autor) {
        this.titulo = dadosLivros.titulo();
        setIdioma(dadosLivros.idioma());
        this.numeroDeDownloads = dadosLivros.numeroDeDownloads();
        this.nomeAutor = autor.getName();
        this.autor = autor;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autores getAutor() {
        return autor;
    }

    public void setAutor(Autores autor) {
        this.autor = autor;
    }

    public List<String> getIdioma() {
        return Arrays.asList(idioma.split(","));
    }

    public void setIdioma(List<String> idioma) {
        this.idioma = String.join(",", idioma);
    }

    public double getNumeroDeDownloads() {
        return numeroDeDownloads;
    }

    public void setNumeroDeDownloads(double numeroDeDownloads) {
        this.numeroDeDownloads = numeroDeDownloads;
    }

    @Override
    public String toString() {
        return "*************** LIVRO 📖 ***************" + "\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + nomeAutor + "\n" +
                "Idioma: " + idioma + "\n" +
                "Número de downloads: " + numeroDeDownloads + "\n" +
                "****************************************" + "\n";
    }
}

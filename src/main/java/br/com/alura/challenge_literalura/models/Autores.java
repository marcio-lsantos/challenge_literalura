package br.com.alura.challenge_literalura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int anoNascimento;
    private int anoFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livros> livros = new ArrayList<>();

    public Autores(DadosAutores dadosAutores) {
        this.name = dadosAutores.nomeAutor();
        this.anoNascimento = dadosAutores.anoNascimento();
        this.anoFalecimento = dadosAutores.anoFalecimento();
    }

    public Autores() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public int getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(int anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<Livros> getLivros() {
        return livros;
    }

    public void setLivros(List<Livros> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        StringBuilder livrosTitulos = new StringBuilder();
        for (Livros livro : livros) {
            livrosTitulos.append(livro.getTitulo()).append(", ");
        }

        if (livrosTitulos.length() > 0) {
            livrosTitulos.setLength(livrosTitulos.length() - 2);
        }

        return  "*************** AUTOR 👨‍ ***************" + "\n" +
                "Autor: " + name + "\n" +
                "Data de nascimento: " + anoNascimento + "\n" +
                "Data de falecimento: " + anoFalecimento + "\n" +
                "Livros: " + livrosTitulos + "\n";
    }
}

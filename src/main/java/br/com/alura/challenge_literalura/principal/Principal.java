package br.com.alura.challenge_literalura.principal;

import br.com.alura.challenge_literalura.models.*;
import br.com.alura.challenge_literalura.repository.IAutoresRepository;
import br.com.alura.challenge_literalura.repository.ILivrosRepository;
import br.com.alura.challenge_literalura.service.ConsumoApi;
import br.com.alura.challenge_literalura.service.ConversorDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConversorDados conversor = new ConversorDados();
    public final String URL_BASE = "https://gutendex.com/books/?search=";

    private IAutoresRepository autoresRepository;
    private ILivrosRepository livrosRepository;

    public Principal(IAutoresRepository autoresRepository, ILivrosRepository livrosRepository) {
        this.autoresRepository = autoresRepository;
        this.livrosRepository = livrosRepository;
    }

    public void mostrarMenu() {
        var opcao = -1;
        System.out.println("\nPor favor selecione uma opção: \n");
        while (opcao != 0) {
            var menu = """
                    
                    1 - Buscar livro por título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    6 - Top 10 livros mais baixados
                    7 - Obter estatísticas
                    0 - Sair
                    
                    """;
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    livrosRegistrados();
                    break;
                case 3:
                    autoresRegistrados();
                    break;
                case 4:
                    autoresPorAno();
                    break;
                case 5:
                    listarPorIdioma();
                    break;
                case 6:
                    topDezLivros();
                    break;
                case 7:
                    estatiticasApi();
                    break;
                case 0:
                    System.out.println("Encerrando aplicação...");
                    break;

                default:
                    System.out.println("Opção não é válida, tentar novamente!");
            }
        }
    }

    private Dados obterDadosLivros() {
        var nomeLivro = leitura.nextLine();
        var json = consumoApi.obterDados(URL_BASE + nomeLivro.replace(" ", "+"));
        System.out.println(json);
        Dados dadosLivros = conversor.obterDados((String) json, Dados.class);
        return dadosLivros;
    }

    private Livros criarLivro(DadosLivros dadosLivros, Autores autor) {
        if (autor != null) {
            return new Livros(dadosLivros, autor);
        } else {
            System.out.println("O autor não existe, não se pode criar o livro!");
            return null;
        }
    }

    private void buscarLivro() {
        System.out.println("Digite o nome do livro que deseja buscar: ");
        Dados dados = obterDadosLivros();
        if (!dados.resultados().isEmpty()) {
            DadosLivros dadosLivros = dados.resultados().get(0);
            DadosAutores dadosAutores = dadosLivros.autor().get(0);
            Livros livro = null;
            Livros livroRepositorio = livrosRepository.findByTitulo(dadosLivros.titulo());
            if (livroRepositorio != null) {
                System.out.println("Este livro já se encontra na base de dados!");
                System.out.println(livroRepositorio.toString());
            } else {
                Autores autorRepositorio = autoresRepository.findByNameIgnoreCase(dadosLivros.autor().get(0).nomeAutor());
                if (autorRepositorio != null) {
                    livro = criarLivro(dadosLivros, autorRepositorio);
                    livrosRepository.save(livro);
                    System.out.println("\n***** Livro Adicionado *****\n");
                    System.out.println(livro);
                } else {
                    Autores autor = new Autores(dadosAutores);
                    autor = autoresRepository.save(autor);
                    livro = criarLivro(dadosLivros, autor);
                    livrosRepository.save(livro);
                    System.out.println("***** Livro Adicionado *****\n");
                    System.out.println(livro);
                }
            }
        } else {
            System.out.println("\nO livro não existe na API de Gutendex, digitar outro livro!");
        }
    }

    private void livrosRegistrados() {
        List<Livros> livros = livrosRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Não há livros registrados!");
            return;
        }
        System.out.println("***** Os Livros registrados são: *****\n");
        livros.stream()
                .sorted(Comparator.comparing(Livros::getTitulo))
                .forEach(System.out::println);
    }


    private void autoresRegistrados() {
        List<Autores> autores = autoresRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Não há autores registrados!");
            return;
        }
        System.out.println("***** Os Autores registrados são: *****\n");
        autores.stream()
                .sorted(Comparator.comparing(Autores::getName))
                .forEach(System.out::println);
    }

    private void autoresPorAno() {
        System.out.println("Escrever o ano que deseja buscar: ");
        var ano = leitura.nextInt();
        leitura.nextLine();
        if(ano < 0) {
            System.out.println("O ano não pode ser negativo, tentar novamente!");
            return;
        }
        List<Autores> autoresPorAno = autoresRepository.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(ano, ano);
        if (autoresPorAno.isEmpty()) {
            System.out.println("Não há autores registrados esse ano!");
            return;
        }
        System.out.println("***** Os Autores vivos registrados nesse ano " + ano + " são: *****\n");
        autoresPorAno.stream()
                .sorted(Comparator.comparing(Autores::getName))
                .forEach(System.out::println);
    }

    private void listarPorIdioma() {
        System.out.println("Digite o idioma ao qual deseja buscar: ");
        String menu = """
                es - Espanhol
                en - Inglês
                fr - Francês
                pt - Português
                """;
        System.out.println(menu);
        var idioma = leitura.nextLine();
        if (!idioma.equals("es") && !idioma.equals("en") && !idioma.equals("fr") && !idioma.equals("pt")) {
            System.out.println("Idioma não é válido, tentar novamente!");
            return;
        }
        List<Livros> livrosPorIdioma = livrosRepository.findByIdiomaContaining(idioma);
        if (livrosPorIdioma.isEmpty()) {
            System.out.println("Não há livros registrados nesse idioma!");
            return;
        }
        System.out.println("***** Os livros registrados no idioma selecionado são: *****\n");
        livrosPorIdioma.stream()
                .sorted(Comparator.comparing(Livros::getTitulo))
                .forEach(System.out::println);
    }

    private void topDezLivros() {
        System.out.println("De onde quer obter os livros mais baixados?");
        String menu = """
                1 - Gutendex
                2 - Base de dados
                """;
        System.out.println(menu);
        var opcion = leitura.nextInt();
        leitura.nextLine();

        if (opcion == 1) {
            System.out.println("***** Os 10 livros mais baixados da API GUTENDEX são: *****\n");
            var json = consumoApi.obterDados(URL_BASE);
            Dados dados = conversor.obterDados((String) json, Dados.class);
            List<Livros> livros = new ArrayList<>();
            for (DadosLivros dadosLivros : dados.resultados()) {
                Autores autor = new Autores(dadosLivros.autor().get(0));
                Livros livro = new Livros(dadosLivros, autor);
                livros.add(livro);
            }
            livros.stream()
                    .sorted(Comparator.comparing(Livros::getNumeroDeDownloads).reversed())
                    .limit(10)
                    .forEach(System.out::println);
        } else if (opcion == 2) {
            System.out.println("***** Os 10 livros mais baixados da base de dados são: *****\n");
            List<Livros> livros = livrosRepository.findAll();
            if (livros.isEmpty()) {
                System.out.println("Não há livros registrados!");
                return;
            }
            livros.stream()
                    .sorted(Comparator.comparing(Livros::getNumeroDeDownloads).reversed())
                    .limit(10)
                    .forEach(System.out::println);
        } else {
            System.out.println("Opção não é válida, tentar novamente!");
        }
    }

    private void estatiticasApi() {
        System.out.println("De onde quer obter as estatísticas? ");
        String menu = """
                1 - Gutendex
                2 - Base de dados
                """;
        System.out.println(menu);
        var opcion = leitura.nextInt();
        leitura.nextLine();

        if (opcion == 1) {
            System.out.println("***** Estatísticas de downloads da API GUTENDEX *****\n");
            var json = consumoApi.obterDados(URL_BASE);
            Dados dados = conversor.obterDados((String) json, Dados.class);
            DoubleSummaryStatistics estatisticas = dados.resultados().stream()
                    .collect(Collectors.summarizingDouble(DadosLivros::numeroDeDownloads));
            System.out.println("📈 Livro com mais downloads: " + estatisticas.getMax());
            System.out.println("📉 Livro com menos downloads: " + estatisticas.getMin());
            System.out.println("📊 Média de downloads: " + estatisticas.getAverage());
            System.out.println("\n");
        } else if (opcion == 2) {
            System.out.println("***** Estatísticas de downloads da base de dados *****\n");
            List<Livros> livros = livrosRepository.findAll();
            if (livros.isEmpty()) {
                System.out.println("Não há livros registrados!");
                return;
            }
            DoubleSummaryStatistics estatisticas = livros.stream()
                    .collect(Collectors.summarizingDouble(Livros::getNumeroDeDownloads));
            System.out.println("📈 Livro com mais downloads: " + estatisticas.getMax());
            System.out.println("📉 Livro com menos downloads: " + estatisticas.getMin());
            System.out.println("📊 Média de downloads: " + estatisticas.getAverage());
            System.out.println("\n");
        } else {
            System.out.println("Opção não é válida, tentar novamente!");
        }
    }
}

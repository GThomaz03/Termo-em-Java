package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * A classe ListaPalavars é responsável por gerenciar uma lista de palavras de cinco letras
 * lidas a partir de um arquivo.
 */
public class Termo {
    // Lista que armazena palavras de cinco letras.
    List<String> palavrasCincoLetras = new ArrayList<>();

    /**
     * Construtor padrão que inicializa a lista de palavras de cinco letras
     * lendo-as de um arquivo específico.
     */
    public void listaPalavras() {
        // Tenta abrir e ler o arquivo de palavras.
        try (BufferedReader br = new BufferedReader(new FileReader("E:\\Estudos\\Java\\Termo\\dicionario.txt"))) {
            String linha;
            // Lê cada linha do arquivo
            while ((linha = br.readLine()) != null) {
                // Verifica se a palavra tem exatamente 5 letras
                if (linha.length() == 5) {
                    palavrasCincoLetras.add(linha); // Adiciona à lista
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Imprime a pilha de erro em caso de exceção
        }
    }

    /**
     * Seleciona aleatoriamente uma palavra de cinco letras da lista carregada.
     *
     * @return Uma palavra aleatória da lista de palavras de cinco letras.
     */
    public String getPalavra() {
        // Gera um índice aleatório para selecionar uma palavra
        double palavraRandom = Math.random() * palavrasCincoLetras.size();
        return palavrasCincoLetras.get((int) palavraRandom);
    }

    /**
     * Converte uma palavra em um array de strings, onde cada elemento é uma letra da palavra.
     *
     * @param palavra A palavra que será convertida.
     * @return Um array de strings contendo as letras da palavra.
     */
    public String[] palavrasToLetras(String palavra) {
        String[] letras = new String[5];
        for (int i = 0; i < 5; i++) {
            letras[i] = String.valueOf(palavra.charAt(i)); // Converte cada caractere para string
        }
        return letras;
    }

    /**
     * Inicia o jogo de adivinhação, permitindo que o jogador tente adivinhar a palavra selecionada.
     *
     * @param palavra A palavra que o jogador deve adivinhar.
     */
    public void game(String palavra) {
        Scanner scanner = new Scanner(System.in); // Scanner para entrada do jogador
        boolean acertou = false; // Indica se o jogador adivinhou corretamente
        int chances = 5; // Número máximo de tentativas
        ArrayList<String> conjuntoChutes = new ArrayList<>(); // Armazena os chutes anteriores do jogador

        // Loop que permite tentativas até que o jogador acerte ou esgote as tentativas
        while (!acertou && chances > 0) {
            String tempPalavra = ""; // Palavra temporária para exibir o resultado parcial
            System.out.println("Tentativas: " + chances);
            String palavraChutada = scanner.next().toLowerCase(Locale.ROOT); // Lê a palavra chutada pelo jogador

            // Verifica se a palavra chutada tem exatamente 5 letras
            if (palavraChutada.length() != 5) {
                System.out.println("Digite uma palavra com 5 letras");
            } else {
                // Compara a palavra chutada com a palavra correta
                int[] comparacao = comparaPalavras(palavra, palavraChutada);
                String[] chute = palavrasToLetras(palavraChutada);

                // Monta a string com as cores correspondentes para o chute atual
                for (int i = 0; i < 5; i++) {
                    if (comparacao[i] == 0) {
                        tempPalavra += chute[i]; // Letra não presente na palavra correta
                    } else if (comparacao[i] == 1) {
                        tempPalavra += ConsoleColors.GREEN + chute[i] + ConsoleColors.RESET; // Letra correta e na posição certa
                    } else {
                        tempPalavra += ConsoleColors.YELLOW + chute[i] + ConsoleColors.RESET; // Letra correta mas na posição errada
                    }
                }
                conjuntoChutes.add(tempPalavra); // Adiciona o chute formatado à lista de chutes anteriores
                for (String j : conjuntoChutes) {
                    System.out.println(j); // Exibe todos os chutes anteriores
                }
                System.out.println();

                acertou = verificaGanhou(comparacao); // Verifica se o jogador ganhou
                chances--; // Decrementa o número de tentativas restantes
            }
        }
    }

    /**
     * Compara duas palavras letra por letra para determinar se as letras estão
     * na posição correta, incorreta, ou não estão presentes.
     *
     * @param palavraRandom  A palavra correta.
     * @param palavraChutada A palavra chutada pelo jogador.
     * @return Um array de inteiros que indica o resultado da comparação para cada letra.
     */
    public int[] comparaPalavras(String palavraRandom, String palavraChutada) {
        String[] palavraRandomArray = palavrasToLetras(palavraRandom);
        String[] palavraChutadaArray = palavrasToLetras(palavraChutada);

        int[] letrasIguais = new int[5];
        // Loop de verificação de letras
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (Objects.equals(palavraChutadaArray[i], palavraRandomArray[j])) {
                    if (i == j) {
                        letrasIguais[i] = 1; // Letra correta e na posição certa
                    } else {
                        if (letrasIguais[i] != 1) {
                            letrasIguais[i] = 2; // Letra correta mas na posição errada
                        }
                    }
                }
            }
        }

        return letrasIguais;
    }

    /**
     * Verifica se o jogador acertou a palavra, ou seja, se todas as letras
     * estão na posição correta.
     *
     * @param letrasIguais Um array de inteiros representando o resultado da comparação das letras.
     * @return True se o jogador acertou a palavra, caso contrário, False.
     */
    public boolean verificaGanhou(int[] letrasIguais) {
        int[] gabarito = {1, 1, 1, 1, 1}; // Gabarito para uma palavra completamente correta
        return Arrays.equals(letrasIguais, gabarito); // Compara os arrays para verificar se são idênticos
    }
}

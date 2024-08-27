package org.example;

public class Main {
    public static void main(String[] args) {
        Termo palavras = new Termo();
        palavras.listaPalavras();

        String palavra = palavras.getPalavra();
        palavras.game(palavra);
    }
}
package main;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class NQueensTresPorquinhos {

    /*
         N-Queens Problem (Versão dos Três porquinhos do JB)
         Em uma matriz NxN, coloque os porquinhos e as galinhas de forma que nenhum porquinho veja galinhas, e nenhuma galinha veja os porquinhos.

         Input - tamanho da matriz quadrada, e quantidade de porquinhos e galinhas
         ex: java porquinhos 5 3 5 // tabuleiro 5x5, 3 porquinhos, 5 galinhas

         Output - A saída é um inteiro que representa o número de soluções válidas para o problema.

         O problema é equivalente ao problema das N-Rainhas, mas com as seguintes restrições:
            - As galinhas podem ver as galinhas, mas não podem ver os porquinhos.
            - Os porquinhos podem ver os porquinhos, mas não podem ver as galinhas.
            - Todos os porquinhos e galinhas devem fazer parte do jogo
            - Porquinhos e galinhas só veem bichos que estão na mesma linha, coluna ou diagonal.

        O algoritmo deve ser baseado em backtraking que ler a configuração do jogo via linha de comando.
     */

    public static void main(String[] args) {

        Instant before = Instant.now();
        System.out.println(new NQueensTresPorquinhos().run(args));
        Instant after = Instant.now();
        long delta = Duration.between(before, after).toSeconds();
        System.out.println("tempo: " + delta);
    }

    private long run(String[] args) {

        char[][] tabuleiro = novoTabuleiro(Integer.parseInt(args[0]));

        int min = Math.min(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        int max = Math.max(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        int p = min;
        int g = max;

        //printTabuleiro(tabuleiro);

        return posicionarPorquinhos(p, g, tabuleiro, 0, 0, 0);
    }

    /**
     * Aloca os porquinhos no tabuleiro de forma recursiva, tentando todas as possibilidades.
     * Toda vez que conseguir posicionar todos os porquinhos, tenta posicionar as galinhas.
     * Se conseguir posicionar todas as galinhas, incrementa o número de soluções.
     * <p>
     * Ao chamar recursivamente, busca a próxima posição a partir da posição atual, sem voltar ao início.
     *
     * @param p              número de porquinhos a serem posicionados
     * @param g              número de galinhas a serem posicionadas
     * @param tabuleiro      tabuleiro onde os porquinhos e galinhas serão posicionados
     * @param numeroSolucoes número de soluções válidas encontradas até o momento
     * @param linha          linha atual
     * @param coluna         coluna atual
     */
    private long posicionarPorquinhos(int p, int g, char[][] tabuleiro, long numeroSolucoes, int linha, int coluna) {
        if (p == 0) {
            char[][] posicoes = posicoesLivres(tabuleiro);
            return posicionarGalinhas(g, tabuleiro, numeroSolucoes, posicoes, 0, 0);
        }

        for (int i = linha; i < tabuleiro.length; i++) {
            for (int j = coluna; j < tabuleiro.length; j++) {
                if (tabuleiro[i][j] == '.') {
                    tabuleiro[i][j] = 'P';
                    numeroSolucoes = posicionarPorquinhos(p - 1, g, tabuleiro, numeroSolucoes, i, j);
                    tabuleiro[i][j] = '.';
                }
            }
            coluna = 0;
        }
        return numeroSolucoes;
    }

    private char[][] posicoesLivres(char[][] tabuleiro) {
        var posicoes = new char[tabuleiro.length][tabuleiro.length];

        for (int i = 0; i < posicoes.length; i++) {
            for (int j = 0; j < posicoes.length; j++) {
                if (tabuleiro[i][j] == 'P') {
                    // preenche posicao
                    posicoes[i][j] = 'X';

                    //preenche linha
                    Arrays.fill(posicoes[i], 'X');

                    //preenche coluna
                    for (int k = 0; k < posicoes.length; k++) {
                        posicoes[k][j] = 'X';
                    }

                    if (i == j) {
                        //preenche diagonal principal
                        for (int k = 0; k < posicoes.length; k++) {
                            posicoes[k][k] = 'X';
                        }

                        // preenche diagonal secundária
                        int diff = Math.min(i, posicoes.length - 1 - j);
                        for (int k = j + diff, l = i - diff; k >= 0 && l <= posicoes.length - 1; k--, l++) {
                            posicoes[l][k] = 'X';
                        }
                    } else {
                        // preenche diagonal principal offset
                        int diff = Math.min(i, j);
                        for (int k = j - diff, l = i - diff; k <= posicoes.length - 1 && l <= posicoes.length - 1; k++, l++) {
                            posicoes[l][k] = 'X';
                        }
                        // preenche diagonal secundária offset
                        diff = Math.min(i, posicoes.length - 1 - j);
                        for (int k = j + diff, l = i - diff; k >= 0 && l <= posicoes.length - 1; k--, l++) {
                            posicoes[l][k] = 'X';
                        }
                    }
                }
            }
        }
        //printTabuleiro(posicoes);
        return posicoes;
    }

    private long posicionarGalinhas(int g, char[][] tabuleiro, long numeroSolucoes, char[][] posicoesLivres, int linha, int coluna) {
        if (g == 0) {
            numeroSolucoes++;
            //System.out.println(numeroSolucoes);
            //printTabuleiro(tabuleiro);
            return numeroSolucoes;
        }

        for (int i = linha; i < tabuleiro.length; i++) {
            for (int j = coluna; j < tabuleiro.length; j++) {
                if (tabuleiro[i][j] == '.' && posicoesLivres[i][j] != 'X') {
                    tabuleiro[i][j] = 'G';
                    numeroSolucoes = posicionarGalinhas(g - 1, tabuleiro, numeroSolucoes, posicoesLivres, i, j);
                    tabuleiro[i][j] = '.';
                }
            }
            coluna = 0;
        }
        return numeroSolucoes;
    }

    private char[][] novoTabuleiro(int n) {
        char[][] tabuleiro = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tabuleiro[i][j] = '.';
            }
        }
        return tabuleiro;
    }

    private void printTabuleiro(char[][] tabuleiro) {
        for (char[] chars : tabuleiro) {
            for (int j = 0; j < tabuleiro.length; j++) {
                System.out.print(chars[j] + " ");
            }
            System.out.println();
        }
    }
}

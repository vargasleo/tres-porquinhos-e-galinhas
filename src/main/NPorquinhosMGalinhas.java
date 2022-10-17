package main;

import java.math.BigInteger;
import java.util.Arrays;

public class NPorquinhosMGalinhas {

    public static void main(String[] args) {
        System.out.println(new NPorquinhosMGalinhas().run(args));
    }

    private long run(String[] args) {
        char[][] tabuleiro = novoTabuleiro(Integer.parseInt(args[0]));

        int min = Math.min(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        int max = Math.max(Integer.parseInt(args[1]), Integer.parseInt(args[2]));

        return posicionarPorquinhos(max, min, tabuleiro, 0, 0, 0);
    }

    private long posicionarPorquinhos(int p, int g, char[][] tabuleiro, long numeroSolucoes, int linha, int coluna) {
        if (p == 0) {
            return posicionarGalinhas(g, numeroSolucoes, posicoesLivres(tabuleiro));
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

    private int posicoesLivres(char[][] tabuleiro) {
        char[][] posicoes = new char[tabuleiro.length][tabuleiro.length];
        for (int i = 0; i < posicoes.length; i++) {
            for (int j = 0; j < posicoes.length; j++) {
                if (tabuleiro[i][j] == 'P') {
                    Arrays.fill(posicoes[i], 'X');
                    for (int k = 0; k < posicoes.length; k++) posicoes[k][j] = 'X';
                    int diff = Math.min(i, j); // diagonal principal
                    for (int k = j - diff, l = i - diff; k <= posicoes.length - 1 && l <= posicoes.length - 1; k++, l++) {
                        posicoes[l][k] = 'X';
                    }
                    diff = Math.min(i, posicoes.length - 1 - j); // diagonal secundária
                    for (int k = j + diff, l = i - diff; k >= 0 && l <= posicoes.length - 1; k--, l++) {
                        posicoes[l][k] = 'X';
                    }
                }
            }
        }
        int ocupados = 0;
        for (char[] posicao : posicoes) {
            for (int j = 0; j < posicoes.length; j++) if (posicao[j] == 'X') ocupados++;
        }
        return tabuleiro.length * tabuleiro.length - ocupados;
    }

    /**
     * Quantas formas diferentes de posicionar G galinhas em N posições livres?
     * N! / ((N - g)! * g!)
     */
    private long posicionarGalinhas(int g, long numeroSolucoes, int posicoesLivres) {
        if (g > posicoesLivres) return numeroSolucoes;
        numeroSolucoes +=
                fatorial(BigInteger.valueOf(posicoesLivres))
                        .divide(fatorial(BigInteger.valueOf(g)).multiply(fatorial(BigInteger.valueOf(posicoesLivres - g))))
                        .longValueExact();
        return numeroSolucoes;
    }

    private BigInteger fatorial(BigInteger n) {
        if (BigInteger.ZERO.equals(n)) return BigInteger.ONE;
        return n.multiply(fatorial(n.subtract(BigInteger.ONE)));
    }

    private char[][] novoTabuleiro(int n) {
        char[][] tabuleiro = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) tabuleiro[i][j] = '.';
        }
        return tabuleiro;
    }
}

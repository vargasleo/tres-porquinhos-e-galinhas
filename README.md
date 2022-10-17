Execução:
```bash
javac NPorquinhosMGalinhas.java
java NPorquinhosMGalinhas.java <n> <p> <g>
```

```
n: Dimensões do tabuleiro
p: Número de porquinhos
g: Número de galinhas
```

**Algoritmo**
1. Posiciona P recursivamente.
2. Define posições ocupadas
3. Soma posições livres
4. Calcula quantidade de posições das galinhas 

**Fórmula para calcular as galinhas**
```
N!/((N - g)! * g!) sendo N o número de posições livres
```

_Obs:Posicionar p é muito mais lento que calcular g, então, pra cada entrada:_

**p = min(p,g), g = max(p,g)**
package grafo.algoritmos;

import java.util.HashMap;
import java.util.Map;

import grafo.Vertice;
import grafo.nao_orientado.GrafoNaoDirecionado;

public class DFS {
    private enum Cor {
        BRANCO, CINZA, PRETO
    };

    private final GrafoNaoDirecionado grafo;
    private final Map<Vertice, Cor> cores = new HashMap<>();
    private final Map<Vertice, Integer> descobertos = new HashMap<>();
    private final Map<Vertice, Integer> finalizados = new HashMap<>();
    private final Map<Vertice, Vertice> antecessores = new HashMap<>();
    private Resultado resultado;
    int tempo = 0;

    public DFS(GrafoNaoDirecionado grafo) {
        this.grafo = grafo;
        grafo.getVertices().forEach(vertice -> cores.put(vertice, Cor.BRANCO));
        for (Map.Entry<Vertice, Cor> entry : cores.entrySet()) {
            Vertice vertice = entry.getKey();
            Cor cor = entry.getValue();
            if (cor.equals(Cor.BRANCO))
                dfsVisit(vertice);
        }
        resultado = new Resultado(
                Map.copyOf(cores),
                Map.copyOf(descobertos),
                Map.copyOf(finalizados),
                Map.copyOf(antecessores));
    }

    private void dfsVisit(Vertice vertice) {
        cores.put(vertice, Cor.CINZA);
        descobertos.put(vertice, ++tempo);
        for (Vertice vizinho : grafo.getAdjacentes(vertice)) {
            if (cores.get(vizinho).equals(Cor.BRANCO)) {
                antecessores.put(vizinho, vertice);
                dfsVisit(vizinho);
            }
        }
        cores.put(vertice, Cor.PRETO);
        finalizados.put(vertice, ++tempo);
    }

    public Resultado getResultado() {
        return resultado;
    }

    public record Resultado(
            Map<Vertice, Cor> cores,
            Map<Vertice, Integer> descobertos,
            Map<Vertice, Integer> finalizados,
            Map<Vertice, Vertice> antecessores) {
    }
}

package grafo.algoritmos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import grafo.Grafo;
import grafo.Vertice;

public class DFS {
    private enum Cor {
        BRANCO, CINZA, PRETO
    };

    private final Grafo grafo;
    private final Map<Vertice, Cor> cores = new HashMap<>();
    private final Map<Vertice, Integer> descobertos = new HashMap<>();
    private final Map<Vertice, Integer> finalizados = new HashMap<>();
    private final Map<Vertice, Vertice> antecessores = new HashMap<>();
    private final List<List<Vertice>> ciclos = new ArrayList<>();
    private Resultado resultado;
    int tempo = 0;

    public DFS(Grafo grafo) {
        this.grafo = grafo;
        grafo.getVertices().forEach(vertice -> cores.put(vertice, Cor.BRANCO));
        for (Map.Entry<Vertice, Cor> entry : cores.entrySet()) {
            Vertice vertice = entry.getKey();
            Cor cor = entry.getValue();
            if (cor.equals(Cor.BRANCO))
                dfsVisit(vertice, ImmutableList.of());
        }
        resultado = new Resultado(
                ImmutableMap.copyOf(cores),
                ImmutableMap.copyOf(descobertos),
                ImmutableMap.copyOf(finalizados),
                ImmutableMap.copyOf(antecessores));
    }

    private void dfsVisit(Vertice vertice, final ImmutableList<Vertice> caminho) {
        ImmutableList<Vertice> novoCaminho = ImmutableList
                .<Vertice>builderWithExpectedSize(caminho.size() + 1)
                .addAll(caminho)
                .add(vertice)
                .build();
        cores.put(vertice, Cor.CINZA);
        descobertos.put(vertice, ++tempo);
        for (Vertice vizinho : grafo.getAdjacentes(vertice)) {
            if (vizinho.equals(caminho.getFirst()) && caminho.size() > 1) {
                ciclos.add(caminho);
            }
            if (cores.get(vizinho).equals(Cor.BRANCO)) {
                antecessores.put(vizinho, vertice);
                dfsVisit(vizinho, novoCaminho);
            }
        }
        cores.put(vertice, Cor.PRETO);
        finalizados.put(vertice, ++tempo);
    }

    public Resultado getResultado() {
        return resultado;
    }

    public record Resultado(
            ImmutableMap<Vertice, Cor> cores,
            ImmutableMap<Vertice, Integer> descobertos,
            ImmutableMap<Vertice, Integer> finalizados,
            ImmutableMap<Vertice, Vertice> antecessores) {
    }
}

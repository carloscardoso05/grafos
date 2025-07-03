package grafo.algoritmos;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import com.google.common.collect.ImmutableMap;

import grafo.Grafo;
import grafo.Vertice;

public class BFS {
    private enum Cor {
        BRANCO, CINZA, PRETO
    }

    private final Grafo grafo;
    private final Map<Vertice, Cor> cores = new HashMap<>();
    private final Map<Vertice, Integer> descobertos = new HashMap<>();
    private final Map<Vertice, Vertice> antecessores = new HashMap<>();
    private final Map<Vertice, Integer> distancias = new HashMap<>();
    private Resultado resultado;

    public BFS(Grafo grafo, Vertice origem) {
        this.grafo = grafo;
        grafo.getVertices().forEach(vertice -> {
            cores.put(vertice, Cor.BRANCO);
            distancias.put(vertice, Integer.MAX_VALUE);
            antecessores.put(vertice, null);
        });
        
        bfsVisit(origem);
        
        resultado = new Resultado(
                ImmutableMap.copyOf(cores),
                ImmutableMap.copyOf(descobertos),
                ImmutableMap.copyOf(antecessores),
                ImmutableMap.copyOf(distancias));
    }

    private void bfsVisit(Vertice origem) {
        Queue<Vertice> fila = new ArrayDeque<>();
        int tempo = 0;
        
        cores.put(origem, Cor.CINZA);
        distancias.put(origem, 0);
        descobertos.put(origem, ++tempo);
        fila.offer(origem);
        
        while (!fila.isEmpty()) {
            Vertice atual = fila.poll();
            
            for (Vertice vizinho : grafo.getAdjacentes(atual)) {
                if (cores.get(vizinho).equals(Cor.BRANCO)) {
                    cores.put(vizinho, Cor.CINZA);
                    distancias.put(vizinho, distancias.get(atual) + 1);
                    antecessores.put(vizinho, atual);
                    descobertos.put(vizinho, ++tempo);
                    fila.offer(vizinho);
                }
            }
            
            cores.put(atual, Cor.PRETO);
        }
    }

    public Resultado getResultado() {
        return resultado;
    }

    public record Resultado(
            ImmutableMap<Vertice, Cor> cores,
            ImmutableMap<Vertice, Integer> descobertos,
            ImmutableMap<Vertice, Vertice> antecessores,
            ImmutableMap<Vertice, Integer> distancias) {
    }
}

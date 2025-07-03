package grafo.algoritmos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
        
        // Filtra os antecessores para remover valores nulos antes de criar o ImmutableMap
        Map<Vertice, Vertice> antecessoresFiltrados = new HashMap<>();
        antecessores.entrySet().stream()
            .filter(entry -> entry.getValue() != null)
            .forEach(entry -> antecessoresFiltrados.put(entry.getKey(), entry.getValue()));
        
        resultado = new Resultado(
                ImmutableMap.copyOf(cores),
                ImmutableMap.copyOf(descobertos),
                ImmutableMap.copyOf(antecessoresFiltrados),
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

    /**
     * Retorna a distância (número de arestas) entre a origem e o vértice de destino.
     * 
     * @param destino o vértice de destino
     * @return a distância entre origem e destino, ou -1 se não houver caminho
     */
    public int getDistanciaEntre(Vertice destino) {
        Integer distancia = distancias.get(destino);
        return (distancia == null || distancia == Integer.MAX_VALUE) ? -1 : distancia;
    }

    /**
     * Reconstrói o caminho da origem até o vértice de destino.
     * 
     * @param destino o vértice de destino
     * @return uma lista com o caminho da origem ao destino, ou lista vazia se não houver caminho
     */
    public List<Vertice> getCaminhoAte(Vertice destino) {
        if (getDistanciaEntre(destino) == -1) {
            return Collections.emptyList();
        }

        List<Vertice> caminho = new ArrayList<>();
        Vertice atual = destino;
        
        while (atual != null) {
            caminho.add(atual);
            atual = antecessores.get(atual);
        }
        
        Collections.reverse(caminho);
        return caminho;
    }

    /**
     * Verifica se existe um caminho da origem até o vértice de destino.
     * 
     * @param destino o vértice de destino
     * @return true se existe caminho, false caso contrário
     */
    public boolean existeCaminhoAte(Vertice destino) {
        return getDistanciaEntre(destino) != -1;
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

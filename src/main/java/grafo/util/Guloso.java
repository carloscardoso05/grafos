package grafo.util;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.LinkedHashSet;
import java.util.SequencedSet;

import grafo.Aresta;
import grafo.Vertice;
import grafo.nao_orientado.GrafoNaoDirecionado;

public class Guloso {
    private final GrafoNaoDirecionado grafo;
    private final Vertice verticeInicial;
    private final SequencedSet<Aresta> caminhoHamiltoniano = new LinkedHashSet<>();
    private final SequencedSet<Vertice> H = new LinkedHashSet<>();
    private Vertice verticeAtual;

    public Guloso(GrafoNaoDirecionado grafo, Vertice verticeInicial) {
        checkNotNull(grafo, "Grafo não pode ser nulo");
        checkNotNull(verticeInicial, "Vértice inicial não pode ser nulo");
        checkArgument(grafo.existeVertice(verticeInicial), "Vértice inicial existe no grafo");
        checkArgument(grafo.ehConexo(), "Grafo deve ser conexo para aplicar o algoritmo de Fleury");
        this.grafo = grafo;
        this.verticeInicial = verticeInicial;
        rodar();
    }

    public void rodar() {
        verticeAtual = verticeInicial;
        int qtdVertices = grafo.getVertices().size();
        while (H.size() < qtdVertices) {
            H.add(verticeAtual);
            Vertice vizinho = escolheVizinho();
            caminhoHamiltoniano.add(grafo.encontrarAresta(verticeAtual, vizinho));
            verticeAtual = vizinho;
        }
    }

    private Vertice escolheVizinho() {
        return grafo.getAdjacentes(verticeAtual)
                .stream()
                .filter(vertice -> !H.contains(vertice))
                .flatMap(vizinho -> grafo.encontrarArestas(verticeAtual, vizinho).stream())
                .min((aresta1, aresta2) -> Double.compare(aresta1.peso(), aresta2.peso()))
                .map(aresta -> aresta.origem() == verticeAtual ? aresta.destino() : aresta.origem())
                .orElse(null);
    }
}

package grafo.algoritmos;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedSet;

import grafo.Aresta;
import grafo.Vertice;
import grafo.nao_orientado.GrafoNaoDirecionado;

public class Fleury {
    private final GrafoNaoDirecionado grafo;
    private final Vertice verticeInicial;
    private final SequencedSet<Aresta> caminhoEuleriano = new LinkedHashSet<>();
    private Vertice verticeAtual;

    public List<Aresta> getCaminhoEuleriano() {
        return List.copyOf(caminhoEuleriano);
    }

    public Fleury(GrafoNaoDirecionado grafo, Vertice verticeInicial) {
        checkNotNull(grafo, "Grafo não pode ser nulo");
        checkNotNull(verticeInicial, "Vértice inicial não pode ser nulo");
        checkArgument(grafo.existeVertice(verticeInicial), "Vértice inicial existe no grafo");
        checkArgument(grafo.ehEuleriano(), "Grafo deve ser Euleriano para aplicar o algoritmo de Fleury");
        checkArgument(grafo.ehConexo(), "Grafo deve ser conexo para aplicar o algoritmo de Fleury");
        this.grafo = grafo;
        this.verticeInicial = verticeInicial;
        rodar();
    }

    private void rodar() {
        verticeAtual = verticeInicial;
        int qtdArestas = grafo.getArestas().size();
        while (caminhoEuleriano.size() < qtdArestas) {
            Aresta aresta = escolheAresta();
            verticeAtual = verticeAtual != aresta.destino() ? aresta.destino() : aresta.origem();
            caminhoEuleriano.add(aresta);
        }
    }

    private Aresta escolheAresta() {
        var vizinhos = grafo.getAdjacentes(verticeAtual);
        if (vizinhos.isEmpty()) {
            return null;
        }
        var arestasCandidatas = vizinhos
                .stream()
                .flatMap(v -> grafo.encontrarArestas(verticeAtual, v).stream())
                .filter(aresta -> !caminhoEuleriano.contains(aresta) && !grafo.ehPonte(aresta));

        return arestasCandidatas.findFirst().orElse(null);
    }
}

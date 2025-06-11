package grafo;

import static com.google.common.base.Preconditions.checkNotNull;

public record Vertice(String label) {
    public Vertice {
        checkNotNull(label, "Rótulo do vértice não pode ser nulo");
    }

    public Aresta formarAresta(String label, Vertice destino) {
        checkNotNull(label, "Rótulo da aresta não pode ser nulo");
        return new Aresta(label, this, destino);
    }
}

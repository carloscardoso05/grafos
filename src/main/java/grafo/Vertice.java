package grafo;

import grafo.util.Assert;

public record Vertice(int id) {
    public Vertice {
        Assert.positive(id, "ID do vértice deve ser positivo");
    }

    public Aresta formarAresta(Vertice destino) {
        Assert.notNull(destino, "Destino da aresta não pode ser nulo");
        return new Aresta(this, destino);
    }
}

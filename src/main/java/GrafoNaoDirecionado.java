public abstract class GrafoNaoDirecionado extends Grafo {
    public int getGrau(Vertice vertice) {
        Assert.notNull(vertice, "O vértice não pode ser nulo.");
        return getVizinhos(vertice).size();
    }

    protected abstract void addArestaInternal(Aresta aresta);

    @Override
    public final void addAresta(Aresta aresta) {
        addArestaInternal(aresta);
        addArestaInternal(aresta.inversa());
    }

    public abstract void removeArestaInternal(Aresta aresta);

    @Override
    public final void removeAresta(Aresta aresta) {
        removeArestaInternal(aresta);
        removeArestaInternal(aresta.inversa());
    }

    @Override
    public final int getGrauDeEntrada(Vertice vertice) {
        return getGrau(vertice);
    }

    @Override
    public final int getGrauDeSaida(Vertice vertice) {
        return getGrau(vertice);
    }
}

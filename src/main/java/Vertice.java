public record Vertice(String label) {
    public Vertice {
        Assert.notBlank(label, "Label do vértice não pode ser nulo ou vazio");
    }

    public Aresta formarAresta(Vertice destino) {
        Assert.notNull(destino, "Destino da aresta não pode ser nulo");
        return new Aresta(this, destino);
    }
}

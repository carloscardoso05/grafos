public record Aresta(Vertice origem, Vertice destino, double peso) {
	public Aresta(Vertice origem, Vertice destino) {
		this(origem, destino, 0);
	}

	public Aresta {
		Assert.notNull(origem);
		Assert.notNull(destino);
	}
}

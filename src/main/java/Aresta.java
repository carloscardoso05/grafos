public record Aresta(Vertice origem, Vertice destino, double peso) {
	public Aresta(Vertice origem, Vertice destino) {
		this(origem, destino, 0);
	}

	public Aresta {
		Assert.notNull(origem, "Aresta não pode ter origem nula");
		Assert.notNull(destino, "Aresta não pode ter destino nulo");
	}

	public Aresta inversa() {
		return new Aresta(destino, origem, peso);
	}
}

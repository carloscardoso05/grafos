import java.util.List;

public abstract class GrafoNaoDirecionado extends Grafo {
	public int getGrau(Vertice vertice) {
		Assert.notNull(vertice, "O vértice não pode ser nulo.");
		List<Aresta> arestas = getArestas(vertice);
		int qtdArestas = arestas.size();
		int qtdLoops = (int) arestas
				.stream()
				.filter(a -> a.origem().equals(a.destino()))
				.count();
		return qtdArestas + qtdLoops;
	}

	@Override
	public final int getGrauDeEntrada(Vertice vertice) {
		return getGrau(vertice);
	}

	@Override
	public final int getGrauDeSaida(Vertice vertice) {
		return getGrau(vertice);
	}

	@Override
	public int getQuantidadeDeArestas(Aresta aresta) {
		Assert.notNull(aresta, MSG_ARESTA_NULA);
		return (int) getArestas().stream()
				.filter(a -> a.equals(aresta) || a.inversa().equals(aresta))
				.count();
	}

	public GrafoNaoDirecionado unir(GrafoNaoDirecionado outroGrafo) {
		Assert.notNull(outroGrafo, "O grafo a ser unido não pode ser nulo.");

		Grafo grafoUnido = this.clone();
		grafoUnido.addVertices(outroGrafo.getVertices().toArray(Vertice[]::new));
		grafoUnido.addArestas(outroGrafo.getArestas().toArray(Aresta[]::new));
		return (GrafoNaoDirecionado) grafoUnido;
	}
}

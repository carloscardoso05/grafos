import java.util.List;
import java.util.Set;

public abstract class Grafo {
	protected static final String MSG_VERTICE_NULO = "Vértice não pode ser nulo";
	protected static final String MSG_ARESTA_NULA = "Aresta não pode ser nula";

	public abstract void addAresta(Aresta aresta);

	public void addArestas(Aresta... arestas) {
		for (Aresta aresta : arestas) {
			addAresta(aresta);
		}
	}

	public abstract void removeAresta(Aresta aresta);

	public void removeArestas(Aresta... arestas) {
		for (Aresta aresta : arestas) {
			removeAresta(aresta);
		}
	}

	public void removeTodasArestas(Aresta aresta) {
		Assert.notNull(aresta, MSG_ARESTA_NULA);

		while (existeAresta(aresta)) {
			removeAresta(aresta);
		}
	}

	public abstract void addVertice(Vertice vertice);

	public void addVertices(Vertice... vertices) {
		for (Vertice vertice : vertices) {
			addVertice(vertice);
		}
	}

	public abstract void removeVertice(Vertice vertice);

	public void removeVertices(Vertice... vertices) {
		for (Vertice vertice : vertices) {
			removeVertice(vertice);
		}
	}

	public abstract boolean existeAresta(Aresta aresta);

	public int getQuantidadeDeArestas(Aresta aresta) {
		return (int) getArestas().stream().filter(aresta::equals).count();
	}

	public abstract boolean existeVertice(Vertice vertice);

	public abstract List<Aresta> getArestas();

	public abstract List<Aresta> getArestas(Vertice vertice);

	public abstract Set<Vertice> getVertices();

	public abstract Set<Vertice> getVizinhos(Vertice vertice);

	public abstract int getGrauDeEntrada(Vertice vertice);

	public abstract int getGrauDeSaida(Vertice vertice);

	public void resetar() {
		for (Vertice vertice : Set.copyOf(getVertices())) {
			removeVertice(vertice);
		}
	}
}

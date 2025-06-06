import java.util.*;

public class GrafoNaoDirecionadoPorLista extends GrafoNaoDirecionado {
	private final Map<Vertice, List<Vertice>> verticesAdjacencias = new HashMap<>();

	@Override
	public void addArestaInternal(Aresta aresta) {
		Assert.notNull(aresta, MSG_ARESTA_NULA);

		Vertice origem = aresta.origem();
		Vertice destino = aresta.destino();

		if (!verticesAdjacencias.containsKey(origem)) {
			verticesAdjacencias.put(origem, new ArrayList<>());
		}

		verticesAdjacencias.get(origem).add(destino);
	}

	@Override
	public void removeArestaInternal(Aresta aresta) {
		Assert.notNull(aresta, MSG_ARESTA_NULA);

		Vertice origem = aresta.origem();
		Vertice destino = aresta.destino();
		if (verticesAdjacencias.containsKey(origem)) {
			List<Vertice> vizinhos = verticesAdjacencias.get(origem);
			vizinhos.remove(destino);
		}
	}

	@Override
	public void addVertice(Vertice vertice) {
		Assert.notNull(vertice, MSG_VERTICE_NULO);

		if (!verticesAdjacencias.containsKey(vertice)) {
			verticesAdjacencias.put(vertice, new ArrayList<>());
		}
	}

	@Override
	public void removeVertice(Vertice vertice) {
		Assert.notNull(vertice, MSG_VERTICE_NULO);

		verticesAdjacencias.remove(vertice);

		for (List<Vertice> vizinhos : verticesAdjacencias.values()) {
			vizinhos.remove(vertice);
		}
	}

	@Override
	public boolean existeAresta(Aresta aresta) {
		Assert.notNull(aresta, MSG_ARESTA_NULA);

		Vertice origem = aresta.origem();
		Vertice destino = aresta.destino();

		return existeVertice(origem) && getVizinhos(origem).contains(destino);
	}

	@Override
	public boolean existeVertice(Vertice vertice) {
		Assert.notNull(vertice, MSG_VERTICE_NULO);

		return verticesAdjacencias.containsKey(vertice);
	}

	@Override
	public List<Aresta> getArestas() {
		return verticesAdjacencias.keySet().stream().map(this::getArestas).flatMap(List::stream).toList();
	}

	@Override
	public List<Aresta> getArestas(Vertice vertice) {
		Assert.notNull(vertice, MSG_VERTICE_NULO);

		return verticesAdjacencias.get(vertice).stream().map(vizinho -> new Aresta(vertice, vizinho)).toList();
	}

	@Override
	public Set<Vertice> getVertices() {
		return verticesAdjacencias.keySet();
	}

	@Override
	public Set<Vertice> getVizinhos(Vertice vertice) {
		return new HashSet<>(verticesAdjacencias.get(vertice));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Grafo não direcionado:\n");
		for (Map.Entry<Vertice, List<Vertice>> entry : verticesAdjacencias.entrySet()) {
			sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
		}
		return sb.toString();
	}
}

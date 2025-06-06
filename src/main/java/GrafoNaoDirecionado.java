import java.util.*;

public class GrafoNaoDirecionado extends Grafo {
	private final Map<Vertice, List<Vertice>> adjacencias = new HashMap<>();

	@Override
	public void addAresta(Aresta aresta) {
		Assert.notNull(aresta);

		Vertice origem = aresta.origem();
		Vertice destino = aresta.destino();

		if (!adjacencias.containsKey(origem)) {
			adjacencias.put(origem, new ArrayList<>());
		}

		adjacencias.get(origem).add(destino);
	}

	@Override
	public void removeAresta(Aresta aresta) {
		Assert.notNull(aresta);

		Vertice origem = aresta.origem();
		Vertice destino = aresta.destino();
		if (adjacencias.containsKey(origem)) {
			List<Vertice> vizinhos = adjacencias.get(origem);
			vizinhos.remove(destino);
		}
	}

	@Override
	public void addVertice(Vertice vertice) {
		Assert.notNull(vertice);

		if (!adjacencias.containsKey(vertice)) {
			adjacencias.put(vertice, new ArrayList<>());
		}
	}

	@Override
	public void removeVertice(Vertice vertice) {
		Assert.notNull(vertice);

		adjacencias.remove(vertice);

		for (List<Vertice> vizinhos : adjacencias.values()) {
			vizinhos.remove(vertice);
		}
	}

	@Override
	public boolean existeAresta(Aresta aresta) {
		Assert.notNull(aresta);

		Vertice origem = aresta.origem();
		Vertice destino = aresta.destino();

		return adjacencias.containsKey(origem) && adjacencias.get(origem).contains(destino);
	}

	@Override
	public boolean existeVertice(Vertice vertice) {
		Assert.notNull(vertice);

		return adjacencias.containsKey(vertice);
	}

	@Override
	public int grau(Vertice vertice) {
		Assert.notNull(vertice);

		return adjacencias.get(vertice).size();
	}

	@Override
	public List<Aresta> getArestas() {
		return adjacencias.keySet().stream().map(this::getArestas).flatMap(List::stream).toList();
	}

	@Override
	public List<Aresta> getArestas(Vertice vertice) {
		Assert.notNull(vertice);

		return adjacencias.get(vertice).stream().map(vizinho -> new Aresta(vertice, vizinho)).toList();
	}

	@Override
	public Set<Vertice> getVertices() {
		return adjacencias.keySet();
	}

	@Override
	public Set<Vertice> getVizinhos(Vertice vertice) {
		return new HashSet<>(adjacencias.get(vertice));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Grafo não direcionado:\n");
		for (Map.Entry<Vertice, List<Vertice>> entry : adjacencias.entrySet()) {
			sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
		}
		return sb.toString();
	}
}

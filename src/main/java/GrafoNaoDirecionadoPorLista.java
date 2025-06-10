import java.util.*;
import java.util.stream.Collectors;

public class GrafoNaoDirecionadoPorLista extends GrafoNaoDirecionado {
	private int verticeCounter = 1;
	private final Map<Vertice, List<Vertice>> verticesAdjacencias = new HashMap<>();

	@Override
	public void addAresta(Aresta aresta) {
		Assert.notNull(aresta, MSG_ARESTA_NULA);

		Vertice origem = aresta.origem();
		Vertice destino = aresta.destino();

		addVertice(origem);
		addVertice(destino);

		verticesAdjacencias.get(origem).add(destino);
	}

	@Override
	public void removeAresta(Aresta aresta) {
		Assert.notNull(aresta, MSG_ARESTA_NULA);

		Vertice origem = aresta.origem();
		Vertice destino = aresta.destino();
		if (verticesAdjacencias.containsKey(origem)) {
			List<Vertice> vizinhos = verticesAdjacencias.get(origem);
			vizinhos.remove(destino);
			return;
		}
		if (verticesAdjacencias.containsKey(destino)) {
			List<Vertice> vizinhos = verticesAdjacencias.get(destino);
			vizinhos.remove(origem);
		}
	}

	@Override
	public Vertice addVertice() {
		Vertice novoVertice = new Vertice(verticeCounter++);
		verticesAdjacencias.putIfAbsent(novoVertice, new ArrayList<>());
		return novoVertice;
	}

	@Override
	protected void addVertice(Vertice vertice) {
		Assert.notNull(vertice, MSG_ARESTA_NULA);
		if (existeVertice(vertice))
			return;
		verticesAdjacencias.put(vertice, new ArrayList<>());
		verticeCounter++;
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

		boolean verticesExistem = existeVertice(origem) && existeVertice(destino);
		boolean arestaExiste = verticesAdjacencias.get(origem).contains(destino);
		boolean arestaInversaExiste = verticesAdjacencias.get(destino).contains(origem);
		return verticesExistem && (arestaExiste || arestaInversaExiste);
	}

	@Override
	public boolean existeVertice(Vertice vertice) {
		Assert.notNull(vertice, MSG_VERTICE_NULO);

		return verticesAdjacencias.containsKey(vertice);
	}

	@Override
	public List<Aresta> getArestas() {
		return verticesAdjacencias.entrySet().stream().flatMap(entry -> {
			Vertice origem = entry.getKey();
			List<Vertice> destinos = entry.getValue();
			return destinos.stream().map(origem::formarAresta);
		}).toList();
	}

	@Override
	public List<Aresta> getArestas(Vertice vertice) {
		Assert.notNull(vertice, MSG_VERTICE_NULO);

		return getArestas()
				.stream()
				.filter(aresta -> aresta.origem().equals(vertice) || aresta.destino().equals(vertice))
				.toList();
	}

	@Override
	public Set<Vertice> getVertices() {
		return verticesAdjacencias.keySet();
	}

	@Override
	public Set<Vertice> getVizinhos(Vertice vertice) {
		return getArestas(vertice)
				.stream()
				.map(aresta -> aresta.origem().equals(vertice) ? aresta.destino() : aresta.origem())
				.collect(Collectors.toSet());
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

	@Override
	public Grafo clone() {
		GrafoNaoDirecionadoPorLista clone = new GrafoNaoDirecionadoPorLista();
		for (Vertice vertice : verticesAdjacencias.keySet()) {
			clone.addVertice(vertice);
		}
		for (Aresta aresta : getArestas()) {
			clone.addAresta(aresta);
		}
		return clone;
	}
}

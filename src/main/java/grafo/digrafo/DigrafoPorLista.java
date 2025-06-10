package grafo.digrafo;
import java.util.*;

import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;
import grafo.util.Assert;

public class DigrafoPorLista extends Digrafo {
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
		return getVizinhos(aresta.origem()).contains(aresta.destino());
	}

	@Override
	public boolean existeVertice(Vertice vertice) {
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
		Assert.notNull(vertice, MSG_VERTICE_NULO);

		return new HashSet<>(verticesAdjacencias.get(vertice));
	}

	@Override
	public int getGrauDeEntrada(Vertice vertice) {
		Assert.notNull(vertice, MSG_VERTICE_NULO);

		return (int) verticesAdjacencias.values().stream()
				.flatMap(Collection::stream)
				.filter(vertice::equals)
				.count();
	}

	@Override
	public int getGrauDeSaida(Vertice vertice) {
		Assert.notNull(vertice, MSG_VERTICE_NULO);

		return getVizinhos(vertice).size();
	}

	@Override
	public Grafo clone() {
		DigrafoPorLista clone = new DigrafoPorLista();
		for (Vertice vertice : verticesAdjacencias.keySet()) {
			clone.addVertice(vertice);
		}
		for (Aresta aresta : getArestas()) {
			clone.addAresta(aresta);
		}
		return clone;
	}
}

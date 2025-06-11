package grafo.digrafo;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.*;
import java.util.stream.Collectors;

import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;

public class DigrafoPorLista extends Digrafo {
	private final Map<Vertice, List<Aresta>> verticesAdjacencias = new HashMap<>();

	@Override
	public void addAresta(Aresta aresta) {
		checkNotNull(aresta, MSG_ARESTA_NULA);

		Vertice origem = aresta.origem();
		Vertice destino = aresta.destino();

		addVertice(origem);
		addVertice(destino);

		verticesAdjacencias.get(origem).add(aresta);
	}


	@Override
	public void addVertice(Vertice vertice) {
		checkNotNull(vertice, MSG_VERTICE_NULO);
		verticesAdjacencias.putIfAbsent(vertice, new ArrayList<>());
	}

	@Override
	public void removeVertice(Vertice vertice) {
		checkNotNull(vertice, MSG_VERTICE_NULO);

		verticesAdjacencias.remove(vertice);

		for (List<Aresta> adjacencias : verticesAdjacencias.values()) {
			adjacencias.removeIf(aresta -> aresta.origem().equals(vertice) || aresta.destino().equals(vertice));
		}
	}

	@Override
	public Set<Aresta> getArestas() {
		return verticesAdjacencias.values().stream()
				.flatMap(List::stream)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Vertice> getVertices() {
		return verticesAdjacencias.keySet();
	}


	@Override
	public void removeAresta(String label) {
		checkNotNull(label, MSG_ARESTA_NULA);

		for (List<Aresta> adjacencias : verticesAdjacencias.values()) {
			adjacencias.removeIf(aresta -> aresta.label().equals(label));
		}
	}


	@Override
	public Set<Aresta> encontrarArestas(Vertice origem, Vertice destino) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'encontrarArestas'");
	}


	@Override
	protected Grafo novaInstancia() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'novaInstancia'");
	}
}

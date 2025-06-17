package grafo.nao_orientado;

import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class GrafoNaoDirecionadoPorMatriz extends GrafoNaoDirecionado {

	private final Map<Vertice, Map<Vertice, Set<Aresta>>> matriz = new HashMap<>();

	@Override
	public void addAresta(Aresta aresta) {
		checkNotNull(aresta, MSG_ARESTA_NULA);
		checkArgument(!existeAresta(aresta.label()), MSG_ARESTA_EXISTE);

		Vertice origem = aresta.origem();
		Vertice destino = aresta.destino();

		addVertice(origem);
		addVertice(destino);

		matriz.get(origem).get(destino).add(aresta);
	}

	@Override
	public void removeAresta(String label) {
		matriz.values()
			  .stream()
			  .flatMap(linha -> linha.values().stream())
			  .forEach(adjacencias -> adjacencias.removeIf(adjacencia -> adjacencia.label().equals(label)));
	}

	@Override
	public Set<Aresta> encontrarArestas(Vertice origem, Vertice destino) {
		checkNotNull(origem, MSG_VERTICE_NULO);
		checkNotNull(destino, MSG_VERTICE_NULO);
		checkArgument(existeVertice(origem), MSG_VERTICE_NAO_EXISTE);
		checkArgument(existeVertice(destino), MSG_VERTICE_NAO_EXISTE);

		Set<Aresta> arestas = new HashSet<>();
		arestas.addAll(matriz.get(origem).get(destino));
		arestas.addAll(matriz.get(destino).get(origem));
		return arestas;
	}

	@Override
	public Vertice addVertice(Vertice vertice) {
		checkNotNull(vertice, MSG_VERTICE_NULO);
		if (existeVertice(vertice)) return vertice;

		matriz.putIfAbsent(vertice, new HashMap<>());
		matriz.keySet().forEach(outro -> matriz.get(vertice).putIfAbsent(outro, new HashSet<>()));
		matriz.values().forEach(linha -> linha.putIfAbsent(vertice, new HashSet<>()));
		return vertice;
	}

	@Override
	public void removeVertice(Vertice vertice) {
		checkNotNull(vertice, MSG_VERTICE_NULO);

		matriz.remove(vertice);
		getVertices().forEach(v -> matriz.get(v).remove(vertice));
	}

	@Override
	public Set<Aresta> getArestas() {
		return matriz
				.values()
				.stream()
				.flatMap(linha -> linha.values().stream()).flatMap(Set::stream)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Vertice> getVertices() {
		return matriz.keySet();
	}

	@Override
	protected Grafo novaInstancia() {
		return new GrafoNaoDirecionadoPorMatriz();
	}

}

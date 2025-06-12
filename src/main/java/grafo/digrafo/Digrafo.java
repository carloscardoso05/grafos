package grafo.digrafo;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;

public abstract class Digrafo extends Grafo {

	@Override
	public final long getGrauDeEntrada(Vertice vertice) {
		checkNotNull(vertice, MSG_VERTICE_NULO);
		checkArgument(existeVertice(vertice), MSG_VERTICE_NAO_EXISTE);

		return getArestas()
				.stream()
				.filter(aresta -> aresta.destino().equals(vertice))
				.count();
	}

	@Override
	public final long getGrauDeSaida(Vertice vertice) {
		checkNotNull(vertice, MSG_VERTICE_NULO);
		checkArgument(existeVertice(vertice), MSG_VERTICE_NAO_EXISTE);

		return getArestas()
				.stream()
				.filter(aresta -> aresta.origem().equals(vertice))
				.count();
	}

	@Override
	public final Aresta encontrarAresta(Vertice origem, Vertice destino) {
		checkNotNull(origem, MSG_VERTICE_NULO);
		checkNotNull(destino, MSG_VERTICE_NULO);

		return getArestas()
				.stream()
				.filter(aresta -> aresta.origem().equals(origem) && aresta.destino().equals(destino))
				.findFirst()
				.orElse(null);
	}
}
package grafo.digrafo;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
}
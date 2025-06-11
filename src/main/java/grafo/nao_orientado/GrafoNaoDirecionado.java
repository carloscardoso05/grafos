package grafo.nao_orientado;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import grafo.Grafo;
import grafo.Vertice;

public abstract class GrafoNaoDirecionado extends Grafo {
	public final long getGrau(Vertice vertice) {
		checkNotNull(vertice, MSG_VERTICE_NULO);
		return getArestas().stream()
				.flatMap(aresta -> List.of(aresta.destino(), aresta.origem()).stream())
				.filter(vertice::equals)
				.count();
	}

	@Override
	public final long getGrauDeEntrada(Vertice vertice) {
		checkNotNull(vertice, MSG_VERTICE_NULO);
		return getGrau(vertice);
	}

	@Override
	public final long getGrauDeSaida(Vertice vertice) {
		checkNotNull(vertice, MSG_VERTICE_NULO);
		return getGrau(vertice);
	}
}

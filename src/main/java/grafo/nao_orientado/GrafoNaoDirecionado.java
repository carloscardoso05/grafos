package grafo.nao_orientado;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import grafo.Aresta;
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

	@Override
	public final Aresta encontrarAresta(Vertice origem, Vertice destino) {
		checkNotNull(origem, MSG_VERTICE_NULO);
		checkNotNull(destino, MSG_VERTICE_NULO);

		return getArestas()
				.stream()
				.filter(aresta -> (aresta.origem().equals(origem) && aresta.destino().equals(destino)) ||
						(aresta.origem().equals(destino) && aresta.destino().equals(origem)))
				.findFirst()
				.orElse(null);
	}

	public final GrafoNaoDirecionado uniao(GrafoNaoDirecionado outroGrafo) {
		checkNotNull(outroGrafo, "Outro grafo não pode ser nulo");
		// TODO checar se são disjuntos

		GrafoNaoDirecionado grafoUnido = (GrafoNaoDirecionado) clonar();
		grafoUnido.addVertices(outroGrafo.getVertices().toArray(Vertice[]::new));
		grafoUnido.addArestas(outroGrafo.getArestas().toArray(Aresta[]::new));
		return grafoUnido;
	}
}

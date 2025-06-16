package grafo.nao_orientado;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	public final Set<Aresta> encontrarArestas(Vertice origem, Vertice destino) {
		return getArestas()
				.stream()
				.filter(aresta -> (aresta.origem().equals(origem) && aresta.destino().equals(destino)) ||
						(aresta.origem().equals(destino) && aresta.destino().equals(origem)))
				.collect(Collectors.toSet());
	}

	@Override
	public final Aresta encontrarAresta(Vertice origem, Vertice destino) {
		return encontrarArestas(origem, destino).stream().findFirst().orElse(null);
	}

	public final GrafoNaoDirecionado uniao(GrafoNaoDirecionado outroGrafo) {
		checkNotNull(outroGrafo, "Outro grafo não pode ser nulo");
		checkArgument(ehDisjunto(outroGrafo), "Os grafos devem ser disjuntos para serem unidos");

		GrafoNaoDirecionado grafoUnido = (GrafoNaoDirecionado) clonar();
		grafoUnido.addVertices(outroGrafo.getVertices().toArray(Vertice[]::new));
		grafoUnido.addArestas(outroGrafo.getArestas().toArray(Aresta[]::new));
		return grafoUnido;
	}

	public final boolean ehEuleriano() {
		return getVertices().stream().allMatch(vertice -> getGrau(vertice) % 2 == 0);
	}

	public final boolean ehSemiEuleriano() {
		long verticesImpares = getVertices().stream()
				.filter(vertice -> getGrau(vertice) % 2 != 0)
				.count();
		return verticesImpares == 0 || verticesImpares == 2;
	}

	public final GrafoNaoDirecionado intersecao(GrafoNaoDirecionado outroGrafo) {
		throw new UnsupportedOperationException("A interseção de grafos não direcionados ainda não está implementada");
		// checkNotNull(outroGrafo, "Outro grafo não pode ser nulo");

		// GrafoNaoDirecionado grafoIntersecao = (GrafoNaoDirecionado) novaInstancia();
		// Vertice[] vertices = getVertices().stream()
		// .filter(outroGrafo::existeVertice)
		// .toArray(Vertice[]::new);
		// grafoIntersecao.addVertices(vertices);
		// Aresta[] arestas = getArestas().stream()
		// .filter(aresta -> outroGrafo.existeAresta(aresta.origem(), aresta.destino()))
		// .toArray(Aresta[]::new);
		// return grafoIntersecao;
	}
}

package grafo;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import grafo.digrafo.Digrafo;
import grafo.nao_orientado.GrafoNaoDirecionado;

public abstract class Grafo {
	protected static final String MSG_VERTICE_NULO = "Vértice não pode ser nulo";
	protected static final String MSG_VERTICE_NAO_EXISTE = "Vértice não existe no grafo";
	protected static final String MSG_ARESTA_NULA = "Aresta não pode ser nula";
	protected static final String MSG_ARESTA_EXISTE = "Aresta já existe no grafo com o label informado";

	public abstract void addAresta(Aresta aresta);

	public final void addArestas(Aresta... arestas) {
		for (Aresta aresta : arestas) {
			addAresta(aresta);
		}
	}

	public abstract void removeAresta(String label);

	public final void removeArestas(String... labels) {
		for (String label : labels) {
			removeAresta(label);
		}
	}

	public final void removeAresta(Vertice origem, Vertice destino) {
		checkNotNull(origem, MSG_VERTICE_NULO);
		checkNotNull(destino, MSG_VERTICE_NULO);

		Aresta aresta = encontrarAresta(origem, destino);
		if (aresta != null) {
			removeAresta(aresta.label());
		}
	}

	public final void removeArestas(Vertice origem, Vertice destino) {
		checkNotNull(origem, MSG_VERTICE_NULO);
		checkNotNull(destino, MSG_VERTICE_NULO);

		String[] labels = encontrarArestas(origem, destino).stream()
				.map(Aresta::label)
				.toArray(String[]::new);
		removeArestas(labels);
	}

	/**
	 * Tenta encontrar uma aresta pelo seu label (rótulo).
	 * <br>
	 * Obs.: O label é o identificador único da aresta.
	 *
	 * @param label o rótulo da aresta
	 * @return a aresta com o label informado ou null se não existir
	 */
	public final Aresta encontrarAresta(String label) {
		checkNotNull(label, MSG_ARESTA_NULA);

		return getArestas()
				.stream()
				.filter(aresta -> aresta.label().equals(label))
				.findFirst()
				.orElse(null);
	}

	/**
	 * Tenta encontrar uma aresta entre dois vértices.
	 *
	 * @param origem
	 * @param destino
	 * @return a aresta entre os vértices origem e destino ou null se não existir
	 */
	public abstract Aresta encontrarAresta(Vertice origem, Vertice destino);

	/**
	 * Encontra todas as arestas entre o vértice origem e o vértice destino.
	 * <br>
	 * No caso de um grafo não orientado, são consideradas as arestas (origem ->
	 * destino) e também (destino -> origem).
	 * <br>
	 * No caso de um grafo orientado, são consideradas apenas as arestas (origem ->
	 * destino).
	 *
	 * @param origem
	 * @param destino
	 * @return um conjunto de arestas entre os vértices origem e destino
	 */
	public abstract Set<Aresta> encontrarArestas(Vertice origem, Vertice destino);

	@CanIgnoreReturnValue
	public abstract Vertice addVertice(Vertice vertice);

	@CanIgnoreReturnValue
	public final Vertice[] addVertices(Vertice... vertices) {
		for (Vertice vertice : vertices) {
			addVertice(vertice);
		}
		return vertices;
	}

	public abstract void removeVertice(Vertice vertice);

	public final void removeVertices(Vertice... vertices) {
		for (Vertice vertice : vertices) {
			removeVertice(vertice);
		}
	}

	/**
	 * Verifica se uma aresta com o label (rótulo) existe no grafo.
	 * <br>
	 * Obs.: O label é o identificador único da aresta.
	 *
	 * @param label o rótulo da aresta
	 * @return se a aresta existe no grafo
	 */
	public final boolean existeAresta(String label) {
		checkNotNull(label, MSG_ARESTA_NULA);
		return encontrarAresta(label) != null;
	}

	/**
	 * Verifica se existe alguma aresta entre os vértices origem e destino.
	 * <br>
	 * No caso de um grafo não orientado, a são consideradas as arestas (origem ->
	 * destino) e também (destino -> origem).
	 * <br>
	 * No caso de um grafo orientado, são consideradas apenas as arestas (origem ->
	 * destino).
	 *
	 * @param origem
	 * @param destino
	 * @return se existe uma aresta entre os vértices origem e destino
	 */
	public final boolean existeAresta(Vertice origem, Vertice destino) {
		checkNotNull(origem, MSG_VERTICE_NULO);
		checkNotNull(destino, MSG_VERTICE_NULO);

		return encontrarAresta(origem, destino) != null;
	}

	/**
	 * Verifica se existe um vértice no grafo.
	 *
	 * @param vertice
	 * @return se o vértice existe no grafo
	 */
	public final boolean existeVertice(Vertice vertice) {
		checkNotNull(vertice, MSG_VERTICE_NULO);
		return getVertices().contains(vertice);
	}

	public abstract Set<Aresta> getArestas();

	// public final abstract Set<Aresta> getArestas(Vertice vertice);

	/**
	 * Retorna um conjunto de arestas do grafo.
	 *
	 * @return conjunto de arestas do grafo
	 */
	public abstract Set<Vertice> getVertices();

	/**
	 * Retorna uma lista de vértices que podem ser alcançados a partir do vértice
	 * informado.
	 * <br>
	 * No caso de um vértice não orientado, serão consideradas as arestas (vertice
	 * -> vizinho) e também (vizinho -> vertice).
	 * <br>
	 * No caso de um vértice orientado, serão consideradas apenas as arestas
	 * (vertice -> vizinho).
	 *
	 * @param vertice
	 * @return lista de vértices vizinhos do vértice informado
	 */
	public final Set<Vertice> getVizinhos(Vertice vertice) {
		checkNotNull(vertice, MSG_VERTICE_NULO);
		checkArgument(existeVertice(vertice), MSG_VERTICE_NAO_EXISTE);

		return getVertices()
				.stream()
				.filter(v -> existeAresta(vertice, v))
				.collect(Collectors.toSet());
	}

	public abstract long getGrauDeEntrada(Vertice vertice);

	public abstract long getGrauDeSaida(Vertice vertice);

	/**
	 * Remove todos os vértices e arestas do grafo, deixando-o vazio.
	 */
	public final void resetar() {
		removeVertices(getVertices().toArray(Vertice[]::new));
	}

	/**
	 * Cria uma cópia do grafo atual. Instancia um novo grafo e adiciona os mesmos
	 * vértices e arestas.
	 *
	 * @return uma nova instância do grafo com os mesmos vértices e arestas
	 */
	public final Grafo clonar() {
		Grafo grafo = novaInstancia();
		for (Vertice vertice : getVertices()) {
			grafo.addVertice(vertice);
		}
		for (Aresta aresta : getArestas()) {
			grafo.addAresta(aresta);
		}
		return grafo;
	}

	protected abstract Grafo novaInstancia();

	public final boolean mesmoTipo(Grafo outroGrafo) {
		checkNotNull(outroGrafo, "Outro grafo não pode ser nulo");
		boolean saoDigrafos = this instanceof Digrafo && outroGrafo instanceof Digrafo;
		boolean saoNaoOrientados = this instanceof GrafoNaoDirecionado && outroGrafo instanceof GrafoNaoDirecionado;
		return saoDigrafos ^ saoNaoOrientados;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Grafo grafo)) {
			return false;
		}
		if (!mesmoTipo(grafo)) {
			return false;
		}
		return getVertices().equals(grafo.getVertices()) && getArestas().equals(grafo.getArestas());
	}

	public final boolean temLaco() {
		return getArestas().stream().anyMatch(Aresta::ehLaco);
	}

	public final int componentesConexas() {
		Set<Vertice> visitados = new HashSet<>();
		int componentes = 0;

		for (Vertice vertice : getVertices()) {
			if (!visitados.contains(vertice)) {
				componentes++;
				visitados.add(vertice);
				visitados.addAll(getVizinhos(vertice));
			}
		}

		return componentes;
	}

	public final boolean ehConexo() {
		return componentesConexas() == 1;
	}

	public final boolean ehPonte(Aresta aresta) {
		checkNotNull(aresta, MSG_ARESTA_NULA);
		checkArgument(existeAresta(aresta.label()), "Aresta deve existir no grafo");

		Grafo grafoSemAresta = clonar();
		grafoSemAresta.removeAresta(aresta.label());

		return grafoSemAresta.ehConexo();
	}

	public final boolean ehDisjunto(Grafo outroGrafo) {
		checkNotNull(outroGrafo, "Outro grafo não pode ser nulo");
		Set<Vertice> outrosVertices = outroGrafo.getVertices();
		Set<Aresta> outrasArestas = outroGrafo.getArestas();
		boolean verticesDisjuntos = getVertices().stream().noneMatch(outrosVertices::contains);
		boolean arestasDisjuntas = getArestas().stream().noneMatch(outrasArestas::contains);
		return verticesDisjuntos && arestasDisjuntas;
	}

	@Override
	public final String toString() {
		String str = """
				%s {
					Vértices = { %s }
					Arestas = { %s }
				}""";
		return String.format(
				str,
				getClass().getSimpleName(),
				String.join(
						", ",
						getVertices().stream()
								.map(Vertice::label)
								.map(String::valueOf)
								.toList()),
				String.join(
						", ",
						getArestas().stream()
								.map(aresta -> "(%s, %s)".formatted(aresta.origem().label(), aresta.destino()
										.label()))
								.toList()));
	}
}

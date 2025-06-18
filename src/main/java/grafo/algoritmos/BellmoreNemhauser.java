package grafo.algoritmos;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.*;

import grafo.Aresta;
import grafo.Vertice;
import grafo.nao_orientado.GrafoNaoDirecionado;

/**
 * Implementação do algoritmo de Bellmore e Nemhauser para o problema do
 * caixeiro viajante (TSP).
 * Este algoritmo usa branch-and-bound para encontrar o ciclo hamiltoniano de
 * menor custo.
 */
public class BellmoreNemhauser {
	private final GrafoNaoDirecionado grafo;
	private final Vertice verticeInicial;
	private final Set<Aresta> melhorCaminhoArestas = new LinkedHashSet<>();
	private final List<Vertice> melhorCaminhoVertices = new ArrayList<>();
	private double melhorCusto = Double.MAX_VALUE;

	private final Map<Vertice, Map<Vertice, Double>> matrizDistancia;
	private final int numVertices;

	public BellmoreNemhauser(GrafoNaoDirecionado grafo, Vertice verticeInicial) {
		checkNotNull(grafo, "Grafo não pode ser nulo");
		checkNotNull(verticeInicial, "Vértice inicial não pode ser nulo");
		checkArgument(grafo.existeVertice(verticeInicial), "Vértice inicial existe no grafo");
		checkArgument(grafo.ehConexo(), "Grafo deve ser conexo para aplicar o algoritmo");

		this.grafo = grafo;
		this.verticeInicial = verticeInicial;
		this.numVertices = grafo.getVertices().size();
		this.matrizDistancia = construirMatrizDistancia();

		rodar();
	}

	private Map<Vertice, Map<Vertice, Double>> construirMatrizDistancia() {
		Map<Vertice, Map<Vertice, Double>> matriz = new HashMap<>();
		Set<Vertice> vertices = grafo.getVertices();

		for (Vertice origem : vertices) {
			Map<Vertice, Double> distancias = new HashMap<>();
			for (Vertice destino : vertices) {
				if (origem.equals(destino)) {
					distancias.put(destino, Double.MAX_VALUE);
				} else {
					Aresta aresta = grafo.encontrarAresta(origem, destino);
					if (aresta != null) {
						distancias.put(destino, aresta.peso());
					} else {
						distancias.put(destino, Double.MAX_VALUE);
					}
				}
			}
			matriz.put(origem, distancias);
		}
		return matriz;
	}

	public void rodar() {
		List<Vertice> vertices = new ArrayList<>(grafo.getVertices());
		vertices.remove(verticeInicial);

		// Inicia o branch-and-bound
		List<Vertice> caminhoAtual = new ArrayList<>();
		caminhoAtual.add(verticeInicial);
		Set<Vertice> visitados = new HashSet<>();
		visitados.add(verticeInicial);

		branchAndBound(caminhoAtual, visitados, 0.0, vertices);
	}

	private void branchAndBound(List<Vertice> caminhoAtual, Set<Vertice> visitados,
			double custoAtual, List<Vertice> verticesRestantes) {

		if (visitados.size() == numVertices) {
			Vertice ultimoVertice = caminhoAtual.get(caminhoAtual.size() - 1);
			double custoRetorno = matrizDistancia.get(ultimoVertice).get(verticeInicial);

			if (custoRetorno != Double.MAX_VALUE) {
				double custoTotal = custoAtual + custoRetorno;
				if (custoTotal < melhorCusto) {
					melhorCusto = custoTotal;
					melhorCaminhoVertices.clear();
					melhorCaminhoVertices.addAll(caminhoAtual);
					melhorCaminhoVertices.add(verticeInicial);

					atualizarMelhorCaminhoArestas();
				}
			}
			return;
		}

		double bound = custoAtual + calcularBound(caminhoAtual.get(caminhoAtual.size() - 1), visitados);

		if (bound >= melhorCusto) {
			return;
		}

		Vertice verticeAtual = caminhoAtual.get(caminhoAtual.size() - 1);
		for (Vertice proximoVertice : grafo.getVertices()) {
			if (!visitados.contains(proximoVertice)) {
				double custo = matrizDistancia.get(verticeAtual).get(proximoVertice);

				if (custo != Double.MAX_VALUE) {
					caminhoAtual.add(proximoVertice);
					visitados.add(proximoVertice);

					branchAndBound(caminhoAtual, visitados, custoAtual + custo, verticesRestantes);

					caminhoAtual.remove(caminhoAtual.size() - 1);
					visitados.remove(proximoVertice);
				}
			}
		}
	}

	private double calcularBound(Vertice verticeAtual, Set<Vertice> visitados) {
		double bound = 0.0;
		Set<Vertice> naoVisitados = new HashSet<>(grafo.getVertices());
		naoVisitados.removeAll(visitados);

		if (naoVisitados.isEmpty()) {
			return matrizDistancia.get(verticeAtual).get(verticeInicial);
		}

		double menorCusto = Double.MAX_VALUE;
		for (Vertice v : naoVisitados) {
			double custo = matrizDistancia.get(verticeAtual).get(v);
			if (custo < menorCusto) {
				menorCusto = custo;
			}
		}

		if (menorCusto != Double.MAX_VALUE) {
			bound += menorCusto;
		}

		for (Vertice v : naoVisitados) {
			menorCusto = Double.MAX_VALUE;
			for (Vertice destino : naoVisitados) {
				if (!destino.equals(v)) {
					double custo = matrizDistancia.get(v).get(destino);
					if (custo < menorCusto) {
						menorCusto = custo;
					}
				}
			}
			if (menorCusto != Double.MAX_VALUE) {
				bound += menorCusto;
			}
		}

		return bound;
	}

	private void atualizarMelhorCaminhoArestas() {
		melhorCaminhoArestas.clear();
		for (int i = 0; i < melhorCaminhoVertices.size() - 1; i++) {
			Vertice origem = melhorCaminhoVertices.get(i);
			Vertice destino = melhorCaminhoVertices.get(i + 1);
			Aresta aresta = grafo.encontrarAresta(origem, destino);
			if (aresta != null) {
				melhorCaminhoArestas.add(aresta);
			}
		}
	}

	public List<Aresta> getCaminhoHamiltonianoArestas() {
		return new ArrayList<>(melhorCaminhoArestas);
	}

	public List<Vertice> getCaminhoHamiltonianoVertices() {
		return new ArrayList<>(melhorCaminhoVertices);
	}

	public double getMelhorCusto() {
		return melhorCusto;
	}
}

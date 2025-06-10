import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.MethodSource;

import grafo.Aresta;
import grafo.Vertice;
import grafo.nao_orientado.GrafoNaoDirecionado;
import grafo.nao_orientado.GrafoNaoDirecionadoPorLista;

@ParameterizedClass
@MethodSource("grafoProvider")
public class GrafoNaoDirecionadoTest {
	@Parameter
	GrafoNaoDirecionado grafo;

	static Stream<GrafoNaoDirecionado> grafoProvider() {
		return Stream.of(new GrafoNaoDirecionadoPorLista());
	}

	@BeforeEach
	void beforeEach() {
		grafo.resetar();
	}

	@Test
	void addArestaTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();

		Aresta arestaAB = new Aresta(verticeA, verticeB);

		grafo.addArestas(arestaAB);

		assertTrue(grafo.existeAresta(arestaAB), "Aresta entre A e B deve existir");
		assertTrue(grafo.existeAresta(arestaAB.inversa()), "Aresta entre B e A deve existir (grafo não direcionado)");
	}

	@Test
	void addArestasParalelasTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();

		Aresta arestaAB = new Aresta(verticeA, verticeB);

		grafo.addArestas(arestaAB, arestaAB);

		assumeTrue(grafo.getQuantidadeDeArestas(arestaAB) == 2, "Deve haver duas arestas entre A e B");
		assertEquals(2, grafo.getQuantidadeDeArestas(arestaAB.inversa()), "Deve haver duas arestas entre B e A");
	}

	@Test
	void removeArestaTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();
		Aresta arestaAB = new Aresta(verticeA, verticeB);

		grafo.addAresta(arestaAB);

		grafo.removeAresta(arestaAB);
		assertFalse(grafo.existeAresta(arestaAB), "Aresta entre A e B não deve existir após remoção");
		assertFalse(grafo.existeAresta(arestaAB.inversa()),
				"Aresta entre B e A não deve existir após remoção (grafo não direcionado)");
	}

	@Test
	void removeArestasParalelasTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();

		Aresta arestaAB = new Aresta(verticeA, verticeB);

		grafo.addArestas(arestaAB, arestaAB);

		grafo.removeArestas(arestaAB);

		assertTrue(grafo.getQuantidadeDeArestas(arestaAB) == 1, "Deve haver uma aresta restante entre A e B");
		assertTrue(grafo.getQuantidadeDeArestas(arestaAB.inversa()) == 1,
				"Deve haver uma aresta restante entre B e A (grafo não direcionado)");

		grafo.removeArestas(arestaAB);

		assertFalse(grafo.existeAresta(arestaAB), "Aresta entre A e B não deve existir após remoção parcial");
		assertFalse(grafo.existeAresta(arestaAB.inversa()),
				"Aresta entre B e A não deve existir após remoção parcial (grafo não direcionado)");
	}

	@Test
	void getGrauTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();
		Vertice verticeC = grafo.addVertice();
		Vertice verticeD = grafo.addVertice();
		Vertice verticeE = grafo.addVertice();

		Aresta arestaAB = new Aresta(verticeA, verticeB);
		Aresta arestaBC = new Aresta(verticeB, verticeC);
		Aresta arestaEE = new Aresta(verticeE, verticeE);

		grafo.addArestas(arestaAB, arestaBC, arestaEE);

		assertEquals(1, grafo.getGrau(verticeA), "Grau do vértice A deve ser 1");
		assertEquals(2, grafo.getGrau(verticeB), "Grau do vértice B deve ser 2");
		assertEquals(1, grafo.getGrau(verticeC), "Grau do vértice C deve ser 1");
		assertEquals(0, grafo.getGrau(verticeD), "Grau do vértice D deve ser 0");
		assertEquals(2, grafo.getGrau(verticeE), "Grau do vértice E deve ser 2 (laço)");
	}

	@Test
	void getGrauDeEntradaESaidaTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();
		Vertice verticeC = grafo.addVertice();
		Vertice verticeD = grafo.addVertice();
		Vertice verticeE = grafo.addVertice();

		Aresta arestaAB = new Aresta(verticeA, verticeB);
		Aresta arestaBC = new Aresta(verticeB, verticeC);
		Aresta arestaEE = new Aresta(verticeE, verticeE);

		grafo.addArestas(arestaAB, arestaBC, arestaEE);

		assertEquals(grafo.getGrau(verticeA), grafo.getGrauDeEntrada(verticeA),
				"Grau de entrada do vértice A deve ser igual ao grau (grafo não direcionado)");
		assertEquals(grafo.getGrau(verticeA), grafo.getGrauDeSaida(verticeA),
				"Grau de saída do vértice A deve ser igual ao grau (grafo não direcionado)");

		assertEquals(grafo.getGrau(verticeB), grafo.getGrauDeEntrada(verticeB),
				"Grau de entrada do vértice B deve ser igual ao grau (grafo não direcionado)");
		assertEquals(grafo.getGrau(verticeB), grafo.getGrauDeSaida(verticeB),
				"Grau de saída do vértice B deve ser igual ao grau (grafo não direcionado)");

		assertEquals(grafo.getGrau(verticeC), grafo.getGrauDeEntrada(verticeC),
				"Grau de entrada do vértice C deve ser igual ao grau (grafo não direcionado)");
		assertEquals(grafo.getGrau(verticeC), grafo.getGrauDeSaida(verticeC),
				"Grau de saída do vértice C deve ser igual ao grau (grafo não direcionado)");

		assertEquals(grafo.getGrau(verticeD), grafo.getGrauDeEntrada(verticeD),
				"Grau de entrada do vértice D deve ser igual ao grau (grafo não direcionado)");
		assertEquals(grafo.getGrau(verticeD), grafo.getGrauDeSaida(verticeD),
				"Grau de saída do vértice D deve ser igual ao grau (grafo não direcionado)");

		assertEquals(grafo.getGrau(verticeE), grafo.getGrauDeEntrada(verticeE),
				"Grau de entrada do vértice E deve ser igual ao grau (grafo não direcionado)");
		assertEquals(grafo.getGrau(verticeE), grafo.getGrauDeSaida(verticeE),
				"Grau de saída do vértice E deve ser igual ao grau (grafo não direcionado)");
	}

	@Test
	void getArestasTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();
		Vertice verticeC = grafo.addVertice();

		Aresta arestaAB = new Aresta(verticeA, verticeB);
		Aresta arestaBC = new Aresta(verticeB, verticeC);

		grafo.addArestas(arestaAB, arestaAB, arestaAB.inversa(), arestaBC);

		List<Aresta> arestas = grafo.getArestas();
		assertEquals(4, arestas.size(), "O grafo deve ter 4 arestas.");

		assertEquals(3, grafo.getQuantidadeDeArestas(arestaAB), "Deve haver 3 arestas AB");
		assertEquals(3, grafo.getQuantidadeDeArestas(arestaAB.inversa()), "Deve haver 3 arestas BA");
		assertEquals(1, grafo.getQuantidadeDeArestas(arestaBC), "Deve haver uma aresta BC");
		assertEquals(1, grafo.getQuantidadeDeArestas(arestaBC), "Deve haver uma aresta CB");
	}
}

import grafo.Aresta;
import grafo.Vertice;
import grafo.nao_orientado.GrafoNaoDirecionado;
import grafo.nao_orientado.GrafoNaoDirecionadoPorLista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

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
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));

		Aresta arestaAB = new Aresta("AB", verticeA, verticeB);

		grafo.addArestas(arestaAB);

		assertTrue(grafo.existeAresta(verticeA, verticeB), "Aresta entre A e B deve existir");
		assertTrue(grafo.existeAresta(verticeB, verticeA), "Aresta entre B e A deve existir (grafo não direcionado)");
	}

	@Test
	void addArestasParalelasTest() {
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));

		Aresta arestaAB1 = new Aresta("AB1", verticeA, verticeB);
		Aresta arestaAB2 = arestaAB1.comLabel("AB2");

		grafo.addArestas(arestaAB1, arestaAB2);

		assumeTrue(grafo.encontrarArestas(verticeA, verticeB).size() == 2, "Deve haver duas arestas entre A e B");
		assertEquals(2, grafo.encontrarArestas(verticeB, verticeA).size(), "Deve haver duas arestas entre B e A");
	}

	@Test
	void removeArestaTest() {
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));
		Aresta arestaAB = new Aresta("AB", verticeA, verticeB);

		grafo.addAresta(arestaAB);

		grafo.removeAresta(verticeA, verticeB);
		assertFalse(grafo.existeAresta(verticeA, verticeB), "Aresta entre A e B não deve existir após remoção");
		assertFalse(grafo.existeAresta(verticeB, verticeA),
				"Aresta entre B e A não deve existir após remoção (grafo não direcionado)");
	}

	@Test
	void removeArestasParalelasTest() {
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));

		Aresta arestaAB1 = new Aresta("AB", verticeA, verticeB);
		Aresta arestaAB2 = arestaAB1.comLabel("AB2");

		grafo.addArestas(arestaAB1, arestaAB2);

		grafo.removeAresta(verticeA, verticeB);

		assertEquals(1, grafo.encontrarArestas(verticeA, verticeB).size(),
				"Deve haver uma aresta restante entre A e B");
		assertEquals(1, grafo.encontrarArestas(verticeB, verticeA).size(),
				"Deve haver uma aresta restante entre B e A (grafo não direcionado)");

		grafo.removeAresta(verticeA, verticeB);

		assertFalse(grafo.existeAresta(verticeA, verticeB), "Aresta entre A e B não deve existir após remoção parcial");
		assertFalse(grafo.existeAresta(verticeB, verticeA),
				"Aresta entre B e A não deve existir após remoção parcial (grafo não direcionado)");
	}

	@Test
	void getGrauTest() {
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));
		Vertice verticeC = grafo.addVertice(new Vertice("C"));
		Vertice verticeD = grafo.addVertice(new Vertice("D"));
		Vertice verticeE = grafo.addVertice(new Vertice("E"));

		Aresta arestaAB = new Aresta("AB", verticeA, verticeB);
		Aresta arestaBC = new Aresta("BC", verticeB, verticeC);
		Aresta arestaEE = new Aresta("EE", verticeE, verticeE);

		grafo.addArestas(arestaAB, arestaBC, arestaEE);

		assertEquals(1, grafo.getGrau(verticeA), "Grau do vértice A deve ser 1");
		assertEquals(2, grafo.getGrau(verticeB), "Grau do vértice B deve ser 2");
		assertEquals(1, grafo.getGrau(verticeC), "Grau do vértice C deve ser 1");
		assertEquals(0, grafo.getGrau(verticeD), "Grau do vértice D deve ser 0");
		assertEquals(2, grafo.getGrau(verticeE), "Grau do vértice E deve ser 2 (laço)");
	}

	@Test
	void getGrauDeEntradaESaidaTest() {
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));
		Vertice verticeC = grafo.addVertice(new Vertice("C"));
		Vertice verticeD = grafo.addVertice(new Vertice("D"));
		Vertice verticeE = grafo.addVertice(new Vertice("E"));

		Aresta arestaAB = new Aresta("AB", verticeA, verticeB);
		Aresta arestaBC = new Aresta("BC", verticeB, verticeC);
		Aresta arestaEE = new Aresta("EE", verticeE, verticeE);

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
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));
		Vertice verticeC = grafo.addVertice(new Vertice("C"));

		Aresta arestaAB1 = new Aresta("AB1", verticeA, verticeB);
		Aresta arestaAB2 = arestaAB1.comLabel("AB2");
		Aresta arestaBA = arestaAB1.inversa().comLabel("BA");
		Aresta arestaBC = new Aresta("BC", verticeB, verticeC);

		grafo.addArestas(arestaAB1, arestaAB2, arestaBA, arestaBC);

		Set<Aresta> arestas = grafo.getArestas();
		assertEquals(4, arestas.size(), "O grafo deve ter 4 arestas.");

		assertEquals(3, grafo.encontrarArestas(verticeA, verticeB).size(), "Deve haver 3 arestas AB");
		assertEquals(3, grafo.encontrarArestas(verticeB, verticeA).size(), "Deve haver 3 arestas BA");
		assertEquals(1, grafo.encontrarArestas(verticeB, verticeC).size(), "Deve haver uma aresta BC");
		assertEquals(1, grafo.encontrarArestas(verticeC, verticeB).size(), "Deve haver uma aresta CB");
	}

	@Test
	void unirTest() {

		GrafoNaoDirecionado grafo1 = (GrafoNaoDirecionado) grafo.clonar();

		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));
		Aresta arestaAB = new Aresta("AB", verticeA, verticeB);
		Aresta arestaBA = arestaAB.inversa();
		grafo.addArestas(arestaAB, arestaBA);

		Vertice verticeC = grafo1.addVertice(new Vertice("C"));
		Vertice verticeD = grafo1.addVertice(new Vertice("D"));
		Aresta arestaCC = new Aresta("CC", verticeC, verticeC);
		grafo1.addArestas(arestaCC);

		GrafoNaoDirecionado grafoUnido = grafo.uniao(grafo1);

		assertTrue(grafoUnido.existeVertice(verticeA), "O vértice A deve existir no grafo unido");
		assertTrue(grafoUnido.existeVertice(verticeB), "O vértice B deve existir no grafo unido");
		assertTrue(grafoUnido.existeVertice(verticeC), "O vértice C deve existir no grafo unido");
		assertTrue(grafoUnido.existeAresta(verticeA, verticeB), "A aresta AB deve existir no grafo unido");
		assertTrue(grafoUnido.existeAresta(verticeB, verticeA), "A aresta BA deve existir no grafo unido");
		assertTrue(grafoUnido.existeAresta(verticeC, verticeC), "A aresta CC deve existir no grafo unido");
	}
}

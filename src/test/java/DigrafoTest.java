import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.MethodSource;

import grafo.Aresta;
import grafo.Vertice;
import grafo.digrafo.Digrafo;

@ParameterizedClass
@MethodSource("Provider#digrafos")
public class DigrafoTest {
	@Parameter
	Digrafo grafo;

	@BeforeEach
	void beforeEach() {
		grafo.resetar();
	}

	@Test
	void getGrauDeEntradaESaidaTest() {

		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));
		Vertice verticeC = grafo.addVertice(new Vertice("C"));
		Vertice verticeD = grafo.addVertice(new Vertice("D"));

		Aresta arestaAB = new Aresta("AB", verticeA, verticeB);
		Aresta arestaBC = new Aresta("BC", verticeB, verticeC);

		grafo.addArestas(arestaAB, arestaBC);

		assertEquals(0, grafo.getGrauDeEntrada(verticeA), "Grau de entrada do vértice A deve ser 0");
		assertEquals(1, grafo.getGrauDeSaida(verticeA), "Grau de saída do vértice A deve ser 1");

		assertEquals(1, grafo.getGrauDeEntrada(verticeB), "Grau de entrada do vértice B deve ser 1");
		assertEquals(1, grafo.getGrauDeSaida(verticeB), "Grau de saída do vértice B deve ser 1");

		assertEquals(1, grafo.getGrauDeEntrada(verticeC), "Grau de entrada do vértice C deve ser 1");
		assertEquals(0, grafo.getGrauDeSaida(verticeC), "Grau de saída do vértice C deve ser 0");

		assertEquals(0, grafo.getGrauDeEntrada(verticeD), "Grau de entrada do vértice D deve ser 0");
		assertEquals(0, grafo.getGrauDeSaida(verticeD), "Grau de saída do vértice D deve ser 0");
	}

	@Test
	void getArestasTest() {
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));
		Vertice verticeC = grafo.addVertice(new Vertice("C"));

		Aresta arestaAB1 = new Aresta("AB", verticeA, verticeB);
		Aresta arestaAB2 = arestaAB1.comLabel("AB2");
		Aresta arestaBA = arestaAB1.inversa().comLabel("BA");
		Aresta arestaBC = new Aresta("BC", verticeB, verticeC);

		grafo.addArestas(arestaAB1, arestaAB2, arestaBA, arestaBC);

		Set<Aresta> arestas = grafo.getArestas();
		assertEquals(4, arestas.size(), "O grafo deve ter 4 arestas.");
		assertEquals(2, grafo.encontrarArestas(verticeA, verticeB).size(),
				"O grafo deve ter 2 arestas AB.");
		assertEquals(1, grafo.encontrarArestas(verticeB, verticeA).size(),
				"O grafo deve ter 1 aresta BA.");
		assertEquals(1, grafo.encontrarArestas(verticeB, verticeC).size(), "O grafo deve ter 1 aresta BC.");
	}

}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ParameterizedClass
@MethodSource("grafoProvider")
public class DigrafoTest {
	@Parameter
	Digrafo grafo;

	static Stream<Digrafo> grafoProvider() {
		return Stream.of(new DigrafoPorLista());
	}

	@BeforeEach
	void beforeEach() {
		grafo.resetar();
	}

	@Test
	void getGrauDeEntradaESaidaTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();
		Vertice verticeC = grafo.addVertice();
		Vertice verticeD = grafo.addVertice();

		Aresta arestaAB = new Aresta(verticeA, verticeB);
		Aresta arestaBC = new Aresta(verticeB, verticeC);

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
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();
		Vertice verticeC = grafo.addVertice();

		Aresta arestaAB = new Aresta(verticeA, verticeB);
		Aresta arestaBC = new Aresta(verticeB, verticeC);

		grafo.addArestas(arestaAB, arestaAB, arestaAB.inversa(), arestaBC);

		List<Aresta> arestas = grafo.getArestas();
		assertEquals(4, arestas.size(), "O grafo deve ter 4 arestas.");
		assertEquals(2, grafo.getQuantidadeDeArestas(arestaAB), "O grafo deve ter 2 arestas AB.");
		assertEquals(1, grafo.getQuantidadeDeArestas(arestaAB.inversa()), "O grafo deve ter 1 aresta BA.");
		assertEquals(1, grafo.getQuantidadeDeArestas(arestaBC), "O grafo deve ter 1 aresta BC.");
	}

}

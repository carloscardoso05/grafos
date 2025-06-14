import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.MethodSource;

import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;
import grafo.digrafo.DigrafoPorLista;
import grafo.nao_orientado.GrafoNaoDirecionadoPorLista;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ParameterizedClass
@MethodSource("grafoProvider")
public class GrafoTest {
	@Parameter
	Grafo grafo;

	static Stream<Grafo> grafoProvider() {
		return Stream.of(new GrafoNaoDirecionadoPorLista(), new DigrafoPorLista());
	}

	@BeforeEach
	void beforeEach() {
		grafo.resetar();
	}

	@Test
	void testResetar() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();
		Vertice verticeC = grafo.addVertice();

		Aresta arestaAB = new Aresta(verticeA, verticeB);

		grafo.addArestas(arestaAB, arestaAB);

		assumeTrue(!grafo.getArestas().isEmpty(), () -> String
				.format("O grafo deve possuir arestas antes de resetar. Tem %d arestas.", grafo.getArestas().size()));
		assumeTrue(!grafo.getVertices().isEmpty(), () -> String
				.format("O grafo deve possuir vértices antes de resetar. Tem %d vértices.", grafo.getVertices()
						.size()));

		grafo.resetar();

		assertEquals(0, grafo.getVertices().size(), "O grafo deve ter zero vértices após resetar");
		assertEquals(0, grafo.getArestas().size(), "O grafo deve ter zero arestas após resetar");
	}

	@Test
	void testQuantidadeDeVertices() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();

		assertEquals(2, grafo.getVertices().size(), "O grafo deve ter 2 vértices.");
	}

	@Test
	void testQuantidadeDeArestas() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();

		Aresta arestaAB = new Aresta(verticeA, verticeB);

		grafo.addArestas(arestaAB, arestaAB);

		assertEquals(2, grafo.getArestas().size(), "O grafo deve ter 2 arestas.");
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
	}

	void addVerticeTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();
		Vertice verticeC = grafo.addVertice();

		assertTrue(grafo.existeVertice(verticeA), "Vértice A deve existir");
		assertTrue(grafo.existeVertice(verticeB), "Vértice B deve existir");
		assertFalse(grafo.existeVertice(verticeC), "Vértice C não deve existir");
	}

	@Test
	void addVerticeViaArestaTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();
		Aresta arestaAB = new Aresta(verticeA, verticeB);

		grafo.addAresta(arestaAB);

		assertTrue(grafo.existeVertice(verticeA), "Vértice A deve existir");
		assertTrue(grafo.existeVertice(verticeB), "Vértice B deve existir");
	}

	@Test
	void addArestaTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();
		Vertice verticeC = grafo.addVertice();
		Vertice verticeD = grafo.addVertice();

		Aresta arestaAB = new Aresta(verticeA, verticeB);
		Aresta arestaCD = new Aresta(verticeC, verticeD);
		Aresta arestaAC = new Aresta(verticeA, verticeC);

		grafo.addArestas(arestaAB, arestaCD, arestaAC);

		assertTrue(grafo.existeAresta(arestaAB), "Aresta entre A e B deve existir");
		assertTrue(grafo.existeAresta(arestaCD), "Aresta entre C e D deve existir");
		assertTrue(grafo.existeAresta(arestaAC), "Aresta entre A e C deve existir");
	}

	@Test
	void addArestasParalelasTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();

		Aresta arestaAB = new Aresta(verticeA, verticeB);

		grafo.addArestas(arestaAB, arestaAB);

		assertEquals(2, grafo.getQuantidadeDeArestas(arestaAB), "Deve haver duas arestas entre A e B");
	}

	@Test
	void removeArestaTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();
		Aresta arestaAB = new Aresta(verticeA, verticeB);

		grafo.addAresta(arestaAB);

		grafo.removeAresta(arestaAB);
		assertFalse(grafo.existeAresta(arestaAB), "Aresta entre A e B não deve existir após remoção");
	}

	@Test
	void removeArestasParalelasTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();

		Aresta arestaAB = new Aresta(verticeA, verticeB);

		grafo.addArestas(arestaAB, arestaAB);

		grafo.removeArestas(arestaAB);

		assertEquals(1, grafo.getQuantidadeDeArestas(arestaAB), "Deve haver uma aresta restante entre A e B");

		grafo.removeArestas(arestaAB);

		assertFalse(grafo.existeAresta(arestaAB), "Aresta entre A e B não deve existir após remoção parcial");
	}

	@Test
	void cloneTest() {
		Vertice verticeA = grafo.addVertice();
		Vertice verticeB = grafo.addVertice();
		Vertice verticeC = grafo.addVertice();
		Vertice verticeD = grafo.addVertice();
		Aresta arestaAB = new Aresta(verticeA, verticeB);
		Aresta arestaBC = new Aresta(verticeB, verticeC);
		Aresta arestaDD = new Aresta(verticeD, verticeD);

		grafo.addArestas(arestaAB, arestaAB.inversa(), arestaBC, arestaDD);

		Grafo clone = grafo.clone();

		assertNotSame(grafo, clone, "O clone não deve ser o mesmo objeto");
		assertEquals(grafo, clone);
		assertEquals(grafo.getVertices().size(), clone.getVertices().size(), "O número de vértices deve ser igual");
		assertEquals(grafo.getArestas().size(), clone.getArestas().size(), "O número de arestas deve ser igual");

		assertTrue(clone.existeAresta(arestaAB), "O clone deve conter a aresta original");
		assertTrue(clone.existeAresta(arestaAB.inversa()), "O clone deve conter a aresta inversa");
		assertTrue(clone.existeAresta(arestaBC), "O clone deve conter a aresta BC");
		assertTrue(clone.existeAresta(arestaDD), "O clone deve conter a aresta DD");
		assertTrue(clone.existeVertice(verticeA), "O clone deve conter o vértice A");
		assertTrue(clone.existeVertice(verticeB), "O clone deve conter o vértice B");
		assertTrue(clone.existeVertice(verticeC), "O clone deve conter o vértice C");
		assertTrue(clone.existeVertice(verticeD), "O clone deve conter o vértice D");

		assertIterableEquals(List.of(arestaAB, arestaAB.inversa(), arestaBC, arestaDD), clone.getArestas());
		assertIterableEquals(Set.of(verticeA, verticeB, verticeC, verticeD), clone.getVertices(),
				"O clone deve conter os mesmos vértices");
	}
}

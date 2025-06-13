import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.MethodSource;

import grafo.Aresta;
import grafo.Grafo;
import grafo.Vertice;

@ParameterizedClass
@MethodSource("Provider#grafos")
public class GrafoTest {
	@Parameter
	Grafo grafo;

	@BeforeEach
	void beforeEach() {
		grafo.resetar();
	}

	@Test
	void testResetar() {
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));
		Vertice verticeC = grafo.addVertice(new Vertice("C"));

		Aresta arestaAB = new Aresta("AB1", verticeA, verticeB);

		grafo.addArestas(arestaAB, arestaAB.comLabel("AB2"));

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
		grafo.addVertices(new Vertice("A"), new Vertice("B"));

		assertEquals(2, grafo.getVertices().size(), "O grafo deve ter 2 vértices.");
	}

	@Test
	void testQuantidadeDeArestas() {
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));

		Aresta arestaAB = new Aresta("AB1", verticeA, verticeB);

		grafo.addArestas(arestaAB, arestaAB.comLabel("AB2"));

		assertEquals(2, grafo.getArestas().size(), "O grafo deve ter 2 arestas.");
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
	}

	void addVerticeTest() {
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));
		Vertice verticeC = grafo.addVertice(new Vertice("C"));

		assertTrue(grafo.existeVertice(verticeA), "Vértice A deve existir");
		assertTrue(grafo.existeVertice(verticeB), "Vértice B deve existir");
		assertFalse(grafo.existeVertice(verticeC), "Vértice C não deve existir");
	}

	@Test
	void addVerticeViaArestaTest() {
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));

		Aresta arestaAB = new Aresta("AB", verticeA, verticeB);

		grafo.addAresta(arestaAB);

		assertTrue(grafo.existeVertice(verticeA), "Vértice A deve existir");
		assertTrue(grafo.existeVertice(verticeB), "Vértice B deve existir");
	}

	@Test
	void addArestaTest() {
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));
		Vertice verticeC = grafo.addVertice(new Vertice("C"));
		Vertice verticeD = grafo.addVertice(new Vertice("D"));

		Aresta arestaAB = new Aresta("AB", verticeA, verticeB);
		Aresta arestaCD = new Aresta("CD", verticeC, verticeD);
		Aresta arestaAC = new Aresta("AC", verticeA, verticeC);

		grafo.addArestas(arestaAB, arestaCD, arestaAC);

		assertTrue(grafo.existeAresta(verticeA, verticeB), "Aresta entre A e B deve existir");
		assertTrue(grafo.existeAresta(verticeC, verticeD), "Aresta entre C e D deve existir");
		assertTrue(grafo.existeAresta(verticeA, verticeC), "Aresta entre A e C deve existir");
	}

	@Test
	void addArestasParalelasTest() {
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));

		Aresta arestaAB = new Aresta("AB1", verticeA, verticeB);

		grafo.addArestas(arestaAB, arestaAB.comLabel("AB2"));

		assertEquals(2, grafo.encontrarArestas(verticeA, verticeB).size(), "Deve haver duas arestas entre A e B");
	}

	@Test
	void removeArestaTest() {
		Vertice verticeA = grafo.addVertice(new Vertice("A"));
		Vertice verticeB = grafo.addVertice(new Vertice("B"));
		Aresta arestaAB = new Aresta("AB", verticeA, verticeB);

		grafo.addAresta(arestaAB);

		grafo.removeAresta(verticeA, verticeB);
		assertFalse(grafo.existeAresta(verticeA, verticeB), "Aresta entre A e B não deve existir após remoção");
	}

	@Test
	void removeArestasParalelasTest() {
		Vertice verticeA = new Vertice("A");
		Vertice verticeB = new Vertice("B");

		Aresta arestaAB1 = new Aresta("AB1", verticeA, verticeB);
		Aresta arestaAB2 = new Aresta("AB2", verticeA, verticeB);

		grafo.addArestas(arestaAB1, arestaAB2);

		grafo.removeAresta(verticeA, verticeB);

		assertEquals(1, grafo.encontrarArestas(verticeA, verticeB).size(),
				"Deve haver uma aresta restante entre A e B");

		grafo.removeAresta(verticeA, verticeB);

		assertFalse(grafo.existeAresta(verticeA, verticeB), "Aresta entre A e B não deve existir após remoção total");
	}

	@Test
	void clonarTest() {
		Vertice verticeA = new Vertice("A");
		Vertice verticeB = new Vertice("B");
		Vertice verticeC = new Vertice("C");
		Vertice verticeD = new Vertice("D");

		Aresta arestaAB = new Aresta("AB", verticeA, verticeB);
		Aresta arestaBC = new Aresta("BC", verticeB, verticeC);
		Aresta arestaDD = new Aresta("DD", verticeD, verticeD);

		grafo.addArestas(arestaAB, arestaAB.inversa(), arestaBC, arestaDD);

		Grafo clone = grafo.clonar();

		assertNotSame(grafo, clone, "O clone não deve ser o mesmo objeto");
		assertEquals(grafo, clone);
		assertEquals(grafo.getVertices().size(), clone.getVertices().size(), "O número de vértices deve ser igual");
		assertEquals(grafo.getArestas().size(), clone.getArestas().size(), "O número de arestas deve ser igual");

		assertTrue(clone.existeAresta(verticeA, verticeB), "O clone deve conter a aresta original");
		assertTrue(clone.existeAresta(verticeB, verticeA), "O clone deve conter a aresta inversa");
		assertTrue(clone.existeAresta(verticeB, verticeC), "O clone deve conter a aresta BC");
		assertTrue(clone.existeAresta(verticeD, verticeD), "O clone deve conter a aresta DD");
		assertTrue(clone.existeVertice(verticeA), "O clone deve conter o vértice A");
		assertTrue(clone.existeVertice(verticeB), "O clone deve conter o vértice B");
		assertTrue(clone.existeVertice(verticeC), "O clone deve conter o vértice C");
		assertTrue(clone.existeVertice(verticeD), "O clone deve conter o vértice D");

		assertEquals(Set.of(arestaAB, arestaAB.inversa(), arestaBC, arestaDD), clone.getArestas());
		assertEquals(Set.of(verticeA, verticeB, verticeC, verticeD), clone.getVertices(),
				"O clone deve conter os mesmos vértices");
	}

	@Test
	void ehDisjuntoTest() {
		Grafo grafo1 = grafo.clonar();
		Grafo grafo2 = grafo.clonar();
		Grafo grafo3 = grafo.clonar();

		grafo1.addArestas(
				new Aresta("AB", new Vertice("A"), new Vertice("B")),
				new Aresta("BC", new Vertice("B"), new Vertice("C"))	
		);

		grafo2.addArestas(
				new Aresta("DE", new Vertice("D"), new Vertice("E")),
				new Aresta("EF", new Vertice("E"), new Vertice("F"))
		);

		grafo3.addArestas(
				new Aresta("AH", new Vertice("A"), new Vertice("H")),
				new Aresta("HI", new Vertice("H"), new Vertice("I"))
		);

		assertTrue(grafo1.ehDisjunto(grafo2), "Grafo 1 e Grafo 2 devem ser disjuntos");
		assertFalse(grafo1.ehDisjunto(grafo3), "Grafo 1 e Grafo 3 não devem ser disjuntos");
		assertTrue(grafo2.ehDisjunto(grafo3), "Grafo 2 e Grafo 3 devem ser disjuntos");
	}
}

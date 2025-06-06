import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.MethodSource;

@ParameterizedClass
@MethodSource("grafoProvider")
public class GrafoTest {
    @Parameter
    Grafo grafo;

    static Stream<Grafo> grafoProvider() {
        return Stream.of(new GrafoNaoDirecionadoPorLista());
    }

    @BeforeEach
    void beforeEach() {
        grafo.resetar();
    }

    @Test
    void testResetar() {
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");
        Vertice verticeC = new Vertice("C");

        Aresta arestaAB = new Aresta(verticeA, verticeB);

        grafo.addArestas(arestaAB, arestaAB);
        grafo.addVertices(verticeC);

        assumeTrue(grafo.getArestas().size() == 2, () -> String
                .format("O grafo deve ter 2 arestas antes de resetar. Tem %d arestas.", grafo.getArestas().size()));
        assumeTrue(grafo.getVertices().size() == 3, () -> String
                .format("O grafo deve ter 3 vértices antes de resetar. Tem %d vértices.", grafo.getVertices().size()));

        grafo.resetar();

        assertEquals(0, grafo.getVertices().size(), "O grafo deve ter zero vértices após resetar");
        assertEquals(0, grafo.getArestas().size(), "O grafo deve ter zero arestas após resetar");
    }

    @Test
    void testQuantidadeDeVertices() {
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");

        grafo.addVertices(verticeA, verticeB, verticeB);

        assertEquals(2, grafo.getVertices().size(), "O grafo deve ter 2 vértices.");
    }

    @Test
    void testQuantidadeDeArestas() {
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");

        Aresta arestaAB = new Aresta(verticeA, verticeB);

        grafo.addArestas(arestaAB, arestaAB);

        assertEquals(2, grafo.getArestas().size(), "O grafo deve ter 2 arestas.");
    }

    void addVerticeTest() {
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");
        Vertice verticeC = new Vertice("C");

        grafo.addVertices(verticeA, verticeB);

        assertTrue(grafo.existeVertice(verticeA), "Vértice A deve existir");
        assertTrue(grafo.existeVertice(verticeB), "Vértice B deve existir");
        assertFalse(grafo.existeVertice(verticeC), "Vértice C não deve existir");
    }

    @Test
    void addVerticeViaArestaTest() {
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");
        Aresta arestaAB = new Aresta(verticeA, verticeB);

        grafo.addAresta(arestaAB);

        assertTrue(grafo.existeVertice(verticeA), "Vértice A deve existir");
        assertTrue(grafo.existeVertice(verticeB), "Vértice B deve existir");
    }

    @Test
    void addArestaTest() {
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");
        Vertice verticeC = new Vertice("C");
        Vertice verticeD = new Vertice("D");

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
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");

        Aresta arestaAB = new Aresta(verticeA, verticeB);

        grafo.addArestas(arestaAB, arestaAB);

        assertTrue(grafo.getQuantidadeDeArestas(arestaAB) == 2, "Deve haver duas arestas entre A e B");
    }

    @Test
    void removeArestaTest() {
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");
        Aresta arestaAB = new Aresta(verticeA, verticeB);

        grafo.addAresta(arestaAB);

        grafo.removeAresta(arestaAB);
        assertFalse(grafo.existeAresta(arestaAB), "Aresta entre A e B não deve existir após remoção");
    }

    @Test
    void removeArestasParalelasTest() {
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");

        Aresta arestaAB = new Aresta(verticeA, verticeB);

        grafo.addArestas(arestaAB, arestaAB);

        grafo.removeArestas(arestaAB);

        assertTrue(grafo.getQuantidadeDeArestas(arestaAB) == 1, "Deve haver uma aresta restante entre A e B");

        grafo.removeArestas(arestaAB);

        assertFalse(grafo.existeAresta(arestaAB), "Aresta entre A e B não deve existir após remoção parcial");
    }

}

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

        Aresta arestaAB = new Aresta(verticeA, verticeB);

        grafo.addArestas(arestaAB);

        assertTrue(grafo.existeAresta(arestaAB), "Aresta entre A e B deve existir");
        assertTrue(grafo.existeAresta(arestaAB.inversa()), "Aresta entre B e A deve existir (grafo não direcionado)");
    }

    @Test
    void addArestasParalelasTest() {
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");

        Aresta arestaAB = new Aresta(verticeA, verticeB);

        grafo.addArestas(arestaAB, arestaAB);

        assumeTrue(grafo.getQuantidadeDeArestas(arestaAB) == 2, "Deve haver duas arestas entre A e B");
        assertTrue(grafo.getQuantidadeDeArestas(arestaAB.inversa()) == 2, "Deve haver duas arestas entre B e A");
    }

    @Test
    void removeArestaTest() {
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");
        Aresta arestaAB = new Aresta(verticeA, verticeB);

        grafo.addAresta(arestaAB);

        grafo.removeAresta(arestaAB);
        assertFalse(grafo.existeAresta(arestaAB), "Aresta entre A e B não deve existir após remoção");
        assertFalse(grafo.existeAresta(arestaAB.inversa()),
                "Aresta entre B e A não deve existir após remoção (grafo não direcionado)");
    }

    @Test
    void removeArestasParalelasTest() {
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");

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
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");
        Vertice verticeC = new Vertice("C");
        Vertice verticeD = new Vertice("D");

        Aresta arestaAB = new Aresta(verticeA, verticeB);
        Aresta arestaBC = new Aresta(verticeB, verticeC);

        grafo.addVertice(verticeD);
        grafo.addArestas(arestaAB, arestaBC);

        assertEquals(1, grafo.getGrau(verticeA), "Grau do vértice A deve ser 1");
        assertEquals(2, grafo.getGrau(verticeB), "Grau do vértice B deve ser 2");
        assertEquals(1, grafo.getGrau(verticeC), "Grau do vértice C deve ser 1");
        assertEquals(0, grafo.getGrau(verticeD), "Grau do vértice D deve ser 0");
    }

    @Test
    void getGrauDeEntradaESaidaTest() {
        Vertice verticeA = new Vertice("A");
        Vertice verticeB = new Vertice("B");
        Vertice verticeC = new Vertice("C");
        Vertice verticeD = new Vertice("D");

        Aresta arestaAB = new Aresta(verticeA, verticeB);
        Aresta arestaBC = new Aresta(verticeB, verticeC);

        grafo.addArestas(arestaAB, arestaBC);
        grafo.addVertice(verticeD);

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
    }
}

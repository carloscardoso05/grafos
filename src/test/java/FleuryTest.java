import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.MethodSource;

import grafo.Aresta;
import grafo.Vertice;
import grafo.nao_orientado.GrafoNaoDirecionado;
import grafo.util.Fleury;

@ParameterizedClass
@MethodSource("Provider#grafosNaoDirecionados")
public class FleuryTest {
    @Parameter
    GrafoNaoDirecionado grafo;

    @BeforeEach
    void beforeEach() {
        grafo.resetar();
    }

    @Test
    void fleuryTest() {
        Vertice verticeA = grafo.addVertice(new Vertice("A"));
        Vertice verticeB = grafo.addVertice(new Vertice("B"));
        Vertice verticeC = grafo.addVertice(new Vertice("C"));
        Vertice verticeD = grafo.addVertice(new Vertice("D"));

        grafo.addArestas(
                new Aresta("AB", verticeA, verticeB),
                new Aresta("BC", verticeB, verticeC),
                new Aresta("CD", verticeC, verticeD),
                new Aresta("DA", verticeD, verticeA),
                new Aresta("AC1", verticeA, verticeC),
                new Aresta("AC2", verticeA, verticeC));

        List<Aresta> caminho = (new Fleury(grafo, verticeA)).getCaminhoEuleriano();

        assertEquals(grafo.getArestas().size(), caminho.size());
    }
}

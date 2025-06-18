import grafo.Aresta;
import grafo.Vertice;
import grafo.algoritmos.BellmoreNemhauser;
import grafo.nao_orientado.GrafoNaoDirecionado;
import grafo.nao_orientado.GrafoNaoDirecionadoPorLista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ParameterizedClass
@MethodSource("Provider#grafosNaoDirecionados")
public class BellmoreNemhauserTest {
    @Parameter
    GrafoNaoDirecionado grafo;

    @BeforeEach
    void beforeEach() {
        grafo.resetar();
    }

    @Test
    void testBellmoreNemhauserComPesos() {
        Vertice verticeA = grafo.addVertice(new Vertice("A"));
        Vertice verticeB = grafo.addVertice(new Vertice("B"));
        Vertice verticeC = grafo.addVertice(new Vertice("C"));
        Vertice verticeD = grafo.addVertice(new Vertice("D"));

        grafo.addArestas(
                new Aresta("AB", verticeA, verticeB, 10.0),
                new Aresta("AC", verticeA, verticeC, 5.0),
                new Aresta("AD", verticeA, verticeD, 8.0),
                new Aresta("BC", verticeB, verticeC, 3.0),
                new Aresta("BD", verticeB, verticeD, 2.0),
                new Aresta("CD", verticeC, verticeD, 7.0));

        BellmoreNemhauser guloso = new BellmoreNemhauser(grafo, verticeA);

        List<Vertice> caminho = guloso.getCaminhoHamiltonianoVertices();
        List<Aresta> arestas = guloso.getCaminhoHamiltonianoArestas();
        double custo = guloso.getMelhorCusto();

        assertNotNull(caminho, "Caminho não deve ser nulo");
        assertNotNull(arestas, "Arestas não devem ser nulas");

        assertEquals(verticeA, caminho.get(0), "Deve começar no vértice inicial");
        assertEquals(verticeA, caminho.get(caminho.size() - 1), "Deve terminar no vértice inicial");

        assertEquals(5, caminho.size(), "Deve ter 5 vértices no caminho (4 únicos + retorno)");

        assertEquals(4, arestas.size(), "Deve ter 4 arestas no ciclo");

        assertTrue(custo > 0 && custo != Double.MAX_VALUE,
                "Custo deve ser positivo e finito, foi: " + custo);

        System.out.println("Caminho encontrado: " + caminho);
        System.out.println("Custo total: " + custo);
        System.out.println("Arestas: " + arestas);
    }

    @Test
    void testGrafoCompleto3Vertices() {
        Vertice v1 = grafo.addVertice(new Vertice("1"));
        Vertice v2 = grafo.addVertice(new Vertice("2"));
        Vertice v3 = grafo.addVertice(new Vertice("3"));

        grafo.addArestas(
                new Aresta("12", v1, v2, 1.0),
                new Aresta("13", v1, v3, 4.0),
                new Aresta("23", v2, v3, 2.0));

        BellmoreNemhauser guloso = new BellmoreNemhauser(grafo, v1);

        double custo = guloso.getMelhorCusto();
        List<Vertice> caminho = guloso.getCaminhoHamiltonianoVertices();

        // O menor ciclo deve ser 1->2->3->1 com custo 1+2+4=7
        assertEquals(7.0, custo, 0.001, "Custo mínimo deve ser 7.0");
        assertEquals(4, caminho.size(), "Caminho deve ter 4 vértices");

        System.out.println("Teste grafo 3 vértices:");
        System.out.println("Caminho: " + caminho);
        System.out.println("Custo: " + custo);
    }
}

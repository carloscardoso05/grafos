import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import grafo.Aresta;
import grafo.Vertice;

public class ArestaTest {
    @Test
    void criarArestaTest() {
        assertThrows(NullPointerException.class, () -> {
            Vertice origem = new Vertice("A");
            new Aresta("A - null", origem, null);
        }, "Aresta com destino nulo deve lançar NullPointerException");

        assertThrows(NullPointerException.class, () -> {
            Vertice destino = new Vertice("B");
            new Aresta("null - B", null, destino);
        }, "Aresta com origem nula deve lançar NullPointerException");
    }

    @Test
    void arestaInversaTest() {
        Vertice origem = new Vertice("A");
        Vertice destino = new Vertice("B");
        Aresta aresta = new Aresta("AB", origem, destino);
        Aresta inversa = new Aresta("BA", destino, origem);

        assertTrue(aresta.sentidoInverso(inversa), "Aresta inversa deve ser igual à aresta original invertida");
        assertTrue(aresta.sentidoInverso(aresta.inversa()), "Aresta inversa deve ser igual à aresta original invertida");
    }
}

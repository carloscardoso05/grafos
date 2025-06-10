import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import grafo.Aresta;
import grafo.Vertice;

public class ArestaTest {
    @Test
    void criarArestaTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Vertice origem = new Vertice(1);
            new Aresta(origem, null);
        }, "Aresta com destino nulo deve lançar IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, () -> {
            Vertice destino = new Vertice(1);
            new Aresta(null, destino);
        }, "Aresta com origem nula deve lançar IllegalArgumentException");
    }

    @Test
    void arestaInversaTest() {
        Vertice origem = new Vertice(1);
        Vertice destino = new Vertice(2);
        Aresta aresta = new Aresta(origem, destino);
        Aresta inversa = new Aresta(destino, origem);

        assertEquals(inversa, aresta.inversa(),
                "Aresta inversa deve ser igual à aresta original invertida");
    }
}

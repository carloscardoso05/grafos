import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ArestaTest {
    @Test
    void criarArestaTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Vertice origem = new Vertice("A");
            new Aresta(origem, null);
        }, "Aresta com destino nulo deve lançar IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, () -> {
            Vertice destino = new Vertice("B");
            new Aresta(null, destino);
        }, "Aresta com origem nula deve lançar IllegalArgumentException");
    }

    @Test
    void arestaInversaTest() {
        Vertice origem = new Vertice("A");
        Vertice destino = new Vertice("B");
        Aresta aresta = new Aresta(origem, destino);
        Aresta inversa = new Aresta(destino, origem);

        assertEquals(inversa, aresta.inversa(),
                "Aresta inversa deve ser igual à aresta original invertida");
    }
}

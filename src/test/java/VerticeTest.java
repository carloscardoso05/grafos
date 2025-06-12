import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import grafo.Vertice;

public class VerticeTest {
    @Test
    void criarVerticeTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Vertice("");
        }, "Deveria lançar IllegalArgumentException para vértice com label vazio");
        assertThrows(IllegalArgumentException.class, () -> {
            new Vertice(null);
        }, "Deveria lançar IllegalArgumentException para vértice com label nulo");
    }
}

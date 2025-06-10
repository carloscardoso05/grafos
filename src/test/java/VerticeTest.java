import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import grafo.Vertice;

public class VerticeTest {
    @Test
    void criarVerticeTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Vertice(-1);
        }, "Deveria lançar IllegalArgumentException para vértice com id negativo");
    }
}

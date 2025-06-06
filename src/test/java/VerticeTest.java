import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class VerticeTest {
    @Test
    void criarVerticeTest() {
        assertDoesNotThrow(() -> {
            new Vertice("A");
        }, "Deveria criar um vértice sem exceção");

        assertThrows(IllegalArgumentException.class, () -> {
            new Vertice(null);
        }, "Deveria lançar IllegalArgumentException para vértice com label nulo");

        assertThrows(IllegalArgumentException.class, () -> {
            new Vertice("   ");
        }, "Deveria lançar IllegalArgumentException para vértice com label vazio");
    }
}

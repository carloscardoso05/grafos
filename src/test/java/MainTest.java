import grafo.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainTest {
	@Test
	void testMain() {
		assertDoesNotThrow(() -> Main.main(null), "Main method should run without exceptions");
	}
}

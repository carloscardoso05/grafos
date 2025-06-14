package grafo.util;
import java.util.Collection;

public abstract class Util {
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isEmpty(CharSequence charSequence) {
		return charSequence == null || charSequence.isEmpty();
	}

	public static boolean isBlank(CharSequence charSequence) {
		return charSequence == null || charSequence.toString().isBlank();
	}
}

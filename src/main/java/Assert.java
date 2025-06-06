import java.util.Collection;

public abstract class Assert {
	public static void notNull(Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException("Argumento não pode ser nulo");
		}
	}

	public static void notEmpty(CharSequence charSequence) {
		if (Util.isEmpty(charSequence)) {
			throw new IllegalArgumentException("String não pode ser nula ou vazia");
		}
	}

	public static void notEmpty(Collection<?> collection) {
		if (Util.isEmpty(collection)) {
			throw new IllegalArgumentException("Coleção não pode ser nula ou vazia");
		}
	}
}

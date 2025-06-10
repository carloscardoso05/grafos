import java.util.Collection;

public abstract class Assert {
	public static void notNull(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(CharSequence charSequence, String message) {
		if (Util.isEmpty(charSequence)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(Collection<?> collection, String message) {
		if (Util.isEmpty(collection)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notBlank(CharSequence charSequence, String message) {
		if (Util.isBlank(charSequence)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isTrue(boolean condition, String message) {
		if (!condition) {
			throw new IllegalArgumentException(message);
		}
	}
}

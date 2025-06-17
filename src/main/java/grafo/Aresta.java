package grafo;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Strings;

import java.util.function.UnaryOperator;

public record Aresta(String label, Vertice origem, Vertice destino, double peso) {
	public Aresta(String label, Vertice origem, Vertice destino) {
		this(label, origem, destino, 0);
	}

	public Aresta {
		checkArgument(!Strings.isNullOrEmpty(label), "Rótulo da aresta não pode ser nulo ou vazio");
		checkNotNull(origem, "Vértice de origem não pode ser nulo");
		checkNotNull(destino, "Vértice de destino não pode ser nulo");
	}

	public Aresta inversa() {
		return new Aresta(label + "- inversa", destino, origem, peso);
	}

	public boolean mesmoLabel(Aresta outra) {
		return this.label.equals(outra.label);
	}

	/**
	 * Verifica se a aresta tem o mesmo sentido que outra aresta.
	 * <br>
	 * O sentido é considerado o mesmo se as arestas tiverem a mesma origem e
	 * destino.
	 * <br>
	 * Exemplo: A -> B é o mesmo sentido que A -> B. A -> B não é o mesmo sentido
	 * que B -> A.
	 *
	 * @param outra
	 * @return se as arestas têm o mesmo sentido
	 * @see #mesmaDirecao(Aresta outra)
	 */
	public boolean mesmoSentido(Aresta outra) {
		return this.origem.equals(outra.origem) && this.destino.equals(outra.destino);
	}

	/**
	 * Verifica se a aresta é inversa de outra aresta.
	 * <br>
	 * A aresta é considerada inversa se a origem for o destino da outra e o
	 * destino for a origem da outra.
	 * <br>
	 * Exemplo: A -> B é inversa de B -> A.
	 *
	 * @param outra
	 * @return se as arestas são inversas
	 */
	public boolean sentidoInverso(Aresta outra) {
		return this.origem.equals(outra.destino) && this.destino.equals(outra.origem);
	}

	/**
	 * Verifica se a aresta tem a mesma direção que outra aresta.
	 * <br>
	 * A direção é considerada a mesma se as arestas tiverem as duas extremidades
	 * iguais, ou seja
	 * se o sentido for o mesmo ou se forem inversas.
	 * <br>
	 * Exemplo: A -> B é a mesma direção que B -> A.
	 *
	 * @param outra
	 * @return se as arestas têm a mesma direção
	 * @see #mesmoSentido(Aresta outra)
	 * @see #inversa()
	 */
	public boolean mesmaDirecao(Aresta outra) {
		return mesmoSentido(outra) || sentidoInverso(outra);
	}

	public Aresta comLabel(String novoLabel) {
		checkArgument(!Strings.isNullOrEmpty(novoLabel), "Rótulo da aresta não pode ser nulo ou vazio");
		return new Aresta(novoLabel, origem, destino, peso);
	}

	public Aresta comLabel(UnaryOperator<String> funcaoLabel) {
		checkNotNull(funcaoLabel, "Função de rótulo não pode ser nula");
		return comLabel(funcaoLabel.apply(label));
	}

	public boolean ehLaco() {
		return origem.equals(destino);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Aresta outra)) {
			return false;
		}
		return mesmoLabel(outra);
	}

	@Override
	public int hashCode() {
		return label.hashCode();
	}

	public boolean conecta(Vertice origem, Vertice destino) {
		return origem.equals(origem()) && destino.equals(destino()) || origem.equals(destino()) && destino.equals(origem());
	}
}

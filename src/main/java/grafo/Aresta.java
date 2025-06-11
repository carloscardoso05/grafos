package grafo;

import static com.google.common.base.Preconditions.checkNotNull;

public record Aresta(String label, Vertice origem, Vertice destino, double peso) {
	public Aresta(String label, Vertice origem, Vertice destino) {
		this(label, origem, destino, 0);
	}

	public Aresta {
		checkNotNull(label, "Rótulo da aresta não pode ser nulo");
		checkNotNull(origem, "Vértice de origem não pode ser nulo");
		checkNotNull(destino, "Vértice de destino não pode ser nulo");
	}

	public Aresta inversa() {
		return new Aresta(label + "- inversa", destino, origem, peso);
	}
}

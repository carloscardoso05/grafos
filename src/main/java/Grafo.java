import java.util.List;
import java.util.Set;

public abstract class Grafo {
	public abstract void addAresta(Aresta aresta);

	public abstract void removeAresta(Aresta aresta);

	public abstract void addVertice(Vertice vertice);

	public abstract void removeVertice(Vertice vertice);

	public abstract boolean existeAresta(Aresta aresta);

	public abstract boolean existeVertice(Vertice vertice);

	public abstract int grau(Vertice vertice);

	public abstract List<Aresta> getArestas();

	public abstract List<Aresta> getArestas(Vertice vertice);

	public abstract Set<Vertice> getVertices();

	public abstract Set<Vertice> getVizinhos(Vertice vertice);
}

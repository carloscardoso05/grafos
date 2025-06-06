public class Main {
	public static void main(String[] args) {
		Grafo grafo = new GrafoNaoDirecionadoPorLista();

		Vertice v1 = new Vertice("A");
		Vertice v2 = new Vertice("B");
		Vertice v3 = new Vertice("C");
		Vertice v4 = new Vertice("D");

		grafo.addVertice(v1);
		grafo.addVertice(v2);
		grafo.addVertice(v3);
		grafo.addVertice(v4);

		grafo.addAresta(new Aresta(v1, v2));
		grafo.addAresta(new Aresta(v2, v3));
		grafo.addAresta(new Aresta(v3, v4));
		grafo.addAresta(new Aresta(v4, v1));
		grafo.addAresta(new Aresta(v1, v3));

		System.out.println(grafo);
	}
}

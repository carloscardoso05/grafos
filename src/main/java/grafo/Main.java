package grafo;

import grafo.nao_orientado.GrafoNaoDirecionadoPorLista;

public class Main {
	public static void main(String[] args) {
		Grafo grafo = new GrafoNaoDirecionadoPorLista();

		Vertice v1 = grafo.addVertice();
		Vertice v2 = grafo.addVertice();
		Vertice v3 = grafo.addVertice();
		Vertice v4 = grafo.addVertice();

		grafo.addAresta(new Aresta(v1, v2));
		grafo.addAresta(new Aresta(v2, v3));
		grafo.addAresta(new Aresta(v3, v4));
		grafo.addAresta(new Aresta(v4, v1));
		grafo.addAresta(new Aresta(v1, v3));

		System.out.println(grafo);
	}
}

package grafo;

import grafo.nao_orientado.GrafoNaoDirecionadoPorLista;

public class Main {
	public static void main(String[] args) {
		Grafo grafo = new GrafoNaoDirecionadoPorLista();

		Vertice verticeA = new Vertice("A");
		Vertice verticeB = new Vertice("B");
		Vertice verticeC = new Vertice("C");
		Vertice verticeD = new Vertice("D");

		grafo.addVertice(verticeA);
		grafo.addVertice(verticeB);
		grafo.addVertice(verticeC);
		grafo.addVertice(verticeD);

		grafo.addAresta(new Aresta("AB", verticeA, verticeB));
		grafo.addAresta(new Aresta("BC", verticeB, verticeC));
		grafo.addAresta(new Aresta("CD", verticeC, verticeD));
		grafo.addAresta(new Aresta("DA", verticeD, verticeA));
		grafo.addAresta(new Aresta("AC", verticeA, verticeC));

		System.out.println(grafo);
	}
}

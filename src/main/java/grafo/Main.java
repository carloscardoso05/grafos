package grafo;

import java.util.List;

import grafo.algoritmos.BFS;
import grafo.nao_orientado.GrafoNaoDirecionadoPorLista;

public class Main {
	public static void main(String[] args) {
		Grafo grafo = new GrafoNaoDirecionadoPorLista();

		// Criando vértices
		Vertice verticeA = new Vertice("A");
		Vertice verticeB = new Vertice("B");
		Vertice verticeC = new Vertice("C");
		Vertice verticeD = new Vertice("D");
		Vertice verticeE = new Vertice("E");
		Vertice verticeF = new Vertice("F");

		// Adicionando vértices ao grafo
		grafo.addVertices(verticeA, verticeB, verticeC, verticeD, verticeE, verticeF);

		// Criando arestas para formar um grafo conexo
		grafo.addAresta(new Aresta("AB", verticeA, verticeB));
		grafo.addAresta(new Aresta("AC", verticeA, verticeC));
		grafo.addAresta(new Aresta("BC", verticeB, verticeC));
		grafo.addAresta(new Aresta("BD", verticeB, verticeD));
		grafo.addAresta(new Aresta("CD", verticeC, verticeD));
		grafo.addAresta(new Aresta("DE", verticeD, verticeE));
		// Vértice F fica isolado para demonstrar vértices não alcançáveis

		System.out.println("Grafo criado:");
		System.out.println(grafo);
		System.out.println();

		// Teste do algoritmo BFS
		System.out.println("=== Teste do Algoritmo BFS ===");
		BFS bfs = new BFS(grafo, verticeA);

		System.out.println("Vértice de origem: " + verticeA.label());
		System.out.println();

		// Testando caminhos para diferentes vértices
		Vertice[] destinos = {verticeB, verticeC, verticeD, verticeE, verticeF};
		
		for (Vertice destino : destinos) {
			System.out.println("Análise do caminho até " + destino.label() + ":");
			
			if (bfs.existeCaminhoAte(destino)) {
				int distancia = bfs.getDistanciaEntre(destino);
				List<Vertice> caminho = bfs.getCaminhoAte(destino);
				
				System.out.println("  ✓ Caminho existe");
				System.out.println("  ✓ Distância: " + distancia + " aresta(s)");
				System.out.println("  ✓ Caminho: " + caminho.stream()
					.map(Vertice::label)
					.reduce((a, b) -> a + " → " + b)
					.orElse("Erro"));
			} else {
				System.out.println("  ✗ Não existe caminho!");
			}
			System.out.println();
		}

		// Demonstrando diferentes origens
		System.out.println("=== Teste com origem diferente (Vértice D) ===");
		BFS bfsD = new BFS(grafo, verticeD);
		
		System.out.println("Caminhos a partir de " + verticeD.label() + ":");
		System.out.println("  Para A: distância = " + bfsD.getDistanciaEntre(verticeA) + " → " + 
			bfsD.getCaminhoAte(verticeA).stream().map(Vertice::label).reduce((a, b) -> a + " → " + b).orElse(""));
		System.out.println("  Para E: distância = " + bfsD.getDistanciaEntre(verticeE) + " → " + 
			bfsD.getCaminhoAte(verticeE).stream().map(Vertice::label).reduce((a, b) -> a + " → " + b).orElse(""));
	}
}

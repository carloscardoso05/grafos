//public class ArestaBidirecional extends Aresta {
//	public ArestaBidirecional(Vertice origem, Vertice destino) {
//		super(origem, destino);
//	}
//
//	public ArestaBidirecional(Vertice origem, Vertice destino, double peso) {
//		super(origem, destino, peso);
//	}
//
//	@Override
//	public Aresta inversa() {
//		return new ArestaBidirecional(destino(), origem(), peso());
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (obj == this) return true;
//		if (obj == null) return false;
//		if (!(obj instanceof ArestaBidirecional outraAresta)) return false;
//		if (peso() != outraAresta.peso()) return false;
//		return (origem().equals(outraAresta.origem()) && destino().equals(outraAresta.destino())) ||
//				(origem().equals(outraAresta.destino()) && destino().equals(outraAresta.origem()));
//	}
//}

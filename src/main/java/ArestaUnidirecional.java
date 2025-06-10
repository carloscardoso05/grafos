//public class ArestaUnidirecional extends Aresta {
//	public ArestaUnidirecional(Vertice origem, Vertice destino) {
//		super(origem, destino);
//	}
//
//	public ArestaUnidirecional(Vertice origem, Vertice destino, double peso) {
//		super(origem, destino, peso);
//	}
//
//	@Override
//	public Aresta inversa() {
//		return new ArestaUnidirecional(destino(), origem(), peso());
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (obj == this) return true;
//		if (obj == null ) return false;
//		if (!(obj instanceof ArestaUnidirecional outraAresta)) return false;
//		if (peso() != outraAresta.peso()) return false;
//		return origem().equals(outraAresta.origem()) && destino().equals(outraAresta.destino());
//	}
//}

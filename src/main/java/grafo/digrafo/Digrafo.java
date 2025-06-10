package grafo.digrafo;

import grafo.Aresta;
import grafo.Grafo;
import grafo.util.Assert;

public abstract class Digrafo extends Grafo {

	@Override
	public int getQuantidadeDeArestas(Aresta aresta) {
		Assert.notNull(aresta, MSG_ARESTA_NULA);
		return (int) getArestas().stream().filter(aresta::equals).count();
	}
}

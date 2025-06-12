package grafo;

import com.google.common.base.Strings;

import java.util.function.UnaryOperator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public record Vertice(String label) {
    public Vertice {
        checkArgument(!Strings.isNullOrEmpty(label), "Rótulo do vértice não pode ser nulo ou vazio");
    }

    public Aresta formarAresta(String label, Vertice destino) {
        checkArgument(!Strings.isNullOrEmpty(label), "Rótulo da aresta não pode ser nulo ou vazio");
        return new Aresta(label, this, destino);
    }

    public Vertice comLabel(UnaryOperator<String> funcao) {
        checkNotNull(funcao, "Função não pode ser nula");

        String novoLabel = funcao.apply(label);
        checkArgument(!Strings.isNullOrEmpty(novoLabel), "Rótulo do vértice resultante não pode ser nulo ou vazio");
        return new Vertice(novoLabel);
    }
}

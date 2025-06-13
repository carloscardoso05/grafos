import java.util.stream.Stream;

import grafo.Grafo;
import grafo.digrafo.Digrafo;
import grafo.digrafo.DigrafoPorLista;
import grafo.nao_orientado.GrafoNaoDirecionado;
import grafo.nao_orientado.GrafoNaoDirecionadoPorLista;

public abstract class Provider {
    public static Stream<Digrafo> digrafos() {
        return Stream.of(new DigrafoPorLista());
    }

    public static Stream<GrafoNaoDirecionado> grafosNaoDirecionados() {
        return Stream.of(new GrafoNaoDirecionadoPorLista());
    }

    public static Stream<Grafo> grafos() {
        return Stream.concat(digrafos(), grafosNaoDirecionados());
    }

}

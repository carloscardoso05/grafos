import java.util.stream.Stream;

import grafo.Grafo;
import grafo.digrafo.Digrafo;
import grafo.digrafo.DigrafoPorLista;
import grafo.digrafo.DigrafoPorMatriz;
import grafo.nao_orientado.GrafoNaoDirecionado;
import grafo.nao_orientado.GrafoNaoDirecionadoPorLista;
import grafo.nao_orientado.GrafoNaoDirecionadoPorMatriz;

public abstract class Provider {
    public static Stream<Digrafo> digrafos() {
        return Stream.of(new DigrafoPorLista(), new DigrafoPorMatriz());
    }

    public static Stream<GrafoNaoDirecionado> grafosNaoDirecionados() {
        return Stream.of(new GrafoNaoDirecionadoPorLista(), new GrafoNaoDirecionadoPorMatriz());
    }

    public static Stream<Grafo> grafos() {
        return Stream.concat(digrafos(), grafosNaoDirecionados());
    }

}

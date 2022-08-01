package br.com.samoel.testeenvixo.compare;

import java.util.Comparator;

import br.com.samoel.testeenvixo.model.Categoria;
import br.com.samoel.testeenvixo.model.Post;

public class ComparePost implements Comparator<Categoria> {


    @Override
    public int compare(Categoria categoria, Categoria t1) {
        if (categoria.getNome().compareTo(t1.getNome()) > 0 ){
            return -1;
        }
        return 1;
    }
}

package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum TipoSaidaProduto {

    VENDA(0, "venda"),
    BONIFICACAO(1, "bonif"),
    RETIRADA(2, "retirada"),
    CORTESIA(3, "cortesia"),
    AMOSTRA(4, "amostra"),
    TESTE(5, "teste");

    private int cod;
    private String descricao;

    private TipoSaidaProduto(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static List<TipoSaidaProduto> getList() {
        List list = Arrays.asList(TipoSaidaProduto.values());
        Collections.sort(list, new Comparator<TipoSaidaProduto>() {
            @Override
            public int compare(TipoSaidaProduto e1, TipoSaidaProduto e2) {
                return e1.getDescricao().compareTo(e2.getDescricao());
            }
        });
        return list;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return getDescricao();
    }

}

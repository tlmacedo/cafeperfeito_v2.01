package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum TipoSaidaProduto {

    VENDA(102, "venda"),
    BONIFICACAO(910, "bonif"),
    RETIRADA(557, "retirada"),
    CORTESIA(910, "cortesia"),
    AMOSTRA(911, "amostra"),
    TESTE(912, "teste");

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

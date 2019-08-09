package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum TipoEndereco {

    PRINCIPAL(1, "Principal"),
    ENTREGA(2, "Entrega"),
    COBRANCA(3, "Cobrança"),
    CORRESPONDENCIA(4, "Correspondência"),
    RESIDENCIAL(5, "Residencial"),
    RECADO(6, "Recado");

    private Integer cod;
    private String descricao;

    private TipoEndereco(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static TipoEndereco toEnum(Integer cod) {
        if (cod == null) return null;
        for (TipoEndereco tipo : TipoEndereco.getList())
            if (cod == tipo.getCod())
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }

    public static List<TipoEndereco> getList() {
        List list = Arrays.asList(TipoEndereco.values());
        Collections.sort(list, new Comparator<TipoEndereco>() {
            @Override
            public int compare(TipoEndereco e1, TipoEndereco e2) {
                return e1.getDescricao().compareTo(e2.getDescricao());
            }
        });
        return list;
    }

    public Integer getCod() {
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

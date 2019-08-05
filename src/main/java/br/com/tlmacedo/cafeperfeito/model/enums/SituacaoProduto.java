package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum SituacaoProduto {

    INATIVO(0, "Inativo"),
    ATIVO(1, "Ativo"),
    TESTE(2, "Teste"),
    AMOSTRA(3, "Amostra"),
    SUSPENSO(4, "Suspenso"),
    PROPRIO(5, "Próprio"),
    CANCELADO(6, "Cancelado");

    private int cod;
    private String descricao;

    private SituacaoProduto(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static SituacaoProduto toEnum(Integer cod) {
        if (cod == null) return null;
        for (SituacaoProduto tipo : SituacaoProduto.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }

    public static List<SituacaoProduto> getList() {
        List list = Arrays.asList(SituacaoProduto.values());
        Collections.sort(list, new Comparator<SituacaoProduto>() {
            @Override
            public int compare(SituacaoProduto e1, SituacaoProduto e2) {
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

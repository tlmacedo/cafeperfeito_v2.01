package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum ColaboradorSituacao {

    DESATIVADO(0, "Desativado"),
    ATIVO(1, "Ativo");

    private int cod;
    private String descricao;

    private ColaboradorSituacao(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static ColaboradorSituacao toEnum(Integer cod) {
        if (cod == null) return null;
        for (ColaboradorSituacao tipo : ColaboradorSituacao.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }

    public static List<ColaboradorSituacao> getList() {
        List list = Arrays.asList(ColaboradorSituacao.values());
        Collections.sort(list, new Comparator<ColaboradorSituacao>() {
            @Override
            public int compare(ColaboradorSituacao e1, ColaboradorSituacao e2) {
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

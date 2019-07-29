package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum SituacaoCadastroEmpresa {

    INATIVO(0, "Inativo"),
    ATIVO(1, "Ativo"),
    POTENCIAL(2, "Potencial"),
    ANALISE(3, "Análise"),
    SUSPENSO(4, "Suspenso"),
    FECHADO(5, "Fechado"),
    CANCELADO(6, "Cancelado");

    private int cod;
    private String descricao;

    private SituacaoCadastroEmpresa(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static SituacaoCadastroEmpresa toEnum(Integer cod) {
        if (cod == null) return null;
        for (SituacaoCadastroEmpresa tipo : SituacaoCadastroEmpresa.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }

    public static List<SituacaoCadastroEmpresa> getList() {
        List list = Arrays.asList(SituacaoCadastroEmpresa.values());
        Collections.sort(list, new Comparator<SituacaoCadastroEmpresa>() {
            @Override
            public int compare(SituacaoCadastroEmpresa e1, SituacaoCadastroEmpresa e2) {
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

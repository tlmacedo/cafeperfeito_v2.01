package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum EnumsTasks {

    TABELA_CRIAR(0, "criando tabela de "),
    TABELA_VINCULAR(1, "vinculando tModel"),
    TABELA_PREENCHER(2, "preenchendo tabela de "),
    COMBOS_PREENCHER(3, "carregando informações do cadastro"),
    SALVAR_SAIDA(4, "salvando"),
    ADD_RECEBIMENTO(5, "adicionando recebimento"),
    UPDATE_RECEBIMENTO(6, "editando recebimento");

    private int cod;
    private String descricao;

    private EnumsTasks(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static EnumsTasks toEnum(Integer cod) {
        if (cod == null) return null;
        for (EnumsTasks tipo : EnumsTasks.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }

    public static List<EnumsTasks> getList() {
        List list = Arrays.asList(EnumsTasks.values());
        Collections.sort(list, new Comparator<EnumsTasks>() {
            @Override
            public int compare(EnumsTasks e1, EnumsTasks e2) {
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

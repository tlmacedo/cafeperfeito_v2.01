package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum PagamentoModalidade {

    DINHEIRO(0, "Dinheiro"),
    CARTAO(1, "Cartão"),
    BOLETO(2, "Boleto"),
    TRANSFERENCIA(3, "Transferência"),
    ORDEM_BANCARIA(4, "Ordem bancária"),
    RETIRADA(5, "Retirada"),
    BONIFICACAO(6, "Bonificação"),
    AMOSTRA(7, "Amostra"),
    CREDITO_BAIXA(8, "Crédito baixado"),
    CREDITO(9, "Crédito");

    private Integer cod;
    private String descricao;

    private PagamentoModalidade(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

//    public static TipoSaidaProduto toEnum(Integer cod) {
//        if (cod == null) return null;
//        for (TipoSaidaProduto tipo : TipoSaidaProduto.values())
//            if (cod == tipo.getCod())
//                return tipo;
//        throw new IllegalArgumentException("Id inválido");
//    }

    public static List<PagamentoModalidade> getList() {
        List list = Arrays.asList(PagamentoModalidade.values());
        Collections.sort(list, new Comparator<PagamentoModalidade>() {
            @Override
            public int compare(PagamentoModalidade e1, PagamentoModalidade e2) {
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

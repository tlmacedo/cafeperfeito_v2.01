package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum NfeCobrancaDuplicataPagamentoIndicador {

    AVISTA(0, "Pagamento à Vista"),
    PRAZO(1, "Pagamento à Prazo");

    private Integer cod;
    private String descricao;

    private NfeCobrancaDuplicataPagamentoIndicador(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static List<NfeCobrancaDuplicataPagamentoIndicador> getList() {
        List list = Arrays.asList(NfeCobrancaDuplicataPagamentoIndicador.values());
        Collections.sort(list, new Comparator<NfeCobrancaDuplicataPagamentoIndicador>() {
            @Override
            public int compare(NfeCobrancaDuplicataPagamentoIndicador e1, NfeCobrancaDuplicataPagamentoIndicador e2) {
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

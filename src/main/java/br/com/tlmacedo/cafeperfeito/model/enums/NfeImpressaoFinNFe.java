package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum NfeImpressaoFinNFe {

    NORMAL(1, "NF-e normal"),
    COMPLEMENTAR(2, "NF-e complementar"),
    AJUSTE(3, "NF-e de ajuste"),
    DEVOLUCAO(4, "Devolução de mercadoria");

    private int cod;
    private String descricao;

    private NfeImpressaoFinNFe(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static List<NfeImpressaoFinNFe> getList() {
        List list = Arrays.asList(NfeImpressaoFinNFe.values());
        Collections.sort(list, new Comparator<NfeImpressaoFinNFe>() {
            @Override
            public int compare(NfeImpressaoFinNFe e1, NfeImpressaoFinNFe e2) {
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

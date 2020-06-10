package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum NfeDadosDestinoOperacao {

    INTERNA(1, "Operação interna"),
    EXTERNA(2, "Operação interestadual"),
    EXTERIOR(3, "Operação com exterior");

    private Integer cod;
    private String descricao;

    private NfeDadosDestinoOperacao(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static List<NfeDadosDestinoOperacao> getList() {
        List list = Arrays.asList(NfeDadosDestinoOperacao.values());
        Collections.sort(list, new Comparator<NfeDadosDestinoOperacao>() {
            @Override
            public int compare(NfeDadosDestinoOperacao e1, NfeDadosDestinoOperacao e2) {
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

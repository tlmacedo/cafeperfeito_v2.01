package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum NfeDadosNaturezaOperacao {

    INTERNA(0, "DENTRO DO ESTADO"),
    EXTERNA(1, "FORA DO ESTADO"),
    EXTERIOR(2, "FORA DO PAÍS");

    private int cod;
    private String descricao;

    private NfeDadosNaturezaOperacao(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static List<NfeDadosNaturezaOperacao> getList() {
        List list = Arrays.asList(NfeDadosNaturezaOperacao.values());
        Collections.sort(list, new Comparator<NfeDadosNaturezaOperacao>() {
            @Override
            public int compare(NfeDadosNaturezaOperacao e1, NfeDadosNaturezaOperacao e2) {
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

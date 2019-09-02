package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum NfeImpressaoTpImp {

    SEM_DANFE(0, "Sem geração de DANFE"),
    RETRATO(1, "DANFE normal, Retrato"),
    PAISAGEM(2, "DANFE normal, Paisagem"),
    SIMPLIFICADO(3, "DANFE Simplificado"),
    NFCE(4, "DANFE NFC-e"),
    NFCE_MSG(5, "DANFE NFC-e em msg eletrônica");

    private int cod;
    private String descricao;

    private NfeImpressaoTpImp(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static NfeImpressaoTpImp toEnum(Integer cod) {
        if (cod == null) return null;
        for (NfeImpressaoTpImp tipo : NfeImpressaoTpImp.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }

    public static List<NfeImpressaoTpImp> getList() {
        List list = Arrays.asList(NfeImpressaoTpImp.values());
        Collections.sort(list, new Comparator<NfeImpressaoTpImp>() {
            @Override
            public int compare(NfeImpressaoTpImp e1, NfeImpressaoTpImp e2) {
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
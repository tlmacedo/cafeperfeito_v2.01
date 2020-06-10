package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum NfeDadosIndicadorConsumidorFinal {

    NORMAL(0, "Normal"),
    FINAL(1, "Consumidor final");

    private Integer cod;
    private String descricao;

    private NfeDadosIndicadorConsumidorFinal(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static List<NfeDadosIndicadorConsumidorFinal> getList() {
        List list = Arrays.asList(NfeDadosIndicadorConsumidorFinal.values());
        Collections.sort(list, new Comparator<NfeDadosIndicadorConsumidorFinal>() {
            @Override
            public int compare(NfeDadosIndicadorConsumidorFinal e1, NfeDadosIndicadorConsumidorFinal e2) {
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

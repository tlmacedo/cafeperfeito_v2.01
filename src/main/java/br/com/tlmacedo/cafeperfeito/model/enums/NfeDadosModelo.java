package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum NfeDadosModelo {

    MOD55(55, "55"),
    MOD01(1, "01"),
    MOD57(57, "57"),
    MOD65(65, "Nfc-e");

    private int cod;
    private String descricao;

    private NfeDadosModelo(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static List<NfeDadosModelo> getList() {
        List list = Arrays.asList(NfeDadosModelo.values());
        Collections.sort(list, new Comparator<NfeDadosModelo>() {
            @Override
            public int compare(NfeDadosModelo e1, NfeDadosModelo e2) {
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

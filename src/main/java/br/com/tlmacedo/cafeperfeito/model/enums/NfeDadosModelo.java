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

    private Integer cod;
    private String descricao;

    private NfeDadosModelo(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static NfeDadosModelo toEnum(Integer cod) {
        if (cod == null) return null;
        for (NfeDadosModelo tipo : NfeDadosModelo.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
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

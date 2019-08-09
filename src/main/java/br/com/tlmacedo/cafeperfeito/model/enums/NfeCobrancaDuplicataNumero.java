package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum NfeCobrancaDuplicataNumero {

    NULL(0, null),
    N001(1, "1"),
    N002(2, "2"),
    N003(3, "3"),
    N004(4, "4");

    private int cod;
    private String descricao;

    private NfeCobrancaDuplicataNumero(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static NfeCobrancaDuplicataNumero toEnum(Integer cod) {
        if (cod == null) return null;
        for (NfeCobrancaDuplicataNumero tipo : NfeCobrancaDuplicataNumero.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }

    public static List<NfeCobrancaDuplicataNumero> getList() {
        List list = Arrays.asList(NfeCobrancaDuplicataNumero.values());
        Collections.sort(list, new Comparator<NfeCobrancaDuplicataNumero>() {
            @Override
            public int compare(NfeCobrancaDuplicataNumero e1, NfeCobrancaDuplicataNumero e2) {
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

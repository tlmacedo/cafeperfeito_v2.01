package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum NfeDadosIndicadorPresenca {

    SEM_APLICACAO(0, "Não se aplica (NF compl ou ajuste)"),
    PRESENCIAL(1, "Operação presencial"),
    INTERNET(2, "Operação não presencial, pela Internet"),
    TELEATENDIMENTO(3, "Operação não presencial, Teleatendimento"),
    DOMICILIO(4, "NFC-e em operação com entrega a domicílio"),
    FORA_ESTABELECIMENTO(5, "Operação presencial, fora do estabelecimento"),
    OUTROS(9, "Operação não presencial, outros");

    private int cod;
    private String descricao;

    private NfeDadosIndicadorPresenca(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static NfeDadosIndicadorPresenca toEnum(Integer cod) {
        if (cod == null) return null;
        for (NfeDadosIndicadorPresenca tipo : NfeDadosIndicadorPresenca.values())
            if (cod.equals(tipo.getCod()))
                return tipo;
        throw new IllegalArgumentException("Id inválido");
    }

    public static List<NfeDadosIndicadorPresenca> getList() {
        List list = Arrays.asList(NfeDadosIndicadorPresenca.values());
        Collections.sort(list, new Comparator<NfeDadosIndicadorPresenca>() {
            @Override
            public int compare(NfeDadosIndicadorPresenca e1, NfeDadosIndicadorPresenca e2) {
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

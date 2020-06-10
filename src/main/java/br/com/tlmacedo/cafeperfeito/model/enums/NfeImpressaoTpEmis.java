package br.com.tlmacedo.cafeperfeito.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum NfeImpressaoTpEmis {

    NORMAL(1, "Emissão normal (não em contingência)"),
    FS_IA(2, "Contingência FS-IA, com impressão do DANFE em Formulário de Segurança - Impressor Autônomo"),
    SCAN(3, "Contingência SCAN (Sistema de Contingência do Ambiente Nacional)"),
    EPEC(4, "Contingência EPEC (Evento Prévio da Emissão em Contingência)"),
    FS_DA(5, "Contingência FS-DA, com impressão do DANFE em Formulário de Segurança - Documento Auxiliar"),
    SVC_AN(6, "Contingência SVC-AN (SEFAZ Virtual de Contingência do AN)"),
    SVC_RS(7, "Contingência SVC-RS (SEFAZ Virtual de Contingência do RS)"),
    OFF(9, "Contingência off-line da NFC-e");

    private Integer cod;
    private String descricao;

    private NfeImpressaoTpEmis(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static List<NfeImpressaoTpEmis> getList() {
        List list = Arrays.asList(NfeImpressaoTpEmis.values());
        Collections.sort(list, new Comparator<NfeImpressaoTpEmis>() {
            @Override
            public int compare(NfeImpressaoTpEmis e1, NfeImpressaoTpEmis e2) {
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

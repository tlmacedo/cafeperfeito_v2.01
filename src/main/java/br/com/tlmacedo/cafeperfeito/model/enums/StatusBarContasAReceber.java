package br.com.tlmacedo.cafeperfeito.model.enums;

public enum StatusBarContasAReceber {

    DIGITACAO(0, "[Insert-Novo recebimento]  [Ctrl+P-Imprimir recibo]  [F4-Editar recebimento]  [F6-Cliente]  [F7-Pesquisa]  [F8-Datas]  [F9-Contas]  [F12-Sair]");

    private Integer cod;
    private String descricao;

    private StatusBarContasAReceber(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

//    public static UndComercialProduto toEnum(Integer cod) {
//        if (cod == null) return null;
//        for (UndComercialProduto tipo : UndComercialProduto.values())
//            if (cod == tipo.getCod())
//                return tipo;
//        throw new IllegalArgumentException("Id inválido");
//    }
//
//    public static List<UndComercialProduto> getList() {
//        List list = Arrays.asList(UndComercialProduto.values());
//        Collections.sort(list, new Comparator<UndComercialProduto>() {
//            @Override
//            public int compare(UndComercialProduto e1, UndComercialProduto e2) {
//                return e1.getDescricao().compareTo(e2.getDescricao());
//            }
//        });
//        return list;
//    }

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

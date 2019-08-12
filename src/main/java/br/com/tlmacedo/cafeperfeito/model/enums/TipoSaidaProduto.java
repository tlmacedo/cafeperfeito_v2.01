package br.com.tlmacedo.cafeperfeito.model.enums;

public enum TipoSaidaProduto {

    VENDA(0, "venda"),
    BONIFICACAO(1, "bonif"),
    RETIRADA(2, "retirada"),
    AMOSTRA(3, "amostra"),
    TESTE(4, "teste"),
    CORTESIA(5, "cortesia");

    private Integer cod;
    private String descricao;

    private TipoSaidaProduto(Integer cod, String descricao) {
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

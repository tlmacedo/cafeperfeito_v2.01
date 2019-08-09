package br.com.tlmacedo.cafeperfeito.model.enums;

public enum UndComercialProduto {

    UNIDADE(0, "UND"),
    PACOTE(1, "PCT"),
    PESO(2, "KG"),
    FARDO(3, "FD"),
    CAIXA(4, "CX"),
    VIDRO(5, "VD"),
    DUZIA(6, "DZ"),
    LATA(7, "LT");

    private Integer cod;
    private String descricao;

    private UndComercialProduto(Integer cod, String descricao) {
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

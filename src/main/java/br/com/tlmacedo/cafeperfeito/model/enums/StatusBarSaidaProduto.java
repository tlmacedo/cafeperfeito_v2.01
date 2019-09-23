package br.com.tlmacedo.cafeperfeito.model.enums;

public enum StatusBarSaidaProduto {

    DIGITACAO(0, "[F1-Novo]  [F2-Finalizar venda]  [F6-Cliente]  [F7-Pesquisa produto]  [F8-Itens venda]  [F9-nfe]  [F12-Sair]"),
    FINALIZADA(1, "[F12-Sair]");

    private int cod;
    private String descricao;

    private StatusBarSaidaProduto(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
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

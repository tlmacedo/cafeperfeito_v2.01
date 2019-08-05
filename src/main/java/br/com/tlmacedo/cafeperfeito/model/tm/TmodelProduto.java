package br.com.tlmacedo.cafeperfeito.model.tm;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TmodelProduto {

    private final String tipo;
    private Label lblRegistrosLocalizados;
    private TextField txtPesquisa;


    public TmodelProduto(String tipo) {
        this.tipo = tipo;
    }
}

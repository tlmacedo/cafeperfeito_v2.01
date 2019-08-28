package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRecebimento implements Initializable, ModeloCafePerfeito {


    public AnchorPane painelViewSaidaProduto;
    public TitledPane tpnRecebimento;
    public ComboBox cboPagamentoTipo;
    public TextField txtDocumento;
    public DatePicker dtpDtVencimento;
    public TextField txtValor;
    public Button btnOK;
    public Button btnCancel;
    private Stage recebimentoStage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        criarObjetos();
        preencherObjetos();
        fatorarObjetos();
        escutarTecla();
        Platform.runLater(() -> {
            //setLoginStage(ViewLogin.getStage());
        });
    }

    @Override
    public void fechar() {

    }

    @Override
    public void criarObjetos() {

    }

    @Override
    public void preencherObjetos() {

    }

    @Override
    public void fatorarObjetos() {

    }

    @Override
    public void escutarTecla() {

    }


}

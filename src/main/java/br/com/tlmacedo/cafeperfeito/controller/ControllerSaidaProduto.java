package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.service.ServiceCampoPersonalizado;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerSaidaProduto implements Initializable, ModeloCafePerfeito {


    public AnchorPane painelViewSaidaProduto;

    public TitledPane tpnCliente;
    public ComboBox<Empresa> cboEmpresa;
    public TextField txtLimite;
    public TextField txtLimiteUtilizado;
    public TextField txtLimiteDisponivel;
    public TextField txtPrazo;
    public ComboBox cboEndereco;
    public TextField txtLogradoruro;
    public TextField txtNumero;
    public TextField txtBairro;
    public TextField txtComplemento;
    public ComboBox cboTelefone;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarObjetos();
        preencherObjetos();
        fatorarObjetos();
        escutarTecla();
        ServiceCampoPersonalizado.fieldClear(getPainelViewSaidaProduto());
        ServiceCampoPersonalizado.fieldTextFormat(getPainelViewSaidaProduto());
    }

    @Override
    public void fechar() {

    }

    @Override
    public void criarObjetos() {

    }

    @Override
    public void preencherObjetos() {
        getCboEmpresa().setItems(new EmpresaDAO().getAll(Empresa.class, null, null, null, "razao")
                .stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    @Override
    public void fatorarObjetos() {

    }

    @Override
    public void escutarTecla() {

    }


    public AnchorPane getPainelViewSaidaProduto() {
        return painelViewSaidaProduto;
    }

    public void setPainelViewSaidaProduto(AnchorPane painelViewSaidaProduto) {
        this.painelViewSaidaProduto = painelViewSaidaProduto;
    }

    public TitledPane getTpnCliente() {
        return tpnCliente;
    }

    public void setTpnCliente(TitledPane tpnCliente) {
        this.tpnCliente = tpnCliente;
    }

    public ComboBox<Empresa> getCboEmpresa() {
        return cboEmpresa;
    }

    public void setCboEmpresa(ComboBox<Empresa> cboEmpresa) {
        this.cboEmpresa = cboEmpresa;
    }

    public TextField getTxtLimite() {
        return txtLimite;
    }

    public void setTxtLimite(TextField txtLimite) {
        this.txtLimite = txtLimite;
    }
}

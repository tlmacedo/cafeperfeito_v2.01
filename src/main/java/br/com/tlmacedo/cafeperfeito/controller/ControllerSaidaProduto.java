package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.Endereco;
import br.com.tlmacedo.cafeperfeito.service.ServiceCampoPersonalizado;
import javafx.beans.binding.Bindings;
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
    public ComboBox<Endereco> cboEndereco;
    public TextField txtLogradoruro;
    public TextField txtNumero;
    public TextField txtBairro;
    public TextField txtComplemento;
    public ComboBox cboTelefone;

    private Endereco endereco = new Endereco();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarObjetos();
        preencherObjetos();
        fatorarObjetos();
        escutarTecla();
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
        getCboEmpresa().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            getCboEndereco().setItems(n.getEnderecoList().stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
        });

        getCboEndereco().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            setEndereco(n);
        });

        getTxtLogradoruro().textProperty().bind(
                Bindings.createStringBinding(() -> {
                            if (getCboEndereco().getSelectionModel().getSelectedItem() == null)
                                return "";
                            else
                                return getCboEndereco().getSelectionModel().getSelectedItem().logradouroProperty().get();
                        }, getCboEndereco().getSelectionModel().selectedItemProperty()
                ));
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

    public TextField getTxtLimiteUtilizado() {
        return txtLimiteUtilizado;
    }

    public void setTxtLimiteUtilizado(TextField txtLimiteUtilizado) {
        this.txtLimiteUtilizado = txtLimiteUtilizado;
    }

    public TextField getTxtLimiteDisponivel() {
        return txtLimiteDisponivel;
    }

    public void setTxtLimiteDisponivel(TextField txtLimiteDisponivel) {
        this.txtLimiteDisponivel = txtLimiteDisponivel;
    }

    public TextField getTxtPrazo() {
        return txtPrazo;
    }

    public void setTxtPrazo(TextField txtPrazo) {
        this.txtPrazo = txtPrazo;
    }

    public ComboBox<Endereco> getCboEndereco() {
        return cboEndereco;
    }

    public void setCboEndereco(ComboBox<Endereco> cboEndereco) {
        this.cboEndereco = cboEndereco;
    }

    public TextField getTxtLogradoruro() {
        return txtLogradoruro;
    }

    public void setTxtLogradoruro(TextField txtLogradoruro) {
        this.txtLogradoruro = txtLogradoruro;
    }

    public TextField getTxtNumero() {
        return txtNumero;
    }

    public void setTxtNumero(TextField txtNumero) {
        this.txtNumero = txtNumero;
    }

    public TextField getTxtBairro() {
        return txtBairro;
    }

    public void setTxtBairro(TextField txtBairro) {
        this.txtBairro = txtBairro;
    }

    public TextField getTxtComplemento() {
        return txtComplemento;
    }

    public void setTxtComplemento(TextField txtComplemento) {
        this.txtComplemento = txtComplemento;
    }

    public ComboBox getCboTelefone() {
        return cboTelefone;
    }

    public void setCboTelefone(ComboBox cboTelefone) {
        this.cboTelefone = cboTelefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}

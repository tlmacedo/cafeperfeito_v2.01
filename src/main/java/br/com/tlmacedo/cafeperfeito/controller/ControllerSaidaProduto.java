package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.Endereco;
import br.com.tlmacedo.cafeperfeito.service.ServiceAlertMensagem;
import br.com.tlmacedo.cafeperfeito.service.ServiceCampoPersonalizado;
import br.com.tlmacedo.cafeperfeito.service.ServiceSegundoPlano;
import br.com.tlmacedo.cafeperfeito.service.autoComplete.ServiceAutoCompleteComboBox;
import br.com.tlmacedo.cafeperfeito.view.ViewSaidaProduto;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    public TextField txtUltimoPedidoDt;
    public TextField txtUltimoPedidoDias;
    public TextField txtUltimoPedidoVlr;
    public TextField txtQtdPedidos;
    public TextField txtTicketMedioVlr;
    public ComboBox<Endereco> cboEndereco;
    public TextField txtLogradoruro;
    public TextField txtNumero;
    public TextField txtBairro;
    public TextField txtComplemento;
    public ComboBox cboTelefone;

    private boolean tabCarregada = false;
    private List<Pair> listaTarefa = new ArrayList<>();

    private Tab saidaProdutoTab = ViewSaidaProduto.getTab();
    private String nomeTab = ViewSaidaProduto.getTitulo();
    private String nomeController = "saidaProduto";

    private Task<Void> taskSaidaProduto = newTaskSaidaProduto();
    private EventHandler eventHandlerSaidaProduto;
    private ServiceAlertMensagem alertMensagem;

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
        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getTabs().remove(getSaidaProdutoTab());
        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerSaidaProduto());
    }

    @Override
    public void criarObjetos() {
        //getListaTarefa().add(new Pair());
    }

    @Override
    public void preencherObjetos() {

        getListaTarefa().add(new Pair("preencherCombo", "carregando informações do cadastro"));

        setTabCarregada(new ServiceSegundoPlano().abrindoCadastro(getTaskSaidaProduto(), "Abrindo saida de produtos!"));

    }

    @Override
    public void fatorarObjetos() {

    }

    @Override
    public void escutarTecla() {

        setEventHandlerSaidaProduto(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().getSelectedIndex() < 0)
                    return;
                if (!ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().getSelectedItem().getText().equals(getNomeTab()))
                    return;
                switch (event.getCode()) {

                }
            }
        });

        ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            if (ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().getSelectedItem() == getSaidaProdutoTab())
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().addEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerSaidaProduto());
            else
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerSaidaProduto());
        });

        new ServiceAutoCompleteComboBox(Empresa.class, getCboEmpresa());

        getCboEmpresa().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            getCboEndereco().setItems(n.getEnderecoList().stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
            getCboEndereco().getSelectionModel().select(0);
            getCboTelefone().setItems(n.getTelefoneList().stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
            getCboTelefone().getSelectionModel().select(0);
        });

        getTxtLogradoruro().textProperty().bind(
                Bindings.createStringBinding(() -> {
                            if (getCboEndereco().getSelectionModel().getSelectedItem() == null)
                                return "";
                            else
                                return getCboEndereco().getSelectionModel().getSelectedItem().logradouroProperty().get();
                        }, getCboEndereco().getSelectionModel().selectedItemProperty()
                ));

        getTxtNumero().textProperty().bind(
                Bindings.createStringBinding(() -> {
                            if (getCboEndereco().getSelectionModel().getSelectedItem() == null)
                                return "";
                            else
                                return getCboEndereco().getSelectionModel().getSelectedItem().numeroProperty().get();
                        }, getCboEndereco().getSelectionModel().selectedItemProperty()
                ));

        getTxtBairro().textProperty().bind(
                Bindings.createStringBinding(() -> {
                            if (getCboEndereco().getSelectionModel().getSelectedItem() == null)
                                return "";
                            else
                                return getCboEndereco().getSelectionModel().getSelectedItem().bairroProperty().get();
                        }, getCboEndereco().getSelectionModel().selectedItemProperty()
                ));

        getTxtComplemento().textProperty().bind(
                Bindings.createStringBinding(() -> {
                            if (getCboEndereco().getSelectionModel().getSelectedItem() == null)
                                return "";
                            else
                                return getCboEndereco().getSelectionModel().getSelectedItem().complementoProperty().get();
                        }, getCboEndereco().getSelectionModel().selectedItemProperty()
                ));

        //getCboTelefone().valueProperty().bind(getCboEmpresa().getValue().getTelefoneList().get(0).descricaoProperty());
    }

    /**
     * Begin Tasks
     */

    private Task newTaskSaidaProduto() {
        int qtdTarefas = getListaTarefa().size();
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Loading...");
                for (Pair tarefa : getListaTarefa()) {
                    updateProgress(getListaTarefa().indexOf(tarefa), qtdTarefas);
                    Thread.sleep(200);
                    updateMessage(tarefa.getValue().toString());
                    switch (tarefa.getKey().toString().toLowerCase()) {
                        case "preenchercombo":
                            getCboEmpresa().setItems(
                                    new EmpresaDAO().getAll(Empresa.class, null, null, null, "razao")
                                            .stream().filter(Empresa::isCliente)
                                            .collect(Collectors.toCollection(FXCollections::observableArrayList))
                            );

                            break;
                    }
                }
                updateMessage("Carregamento concluido!!!");
                updateProgress(qtdTarefas, qtdTarefas);
                return null;
            }
        };
    }

    /**
     * END Tasks
     */

    /**
     * Begin voids
     */

    private void limpaCampos(AnchorPane anchorPane) {
        ServiceCampoPersonalizado.fieldClear(anchorPane);
        if (anchorPane == getPainelViewSaidaProduto())
            getCboEmpresa().requestFocus();
    }

    /**
     * END voids
     */


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

    public TextField getTxtUltimoPedidoDt() {
        return txtUltimoPedidoDt;
    }

    public void setTxtUltimoPedidoDt(TextField txtUltimoPedidoDt) {
        this.txtUltimoPedidoDt = txtUltimoPedidoDt;
    }

    public TextField getTxtUltimoPedidoDias() {
        return txtUltimoPedidoDias;
    }

    public void setTxtUltimoPedidoDias(TextField txtUltimoPedidoDias) {
        this.txtUltimoPedidoDias = txtUltimoPedidoDias;
    }

    public TextField getTxtUltimoPedidoVlr() {
        return txtUltimoPedidoVlr;
    }

    public void setTxtUltimoPedidoVlr(TextField txtUltimoPedidoVlr) {
        this.txtUltimoPedidoVlr = txtUltimoPedidoVlr;
    }

    public TextField getTxtQtdPedidos() {
        return txtQtdPedidos;
    }

    public void setTxtQtdPedidos(TextField txtQtdPedidos) {
        this.txtQtdPedidos = txtQtdPedidos;
    }

    public TextField getTxtTicketMedioVlr() {
        return txtTicketMedioVlr;
    }

    public void setTxtTicketMedioVlr(TextField txtTicketMedioVlr) {
        this.txtTicketMedioVlr = txtTicketMedioVlr;
    }

    public boolean isTabCarregada() {
        return tabCarregada;
    }

    public void setTabCarregada(boolean tabCarregada) {
        this.tabCarregada = tabCarregada;
    }

    public Tab getSaidaProdutoTab() {
        return saidaProdutoTab;
    }

    public void setSaidaProdutoTab(Tab saidaProdutoTab) {
        this.saidaProdutoTab = saidaProdutoTab;
    }

    public List<Pair> getListaTarefa() {
        return listaTarefa;
    }

    public void setListaTarefa(List<Pair> listaTarefa) {
        this.listaTarefa = listaTarefa;
    }

    public EventHandler getEventHandlerSaidaProduto() {
        return eventHandlerSaidaProduto;
    }

    public void setEventHandlerSaidaProduto(EventHandler eventHandlerSaidaProduto) {
        this.eventHandlerSaidaProduto = eventHandlerSaidaProduto;
    }

    public String getNomeTab() {
        return nomeTab;
    }

    public void setNomeTab(String nomeTab) {
        this.nomeTab = nomeTab;
    }

    public String getNomeController() {
        return nomeController;
    }

    public void setNomeController(String nomeController) {
        this.nomeController = nomeController;
    }

    public Task<Void> getTaskSaidaProduto() {
        return taskSaidaProduto;
    }

    public void setTaskSaidaProduto(Task<Void> taskSaidaProduto) {
        this.taskSaidaProduto = taskSaidaProduto;
    }

    public ServiceAlertMensagem getAlertMensagem() {
        return alertMensagem;
    }

    public void setAlertMensagem(ServiceAlertMensagem alertMensagem) {
        this.alertMensagem = alertMensagem;
    }
}

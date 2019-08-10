package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.ProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.*;
import br.com.tlmacedo.cafeperfeito.model.tm.TmodelProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.Endereco;
import br.com.tlmacedo.cafeperfeito.model.vo.Produto;
import br.com.tlmacedo.cafeperfeito.model.vo.Telefone;
import br.com.tlmacedo.cafeperfeito.service.ServiceAlertMensagem;
import br.com.tlmacedo.cafeperfeito.service.ServiceCampoPersonalizado;
import br.com.tlmacedo.cafeperfeito.service.ServiceSegundoPlano;
import br.com.tlmacedo.cafeperfeito.service.autoComplete.ServiceAutoCompleteComboBox;
import br.com.tlmacedo.cafeperfeito.service.format.FormatDataPicker;
import br.com.tlmacedo.cafeperfeito.view.ViewSaidaProduto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerSaidaProduto implements Initializable, ModeloCafePerfeito {


    public AnchorPane painelViewSaidaProduto;

    public TitledPane tpnCliente;
    public DatePicker dtpDtSaida;
    public DatePicker dtpDtVencimento;
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
    public ComboBox<Telefone> cboTelefone;

    public TitledPane tpnNfe;
    public Tab tabNfeDados;
    public ComboBox<NfeDadosNaturezaOperacao> cboNfeDadosNaturezaOperacao;
    public TextField txtNfeDadosNumero;
    public TextField txtNfeDadosSerie;
    public ComboBox<NfeDadosModelo> cboNfeDadosModelo;
    public DatePicker dtpNfeDadosDtEmissao;
    public TextField txtNfeDadosHoraEmissao;
    public DatePicker dtpNfeDadosDtSaida;
    public TextField txtNfeDadosHoraSaida;
    public ComboBox<NfeDadosDestinoOperacao> cboNfeDadosDestinoOperacao;
    public ComboBox<NfeDadosIndicadorConsumidorFinal> cboNfeDadosIndicadorConsumidorFinal;
    public ComboBox<NfeDadosIndicadorPresenca> cboNfeDadosIndicadorPresenca;

    public Tab tabNfeImpressao;
    public ComboBox<NfeImpressaoTpImp> cboNfeImpressaoTpImp;
    public ComboBox<NfeImpressaoTpEmis> cboNfeImpressaoTpEmis;
    public ComboBox<NfeImpressaoFinNFe> cboNfeImpressaoFinNFe;

    public Tab tabNfeTransporta;
    public ComboBox<NfeTransporteModFrete> cboNfeTransporteModFrete;
    public ComboBox<Empresa> cboNfeTransporteTransportadora;

    public Tab tabNfeCobranca;
    public ComboBox<NfeCobrancaDuplicataNumero> cboNfeCobrancaDuplicataNumeros;
    public TextField txtNfeCobrancaDuplicataValor;
    public ComboBox<NfeCobrancaDuplicataPagamentoIndicador> cboNfeCobrancaPagamentoIndicador;
    public ComboBox<NfeCobrancaDuplicataPagamentoMeio> cboNfeCobrancaPagamentoMeio;
    public TextField txtNfeCobrancaPagamentoValor;

    public Tab tabNfeInformacoes;
    public TextArea txaNfeInformacoesAdicionais;

    public TitledPane tpnProdutos;
    public TextField txtPesquisa;
    public Label lblRegistrosLocalizados;
    public TreeTableView ttvProdutos;

    public TitledPane tpnItensPedido;
    public TreeTableView ttvItensPedido;
    public VBox vBoxTotalLiquido;
    public Label lblTotalLiquido;


    private boolean tabCarregada = false;
    private List<EnumsTasks> enumsTasksList = new ArrayList<>();

    private String nomeTab = ViewSaidaProduto.getTitulo();
    private String nomeController = "saidaProduto";
    private Task<Void> taskSaidaProduto = newTaskSaidaProduto();
    private EventHandler eventHandlerSaidaProduto;
    private ServiceAlertMensagem alertMensagem;

    private Tab saidaProdutoTab = ViewSaidaProduto.getTab();
    private TmodelProduto tmodelProduto;
    private ObservableList<Produto> produtoObservableList = FXCollections.observableArrayList(new ProdutoDAO().getAll(Produto.class, null, null, null, "descricao"));
    private FilteredList<Produto> produtoFilteredList = new FilteredList<>(getProdutoObservableList());


    private Endereco endereco = new Endereco();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarObjetos();
        preencherObjetos();
        fatorarObjetos();
        escutarTecla();
        ServiceCampoPersonalizado.fieldTextFormat(getPainelViewSaidaProduto());
        Platform.runLater(() -> limpaCampos(getPainelViewSaidaProduto()));
    }

    @Override
    public void fechar() {
        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getTabs().remove(getSaidaProdutoTab());
        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerSaidaProduto());
    }

    @Override
    public void criarObjetos() {
        getEnumsTasksList().add(EnumsTasks.TABELA_CRIAR);
    }

    @Override
    public void preencherObjetos() {
        getEnumsTasksList().add(EnumsTasks.TABELA_VINCULAR);

        getEnumsTasksList().add(EnumsTasks.TABELA_PREENCHER);

        getEnumsTasksList().add(EnumsTasks.COMBOS_PREENCHER);

        setTabCarregada(new ServiceSegundoPlano().abrindoCadastro(getTaskSaidaProduto(), String.format("Abrindo %s!", getNomeTab())));

    }

    @Override
    public void fatorarObjetos() {

        getDtpDtSaida().setDayCellFactory(param -> new FormatDataPicker(null));
        getDtpDtVencimento().setDayCellFactory(param -> new FormatDataPicker(null));
        getDtpNfeDadosDtEmissao().setDayCellFactory(param -> new FormatDataPicker(null));
        getDtpNfeDadosDtSaida().setDayCellFactory(param -> new FormatDataPicker(null));

        getDtpDtSaida().valueProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            getDtpDtVencimento().setValue(n);
            getDtpDtVencimento().setDayCellFactory(param -> new FormatDataPicker(n));
        });

        getDtpNfeDadosDtEmissao().valueProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            getDtpNfeDadosDtSaida().setValue(n);
            getDtpNfeDadosDtSaida().setDayCellFactory(param -> new FormatDataPicker(n));
        });


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
            getTxtLimite().setText(String.valueOf(n.getLimite()));
            getTxtPrazo().setText(String.valueOf(n.getPrazo()));

        });

        getTxtPrazo().textProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            getDtpDtVencimento().setValue(getDtpDtSaida().getValue().plusDays(Integer.parseInt(n)));
        });

        getDtpDtSaida().valueProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            getDtpDtVencimento().setValue(getDtpDtSaida().getValue().plusDays(Integer.parseInt(getTxtPrazo().getText())));
        });

        getCboEndereco().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            if (n == null) {
                limpaEndereco();
                return;
            }
            getTxtLogradoruro().setText(n.getLogradouro());
            getTxtNumero().setText(n.getNumero());
            getTxtBairro().setText(n.getBairro());
            getTxtComplemento().setText(n.getComplemento());
        });

        getTabNfeInformacoes().selectedProperty().addListener((ov, o, n) -> {
            int diff = 21;
            if (!n) diff = (diff * (-1));
            getTpnNfe().setPrefHeight(getTpnNfe().getPrefHeight() + diff);
            getTpnProdutos().setLayoutY(getTpnProdutos().getLayoutY() + diff);
            getTpnItensPedido().setPrefHeight(getTpnItensPedido().getPrefHeight() + (diff * -1));
            getTpnItensPedido().setLayoutY(getTpnItensPedido().getLayoutY() + diff);
            getTtvItensPedido().setPrefHeight(getTtvItensPedido().getPrefHeight() + (diff * -1));
            getvBoxTotalLiquido().setLayoutY(getvBoxTotalLiquido().getLayoutY() + (diff * -1));

            //getTtvItens().setPrefHeight(getTtvItens().getPrefWidth() + diff);
//            if (n)
//                getTpnNfe().setPrefHeight(getTpnNfe().getPrefHeight() + 21);
//            else
//                getTpnNfe().setPrefHeight(getTpnNfe().getPrefHeight() - 21);
        });
    }

    /**
     * Begin Tasks
     */

    private Task newTaskSaidaProduto() {
        int qtdTasks = getEnumsTasksList().size();
        final int[] cont = {1};
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Loading...");
                for (EnumsTasks tasks : getEnumsTasksList()) {
                    updateProgress(cont[0]++, qtdTasks);
                    Thread.sleep(200);
                    updateMessage(String.format("%s%s", tasks.getDescricao(),
                            tasks.getDescricao().endsWith(" de ") ? getNomeController() : ""));
                    switch (tasks) {
                        case TABELA_CRIAR:
                            setTmodelProduto(new TmodelProduto(TModelTipo.PROD_VENDA));
                            getTmodelProduto().criaTabela();

                            break;

                        case TABELA_VINCULAR:
                            getTmodelProduto().setLblRegistrosLocalizados(getLblRegistrosLocalizados());
                            getTmodelProduto().setTtvProduto(getTtvProdutos());
                            getTmodelProduto().setTxtPesquisa(getTxtPesquisa());
                            getTmodelProduto().setProdutoObservableList(getProdutoObservableList());
                            getTmodelProduto().setProdutoFilteredList(getProdutoFilteredList());

                            getTmodelProduto().escutaLista();

                            break;

                        case COMBOS_PREENCHER:
                            getCboEmpresa().setItems(
                                    new EmpresaDAO().getAll(Empresa.class, null, null, null, "razao")
                                            .stream().filter(Empresa::isCliente)
                                            .collect(Collectors.toCollection(FXCollections::observableArrayList))
                            );

                            getCboNfeDadosNaturezaOperacao().setItems(
                                    NfeDadosNaturezaOperacao.getList()
                                            .stream()
                                            .collect(Collectors.toCollection(FXCollections::observableArrayList))
                            );

                            break;

                        case TABELA_PREENCHER:
                            getTmodelProduto().preencheTabela();
                            break;
                    }
                }
                updateMessage("tarefa concluída!!!");
                updateProgress(qtdTasks, qtdTasks);
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

    private void limpaEndereco() {
        getTxtLogradoruro().setText("");
        getTxtNumero().setText("");
        getTxtBairro().setText("");
        getTxtComplemento().setText("");
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

    public DatePicker getDtpDtSaida() {
        return dtpDtSaida;
    }

    public void setDtpDtSaida(DatePicker dtpDtSaida) {
        this.dtpDtSaida = dtpDtSaida;
    }

    public DatePicker getDtpDtVencimento() {
        return dtpDtVencimento;
    }

    public void setDtpDtVencimento(DatePicker dtpDtVencimento) {
        this.dtpDtVencimento = dtpDtVencimento;
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

    public ComboBox<Telefone> getCboTelefone() {
        return cboTelefone;
    }

    public void setCboTelefone(ComboBox<Telefone> cboTelefone) {
        this.cboTelefone = cboTelefone;
    }

    public TitledPane getTpnNfe() {
        return tpnNfe;
    }

    public void setTpnNfe(TitledPane tpnNfe) {
        this.tpnNfe = tpnNfe;
    }

    public Tab getTabNfeDados() {
        return tabNfeDados;
    }

    public void setTabNfeDados(Tab tabNfeDados) {
        this.tabNfeDados = tabNfeDados;
    }

    public ComboBox<NfeDadosNaturezaOperacao> getCboNfeDadosNaturezaOperacao() {
        return cboNfeDadosNaturezaOperacao;
    }

    public void setCboNfeDadosNaturezaOperacao(ComboBox<NfeDadosNaturezaOperacao> cboNfeDadosNaturezaOperacao) {
        this.cboNfeDadosNaturezaOperacao = cboNfeDadosNaturezaOperacao;
    }

    public TextField getTxtNfeDadosNumero() {
        return txtNfeDadosNumero;
    }

    public void setTxtNfeDadosNumero(TextField txtNfeDadosNumero) {
        this.txtNfeDadosNumero = txtNfeDadosNumero;
    }

    public TextField getTxtNfeDadosSerie() {
        return txtNfeDadosSerie;
    }

    public void setTxtNfeDadosSerie(TextField txtNfeDadosSerie) {
        this.txtNfeDadosSerie = txtNfeDadosSerie;
    }

    public ComboBox<NfeDadosModelo> getCboNfeDadosModelo() {
        return cboNfeDadosModelo;
    }

    public void setCboNfeDadosModelo(ComboBox<NfeDadosModelo> cboNfeDadosModelo) {
        this.cboNfeDadosModelo = cboNfeDadosModelo;
    }

    public DatePicker getDtpNfeDadosDtEmissao() {
        return dtpNfeDadosDtEmissao;
    }

    public void setDtpNfeDadosDtEmissao(DatePicker dtpNfeDadosDtEmissao) {
        this.dtpNfeDadosDtEmissao = dtpNfeDadosDtEmissao;
    }

    public TextField getTxtNfeDadosHoraEmissao() {
        return txtNfeDadosHoraEmissao;
    }

    public void setTxtNfeDadosHoraEmissao(TextField txtNfeDadosHoraEmissao) {
        this.txtNfeDadosHoraEmissao = txtNfeDadosHoraEmissao;
    }

    public DatePicker getDtpNfeDadosDtSaida() {
        return dtpNfeDadosDtSaida;
    }

    public void setDtpNfeDadosDtSaida(DatePicker dtpNfeDadosDtSaida) {
        this.dtpNfeDadosDtSaida = dtpNfeDadosDtSaida;
    }

    public TextField getTxtNfeDadosHoraSaida() {
        return txtNfeDadosHoraSaida;
    }

    public void setTxtNfeDadosHoraSaida(TextField txtNfeDadosHoraSaida) {
        this.txtNfeDadosHoraSaida = txtNfeDadosHoraSaida;
    }

    public ComboBox<NfeDadosDestinoOperacao> getCboNfeDadosDestinoOperacao() {
        return cboNfeDadosDestinoOperacao;
    }

    public void setCboNfeDadosDestinoOperacao(ComboBox<NfeDadosDestinoOperacao> cboNfeDadosDestinoOperacao) {
        this.cboNfeDadosDestinoOperacao = cboNfeDadosDestinoOperacao;
    }

    public ComboBox<NfeDadosIndicadorConsumidorFinal> getCboNfeDadosIndicadorConsumidorFinal() {
        return cboNfeDadosIndicadorConsumidorFinal;
    }

    public void setCboNfeDadosIndicadorConsumidorFinal(ComboBox<NfeDadosIndicadorConsumidorFinal> cboNfeDadosIndicadorConsumidorFinal) {
        this.cboNfeDadosIndicadorConsumidorFinal = cboNfeDadosIndicadorConsumidorFinal;
    }

    public ComboBox<NfeDadosIndicadorPresenca> getCboNfeDadosIndicadorPresenca() {
        return cboNfeDadosIndicadorPresenca;
    }

    public void setCboNfeDadosIndicadorPresenca(ComboBox<NfeDadosIndicadorPresenca> cboNfeDadosIndicadorPresenca) {
        this.cboNfeDadosIndicadorPresenca = cboNfeDadosIndicadorPresenca;
    }

    public Tab getTabNfeImpressao() {
        return tabNfeImpressao;
    }

    public void setTabNfeImpressao(Tab tabNfeImpressao) {
        this.tabNfeImpressao = tabNfeImpressao;
    }

    public ComboBox<NfeImpressaoTpImp> getCboNfeImpressaoTpImp() {
        return cboNfeImpressaoTpImp;
    }

    public void setCboNfeImpressaoTpImp(ComboBox<NfeImpressaoTpImp> cboNfeImpressaoTpImp) {
        this.cboNfeImpressaoTpImp = cboNfeImpressaoTpImp;
    }

    public ComboBox<NfeImpressaoTpEmis> getCboNfeImpressaoTpEmis() {
        return cboNfeImpressaoTpEmis;
    }

    public void setCboNfeImpressaoTpEmis(ComboBox<NfeImpressaoTpEmis> cboNfeImpressaoTpEmis) {
        this.cboNfeImpressaoTpEmis = cboNfeImpressaoTpEmis;
    }

    public ComboBox<NfeImpressaoFinNFe> getCboNfeImpressaoFinNFe() {
        return cboNfeImpressaoFinNFe;
    }

    public void setCboNfeImpressaoFinNFe(ComboBox<NfeImpressaoFinNFe> cboNfeImpressaoFinNFe) {
        this.cboNfeImpressaoFinNFe = cboNfeImpressaoFinNFe;
    }

    public Tab getTabNfeTransporta() {
        return tabNfeTransporta;
    }

    public void setTabNfeTransporta(Tab tabNfeTransporta) {
        this.tabNfeTransporta = tabNfeTransporta;
    }

    public ComboBox<NfeTransporteModFrete> getCboNfeTransporteModFrete() {
        return cboNfeTransporteModFrete;
    }

    public void setCboNfeTransporteModFrete(ComboBox<NfeTransporteModFrete> cboNfeTransporteModFrete) {
        this.cboNfeTransporteModFrete = cboNfeTransporteModFrete;
    }

    public ComboBox<Empresa> getCboNfeTransporteTransportadora() {
        return cboNfeTransporteTransportadora;
    }

    public void setCboNfeTransporteTransportadora(ComboBox<Empresa> cboNfeTransporteTransportadora) {
        this.cboNfeTransporteTransportadora = cboNfeTransporteTransportadora;
    }

    public Tab getTabNfeCobranca() {
        return tabNfeCobranca;
    }

    public void setTabNfeCobranca(Tab tabNfeCobranca) {
        this.tabNfeCobranca = tabNfeCobranca;
    }

    public ComboBox<NfeCobrancaDuplicataNumero> getCboNfeCobrancaDuplicataNumeros() {
        return cboNfeCobrancaDuplicataNumeros;
    }

    public void setCboNfeCobrancaDuplicataNumeros(ComboBox<NfeCobrancaDuplicataNumero> cboNfeCobrancaDuplicataNumeros) {
        this.cboNfeCobrancaDuplicataNumeros = cboNfeCobrancaDuplicataNumeros;
    }

    public TextField getTxtNfeCobrancaDuplicataValor() {
        return txtNfeCobrancaDuplicataValor;
    }

    public void setTxtNfeCobrancaDuplicataValor(TextField txtNfeCobrancaDuplicataValor) {
        this.txtNfeCobrancaDuplicataValor = txtNfeCobrancaDuplicataValor;
    }

    public ComboBox<NfeCobrancaDuplicataPagamentoIndicador> getCboNfeCobrancaPagamentoIndicador() {
        return cboNfeCobrancaPagamentoIndicador;
    }

    public void setCboNfeCobrancaPagamentoIndicador(ComboBox<NfeCobrancaDuplicataPagamentoIndicador> cboNfeCobrancaPagamentoIndicador) {
        this.cboNfeCobrancaPagamentoIndicador = cboNfeCobrancaPagamentoIndicador;
    }

    public ComboBox<NfeCobrancaDuplicataPagamentoMeio> getCboNfeCobrancaPagamentoMeio() {
        return cboNfeCobrancaPagamentoMeio;
    }

    public void setCboNfeCobrancaPagamentoMeio(ComboBox<NfeCobrancaDuplicataPagamentoMeio> cboNfeCobrancaPagamentoMeio) {
        this.cboNfeCobrancaPagamentoMeio = cboNfeCobrancaPagamentoMeio;
    }

    public TextField getTxtNfeCobrancaPagamentoValor() {
        return txtNfeCobrancaPagamentoValor;
    }

    public void setTxtNfeCobrancaPagamentoValor(TextField txtNfeCobrancaPagamentoValor) {
        this.txtNfeCobrancaPagamentoValor = txtNfeCobrancaPagamentoValor;
    }

    public Tab getTabNfeInformacoes() {
        return tabNfeInformacoes;
    }

    public void setTabNfeInformacoes(Tab tabNfeInformacoes) {
        this.tabNfeInformacoes = tabNfeInformacoes;
    }

    public TextArea getTxaNfeInformacoesAdicionais() {
        return txaNfeInformacoesAdicionais;
    }

    public void setTxaNfeInformacoesAdicionais(TextArea txaNfeInformacoesAdicionais) {
        this.txaNfeInformacoesAdicionais = txaNfeInformacoesAdicionais;
    }

    public TitledPane getTpnProdutos() {
        return tpnProdutos;
    }

    public void setTpnProdutos(TitledPane tpnProdutos) {
        this.tpnProdutos = tpnProdutos;
    }

    public TextField getTxtPesquisa() {
        return txtPesquisa;
    }

    public void setTxtPesquisa(TextField txtPesquisa) {
        this.txtPesquisa = txtPesquisa;
    }

    public Label getLblRegistrosLocalizados() {
        return lblRegistrosLocalizados;
    }

    public void setLblRegistrosLocalizados(Label lblRegistrosLocalizados) {
        this.lblRegistrosLocalizados = lblRegistrosLocalizados;
    }

    public TreeTableView getTtvProdutos() {
        return ttvProdutos;
    }

    public void setTtvProdutos(TreeTableView ttvProdutos) {
        this.ttvProdutos = ttvProdutos;
    }

    public TitledPane getTpnItensPedido() {
        return tpnItensPedido;
    }

    public void setTpnItensPedido(TitledPane tpnItensPedido) {
        this.tpnItensPedido = tpnItensPedido;
    }

    public TreeTableView getTtvItensPedido() {
        return ttvItensPedido;
    }

    public void setTtvItensPedido(TreeTableView ttvItensPedido) {
        this.ttvItensPedido = ttvItensPedido;
    }

    public Label getLblTotalLiquido() {
        return lblTotalLiquido;
    }

    public void setLblTotalLiquido(Label lblTotalLiquido) {
        this.lblTotalLiquido = lblTotalLiquido;
    }

    public VBox getvBoxTotalLiquido() {
        return vBoxTotalLiquido;
    }

    public void setvBoxTotalLiquido(VBox vBoxTotalLiquido) {
        this.vBoxTotalLiquido = vBoxTotalLiquido;
    }

    /**
     *
     */
    public boolean isTabCarregada() {
        return tabCarregada;
    }

    public void setTabCarregada(boolean tabCarregada) {
        this.tabCarregada = tabCarregada;
    }

    public List<EnumsTasks> getEnumsTasksList() {
        return enumsTasksList;
    }

    public void setEnumsTasksList(List<EnumsTasks> enumsTasksList) {
        this.enumsTasksList = enumsTasksList;
    }

    public Tab getSaidaProdutoTab() {
        return saidaProdutoTab;
    }

    public void setSaidaProdutoTab(Tab saidaProdutoTab) {
        this.saidaProdutoTab = saidaProdutoTab;
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

    public EventHandler getEventHandlerSaidaProduto() {
        return eventHandlerSaidaProduto;
    }

    public void setEventHandlerSaidaProduto(EventHandler eventHandlerSaidaProduto) {
        this.eventHandlerSaidaProduto = eventHandlerSaidaProduto;
    }

    public ServiceAlertMensagem getAlertMensagem() {
        return alertMensagem;
    }

    public void setAlertMensagem(ServiceAlertMensagem alertMensagem) {
        this.alertMensagem = alertMensagem;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public TmodelProduto getTmodelProduto() {
        return tmodelProduto;
    }

    public void setTmodelProduto(TmodelProduto tmodelProduto) {
        this.tmodelProduto = tmodelProduto;
    }

    public ObservableList<Produto> getProdutoObservableList() {
        return produtoObservableList;
    }

    public void setProdutoObservableList(ObservableList<Produto> produtoObservableList) {
        this.produtoObservableList = produtoObservableList;
    }

    public FilteredList<Produto> getProdutoFilteredList() {
        return produtoFilteredList;
    }

    public void setProdutoFilteredList(FilteredList<Produto> produtoFilteredList) {
        this.produtoFilteredList = produtoFilteredList;
    }
}

package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.RecebimentoDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.EnumsTasks;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoModalidade;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoSituacao;
import br.com.tlmacedo.cafeperfeito.model.vo.ContasAReceber;
import br.com.tlmacedo.cafeperfeito.model.vo.Recebimento;
import br.com.tlmacedo.cafeperfeito.model.vo.UsuarioLogado;
import br.com.tlmacedo.cafeperfeito.service.*;
import br.com.tlmacedo.cafeperfeito.view.ViewRecebimento;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.CHAR_KEY_SHIFT_CTRL_N;
import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.CODE_KEY_SHIFT_CTRL_N;

public class ControllerRecebimento implements Initializable, ModeloCafePerfeito {


    public AnchorPane painelViewRecebimento;
    public TitledPane tpnRecebimento;
    public ComboBox<PagamentoModalidade> cboPagamentoModalidade;
    public ImageView imgNewDocumento;
    public TextField txtDocumento;
    public DatePicker dtpDtPagamento;
    public ComboBox<PagamentoSituacao> cboSituacao;
    public TextField txtValor;
    public Button btnOK;
    public Button btnCancel;

    private Stage recebimentoStage;
    private String nomeController = "recebimento";
    private List<EnumsTasks> enumsTasksList = new ArrayList<>();
    private ServiceAlertMensagem alertMensagem;

    private ContasAReceber aReceber;
    private Recebimento recebimento;
    private RecebimentoDAO recebimentoDAO = new RecebimentoDAO();
    private BigDecimal saldo = BigDecimal.ZERO;

    private BooleanProperty deshabilita = new SimpleBooleanProperty(true);

    public ControllerRecebimento() {
        setRecebimento(ViewRecebimento.getRecebimento());
        if (getRecebimento() == null) {
            setaReceber(ViewRecebimento.getaReceber());
            setSaldo(ViewRecebimento.getSaldo());
            getEnumsTasksList().add(EnumsTasks.ADD_RECEBIMENTO);
        } else {
            setRecebimento(ViewRecebimento.getRecebimento());
            setaReceber(ViewRecebimento.getaReceber());
            getEnumsTasksList().add(EnumsTasks.UPDATE_RECEBIMENTO);
            setDeshabilita(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            criarObjetos();
            preencherObjetos();
            fatorarObjetos();
            escutarTecla();
            fieldsFormat();
            Platform.runLater(() -> {
                setRecebimentoStage(ViewRecebimento.getStage());
                getCboPagamentoModalidade().requestFocus();
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void fieldsFormat() throws Exception {
        ServiceCampoPersonalizado.fieldTextFormat(getPainelViewRecebimento());
    }

    @Override
    public void fechar() {
        getRecebimentoStage().close();
    }

    @Override
    public void criarObjetos() throws Exception {

    }

    @Override
    public void preencherObjetos() throws Exception {
        getCboPagamentoModalidade().setItems(Arrays.stream(PagamentoModalidade.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
        getCboSituacao().setItems(
                Arrays.stream(PagamentoSituacao.values())
                        .filter(pagamentoSituacao -> pagamentoSituacao.getCod() != 2)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));

        if (getRecebimento() == null) {
            setRecebimento(new Recebimento());
            getRecebimento().setaReceber(getaReceber());
            getRecebimento().setPagamentoModalidade(PagamentoModalidade.DINHEIRO);
            getRecebimento().setPagamentoSituacao(PagamentoSituacao.PENDENTE);
            getRecebimento().valorProperty().setValue(getaReceber().valorProperty().getValue()
                    .subtract(getaReceber().getRecebimentoList().stream()
                            .map(Recebimento::getValor).reduce(BigDecimal.ZERO, BigDecimal::add)));
            getRecebimento().dtPagamentoProperty().setValue(getaReceber().dtVencimentoProperty().getValue());
        }
        getTxtDocumento().setText(getRecebimento().documentoProperty().getValue());
        getCboPagamentoModalidade().getSelectionModel().select(getRecebimento().getPagamentoModalidade());
        getCboSituacao().getSelectionModel().select(getRecebimento().getPagamentoSituacao());

        if (getSaldo().compareTo(BigDecimal.ZERO) < 0)
            getTxtValor().setText(ServiceMascara.getMoeda(getRecebimento().valorProperty().getValue().add(getSaldo()), 2));
        else
            getTxtValor().setText(ServiceMascara.getMoeda(getRecebimento().valorProperty().getValue(), 2));

        getDtpDtPagamento().setValue(getRecebimento().dtPagamentoProperty().getValue() != null
                ? getRecebimento().dtPagamentoProperty().getValue()
                : LocalDate.now());

    }

    @Override
    public void fatorarObjetos() throws Exception {

    }

    @Override
    public void escutarTecla() throws Exception {

        getPainelViewRecebimento().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (CODE_KEY_SHIFT_CTRL_N.match(keyEvent) || CHAR_KEY_SHIFT_CTRL_N.match(keyEvent)) {
                getNewDocumento();
            }
        });

        deshabilitaProperty().bind(Bindings.createBooleanBinding(() -> {
                    if (getRecebimento() != null)
                        getCboSituacao().getSelectionModel().select(getRecebimento().getPagamentoSituacao());
                    if (getCboPagamentoModalidade().getValue().equals(PagamentoModalidade.AMOSTRA)
                            || getCboPagamentoModalidade().getValue().equals(PagamentoModalidade.BONIFICACAO)
                            || getCboPagamentoModalidade().getValue().equals(PagamentoModalidade.RETIRADA)) {
                        getCboSituacao().getSelectionModel().select(PagamentoSituacao.QUITADO);
                        return false;
                    }

                    return (
                            ServiceMascara.getBigDecimalFromTextField(getTxtValor().getText(), 2).compareTo(BigDecimal.ZERO) <= 0
                                    && getDtpDtPagamento().getValue().compareTo(LocalDate.now().minusDays(7)) < 0
                    );
                }, getCboPagamentoModalidade().valueProperty(), getDtpDtPagamento().valueProperty(), getTxtValor().textProperty()
        ));

        getBtnCancel().setOnAction(actionEvent -> fechar());

        getBtnOK().disableProperty().bind(deshabilitaProperty());

        getBtnOK().setOnAction(actionEvent -> {
            try {
                if (new ServiceSegundoPlano().executaListaTarefas(newTaskRecebimento(), "recebimento"))
                    fechar();
                else
                    getCboPagamentoModalidade().requestFocus();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        getImgNewDocumento().addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> getNewDocumento());


    }

    /**
     * Begin Booleans
     */

    private boolean salvarRecebimento() {
        try {
            getRecebimentoDAO().transactionBegin();
            if (getaReceber() != null)
                getRecebimento().setaReceber(getaReceber());
            getRecebimento().setPagamentoSituacao(getCboSituacao().getValue());
            getRecebimento().documentoProperty().setValue(getTxtDocumento().getText());
            if (getRecebimento().documentoProperty().getValue() == null)
                getRecebimento().documentoProperty().setValue("");
            getRecebimento().setPagamentoModalidade(getCboPagamentoModalidade().getValue());
            getRecebimento().valorProperty().setValue(ServiceMascara.getBigDecimalFromTextField(getTxtValor().getText(), 2));
            getRecebimento().setUsuarioPagamento(null);
            getRecebimento().setDtPagamento(null);
            if (getRecebimento().getPagamentoSituacao().equals(PagamentoSituacao.QUITADO)) {
                getRecebimento().setUsuarioPagamento(UsuarioLogado.getUsuario());
                getRecebimento().dtPagamentoProperty().setValue(getDtpDtPagamento().getValue());
            }
            getRecebimento().setUsuarioCadastro(UsuarioLogado.getUsuario());
            setRecebimento(getRecebimentoDAO().setTransactionPersist(getRecebimento()));
            getRecebimentoDAO().transactionCommit();
        } catch (Exception ex) {
            getRecebimentoDAO().transactionRollback();
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private Task newTaskRecebimento() {
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
                        case ADD_RECEBIMENTO:
                        case UPDATE_RECEBIMENTO:
                            if (!salvarRecebimento()) {
                                Thread.currentThread().interrupt();
                            }
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
     * END Booleans
     */


    /**
     * Begin Voids
     */

    private void getNewDocumento() {
        getTxtDocumento().setText(ServiceValidarDado.gerarCodigoCafePerfeito(Recebimento.class, getaReceber().dtCadastroProperty().getValue().toLocalDate()));
    }

    /**
     * END Voids
     */
    /**
     * Begin Getters e Setters
     */

    public AnchorPane getPainelViewRecebimento() {
        return painelViewRecebimento;
    }

    public void setPainelViewRecebimento(AnchorPane painelViewRecebimento) {
        this.painelViewRecebimento = painelViewRecebimento;
    }


    public TitledPane getTpnRecebimento() {
        return tpnRecebimento;
    }

    public void setTpnRecebimento(TitledPane tpnRecebimento) {
        this.tpnRecebimento = tpnRecebimento;
    }

    public ComboBox<PagamentoModalidade> getCboPagamentoModalidade() {
        return cboPagamentoModalidade;
    }

    public void setCboPagamentoModalidade(ComboBox<PagamentoModalidade> cboPagamentoModalidade) {
        this.cboPagamentoModalidade = cboPagamentoModalidade;
    }

    public TextField getTxtDocumento() {
        return txtDocumento;
    }

    public void setTxtDocumento(TextField txtDocumento) {
        this.txtDocumento = txtDocumento;
    }

    public DatePicker getDtpDtPagamento() {
        return dtpDtPagamento;
    }

    public void setDtpDtPagamento(DatePicker dtpDtPagamento) {
        this.dtpDtPagamento = dtpDtPagamento;
    }

    public ComboBox<PagamentoSituacao> getCboSituacao() {
        return cboSituacao;
    }

    public void setCboSituacao(ComboBox<PagamentoSituacao> cboSituacao) {
        this.cboSituacao = cboSituacao;
    }

    public TextField getTxtValor() {
        return txtValor;
    }

    public void setTxtValor(TextField txtValor) {
        this.txtValor = txtValor;
    }

    public Button getBtnOK() {
        return btnOK;
    }

    public void setBtnOK(Button btnOK) {
        this.btnOK = btnOK;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(Button btnCancel) {
        this.btnCancel = btnCancel;
    }

    public Stage getRecebimentoStage() {
        return recebimentoStage;
    }

    public void setRecebimentoStage(Stage recebimentoStage) {
        this.recebimentoStage = recebimentoStage;
    }

    public ServiceAlertMensagem getAlertMensagem() {
        return alertMensagem;
    }

    public void setAlertMensagem(ServiceAlertMensagem alertMensagem) {
        this.alertMensagem = alertMensagem;
    }

    public ContasAReceber getaReceber() {
        return aReceber;
    }

    public void setaReceber(ContasAReceber aReceber) {
        this.aReceber = aReceber;
    }

    public Recebimento getRecebimento() {
        return recebimento;
    }

    public void setRecebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
    }

    public RecebimentoDAO getRecebimentoDAO() {
        return recebimentoDAO;
    }

    public void setRecebimentoDAO(RecebimentoDAO recebimentoDAO) {
        this.recebimentoDAO = recebimentoDAO;
    }

    public boolean isDeshabilita() {
        return deshabilita.get();
    }

    public BooleanProperty deshabilitaProperty() {
        return deshabilita;
    }

    public void setDeshabilita(boolean deshabilita) {
        this.deshabilita.set(deshabilita);
    }

    public List<EnumsTasks> getEnumsTasksList() {
        return enumsTasksList;
    }

    public void setEnumsTasksList(List<EnumsTasks> enumsTasksList) {
        this.enumsTasksList = enumsTasksList;
    }

    public String getNomeController() {
        return nomeController;
    }

    public void setNomeController(String nomeController) {
        this.nomeController = nomeController;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public ImageView getImgNewDocumento() {
        return imgNewDocumento;
    }

    public void setImgNewDocumento(ImageView imgNewDocumento) {
        this.imgNewDocumento = imgNewDocumento;
    }

/**
 * END Getters e Setters
 */

}

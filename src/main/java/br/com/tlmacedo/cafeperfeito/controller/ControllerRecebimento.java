package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.RecebimentoDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.EnumsTasks;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoModalidade;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoSituacao;
import br.com.tlmacedo.cafeperfeito.model.vo.ContasAReceber;
import br.com.tlmacedo.cafeperfeito.model.vo.Recebimento;
import br.com.tlmacedo.cafeperfeito.model.vo.UsuarioLogado;
import br.com.tlmacedo.cafeperfeito.service.ServiceAlertMensagem;
import br.com.tlmacedo.cafeperfeito.service.ServiceCampoPersonalizado;
import br.com.tlmacedo.cafeperfeito.service.ServiceMascara;
import br.com.tlmacedo.cafeperfeito.service.ServiceSegundoPlano;
import br.com.tlmacedo.cafeperfeito.view.ViewRecebimento;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

public class ControllerRecebimento implements Initializable, ModeloCafePerfeito {


    public AnchorPane painelViewRecebimento;
    public TitledPane tpnRecebimento;
    public ComboBox<PagamentoModalidade> cboPagamentoModalidade;
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
    private RecebimentoDAO recebimentoDAO = new RecebimentoDAO();

    private BooleanProperty deshabilita = new SimpleBooleanProperty(true);

    public ControllerRecebimento() {
        setaReceber(ViewRecebimento.getaReceber());
        getEnumsTasksList().add(EnumsTasks.ADD_RECEBIMENTO);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        criarObjetos();
        preencherObjetos();
        fatorarObjetos();
        escutarTecla();
        fieldsFormat();
        Platform.runLater(() -> {
            setRecebimentoStage(ViewRecebimento.getStage());
            getCboPagamentoModalidade().requestFocus();
        });
    }

    @Override
    public void fieldsFormat() {
        ServiceCampoPersonalizado.fieldTextFormat(getPainelViewRecebimento());
    }

    @Override
    public void fechar() {
        getRecebimentoStage().close();
    }

    @Override
    public void criarObjetos() {

    }

    @Override
    public void preencherObjetos() {
        getCboPagamentoModalidade().setItems(Arrays.stream(PagamentoModalidade.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
        getCboPagamentoModalidade().getSelectionModel().select(PagamentoModalidade.DINHEIRO);
        getCboSituacao().setItems(
                Arrays.stream(PagamentoSituacao.values())
                        .filter(pagamentoSituacao -> pagamentoSituacao.getCod() != 2)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        getCboSituacao().getSelectionModel().select(PagamentoSituacao.PENDENTE);

        getTxtValor().setText(ServiceMascara.getMoeda(getaReceber().valorProperty().getValue(), 2));

        getDtpDtPagamento().setValue(getaReceber().dtVencimentoProperty().getValue());

    }

    @Override
    public void fatorarObjetos() {

    }

    @Override
    public void escutarTecla() {

        deshabilitaProperty().bind(Bindings.createBooleanBinding(() -> {
                    if (
                            ServiceMascara.getBigDecimalFromTextField(getTxtValor().getText(), 2).compareTo(BigDecimal.ZERO) > 0
                                    && getDtpDtPagamento().getValue().compareTo(LocalDate.now().minusDays(7)) >= 0
                    )
                        return false;
                    else
                        return true;
                }, getDtpDtPagamento().valueProperty(), getTxtValor().textProperty()
        ));

        getBtnCancel().setOnAction(actionEvent -> fechar());

        getBtnOK().disableProperty().bind(deshabilitaProperty());

        getBtnOK().setOnAction(actionEvent -> {
            if (new ServiceSegundoPlano().executaListaTarefas(newTaskRecebimento(), "recebimento"))
                fechar();
            else
                getCboPagamentoModalidade().requestFocus();
        });


    }

    /**
     * Begin Booleans
     */

    private boolean salvarRecebimento() {
        try {
            Recebimento recebimento = new Recebimento();
            recebimento.setaReceber(getaReceber());
            recebimento.setPagamentoSituacao(getCboSituacao().getValue());
            recebimento.documentoProperty().setValue(getTxtDocumento().getText());
            recebimento.setPagamentoModalidade(getCboPagamentoModalidade().getValue());
            recebimento.valorProperty().setValue(ServiceMascara.getBigDecimalFromTextField(getTxtValor().getText(), 2));
            recebimento.setUsuarioPagamento(null);
            recebimento.setDtPagamento(null);
            if (recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO)) {
                recebimento.setUsuarioPagamento(UsuarioLogado.getUsuario());
                recebimento.dtPagamentoProperty().setValue(getDtpDtPagamento().getValue());
            }
            recebimento.setUsuarioCadastro(UsuarioLogado.getUsuario());
            getRecebimentoDAO().merger(recebimento);

        } catch (Exception ex) {
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
/**
 * END Getters e Setters
 */

}
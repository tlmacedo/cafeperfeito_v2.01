package br.com.tlmacedo.cafeperfeito.service;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.SPLASH_IMAGENS;
import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

public class ServiceAlertMensagem {

    private Thread thread;
    private Dialog dialog = new Dialog();
    private DialogPane dialogPane = dialog.getDialogPane();
    private Task<?> task;
    private String cabecalho = null, contentText = null, strIco = null;

    private boolean retornoWait = false;
    private boolean retornoProgressBar = false;
    private HBox hBox = new HBox();
    private VBox vBox = new VBox();
    private ImageView imageView;
    private Button[] btns = new Button[3];
    private Button btnOk, btnCancel, btnYes, btnNo, btnApply, btnClose, btnFinish;
    private Label lblMsg = new Label(), lblContagem = new Label();
    private Integer timeOut = TCONFIG.getTimeOut();
    private Timeline timeline;
    private ProgressBar progressBar;
    private ProgressIndicator progressIndicator;


    public void alertOk() {
        loadDialog();
        loadDialogPane();

        setBtnOk(new Button());
        getBtns()[0] = getBtnOk();

        addButton();

        getBtnOk().setDisable(false);
        getDialog().showAndWait();
    }

    public boolean alertProgressBar(Task<?> task, boolean isWait) {
        setTask(task);
        setRetornoWait(isWait);

        setTimeline(newTimeLine(0));

        loadDialog();
        loadDialogPane();
        getDialogPane().getStyleClass().remove("alertMsg_return");
        getDialogPane().getStyleClass().add("alertMsg_progress");


        if (isWait) {
            setBtnOk(new Button());
            getBtns()[0] = getBtnOk();
            setBtnCancel(new Button());
            getBtns()[1] = getBtnCancel();
        }

        addButton();

        getDialogPane().setContent(contentProgress(!isWait));

        startContagemRegressiva();

        getTask().setOnFailed(event -> {
            setRetornoProgressBar(false);
            dialogClose();
        });

        getTask().setOnCancelled(event -> {
            setRetornoProgressBar(false);
            dialogClose();
        });

        getTask().setOnSucceeded(event -> {
            setRetornoProgressBar(true);
            getTimeline().stop();
            if (isWait) {
                getProgressBar().setProgress(100);
                addImage("/image/sis_logo_240dp.png");
                getBtnOk().setDisable(false);
            } else {
                dialogClose();
            }
        });

        getTimeline().setOnFinished(event -> {
            setRetornoProgressBar(false);
            dialogClose();
            return;
        });

        setThread(new Thread(getTask()));
        getThread().setDaemon(true);
        getThread().start();

        getDialog().showAndWait();

        return isRetornoProgressBar();
    }

    /**
     * Begin Returns
     */

    private VBox contentProgress(boolean loading) {
        setProgressBar(new ProgressBar());
        getProgressBar().setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        getProgressBar().prefWidthProperty().bind(getDialogPane().widthProperty().subtract(20));

        //getLblMsg().prefWidthProperty().bind(getDialogPane().widthProperty().subtract(15));
        getLblMsg().getStyleClass().add("dialog-update-msg");

        gethBox().setSpacing(7);
        gethBox().setAlignment(Pos.CENTER_LEFT);
        getvBox().setAlignment(Pos.CENTER);

        if (loading) {
            int random = (int) (Math.random() * SPLASH_IMAGENS.size());
            addImage(SPLASH_IMAGENS.get(random).toString());
            getImageView().setClip(new Circle(120, 120, 120));
            getvBox().getChildren().add(getImageView());
            getvBox().getChildren().add(new Label(""));
            getvBox().getChildren().add(new Label(""));
            HBox hBox1 = new HBox();
            hBox1.getChildren().addAll(getLblMsg(), getLblContagem());
            getvBox().getChildren().add(hBox1);
        } else {
            setProgressIndicator(new ProgressIndicator());
            getProgressIndicator().setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            getProgressIndicator().setPrefSize(25, 25);
            gethBox().getChildren().addAll(getProgressIndicator(), getLblMsg());
            getvBox().getChildren().add(gethBox());
        }

        if (!isRetornoProgressBar()) {
            if (getTask().getTotalWork() > 1)
                getProgressBar().progressProperty().bind(getTask().progressProperty());
        }

        getvBox().getChildren().add(getProgressBar());
        return getvBox();
    }

    /**
     * END Returns
     */

    /**
     * Begin Voids
     */

    private void dialogClose() {
        getDialog().setResult(ButtonType.CANCEL);
        getDialog().close();
    }

    private void loadDialog() {
        getDialogPane().getStylesheets().add(getClass().getResource(TCONFIG.getPersonalizacao().getStyleSheets()).toString());
        getDialogPane().getButtonTypes().clear();
        getDialogPane().getStyleClass().add("alertMsg_return");
    }

    private void loadDialogPane() {
        getDialogPane().setHeaderText(getCabecalho());
        getDialogPane().setContentText(getContentText());
        if (getStrIco() != null)
            getDialog().setGraphic(new ImageView(getClass().getResource(getStrIco()).toString()));
    }

    private void addButton() {
        for (Button btn : getBtns()) {
            if (btn == getBtnOk()) {
                getDialogPane().getButtonTypes().add(ButtonType.OK);
                setBtnOk((Button) getDialogPane().lookupButton(ButtonType.OK));
                getBtnOk().setDisable(true);
                getBtnOk().setDefaultButton(true);
            } else if (btn == getBtnCancel()) {
                getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                setBtnCancel((Button) getDialogPane().lookupButton(ButtonType.CANCEL));
                getBtnCancel().setCancelButton(true);
            }
        }
    }

    private void addImage(String pathImg) {
        if (getImageView() == null)
            setImageView(new ImageView());
        getImageView().setImage(new Image(getClass().getResource(pathImg).toString()));
    }

    private void startContagemRegressiva() {
        getTimeline().setCycleCount(getTimeOut() * 10);
        getTimeline().play();
    }

    private Timeline newTimeLine(int tempo) {
        if (tempo > 0)
            setTimeOut(tempo);
        getLblMsg().textProperty().bind(getTask().messageProperty());
        final String[] pontos = {""}, contagem = {""};
        final int[] i = {0};

        return new Timeline(new KeyFrame(
                Duration.millis(100),
                ae -> {
                    if (i[0] % 10 == 1)
                        if (pontos[0].length() < 3)
                            pontos[0] += ".";
                        else
                            pontos[0] = "";
                    i[0]++;
                    contagem[0] = String.format("(%d) %s", (getTimeOut() - (i[0] / 10)), pontos[0]);
                    getLblContagem().setText(contagem[0]);
                }));
    }

    /**
     * END Voids
     */


    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public DialogPane getDialogPane() {
        return dialogPane;
    }

    public void setDialogPane(DialogPane dialogPane) {
        this.dialogPane = dialogPane;
    }

    public Task<?> getTask() {
        return task;
    }

    public void setTask(Task<?> task) {
        this.task = task;
    }

    public boolean isRetornoWait() {
        return retornoWait;
    }

    public void setRetornoWait(boolean retornoWait) {
        this.retornoWait = retornoWait;
    }

    public boolean isRetornoProgressBar() {
        return retornoProgressBar;
    }

    public void setRetornoProgressBar(boolean retornoProgressBar) {
        this.retornoProgressBar = retornoProgressBar;
    }

    public HBox gethBox() {
        return hBox;
    }

    public void sethBox(HBox hBox) {
        this.hBox = hBox;
    }

    public VBox getvBox() {
        return vBox;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    public String getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(String cabecalho) {
        this.cabecalho = cabecalho;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getStrIco() {
        return strIco;
    }

    public void setStrIco(String strIco) {
        this.strIco = strIco;
    }

    public Button getBtnOk() {
        return btnOk;
    }

    public void setBtnOk(Button btnOk) {
        this.btnOk = btnOk;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(Button btnCancel) {
        this.btnCancel = btnCancel;
    }

    public Button getBtnYes() {
        return btnYes;
    }

    public void setBtnYes(Button btnYes) {
        this.btnYes = btnYes;
    }

    public Button getBtnNo() {
        return btnNo;
    }

    public void setBtnNo(Button btnNo) {
        this.btnNo = btnNo;
    }

    public Button getBtnApply() {
        return btnApply;
    }

    public void setBtnApply(Button btnApply) {
        this.btnApply = btnApply;
    }

    public Button getBtnClose() {
        return btnClose;
    }

    public void setBtnClose(Button btnClose) {
        this.btnClose = btnClose;
    }

    public Button getBtnFinish() {
        return btnFinish;
    }

    public void setBtnFinish(Button btnFinish) {
        this.btnFinish = btnFinish;
    }

    public Button[] getBtns() {
        return btns;
    }

    public void setBtns(Button[] btns) {
        this.btns = btns;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Label getLblMsg() {
        return lblMsg;
    }

    public void setLblMsg(Label lblMsg) {
        this.lblMsg = lblMsg;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public Label getLblContagem() {
        return lblContagem;
    }

    public void setLblContagem(Label lblContagem) {
        this.lblContagem = lblContagem;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }

    public void setProgressIndicator(ProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }
}
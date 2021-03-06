package br.com.tlmacedo.cafeperfeito.service;

import br.com.tlmacedo.cafeperfeito.model.vo.UsuarioLogado;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_DATAHORA_HMS;
import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_HORA_HMS;
import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

//import org.apache.maven.surefire.shade.booter.org.apache.commons.lang3.StringUtils;

public class ServiceStatusBar {

    private Timeline timeline;
    private final ToolBar statusBar;
    private Label stbLogadoInf, stbTeclas, stbRelogio;
    private final String toolDataBase;
    private final String tooHorarioLog;

    /**
     * Begin Voids
     */

    public void atualizaStatusBar(String teclas) {
        getStbTeclas().setText(teclas);
    }

    /**
     * END Voids
     */

    public ServiceStatusBar(ToolBar statusBar, Label lblUsuario, Label lblTeclas, Label lblRelogio) {
        this.statusBar = statusBar;
        setStbLogadoInf(lblUsuario);
        setStbTeclas(lblTeclas);
        setStbRelogio(lblRelogio);
        toolDataBase = String.format(
                "%s:%s/%s",
                TCONFIG.getSis().getConnectDB().getDbHost(),
                TCONFIG.getSis().getConnectDB().getDbPorta(),
                TCONFIG.getSis().getConnectDB().getDbDatabase()
        );
        tooHorarioLog = DTF_DATAHORA_HMS.format(UsuarioLogado.getDataDeLog());
        getStbRelogio().setTooltip(new Tooltip(
                String.format("banco de dados: [%s]\thorário_log: %s",
                        toolDataBase, tooHorarioLog)
        ));
        setTimeline(new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> getStbRelogio().setText(LocalTime.now().format(DTF_HORA_HMS)))));
        getTimeline().setCycleCount(Animation.INDEFINITE);
        getTimeline().play();

        getStbLogadoInf().setText(
                String.format("Usuário [%02d]: %s",
                        UsuarioLogado.getUsuario().idProperty().intValue(),
                        StringUtils.capitalize(UsuarioLogado.getUsuario().apelidoProperty().get()))
        );

    }


    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public ToolBar getStatusBar() {
        return statusBar;
    }

    public Label getStbLogadoInf() {
        return stbLogadoInf;
    }

    public void setStbLogadoInf(Label stbLogadoInf) {
        this.stbLogadoInf = stbLogadoInf;
    }

    public Label getStbTeclas() {
        return stbTeclas;
    }

    public void setStbTeclas(Label stbTeclas) {
        this.stbTeclas = stbTeclas;
    }

    public Label getStbRelogio() {
        return stbRelogio;
    }

    public void setStbRelogio(Label stbRelogio) {
        this.stbRelogio = stbRelogio;
    }
}

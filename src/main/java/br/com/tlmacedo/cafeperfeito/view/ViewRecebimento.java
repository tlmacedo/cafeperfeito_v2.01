package br.com.tlmacedo.cafeperfeito.view;

import br.com.tlmacedo.cafeperfeito.model.vo.ContasAReceber;
import br.com.tlmacedo.cafeperfeito.model.vo.Recebimento;
import br.com.tlmacedo.cafeperfeito.service.ServiceOpenView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;

import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;
import static javafx.stage.StageStyle.UNDECORATED;

public class ViewRecebimento {

    private static Stage stage;
    private static ContasAReceber aReceber;
    private static Recebimento recebimento;
    private static BigDecimal saldo;

    public void openViewRecebimento(Recebimento recebimento) {
        if (recebimento != null) {
            setRecebimento(recebimento);
            setaReceber(recebimento.getaReceber());
        }
        openViewRecebimento();
    }

    public void openViewRecebimento(ContasAReceber aReceber, BigDecimal saldo) {
        setaReceber(new ContasAReceber());
        if (aReceber != null)
            setaReceber(aReceber);
        setSaldo(saldo);
        if (getSaldo() == null)
            setSaldo(BigDecimal.ZERO);
        openViewRecebimento();
    }

    public void openViewRecebimento() {
        setStage(new Stage());
        Parent parent;
        Scene scene = null;

        try {
            parent = FXMLLoader.load(getClass().getResource(TCONFIG.getFxml().getRecebimento().getFxml()));
            scene = new Scene(parent);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        getStage().setResizable(false);
        getStage().setScene(scene);
        getStage().initStyle(UNDECORATED);
        getStage().setTitle(TCONFIG.getFxml().getRecebimento().getTitulo());
        getStage().getIcons().setAll(new Image(getClass().getResource(TCONFIG.getFxml().getRecebimento().getIcone()).toString()));
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().setAll(getClass().getResource(TCONFIG.getPersonalizacao().getStyleSheets()).toString());

        new ServiceOpenView(getStage(), true);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ViewRecebimento.stage = stage;
    }

    public static ContasAReceber getaReceber() {
        return aReceber;
    }

    public static void setaReceber(ContasAReceber aReceber) {
        ViewRecebimento.aReceber = aReceber;
    }

    public static Recebimento getRecebimento() {
        return recebimento;
    }

    public static void setRecebimento(Recebimento recebimento) {
        ViewRecebimento.recebimento = recebimento;
    }

    public static BigDecimal getSaldo() {
        return saldo;
    }

    public static void setSaldo(BigDecimal saldo) {
        ViewRecebimento.saldo = saldo;
    }
}

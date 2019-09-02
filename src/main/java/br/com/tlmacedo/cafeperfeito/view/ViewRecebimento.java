package br.com.tlmacedo.cafeperfeito.view;

import br.com.tlmacedo.cafeperfeito.model.vo.ContasAReceber;
import br.com.tlmacedo.cafeperfeito.service.ServiceOpenView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;
import static javafx.stage.StageStyle.UNDECORATED;

public class ViewRecebimento {

    private static Stage stage;
    private static ContasAReceber aReceber;

    public void openViewRecebimento(ContasAReceber aReceber) {
        if (aReceber != null)
            setaReceber(aReceber);

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

}

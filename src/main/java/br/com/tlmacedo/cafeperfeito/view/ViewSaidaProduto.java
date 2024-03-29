package br.com.tlmacedo.cafeperfeito.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

import java.io.IOException;

import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

public class ViewSaidaProduto {

    private static String titulo;
    private static Tab tab;

    public Tab tabSaidaProduto(String titulo) {
        setTitulo(titulo);
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource(TCONFIG.getFxml().getSaidaProduto().getFxml()));
            parent.getStylesheets().setAll(getClass().getResource(TCONFIG.getPersonalizacao().getStyleSheets()).toString());

            setTab(new Tab(getTitulo()));
            getTab().setContent(parent);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return getTab();
    }

    public static String getTitulo() {
        return titulo;
    }

    public static void setTitulo(String titulo) {
        ViewSaidaProduto.titulo = titulo;
    }

    public static Tab getTab() {
        return tab;
    }

    public static void setTab(Tab tab) {
        ViewSaidaProduto.tab = tab;
    }
}

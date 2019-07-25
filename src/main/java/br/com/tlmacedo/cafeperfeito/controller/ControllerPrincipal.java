package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.MenuPrincipalDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.MenuPrincipal;
import br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema;
import br.com.tlmacedo.cafeperfeito.view.ViewPrincipal;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;


public class ControllerPrincipal implements Initializable, ModeloCafePerfeito {

    public BorderPane painelViewPrincipal;
    public TabPane tabPaneViewPrincipal;
    public Label lblImageLogoViewPrincipal;
    public TreeView<MenuPrincipal> treeMenuViewPrincipal;
    public ImageView imgMenuPrincipalExpande;
    public ImageView imgMenuPrincipalRetrair;
    public Label lblCopyRight;
    public ToolBar statusBar_ViewPrincipal;
    public Label stbLogadoInf;
    public Label stbTeclas;
    public Label stbRelogio;

    private Stage principalStage = ViewPrincipal.getStage();
    public static ControllerPrincipal ctrlPrincipal;
    private String tabSelecionada = "";
    private EventHandler<KeyEvent> eventHandlerPrincipal;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ctrlPrincipal = this;
        getLblImageLogoViewPrincipal().setVisible(true);
        criarObjetos();
        preencherObjetos();
        fatorarObjetos();
        escutarTecla();
    }

    @Override
    public void fechar() {
        principalStage.close();
    }

    @Override
    public void criarObjetos() {

    }

    @Override
    public void preencherObjetos() {
        carregaMenuPrincipal();
    }

    @Override
    public void fatorarObjetos() {

    }

    @Override
    public void escutarTecla() {
        imgMenuPrincipalExpande.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> expandeAllMenuPrincipal(true));
        imgMenuPrincipalRetrair.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> expandeAllMenuPrincipal(false));
    }


    private int tabAberta(String menuLabel) {
        for (int i = 0; i < getTabPaneViewPrincipal().getTabs().size(); i++)
            if (getTabPaneViewPrincipal().getTabs().get(i).getText().equals(menuLabel))
                return i;
        return -1;
    }

    private void expandeAllMenuPrincipal(boolean expand) {
        for (int i = 0; i < getTreeMenuViewPrincipal().getExpandedItemCount(); i++)
            getTreeMenuViewPrincipal().getTreeItem(i).setExpanded(expand);
    }

    private void carregaMenuPrincipal() {
        getLblCopyRight().setText(String.format("%s %d",
                TCONFIG.getInfLoja().getCopyright(),
                LocalDate.now().getYear()
        ));
        String path = TCONFIG.getPaths().getPathIconeSistema();
        List<MenuPrincipal> menuPrincipalList = new MenuPrincipalDAO()
                .getAll(MenuPrincipal.class, null, null, null, null);
        TreeItem[] treeItems = new TreeItem[menuPrincipalList.size() + 1];
        treeItems[0] = new TreeItem();
        int i = 0;
        for (MenuPrincipal menu : menuPrincipalList) {
            i = menu.idProperty().intValue();
            treeItems[i] = new TreeItem(menu);
            if (!menu.icoMenuProperty().get().equals(menu))
                treeItems[i].setGraphic(new ImageView(getClass().getResource(path + menu.icoMenuProperty().get()).toString()));
            treeItems[i].setExpanded(true);
            treeItems[menu.menuPai_idProperty().intValue()].getChildren().add(treeItems[i]);
        }
        getTreeMenuViewPrincipal().setRoot(treeItems[0]);
        getTreeMenuViewPrincipal().setShowRoot(false);
    }


    public BorderPane getPainelViewPrincipal() {
        return painelViewPrincipal;
    }

    public void setPainelViewPrincipal(BorderPane painelViewPrincipal) {
        this.painelViewPrincipal = painelViewPrincipal;
    }

    public TabPane getTabPaneViewPrincipal() {
        return tabPaneViewPrincipal;
    }

    public void setTabPaneViewPrincipal(TabPane tabPaneViewPrincipal) {
        this.tabPaneViewPrincipal = tabPaneViewPrincipal;
    }

    public Label getLblImageLogoViewPrincipal() {
        return lblImageLogoViewPrincipal;
    }

    public void setLblImageLogoViewPrincipal(Label lblImageLogoViewPrincipal) {
        this.lblImageLogoViewPrincipal = lblImageLogoViewPrincipal;
    }

    public TreeView<MenuPrincipal> getTreeMenuViewPrincipal() {
        return treeMenuViewPrincipal;
    }

    public void setTreeMenuViewPrincipal(TreeView<MenuPrincipal> treeMenuViewPrincipal) {
        this.treeMenuViewPrincipal = treeMenuViewPrincipal;
    }

    public ImageView getImgMenuPrincipalExpande() {
        return imgMenuPrincipalExpande;
    }

    public void setImgMenuPrincipalExpande(ImageView imgMenuPrincipalExpande) {
        this.imgMenuPrincipalExpande = imgMenuPrincipalExpande;
    }

    public ImageView getImgMenuPrincipalRetrair() {
        return imgMenuPrincipalRetrair;
    }

    public void setImgMenuPrincipalRetrair(ImageView imgMenuPrincipalRetrair) {
        this.imgMenuPrincipalRetrair = imgMenuPrincipalRetrair;
    }

    public Label getLblCopyRight() {
        return lblCopyRight;
    }

    public void setLblCopyRight(Label lblCopyRight) {
        this.lblCopyRight = lblCopyRight;
    }

    public ToolBar getStatusBar_ViewPrincipal() {
        return statusBar_ViewPrincipal;
    }

    public void setStatusBar_ViewPrincipal(ToolBar statusBar_ViewPrincipal) {
        this.statusBar_ViewPrincipal = statusBar_ViewPrincipal;
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

    public Stage getPrincipalStage() {
        return principalStage;
    }

    public void setPrincipalStage(Stage principalStage) {
        this.principalStage = principalStage;
    }

    public static ControllerPrincipal getCtrlPrincipal() {
        return ctrlPrincipal;
    }

    public static void setCtrlPrincipal(ControllerPrincipal ctrlPrincipal) {
        ControllerPrincipal.ctrlPrincipal = ctrlPrincipal;
    }

    public String getTabSelecionada() {
        return tabSelecionada;
    }

    public void setTabSelecionada(String tabSelecionada) {
        this.tabSelecionada = tabSelecionada;
    }

    public EventHandler<KeyEvent> getEventHandlerPrincipal() {
        return eventHandlerPrincipal;
    }

    public void setEventHandlerPrincipal(EventHandler<KeyEvent> eventHandlerPrincipal) {
        this.eventHandlerPrincipal = eventHandlerPrincipal;
    }
}
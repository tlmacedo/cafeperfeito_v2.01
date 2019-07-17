package br.com.tlmacedo.cafeperfeito.service;

//import br.com.tlmacedo.cafeperfeito.xsd.sistema.config.TConfig;

import br.com.tlmacedo.cafeperfeito.xsd.sistema.config.TConfig;
import javafx.scene.image.Image;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Locale;

public class ServiceVariaveisSistema {
    public static TConfig TCONFIG;
    public static Locale MY_LOCALE;
    public static String PATHICONE, PATHFXML;
    public static List SPLASH_IMAGENS;
    public static Image IMG_DEFAULT_PRODUTO;

    private FileReader arqConfgSistema = null;

    public ServiceVariaveisSistema() throws FileNotFoundException {
        System.setProperty("senhaDoCertificado", "4879");
        arqConfgSistema = new FileReader(getClass().getClassLoader().getResource("configSistema.xml").getFile());
//        if (LogadoInf.getUserLog() == null) {
//            UsuarioDAO usuarioDAO = new UsuarioDAO();
//            LogadoInf.setUserLog(usuarioDAO.getById(Usuario.class, 1L));
//            LogadoInf.getUserLog().setApelido(StringUtils.capitalize(LogadoInf.getUserLog().getApelido()));
//            LogadoInf.setLojaUser(new EmpresaDAO().getById(Empresa.class, Long.valueOf(1)));
//        }

    }

    public void getVariaveisSistema() {
        try {
            //FileInputStream arqConfiSistema = new FileInputStream(getClass().getClassLoader().getResource("xml/configSistema.xml").getFile());
            String xml = ServiceXmlUtil.FileXml4String(arqConfgSistema);
            TCONFIG = ServiceXmlUtil.xmlToObject(xml, TConfig.class);
            MY_LOCALE = new Locale(TCONFIG.getMyLocale().substring(0, 2), TCONFIG.getMyLocale().substring(3));
            Locale.setDefault(MY_LOCALE);
            PATHICONE = TCONFIG.getPaths().getPathIconeSistema();
            PATHFXML = TCONFIG.getPaths().getPathFXML();
            SPLASH_IMAGENS = TCONFIG.getPersonalizacao().getSplashImagens().getImage();
            //IMG_DEFAULT_PRODUTO = new Image(getClass().getResource("image/default_produto.png").toString());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void getVariaveisSistemaSimples() {
        try {
            String xml = ServiceXmlUtil.FileXml4String(arqConfgSistema);
            TCONFIG = ServiceXmlUtil.xmlToObject(xml, TConfig.class);
            MY_LOCALE = new Locale(TCONFIG.getMyLocale().substring(0, 2), TCONFIG.getMyLocale().substring(3));
            Locale.setDefault(MY_LOCALE);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void getVariaveisSistemaBasica() {
        try {
            FileReader arqConfiSistema = new FileReader(ServiceVariaveisSistema.class.getClassLoader().getResource("configSistema.xml").getFile());
            String xml = ServiceXmlUtil.FileXml4String(arqConfiSistema);
            TCONFIG = ServiceXmlUtil.xmlToObject(xml, TConfig.class);
            MY_LOCALE = new Locale(TCONFIG.getMyLocale().substring(0, 2), TCONFIG.getMyLocale().substring(3));
            Locale.setDefault(MY_LOCALE);
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

//    public TConfig gettConfig() {
//        return TCONFIG;
//    }
//
//    public void settConfig(TConfig TCONFIG) {
//        this.TCONFIG = TCONFIG;
//    }
}

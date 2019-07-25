package br.com.tlmacedo.cafeperfeito.model.vo;

import org.apache.maven.surefire.shade.booter.org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UsuarioLogado implements Serializable {
    private static final long serialVersionUID = 1L;

    private static Usuario usuario;
    private static LocalDateTime dataDeLog = LocalDateTime.now();

    public UsuarioLogado() {
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        UsuarioLogado.usuario = usuario;
    }

    public static LocalDateTime getDataDeLog() {
        return dataDeLog;
    }

    public static void setDataDeLog(LocalDateTime dataDeLog) {
        UsuarioLogado.dataDeLog = dataDeLog;
    }

    @Override
    public String toString(){
        return StringUtils.capitalize(getUsuario().getApelido());
    }
}

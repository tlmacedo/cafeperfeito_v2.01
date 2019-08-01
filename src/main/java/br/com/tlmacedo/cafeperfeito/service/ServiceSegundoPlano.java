package br.com.tlmacedo.cafeperfeito.service;

import javafx.concurrent.Task;

public class ServiceSegundoPlano {


    public boolean abrindoCadastro(Task<?> task, String titulo) {
        ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setCabecalho(titulo);
        //alertMensagem.setStrIco("");
        return alertMensagem.alertProgressBar(task, false);
    }
}

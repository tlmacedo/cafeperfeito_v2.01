package br.com.tlmacedo.cafeperfeito.service;

import javafx.concurrent.Task;

public class ServiceSegundoPlano {


    public boolean executaListaTarefas(Task<?> task, String titulo) throws Exception {
        ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setCabecalho(titulo);
        //alertMensagem.setStrIco("");
        return alertMensagem.alertProgressBar(task, false);
    }
}

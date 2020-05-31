package br.com.tlmacedo.cafeperfeito.service;

import br.com.tlmacedo.service.ServiceAlertMensagem;
import javafx.concurrent.Task;

import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

public class ServiceSegundoPlano {


    public boolean executaListaTarefas(Task<?> task, String titulo) throws Exception {
        ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem(
                TCONFIG.getTimeOut(),
                ServiceVariaveisSistema.SPLASH_IMAGENS,
                TCONFIG.getPersonalizacao().getStyleSheets()
        );
        alertMensagem.setCabecalho(titulo);
        //alertMensagem.setStrIco("");
        return alertMensagem.alertProgressBar(task, false);
    }
}

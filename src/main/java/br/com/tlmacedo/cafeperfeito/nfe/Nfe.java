package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProdutoNfe;
import br.com.tlmacedo.cafeperfeito.service.ServiceSegundoPlano;
import br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema;
import br.com.tlmacedo.nfe.model.vo.EnviNfeVO;
import br.com.tlmacedo.nfe.service.NFev400;
import br.com.tlmacedo.service.ServiceAlertMensagem;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.MY_ZONE_TIME;
import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

public class Nfe {

    private EnviNfeVO enviNfeVO;
    private static ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem(
            TCONFIG.getTimeOut(),
            ServiceVariaveisSistema.SPLASH_IMAGENS,
            TCONFIG.getPersonalizacao().getStyleSheets()
    );

    public Nfe(SaidaProduto saidaProduto, boolean imprimeLote) throws Exception {
        NFev400 nFev400 = new NFev400(null, alertMensagem, MY_ZONE_TIME, true);
        if (!nFev400.validCertificate()) {
            System.out.printf("operação cancelada pelo usuario\n");
            return;
        }
        SaidaProdutoNfe myNfe;
        if ((myNfe = saidaProduto.getSaidaProdutoNfeList().stream()
                .filter(saidaProdutoNfe1 -> !saidaProdutoNfe1.isCancelada()).findFirst().orElse(null)) == null
                || myNfe.getXmlAssinatura() == null) {
            setEnviNfeVO(new New_Nfe(saidaProduto, imprimeLote).getEnviNfeVO());
            nFev400.newNFev400(getEnviNfeVO());
        } else {
            nFev400.newNFev400(myNfe.getXmlProtNfe().toString());
        }

        boolean retorno = new ServiceSegundoPlano().executaListaTarefas(nFev400.getNewTaskNFe(), "NF-e");

        System.out.printf("\npegando_meu_xml:\n%s\n\n", nFev400.getXml());

        System.out.printf("\npegando_meu_xmlAssinado:\n%s\n\n", nFev400.getXmlAssinado());

        System.out.printf("\npegando_meu_xmlAutorizado:\n%s\n\n", nFev400.getXmlAutorizacao());
        System.out.printf("retorno: [%s]\n", retorno);

    }

    /**
     * Begin Getters and Setters
     */
    public EnviNfeVO getEnviNfeVO() {
        return enviNfeVO;
    }

    public void setEnviNfeVO(EnviNfeVO enviNfeVO) {
        this.enviNfeVO = enviNfeVO;
    }
/**
 * END Getters and Setters
 */
}

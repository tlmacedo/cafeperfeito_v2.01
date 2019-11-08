package br.com.tlmacedo.cafeperfeito.nfe;


import br.com.tlmacedo.cafeperfeito.service.ServiceAlertMensagem;
import br.com.tlmacedo.nfe.service.ServiceLoadCertificates;
import javafx.scene.control.ButtonType;

public class LoadCertificadoA3 {

    private ServiceLoadCertificates certificates;

    public LoadCertificadoA3() {
        try {

            setCertificates(new ServiceLoadCertificates());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public boolean load() {
        try {
            getCertificates().loadToken();
            ;
            getCertificates().loadSocketDinamico();
        } catch (Exception ex) {
            setCertificates(null);
            ex.printStackTrace();

            ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setCabecalho("Certificado digital");
            alertMensagem.setContentText("erro no certificado, deseja tentar novamente?");
            if (alertMensagem.alertYesNo().get() == ButtonType.NO)
                return false;
            else
                return true;
        }
        return false;
    }

    /**
     * Begin Getters and Setters
     */

    public ServiceLoadCertificates getCertificates() {
        return certificates;
    }

    public void setCertificates(ServiceLoadCertificates certificates) {
        this.certificates = certificates;
    }

    /**
     * END Getters and Setters
     */

}

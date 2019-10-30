package br.com.tlmacedo.cafeperfeito.nfe;


import br.com.tlmacedo.nfe.service.ServiceLoadCertificates;

public class MeuCertificado {

    private ServiceLoadCertificates certificates;

    public MeuCertificado() {
        try {
            setCertificates(new ServiceLoadCertificates());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

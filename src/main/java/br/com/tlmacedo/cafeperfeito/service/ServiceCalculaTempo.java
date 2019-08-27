package br.com.tlmacedo.cafeperfeito.service;

public class ServiceCalculaTempo {

    Long ini, fim;

    public ServiceCalculaTempo() {
        ini = System.currentTimeMillis();
    }

    public void fim() {
        System.out.printf("inicio: [%s]", ini);

        fim = System.currentTimeMillis();
        System.out.printf("\tfim: [%s]", fim);

        System.out.printf("\t\t\ttempo: [%s]\n", fim - ini);

    }

}

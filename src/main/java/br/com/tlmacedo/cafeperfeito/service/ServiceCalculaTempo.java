package br.com.tlmacedo.cafeperfeito.service;

public class ServiceCalculaTempo {

    Long ini, fim;
    int cont = 0;

    public ServiceCalculaTempo() {
        start();
    }

    public void start() {
        ini = System.currentTimeMillis();
    }

    public void fim() {

        fim = System.currentTimeMillis();
        try {
            cont++;
            System.out.printf("ini(%2$02d): [%1$13s]\n", ini, cont);
            System.out.printf("fim(%2$02d): [%1$13s]\n", fim, cont);

            System.out.printf("dif(%2$02d): [%1$13s]\n", fim - ini, cont);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

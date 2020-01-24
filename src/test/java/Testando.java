import br.com.tlmacedo.cafeperfeito.service.ServiceValidarDado;

public class Testando {
    public static void main(String[] args) {
        if (ServiceValidarDado.isEan13Valido("7891021001885"))
            System.out.printf("é Valido");
        else
            System.out.printf("não é Válido!!!");
    }
}

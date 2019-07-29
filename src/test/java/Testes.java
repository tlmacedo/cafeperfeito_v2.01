import br.com.tlmacedo.cafeperfeito.service.ServiceMascara;

import java.util.Scanner;

public class Testes {


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println("qual tamanho da mascara?");
            System.out.printf("Resultado foi: [%s]\n\n", ServiceMascara.getTextoMask(Integer.parseInt(new Scanner(System.in).nextLine()), "#"));
        }
    }
}

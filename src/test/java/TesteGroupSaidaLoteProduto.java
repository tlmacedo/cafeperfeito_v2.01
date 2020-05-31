import br.com.tlmacedo.cafeperfeito.model.dao.SaidaProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.cafeperfeito.nfe.Nfe;
import br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TesteGroupSaidaLoteProduto {

    static Long nPed = 85L;
    //    static Long nPed = 102L;
    static SaidaProduto pedido;
    private static StringProperty xmlNFe = new SimpleStringProperty();


    public static void main(String[] args) throws Exception {
        new ServiceVariaveisSistema().getVariaveisSistema();
        setPedido(new SaidaProdutoDAO().getById(SaidaProduto.class, getnPed()));

        System.out.printf("iniciando123 nova NF-e\n");
        new Nfe(new SaidaProdutoDAO().getById(SaidaProduto.class, 85L), true);


    }





    public static Long getnPed() {
        return nPed;
    }

    public static void setnPed(Long nPed) {
        TesteGroupSaidaLoteProduto.nPed = nPed;
    }

    public static SaidaProduto getPedido() {
        return pedido;
    }

    public static void setPedido(SaidaProduto pedido) {
        TesteGroupSaidaLoteProduto.pedido = pedido;
    }

    public static String getXmlNFe() {
        return xmlNFe.get();
    }

    public static StringProperty xmlNFeProperty() {
        return xmlNFe;
    }

    public static void setXmlNFe(String xmlNFe) {
        TesteGroupSaidaLoteProduto.xmlNFe.set(xmlNFe);
    }
}

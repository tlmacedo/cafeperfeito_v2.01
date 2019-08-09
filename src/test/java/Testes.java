import br.com.tlmacedo.cafeperfeito.model.dao.ProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.Produto;

public class Testes {


    public static void main(String[] args) {

        try {
//            UndComercialProduto.getList().stream().forEach(tipoEndereco -> System.out.printf("%d-%s\n", tipoEndereco.getCod(), tipoEndereco));
//
//            boolean continuar = true;
//            while (continuar) {
//                System.out.printf("\nQual código vc vai pesquisar agora? ");
//                Integer val = Integer.parseInt(new Scanner(System.in).nextLine().replaceAll("\\D", ""));
//                if (val == 100)
//                    continuar = true;
//                System.out.printf("\nUnd Comércial do código(%d): [%s]", val, UndComercialProduto.toEnum(val));
//            }


            new ProdutoDAO().getAll(Produto.class, null, null, null, "descricao").stream()
                    .forEach(produto -> System.out.printf("%s [%s]\n", produto.descricaoProperty().get(),
                            produto.getUnidadeComercial()));

//            new EnderecoDAO().getAll(Endereco.class, null, null, null, null).stream()
//                    .forEach(endereco -> System.out.printf("%s\n", endereco.logradouroProperty().get()));

//        new EmpresaDAO().getAll(Empresa.class, null, null, null, "razao").stream()
//                .forEach(empresa -> System.out.printf("%s (%s)\n", empresa.razaoProperty().get(), empresa.getEnderecoList()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

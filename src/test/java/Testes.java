import br.com.tlmacedo.cafeperfeito.model.dao.ContasAReceberDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.ContasAReceber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TreeItem;

public class Testes {


    public static void main(String[] args) {

        try {

            ObservableList<ContasAReceber> contasAReceberObservableList = FXCollections.observableArrayList(new ContasAReceberDAO().getAll(ContasAReceber.class, null, null, null, "dtVencimento"));
            FilteredList<ContasAReceber> contasAReceberFilteredList = new FilteredList<>(contasAReceberObservableList);

            TreeItem contasAReceberTreeItem = new TreeItem();

            contasAReceberFilteredList.stream().forEach(aReceber1 -> {
                final TreeItem receberTreeItem = new TreeItem(aReceber1);
                contasAReceberTreeItem.getChildren().add(receberTreeItem);
                System.out.printf("item0: [%s]\n", receberTreeItem);
                aReceber1.getRecebimentoList().stream()
                        .forEach(recebimento -> {
                            System.out.printf("\t\titem1: [%s]\n", recebimento);
                            receberTreeItem.getChildren().add(new TreeItem(recebimento));
                        });
            });

        } catch (
                Exception ex) {
            ex.printStackTrace();
        }
    }
}

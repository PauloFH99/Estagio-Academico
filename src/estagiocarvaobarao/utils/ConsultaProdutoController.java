package estagiocarvaobarao.utils;

import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.ui.TelaAcertoEstoqueController;
import estagiocarvaobarao.ui.TelaEntradaProdutosController;
import estagiocarvaobarao.ui.TelaProducaoController;
import estagiocarvaobarao.ui.TelaVendaController;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Felipe
 */
public class ConsultaProdutoController implements Initializable {

    @FXML
    private RadioButton rbDescri;
    @FXML
    private RadioButton rbCat;
    @FXML
    private Button btnPesquisar;
    @FXML
    private TableView<Produto> tabela;
    @FXML
    private TableColumn<Produto, Integer> colCod;
    @FXML
    private TableColumn<Produto, String> colDescri;
    @FXML
    private TableColumn<Produto, String> colCat;
    @FXML
    private TextField txPesquisa;
    public static Produto produto;
    public static int flag = 0;

    public static int getFlag() {
        return flag;
    }

    public static void setFlag(int flag) {
        ConsultaProdutoController.flag = flag;
    }

    public static Produto getProduto() {
        return produto;
    }

    public static void setProduto(Produto produto) {
        ConsultaProdutoController.produto = produto;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colCat.setCellValueFactory(new PropertyValueFactory("categoria"));
        colDescri.setCellValueFactory(new PropertyValueFactory("descricao"));
        carregarConsulta("", flag);
    }

    @FXML
    private void evtrbDescri(ActionEvent event) {
        if (rbCat.isSelected()) {
            rbCat.setSelected(false);
        }
    }

    @FXML
    private void evtRbCat(ActionEvent event) {
        if (rbDescri.isSelected()) {
            rbDescri.setSelected(false);
        }
    }

    @FXML
    private void evtBtnPesquisar(ActionEvent event) {
        String filtro = "";

        if (!txPesquisa.getText().isEmpty()) {
            if (rbDescri.isSelected()) {
                filtro = "descricao like '" + txPesquisa.getText().trim() + "%'";
            }
        }
        carregarConsulta(filtro, flag);
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        produto = tabela.getSelectionModel().getSelectedItem();

        TelaAcertoEstoqueController.setProd(produto);
        TelaVendaController.setProd(produto);
        TelaEntradaProdutosController.setProd(produto);
        TelaProducaoController.setProduto(produto);
        Stage self = (Stage)tabela.getScene().getWindow();
        self.close();
    }

    public void carregarConsulta(String filtro, int flag) {
        DALConsulta dal = new DALConsulta();
        List<Produto> prod = new ArrayList();
        ObservableList<Produto> f; 
        prod = (List<Produto>) dal.getProdutos(filtro,flag);
        f = FXCollections.observableArrayList(prod);
        tabela.setItems(f);
    }

}

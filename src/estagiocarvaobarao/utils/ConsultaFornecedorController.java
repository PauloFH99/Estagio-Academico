package estagiocarvaobarao.utils;

import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Fornecedor;
import estagiocarvaobarao.ui.TelaAcertoEstoqueController;
import estagiocarvaobarao.ui.TelaContasPagarController;
import estagiocarvaobarao.ui.TelaEntradaProdutosController;
import estagiocarvaobarao.ui.TelaLancarDespesasController;
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

public class ConsultaFornecedorController implements Initializable {

    public static Fornecedor fornecedor;

    @FXML
    private RadioButton rbCodigo;
    @FXML
    private RadioButton rbNome;
    @FXML
    private Button btnPesquisar;
    @FXML
    private TableColumn<Fornecedor, Integer> colCod;
    @FXML
    private TableColumn<Fornecedor, String> colNome;
    @FXML
    private TableView<Fornecedor> tabela;
    @FXML
    private TextField txPesquisa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory("nomefantasia"));
        carregarConsulta("fornecedor", "");
    }

    public static Fornecedor getFornecedor() {
        return fornecedor;
    }

    public static void setFornecedor(Fornecedor fornecedor) {
        ConsultaFornecedorController.fornecedor = fornecedor;
    }

    @FXML
    private void evtRbCodigo(ActionEvent event) {
        if (rbNome.isSelected()) {
            rbNome.setSelected(false);
        }
    }

    @FXML
    private void evtRbNome(ActionEvent event) {
        if (rbCodigo.isSelected()) {
            rbCodigo.setSelected(false);
        }
    }

    @FXML
    private void evtPesquisar(ActionEvent event) {
        String filtro = "";

        if (!txPesquisa.getText().isEmpty()) {
            if (rbCodigo.isSelected()) {
                filtro = " (f.codigo) = '" + txPesquisa.getText().trim().toLowerCase() + "'";
            } else if (rbNome.isSelected()) {
                filtro = " (f.nomefantasia) like '" + txPesquisa.getText().trim().toLowerCase() + "%'";
            }
        }

        carregarConsulta("fornecedor", filtro);
    }

    public void carregarConsulta(String tabela, String filtro) {
        DALConsulta dal = new DALConsulta();
        List<Fornecedor> func = new ArrayList();
        ObservableList<Fornecedor> f;

        func = (List<Fornecedor>) dal.get(tabela, filtro);
        f = FXCollections.observableArrayList(func);
        this.tabela.setItems(f);
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        fornecedor = tabela.getSelectionModel().getSelectedItem();
        TelaContasPagarController.setFornecedor(fornecedor);
        TelaEntradaProdutosController.setForn(fornecedor);

        Stage self = (Stage) tabela.getScene().getWindow();
        self.close();
    }

}

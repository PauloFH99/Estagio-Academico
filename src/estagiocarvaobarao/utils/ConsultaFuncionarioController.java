package estagiocarvaobarao.utils;

import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.ui.TelaAcertoEstoqueController;
import estagiocarvaobarao.ui.TelaEntradaProdutosController;
import estagiocarvaobarao.ui.TelaLancarDespesasController;
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


public class ConsultaFuncionarioController implements Initializable {

    public static Funcionario funcionario;

    @FXML
    private RadioButton rbCodigo;
    @FXML
    private RadioButton rbNome;
    @FXML
    private Button btnPesquisar;
    @FXML
    private TableColumn<Funcionario, Integer> colCod;
    @FXML
    private TableColumn<Funcionario, String> colNome;
    @FXML
    private TableView<Funcionario> tabela;
    @FXML
    private TextField txPesquisa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        carregarConsulta("funcionario", "");
    }

    public static Funcionario getFunc() {
        return funcionario;
    }

    public static void setFunc(Funcionario func) {
        ConsultaFuncionarioController.funcionario = funcionario;
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
                filtro = " (f.nome) like '" + txPesquisa.getText().trim().toLowerCase() + "%'";
            }
        }

        carregarConsulta("funcionario", filtro);
    }

    public void carregarConsulta(String tabela, String filtro) {
        DALConsulta dal = new DALConsulta();
        List<Funcionario> func = new ArrayList();
        ObservableList<Funcionario> f;

        func = (List<Funcionario>) dal.get(tabela, filtro);
        f = FXCollections.observableArrayList(func);
        this.tabela.setItems(f);
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        funcionario = tabela.getSelectionModel().getSelectedItem();
        TelaAcertoEstoqueController.setFuncionario(funcionario);
        TelaLancarDespesasController.setFuncionario(funcionario);
        TelaVendaController.setFuncionario(funcionario);
        TelaEntradaProdutosController.setFuncionario(funcionario);
        Stage self = (Stage)tabela.getScene().getWindow();
        self.close();
    }
    

}

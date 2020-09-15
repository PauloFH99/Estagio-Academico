package estagiocarvaobarao.utils;

import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Cidade;
import estagiocarvaobarao.ui.TelaClienteCadController;
import estagiocarvaobarao.ui.TelaEmpresaParametrosCadController;
import estagiocarvaobarao.ui.TelaFornecedorCadController;
import estagiocarvaobarao.ui.TelaFuncionarioCadController;
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

public class ConsultaController implements Initializable {

    public static Cidade cid;

    @FXML
    private RadioButton rbCodigo;
    @FXML
    private RadioButton rbNome;
    @FXML
    private Button btnPesquisar;
    @FXML
    private TableColumn<Cidade, Integer> colCod;
    @FXML
    private TableColumn<Cidade, String> colNome;
    @FXML
    private TableView<Cidade> tabela;
    @FXML
    private TextField txPesquisa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory("cid_cod"));
        colNome.setCellValueFactory(new PropertyValueFactory("cid_nome"));
        carregarConsulta("cidade", "");
    }

    public static Cidade getCid() {
        return cid;
    }

    public static void setCid(Cidade cid) {
        ConsultaController.cid = cid;
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
                filtro = " lower (e.est_sgl) = '" + txPesquisa.getText().trim().toLowerCase() + "'";
            } else if (rbNome.isSelected()) {
                filtro = " lower(c.cid_nome) like '" + txPesquisa.getText().trim().toLowerCase() + "%'";
            }
        }

        carregarConsulta("cidade", filtro);
    }

    public void carregarConsulta(String tabela, String filtro) {
        DALConsulta dal = new DALConsulta();
        List<Cidade> cid = new ArrayList();
        ObservableList<Cidade> f;

        cid = (List<Cidade>) dal.get(tabela, filtro);
        f = FXCollections.observableArrayList(cid);
        this.tabela.setItems(f);
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        cid = tabela.getSelectionModel().getSelectedItem();
        TelaClienteCadController.setCid(cid);
        TelaFornecedorCadController.setCid(cid);
        TelaEmpresaParametrosCadController.setCid(cid);
        TelaFuncionarioCadController.setCid(cid);
        Stage self = (Stage)tabela.getScene().getWindow();
        self.close();
    }

}

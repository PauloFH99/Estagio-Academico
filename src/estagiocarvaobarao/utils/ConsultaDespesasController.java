package estagiocarvaobarao.utils;

import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Despesas;
import estagiocarvaobarao.ui.TelaLancarDespesasController;
import estagiocarvaobarao.ui.TelaPedidosCaminhaoController;
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


public class ConsultaDespesasController implements Initializable {

    public static Despesas despesas;

    @FXML
    private RadioButton rbCodigo;
    @FXML
    private RadioButton rbNome;
    @FXML
    private Button btnPesquisar;
    @FXML
    private TableColumn<Despesas, Integer> colCod;
    @FXML
    private TableColumn<Despesas, String> colNome;
    @FXML
    private TableView<Despesas> tabela;
    @FXML
    private TextField txPesquisa;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory("descricao"));
        carregarConsulta("despesas", "");
    }

   

    public static Despesas getDespesas() {
        return despesas;
    }

    public static void setDespesas(Despesas despesas) {
        ConsultaDespesasController.despesas = despesas;
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
                filtro = "codigo = " + txPesquisa.getText().trim().toLowerCase() + "";
            } else if (rbNome.isSelected()) {
                filtro = "(descricao) like '" + txPesquisa.getText().trim().toLowerCase() + "%'";
            }
        }

        carregarConsulta("despesas", filtro);
    }

    public void carregarConsulta(String tabela, String filtro) {
        DALConsulta dal = new DALConsulta();
        List<Despesas> func = new ArrayList();
        ObservableList<Despesas> f;

        func = (List<Despesas>) dal.get(tabela, filtro);
        f = FXCollections.observableArrayList(func);
        this.tabela.setItems(f);
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        despesas = tabela.getSelectionModel().getSelectedItem();
        TelaLancarDespesasController.setDespesas(despesas);
        TelaPedidosCaminhaoController.setTipodespesas(despesas);
        Stage self = (Stage)tabela.getScene().getWindow();
        self.close();
    }

}

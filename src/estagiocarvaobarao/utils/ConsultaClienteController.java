package estagiocarvaobarao.utils;

import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Cliente;
import estagiocarvaobarao.ui.TelaPedidosArmazemController;
import estagiocarvaobarao.ui.TelaReceberFiadoController;
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

public class ConsultaClienteController implements Initializable {

    @FXML
    private RadioButton rbNome;
    @FXML
    private RadioButton rbCPF;
    @FXML
    private TextField txPesquisar;
    @FXML
    private Button BtnPesquisar;
    @FXML
    private TableView<Cliente> tabela;
    @FXML
    private TableColumn<Cliente, Integer> colCod;
    @FXML
    private TableColumn<Cliente, String> colNome;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        carregarConsulta("");
    }

    @FXML
    private void evtrbNome(ActionEvent event) {
        if (rbCPF.isSelected()) {
            rbCPF.setSelected(false);
        }
    }

    @FXML
    private void evtRbCPF(ActionEvent event) {
        if (rbNome.isSelected()) {
            rbNome.setSelected(false);
        }
    }

    @FXML
    private void evtBtnPesquisar(ActionEvent event) {
        String filtro = "";

        if (!txPesquisar.getText().isEmpty()) {
            if (rbNome.isSelected()) {
                filtro = " nome like '" + txPesquisar.getText().trim() + "%'";
            } else if (rbCPF.isSelected()) {
                filtro = "cpf like '" + txPesquisar.getText().trim() + "%'";
            }
        }

        carregarConsulta(filtro);
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        TelaVendaController.setCli(tabela.getSelectionModel().getSelectedItem());
        TelaReceberFiadoController.setCli(tabela.getSelectionModel().getSelectedItem());
        TelaPedidosArmazemController.setCli(tabela.getSelectionModel().getSelectedItem());
        Stage self = (Stage) tabela.getScene().getWindow();
        self.close();
    }

    public void carregarConsulta(String filtro) {
        DALConsulta dal = new DALConsulta();
        List<Cliente> cli = new ArrayList();
        ObservableList<Cliente> f;

        cli = (List<Cliente>) dal.getClientes(filtro);
        f = FXCollections.observableArrayList(cli);
        tabela.setItems(f);
    }

}

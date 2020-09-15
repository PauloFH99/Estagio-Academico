package estagiocarvaobarao.utils;

import estagiocarvaobarao.controller.ControllerProduto;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Armazem;
import estagiocarvaobarao.ui.TelaEntradaProdutosController;
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
public class ConsultaArmazemController implements Initializable {

    @FXML
    private RadioButton rbDescri;
    @FXML
    private RadioButton rbCat;
    @FXML
    private Button btnPesquisar;
    @FXML
    private TableView<Armazem> tabela;
    @FXML
    private TableColumn<Armazem, Integer> colCod;
    @FXML
    private TableColumn<Armazem, String> colCat;
    @FXML
    private TableColumn<Armazem, String> colDescri;
    @FXML
    private TableColumn<Armazem, Integer> colQtde;
    @FXML
    private TextField txPesquisa;
    public static Armazem produto;
    public static int flag = 0;
    public static int cod_for = 0;
    public static int cod_cli = 0;

    public static int getCod_cli() {
        return cod_cli;
    }

    public static void setCod_cli(int cod_cli) {
        ConsultaArmazemController.cod_cli = cod_cli;
    }

    public static int getCod_for() {
        return cod_for;
    }

    public static void setCod_for(int cod_for) {
        ConsultaArmazemController.cod_for = cod_for;
    }

    public static int getFlag() {
        return flag;
    }

    public static void setFlag(int flag) {
        ConsultaArmazemController.flag = flag;
    }

    public static Armazem getProduto() {
        return produto;
    }

    public static void setProduto(Armazem produto) {
        ConsultaArmazemController.produto = produto;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory("cod_prod"));
        colDescri.setCellValueFactory(new PropertyValueFactory("produto"));
        colCat.setCellValueFactory(new PropertyValueFactory("categoria"));
        colQtde.setCellValueFactory(new PropertyValueFactory("quantidade"));
        carregarConsulta("");
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
        carregarConsulta(filtro);
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        produto = tabela.getSelectionModel().getSelectedItem();
        TelaVendaController.setArmazem(produto);
        TelaEntradaProdutosController.setArmazem(produto);
        Stage self = (Stage) tabela.getScene().getWindow();
        self.close();
    }

    public void carregarConsulta(String filtro) {
        ControllerProduto cp = new ControllerProduto();
        cp.carregarConsulta(filtro, tabela, flag, cod_for, cod_cli);

    }

}

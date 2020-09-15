/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.utils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.EntradaProdutos;
import estagiocarvaobarao.ui.TelaEntradaProdutosController;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
public class ConsultaEntradaProdutosController implements Initializable {

    private RadioButton rbCliente;
    @FXML
    private RadioButton rbEmissao;
    @FXML
    private TextField txPesquisar;
    @FXML
    private Button btnPesquisar;
    @FXML
    private TableView<EntradaProdutos> tabela;
    @FXML
    private TableColumn<EntradaProdutos, String> colEmissao;
    @FXML
    private TableColumn<EntradaProdutos, String> colFornecedor;
    @FXML
    private TableColumn<EntradaProdutos, Double> colTotal;
    @FXML
    private RadioButton rbFornecedor;
    @FXML
    private FontAwesomeIcon iconcalendar;
    @FXML
    private Label lbfim;
    @FXML
    private Label lbini;
    @FXML
    private DatePicker dpfinal;
    @FXML
    private DatePicker dpinicial;
    @FXML
    private Label lberroini;
    @FXML
    private Label lberrofim;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        colEmissao.setCellValueFactory(new PropertyValueFactory("emissao"));
        colFornecedor.setCellValueFactory(new PropertyValueFactory("fornecedor"));
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));
        carregarConsulta("", "", "");

        // TODO
    }

    public void desativaData(boolean flag) {
        lbini.setVisible(flag);
        lbfim.setVisible(flag);
        dpinicial.setVisible(flag);
        dpfinal.setVisible(flag);
        iconcalendar.setVisible(flag);

    }

    @FXML
    private void evtRbFornecedor(ActionEvent event) {
        if (rbEmissao.isSelected()) {
            rbEmissao.setSelected(false);
        } else {
            rbFornecedor.setSelected(true);
        }
        desativaData(false);
        txPesquisar.setVisible(true);
    }

    @FXML
    private void evtRbEmissao(ActionEvent event) {
        if (rbFornecedor.isSelected()) {
            rbFornecedor.setSelected(false);
        } else {
            rbEmissao.setSelected(true);
        }
        desativaData(true);
        txPesquisar.setVisible(false);
    }

    @FXML
    private void evtBtnPesquisar(ActionEvent event) {
        String filtro = "";
        String dtini = "";
        String dtfin = "";
        if (!txPesquisar.getText().isEmpty()) {
            if (rbFornecedor.isSelected()) {
                filtro = " where f.nomefantasia ilike '%" + txPesquisar.getText().trim() + "%'";
            }
        }
        if (dpinicial.getValue() != null && dpfinal.getValue() != null) {
            dtini = dpinicial.getValue().toString();
            dtfin = dpfinal.getValue().toString();
        }
        carregarConsulta(filtro, dtini, dtfin);
    }

    @FXML
    private void onDoubleClick(MouseEvent event) {
        TelaEntradaProdutosController.setEntradaproduto(tabela.getSelectionModel().getSelectedItem());
        Stage self = (Stage) tabela.getScene().getWindow();
        self.close();
    }

    public void carregarConsulta(String filtro, String dpini, String dpfim) {
        DALConsulta dal = new DALConsulta();
        List<EntradaProdutos> comp = new ArrayList();
        ObservableList<EntradaProdutos> c;

        comp = (List<EntradaProdutos>) dal.getCompras(filtro, dpini, dpfim);
        c = FXCollections.observableArrayList(comp);
        tabela.setItems(c);
    }

}

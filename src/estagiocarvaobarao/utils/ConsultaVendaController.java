/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.utils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Venda;
import estagiocarvaobarao.ui.TelaVendaController;
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
public class ConsultaVendaController implements Initializable {

    @FXML
    private RadioButton rbCliente;
    @FXML
    private RadioButton rbEmissao;
    @FXML
    private TextField txPesquisar;
    @FXML
    private Button btnPesquisar;
    @FXML
    private TableView<Venda> tabela;
    @FXML
    private TableColumn<Venda, String> colEmissao;
    @FXML
    private TableColumn<Venda, String> colCliente;
    @FXML
    private TableColumn<Venda, Double> colTotal;
    @FXML
    private DatePicker dpinicial;
    @FXML
    private DatePicker dpfinal;
    @FXML
    private FontAwesomeIcon iconcalendar;
    @FXML
    private Label lberrodtini;
    @FXML
    private Label lberrodtfinal;
    @FXML
    private Label lbini;
    @FXML
    private Label lbfim;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        colEmissao.setCellValueFactory(new PropertyValueFactory("emissao"));
        colCliente.setCellValueFactory(new PropertyValueFactory("Cliente"));
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
    private void evtRbCliente(ActionEvent event) {
        if (rbEmissao.isSelected()) {
            rbEmissao.setSelected(false);
        } else {
            rbCliente.setSelected(true);
        }
        desativaData(false);
        txPesquisar.setVisible(true);
    }

    @FXML
    private void evtRbEmissao(ActionEvent event) {
        if (rbCliente.isSelected()) {
            rbCliente.setSelected(false);
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
            if (rbCliente.isSelected()) {
                filtro = " where c.nome ilike '%" + txPesquisar.getText().trim() + "%'";
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
        TelaVendaController.setVenda(tabela.getSelectionModel().getSelectedItem());
        Stage self = (Stage) tabela.getScene().getWindow();
        self.close();
    }

    public void carregarConsulta(String filtro, String dtini, String dtfin) {
        DALConsulta dal = new DALConsulta();
        List<Venda> cli = new ArrayList();
        ObservableList<Venda> f;

        cli = (List<Venda>) dal.getVendas(filtro, dtini, dtfin);
        f = FXCollections.observableArrayList(cli);
        tabela.setItems(f);
    }

}

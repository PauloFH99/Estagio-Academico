/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import estagiocarvaobarao.entidade.ContasPagar;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class MenuFormPagtoController implements Initializable {

    @FXML
    private DatePicker dpprivenci;
    @FXML
    private JFXTextField txqtdeparcelas;
    @FXML
    private JFXTextField txdiasentreparc;

    @FXML
    private JFXButton btnconfirmar;
    @FXML
    private Label lbqtdeparc;
    @FXML
    private Label lbforma;
    @FXML
    private FontAwesomeIcon ftformpagt;
    @FXML
    private Label lbdata;
    @FXML
    private Label lbdiasentreparc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void evtConfirmar(ActionEvent event) {
        int erro = 0;
        if (erro == 0) {
            TelaLancarDespesasController.setQtdeentreparcelas(Integer.parseInt(txdiasentreparc.getText()));
            TelaLancarDespesasController.setDtprazo(dpprivenci.getValue());
            TelaLancarDespesasController.setQtdeparcelas(Integer.parseInt(txqtdeparcelas.getText()));
            lbdata.setVisible(true);

            lbdata.setText(dpprivenci.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            lbforma.setVisible(true);
            ftformpagt.setVisible(true);
            lbqtdeparc.setVisible(true);
            lbqtdeparc.setText(txqtdeparcelas.getText());
            lbdiasentreparc.setVisible(true);
            lbdiasentreparc.setText(txdiasentreparc.getText());
        }
    }

}

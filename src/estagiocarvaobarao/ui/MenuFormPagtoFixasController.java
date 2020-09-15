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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
public class MenuFormPagtoFixasController implements Initializable {

    private TableView< ContasPagar> tabela;
    @FXML
    private DatePicker dpvenci;
   
    
    @FXML
    private JFXButton btnconfirmar;
  
    @FXML
    private Label lbdata;
    @FXML
    private FontAwesomeIcon ftformpagt;
    @FXML
    private Label lbforma;
    @FXML
    private Label lbqtdemeses;
    @FXML
    private JFXTextField txqtdemeses;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }

    /**
     *
     * @param event
     */
    @FXML
    public void evtConfirmar(ActionEvent event) {
        int erro=0;
        if(erro==0){
        TelaLancarDespesasController.setDtprazo(dpvenci.getValue());
        TelaLancarDespesasController.setQtdeparcelas(Integer.parseInt(txqtdemeses.getText()));
        lbdata.setVisible(true);
        lbdata.setText(dpvenci.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        lbforma.setVisible(true);
        ftformpagt.setVisible(true);
        lbqtdemeses.setVisible(true);
        lbqtdemeses.setText(txqtdemeses.getText());
        }
    }

}

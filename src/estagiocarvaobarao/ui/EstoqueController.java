/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import estagiocarvaobarao.entidade.Armazem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class EstoqueController implements Initializable {

    @FXML
    private TableView<Armazem> tabelaA;
    @FXML
    private TableColumn<Armazem, String> colDesc;
    @FXML
    private TableColumn<Armazem, Integer> colQtade;
    @FXML
    private TableColumn<Armazem, String> colCat;
    @FXML
    private TableView<?> tabelaC;
    Armazem arm = new Armazem();
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colDesc.setCellValueFactory(new PropertyValueFactory("produto"));
        colCat.setCellValueFactory(new PropertyValueFactory("categoria"));
        colQtade.setCellValueFactory(new PropertyValueFactory("quantidade"));
        atualizarTabela();
    } 
    public void atualizarTabela() {
        ObservableList<Armazem> prod_a = null;
        if (tabelaA.getItems() != null) {
            tabelaA.getItems().clear();
        }
       
        prod_a = FXCollections.observableArrayList(arm.get());
        tabelaA.setItems(prod_a);
    }
    
}

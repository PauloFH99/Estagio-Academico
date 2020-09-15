/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import estagiocarvaobarao.EstagioCarvaoBarao;
import estagiocarvaobarao.controller.ControllerCategoria;
import estagiocarvaobarao.dal.DALCategoria;
import estagiocarvaobarao.entidade.Categoria;
import estagiocarvaobarao.utils.MaskFieldUtil;
import estagiocarvaobarao.utils.Mensagens;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaCategoriaCadController implements Initializable {

    @FXML
    private Pane pnbtn;
    @FXML
    private JFXButton btnovo;
    @FXML
    private JFXButton btalterar;
    @FXML
    private JFXButton btapagar;
    @FXML
    private JFXButton btconfirmar;
    @FXML
    private JFXButton btcancelar;
    @FXML
    private Pane pnpesquisa;
    @FXML
    private JFXTextField txpesquisar;
    @FXML
    private JFXButton btpesquisar;
    @FXML
    private TableView<Categoria> tabela;
    @FXML
    private TableColumn<Categoria, Integer> colcod;
    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;

    @FXML
    private JFXTextField txnome;

    private JFXComboBox<Categoria> cbnivel;

   
    @FXML
    private TableColumn<Categoria, String> coldesc;
    ControllerCategoria catcontro = new ControllerCategoria();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        coldesc.setCellValueFactory(new PropertyValueFactory("descricao"));

        catcontro.estadoInicial(pnpesquisa,
                pndados,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome, tabela);
    }

    private void estadoInicial() {
        catcontro.estadoInicial(pnpesquisa,
                pndados,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome, tabela);
    }

    private void carregaTabela(String filtro) {

        catcontro.carregaTabela(tabela, filtro);
    }

    private void estadoEdicao() {
        catcontro.estadoEdicao(pnpesquisa, pndados, btconfirmar, btapagar, btalterar, txnome,txpesquisar);

    }

    @FXML
    private void clknovo(ActionEvent event) {
        catcontro.estadoEdicao(pnpesquisa, pndados, btconfirmar, btapagar, btalterar, txnome,txpesquisar);
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        catcontro.estadoEdicao(pnpesquisa, pndados, btconfirmar, btapagar, btalterar, txnome,txpesquisar);
        catcontro.alterar(tabela, txcod, txnome);

    }

    @FXML
    private void clkapagar(ActionEvent event) {
        catcontro.excluir(tabela);

    }

    private boolean CampoVazio(String valor) {
        boolean resultado = (valor.isEmpty() || valor.trim().isEmpty());
        return resultado;
    }

    private int retornaValor(String valor) {
        int res = 0;
        if (!valor.equals("")) {
            res = Integer.parseInt(valor);
        }
        return res;
    }

    public void validar(JFXTextField campo, String texto) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        campo.resetValidation();
        campo.getValidators().add(validator);
        validator.setMessage(texto);
        campo.validate();
    }

    @FXML
    private void clkconfirmar(ActionEvent event) {

        int cod, erro = 0;
 
        Mensagens msg = new Mensagens();
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        if (txnome.getText().isEmpty()) {
            validar(txnome, "Campo não estar vazio!");
            erro = 1;
        }
        if (erro == 0) {     
            catcontro.confirmar(cod, pnpesquisa, pndados, btconfirmar, btcancelar, btapagar, btalterar, btnovo, txnome, tabela);
        }
    }

    @FXML
    private void clkcancelar(ActionEvent event) {
        catcontro.cancelar(pnpesquisa, pndados, btconfirmar, btcancelar, btapagar, btnovo, btalterar, txnome, tabela);

    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        catcontro.pesquisar(txpesquisar, tabela);

    }

    @FXML
    private void evtTabela(MouseEvent event) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    @FXML
    private void evRbCodigo(MouseEvent event) {
    }


  

}

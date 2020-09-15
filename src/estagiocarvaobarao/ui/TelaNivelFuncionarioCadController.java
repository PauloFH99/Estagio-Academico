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
import estagiocarvaobarao.controller.ControllerNivelFuncionario;
import estagiocarvaobarao.dal.DALConsulta;

import estagiocarvaobarao.dal.DALNivelFuncionario;

import estagiocarvaobarao.entidade.NivelFuncionario;
import static estagiocarvaobarao.ui.TelaFornecedorCadController.cid;

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
public class TelaNivelFuncionarioCadController implements Initializable {

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
    private TableView<NivelFuncionario> tabela;
    @FXML
    private TableColumn<NivelFuncionario, Integer> colcod;
    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;

    @FXML
    private JFXTextField txnome;

    private JFXComboBox<NivelFuncionario> cbnivel;

    
    @FXML
    private TableColumn<NivelFuncionario, String> coldesc;
    ControllerNivelFuncionario nfc = new ControllerNivelFuncionario();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        coldesc.setCellValueFactory(new PropertyValueFactory("descricao"));

        nfc.estadoInicial(pnpesquisa,
                pndados,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome,
                tabela);
    }

    private void estadoInicial() {
        nfc.estadoInicial(pnpesquisa,
                pndados,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome,
                tabela);
    }

    private void carregaTabela(String filtro) {
        nfc.carregaTabela(tabela, filtro);
    }

    private void estadoEdicao() {
        nfc.estadoEdicao(pndados,
                pnpesquisa,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome,
                tabela,txpesquisar);

    }

    @FXML
    private void clknovo(ActionEvent event) {
        nfc.estadoEdicao(pndados,
                pnpesquisa,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome,
                tabela,txpesquisar);
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        nfc.estadoEdicao(pndados,
                pnpesquisa,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome,
                tabela,txpesquisar);
        nfc.alterar(tabela,txcod,txnome);
        

    }

    @FXML
    private void clkapagar(ActionEvent event) {
        nfc.apagar(tabela);
        
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
            validar(txnome, "Campo não pode estra vazio!");
            erro = 1;
        }
        if (erro == 0) {
            nfc.confirmar(cod, txnome.getText(),pnpesquisa,
                pndados,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome,
                tabela);
            
        }
    }

    @FXML
    private void clkcancelar(ActionEvent event) {
        nfc.cancelar(pnpesquisa,
                pndados,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txnome,
                tabela);
        
    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        nfc.Pesquisar(txpesquisar,tabela);
       
    }

    @FXML
    private void evtTabela(MouseEvent event) {
         nfc.evtTabela(tabela,btalterar,btapagar);
        
    }


}

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
import estagiocarvaobarao.controller.ControlerDespesas;
import estagiocarvaobarao.dal.DALDespesas;
import estagiocarvaobarao.entidade.Despesas;
import static estagiocarvaobarao.ui.TelaFornecedorCadController.cid;
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
import javafx.scene.control.Control;
import javafx.scene.control.Label;
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
public class TelaDespesasCadController implements Initializable {

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
    private TableView<Despesas> tabela;
    @FXML
    private TableColumn<Despesas, Integer> colcod;
    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;

    @FXML
    private JFXTextField txnome;

   
    @FXML
    private TableColumn<Despesas, String> coldesc;
    @FXML
    private TableColumn<Despesas, String> coldiapag;
    @FXML
    private JFXTextField txdiapag;
    ControlerDespesas cdes = new ControlerDespesas();
    @FXML
    private Label lberro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        MaskFieldUtil.numericField(txdiapag);
        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        coldesc.setCellValueFactory(new PropertyValueFactory("descricao"));
        coldiapag.setCellValueFactory(new PropertyValueFactory("dia_pagar"));
        estadoInicial();
    }

    private void estadoInicial() {
        cdes.estadoInicial(pnpesquisa, pndados, btconfirmar, btcancelar,
                btapagar, btalterar, btnovo, txnome, txdiapag, tabela, lberro);
    }

    private void estadoEdicao() {
        cdes.estadoEdicao(pnpesquisa, pndados, btconfirmar, btcancelar,
                btapagar, btalterar, txnome, txdiapag,txpesquisar);

    }

    @FXML
    private void clknovo(ActionEvent event) {
        cdes.estadoEdicao(pnpesquisa, pndados, btconfirmar, btcancelar,
                btapagar, btalterar, txnome, txdiapag,txpesquisar);
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        cdes.estadoEdicao(pnpesquisa, pndados, btconfirmar, btcancelar,
                btapagar, btalterar, txnome, txdiapag,txpesquisar);
        cdes.alterar(tabela, txcod, txnome, txdiapag);

    }

    @FXML
    private void clkapagar(ActionEvent event) {
        cdes.apagar(tabela);

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
            validar(txnome, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txdiapag.getText().isEmpty()) {
            validar(txdiapag, "Campo não pode estar vazio!");
            erro = 1;
        } else {
            if (Integer.parseInt(txdiapag.getText()) == 0) {
                lberro.setText("O dia não pode ser 0!");
                erro = 1;
            } else if (Integer.parseInt(txdiapag.getText()) < 0) {
                lberro.setText("O dia não pode ser negativo!");
                erro = 1;
            } else if (Integer.parseInt(txdiapag.getText()) > 30) {
                lberro.setText("O dia não pode ser maior que 30!");
                erro = 1;
            }
        }

        if (erro == 0) {

            cdes.confirmar(cod, pnpesquisa, pndados, btconfirmar, btcancelar,
                    btapagar, btalterar, btnovo, txnome, txdiapag, tabela, lberro);

        }
    }

    @FXML
    private void clkcancelar(ActionEvent event) {
        cdes.cancelar(pnpesquisa, pndados, btconfirmar, btcancelar,
                btapagar, btalterar, btnovo, txnome, txdiapag, tabela, lberro);

    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        cdes.pesquisar(txpesquisar, tabela);

    }

    @FXML
    private void evtTabela(MouseEvent event) {
        cdes.evtTabela(tabela, btalterar, btapagar);

    }


    @FXML
    private void diavalida(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txdiapag.getText().isEmpty()) {
            cdes.validaDia(Integer.parseInt(txdiapag.getText()), event, lberro);
        }
    }

}

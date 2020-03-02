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
import estagiocarvaobarao.dal.DALCategoria;
import estagiocarvaobarao.entidade.Categoria;
import static estagiocarvaobarao.ui.TelaFornecedorCadController.cid;
import estagiocarvaobarao.utils.MaskFieldUtil;
import estagiocarvaobarao.utils.Messages;
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
    private JFXRadioButton rbcodigo;
    @FXML
    private ToggleGroup Pesquisa;
    @FXML
    private JFXRadioButton rbdescricao;
    @FXML
    private TableColumn<Categoria, String> coldesc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        coldesc.setCellValueFactory(new PropertyValueFactory("descricao"));

        estadoInicial();
    }

    private void estadoInicial() {
        pnpesquisa.setDisable(false);
        pndados.setDisable(true);
        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);
        ObservableList<Node> componentes = pndados.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
            if (n instanceof ComboBox) {
                ((ComboBox) n).getSelectionModel().select(0);
            }
        }

        carregaTabela("");
    }

    private int carregaTabela(String filtro) {
        DALCategoria dal = new DALCategoria();
        List<Categoria> res = dal.get(filtro);
        ObservableList<Categoria> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
        return res.size();
    }

    private void estadoEdicao() {

        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txnome.requestFocus();
    }

    @FXML
    private void clknovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        estadoEdicao();
        DALCategoria dal = new DALCategoria();
        
        Categoria c = (Categoria) tabela.getSelectionModel().getSelectedItem();
        
        txcod.setText("" + c.getCodigo());
        txnome.setText("" + c.getDescricao());

    }

    @FXML
    private void clkapagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            DALCategoria dal = new DALCategoria();
            Categoria c;
            c = tabela.getSelectionModel().getSelectedItem();
            dal.apagar(c.getCodigo());
            carregaTabela("");
        }
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

    @FXML
    private void clkconfirmar(ActionEvent event) {

        int cod, erro = 0;
        Categoria c;
        DALCategoria dal = new DALCategoria();

        Messages msg = new Messages();
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }

        if (erro == 0) {

            c = new Categoria(cod, txnome.getText());

            if (c.getCodigo() == 0) {
                if (dal.salvar(c)) {
                    msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
                } else {
                    msg.Error("Erro ao gravar!", "Problemas ao Gravar");
                }
            } else if (dal.alterar(c)) {
                msg.Confirmation("Gravação concluida", "Alterado com Sucesso");
            } else {
                msg.Error("Erro ao alterar!", "Problemas ao Alterar");
            }
            estadoInicial();
        }
    }

    @FXML
    private void clkcancelar(ActionEvent event) {
        if (!pndados.isDisabled())//encontra em estado de edição
        {
            estadoInicial();
        } else {
            btnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        if (!txpesquisar.getText().isEmpty()) {
            if (rbdescricao.isSelected()) {
                carregaTabela("upper(descricao) like '%" + txpesquisar.getText().toUpperCase() + "%'");
            } else {
                if (rbcodigo.isSelected()) {
                    carregaTabela("codigo=" + txpesquisar.getText().toUpperCase());
                }
            }
        } else {
            carregaTabela("upper(descricao) like '%" + txpesquisar.getText().toUpperCase() + "%'");
        }
    }

    @FXML
    private void evtTabela(MouseEvent event) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    @FXML
    private void evRbCodigo(ActionEvent event) {
    }

    @FXML
    private void evRbDescricao(ActionEvent event) {
    }

   

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import static de.jensd.fx.glyphs.GlyphsStyle.RED;
import estagiocarvaobarao.controller.ControllerProduto;
import estagiocarvaobarao.dal.DALCategoria;
import estagiocarvaobarao.dal.DALProduto;
import estagiocarvaobarao.entidade.Categoria;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.utils.MaskFieldUtil;
import estagiocarvaobarao.utils.Mensagens;
import java.awt.Color;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import static javafx.application.Platform.exit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaProdCadController implements Initializable {

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
    private JFXRadioButton rbdescricao;
    @FXML
    private ToggleGroup Pesquisa;
    @FXML
    private JFXRadioButton rbcategoria;
    @FXML
    private JFXTextField txpesquisar;
    @FXML
    private JFXButton btpesquisar;
    @FXML
    private TableView<Produto> tabela;
    @FXML
    private TableColumn<Produto, Integer> colcod;
    @FXML
    private TableColumn<Produto, String> coldescricao;
    @FXML
    private TableColumn<Produto, String> colpreco;
    @FXML
    private TableColumn<Produto, String> colcat;
    @FXML
    private TableColumn<Produto, Integer> colestfisi;
    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;
    @FXML
    private JFXTextField txdescricao;
    @FXML
    private JFXComboBox<Categoria> cbCat;
    @FXML
    private JFXTextField txmin;
    @FXML
    private JFXTextField txmax;
    @FXML
    private JFXTextField txfisi;
    @FXML
    private JFXTextField txpreco;
    @FXML
    private JFXCheckBox chkAtivo;
    @FXML
    private Label lbestmin;
    @FXML
    private Label lbestmax;
    @FXML
    private Label lbestfisi;
    ControllerProduto controllerproduto = new ControllerProduto();
    @FXML
    private JFXTextField txpeso;
    @FXML
    private Label lbpeso;
  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFieldUtil.monetaryField(txpreco);
        MaskFieldUtil.monetaryField(txpeso);
        MaskFieldUtil.numericField(txmin);
        MaskFieldUtil.numericField(txmax);
        MaskFieldUtil.numericField(txfisi);
        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        coldescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        colpreco.setCellValueFactory(new PropertyValueFactory("preco"));
        colcat.setCellValueFactory(new PropertyValueFactory("categoria"));
        colestfisi.setCellValueFactory(new PropertyValueFactory("estoque"));
        estadoInicial();
    }

    private void estadoInicial() {
        controllerproduto.estadoInicial(pnpesquisa,
                pndados,
                btconfirmar,
                btcancelar,
                btapagar,
                btalterar,
                btnovo,
                txdescricao,
                cbCat,
                txmin,
                txmax,
                txfisi,
                txpreco,
                txpeso,
                lbestmin,
                lbestmax,
                lbestfisi,lbpeso,tabela);
    }

    private void estadoEdicao() {
        controllerproduto.estadoEdicao(cbCat, pnpesquisa, pndados, btconfirmar, btapagar, btalterar, txdescricao, txmin, txmax, txfisi, txpreco,txpeso);

    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        controllerproduto.pesquisar(txpesquisar, rbdescricao, rbcategoria, tabela);

    }

    @FXML
    private void clknovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        estadoEdicao();
        controllerproduto.alterar(tabela, txcod, txmin, txmax, txdescricao, txpreco, txfisi,txpeso ,chkAtivo, cbCat);

    }

    @FXML
    private void clkapagar(ActionEvent event) {
        controllerproduto.apagar(tabela);

    }

    public boolean CampoVazio(String valor) {
        boolean resultado = (valor.isEmpty() || valor.trim().isEmpty());
        return resultado;
    }

    public int retornaValor(String valor) {
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

    public void validarCb(JFXComboBox campo) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        campo.resetValidation();
        campo.getValidators().add(validator);
        validator.setMessage("Campo não pode estar vazio!");
        campo.validate();
    }

    @FXML
    private void clkconfirmar(ActionEvent event) {
        int cod = 0, erro = 0;
        Mensagens msg = new Mensagens();

        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }

        if (txdescricao.getText().isEmpty()) {
            validar(txdescricao, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (cbCat.getSelectionModel().isEmpty()) {
            validarCb(cbCat);
            erro = 1;
        }
        if (txmin.getText().isEmpty()) {
            validar(txmin, "Campo não pode estar vazio!");
            erro = 1;
        } else if (Integer.parseInt(txmin.getText()) == 0) {
            msg.campoNumerico(txmin, lbestmin, "O estoque mínimo não pode ser 0!");
            erro = 1;
        } else if (Integer.parseInt(txmin.getText()) < 0) {
            msg.campoNumerico(txmin, lbestmin, "O estoque mínimo não pode ser negativo!");
            erro = 1;
        } else if (!txmin.getText().isEmpty() && !txmax.getText().isEmpty()
                && retornaValor(txmin.getText()) >= retornaValor(txmax.getText())) {
            msg.campoNumerico(txmin, lbestmin, "O estoque mínimo tem que ser menor que o estoque máximo!");
            erro = 1;
        }

        if (txmax.getText().isEmpty()) {
            validar(txmax, "Campo não pode estar vazio!");
            erro = 1;
        } else if (Integer.parseInt(txmax.getText()) == 0) {
            msg.campoNumerico(txmax, lbestmax, "O estoque máximo não pode ser 0!");
            erro = 1;
        } else if (Integer.parseInt(txmax.getText()) < 0) {
            msg.campoNumerico(txmax, lbestmax, "O estoque máximo não pode ser negativo!");
            erro = 1;
        } else if (!txmin.getText().isEmpty() && !txmax.getText().isEmpty()
                && retornaValor(txmax.getText()) <= retornaValor(txmin.getText())) {
            msg.campoNumerico(txmax, lbestmax, "O estoque máximo tem que ser maior que o estoque mínimo!");
            erro = 1;
        }

        if (txfisi.getText().isEmpty()) {
            validar(txfisi, "Campo não pode estar vazio!");
            erro = 1;
        } else if (Integer.parseInt(txfisi.getText()) == 0) {
            msg.campoNumerico(txfisi, lbestfisi, "O estoque físico não pode ser 0!");
            erro = 1;
        } else if (Integer.parseInt(txfisi.getText()) < 0) {
            msg.campoNumerico(txfisi, lbestfisi, "O estoque físico não pode ser negativo!");
            erro = 1;
        }

        if (txpreco.getText().isEmpty()) {
            validar(txpreco, "Campo não pode estar vazio!");
            erro = 1;
        }
       
        if (erro == 0) {
            controllerproduto.confirmar(cod, txmin, txmax,
                    txdescricao, txpreco,
                    txfisi, chkAtivo,
                    cbCat.getSelectionModel().getSelectedItem(), pnpesquisa,
                    pndados,
                    btconfirmar,
                    btcancelar,
                    btapagar,
                    btalterar,
                    btnovo,
                    txdescricao,
                    txpeso,
                    cbCat,
                    lbestmin,
                    lbestmax,
                    lbestfisi,lbpeso, tabela);

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
    private void evtTabela(MouseEvent event) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    private void exit(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

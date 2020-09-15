/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.dal.DALCategoria;
import estagiocarvaobarao.dal.DALNivelFuncionario;
import estagiocarvaobarao.entidade.Categoria;
import estagiocarvaobarao.entidade.NivelFuncionario;
import estagiocarvaobarao.utils.Mensagens;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;

/**
 *
 * @author Paulo
 */
public class ControllerCategoria {

    Mensagens msg = new Mensagens();

    public ObservableList<Categoria> carregaTabela(TableView<Categoria> tabela, String filtro) {
        DALCategoria dal = new DALCategoria();
        List<Categoria> res = dal.get(filtro);
        ObservableList<Categoria> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
        return modelo;
    }

    public void estadoInicial(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, TableView<Categoria> tabela) {
        pnpesquisa.setDisable(false);
        pndados.setDisable(true);
        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);
        txnome.resetValidation();
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

        carregaTabela(tabela, "");
    }

    public void estadoEdicao(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btapagar, JFXButton btalterar, JFXTextField txnome, JFXTextField txpesquisar) {

        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txnome.requestFocus();
        txpesquisar.setText("");
        txnome.resetValidation();
       // msg.campoVazio(txnome, "");
    }

    public void alterar(TableView<Categoria> tabela, JFXTextField txcod, JFXTextField txnome) {
        Categoria c = (Categoria) tabela.getSelectionModel().getSelectedItem();
        txcod.setText("" + c.getCodigo());
        txnome.setText("" + c.getDescricao());
    }

    public void excluir(TableView<Categoria> tabela) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            Categoria c = new Categoria();
            c = tabela.getSelectionModel().getSelectedItem();
            if (!c.apagar(c.getCodigo())) {
                msg.Error("Erro", "Essa categoria é utilizada por produtos!");
            }
            carregaTabela(tabela, "");
        }
    }

    public void cancelar(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btnovo, JFXButton btalterar, JFXTextField txnome, TableView<Categoria> tabela) {
        if (!pndados.isDisabled())//encontra em estado de edição
        {
            estadoInicial(pnpesquisa, pndados, btconfirmar, btcancelar, btapagar, btalterar, btnovo, txnome, tabela);
        } else {
            btnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    public void pesquisar(JFXTextField txpesquisar,TableView<Categoria> tabela) {
        ControllerCategoria c = new ControllerCategoria();
        if (!txpesquisar.getText().isEmpty()) {

            c.carregaTabela(tabela, "upper(descricao) like '%" + txpesquisar.getText().toUpperCase() + "%'");

        } else {
            c.carregaTabela(tabela, "upper(descricao) like '%" + txpesquisar.getText().toUpperCase() + "%'");
        }
    }

    public void confirmar(int cod, Pane pnpesquisa, Pane pndados, JFXButton btconfirmar,
            JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo,
            JFXTextField txnome, TableView<Categoria> tabela) {
        Categoria c = new Categoria(cod, txnome.getText());
        if (c.getCodigo() == 0) {
            if (c.salvar(c)) {
                msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
            } else {
                msg.Error("Erro ao gravar!", "Problemas ao Gravar");
            }
        } else if (c.alterar(c)) {
            msg.Confirmation("Gravação concluida", "Alterado com Sucesso");
        } else {
            msg.Error("Erro ao alterar!", "Problemas ao Alterar");
        }
        estadoInicial(pnpesquisa, pndados, btconfirmar, btcancelar, btapagar, btalterar, btnovo, txnome, tabela);
    }

}

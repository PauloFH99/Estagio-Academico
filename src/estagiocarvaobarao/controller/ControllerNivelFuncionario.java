/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.dal.DALNivelFuncionario;
import estagiocarvaobarao.entidade.NivelFuncionario;
import estagiocarvaobarao.entidade.Produto;
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
public class ControllerNivelFuncionario {

    Mensagens msg = new Mensagens();

    public ObservableList<NivelFuncionario> carregaTabela(TableView<NivelFuncionario> tabela, String filtro) {
        DALNivelFuncionario dal = new DALNivelFuncionario();
        List<NivelFuncionario> res = dal.get(filtro);
        ObservableList<NivelFuncionario> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
        return modelo;
    }

    public void estadoInicial(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, TableView<NivelFuncionario> tabela) {
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

    public void estadoEdicao(Pane pndados, Pane pnpesquisa, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, TableView<NivelFuncionario> tabela, JFXTextField txpesquisar) {

        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txnome.requestFocus();
        txpesquisar.setText("");
//        msg.campoVazio(txnome, "");
        txnome.resetValidation();
    }

    public void alterar(TableView<NivelFuncionario> tabela, JFXTextField txcod, JFXTextField txnome) {
        NivelFuncionario nf = (NivelFuncionario) tabela.getSelectionModel().getSelectedItem();
        txcod.setText("" + nf.getCodigo());
        txnome.setText("" + nf.getDescricao());
    }

    public void apagar(TableView<NivelFuncionario> tabela) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            NivelFuncionario nf = new NivelFuncionario();
            nf = tabela.getSelectionModel().getSelectedItem();
            if (!nf.apagar(nf.getCodigo())) {
                msg.Error("Erro", "Esse nível é utilizada por um funcionário!");
            }
            carregaTabela(tabela, "");
        }
    }

    public void cancelar(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, TableView<NivelFuncionario> tabela) {
        if (!pndados.isDisabled())//encontra em estado de edição
        {
            estadoInicial(pnpesquisa,
                    pndados,
                    btconfirmar,
                    btcancelar,
                    btapagar,
                    btalterar,
                    btnovo,
                    txnome,
                    tabela);
        } else {
            btnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    public void Pesquisar(JFXTextField txpesquisar, TableView<NivelFuncionario> tabela) {
        if (!txpesquisar.getText().isEmpty()) {

            carregaTabela(tabela, "upper(descricao) like '%" + txpesquisar.getText().toUpperCase() + "%'");

        } else {
            carregaTabela(tabela, "upper(descricao) like '%" + txpesquisar.getText().toUpperCase() + "%'");
        }
    }

    public void evtTabela(TableView<NivelFuncionario> tabela, JFXButton btalterar, JFXButton btapagar) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    public void confirmar(int cod, String text, Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, TableView<NivelFuncionario> tabela) {
        NivelFuncionario nf = new NivelFuncionario(cod, txnome.getText());
        Mensagens msg = new Mensagens();
        if (nf.getCodigo() == 0) {
            if (nf.salvar(nf)) {
                msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
            } else {
                msg.Error("Erro ao gravar!", "Problemas ao Gravar");
            }
        } else if (nf.alterar(nf)) {
            msg.Confirmation("Gravação concluida", "Alterado com Sucesso");
        } else {
            msg.Error("Erro ao alterar!", "Problemas ao Alterar");
        }
        estadoInicial(pnpesquisa, pndados, btconfirmar, btcancelar, btapagar, btalterar, btnovo, txnome, tabela);
    }
}

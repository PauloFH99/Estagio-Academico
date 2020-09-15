/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.dal.DALDespesas;
import estagiocarvaobarao.entidade.Despesas;
import estagiocarvaobarao.utils.Mensagens;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Paulo
 */
public class ControlerDespesas {

    Mensagens msg = new Mensagens();

    public ObservableList<Despesas> carregaTabela(TableView<Despesas> tabela, String filtro) {
        DALDespesas dal = new DALDespesas();
        List<Despesas> res = dal.get(filtro);
        ObservableList<Despesas> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
        return modelo;
    }

    public void estadoInicial(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar,
            JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, JFXTextField txdiapag, TableView<Despesas> tabela, Label lberro) {
        pnpesquisa.setDisable(false);
        pndados.setDisable(true);
        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);
        txnome.resetValidation();
        txdiapag.resetValidation();
        lberro.setText("");
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

    public void estadoEdicao(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar,
            JFXButton btapagar, JFXButton btalterar, JFXTextField txnome, JFXTextField txdiapag,JFXTextField txpesquisa) {
        Mensagens msg = new Mensagens();
        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txnome.requestFocus();
        txpesquisa.setText("");
        txnome.resetValidation();
//        msg.campoVazio(txnome, "");
//        msg.campoVazio(txdiapag, "");
    }

    public void alterar(TableView<Despesas> tabela, JFXTextField txcod, JFXTextField txnome, JFXTextField txdiapag) {
        Despesas d = (Despesas) tabela.getSelectionModel().getSelectedItem();
        txcod.setText("" + d.getCodigo());
        txnome.setText("" + d.getDescricao());
        txdiapag.setText("" + d.getDia_pagar());
    }

    public void apagar(TableView<Despesas> tabela) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            Despesas d = new Despesas();
            d = tabela.getSelectionModel().getSelectedItem();
            d.apagar(d.getCodigo());
            carregaTabela(tabela, "");
        }
    }

    public void cancelar(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, JFXTextField txdiapag, TableView<Despesas> tabela, Label lberro) {
        if (!pndados.isDisabled())//encontra em estado de edição
        {
            estadoInicial(pnpesquisa, pndados, btconfirmar, btcancelar, btapagar, btalterar, btnovo,
                    txnome, txdiapag, tabela, lberro);
        } else {
            btnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    public void pesquisar(JFXTextField txpesquisar, TableView<Despesas> tabela) {
        if (!txpesquisar.getText().isEmpty()) {
            carregaTabela(tabela, "upper(descricao) like '%" + txpesquisar.getText().toUpperCase() + "%'");
        } else {
            carregaTabela(tabela, "upper(descricao) like '%" + txpesquisar.getText().toUpperCase() + "%'");
        }
    }

    public void evtTabela(TableView<Despesas> tabela, JFXButton btalterar, JFXButton btapagar) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    public void confirmar(int cod, Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, JFXTextField txdiapag, TableView<Despesas> tabela, Label lberro) {
        Despesas d = new Despesas(cod, txnome.getText(), Integer.parseInt(txdiapag.getText()));
        if (d.getCodigo() == 0) {
            if (d.salvar(d)) {
                msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
            } else {
                msg.Error("Erro ao gravar!", "Problemas ao Gravar");
            }
        } else if (d.alterar(d)) {
            msg.Confirmation("Gravação concluida", "Alterado com Sucesso");
        } else {
            msg.Error("Erro ao alterar!", "Problemas ao Alterar");
        }
        estadoInicial(pnpesquisa, pndados, btconfirmar, btcancelar,
                btapagar, btalterar, btnovo, txnome, txdiapag, tabela, lberro);
    }

    public void validaDia(int dia, KeyEvent event, Label lberro) {
        if (event.getCode() == KeyCode.TAB) {
            if (dia == 0) {
                lberro.setText("O dia não pode ser 0!");

            } else if (dia < 0) {
                lberro.setText("O dia não pode ser negativo!");

            } else if (dia > 30) {
                lberro.setText("O dia não pode ser maior que 30!");

            }
        }

    }
}

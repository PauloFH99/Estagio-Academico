/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.dal.DALCategoria;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.dal.DALProduto;
import estagiocarvaobarao.entidade.Armazem;
import estagiocarvaobarao.entidade.Categoria;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.utils.Mensagens;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
import javafx.scene.layout.Pane;

/**
 *
 * @author Paulo
 */
public class ControllerProduto {

    Mensagens msg = new Mensagens();

    public ObservableList<Produto> carregaTabela(String filtro, TableView<Produto> tabela) {
        Produto p = new Produto();
        List<Produto> res = p.get(filtro);
        ObservableList<Produto> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
        return modelo;
    }

    public void carregarConsulta(String filtro, TableView<Armazem> tabela, int flag, int cod_for, int cod_cli) {
        DALConsulta dal = new DALConsulta();
        List<Armazem> prod = new ArrayList();
        ObservableList<Armazem> f;
        if (filtro.isEmpty() && flag != 0 && flag != 1) {
            prod = (List<Armazem>) dal.getProdutosArmazemAll("");
        } else if (!filtro.isEmpty() && flag != 0) {
            prod = (List<Armazem>) dal.getProdutosArmazem("", cod_for);
        } else if (flag == 0) {
            prod = (List<Armazem>) dal.getProdutosArmazem("", cod_for);
        } else {
            prod = (List<Armazem>) dal.getProdutosArmazemV("", cod_cli);
        }
        f = FXCollections.observableArrayList(prod);
        tabela.setItems(f);
    }

    public ObservableList<Produto> carregaTabelaCategoria(String filtro, TableView<Produto> tabela) {
        Produto p = new Produto();
        List<Produto> res = p.getCategoria(filtro);
        ObservableList<Produto> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
        return modelo;
    }

    public ComboBox<Categoria> carregaCategorias(ComboBox<Categoria> cbCat) {
        Categoria c = new Categoria();
        List<Categoria> cat = c.get("");
        cbCat.setItems(FXCollections.observableArrayList(cat));
        return cbCat;
    }

    public void estadoInicial(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txdescricao, JFXComboBox<Categoria> cbCat, JFXTextField txmin, JFXTextField txmax, JFXTextField txfisi, JFXTextField txpreco, JFXTextField txpeso, Label lbestmin, Label lbestmax, Label lbestfisi, Label lbpeso, TableView<Produto> tabela) {
        pnpesquisa.setDisable(false);
        pndados.setDisable(true);
        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);
        txdescricao.resetValidation();
        cbCat.resetValidation();
        txmin.resetValidation();
        txmax.resetValidation();
        txfisi.resetValidation();
        txpreco.resetValidation();
        txpeso.resetValidation();
        lbestmin.setText("");
        lbestmax.setText("");
        lbestfisi.setText("");
        lbpeso.setText("");
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

        carregaTabela("", tabela);
    }

    public void estadoEdicao(JFXComboBox<Categoria> cbCat, Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btapagar, JFXButton btalterar, JFXTextField txdescricao, JFXTextField txmin, JFXTextField txmax, JFXTextField txfisi, JFXTextField txpreco, JFXTextField txpeso) {
        carregaCategorias(cbCat);

        txdescricao.resetValidation();
        txfisi.resetValidation();
        txmax.resetValidation();
        txmin.resetValidation();
        txpreco.resetValidation();
        cbCat.resetValidation();
        txpeso.resetValidation();
        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txdescricao.requestFocus();
        txfisi.setDisable(false);
    }

    public void pesquisar(JFXTextField txpesquisar, JFXRadioButton rbdescricao, JFXRadioButton rbcategoria, TableView<Produto> tabela) {
        if (!txpesquisar.getText().isEmpty()) {
            if (rbdescricao.isSelected()) {
                carregaTabela("upper(descricao) like '%" + txpesquisar.getText().toUpperCase() + "%'", tabela);
            } else {
                if (rbcategoria.isSelected()) {
                    carregaTabelaCategoria(txpesquisar.getText(), tabela);
                }
            }
        } else {
            carregaTabela("upper(descricao) like '%" + txpesquisar.getText().toUpperCase() + "%'", tabela);
        }
    }

    public boolean decimal(String valor) {
        int k = 0;
        int pos = valor.indexOf(".");
        for (int i = pos + 1; i < valor.length(); i++) {
            k++;
            if (k == 2) {
                if (Integer.parseInt(String.valueOf(valor.charAt(i))) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void alterar(TableView<Produto> tabela, JFXTextField txcod, JFXTextField txmin, JFXTextField txmax, JFXTextField txdescricao, JFXTextField txpreco, JFXTextField txfisi, JFXTextField txpeso, JFXCheckBox chkAtivo, JFXComboBox<Categoria> cbCat) {
        Produto p = (Produto) tabela.getSelectionModel().getSelectedItem();
        txcod.setText("" + p.getCodigo());
        txmin.setText("" + p.getEst_min());
        txmax.setText("" + p.getEst_max());
        txdescricao.setText(p.getDescricao());

        if (decimal(String.valueOf(p.getPreco())) && String.valueOf(p.getPreco()).length() >= 3 && String.valueOf(p.getPreco()).length() <= 5 || p.getPreco() >= 100.0) {
            txpreco.setText(String.valueOf(p.getPreco()) + "0");
        } else {
            txpreco.setText(String.valueOf(p.getPreco()));
        }
        if (decimal(String.valueOf(p.getPeso())) && String.valueOf(p.getPeso()).length() >= 3 && String.valueOf(p.getPeso()).length() <= 5 || p.getPeso() >= 100.0) {
            txpeso.setText(String.valueOf(p.getPeso()) + "0");
        } else {
            txpeso.setText(String.valueOf(p.getPeso()));
        }
        txfisi.setText("" + p.getEstoque());
        chkAtivo.setSelected(p.isAtivo());
        cbCat.getSelectionModel().select(0);
        cbCat.getSelectionModel().select(p.getCategoria());
    }

    public void apagar(TableView<Produto> tabela) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            Produto p;
            Armazem arm = new Armazem();
            p = tabela.getSelectionModel().getSelectedItem();
            arm.apagar(p.getCodigo());
            if (p.apagar(p.getCodigo())) {
                msg.Confirmation("Exclusão concluida", "Excluído com Sucesso!");
            }
            carregaTabela("", tabela);
        }
    }

    public void confirmar(int cod, JFXTextField txmin, JFXTextField txmax, JFXTextField txdescricao, JFXTextField txpreco, JFXTextField txfisi, JFXCheckBox chkAtivo, Categoria selectedItem, Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txdescricao0, JFXTextField txpeso, JFXComboBox<Categoria> cbCat, Label lbestmin, Label lbestmax, Label lbestfisi, Label lbpeso, TableView<Produto> tabela) {
        Mensagens msg = new Mensagens();
        NumberFormat nf = new DecimalFormat("#,###.00");
        Armazem arm = new Armazem();
        try {
            Produto p = new Produto(cod, Integer.parseInt(txmin.getText()), Integer.parseInt(txmax.getText()),
                    txdescricao.getText(), nf.parse(txpreco.getText()).doubleValue(),
                    Integer.parseInt(txfisi.getText()), chkAtivo.isSelected(),
                    cbCat.getSelectionModel().getSelectedItem(), nf.parse(txpeso.getText()).doubleValue());

            if (p.getCodigo() == 0) {
                if (p.salvar(p)) {
                    msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
                    int codp = 0;
                    codp = p.getCod();
                    if (!arm.getProduto(codp)) {
                        arm.salvarP(codp, Integer.parseInt(txfisi.getText()));
                    }

                } else {
                    msg.Error("Erro ao gravar!", "Problemas ao Gravar");
                }
            } else if (p.alterar(p)) {
                msg.Confirmation("Gravação concluida", "Alterado com Sucesso");
                if (arm.getProduto(cod)) {
                    arm.alterarP(cod, Integer.parseInt(txfisi.getText()));
                }
            } else {
                msg.Error("Erro ao alterar!", "Problemas ao Alterar");
            }
            estadoInicial(pnpesquisa,
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
                    lbestfisi, lbpeso, tabela);
        } catch (Exception e) {
        }

    }

    public boolean validaProduto(String descricao) {
        Mensagens msg = new Mensagens();
        Produto p = new Produto();
        return p.validaProduto(descricao);
    }

}

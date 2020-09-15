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
import estagiocarvaobarao.entidade.Categoria;
import estagiocarvaobarao.entidade.Cidade;
import estagiocarvaobarao.entidade.Fornecedor;
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
import javafx.scene.layout.Pane;

/**
 *
 * @author Paulo
 */
public class ControllerFornecedor {

    Mensagens msg = new Mensagens();

    public void carregaTabela(TableView<Fornecedor> tabela, String filtro) {
        Fornecedor f = new Fornecedor();
        List<Fornecedor> res = f.get(filtro);
        ObservableList<Fornecedor> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }

    public ComboBox<Categoria> carregaCategorias(ComboBox<Categoria> cbCat) {
        Categoria c = new Categoria();
        List<Categoria> cat = c.get("");
        cbCat.setItems(FXCollections.observableArrayList(cat));
        return cbCat;
    }

    public void estadoInicial(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar,
            JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txfantasia,
            JFXTextField txlogradouro, JFXTextField txnum, JFXTextField txbairro, JFXTextField txcnpj,
            JFXTextField txcid, JFXTextField txcep, JFXTextField txfonecontato, JFXTextField txnomecontato,
            JFXTextField txtelefone, JFXTextField txrazasocial, JFXTextField txemail,
            TableView<Fornecedor> tabela, Label lbErroCNPJ) {
        pnpesquisa.setDisable(false);
        pndados.setDisable(true);
        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);
        txfantasia.resetValidation();
        txlogradouro.resetValidation();
        txnum.resetValidation();
        txbairro.resetValidation();
        txcnpj.resetValidation();
        txcid.resetValidation();
        txcep.resetValidation();
        txfonecontato.resetValidation();;
        txnomecontato.resetValidation();
        txtelefone.resetValidation();
        txrazasocial.resetValidation();
        txemail.resetValidation();
        lbErroCNPJ.setText("");
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
            JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txfantasia,
            JFXTextField txlogradouro, JFXTextField txnum, JFXTextField txbairro, JFXTextField txcnpj,
            JFXTextField txcid, JFXTextField txcep, JFXTextField txfonecontato,
            JFXTextField txnomecontato, JFXTextField txtelefone, JFXTextField txrazasocial,
            JFXTextField txemail, JFXComboBox<Categoria> cbCatprod) {
        carregaCategorias(cbCatprod);
        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txfantasia.requestFocus();
        txfantasia.resetValidation();
        txlogradouro.resetValidation();
        txnum.resetValidation();
        txbairro.resetValidation();
        txbairro.resetValidation();
        txcnpj.resetValidation();
        txcid.resetValidation();
        txcep.resetValidation();
        txfonecontato.resetValidation();
        txnomecontato.resetValidation();
        txtelefone.resetValidation();
        txrazasocial.resetValidation();
        txemail.resetValidation();

    }

    public void alterar(JFXTextField txcod, JFXTextField txcodcid, JFXCheckBox chkAtivo, 
            JFXTextField txfantasia, JFXTextField txlogradouro, JFXTextField txnum, 
            JFXTextField txbairro, JFXTextField txcnpj, JFXTextField txcid, JFXTextField txcep, 
            JFXTextField txfonecontato, JFXTextField txnomecontato, JFXTextField txtelefone, 
            JFXTextField txrazasocial, JFXTextField txemail, TableView<Fornecedor> tabela,JFXComboBox<Categoria> cbCatprod) {
        Fornecedor f = (Fornecedor) tabela.getSelectionModel().getSelectedItem();
        f = f.get(f.getCodigo());
        txcod.setText("" + f.getCodigo());
        txfantasia.setText("" + f.getNomefantasia());
        txcnpj.setText("" + f.getCnpj());
        txrazasocial.setText("" + f.getRazaosocial());
        txtelefone.setText("" + f.getTelefone());
        if (f.isAtivo().toLowerCase().equals("ativo")) {
            chkAtivo.setText("Ativo");
        } else {
            chkAtivo.setText("Não Ativo");
        }

        txlogradouro.setText("" + f.getLogradouro());
        txbairro.setText("" + f.getBairro());
        txnum.setText("" + f.getNumero());
        Cidade c = new Cidade();
        c = c.get(f.getCidade().getCid_cod());
        txcodcid.setText(String.valueOf(c.getCid_cod()));
        txcid.setText(c.getCid_nome());
        txcep.setText("" + f.getCep());
        txnomecontato.setText("" + f.getNomecontato());
        txfonecontato.setText("" + f.getTelefonecontato());
        txemail.setText("" + f.getEmail());
        cbCatprod.getSelectionModel().select(0);
        cbCatprod.getSelectionModel().select(f.getCategoria());
    }

    public void apagar(TableView<Fornecedor> tabela) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            Fornecedor f = new Fornecedor();
            f = tabela.getSelectionModel().getSelectedItem();
            f.apagar(f.getCodigo());
            carregaTabela(tabela, "");
        }
    }

    public void cancelar(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txfantasia, JFXTextField txlogradouro, JFXTextField txnum, JFXTextField txbairro, JFXTextField txcnpj, JFXTextField txcid, JFXTextField txcep, JFXTextField txfonecontato, JFXTextField txnomecontato, JFXTextField txtelefone, JFXTextField txrazasocial, JFXTextField txemail, TableView<Fornecedor> tabela, Label lbErroCNPJ) {
        if (!pndados.isDisabled())//encontra em estado de edição
        {
            estadoInicial(pnpesquisa,
                    pndados,
                    btconfirmar,
                    btcancelar,
                    btapagar,
                    btalterar,
                    btnovo,
                    txfantasia,
                    txlogradouro,
                    txnum,
                    txbairro,
                    txcnpj,
                    txcid,
                    txcep,
                    txfonecontato,
                    txnomecontato,
                    txtelefone,
                    txrazasocial,
                    txemail, tabela, lbErroCNPJ);
        } else {
            btnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    public void Pesquisar(JFXTextField txpesquisar, JFXRadioButton rbfantasia, JFXRadioButton rbcnpj, TableView<Fornecedor> tabela) {
        if (!txpesquisar.getText().isEmpty()) {
            if (rbfantasia.isSelected()) {
                carregaTabela(tabela, "upper(nomefantasia) like '%" + txpesquisar.getText().toUpperCase() + "%'");
            } else {
                if (rbcnpj.isSelected()) {
                    carregaTabela(tabela, "upper(cnpj) like '%" + txpesquisar.getText().toUpperCase() + "%'");
                }
            }
        } else {
            carregaTabela(tabela, "upper(nomefantasia) like '%" + txpesquisar.getText().toUpperCase() + "%'");
        }
    }

    public void evtTabela(TableView<Fornecedor> tabela, JFXButton btalterar, JFXButton btapagar) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    public void pesquisarCidade(int cid_cod, JFXTextField txcodcid, JFXTextField txcid) {
        Cidade cidade = new Cidade();
        cidade = cidade.get(cid_cod);
        if (cidade != null) {
            txcodcid.setText(String.valueOf(cidade.getCid_cod()));
            txcid.setText(cidade.getCid_nome());
        } else {
            txcodcid.setText("0");
            txcid.setText("Valor digitado não encontrado...");
        }
    }

    public void confirmar(int cod, Pane pnpesquisa, Pane pndados, JFXButton btconfirmar,
            JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar,
            JFXButton btnovo, JFXTextField txfantasia,
            JFXTextField txlogradouro, JFXTextField txnum,
            JFXTextField txbairro, JFXTextField txcnpj, JFXTextField txcodcid,
            JFXTextField txcid, JFXTextField txcep, JFXTextField txfonecontato,
            JFXTextField txnomecontato, JFXTextField txtelefone,
            JFXTextField txrazasocial, JFXTextField txemail, JFXCheckBox chkAtivo,
            TableView<Fornecedor> tabela, Label lbErroCNPJ, int cod_cat) {
        String ativo = "";
        if (chkAtivo.isSelected()) {
            ativo = "ativo";

        } else {
            ativo = "não ativo";
        }
        Fornecedor f = new Fornecedor(cod, txfantasia.getText(), txcnpj.getText(), ativo,
                txlogradouro.getText(), txbairro.getText(), txnum.getText(),
                new Cidade(Integer.parseInt(txcodcid.getText())), txcep.getText(), txnomecontato.getText(),
                txfonecontato.getText(), txemail.getText(), txtelefone.getText(), txrazasocial.getText(), new Categoria(cod_cat));
        if (f.getCodigo() == 0) {
            if (f.salvar(f)) {
                msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
                estadoInicial(pnpesquisa,
                        pndados,
                        btconfirmar,
                        btcancelar,
                        btapagar,
                        btalterar,
                        btnovo,
                        txfantasia,
                        txlogradouro,
                        txnum,
                        txbairro,
                        txcnpj,
                        txcid,
                        txcep,
                        txfonecontato,
                        txnomecontato,
                        txtelefone,
                        txrazasocial,
                        txemail, tabela, lbErroCNPJ);
            } else {
                msg.Error("Erro ao gravar!", "Problemas ao Gravar");
            }
        } else if (f.alterar(f)) {
            msg.Confirmation("Gravação concluida", "Alterado com Sucesso");
            estadoInicial(pnpesquisa,
                    pndados,
                    btconfirmar,
                    btcancelar,
                    btapagar,
                    btalterar,
                    btnovo,
                    txfantasia,
                    txlogradouro,
                    txnum,
                    txbairro,
                    txcnpj,
                    txcid,
                    txcep,
                    txfonecontato,
                    txnomecontato,
                    txtelefone,
                    txrazasocial,
                    txemail, tabela, lbErroCNPJ);
        } else {
            msg.Error("Erro ao alterar!", "Problemas ao Alterar");
        }

    }
}

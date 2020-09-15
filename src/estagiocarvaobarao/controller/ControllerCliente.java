/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.dal.DALCliente;
import estagiocarvaobarao.entidade.Cidade;
import estagiocarvaobarao.entidade.Cliente;
import estagiocarvaobarao.utils.Mensagens;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

/**
 *
 * @author Paulo
 */
public class ControllerCliente {

    Mensagens msg = new Mensagens();

    public ObservableList<Cliente> carregaTabela(TableView<Cliente> tabela, String filtro) {
        DALCliente dal = new DALCliente();
        List<Cliente> res = dal.get(filtro);
        ObservableList<Cliente> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
        return modelo;
    }

    public void estadoInicial(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, JFXTextField txcpf, JFXTextField txendereco, JFXTextField txnum, JFXTextField txbairro, JFXTextField txtelefone, JFXTextField txcid, JFXTextField txcep, JFXTextField txemail, JFXTextField txlimite, TableView<Cliente> tabela, Label lbErroData, DatePicker dtdata, Label lbErroCPF) {
        pnpesquisa.setDisable(false);
        pndados.setDisable(true);
        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);
        txnome.resetValidation();
        txcpf.resetValidation();
        txendereco.resetValidation();
        txnum.resetValidation();
        txbairro.resetValidation();
        txtelefone.resetValidation();
        txcid.resetValidation();
        txcep.resetValidation();
        txemail.resetValidation();
        txlimite.resetValidation();
        lbErroData.setText("");
        lbErroCPF.setText("");
        dtdata.setValue(null);
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

    public void estadoEdicao(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, JFXTextField txcpf, JFXTextField txendereco, JFXTextField txnum, JFXTextField txbairro, JFXTextField txtelefone, JFXTextField txcid, JFXTextField txcep, JFXTextField txemail, JFXTextField txlimite,DatePicker dtdata) {
        Mensagens msg = new Mensagens();
        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txnome.requestFocus();
        txnome.resetValidation();
        txcpf.resetValidation();
        txendereco.resetValidation();
        txnum.resetValidation();
        txbairro.resetValidation();
        txtelefone.resetValidation();
        txcid.resetValidation();
        txcep.resetValidation();
        txemail.resetValidation();
        txlimite.resetValidation();  
        dtdata.setValue(LocalDate.now());
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

    public void alterar(TableView<Cliente> tabela, JFXTextField txcod, JFXTextField txnome, JFXTextField txcpf, JFXTextField txendereco, JFXTextField txnum, JFXTextField txbairro, JFXTextField txtelefone, JFXCheckBox chkAtivo, JFXTextField txcodcid, JFXTextField txcid, JFXTextField txcep, JFXTextField txemail, JFXTextField txlimite, DatePicker dtdata) {
        Cliente c = (Cliente) tabela.getSelectionModel().getSelectedItem();
        Cidade cidade = new Cidade();

        c = c.get(c.getCodigo());
        cidade = cidade.get(c.getCidade().getCid_cod());
        txcod.setText("" + c.getCodigo());
        txnome.setText("" + c.getNome());
        txcpf.setText("" + c.getCpf());
        txendereco.setText("" + c.getEndereco());
        txtelefone.setText("" + c.getTelefone());
        chkAtivo.setText("" + c.getAtivo());
        txbairro.setText("" + c.getBairro());
        txnum.setText("" + c.getNumero());
        txcodcid.setText(String.valueOf(c.getCidade().getCid_cod()));
        txcid.setText("" + cidade.getCid_nome());
        txcep.setText("" + c.getCep());
        txemail.setText("" + c.getEmail());

        if (decimal(String.valueOf(c.getLimite_fiado())) && String.valueOf(c.getLimite_fiado()).length() >= 3 && String.valueOf(c.getLimite_fiado()).length() <= 5 || c.getLimite_fiado() >= 100.0) {
            txlimite.setText(String.valueOf(c.getLimite_fiado()) + "0");
        } else {
            txlimite.setText(String.valueOf(c.getLimite_fiado()));
        }

        int ano = c.getData().getYear() + 1900;
        int mes = c.getData().getMonth() + 1;
        int dia = c.getData().getDate();
        dtdata.setValue(LocalDate.of(ano, mes, dia));
        dtdata.setShowWeekNumbers(true);

        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter
                    = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }//To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                } //To change body of generated methods, choose Tools | Templates.
            }
        };
        dtdata.setConverter(converter);

    }

    public void apagar(TableView<Cliente> tabela) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        ControllerCliente cli = new ControllerCliente();
        if (a.showAndWait().get() == ButtonType.OK) {
            Cliente c;
            c = tabela.getSelectionModel().getSelectedItem();
            c.apagar(c.getCodigo());
            carregaTabela(tabela, "");
        }
    }

    public void cancelar(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, JFXTextField txcpf, JFXTextField txendereco, JFXTextField txnum, JFXTextField txbairro, JFXTextField txtelefone, JFXTextField txcid, JFXTextField txcep, JFXTextField txemail, JFXTextField txlimite, TableView<Cliente> tabela, Label lbErroData, DatePicker dtdata, Label lbErroCPF) {
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
                    txcpf,
                    txendereco,
                    txnum,
                    txbairro,
                    txtelefone,
                    txcid,
                    txcep,
                    txemail,
                    txlimite, tabela, lbErroData, dtdata, lbErroCPF);
        } else {
            btnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    public void Pesquisar(JFXTextField txpesquisar, JFXRadioButton rbnome, JFXRadioButton rbcpf, TableView<Cliente> tabela) {
        if (!txpesquisar.getText().isEmpty()) {
            if (rbnome.isSelected()) {
                carregaTabela(tabela, "upper(nome) like '%" + txpesquisar.getText().toUpperCase() + "%'");
            } else {
                if (rbcpf.isSelected()) {
                    carregaTabela(tabela, "upper(cpf) like '%" + txpesquisar.getText().toUpperCase() + "%'");
                }
            }
        } else {
            carregaTabela(tabela, "upper(nome) like '%" + txpesquisar.getText().toUpperCase() + "%'");
        }
    }

    public void evtTabela(TableView<Cliente> tabela, JFXButton btalterar, JFXButton btapagar) {
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

    public void confirmar(int cod, Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, JFXTextField txcpf, JFXTextField txendereco, JFXTextField txnum, JFXTextField txbairro, JFXTextField txtelefone, JFXTextField txcodcid, JFXTextField txcid, JFXTextField txcep, JFXTextField txemail, JFXTextField txlimite, TableView<Cliente> tabela, String ativo, DatePicker dtdata, Label lbErroData, Label lbErroCPF) {
        Cidade cidade = new Cidade();
        cidade = cidade.get(Integer.parseInt(txcodcid.getText()));
        NumberFormat nf = new DecimalFormat("#,###.00");
        Cliente c;
        try {
            c = new Cliente(cod, txnome.getText(), txcpf.getText(), txendereco.getText(),
                    txnum.getText(), txcep.getText(), cidade, txbairro.getText(), java.sql.Date.valueOf(dtdata.getValue()), ativo,
                    txemail.getText(), txtelefone.getText(), 0.00, nf.parse(txlimite.getText()).doubleValue());
            if (c.getCodigo() == 0) {
                if (c.salvar(c)) {
                    msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
                     estadoInicial(pnpesquisa,
                    pndados,
                    btconfirmar,
                    btcancelar,
                    btapagar,
                    btalterar,
                    btnovo,
                    txnome,
                    txcpf,
                    txendereco,
                    txnum,
                    txbairro,
                    txtelefone,
                    txcid,
                    txcep,
                    txemail,
                    txlimite, tabela, lbErroData, dtdata, lbErroCPF);
                } else {
                    msg.Error("Erro ao gravar!", "Problemas ao Gravar");
                }
            } else if (c.alterar(c)) {
                msg.Confirmation("Gravação concluida", "Alterado com Sucesso");
                 estadoInicial(pnpesquisa,
                    pndados,
                    btconfirmar,
                    btcancelar,
                    btapagar,
                    btalterar,
                    btnovo,
                    txnome,
                    txcpf,
                    txendereco,
                    txnum,
                    txbairro,
                    txtelefone,
                    txcid,
                    txcep,
                    txemail,
                    txlimite, tabela, lbErroData, dtdata, lbErroCPF);
            } else {
                msg.Error("Erro ao alterar!", "Problemas ao Alterar");
            }
           

        } catch (ParseException ex) {
            Logger.getLogger(ControllerCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

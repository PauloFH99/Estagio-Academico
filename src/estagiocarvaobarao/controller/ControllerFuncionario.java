/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.dal.DALFuncionario;
import estagiocarvaobarao.dal.DALNivelFuncionario;
import estagiocarvaobarao.entidade.Cidade;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.NivelFuncionario;
import static estagiocarvaobarao.ui.TelaFuncionarioCadController.primeiro_acesso;
import estagiocarvaobarao.utils.Mensagens;
import java.sql.ResultSet;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Paulo
 */
public class ControllerFuncionario {

    Mensagens msg = new Mensagens();

    public void carregaTabela(TableView<Funcionario> tabela, String filtro) {
        DALFuncionario dal = new DALFuncionario();
        List<Funcionario> res = dal.get(filtro);
        ObservableList<Funcionario> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);

    }

    public void carregarNivel(JFXComboBox<NivelFuncionario> cbnivel) {
        DALNivelFuncionario dal = new DALNivelFuncionario();
        List<NivelFuncionario> nf = dal.get();
        cbnivel.setItems(FXCollections.observableArrayList(nf));
        cbnivel.getSelectionModel().select(0);
    }

    public void estadoInicial(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, JFXTextField txcpf, JFXTextField txendereco, JFXTextField txnum, JFXTextField txbairro, JFXTextField txtelefone, JFXTextField txcid, JFXTextField txcep, JFXTextField txemail, JFXComboBox<NivelFuncionario> cbnivel, JFXTextField txlogin, JFXPasswordField txsenha, TableView<Funcionario> tabela, Label lbErroCPF) {
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
        cbnivel.resetValidation();
        txlogin.resetValidation();
        txsenha.resetValidation();
        lbErroCPF.setText("");
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

        carregarNivel(cbnivel);
        carregaTabela(tabela, "");
    }

    public void estadoEdicao(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, JFXTextField txcpf, JFXTextField txendereco, JFXTextField txnum, JFXTextField txbairro, JFXTextField txtelefone, JFXTextField txcid, JFXTextField txcep, JFXTextField txemail, JFXComboBox<NivelFuncionario> cbnivel, JFXTextField txlogin, JFXPasswordField txsenha, TableView<Funcionario> tabela, boolean primeiro_acesso, JFXCheckBox chkAtivo) {
        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txnome.requestFocus();
        if (primeiro_acesso) {
            cbnivel.setDisable(true);
            chkAtivo.setDisable(true);
            primeiro_acesso = false;
        } else {
            cbnivel.setDisable(false);
            chkAtivo.setDisable(false);
        }
txnome.resetValidation();
txcpf.resetValidation();
txendereco.resetValidation();
txnum.resetValidation();
txbairro.resetValidation();
txtelefone.resetValidation();
txcid.resetValidation();
txcep.resetValidation();
txemail.resetValidation();
cbnivel.resetValidation();
txlogin.resetValidation();
txsenha.resetValidation();
//        msg.campoVazio(txnome, "");
//        msg.campoVazio(txcpf, "");
//        msg.campoVazio(txendereco, "");
//        msg.campoVazio(txnum, "");
//        msg.campoVazio(txbairro, "");
//        msg.campoVazio(txtelefone, "");
//        msg.campoVazio(txcid, "");
//        msg.campoVazio(txcep, "");
//        msg.campoVazio(txemail, "");
//        msg.campoVazioCbx(cbnivel, "");
//        msg.campoVazio(txlogin, "");
//        msg.campoVazio(txsenha, "");
    }

    public void alterar(JFXTextField txcod, JFXTextField txnome, JFXTextField txcpf, JFXTextField txendereco, JFXTextField txnum, JFXTextField txbairro, JFXTextField txtelefone, JFXCheckBox chkAtivo, JFXTextField txcodcid, JFXTextField txcid, JFXTextField txcep, JFXTextField txemail, JFXComboBox<NivelFuncionario> cbnivel, JFXTextField txlogin, JFXPasswordField txsenha, JFXComboBox<NivelFuncionario> cbnivel0, TableView<Funcionario> tabela) {
        Funcionario f = (Funcionario) tabela.getSelectionModel().getSelectedItem();
        Cidade cidade = new Cidade();
        NivelFuncionario nf = new NivelFuncionario();
        nf = nf.get(f.getNivel().getCodigo());
        f = f.get(f.getCodigo());
        cidade = cidade.get(f.getCidade().getCid_cod());
        txcod.setText("" + f.getCodigo());
        txnome.setText("" + f.getNome());
        txcpf.setText("" + f.getCpf());
        txendereco.setText("" + f.getEndereco());
        txtelefone.setText("" + f.getTelefone());
        if (f.getAtivo().toLowerCase().equals("ativo")) {
            chkAtivo.setSelected(true);
            chkAtivo.setText("" + f.getAtivo());
        } else {
            chkAtivo.setSelected(false);
            chkAtivo.setText("" + f.getAtivo());
        }
        txbairro.setText("" + f.getBairro());
        txnum.setText("" + f.getNumero());
        txcodcid.setText(String.valueOf(f.getCidade().getCid_cod()));
        txcid.setText("" + cidade.getCid_nome());
        txcep.setText("" + f.getCep());
        txsenha.setText("" + f.getSenha());
        txlogin.setText("" + f.getLogin());
        txemail.setText("" + f.getEmail());
        cbnivel.getSelectionModel().select(0);
        cbnivel.getSelectionModel().select(nf);
    }

    public void apagar(TableView<Funcionario> tabela) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        Funcionario f = new Funcionario();
        f = tabela.getSelectionModel().getSelectedItem();
        f = f.get(f.getCodigo());
        Banco.conectar();
        ResultSet rs = Banco.getCon().consultar("select  * from funcionario");
        int rows = 0;
        try {
            while (rs.next()) {
                rows = rs.getRow();
            }
            if (rows == 1) {
                f.setNivel(new NivelFuncionario(1));
                f.setAtivo("ativo");
                f.alterar(f);
                msg.Affirmation("", "O sistema precisa ter pelo menos um usuário");
                carregaTabela(tabela, "");
            } else {
                a.setContentText("Confirma a exclusão");
                if (a.showAndWait().get() == ButtonType.OK) {

                    f.apagar(f.getCodigo());
                    carregaTabela(tabela, "");
                }
            }

        } catch (Exception e) {
        }

    }

    public void cancelar(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, JFXTextField txnome, JFXTextField txcpf, JFXTextField txendereco, JFXTextField txnum, JFXTextField txbairro, JFXTextField txtelefone, JFXTextField txcid, JFXTextField txcep, JFXTextField txemail, JFXComboBox<NivelFuncionario> cbnivel, JFXTextField txlogin, JFXPasswordField txsenha, TableView<Funcionario> tabela, Label lbErroCPF) {
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
                    cbnivel,
                    txlogin,
                    txsenha,
                    tabela, lbErroCPF);
        } else {
            btnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    public void Pesquisar(JFXTextField txpesquisar, JFXRadioButton rbnome, JFXRadioButton rbcpf, TableView<Funcionario> tabela) {
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

    public void evtTabela(TableView<Funcionario> tabela, JFXButton btalterar, JFXButton btapagar) {
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

    public void confirmar(Pane pnpesquisa, Pane pndados, JFXButton btconfirmar, JFXButton btcancelar, JFXButton btapagar, JFXButton btalterar, JFXButton btnovo, int cod, JFXTextField txcodcid, JFXTextField txnome, JFXTextField txcpf, JFXTextField txendereco, JFXTextField txnum, JFXTextField txbairro, JFXTextField txtelefone, JFXTextField txcid, JFXTextField txcep, JFXTextField txemail, JFXComboBox<NivelFuncionario> cbnivel, JFXTextField txlogin, JFXPasswordField txsenha, String ativo, String pAcesso, TableView<Funcionario> tabela, Label lbErroCPF) {
        Banco.conectar();
        ResultSet rs = Banco.getCon().consultar("select  * from funcionario");
        int rows = 0, cont = 0;
        try {
            while (rs.next()) {
                rows = rs.getRow();
            }
        } catch (Exception e) {
        }
        NivelFuncionario nivel;
        Cidade cidade;
        cidade = new Cidade(Integer.parseInt(txcodcid.getText()));
        nivel = cbnivel.valueProperty().getValue();
        Funcionario f = new Funcionario(cod, txnome.getText(), txcpf.getText(), txendereco.getText(),
                txnum.getText(), txtelefone.getText(), txemail.getText(), txlogin.getText(),
                txsenha.getText(), ativo, pAcesso, cidade, nivel, txbairro.getText(), txcep.getText());
        NivelFuncionario n = new NivelFuncionario();
        List<Funcionario> aux = new ArrayList();

        if (f.getCodigo() == 0) {
            if (f.salvar(f)) {
                msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
            } else {
                msg.Error("Erro ao gravar!", "Problemas ao Gravar");
            }
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
                    cbnivel,
                    txlogin,
                    txsenha,
                    tabela, lbErroCPF);
        } else {
            aux = f.getAux(f.getCodigo());
            if (aux.size() == 0) {
                if (f.getNivel().getDescricao().equals("Administrador") || f.getNivel().getDescricao().equals("administrador")) {
                    cont = 1;
                }
            } else {
                for (Funcionario funcionario : aux) {
                    n = n.get(funcionario.getNivel().getCodigo());
                    if (n.getDescricao().equals("Administrador") || n.getDescricao().equals("administrador")) {
                        cont = 1;
                    }
                }
            }
            if (cont >= 1) {
                if (f.alterar(f)) {
                    msg.Confirmation("Gravação concluida", "Alterado com Sucesso");
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
                        txnome,
                        txcpf,
                        txendereco,
                        txnum,
                        txbairro,
                        txtelefone,
                        txcid,
                        txcep,
                        txemail,
                        cbnivel,
                        txlogin,
                        txsenha,
                        tabela, lbErroCPF);
            } else {
                msg.Error("Erro ao alterar!", "É necessario ter pelo menos um administrador, nivel de acesso foi alterado para administrador!");
                n = n.getA("Administrador");
                f.setNivel(n);
                if (f.alterar(f)) {
                    msg.Confirmation("Gravação concluida", "Alterado com Sucesso");
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
                        txnome,
                        txcpf,
                        txendereco,
                        txnum,
                        txbairro,
                        txtelefone,
                        txcid,
                        txcep,
                        txemail,
                        cbnivel,
                        txlogin,
                        txsenha,
                        tabela, lbErroCPF);

            }
        }
    }

    public void validaLogin(JFXTextField txlogin, int cod, KeyEvent event) {
        Funcionario novo = new Funcionario();
        if (event.getCode() == KeyCode.TAB && !txlogin.getText().isEmpty() && cod == 0) {
            if (novo.getloginF(txlogin.getText()) != null) {
                msg.Error("Erro!", "Login já existente cadastre outro!");
            }
        }
    }

    public boolean validaLogin(JFXTextField txlogin, int cod) {
        Funcionario novo = null;
        if (!txlogin.getText().isEmpty() && cod == 0) {
            novo = new Funcionario();
            if (novo.getloginF(txlogin.getText()) != null) {
                msg.Error("Erro!", "Login já existente cadastre outro!");
                return true;
            }

        }
        return false;
    }

}

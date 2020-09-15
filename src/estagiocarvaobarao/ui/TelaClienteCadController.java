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
import estagiocarvaobarao.controller.ControllerCliente;
import estagiocarvaobarao.dal.DALCidade;
import estagiocarvaobarao.dal.DALCliente;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Cidade;
import estagiocarvaobarao.entidade.Cliente;
import estagiocarvaobarao.utils.MaskFieldUtil;
import estagiocarvaobarao.utils.Mensagens;
import estagiocarvaobarao.utils.ValidarCPF;
import java.io.IOException;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
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
public class TelaClienteCadController implements Initializable {

    public static Cidade cid;
    public static boolean primeiro_acesso;

    public static Cidade getCid() {
        return cid;
    }

    public static void setCid(Cidade cid) {
        TelaClienteCadController.cid = cid;
    }

    public static boolean isPrimeiro_acesso() {
        return primeiro_acesso;
    }

    public static void setPrimeiro_acesso(boolean primeiro_acesso) {
        TelaClienteCadController.primeiro_acesso = primeiro_acesso;
    }
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
    private JFXRadioButton rbnome;
    @FXML
    private ToggleGroup Pesquisa1;
    @FXML
    private JFXRadioButton rbcpf;
    @FXML
    private JFXTextField txpesquisar;
    @FXML
    private JFXButton btpesquisar;
    @FXML
    private TableView<Cliente> tabela;
    @FXML
    private TableColumn<Cliente, Integer> colcod;
    @FXML
    private TableColumn<Cliente, String> colnome;
    @FXML
    private TableColumn<Cliente, String> colcpf;
    @FXML
    private TableColumn<Cliente, String> coltelefone;
    @FXML
    private TableColumn<Cliente, String> colativo;
    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;
    @FXML
    private JFXCheckBox chkAtivo;
    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXTextField txcpf;
    @FXML
    private JFXTextField txendereco;
    @FXML
    private JFXTextField txnum;
    @FXML
    private JFXTextField txbairro;
    @FXML
    private JFXTextField txtelefone;
    @FXML
    private JFXTextField txcodcid;
    @FXML
    private JFXButton btnpesquisarcid;
    @FXML
    private JFXTextField txcid;
    @FXML
    private JFXTextField txcep;
    @FXML
    private JFXTextField txemail;
    @FXML
    private JFXTextField txlimite;
    @FXML
    private DatePicker dtdata;
    ControllerCliente clicontro = new ControllerCliente();
    @FXML
    private Label lbErroData;
    @FXML
    private Label lbErroCPF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        MaskFieldUtil.cepField(txcep);
        MaskFieldUtil.cpfCnpjField(txcpf);
        MaskFieldUtil.foneField(txtelefone);
        MaskFieldUtil.numericField(txcodcid);
        MaskFieldUtil.monetaryField(txlimite);
        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colnome.setCellValueFactory(new PropertyValueFactory("nome"));
        colcpf.setCellValueFactory(new PropertyValueFactory("cpf"));
        coltelefone.setCellValueFactory(new PropertyValueFactory("telefone"));
        colativo.setCellValueFactory(new PropertyValueFactory("ativo"));
        dtdata.setValue(LocalDate.now());
        clicontro.estadoInicial(pnpesquisa,
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
    }

    private void estadoInicial() {
        clicontro.estadoInicial(pnpesquisa,
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
    }

    private void carregaTabela(String filtro) {

        clicontro.carregaTabela(tabela, filtro);
    }

    private void estadoEdicao() {
        clicontro.estadoEdicao(pnpesquisa,
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
                txlimite,dtdata);

    }

    @FXML
    private void clknovo(ActionEvent event) {
        clicontro.estadoEdicao(pnpesquisa,
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
                txlimite,dtdata);
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        clicontro.estadoEdicao(pnpesquisa,
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
                txlimite,dtdata);

        clicontro.alterar(tabela, txcod, txnome,
                txcpf,
                txendereco,
                txnum,
                txbairro,
                txtelefone,
                chkAtivo,
                txcodcid,
                txcid,
                txcep,
                txemail,
                txlimite, dtdata);

    }

    @FXML
    private void clkapagar(ActionEvent event) {
        clicontro.apagar(tabela);

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
        String ativo, pAcesso;
        ValidarCPF valida = new ValidarCPF();
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
        if (txcep.getText().isEmpty()) {
            validar(txcep, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txcpf.getText().isEmpty()) {
            validar(txcpf, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (!txcpf.getText().isEmpty()) {
            if (!valida.isValid(txcpf.getText())) {
                lbErroCPF.setVisible(true);
                lbErroCPF.setText("CPF/CNPJ inválido! Digite o CPF/CNPJ novamente");
                txcpf.requestFocus();
                erro = 1;
            }
        }
        if (txendereco.getText().isEmpty()) {
            validar(txendereco, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txnum.getText().isEmpty()) {
            validar(txnum, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txbairro.getText().isEmpty()) {
            validar(txbairro, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txtelefone.getText().isEmpty()) {
            validar(txtelefone, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txcid.getText().isEmpty()) {
            validar(txcid, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txcep.getText().isEmpty()) {
            validar(txcep, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txemail.getText().isEmpty()) {
            validar(txemail, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txlimite.getText().isEmpty()) {
            validar(txlimite, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (dtdata.getValue() != null && dtdata.getValue().isAfter(LocalDate.now())) {
            lbErroData.setText("A data  não pode ser maior que a data atual!");
            erro = 1;
        }
        if (dtdata.getValue() == null) {
            lbErroData.setText("Campo não pode estar vazio!");
            erro = 1;
        }
        if (chkAtivo.isSelected()) {
            ativo = "Ativo";
        } else {
            ativo = "Não Ativo";
        }

        if (cod == 0) {
            pAcesso = "Sim";
        } else {
            pAcesso = "Não";
        }

        if (erro == 0) {
            clicontro.confirmar(cod, pnpesquisa,
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
                    txcodcid,
                    txcid,
                    txcep,
                    txemail,
                    txlimite, tabela, ativo, dtdata, lbErroData, lbErroCPF);

        }
    }

    @FXML
    private void clkcancelar(ActionEvent event) {
        clicontro.cancelar(pnpesquisa,
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

    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        clicontro.Pesquisar(txpesquisar, rbnome, rbcpf, tabela);

    }

    @FXML
    private void evtTabela(MouseEvent event) {
        clicontro.evtTabela(tabela, btalterar, btapagar);
    }

    public void pesquisarCidade(int cid_cod) {
        clicontro.pesquisarCidade(cid_cod, txcodcid, txcid);
    }

    @FXML
    private void evtProcurarCidade(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            Stage stage = new Stage();
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/Consulta.fxml"));
            Scene scene = new Scene(pesquisa);
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.setTitle("Consulta de cidades");
            stage.showAndWait();
            if (cid != null) {
                pesquisarCidade(cid.getCid_cod());
                txcep.requestFocus();
            }

        } catch (Exception e) {
            msg.Error("", e.getMessage());
        }
    }

    @FXML
    private void onExitCidade(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodcid.getText().isEmpty()) {
            pesquisarCidade(Integer.parseInt(txcodcid.getText()));//mandar pra controller
        }
    }

    @FXML
    private void evRbNome(ActionEvent event) {
//        if (rbcpf.isSelected()) {
//            rbcpf.setSelected(false);
//        } else {
//            rbnome.setSelected(true);
//        }
//        MaskFieldUtil.onlyDigitsValue(txpesquisar);
    }

    @FXML
    private void evRbCpf(ActionEvent event) {
//        if (rbnome.isSelected()) {
//            rbnome.setSelected(false);
//        } else {
//            rbcpf.setSelected(true);
//        }
//        MaskFieldUtil.cpfField(txpesquisar);
    }

    @FXML
    private void validaCPf(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcpf.getText().isEmpty()) {
            ValidarCPF valida = new ValidarCPF();
            if (!valida.isValid(txcpf.getText())) {
                lbErroCPF.setVisible(true);
                lbErroCPF.setText("CPF inválido! Digite o CPF novamente");
                txcpf.requestFocus();

            } else {
                lbErroCPF.setVisible(false);
            }
        }
    }

}

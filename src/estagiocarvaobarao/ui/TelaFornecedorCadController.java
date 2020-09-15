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
import estagiocarvaobarao.controller.ControllerFornecedor;
import estagiocarvaobarao.entidade.Categoria;
import estagiocarvaobarao.entidade.Cidade;
import estagiocarvaobarao.entidade.Fornecedor;
import estagiocarvaobarao.utils.MaskFieldUtil;
import estagiocarvaobarao.utils.Mensagens;
import estagiocarvaobarao.utils.ValidarCPF;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
public class TelaFornecedorCadController implements Initializable {

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
    private TableView<Fornecedor> tabela;
    @FXML
    private TableColumn<Fornecedor, Integer> colcod;
    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;
    @FXML
    private JFXCheckBox chkAtivo;
    @FXML
    private TableColumn<Fornecedor, String> colnome;
    @FXML
    private TableColumn<Fornecedor, String> colcid;
    @FXML
    private TableColumn<Fornecedor, String> colfone;
    @FXML
    private TableColumn<Fornecedor, String> colativo;
    @FXML
    private JFXTextField txfantasia;
    @FXML
    private JFXTextField txcnpj;
    @FXML
    private JFXTextField txrazasocial;
    @FXML
    private JFXTextField txtelefone;
    @FXML
    private JFXTextField txlogradouro;
    @FXML
    private JFXTextField txbairro;
    @FXML
    private JFXTextField txnum;
    @FXML
    private JFXTextField txcodcid;
    @FXML
    private JFXButton btnpesquisarcid;
    @FXML
    private JFXTextField txcid;
    @FXML
    private JFXTextField txcep;
    @FXML
    private JFXTextField txnomecontato;
    @FXML
    private JFXTextField txfonecontato;
    @FXML
    private JFXTextField txemail;
    @FXML
    private ToggleGroup Pesquisa;
    @FXML
    private JFXRadioButton rbfantasia;
    @FXML
    private JFXRadioButton rbcnpj;
    ControllerFornecedor cf = new ControllerFornecedor();
    @FXML
    private Label lbErroCNPJ;
    @FXML
    private JFXComboBox<Categoria> cbCatprod;

    /**
     * Initializes the controller class.
     */
    public static Cidade cid;

    public static Cidade getCid() {
        return cid;
    }

    public static void setCid(Cidade cid) {
        TelaFornecedorCadController.cid = cid;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MaskFieldUtil.cpfCnpjField(txcnpj);
        MaskFieldUtil.foneField(txtelefone);
        MaskFieldUtil.cepField(txcep);
        MaskFieldUtil.foneField(txfonecontato);

        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colnome.setCellValueFactory(new PropertyValueFactory("nomefantasia"));
        colcid.setCellValueFactory(new PropertyValueFactory("cidade"));
        colfone.setCellValueFactory(new PropertyValueFactory("telefone"));
        colativo.setCellValueFactory(new PropertyValueFactory("ativo"));
        cf.estadoInicial(pnpesquisa,
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
    }

    private void estadoInicial() {
        cf.estadoInicial(pnpesquisa,
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
    }

    private void estadoEdicao() {
        cf.estadoEdicao(pnpesquisa,
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
                txemail, cbCatprod);

    }

    @FXML
    private void clknovo(ActionEvent event) {
        cf.estadoEdicao(pnpesquisa,
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
                txemail, cbCatprod);
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        cf.estadoEdicao(pnpesquisa,
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
                txemail, cbCatprod);
        cf.alterar(txcod,
                txcodcid,
                chkAtivo,
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
                txemail, tabela,cbCatprod);

    }

    @FXML
    private void clkapagar(ActionEvent event) {
        cf.apagar(tabela);

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

    public void validarCb(JFXComboBox campo) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        campo.resetValidation();
        campo.getValidators().add(validator);
        validator.setMessage("Campo não pode estar vazio!");
        campo.validate();
    }

    @FXML
    private void clkconfirmar(ActionEvent event) {
        int cod, erro = 0, cod_cat = 0;
        Mensagens msg = new Mensagens();
        ValidarCPF valida = new ValidarCPF();

        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        if (cbCatprod.getSelectionModel().isEmpty()) {
            validarCb(cbCatprod);
            erro = 1;
        } else {
            try {
                cod_cat = cbCatprod.getSelectionModel().getSelectedItem().getCodigo();
            } catch (Exception e) {
                cod_cat = 0;
            }
        }
        if (txfantasia.getText().isEmpty()) {
            validar(txfantasia, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txcnpj.getText().isEmpty()) {
            validar(txcnpj, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txlogradouro.getText().isEmpty()) {
            validar(txlogradouro, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txbairro.getText().isEmpty()) {
            validar(txbairro, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txnum.getText().isEmpty()) {
            validar(txnum, "Campo não pode estar vazio!");
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
        if (txcnpj.getText().isEmpty()) {
            validar(txcnpj, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (!txcnpj.getText().isEmpty()) {
            if (!valida.isValid(txcnpj.getText())) {
                lbErroCNPJ.setVisible(true);
                lbErroCNPJ.setText("CPF inválido! Digite o CPF novamente");
                txcnpj.requestFocus();
                erro = 1;
            }
        }
        if (txnomecontato.getText().isEmpty()) {
            validar(txnomecontato, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txfonecontato.getText().isEmpty()) {
            validar(txfonecontato, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txemail.getText().isEmpty()) {
            validar(txemail, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txtelefone.getText().isEmpty()) {
            validar(txtelefone, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txrazasocial.getText().isEmpty()) {
            validar(txrazasocial, "Campo não pode estar vazio!");
            erro = 1;
        }

        if (erro == 0) {
            cf.confirmar(cod, pnpesquisa,
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
                    txcodcid,
                    txcid,
                    txcep,
                    txfonecontato,
                    txnomecontato,
                    txtelefone,
                    txrazasocial,
                    txemail, chkAtivo, tabela, lbErroCNPJ,cod_cat);

        }
    }

    @FXML
    private void clkcancelar(ActionEvent event) {
        cf.cancelar(pnpesquisa,
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

    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        cf.Pesquisar(txpesquisar, rbfantasia, rbcnpj, tabela);

    }

    @FXML
    private void evtTabela(MouseEvent event) {
        cf.evtTabela(tabela, btalterar, btapagar);

    }

    public void pesquisarCidade(int cid_cod) {
        cf.pesquisarCidade(cid_cod, txcodcid, txcid);
    }

    @FXML
    private void onExitCidade(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodcid.getText().isEmpty()) {
            pesquisarCidade(Integer.parseInt(txcodcid.getText()));
        }
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

    private void exit(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void validaCNPJ(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcnpj.getText().isEmpty()) {
            ValidarCPF valida = new ValidarCPF();
            if (!valida.isValid(txcnpj.getText())) {
                lbErroCNPJ.setVisible(true);
                lbErroCNPJ.setText("CPF/CNPJ inválido! Digite o CPF/CNPJ novamente");
                txcnpj.requestFocus();

            } else {
                lbErroCNPJ.setVisible(false);
            }
        }
    }

}

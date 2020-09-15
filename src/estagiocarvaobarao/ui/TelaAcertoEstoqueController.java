/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import estagiocarvaobarao.controller.ControllerAcertoEstoque;
import estagiocarvaobarao.entidade.AcertoEstoque;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.utils.MaskFieldUtil;
import estagiocarvaobarao.utils.Mensagens;
import static java.lang.System.exit;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaAcertoEstoqueController implements Initializable {

    @FXML
    private JFXButton btnnovo;

    @FXML
    private JFXButton btnfinalizar;

    private JFXTextField txusu;
    @FXML
    private JFXTextField txcodprod;
    @FXML
    private JFXTextField txprod;

    public static AcertoEstoque acertoestoque;

    public static AcertoEstoque getAcertoestoque() {
        return acertoestoque;
    }

    public static void setAcertoestoque(AcertoEstoque acertoestoque) {
        TelaAcertoEstoqueController.acertoestoque = acertoestoque;
    }

    public static Funcionario funcionario;

    public static Funcionario getFuncionario() {
        return funcionario;
    }

    public static void setFuncionario(Funcionario funcionario) {
        TelaAcertoEstoqueController.funcionario = funcionario;
    }

    public static Produto prod;

    public static Produto getProd() {
        return prod;
    }

    public static void setProd(Produto prod) {
        TelaAcertoEstoqueController.prod = prod;
    }
    @FXML
    private Pane pnnovo;
    @FXML
    private Pane pnpesquisa;
    @FXML
    private JFXButton btnpesquisar;
    @FXML
    private Pane pnconteudo;
    @FXML
    private DatePicker dpemissao;
    @FXML
    private JFXTextField txcod;
    private JFXRadioButton rbgeral;
    @FXML
    private ToggleGroup estoque;
    @FXML
    private JFXRadioButton rbarmazem;
    @FXML
    private JFXRadioButton rbentrada;
    @FXML
    private ToggleGroup Entrada;
    @FXML
    private JFXRadioButton rbsaida;
    private JFXTextField txusunome;
    @FXML
    private JFXButton btnpesquisarprod;
    @FXML
    private JFXTextField txqtde;
    @FXML
    private JFXTextField txqtdefinal;
    @FXML
    private JFXTextField txobser;
    @FXML
    private Label lbqtde;
    @FXML
    private Pane pnbotoes;
    @FXML
    private JFXButton btncancelar;

    ControllerAcertoEstoque acercontro = new ControllerAcertoEstoque();
    @FXML
    private Label lberremissao;
    @FXML
    private Label lberroqtde;
    Mensagens msg = new Mensagens();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MaskFieldUtil.numericField(txqtde);
        acercontro.estadoInicial(pnconteudo,
                pnbotoes,
                btnfinalizar,
                btnpesquisar,
                btncancelar,
                btnnovo,
                dpemissao,
                lbqtde);
    }

    private void estadoInicial() {
        acercontro.estadoInicial(pnconteudo,
                pnbotoes,
                btnfinalizar,
                btnpesquisar,
                btncancelar,
                btnnovo,
                dpemissao,
                lbqtde);
    }

    private void estadoEdicao() {
        acercontro.estadoEdicao(pnconteudo,
                btnfinalizar,
                btncancelar,
                btnpesquisar,
                btnnovo,
                dpemissao,
                txcod,
                txcodprod,
                txqtde,
                txobser, prod);

    }

    @FXML
    private void evtNovo(ActionEvent event) {
        acercontro.estadoEdicao(pnconteudo,
                btnfinalizar,
                btncancelar,
                btnpesquisar,
                btnnovo,
                dpemissao,
                txcod,
                txcodprod,
                txqtde,
                txobser, prod);
    }

    public void pesquisarProduto(Produto prod) {
        acercontro.pesquisarProduto(prod, txcodprod, txprod, txqtde);

    }

    @FXML
    private void evtProcurarProduto(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            Stage stage = new Stage();
            Parent pesquisaP = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaProduto.fxml"));
            Scene scene = new Scene(pesquisaP);
            stage.setTitle("Consulta");
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
            if (prod != null) {
                pesquisarProduto(prod);
            }

        } catch (Exception e) {
            msg.Error("Erro ao Consultar", "Nenhum Produto foi selecionado!");
            exit(1);
        }
    }

    @FXML
    private void onExitProduto(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodprod.getText().isEmpty()) {
            acercontro.pesquisarProduto(prod, txcodprod, txprod, txqtde);
        }
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        acercontro.cancelar(pnconteudo,
                pnbotoes,
                btnfinalizar,
                btnpesquisar,
                btncancelar,
                btnnovo,
                dpemissao,
                lbqtde);

    }

    private void exit(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void validar(JFXTextField campo, String texto) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        campo.resetValidation();
        campo.getValidators().add(validator);
        validator.setMessage(texto);
        campo.validate();
    }

    private int retornaValor(String valor) {
        int res = 0;
        if (valor != null && !valor.equals("")) {
            res = Integer.parseInt(valor);
        }
        return res;
    }

    private void limparLabel() {
        lberremissao.setText("");
        lberroqtde.setText("");
        txobser.resetValidation();
        txqtde.resetValidation();
        txprod.resetValidation();
    }

    @FXML
    private void evtFinalizar(ActionEvent event) {
        limparLabel();
        int cod, erro = 0;

        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        if (dpemissao.getValue() == null) {
            erro = 1;
        } else if (dpemissao.getValue().isAfter(LocalDate.now())) {
            msg.campoLabel(dpemissao, lberremissao, "Data maior que atual!");
            erro = 1;
        }

        if (txprod.getText().isEmpty()) {
            validar(txprod, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txqtde.getText().isEmpty()) {
            validar(txprod, "Campo não pode estar vazio!");
            erro = 1;
        }

        if (txobser.getText().isEmpty()) {
            validar(txobser, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (erro == 0) {
            String tipo = "";
            String estoque = "";
            if (rbentrada.isSelected()) {
                tipo = "e";
            } else if (rbsaida.isSelected()) {
                tipo = "s";
            }
            if (rbarmazem.isSelected()) {
                estoque = "a";
            } else if (rbgeral.isSelected()) {
                estoque = "c";
            }

            if (acercontro.gravar(cod, java.sql.Date.valueOf(dpemissao.getValue()), tipo,
                    Integer.parseInt(txcodprod.getText()),
                    txobser.getText(), Integer.parseInt(txqtde.getText()), funcionario, estoque)) {
                estadoInicial();
            } else {
                if (cod == 0) {
                    msg.Error("Erro ao gravar!", "Problemas ao Gravar");
                } else {
                    msg.Error("Erro ao alterar!", "Problemas ao Alterar");
                }
            }
        }

    }

    public void pesquisarAcerto(AcertoEstoque ae) {

        acercontro.pesquisarAcerto(ae, dpemissao, txcod, txqtde, txqtdefinal, txusunome, txusunome, txcodprod,
                txprod, txobser, rbsaida, rbentrada, rbarmazem, rbgeral);

    }

    @FXML
    private void evtProcurarAcertos(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            Stage stage = new Stage();
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaAcerto.fxml"));
            Scene scene = new Scene(pesquisa);
            stage.setTitle("Consulta Acerto");
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
            if (acertoestoque != null) {
                pesquisarAcerto(acertoestoque);
            }

        } catch (Exception e) {
            msg.Error("Erro ao Consultar", "Nenhum Acerto foi selecionado!");
            exit(1);
        }
    }

    @FXML
    private void CalcQtdeProd(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodprod.getText().isEmpty()) {
            acercontro.CalcQtdeProd(txcodprod, txqtde, rbarmazem, rbentrada, txqtdefinal);
        }
    }

}

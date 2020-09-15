/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import estagiocarvaobarao.controller.ControllerLancarDespesas;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.CondPagto;
import estagiocarvaobarao.entidade.ContasPagar;
import estagiocarvaobarao.entidade.Despesas;
import estagiocarvaobarao.entidade.Fornecedor;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.utils.ConsultaContasDespesasController;
import estagiocarvaobarao.utils.ConsultaContasPagarController;
import estagiocarvaobarao.utils.MaskFieldUtil;
import estagiocarvaobarao.utils.Mensagens;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javax.swing.text.DateFormatter;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaLancarDespesasController implements Initializable {

    @FXML
    private Pane pnnovo;
    @FXML
    private JFXButton btnnovo;
    @FXML
    private JFXButton btnfinalizar;
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
    private JFXTextField txcodusu;
    private JFXTextField txusunome;
    @FXML
    private JFXTextField txcoddes;
    @FXML
    private JFXButton btnpesquisartipodes;
    @FXML
    private JFXTextField txtipodes;

    @FXML
    private Pane pnbotoes;
    @FXML
    private JFXButton btncancelar;

    @FXML
    private Pane pnformapgto;

    boolean flag = true;
    @FXML
    private BorderPane tela;

    @FXML
    private JFXTextField txvalor;
    @FXML
    private TableView<ContasPagar> tabela;
    @FXML
    private TableColumn< ContasPagar, String> colEmissao;
    @FXML
    private TableColumn< ContasPagar, String> colParcela;
    @FXML
    private TableColumn< ContasPagar, Double> colValor;
    @FXML
    private TableColumn< ContasPagar, String> colDataPago;
    @FXML
    private TableColumn< ContasPagar, Double> colValorPago;
    @FXML
    private TableColumn< ContasPagar, String> colVencimento;
    @FXML
    private Label lbvalor;
    public static Funcionario funcionario;
    public static Despesas despesas;
    public static List<ContasPagar> ReceberLD;
    public Double total;
    public static int CodCondPagto;
    public static String tipoFormaPagto;
    public static int qtdeparcelas;
    public static int qtdeentreparcelas;

    public static int getQtdeentreparcelas() {
        return qtdeentreparcelas;
    }

    public static void setQtdeentreparcelas(int qtdeentreparcelas) {
        TelaLancarDespesasController.qtdeentreparcelas = qtdeentreparcelas;
    }
    public static int qtdemeses;
    public static LocalDate dtprazo;

    public static int getQtdeparcelas() {
        return qtdeparcelas;
    }

    public static void setQtdeparcelas(int qtdeparcelas) {
        TelaLancarDespesasController.qtdeparcelas = qtdeparcelas;
    }

    public static int getQtdemeses() {
        return qtdemeses;
    }

    public static void setQtdemeses(int qtdemeses) {
        TelaLancarDespesasController.qtdemeses = qtdemeses;
    }

    public static LocalDate getDtprazo() {
        return dtprazo;
    }

    public static void setDtprazo(LocalDate dtprazo) {
        TelaLancarDespesasController.dtprazo = dtprazo;
    }
    private SplitPane pnparcelas;
    @FXML
    private JFXButton btnexcluir;
    @FXML
    private Pane pnlinhatop;
    @FXML
    private Pane pnlinhadown;
    @FXML
    private Label lbdata;
    @FXML
    private FontAwesomeIcon ftformpagt;
    @FXML
    private Label lbforma;
    @FXML
    private Label lbqtdeparc;

    @FXML
    private Label lbdatatexto;
    @FXML
    private Label lbqtdeparctexto;
    @FXML
    private Label lbformapagtotexto;
    @FXML
    private Label lberroemissao;
    @FXML
    private JFXButton btnparcelas;
    @FXML
    private Label lberro1venc;
    @FXML
    private JFXComboBox<CondPagto> cbFormaPagto;
    @FXML
    private DatePicker dpdatavencimento;
    @FXML
    private Label lberroentredias;
    @FXML
    private Label lberroqtdeparc;
    @FXML
    private JFXTextField txdiasentreparc;
    @FXML
    private JFXTextField txqtdeparc;
    @FXML
    private Pane tabelapane;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public static int getCodCondPagto() {
        return CodCondPagto;
    }

    public static void setCodCondPagto(int CodCondPagto) {
        TelaLancarDespesasController.CodCondPagto = CodCondPagto;
    }

    public static String getTipoFormaPagto() {
        return tipoFormaPagto;
    }

    public static void setTipoFormaPagto(String tipoFormaPagto) {
        TelaLancarDespesasController.tipoFormaPagto = tipoFormaPagto;
    }

    public static List<ContasPagar> getReceber() {
        return ReceberLD;
    }

    public static void setReceber(List<ContasPagar> Receber) {
        TelaLancarDespesasController.ReceberLD = Receber;
    }

    public static Funcionario getFuncionario() {
        return funcionario;
    }

    public static void setFuncionario(Funcionario funcionario) {
        TelaLancarDespesasController.funcionario = funcionario;
    }

    public static Despesas getDespesas() {
        return despesas;
    }

    public static void setDespesas(Despesas despesas) {
        TelaLancarDespesasController.despesas = despesas;
    }
    public static ContasPagar contaspagar;

    public static ContasPagar getContaspagar() {
        return contaspagar;
    }

    public static void setContaspagar(ContasPagar contaspagar) {
        TelaLancarDespesasController.contaspagar = contaspagar;
    }
    ControllerLancarDespesas crllancardes = new ControllerLancarDespesas();
    Mensagens msg = new Mensagens();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MaskFieldUtil.monetaryField(txvalor);
        colEmissao.setCellValueFactory(new PropertyValueFactory("emissao"));
        colParcela.setCellValueFactory(new PropertyValueFactory("parcela"));
        colValor.setCellValueFactory(new PropertyValueFactory("valor"));
        colVencimento.setCellValueFactory(new PropertyValueFactory("vencimento"));
        colDataPago.setCellValueFactory(new PropertyValueFactory("data_pago"));
        colValorPago.setCellValueFactory(new PropertyValueFactory("valor_pago"));

        colValor.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colVencimento.setCellFactory(TextFieldTableCell.forTableColumn());
        colDataPago.setCellFactory(TextFieldTableCell.forTableColumn());
        colValorPago.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        colEmissao.setCellFactory(TextFieldTableCell.forTableColumn());
        ReceberLD = new ArrayList<ContasPagar>();
        estadoInicial();
    }

    private void estadoInicial() {
        pnconteudo.setDisable(true);

        pnbotoes.setDisable(false);
        btnfinalizar.setDisable(true);
        btnpesquisar.setDisable(false);
        btncancelar.setDisable(false);
        btnnovo.setDisable(false);
        pnformapgto.setDisable(true);
        btnexcluir.setDisable(true);
        dpemissao.setValue(null);
        txtipodes.resetValidation();

        txvalor.resetValidation();
        tabelapane.setDisable(true);
        if (tabela != null) {
            tabela.getItems().clear();
        }
        lbforma.setText("");
        ftformpagt.setVisible(false);
        lbdata.setText("");
        ObservableList<Node> componentes = pnconteudo.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
        }
        if (txdiasentreparc.isVisible()) {
            txdiasentreparc.setText("");
        }
        if (txqtdeparc.isVisible()) {
            txqtdeparc.setText("");
        }

        limparLabel();
        crllancardes.carregarNivel(cbFormaPagto, "ld");
    }

    private void estadoEdicao() {
        limparLabel();
        pnconteudo.setDisable(false);
        pnformapgto.setDisable(false);
        btnfinalizar.setDisable(false);
        btncancelar.setDisable(false);
        btnpesquisar.setDisable(false);
        btnnovo.setDisable(false);
        dpemissao.setValue(LocalDate.now());
        dpdatavencimento.setValue(LocalDate.now());
        btnexcluir.setDisable(true);
        dpemissao.requestFocus();
        tabelapane.setDisable(false);

    }

    @FXML
    private void evtNovo(ActionEvent event) {
        estadoEdicao();
    }

    public void validar(JFXTextField campo, String texto) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        campo.resetValidation();
        campo.getValidators().add(validator);
        validator.setMessage(texto);
        campo.validate();
    }

    @FXML
    private void evtFinalizar(ActionEvent event) throws ParseException {
        int cod = 0, coddes = 0, erro = 0, qtde_parcelas = 0;
        String emissor_cheque = "";
        NumberFormat nf = new DecimalFormat("#,###.00");
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        try {
            qtde_parcelas = Integer.parseInt(txqtdeparc.getText());
        } catch (Exception e) {
            qtde_parcelas = 0;
        }
        try {
            coddes = Integer.parseInt(txcoddes.getText());
        } catch (Exception e) {
            coddes = 0;
        }

        if (txtipodes.getText().isEmpty()) {
            validar(txtipodes, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txvalor.getText().isEmpty()) {
            validar(txtipodes, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (dpemissao.getValue() == null) {
            msg.campoLabel(dpemissao, lberroemissao, "Campo não pode estar vazio!");
            erro = 1;
        } else {
            if (dpemissao.getValue().isAfter(LocalDate.now())) {
                msg.campoLabel(dpemissao, lberroemissao, "Data não pode ser maior que atual!");
                erro = 1;
            }
        }
        if (cbFormaPagto.getSelectionModel().isEmpty()) {
            msg.campoVazioCbx(cbFormaPagto, "Campo não pode estar vazio!");
            erro = 1;
        } else {
            if (tipoFormaPagto.equals("a prazo")) {
                if (txdiasentreparc.getText().isEmpty()) {
                    msg.campoLabel(txdiasentreparc, lberroentredias, "Campo não pode estar vazio!");
                    erro = 1;
                } else if (Integer.parseInt(txdiasentreparc.getText()) == 0) {
                    msg.campoLabel(txdiasentreparc, lberroentredias, "Campo não pode ser 0!");
                    erro = 1;
                } else if (Integer.parseInt(txdiasentreparc.getText()) < 0) {
                    msg.campoLabel(txdiasentreparc, lberroentredias, "Campo não pode ser negativo!");
                    erro = 1;
                }
                if (txqtdeparc.getText().isEmpty()) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode estar vazio!");
                    erro = 1;
                } else if (Integer.parseInt(txqtdeparc.getText()) == 0) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode ser 0!");
                    erro = 1;
                } else if (Integer.parseInt(txqtdeparc.getText()) < 0) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode ser negativo!");
                    erro = 1;
                }
            }
            if (tipoFormaPagto.equals("fixa")) {

                if (txqtdeparc.getText().isEmpty()) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode estar vazio!");
                    erro = 1;
                } else if (Integer.parseInt(txqtdeparc.getText()) == 0) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode ser 0!");
                    erro = 1;
                } else if (Integer.parseInt(txqtdeparc.getText()) < 0) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode ser negativo!");
                    erro = 1;
                }

            }
            if (tipoFormaPagto.equals("cheque")) {

                if (txqtdeparc.getText().isEmpty()) {
                    msg.campoVazio(txqtdeparc, "");
                    erro = 1;
                } else {
                    emissor_cheque = txqtdeparc.getText();
                }

            }

            Date vencimento;
            if (ReceberLD != null && !ReceberLD.isEmpty()) {
                for (int i = 0; i < ReceberLD.size(); i++) {
                    vencimento = ReceberLD.get(i).getVencimentoDate();
                    if (vencimento.before(ReceberLD.get(i).getEmissaoDate())) {
                        erro = 1;
                        msg.Error("Prometheus Informa", "Data de vencimento não pode ser anterior a de emissão!");
                        i = ReceberLD.size();
                    }
                }
                if (!tipoFormaPagto.equals("cheque") && !tipoFormaPagto.equals("fixa") && crllancardes.calculaTotalDes(ReceberLD, nf.parse(txvalor.getText()).doubleValue()) == false) {
                    erro = 1;
                    msg.Error("Prometheus Informa", "Somatório das parcelas é maior/menor que total das despesas!!");
                }
            }
            if (!tipoFormaPagto.equals("fiado") && ReceberLD.isEmpty()) {
                erro = 1;
                msg.Error("Prometheus Informa", "Nenhuma parcela gerada!");
            }
        }

        if (dpdatavencimento.getValue() == null) {
            msg.campoLabel(dpdatavencimento, lberro1venc, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (erro == 0) {
            if (crllancardes.finalizar(cod, coddes, funcionario, ReceberLD, tabela, dpdatavencimento, dpemissao, emissor_cheque, qtde_parcelas)) {
                if (cod == 0) {
                    msg.Affirmation("Prometheus Informa", "Gravado com sucesso!");
                } else {
                    msg.Affirmation("Prometheus Informa", "Alterado com sucesso!");
                }
                estadoInicial();
            } else {
                if (cod == 0) {
                    msg.Error("Prometheus Informa", "Erro ao gravar!");
                } else {
                    msg.Error("Prometheus Informa", "Erro ao alterar!");
                }
            }
        }
    }

    private void exit(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        if (!pnconteudo.isDisabled())//encontra em estado de edição
        {
            estadoInicial();
        } else {
            btnnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    public void fecharTextoForma() {
        pnlinhatop.setVisible(false);
        pnlinhadown.setVisible(false);

        lbformapagtotexto.setVisible(false);
        lbqtdeparctexto.setVisible(false);
        lbdata.setVisible(false);
        lbforma.setVisible(false);
        lbqtdeparc.setVisible(false);
        ftformpagt.setVisible(false);
    }

    public void atualizarTextoForma(String forma, String icon) {
        pnlinhatop.setVisible(true);
        pnlinhadown.setVisible(true);

        lbformapagtotexto.setVisible(true);
        lbqtdeparctexto.setVisible(true);
        lbdata.setVisible(true);
        lbforma.setVisible(true);
        lbqtdeparc.setVisible(true);
        ftformpagt.setVisible(true);
        ftformpagt.setIconName(icon);
        if (dpemissao.getValue() != null) {
            lbdata.setText(dpemissao.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        lbqtdeparc.setText("1");
        lbforma.setText(forma);
    }

    @FXML
    private void onExitDespesa(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcoddes.getText().isEmpty()) {
            pesquisarTipoDespesas(null, Integer.parseInt(txcoddes.getText()));
        }
    }

    public void pesquisarTipoDespesas(Despesas des, int cod) {

        crllancardes.pesquisarTipoDespesas(des, txcoddes, txtipodes, cod);

    }

    @FXML
    private void evtProcurarTipoDespesa(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            Stage stage = new Stage();
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaDespesas.fxml"));
            Scene scene = new Scene(pesquisa);
            stage.setTitle("Consulta Despesas");
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
            pesquisarTipoDespesas(despesas, despesas.getCodigo());

        } catch (Exception e) {
            msg.Error("Erro ao Consultar", "Nenhuma Despesa foi selecionada!");
            exit(1);
        }
    }

    public void atualizarTabela(ContasPagar contasPagar) {
        tabela.setVisible(true);
        ObservableList<ContasPagar> tabcontas;
        ReceberLD = (List<ContasPagar>) contasPagar.getConta(contasPagar.getCod_iddes());
        tabcontas = FXCollections.observableArrayList(ReceberLD);
        this.tabela.setItems(null);
        this.tabela.setItems(tabcontas);

    }

    public void atualizarTabelaReceber() {
        ObservableList<ContasPagar> prod_rec = null;
        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }
        prod_rec = FXCollections.observableArrayList(ReceberLD);
        tabela.setItems(prod_rec);
    }

    private void estadoEdicaoD() {
        Mensagens msg = new Mensagens();
        tabela.setDisable(false);
        tabela.setVisible(true);
        pnformapgto.setDisable(false);
        tabelapane.setDisable(false);
        pnconteudo.setDisable(false);

    }

    public void pesquisarDespesas(ContasPagar despesas) {
        if (despesas != null) {
            estadoEdicaoD();
            if (!crllancardes.carregarDespesa(contaspagar, dpemissao, cbFormaPagto, txqtdeparc, txdiasentreparc, ReceberLD,
                    btnexcluir, btnfinalizar, btncancelar, btnnovo, tabela, total, txcod, txcoddes, txtipodes, txvalor, dpdatavencimento)) {
                estadoInicial();
            }
        }
    }

    @FXML
    private void evtProcurarDespesas(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            Stage stage = new Stage();
            ConsultaContasDespesasController.setFlag(1);
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaContasDespesas.fxml"));
            Scene scene = new Scene(pesquisa);
            stage.setTitle("Consulta Despesas");
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
            pesquisarDespesas(contaspagar);

        } catch (Exception e) {
            msg.Error("Erro ao Consultar", "Nenhuma Despesa foi selecionada!");
            exit(1);
        }
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            if (contaspagar.verificaPagamento(contaspagar)) {
                if (crllancardes.excluir(contaspagar)) {
                    msg.Affirmation("Excluido com sucesso", "Despesa excluida!");
                    contaspagar = null;
                    estadoInicial();
                } else {
                    msg.Error("Erro ao excluir!", "Problemas ao excluir");

                }
            } else {
                msg.Error("Erro ao excluir!", "Há parcelas pagas!");
            }
        }
    }

    @FXML
    private void gerarParcelas(ActionEvent event) {
        int cod = 0, erro = 0;
        limparLabel();
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        if (txvalor.getText().isEmpty()) {
            msg.campoVazio(txvalor, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txcoddes.getText().isEmpty()) {
            msg.campoVazio(txtipodes, "Campo não pode estar vazio!");
            erro = 1;
        }

        if (cbFormaPagto.getSelectionModel().isEmpty()) {
            msg.campoVazioCbx(cbFormaPagto, "Campo não pode estar vazio!");
            erro = 1;
        } else {
            tipoFormaPagto = cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao();
            if (tipoFormaPagto.equals("a prazo")) {
                if (txdiasentreparc.getText().isEmpty()) {
                    msg.campoLabel(txdiasentreparc, lberroentredias, "Campo não pode estar vazio!");
                    erro = 1;
                } else if (Integer.parseInt(txdiasentreparc.getText()) == 0) {
                    msg.campoLabel(txdiasentreparc, lberroentredias, "Campo não pode ser 0!");
                    erro = 1;
                } else if (Integer.parseInt(txdiasentreparc.getText()) < 0) {
                    msg.campoLabel(txdiasentreparc, lberroentredias, "Campo não pode ser negativo!");
                    erro = 1;
                }
                if (txqtdeparc.getText().isEmpty()) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode estar vazio!");
                    erro = 1;
                } else if (Integer.parseInt(txqtdeparc.getText()) == 0) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode ser 0!");
                    erro = 1;
                } else if (Integer.parseInt(txqtdeparc.getText()) < 0) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode ser negativo!");
                    erro = 1;
                }
            }
            if (tipoFormaPagto.equals("fixa")) {

                if (txqtdeparc.getText().isEmpty()) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode estar vazio!");
                    erro = 1;
                } else if (Integer.parseInt(txqtdeparc.getText()) == 0) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode ser 0!");
                    erro = 1;
                } else if (Integer.parseInt(txqtdeparc.getText()) < 0) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode ser negativo!");
                    erro = 1;
                }

            }
            if (tipoFormaPagto.equals("cheque")) {

                if (txqtdeparc.getText().isEmpty()) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode estar vazio!");
                    erro = 1;
                }

            }
        }
        if (dpemissao.getValue() == null) {
            msg.campoLabel(dpemissao, lberroemissao, "Campo não pode estar vazio!");
            erro = 1;
        } else if (dpemissao.getValue().isAfter(LocalDate.now())) {
            msg.campoLabel(dpemissao, lberroemissao, "Campo não pode ser maior que data atual!");
            erro = 1;
        }
        if (dpdatavencimento.getValue() == null) {
            msg.campoLabel(dpdatavencimento, lberro1venc, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (erro == 0) {
            limparLabel();
            NumberFormat nf = new DecimalFormat("#,###.00");
            try {
                crllancardes.confirmarPag(tipoFormaPagto, cbFormaPagto, lbqtdeparc,
                        lbdata, txqtdeparc, lbforma, ftformpagt, txdiasentreparc, dpdatavencimento,
                        cod, nf.parse(txvalor.getText()).doubleValue(), dpemissao, tabela, contaspagar);

            } catch (ParseException ex) {
                Logger.getLogger(TelaLancarDespesasController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
        btnparcelas.setDisable(false);
        btnparcelas.setVisible(true);
    }

    public void limparLabel() {
        lberro1venc.setText("");
        lberroentredias.setText("");
        lberroemissao.setText("");
        lberroqtdeparc.setText("");
        lberroqtdeparc.setText("");
        txtipodes.resetValidation();
        cbFormaPagto.resetValidation();
    }

    @FXML
    private void pegarForma(ActionEvent event) {
        if (cbFormaPagto.getSelectionModel().getSelectedItem() != null) {
            tipoFormaPagto = cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao();
        }
        if (!tipoFormaPagto.isEmpty()) {
            if (tipoFormaPagto.equals("cheque")) {
                limparLabel();
                txqtdeparc.setVisible(true);
                txqtdeparc.setPromptText("Banco emissor do cheque");
                txdiasentreparc.setVisible(false);
            } else if (tipoFormaPagto.equals("fiado") || tipoFormaPagto.equals("à vista")
                    || tipoFormaPagto.equals("débito")) {
                limparLabel();
                txqtdeparc.setVisible(false);
                txdiasentreparc.setVisible(false);
            } else if (tipoFormaPagto.equals("fixa")) {
                txqtdeparc.setVisible(true);
                limparLabel();
                txqtdeparc.setPromptText("Quantidade de Meses ");
                txdiasentreparc.setVisible(false);
            } else {
                limparLabel();
                txqtdeparc.setVisible(true);
                txqtdeparc.setPromptText("Quantidade de Parcelas ");
                txdiasentreparc.setVisible(true);
            }
        }
    }

    @FXML
    private void editEmissao(TableColumn.CellEditEvent<ContasPagar, String> event) {
        contaspagar = tabela.getSelectionModel().getSelectedItem();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date emissao;
        try {
            emissao = (Date) formatter.parse(event.getNewValue());
            contaspagar.setEmissao(emissao);
            atualizarTabelaReceber();
        } catch (ParseException ex) {

        }
    }

    public boolean verificaValor() {
        Double total = 0.0;
        for (ContasPagar contasPagar : ReceberLD) {
            total += contasPagar.getValor();
        }
        if (total > ReceberLD.get(0).getValor_total()) {
            return false;
        }

        return true;
    }

    @FXML
    private void editValor(TableColumn.CellEditEvent<ContasPagar, Double> event) {
        if (ReceberLD != null && !ReceberLD.isEmpty()) {
            contaspagar = tabela.getSelectionModel().getSelectedItem();
            contaspagar.setValor(event.getNewValue());
            if (contaspagar.getCondicaopagamento().getDescricao().equals("à vista") && contaspagar.getParcela().equals("1/1")) {
                contaspagar.setValor_pago(event.getNewValue());
            }
            if (contaspagar.getCondicaopagamento().getDescricao().equals("fixa")) {
                try {
                    ReceberLD = crllancardes.gerarParcelas(contaspagar.getCondicaopagamento(), contaspagar.getValor(), dpemissao, dpdatavencimento,
                            txdiasentreparc, contaspagar.getQtde_parcela(), contaspagar.getCod_iddes(), contaspagar.getCodigo());
                } catch (ParseException ex) {
                    Logger.getLogger(TelaLancarDespesasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            atualizarTabelaReceber();
        }

    }

    @FXML
    private void editVencimento(TableColumn.CellEditEvent<ContasPagar, String> event) {
        contaspagar = tabela.getSelectionModel().getSelectedItem();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date vencimento;
        try {
            vencimento = (Date) formatter.parse(event.getNewValue());
            if (!vencimento.before(contaspagar.getEmissaoDate())) {
                contaspagar.setVencimento(vencimento);
                atualizarTabelaReceber();
            } else {
                msg.Error("Prometheus Informa", "Data de vencimento não pode ser anterior a de emissão!");
                atualizarTabelaReceber();
            }
        } catch (ParseException ex) {

        }
    }

    @FXML
    private void editDataPago(TableColumn.CellEditEvent<ContasPagar, String> event) {
        contaspagar = tabela.getSelectionModel().getSelectedItem();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date datapago;
        try {
            datapago = (Date) formatter.parse(event.getNewValue());
            contaspagar.setData_pago(datapago);
            atualizarTabelaReceber();
        } catch (ParseException ex) {

        }
    }

    @FXML
    private void editValorPago(TableColumn.CellEditEvent<ContasPagar, Double> event) {
        contaspagar = tabela.getSelectionModel().getSelectedItem();
        contaspagar.setValor_pago(event.getNewValue());
        atualizarTabelaReceber();
    }

}

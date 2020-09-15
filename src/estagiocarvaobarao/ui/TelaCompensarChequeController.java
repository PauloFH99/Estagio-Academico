/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.controller.ControllerCompensarCheque;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.ContasPagar;
import estagiocarvaobarao.entidade.Fornecedor;
import estagiocarvaobarao.utils.ConsultaContasPagarController;
import estagiocarvaobarao.utils.Mensagens;
import estagiocarvaobarao.utils.TelaBaixarController;
import static estagiocarvaobarao.utils.TelaBaixarController.contaspagarclic;
import static java.lang.System.exit;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaCompensarChequeController implements Initializable {

    @FXML
    private BorderPane tela;
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
    private JFXTextField txcod;
    @FXML
    private AnchorPane pntabelaA;

    @FXML
    private Pane pnbotoes;
    @FXML
    private JFXButton btncancelar;
    @FXML
    private JFXButton btnimprimir;
    @FXML
    private Pane pnconteudo1;
    @FXML
    private Pane pnconteudo11;
    @FXML
    private ToggleGroup Status;
    @FXML
    private Pane pnfiltros;

    @FXML
    private DatePicker dpinicial;
    @FXML
    private DatePicker dpfinal;
    @FXML
    private JFXRadioButton rbpendente;
    @FXML
    private JFXRadioButton rbbaixado;
    @FXML
    private JFXRadioButton rbtodos;

    @FXML
    private Label lbtotal;
    @FXML
    private JFXButton btnestornar;
    @FXML
    private TableView<ContasPagar> tabela;
    @FXML
    private TableColumn<ContasPagar, Integer> colcod;
    @FXML
    private TableColumn<ContasPagar, String> colfor;
    @FXML
    private TableColumn<ContasPagar, String> colemissor;
    @FXML
    private TableColumn<ContasPagar, Date> colven;
    @FXML
    private TableColumn<ContasPagar, Date> colemi;
    @FXML
    private TableColumn<ContasPagar, Double> colvalor;
    @FXML
    private TableColumn<ContasPagar, String> coldatpag;
    @FXML
    private TableColumn<ContasPagar, Date> colvalorpago;
    @FXML
    private TableColumn<ContasPagar, Date> colvalorpendente;
    public static List<ContasPagar> valores;

    public static List<ContasPagar> getValores() {
        return valores;
    }

    public static void setValores(List<ContasPagar> valores) {
        TelaCompensarChequeController.valores = valores;
    }

    public static double valorbaixado;
    public static ContasPagar contapagar;
    public static ContasPagar contapagarclicC;

    public static ContasPagar getContapagarclic() {
        return contapagarclicC;
    }

    public static void setContapagarclic(ContasPagar contapagarclic) {
        TelaCompensarChequeController.contapagarclicC = contapagarclic;
    }

    public static double getValorbaixado() {
        return valorbaixado;
    }

    public static void setValorbaixado(double valorbaixado) {
        TelaCompensarChequeController.valorbaixado = valorbaixado;
    }

    public static ContasPagar getContapagar() {
        return contapagar;
    }

    public static void setContapagar(ContasPagar contapagar) {
        TelaCompensarChequeController.contapagar = contapagar;
    }
    @FXML
    private Pane pntotais;
    @FXML
    private JFXButton btnfiltrar;

    @FXML
    private Pane pnbotoesrodape;
    @FXML
    private Pane pnconteudoparc;
    ControllerCompensarCheque cheqctrl = new ControllerCompensarCheque();
    Mensagens msg = new Mensagens();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colfor.setCellValueFactory(new PropertyValueFactory("fornecedor"));
        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colemissor.setCellValueFactory(new PropertyValueFactory("emissor_cheque"));
        colven.setCellValueFactory(new PropertyValueFactory("vencimento"));
        colemi.setCellValueFactory(new PropertyValueFactory("emissao"));
        colvalor.setCellValueFactory(new PropertyValueFactory("valor"));
        coldatpag.setCellValueFactory(new PropertyValueFactory("data_pago"));
        colvalorpago.setCellValueFactory(new PropertyValueFactory("valor_pago"));
        colvalorpendente.setCellValueFactory(new PropertyValueFactory("valor_pendente"));
        estadoInicial();
    }

    private void estadoInicial() {
        pnfiltros.setDisable(true);
        pnconteudo.setDisable(true);
        pntabelaA.setDisable(true);
        pnpesquisa.setDisable(true);
        pnbotoes.setDisable(false);
        btnimprimir.setDisable(true);
        btnpesquisar.setDisable(true);
        btnestornar.setDisable(true);
        btncancelar.setDisable(false);
        btnfinalizar.setDisable(true);
        pnconteudoparc.setDisable(true);
        if (tabela != null) {
            tabela.getItems().clear();
        }

        ObservableList<Node> componentes = pnfiltros.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
        }
        cheqctrl.carregatabel(tabela, valores);
    }

    private void estadoEdicao() throws ParseException {
        Mensagens msg = new Mensagens();
        pnconteudo.setDisable(false);
        pnfiltros.setDisable(false);
        pntabelaA.setDisable(false);
        pnpesquisa.setDisable(false);
        btnfinalizar.setDisable(false);
        btncancelar.setDisable(false);
        btnpesquisar.setDisable(false);
        btnimprimir.setDisable(false);
        btnnovo.setDisable(false);
        btnfinalizar.setDisable(true);
        pnconteudoparc.setDisable(false);

        cheqctrl.carregatabel(tabela, valores);
        cheqctrl.calcularTotal(valores, lbtotal);

    }

    @FXML
    private void evtNovo(ActionEvent event) throws ParseException {
        estadoEdicao();
    }

    @FXML
    private void evtFinalizar(ActionEvent event) {
        if (cheqctrl.finalizar(contapagarclicC)) {
            msg.Confirmation("Prometheus Informa", "Pago com sucesso!");
            estadoInicial();
        } else {
            msg.Error("Prometheus Informa", "Erro ao pagar!");
        }
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        if (!pnconteudo.isDisabled() && !pnfiltros.isDisable())//encontra em estado de edição
        {
            estadoInicial();
        } else {
            btnnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    @FXML
    private void evtImprimir(ActionEvent event) {
    }

    public void pesquisarContaPagar(ContasPagar contapagar) throws ParseException {
        cheqctrl.pesquisarContaPagar(txcod, tabela, valores, contapagar.getCodigo(), lbtotal);

    }

    @FXML
    private void evtProcurarContasPagar(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            ConsultaContasPagarController.flagCP = 3;
            Stage stage = new Stage();
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaContasPagar.fxml"));
            Scene scene = new Scene(pesquisa);
            stage.setTitle("Consultar Cheques Pagar");
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
            if (contapagar != null) {
                pesquisarContaPagar(contapagar);
            }

        } catch (Exception e) {
            msg.Error("Erro ao Consultar", "Nenhuma Conta foi selecionado!");
            exit(1);
        }
    }

    @FXML
    private void evtEstornar(ActionEvent event) throws ParseException {
        //contapagarclicC = tabela.getSelectionModel().getSelectedItem();
        if (cheqctrl.estornar(contapagarclicC)) {
            msg.Affirmation("Prometheus Informa", "Estornado com sucesso!");
            cheqctrl.carregatabel(tabela, valores);
        } else {
            msg.Error("Prometheus Informa", "Erro ao estornar!");
        }

    }

    @FXML
    private void evtPegarConta(MouseEvent event) {
        contapagarclicC = tabela.getSelectionModel().getSelectedItem();
        lbtotal.setText(String.valueOf(contapagarclicC.getValor()).replace(".", ","));
        if (contapagarclicC.getData_pagoDate() == null && contapagarclicC.getValor_pago() == 0) {
            btnfinalizar.setDisable(false);
            btnestornar.setDisable(true);
        } else if (contapagarclicC.getData_pagoDate() != null && contapagarclicC.getValor_pago() != 0) {
            btnfinalizar.setDisable(true);
            btnestornar.setDisable(false);
        }
    }

    @FXML
    private void evtFiltrar(ActionEvent event) {
        String filtrorb = "";
        String filtroini = "";
        String filtrofim = "";
        if (dpinicial.getValue() != null && dpfinal.getValue() != null) {
            filtroini = dpinicial.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            filtrofim = dpfinal.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            if (rbbaixado.isSelected()) {
                filtrorb = rbbaixado.getText();
            } else if (rbpendente.isSelected()) {
                filtrorb = rbpendente.getText();
            } else {
                filtrorb = rbtodos.getText();
            }
            cheqctrl.carregarConsultaEmissao(filtroini, filtrofim, filtrorb, "cheque", valores, tabela);
        } else {
            msg.Error("Prometheus Informa", "Data Inicial/Final não selecionadas");
        }

    }

    @FXML
    private void evtPendente(ActionEvent event) {
        if (rbpendente.isSelected()) {
            cheqctrl.carregarConsultaPendente("contas_pagar", "cheque", valores, tabela);
            cheqctrl.calcularTotal(valores, lbtotal);

        }
    }

    @FXML
    private void evtBaixado(ActionEvent event) {
        if (rbbaixado.isSelected()) {
            cheqctrl.carregarConsultabaixado("contas_pagar", "cheque", valores, tabela);
            cheqctrl.calcularTotal(valores, lbtotal);

        }

    }

    @FXML
    private void evtTodos(ActionEvent event) {
        if (rbtodos.isSelected()) {
            cheqctrl.carregarConsultaTodos("contas_pagar", "cheque", valores, tabela);
            cheqctrl.calcularTotal(valores, lbtotal);

        }
    }

}

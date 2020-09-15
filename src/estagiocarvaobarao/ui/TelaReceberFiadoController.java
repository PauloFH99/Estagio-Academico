/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.controller.ControllerReceberFiado;
import estagiocarvaobarao.entidade.Cliente;
import estagiocarvaobarao.entidade.ContasReceber;
import estagiocarvaobarao.entidade.Venda;
import estagiocarvaobarao.utils.Mensagens;
import estagiocarvaobarao.utils.TelaBaixarController;
import java.net.URL;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaReceberFiadoController implements Initializable {

    @FXML
    private JFXButton btnpesquisarcli;
    @FXML
    private JFXTextField txcodcli;
    @FXML
    private JFXTextField txclinome;
    @FXML
    private JFXButton btnfiltrar;

    @FXML
    private JFXButton btncancelar;
    @FXML
    private JFXButton btnfinalizar;
    @FXML
    private Label txTotalRecebido;
    @FXML
    private Label txTotal;
    @FXML
    private TableView<ContasReceber> tabela;
    @FXML
    private TableColumn<ContasReceber, Date> colEmissao;
    @FXML
    private TableColumn<ContasReceber, String> colCliente;
    @FXML
    private TableColumn<ContasReceber, Double> colTotal;
    @FXML
    private TableColumn<ContasReceber, Date> colDataPago;
    @FXML
    private TableColumn<ContasReceber, Double> colValorPago;

    @FXML
    private Pane conteudo;
    @FXML
    private Pane conteudo2;
    @FXML
    private Pane periodo;
    @FXML
    private JFXTextField txsaldo;
    ControllerReceberFiado recctrl = new ControllerReceberFiado();
    public static List<ContasReceber> ReceberFiado;

    public static Cliente cli;
    public static Double valor;
    public static Double valorBaixado = 0.0;

    public static Double getValorBaixado() {
        return valorBaixado;
    }

    public static void setValorBaixado(Double valorBaixado) {
        TelaReceberFiadoController.valorBaixado = valorBaixado;
    }

    public static Cliente getCli() {
        return cli;
    }

    public static void setCli(Cliente cli) {
        TelaReceberFiadoController.cli = cli;
    }

    public static Double getValor() {
        return valor;
    }

    public static void setValor(Double valor) {
        TelaReceberFiadoController.valor = valor;
    }

    Mensagens msg = new Mensagens();
    @FXML
    private JFXButton btnestornar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        colEmissao.setCellValueFactory(new PropertyValueFactory("emissao"));
        colCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        colTotal.setCellValueFactory(new PropertyValueFactory("valor"));
        colDataPago.setCellValueFactory(new PropertyValueFactory("data_pago"));
        colValorPago.setCellValueFactory(new PropertyValueFactory("valor_pago"));
        estadoEdicao();
    }

    public void limparlabel() {
        txcodcli.resetValidation();
        txclinome.resetValidation();

    }

    private void estadoEdicao() {
        conteudo.setDisable(false);
        conteudo2.setDisable(false);
        periodo.setDisable(false);
        periodo.setVisible(true);

        tabela.setDisable(false);
        tabela.setVisible(true);

        btnfinalizar.setDisable(false);
        btncancelar.setDisable(false);

    }

    private void estadoInicial() {
        conteudo.setDisable(true);
        conteudo2.setDisable(true);
        tabela.setVisible(false);

        btnfinalizar.setDisable(true);
        btncancelar.setDisable(false);
        tabela.setVisible(false);
        if (tabela != null) {
            tabela.getItems().clear();
        }

        ObservableList<Node> componentes = conteudo.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
        }
        txcodcli.setText("");
        txclinome.setText("");

        txsaldo.setText("");
        limparlabel();
        txcodcli.resetValidation();
    }

    public void pesquisarCliente(Cliente cli) {
        recctrl.pesquisarCliente(cli, txcodcli, txclinome);

    }

    @FXML
    private void evtProcurarCliente(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            Stage stage = new Stage();
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaCliente.fxml"));
            Scene scene = new Scene(pesquisa);
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.setTitle("Consulta de Clientes");
            stage.showAndWait();
            if (cli != null) {
                pesquisarCliente(cli);
            }

        } catch (Exception e) {
            msg.Error("", e.getMessage());
        }
    }

    @FXML
    private void onExitCliente(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodcli.getText().isEmpty()) {
            recctrl.pesquisarCliente(Integer.parseInt(txcodcli.getText()), txcodcli, txclinome);
        }
    }

    public void calculaTotal() {
        Double total = 0.0;
        for (ContasReceber receber : ReceberFiado) {
            total += receber.getValor();
        }

        txTotal.setText("TOTAL: " + total.toString());

    }

    public void calculaBaixado() {
        if (valorBaixado != 0) {
            txTotalRecebido.setText("TOTAL RECEBIDO: " + valorBaixado.toString());
        }
    }

    @FXML
    private void evtFiltrar(ActionEvent event) {
        int erro = 0;
        limparlabel();

        if (txclinome.getText().isEmpty()) {
            msg.campoVazio(txclinome, "");
            erro = 1;
        }
        if (erro == 0) {
            ReceberFiado = recctrl.buscarCli(txcodcli, tabela, txsaldo);
            //calculaTotal();
        }

    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        btncancelar.getScene().getWindow().hide();//fecha a janela
    }

    public void atualizarTabela() {
        ObservableList<ContasReceber> prod_v = null;
        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }
        prod_v = FXCollections.observableArrayList(ReceberFiado);

        tabela.setItems(prod_v);
    }

    @FXML
    private void evtFinalizar(ActionEvent event) {
        int erro = 0;
        limparlabel();

        if (txclinome.getText().isEmpty()) {
            msg.campoVazio(txclinome, "");
            erro = 1;
        }
        if (erro == 0) {
            try {
                Stage stage = new Stage();
                TelaBaixarController.setTipo(3);
                if (tabela.getSelectionModel().getSelectedItem() == null) {
                    msg.Error("prometheus Informa", "Nenhuma conta selecionada!");
                } else {
                    TelaBaixarController.setContasreceberclic(tabela.getSelectionModel().getSelectedItem());
                    Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/TelaBaixar.fxml"));
                    Scene scene = new Scene(pesquisa);
                    //Show main Window
                    stage.setScene(scene);
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
                    stage.setResizable(false);
                    stage.setTitle("Pagamento");
                    stage.showAndWait();
                    calculaBaixado();
                    ReceberFiado = recctrl.buscarCli(txcodcli, tabela, txsaldo);
                    atualizarTabela();
                    //calculaTotal();
                }

            } catch (Exception e) {
                msg.Error("", e.getMessage());
            }
        }

    }

    @FXML
    private void evtTabela(MouseEvent event) {
        if (tabela.getSelectionModel().getSelectedItem() != null) {
            if (tabela.getSelectionModel().getSelectedItem().getValor_pago() < tabela.getSelectionModel().getSelectedItem().getValor()
                    || tabela.getSelectionModel().getSelectedItem().getValor_pago() == 0) {
                btnfinalizar.setDisable(false);
                btnestornar.setDisable(true);
            } else if (tabela.getSelectionModel().getSelectedItem().getValor_pago() >= tabela.getSelectionModel().getSelectedItem().getValor()) {
                btnfinalizar.setDisable(true);
                btnestornar.setDisable(false);
            }
        }
    }

    @FXML
    private void evtEstornar(ActionEvent event) {
        if (recctrl.estornar(tabela.getSelectionModel().getSelectedItem())) {
            msg.Affirmation("Prometheus Informa", "Estornado com sucesso!");
            recctrl.atualizarTabela(tabela.getSelectionModel().getSelectedItem().getCliente().getCodigo(), tabela, txsaldo);
        } else {
            msg.Error("Prometheus Informa", "Erro ao estornar!");
        }
    }

}

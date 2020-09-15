/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.controller.ControllerPedidos;
import estagiocarvaobarao.controller.ControllerVenda;
import estagiocarvaobarao.entidade.Cliente;
import estagiocarvaobarao.entidade.Pedido;
import estagiocarvaobarao.entidade.Produto_Venda;
import estagiocarvaobarao.entidade.Venda;
import estagiocarvaobarao.utils.Mensagens;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaPedidosArmazemController implements Initializable {

    @FXML
    private Pane pnbotoes;
    @FXML
    private JFXButton btncancelar;
    @FXML
    private JFXButton btnfinalizar;
    @FXML
    private Pane conteudo;
    private JFXTextField txcod;
    @FXML
    private JFXTextField txclinome;
    @FXML
    private JFXTextField txcodcli;
    @FXML
    private DatePicker dpinicial;
    @FXML
    private Label lbini;
    @FXML
    private Label lberrodtini;
    @FXML
    private JFXComboBox<String> cbsitu;
    @FXML
    private TableView<Produto_Venda> tabela;
    @FXML
    private TableColumn<Produto_Venda, String> colDescri;
    @FXML
    private TableColumn<Produto_Venda, Integer> colQtde;
    @FXML
    private TableView<Venda> tabelaReceber;
    @FXML
    private TableColumn<Venda, String> colEmissao;
    @FXML
    private TableColumn<Venda, String> colCliente;
    @FXML
    private TableColumn<Venda, Double> colValor;
    @FXML
    private TableColumn<Venda, String> colSitu;
    @FXML
    private TableColumn<Venda, Boolean> colSitu1;

    @FXML
    private JFXButton btnpesquisarcli;
    ControllerVenda cven = new ControllerVenda();
    ControllerPedidos cpe = new ControllerPedidos();
    public static List<Venda> vendas;

    public static List<Venda> getVendas() {
        return vendas;
    }

    public static void setVendas(List<Venda> vendas) {
        TelaPedidosArmazemController.vendas = vendas;
    }

    public static List<Produto_Venda> produtos;

    public static List<Produto_Venda> getProdutos() {
        return produtos;
    }

    public static void setProdutos(List<Produto_Venda> produtos) {
        TelaPedidosArmazemController.produtos = produtos;
    }

    private static Cliente cli;

    public static Cliente getCli() {
        return cli;
    }

    public static void setCli(Cliente cli) {
        TelaPedidosArmazemController.cli = cli;
    }
    @FXML
    private JFXButton btnLimpar;
    @FXML
    private JFXButton btnBuscar1;
    @FXML
    private JFXButton btnentrega;
    Mensagens msg = new Mensagens();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colEmissao.setCellValueFactory(new PropertyValueFactory("emissao"));
        colCliente.setCellValueFactory(new PropertyValueFactory("Cliente"));
        colValor.setCellValueFactory(new PropertyValueFactory("total"));
        colSitu.setCellValueFactory(new PropertyValueFactory("situacao"));
        setupTableViewColumn();
        colSitu.setCellFactory(TextFieldTableCell.forTableColumn());
        colDescri.setCellValueFactory(new PropertyValueFactory("cod_produto"));
        colQtde.setCellValueFactory(new PropertyValueFactory("qtde"));

        estadoEdicao();

        // TODO
    }

    private void setupTableViewColumn() {
        colSitu1.setCellValueFactory(new PropertyValueFactory<>("selected"));
        colSitu1.setCellFactory(CheckBoxTableCell.forTableColumn(colSitu1));
    }

    private void estadoEdicao() {
        Mensagens msg = new Mensagens();
        conteudo.setDisable(false);
        tabela.setDisable(false);
        tabela.setVisible(true);
        btnfinalizar.setDisable(false);
        btncancelar.setDisable(false);

        txclinome.resetValidation();
        String[] dados = {"Retirada Balcão", "Pendente-Entrega", "Entregue", "Entrega"};
        cbsitu.setItems(FXCollections.observableArrayList(dados));

    }

    public void estadoInicial() {
        conteudo.setDisable(true);
        tabela.setVisible(true);
        pnbotoes.setDisable(false);
        btnfinalizar.setDisable(true);
        btncancelar.setDisable(false);

        dpinicial.setValue(LocalDate.now());
        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }
        txcod.setText("");
        txcodcli.setText("");
        txclinome.setText("");
        ObservableList<Node> componentes = conteudo.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }

        }

    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        btncancelar.getScene().getWindow().hide();//fecha a janela

    }

    @FXML
    private void evtEntrega(ActionEvent event) {
        int erro = 0;
        if (tabelaReceber.getItems().isEmpty()) {
            erro = 1;
        }
        if (erro == 0) {
            if (cpe.alterarSituacaoItems(tabelaReceber.getItems())) {
                msg.Affirmation("Prometheus Informa", "Pedidos alterados com sucesso!");
                tabelaReceber.getItems().clear();
                tabela.getItems().clear();
            } else {
                msg.Error("Prometheus Informa", "Erro ao alterar pedidos!");
            }
        }
    }

    @FXML
    private void evtFinalizar(ActionEvent event) {

        String situacao = "";
        int codvenda = 0, erro = 0, cod = 0;
        if (tabelaReceber.getSelectionModel().getSelectedItem() != null) {
            try {
                codvenda = tabelaReceber.getSelectionModel().getSelectedItem().getCodigo();
            } catch (Exception e) {
                codvenda = 0;
            }
            situacao = tabelaReceber.getSelectionModel().getSelectedItem().getSituacao();
        } else {
            msg.Error("Prometheus Informa", "Nenhuma venda selecionada!");
            erro = 1;
        }

        if (erro == 0) {
            if (cpe.alterar(codvenda, situacao)) {
                msg.Affirmation("Prometheus Informa", "Pedido alterado com sucesso!");
                tabelaReceber.getItems().clear();
                tabela.getItems().clear();
            } else {
                msg.Error("Prometheus Informa", "Erro ao alterar pedido!");
            }
        }

    }

    @FXML
    private void onExitCliente(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodcli.getText().isEmpty()) {
            cven.pesquisarCliente(cli, Integer.parseInt(txcodcli.getText()), txcodcli, txclinome);

        }
    }

    @FXML
    private void evtaddBuscar(ActionEvent event) {
        Mensagens msg = new Mensagens();
        int codcli = 0, erro = 0;
        String emissao = "";
        String situacao = "";
        try {
            codcli = Integer.parseInt(txcodcli.getText());
        } catch (Exception e) {
            codcli = 0;
        }
        if (!cbsitu.getSelectionModel().isEmpty()) {
            situacao = cbsitu.getSelectionModel().getSelectedItem();
        }

        if (dpinicial.getValue() != null && dpinicial.getValue().isAfter(LocalDate.now())) {
            msg.campoLabel(dpinicial, lberrodtini, "Data não pode ser maior que atual!");
            erro = 1;
        } else if (dpinicial.getValue() != null && !dpinicial.getValue().isAfter(LocalDate.now())) {
            emissao = dpinicial.getValue().toString();
        }

        if (erro == 0) {
            vendas = cpe.buscar(codcli, emissao, situacao);
            atualizarTabelaVenda();
        }

    }

    public void atualizarTabelaVenda() {
        ObservableList<Venda> prod_v;
        if (tabelaReceber.getItems() != null) {
            tabelaReceber.getItems().clear();
        }
        prod_v = FXCollections.observableArrayList(vendas);
        tabelaReceber.setItems(prod_v);
    }

    public void atualizarTabelaProduto() {
        ObservableList<Produto_Venda> prod_v;
        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }
        prod_v = FXCollections.observableArrayList(produtos);
        tabela.setItems(prod_v);
    }

    @FXML
    private void pegaProdutos(MouseEvent event) {
        int codvenda = 0;
        if (tabelaReceber.getSelectionModel().getSelectedItem() != null) {
            try {
                codvenda = tabelaReceber.getSelectionModel().getSelectedItem().getCodigo();
            } catch (Exception e) {
                codvenda = 0;
            }
            produtos = cpe.buscarProd(codvenda);
            atualizarTabelaProduto();
        }
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
                cven.pesquisarCliente(cli, 0, txcodcli, txclinome);
            }

        } catch (Exception e) {
            msg.Error("", e.getMessage());
        }
    }

    @FXML
    private void onClickProduto(MouseEvent event) {

    }

    @FXML
    private void evtLimpar(ActionEvent event) {
        dpinicial.setValue(null);
        txcodcli.setText("");
        txclinome.setText("");
        cbsitu.getSelectionModel().select(-1);
    }

    @FXML

    private void edditSitu(TableColumn.CellEditEvent<Venda, String> event) {
        if (tabelaReceber.getSelectionModel().getSelectedItem() != null) {
            if (event.getNewValue().toLowerCase().equals("r")) {
                tabelaReceber.getSelectionModel().getSelectedItem().setSituacao("Retirada Balcão");
            } else if (event.getNewValue().toLowerCase().equals("p")) {
                tabelaReceber.getSelectionModel().getSelectedItem().setSituacao("Pendente-Entrega");
            } else if (event.getNewValue().toLowerCase().equals("e")) {
                tabelaReceber.getSelectionModel().getSelectedItem().setSituacao("Entregue");
            } else {
                tabelaReceber.getSelectionModel().getSelectedItem().setSituacao(event.getNewValue());
            }

            atualizarTabelaVenda();
        }
    }

}

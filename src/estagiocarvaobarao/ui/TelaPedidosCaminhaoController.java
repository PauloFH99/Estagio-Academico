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
import estagiocarvaobarao.controller.ControllerEntrega;
import estagiocarvaobarao.controller.ControllerLancarDespesas;
import estagiocarvaobarao.controller.ControllerPedidos;
import estagiocarvaobarao.controller.ControllerVenda;
import estagiocarvaobarao.entidade.Cliente;
import estagiocarvaobarao.entidade.Despesas;
import estagiocarvaobarao.entidade.Despesas_Entrega;
import estagiocarvaobarao.entidade.Entrega;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Pedido;
import estagiocarvaobarao.entidade.Produto_Venda;
import estagiocarvaobarao.entidade.Venda;
import estagiocarvaobarao.utils.MaskFieldUtil;
import estagiocarvaobarao.utils.Mensagens;
import static java.lang.System.exit;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.Alert;
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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaPedidosCaminhaoController implements Initializable {

    @FXML
    private Pane pnbotoes;
    @FXML
    private JFXButton btncancelar;
    @FXML
    private JFXButton btnfinalizar;
    @FXML
    private Pane conteudo;
    @FXML
    private JFXTextField txcod;
    @FXML
    private DatePicker dpinicial;
    @FXML
    private Label lbini;
    @FXML
    private Label lberrodtini;
    @FXML
    private JFXButton btnnovo;
    @FXML
    private JFXButton btnexcuir;
    @FXML
    private JFXButton btnpesquisar;
    @FXML
    private TableView<Despesas_Entrega> tabelaDespesa;
    @FXML
    private TableColumn<Despesas_Entrega, String> colDescDesp;
    @FXML
    private TableColumn<Despesas_Entrega, Double> colValor;
    @FXML
    private JFXTextField txvalor;
    @FXML
    private JFXTextField txtipodes;
    @FXML
    private JFXButton btnpesquisartipodes;
    @FXML
    private JFXTextField txcoddes;
    @FXML
    private JFXComboBox<String> cbRota;
    @FXML
    private JFXButton btnadddesp;

    @FXML
    private TableView<Entrega> tabelaReceber;
    @FXML
    private TableColumn<Entrega, String> colDesc;
    @FXML
    private TableColumn<Entrega, Integer> colCarga;
    @FXML
    private TableColumn<Entrega, Integer> colCargaVenda;
    @FXML
    private TableColumn<Entrega, Integer> colRetorno;
    ControllerLancarDespesas crllancardes = new ControllerLancarDespesas();
    ControllerEntrega crentre = new ControllerEntrega();
    Mensagens msg = new Mensagens();
    public static Funcionario func;

    public static Funcionario getFunc() {
        return func;
    }

    public static void setFunc(Funcionario func) {
        TelaPedidosCaminhaoController.func = func;
    }

    public static List<Entrega> Entregas;

    public static List<Entrega> getEntregas() {
        return Entregas;
    }

    public static void setEntregas(List<Entrega> Entregas) {
        TelaPedidosCaminhaoController.Entregas = Entregas;
    }

    public static List<Despesas_Entrega> Despesas;

    public static List<Despesas_Entrega> getDespesas() {
        return Despesas;
    }

    public static void setDespesas(List<Despesas_Entrega> Despesas) {
        TelaPedidosCaminhaoController.Despesas = Despesas;
    }
    public static Despesas tipodespesas;

    public static Despesas getTipodespesas() {
        return tipodespesas;
    }

    public static void setTipodespesas(Despesas tipodespesas) {
        TelaPedidosCaminhaoController.tipodespesas = tipodespesas;
    }
    public static Entrega entrega;

    public static Entrega getEntrega() {
        return entrega;
    }

    public static void setEntrega(Entrega entrega) {
        TelaPedidosCaminhaoController.entrega = entrega;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFieldUtil.numericField(txcoddes);
        MaskFieldUtil.monetaryField(txvalor);
        colDescDesp.setCellValueFactory(new PropertyValueFactory("descri"));
        colValor.setCellValueFactory(new PropertyValueFactory("valor"));
        addButtonToTable();
        colValor.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        colDesc.setCellValueFactory(new PropertyValueFactory("produto"));
        colCarga.setCellValueFactory(new PropertyValueFactory("carga"));
        colCarga.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colCargaVenda.setCellValueFactory(new PropertyValueFactory("venda"));
        colCargaVenda.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colRetorno.setCellValueFactory(new PropertyValueFactory("retorno"));
        colRetorno.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        Despesas = new ArrayList<Despesas_Entrega>();
        Entregas = new ArrayList<Entrega>();

        estadoInicial();

    }

    public void excluirProduto(int index) {
        Despesas.remove(index);
        atualizarTabela();
    }

    public void atualizarTabela() {
        ObservableList<Despesas_Entrega> prod_v = null;
        if (tabelaDespesa.getItems() != null) {
            tabelaDespesa.getItems().clear();
        }
        prod_v = FXCollections.observableArrayList(Despesas);
        tabelaDespesa.setItems(prod_v);
    }

    public void atualizarTabelaProd() {
        ObservableList<Entrega> prod_v = null;
        if (tabelaReceber.getItems() != null) {
            tabelaReceber.getItems().clear();
        }
        prod_v = FXCollections.observableArrayList(Entregas);
        tabelaReceber.setItems(prod_v);
    }

    public void inicializarTabela() {
        ObservableList<Entrega> prod_v = null;
        if (tabelaReceber.getItems() != null) {
            tabelaReceber.getItems().clear();
            Entregas.clear();
        }
        Entregas = crentre.initTable();
        prod_v = FXCollections.observableArrayList(Entregas);
        tabelaReceber.setItems(prod_v);
    }

    private void addButtonToTable() {
        TableColumn<Despesas_Entrega, Void> colBtn = new TableColumn("Ações");

        Callback<TableColumn<Despesas_Entrega, Void>, TableCell<Despesas_Entrega, Void>> cellFactory = new Callback<TableColumn<Despesas_Entrega, Void>, TableCell<Despesas_Entrega, Void>>() {
            @Override
            public TableCell<Despesas_Entrega, Void> call(final TableColumn<Despesas_Entrega, Void> param) {
                final TableCell<Despesas_Entrega, Void> cell = new TableCell<Despesas_Entrega, Void>() {
                    Image imageDecline = new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/trash.png"));

                    private final JFXButton btn = new JFXButton("Excluir");

                    {
                        btn.setGraphic(new ImageView(imageDecline));
                        btn.setOnAction((ActionEvent event) -> {
                            Despesas_Entrega produto_venda = getTableView().getItems().get(getIndex());
                            excluirProduto(getIndex());

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tabelaDespesa.getColumns().add(colBtn);
        tabelaDespesa.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void estadoEdicao() {
        Mensagens msg = new Mensagens();
        conteudo.setDisable(false);
        tabelaDespesa.setDisable(false);
        tabelaDespesa.setVisible(true);
        btnfinalizar.setDisable(false);
        btncancelar.setDisable(false);

        txtipodes.resetValidation();
        String[] dados = {"Roxa", "Azul"};
        cbRota.setItems(FXCollections.observableArrayList(dados));
        inicializarTabela();

    }

    public void estadoInicial() {
        conteudo.setDisable(true);
        tabelaDespesa.setVisible(true);
        pnbotoes.setDisable(false);
        btnfinalizar.setDisable(true);
        btncancelar.setDisable(false);
        btnpesquisar.setDisable(false);
        btnexcuir.setDisable(true);
        dpinicial.setValue(LocalDate.now());
        tabelaDespesa.getItems().clear();
        tabelaReceber.getItems().clear();
        txcod.setText("");
        txcoddes.setText("");
        txtipodes.setText("");
        txvalor.setText("");
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
        if (!conteudo.isDisabled())//encontra em estado de edição
        {
            estadoInicial();
        } else {
            btnnovo.getScene().getWindow().hide();//fecha a janela
        }

    }

    @FXML
    private void evtFinalizar(ActionEvent event) {
        int erro = 0, cod = 0;
        String rota = "";
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        if (dpinicial.getValue() == null) {
            msg.campoLabel(dpinicial, lberrodtini, "Campo não pode estar vazio!");
            erro = 1;
        } else {
            if (dpinicial.getValue().isAfter(LocalDate.now())) {
                msg.campoLabel(dpinicial, lberrodtini, "Data não pode ser maior que atual!");
                erro = 1;
            }
        }
        if (cbRota.getSelectionModel().getSelectedItem() == null) {
            msg.campoVazioCbx(cbRota, "Campo não pode estar vazio!");
            erro = 1;
        } else {
            rota = cbRota.getSelectionModel().getSelectedItem();
        }

        if (erro == 0) {
            Entregas = tabelaReceber.getItems();
            if (crentre.finalizar(cod, Despesas, Entregas, dpinicial.getValue(), func, rota)) {
                if (cod == 0) {
                    msg.Affirmation("", "Entrega realizada com sucesso!");
                } else {
                    msg.Affirmation("", "Entrega alterada com sucesso!");
                }
                estadoInicial();
            } else {
                if (cod == 0) {
                    msg.Error("", "Erro ao gerar Entrega!");
                } else {
                    msg.Error("", "Erro ao alterar Entrega!");
                }
            }
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void onExitDespesa(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcoddes.getText().isEmpty()) {
            pesquisarTipoDespesas(null, Integer.parseInt(txcoddes.getText()));
            txvalor.requestFocus();
        }
    }

    public void pesquisarTipoDespesas(Despesas des, int cod) {
        crllancardes.pesquisarTipoDespesas(des, txcoddes, txtipodes, cod);
        txvalor.requestFocus();

    }

    @FXML
    private void evtProcurarTipoDespesa(ActionEvent event) {
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
            if (tipodespesas != null) {
                pesquisarTipoDespesas(tipodespesas, tipodespesas.getCodigo());
            }

        } catch (Exception e) {
            msg.Error("Erro ao Consultar", "Nenhuma Despesa foi selecionada!");
            exit(1);
        }
    }

    @FXML
    private void evtaddDescp(ActionEvent event) {
        int erro = 0;
        if (txcoddes.getText().isEmpty()) {
            msg.campoVazio(txtipodes, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txvalor.getText().isEmpty()) {
            msg.campoVazio(txvalor, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (erro == 0) {
            crentre.addDesp(txcoddes, txtipodes, txvalor, Despesas, tabelaDespesa);
            txcoddes.setText("");
            txtipodes.setText("");
            txvalor.setText("");
        }

    }

    @FXML
    private void ediitValor(TableColumn.CellEditEvent<Despesas_Entrega, Double> event) {
        tabelaDespesa.getSelectionModel().getSelectedItem().setValor(event.getNewValue());
        atualizarTabela();
    }

    @FXML
    private void edditCarga(TableColumn.CellEditEvent<String, Integer> event) {
        tabelaReceber.getSelectionModel().getSelectedItem().setCarga(event.getNewValue());

    }

    @FXML
    private void edditVenda(TableColumn.CellEditEvent<String, Integer> event) {
        tabelaReceber.getSelectionModel().getSelectedItem().setVenda(event.getNewValue());

    }

    @FXML
    private void edditRetorno(TableColumn.CellEditEvent<String, Integer> event) {
        tabelaReceber.getSelectionModel().getSelectedItem().setRetorno(event.getNewValue());

    }

    private void estadoEdicaoP() {
        Mensagens msg = new Mensagens();
        conteudo.setDisable(false);
        tabelaDespesa.setDisable(false);
        tabelaReceber.setVisible(true);
        String[] dados = {"Retirada Balcão", "Pendente-Entrega", "Entregue", "Entrega"};
        cbRota.setItems(FXCollections.observableArrayList(dados));
        cbRota.getSelectionModel().select(0);

    }

    public void carregarEntrega() {
        estadoEdicaoP();
        crentre.carregarEntrega(entrega, dpinicial, txcod, Despesas, Entregas,
                btnexcuir, btnfinalizar, btncancelar, btnnovo, tabelaDespesa, tabelaReceber, cbRota);
    }

    @FXML
    private void evtProcurarEntregas(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaEntregaCaminhao.fxml"));
            Scene scene = new Scene(pesquisa);
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.setTitle("Consulta de Entrega Caminhão");
            stage.showAndWait();
            if (entrega != null) {
                carregarEntrega();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        int cod = 0;
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        if (msg.Confirmation("", "Confirmar exclusão?")) {
            if (crentre.excluir(cod)) {
                msg.Affirmation("", "Entrega excluída  com sucesso!");
                estadoInicial();
            } else {
                msg.Error("", "Erro ao excluir!");
            }
        }
    }

}

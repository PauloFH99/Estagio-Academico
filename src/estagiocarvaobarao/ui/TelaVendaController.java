/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import estagiocarvaobarao.controller.ControllerVenda;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Armazem;
import estagiocarvaobarao.entidade.Cliente;
import estagiocarvaobarao.entidade.CondPagto;
import estagiocarvaobarao.entidade.ContasReceber;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.entidade.Produto_Venda;
import estagiocarvaobarao.entidade.Venda;
import estagiocarvaobarao.utils.ConsultaArmazemController;
import estagiocarvaobarao.utils.MaskFieldUtil;
import estagiocarvaobarao.utils.Mensagens;
import static java.lang.System.exit;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class TelaVendaController implements Initializable {

    @FXML
    private JFXButton btnfinalizar;
    @FXML
    private JFXButton btnnovo;
    @FXML
    private JFXTextField txcod;
    @FXML
    private DatePicker dpemissao;

    @FXML
    private JFXTextField txcodcli;
    @FXML
    private JFXButton btnpesquisarcli;
    @FXML
    private JFXTextField txclinome;
    @FXML
    private JFXTextField txcodprod;
    @FXML
    private JFXButton btnpesquisarprod;
    @FXML
    private JFXTextField txproddesc;
    @FXML
    private JFXTextField txquantidade;
    @FXML
    private JFXTextField txpreco;
    @FXML
    private JFXButton btnaddprod;
    @FXML
    private TableView<Produto_Venda> tabela;
    @FXML
    private TableColumn<Produto_Venda, Integer> colCod;
    @FXML
    private TableColumn<Produto_Venda, String> colDescri;
    @FXML
    private TableColumn<Produto_Venda, Integer> colQtde;
    @FXML
    private TableColumn<Produto_Venda, Double> colUnit;
    @FXML
    private TableColumn<Produto_Venda, Double> colTotal;

    private static Funcionario funcionario;
    private static Cliente cli;
    private static Produto prod;
    private static Armazem armazem;
    public static Produto_Venda prodv;
    public static List<Produto_Venda> Produtos;
    public static List<ContasReceber> Receber;
    public Double total;
    public static Double total_ant;
    public static String tipoFormaPagto;

    public static ContasReceber contasreceber;
    public static Venda venda;

    public static Double getTotal_ant() {
        return total_ant;
    }

    public static void setTotal_ant(Double total_ant) {
        TelaVendaController.total_ant = total_ant;
    }

    public static Armazem getArmazem() {
        return armazem;
    }

    public static void setArmazem(Armazem armazem) {
        TelaVendaController.armazem = armazem;
    }

    public static Venda getVenda() {
        return venda;
    }

    public static void setVenda(Venda venda) {
        TelaVendaController.venda = venda;
    }

    public static ContasReceber getContasreceber() {
        return contasreceber;
    }

    public static void setContasreceber(ContasReceber contasreceber) {
        TelaVendaController.contasreceber = contasreceber;
    }

    public static List<ContasReceber> getReceber() {
        return Receber;
    }

    public static void setReceber(List<ContasReceber> Receber) {
        TelaVendaController.Receber = Receber;
    }
    @FXML
    private JFXButton btnconfirmar;
    @FXML
    private RadioButton rbarmazem;
    @FXML
    private ToggleGroup estoque;
    @FXML
    private RadioButton rbcasa;
    @FXML
    private Label lberroemissao;
    @FXML
    private Label lberroqtde;
    @FXML
    private Label lberro1venc;
    @FXML
    private Label lberroqtdeparc;
    @FXML
    private Label lberrodiasentre;
    @FXML
    private Label lberrocheque;
    @FXML
    private Label lbqtdeparca;
    @FXML
    private JFXButton btnimprimir;
    @FXML
    private JFXComboBox<String> cbsitu;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public static String getTipoFormaPagto() {
        return tipoFormaPagto;
    }

    public static void setTipoFormaPagto(String tipoFormaPagto) {
        TelaVendaController.tipoFormaPagto = tipoFormaPagto;
    }

    public static int getCodCondPagto() {
        return CodCondPagto;
    }

    public static void setCodCondPagto(int CodCondPagto) {
        TelaVendaController.CodCondPagto = CodCondPagto;
    }
    public static int CodCondPagto;

    public static Funcionario getFuncionario() {
        return funcionario;
    }

    public static void setFuncionario(Funcionario funcionario) {
        TelaVendaController.funcionario = funcionario;
    }

    public static Cliente getCli() {
        return cli;
    }

    public static void setCli(Cliente cli) {
        TelaVendaController.cli = cli;
    }

    public static Produto getProd() {
        return prod;
    }

    public static void setProd(Produto prod) {
        TelaVendaController.prod = prod;
    }

    public static List<Produto_Venda> getProdutos() {
        return Produtos;
    }

    public static void setProdutos(List<Produto_Venda> Produtos) {
        TelaVendaController.Produtos = Produtos;
    }
    @FXML
    private Label lbtotal;
    @FXML
    private JFXButton btncancelar;
    @FXML
    private JFXButton btnpesquisar;
    @FXML
    private JFXButton btnexcluir;
    @FXML
    private Pane conteudodir1;
    @FXML
    private Pane conteudodir2;
    @FXML
    private DatePicker dpdatavencimento;
    @FXML
    private JFXComboBox<CondPagto> cbFormaPagto;
    @FXML
    private JFXTextField txqtdeparc;
    @FXML
    private Label lbdatatexto;
    @FXML
    private Label lbformapagtotexto;
    @FXML
    private Label lbqtdeparctexto;
    @FXML
    private Label lbdata;
    @FXML
    private FontAwesomeIcon ftformpagt;
    @FXML
    private Label lbforma;
    @FXML
    private Label lbqtdeparc;
    @FXML
    private Pane pnlinhatop;
    @FXML
    private Pane pnlinhatop1;
    @FXML
    private JFXTextField txemissorcheque;
    @FXML
    private JFXTextField txdiasentreparc;
    @FXML
    private Label lbqtdeparctexto1;
    @FXML
    private Label lbqtdeentre;
    @FXML
    private TableView<ContasReceber> tabelaReceber;
    @FXML
    private TableColumn<ContasReceber, String> colEmissao;
    @FXML
    private TableColumn<ContasReceber, String> colParcela;
    @FXML
    private TableColumn<ContasReceber, Double> colValor;
    @FXML
    private TableColumn<ContasReceber, String> colVEncimento;
    @FXML
    private TableColumn<ContasReceber, String> colDataPago;
    @FXML
    private TableColumn<ContasReceber, Double> colValorPago;
    @FXML
    private Pane pnprodutos;
    @FXML
    private Pane pnparcelas;
    @FXML
    private Pane pnbotoes;
    ControllerVenda cven = new ControllerVenda();
    public static Funcionario func;
    public static ContasReceber rec;

    public static ContasReceber getRec() {
        return rec;
    }

    public static void setRec(ContasReceber rec) {
        TelaVendaController.rec = rec;
    }

    public static Funcionario getFunc() {
        return func;
    }

    public static void setFunc(Funcionario func) {
        TelaVendaController.func = func;
    }

    Mensagens msg = new Mensagens();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        MaskFieldUtil.numericField(txcodcli);
        MaskFieldUtil.numericField(txcodprod);
        MaskFieldUtil.numericField(txquantidade);
        MaskFieldUtil.monetaryField(txpreco);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colCod.setCellValueFactory(new PropertyValueFactory("codprod"));
        colDescri.setCellValueFactory(new PropertyValueFactory("descri"));
        colQtde.setCellValueFactory(new PropertyValueFactory("qtde"));
        colUnit.setCellValueFactory(new PropertyValueFactory("unitario"));
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));
        tabela.setEditable(true);

        colQtde.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colUnit.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colTotal.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        colEmissao.setCellValueFactory(new PropertyValueFactory("emissao"));
        colParcela.setCellValueFactory(new PropertyValueFactory("parcela"));
        colValor.setCellValueFactory(new PropertyValueFactory("valor"));
        colVEncimento.setCellValueFactory(new PropertyValueFactory("vencimento"));
        colDataPago.setCellValueFactory(new PropertyValueFactory("data_pago"));
        colValorPago.setCellValueFactory(new PropertyValueFactory("valor_pago"));

        colValor.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colVEncimento.setCellFactory(TextFieldTableCell.forTableColumn());
        colDataPago.setCellFactory(TextFieldTableCell.forTableColumn());
        colValorPago.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colEmissao.setCellFactory(TextFieldTableCell.forTableColumn());

        addButtonToTable();
        dpemissao.setValue(LocalDate.now());
        Produtos = new ArrayList<Produto_Venda>();
        Receber = new ArrayList<ContasReceber>();
        estadoInicial();
    }

    public void excluirProduto(int index) {
        int codP = Produtos.get(index).getCodprod();
        int cod = 0;
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        Produtos.remove(index);
        atualizarTabela(index, codP, cod);
        calculaTotal();
        //estadoGravouProduto();
    }

    private void addButtonToTable() {
        TableColumn<Produto_Venda, Void> colBtn = new TableColumn("Ações");

        Callback<TableColumn<Produto_Venda, Void>, TableCell<Produto_Venda, Void>> cellFactory = new Callback<TableColumn<Produto_Venda, Void>, TableCell<Produto_Venda, Void>>() {
            @Override
            public TableCell<Produto_Venda, Void> call(final TableColumn<Produto_Venda, Void> param) {
                final TableCell<Produto_Venda, Void> cell = new TableCell<Produto_Venda, Void>() {
                    Image imageDecline = new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/trash.png"));

                    private final JFXButton btn = new JFXButton("Excluir");

                    {
                        btn.setGraphic(new ImageView(imageDecline));
                        btn.setOnAction((ActionEvent event) -> {
                            Produto_Venda produto_venda = getTableView().getItems().get(getIndex());
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

        tabela.getColumns().add(colBtn);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    private void evtNovo(ActionEvent event) {
        estadoEdicao();
    }

    private void estadoInicial() {
        conteudodir1.setDisable(true);
        conteudodir2.setDisable(true);
        pnprodutos.setDisable(true);
        tabela.setVisible(false);
        tabelaReceber.setVisible(false);
        pnparcelas.setDisable(true);
        pnbotoes.setDisable(false);
        btnfinalizar.setDisable(true);
        btnpesquisar.setDisable(false);
        btncancelar.setDisable(false);
        btnnovo.setDisable(false);

        btnexcluir.setDisable(true);
        dpemissao.setValue(null);

        Produtos.clear();
        if (tabela != null) {
            tabela.getItems().clear();
        }
        if (tabelaReceber != null) {
            tabelaReceber.getItems().clear();
        }
        ObservableList<Node> componentes = conteudodir1.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
        }
        ObservableList<Node> componentes2 = conteudodir2.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
        }
        txcodcli.resetValidation();
        txcodprod.resetValidation();
        if (txdiasentreparc.isVisible()) {
            txdiasentreparc.resetValidation();
        }
        if (txemissorcheque.isVisible()) {
            txemissorcheque.resetValidation();
        }
        if (txqtdeparc.isVisible()) {
            txqtdeparc.resetValidation();
        }
        if (txqtdeparc.isVisible()) {
            txqtdeparc.resetValidation();
        }
        txquantidade.resetValidation();
        cbFormaPagto.resetValidation();
        txemissorcheque.setText("");
        txqtdeparc.setText("");
        txdiasentreparc.setText("");
        limparLabel();
        lbdata.setVisible(false);
        ftformpagt.setVisible(false);
        lbforma.setVisible(false);
        lbqtdeparc.setVisible(false);
        lbqtdeentre.setVisible(false);
        lbtotal.setText("0.00");
        txemissorcheque.setVisible(false);
        txdiasentreparc.setVisible(false);
        txqtdeparc.setVisible(false);
        dpemissao.setValue(LocalDate.now());
        dpdatavencimento.setValue(LocalDate.now());
        cven.carregarNivel(cbFormaPagto);
    }

    private void estadoEdicao() {
        Mensagens msg = new Mensagens();
        conteudodir1.setDisable(false);
        conteudodir2.setDisable(false);
        pnprodutos.setDisable(false);
        tabela.setDisable(false);
        tabela.setVisible(true);
        pnparcelas.setDisable(false);
        btnfinalizar.setDisable(false);
        btncancelar.setDisable(false);
        btnpesquisar.setDisable(false);
        btnexcluir.setDisable(true);
        btnnovo.setDisable(false);
        String[] dados = {"Retirada Balcão", "Pendente-Entrega", "Entregue","Entrega"};
        cbsitu.setItems(FXCollections.observableArrayList(dados));
        cbsitu.getSelectionModel().select(0);

    }

    private void estadoEdicaoP() {
        Mensagens msg = new Mensagens();
        conteudodir1.setDisable(false);
        conteudodir2.setDisable(false);
        pnprodutos.setDisable(false);
        tabela.setDisable(false);
        tabela.setVisible(true);
        pnparcelas.setDisable(false);
        String[] dados = {"Retirada Balcão", "Pendente-Entrega", "Entregue","Entrega"};
        cbsitu.setItems(FXCollections.observableArrayList(dados));
        cbsitu.getSelectionModel().select(0);

    }

    public void limpaProd() {
        txcodprod.setText("");
        txproddesc.setText("");
        txpreco.setText("");
    }

    @FXML
    private void onExitCliente(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodcli.getText().isEmpty()) {
            cven.pesquisarCliente(cli, Integer.parseInt(txcodcli.getText()), txcodcli, txclinome);
            limpaProd();
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
    private void onExitProduto(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodprod.getText().isEmpty() && !txcodcli.getText().isEmpty()) {
            cven.pesquisarProduto(armazem, Integer.parseInt(txcodprod.getText()), txcodprod, txproddesc, txpreco, txquantidade, Integer.parseInt(txcodcli.getText()));
        }
    }

    @FXML
    private void evtProcurarProduto(ActionEvent event) {
        Mensagens msg = new Mensagens();
        int codcli = 0;
        try {
            Stage stage = new Stage();
            Parent pesquisa = null;
            try {
                codcli = Integer.parseInt(txcodcli.getText());
            } catch (Exception e) {
                codcli = 0;
            }

            txclinome.resetValidation();
            if (rbarmazem.isSelected()) {
                ConsultaArmazemController.setFlag(1);
                ConsultaArmazemController.setCod_cli(codcli);
                pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaArmazem.fxml"));
            }
            if (pesquisa != null) {
                Scene scene = new Scene(pesquisa);
                //Show main Window
                stage.setScene(scene);
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
                stage.setResizable(false);
                stage.setTitle("Consulta de Produtos");
                stage.showAndWait();
                if (armazem != null) {
                    cven.pesquisarProduto(armazem, 0, txcodprod, txproddesc, txpreco, txquantidade, codcli);

                }
            }

        } catch (Exception e) {
            msg.Error("", e.getMessage());
        }

    }

    public void atualizarTabela(int index, int codP, int cod) {
        ObservableList<Produto_Venda> prod_v = null;
        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }
        prod_v = FXCollections.observableArrayList(Produtos);
        if (cod != 0) {
            cven.excluirProd(cod, codP);
        }
        tabela.setItems(prod_v);
    }

    public void atualizarTabela() {
        ObservableList<Produto_Venda> prod_v = null;
        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }
        prod_v = FXCollections.observableArrayList(Produtos);
        tabela.setItems(prod_v);
    }

    public void atualizarTabelaReceber() {
        ObservableList<ContasReceber> prod_rec = null;
        if (tabelaReceber.getItems() != null) {
            tabelaReceber.getItems().clear();
        }
        prod_rec = FXCollections.observableArrayList(Receber);
        tabelaReceber.setItems(prod_rec);
    }

    public void calculaTotal() {
        total = 0.0;
        for (Produto_Venda Produto : Produtos) {
            total += Produto.getQtde() * Produto.getUnitario();
        }

        lbtotal.setText(total.toString());

    }

    @FXML
    private void evtaddProd(ActionEvent event) {
        int erro = 0;
        String estoque = "";
        limparLabel();
        if (rbarmazem.isSelected()) {
            estoque = "a";
        } else {
            estoque = "c";
        }

        if (txcodprod.getText().isEmpty()) {
            msg.campoVazio(txproddesc, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (txquantidade.getText().isEmpty()) {
            msg.campoVazio(txquantidade, "Campo não pode estar vazio!");
            erro = 1;
        } else {
            if (Integer.parseInt(txquantidade.getText()) == 0) {
                msg.campoLabel(txquantidade, lberroqtde, "Campo não pode ser 0!");
                erro = 1;
            }
            if (Integer.parseInt(txquantidade.getText()) < 0) {
                msg.campoLabel(txquantidade, lberroqtde, "Campo não pode ser negativo!");
                erro = 1;
            }
            if (Integer.parseInt(txquantidade.getText()) > 0) {
                if (cven.verificaEstoque(txcodprod, txquantidade, estoque, Produtos) == false) {
                    erro = 1;
                }
            }

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
        if (erro == 0) {
            cven.addProduto(txcodprod, txproddesc, txquantidade, txpreco, Produtos, tabela, lbtotal, total);
        }

    }

    @FXML
    private void evtFinalizar(ActionEvent event) throws ParseException {
        int cod = 0, erro = 0;
        String estoque = "";
        String situacao = "";
        limparLabel();
        if (rbarmazem.isSelected()) {
            estoque = "a";
        } else {
            estoque = "c";
        }
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }

        if (Produtos.isEmpty()) {
            erro = 1;
            msg.Error("Prometheus Informa", "Nenhum produto selecionado!");
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
        if (cbsitu.getSelectionModel().isEmpty()) {
            msg.campoVazioCbx(cbsitu, "Campo não pode estar vazio!");
            erro = 1;
        } else {
            situacao = cbsitu.getSelectionModel().getSelectedItem();
        }
        if (cbFormaPagto.getSelectionModel().isEmpty()) {
            msg.campoVazioCbx(cbFormaPagto, "Campo não pode estar vazio!");
            erro = 1;
        } else {
            if (tipoFormaPagto.equals("a prazo")) {
                if (txdiasentreparc.getText().isEmpty()) {
                    msg.campoLabel(txdiasentreparc, lberrodiasentre, "Campo não pode estar vazio!");
                    erro = 1;
                } else if (Integer.parseInt(txdiasentreparc.getText()) == 0) {
                    msg.campoLabel(txdiasentreparc, lberrodiasentre, "Campo não pode ser 0!");
                    erro = 1;
                } else if (Integer.parseInt(txdiasentreparc.getText()) < 0) {
                    msg.campoLabel(txdiasentreparc, lberrodiasentre, "Campo não pode ser negativo!");
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

                if (txemissorcheque.getText().isEmpty()) {
                    msg.campoVazio(txemissorcheque, "");
                    erro = 1;
                }

            }
            if (!txcodcli.getText().isEmpty() && tipoFormaPagto.equals("fiado")) {
                if (!cven.verificaSaldo(Integer.parseInt(txcodcli.getText()), Double.parseDouble(lbtotal.getText()))) {
                    erro = 1;
                    msg.Error("Prometheus Informa", "O cliente " + txclinome.getText() + " não possui limite para essa venda!");
                }
            } else {
                Date vencimento;
                if (Receber != null && !Receber.isEmpty()) {
                    for (int i = 0; i < Receber.size(); i++) {
                        vencimento = Receber.get(i).getVencimentoDate();
                        if (vencimento.before(Receber.get(i).getEmissaoDate())) {
                            erro = 1;
                            msg.Error("Prometheus Informa", "Data de vencimento não pode ser anterior a de emissão!");
                            i = Receber.size();
                        }
                    }
                    if (cven.calculaTotalVenda(Receber, Double.parseDouble(lbtotal.getText())) == false) {
                        erro = 1;
                        msg.Error("Prometheus Informa", "Somatório das parcelas é maior/menor que total da venda!!");
                    }
                }
            }
            if (!tipoFormaPagto.equals("fiado") && Receber.isEmpty()) {
                erro = 1;
                msg.Error("Prometheus Informa", "Nenhuma parcela gerada!");
            }

        }
        if (dpdatavencimento.getValue() == null) {
            msg.campoLabel(dpdatavencimento, lberro1venc, "Campo não pode estar vazio!");
            erro = 1;
        }

        if (erro == 0) {
            if (cven.finalizar(Double.parseDouble(lbtotal.getText()), txcod, lbtotal, tipoFormaPagto, cbFormaPagto, txqtdeparc,
                    contasreceber, txcodcli, Produtos, dpemissao, txdiasentreparc, tabelaReceber, tabela, func,
                    txemissorcheque, estoque, func, situacao)) {
                if (cod == 0) {
                    msg.Affirmation("", "Venda realizada com sucesso!");
                } else {
                    msg.Affirmation("", "Venda alterada com sucesso!");
                }
                estadoInicial();
            } else {
                if (cod == 0) {
                    msg.Error("", "Erro ao gerar Venda!");
                } else {
                    msg.Error("", "Erro ao alterar Venda!");
                }
            }
        }
    }

    @FXML
    private void onClickProduto(MouseEvent event) {
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        if (!conteudodir1.isDisabled())//encontra em estado de edição
        {
            estadoInicial();
        } else {
            btnnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    @FXML
    private void evtExcluir(ActionEvent event) {

        if (cven.excluir(venda, Receber, tabelaReceber)) {
            estadoInicial();
        }

    }

    @FXML
    private void evtImprimir(ActionEvent event) {
    }

    public boolean verificaBoleto(String num) {
        String aux = "";
        aux = num.replaceAll(".", "");
        for (int i = 0; i < aux.length(); i++) {
            if (Integer.parseInt(String.valueOf(aux.charAt(i))) < 0) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void evtConfirmarCond(ActionEvent event) {
        int erro = 0, cod = 0;
        limparLabel();
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        if (cbFormaPagto.getSelectionModel().isEmpty()) {
            msg.campoVazioCbx(cbFormaPagto, "Campo não pode estar vazio!");
            erro = 1;
        } else {
            tipoFormaPagto = cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao();
            if (tipoFormaPagto.equals("a prazo")) {
                if (txdiasentreparc.getText().isEmpty()) {
                    msg.campoLabel(txdiasentreparc, lberrodiasentre, "Campo não pode estar vazio!");
                    erro = 1;
                } else if (Integer.parseInt(txdiasentreparc.getText()) == 0) {
                    msg.campoLabel(txdiasentreparc, lberrodiasentre, "Campo não pode ser 0!");
                    erro = 1;
                } else if (Integer.parseInt(txdiasentreparc.getText()) < 0) {
                    msg.campoLabel(txdiasentreparc, lberrodiasentre, "Campo não pode ser negativo!");
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
            if (tipoFormaPagto.equals("à vista")) {
                limparLabelQtde();
            }
            if (tipoFormaPagto.equals("boleto")) {
                if (txqtdeparc.getText().isEmpty()) {
                    msg.campoLabel(txqtdeparc, lberroqtdeparc, "Campo não pode estar vazio!");
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

                if (txemissorcheque.getText().isEmpty()) {
                    msg.campoLabel(txemissorcheque, lberrocheque, "Campo não pode estar vazio!");
                    erro = 1;
                }

            }

        }
        if (dpdatavencimento.getValue() == null) {
            msg.campoLabel(dpdatavencimento, lberro1venc, "Campo não pode estar vazio!");
            erro = 1;
        }
        if (erro == 0) {
            limparLabel();
            cven.confirmarPag(tipoFormaPagto, cbFormaPagto, lbqtdeparc, lbqtdeentre,
                    lbdata, txqtdeparc, lbforma, ftformpagt, txdiasentreparc, dpdatavencimento,
                    cod, Double.parseDouble(lbtotal.getText()), dpemissao, tabelaReceber, txemissorcheque);

        }
    }

    public void limparLabel() {
        lberro1venc.setText("");
        lberrodiasentre.setText("");
        lberroemissao.setText("");
        lberroqtde.setText("");
        lberroqtdeparc.setText("");
        lberrocheque.setText("");
        lbqtdeparc.setText("");
        lbqtdeentre.setText("");
        txclinome.resetValidation();
        txproddesc.resetValidation();
    }

    public void limparLabelQtde() {
        lberro1venc.setText("");
        lberrodiasentre.setText("");
        lberroemissao.setText("");
        lberroqtde.setText("");
        lberroqtdeparc.setText("");
        lberrocheque.setText("");
        lbqtdeparc.setText("");
        lbqtdeentre.setText("");
        txemissorcheque.setText("");
        txqtdeparc.setText("");
        txdiasentreparc.setText("");
    }

    @FXML
    private void pegarForma(ActionEvent event) {
        limparLabel();

        if (cbFormaPagto.getSelectionModel().getSelectedItem() != null) {
            tipoFormaPagto = cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao();
        }
        if (!tipoFormaPagto.isEmpty()) {
            if (tipoFormaPagto.equals("cheque")) {
                limparLabel();
                txemissorcheque.setVisible(true);
                txqtdeparc.setVisible(false);
                txdiasentreparc.setVisible(false);

            } else if (tipoFormaPagto.equals("fiado")) {
                limparLabel();
                limparLabelQtde();
                txemissorcheque.setVisible(false);
                txqtdeparc.setVisible(false);
                txdiasentreparc.setVisible(false);
                if (Receber != null) {
                    Receber.clear();
                }
            } else if (tipoFormaPagto.equals("à vista")
                    || tipoFormaPagto.equals("débito")) {
                limparLabel();
                limparLabelQtde();
                txemissorcheque.setVisible(false);
                txqtdeparc.setVisible(false);
                txdiasentreparc.setVisible(false);
            } else if (tipoFormaPagto.equals("fixa")) {
                txemissorcheque.setVisible(false);
                txqtdeparc.setVisible(true);
                limparLabel();
                txqtdeparc.setPromptText("Quantidade de Meses *");
                txdiasentreparc.setVisible(false);
            } else if (tipoFormaPagto.equals("boleto")) {
                txemissorcheque.setVisible(true);
                txqtdeparc.setVisible(true);
                limparLabel();
                txqtdeparc.setPromptText("Número Boleto *");
                txdiasentreparc.setVisible(false);
            } else {
                limparLabel();
                txemissorcheque.setVisible(false);
                txqtdeparc.setVisible(true);
                txqtdeparc.setPromptText("Quantidade de Parcelas *");
                txdiasentreparc.setVisible(true);
            }
        }
    }

    public void atualizarReceber() {
        try {
            tabelaReceber.setVisible(true);
            ObservableList<ContasReceber> contr;
            contr = FXCollections.observableArrayList(Receber);
            tabelaReceber.setItems(contr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void carregarVenda() throws ParseException {
        estadoEdicaoP();
        cven.carregarVenda(venda, dpemissao, txcodcli, txclinome, Produtos, cbFormaPagto, txqtdeparc, txdiasentreparc, Receber,
                btnexcluir, btnfinalizar, btncancelar, btnnovo, tabela, tabelaReceber, total, lbtotal,
                txemissorcheque, txcod, dpdatavencimento, cbsitu);
    }

    @FXML
    private void evtProcurarVendas(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            Stage stage = new Stage();
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaVenda.fxml"));
            Scene scene = new Scene(pesquisa);
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.setTitle("Consulta de Vendas");
            stage.showAndWait();
            if (venda != null) {
                carregarVenda();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }

    @FXML
    private void editQtde(TableColumn.CellEditEvent<Produto_Venda, Integer> event) {

        prodv = tabela.getSelectionModel().getSelectedItem();
        String estoque = "";
        String num_boleto = "";
        if (rbarmazem.isSelected()) {
            estoque = "a";
        } else {
            estoque = "c";
        }
        if (cbFormaPagto.getSelectionModel().getSelectedItem().equals("boleto")) { //<-- Cheque/Boleto
            num_boleto = txqtdeparc.getText();
        }
        if (cven.verificaEstoque(event.getNewValue(), estoque, prodv.getCodprod(), prodv.getQtde()) == false) {
            atualizarTabela();
        } else {
            prodv.setQtde(event.getNewValue());
            prodv.setTotal(prodv.getQtde() * prodv.getUnitario());
            atualizarTabela();
            calculaTotal();
            if (Receber != null && !Receber.isEmpty() && !cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao().equals("fiado")) {
                int qtde = 0, cod = 0;
                try {
                    cod = Integer.parseInt(txcod.getText());
                } catch (Exception e) {
                    cod = 0;
                }
                try {
                    if (!cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao().equals("cheque")) {
                        qtde = Integer.parseInt(txqtdeparc.getText());
                    }
                } catch (Exception e) {
                    qtde = 0;
                }
                if (cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao().equals("à vista") || cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao().equals("boleto")
                        || cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao().equals("cheque")) {
                    qtde = 1;
                }
                try {
                    Receber.clear();
                    Receber = cven.gerarParcelas(cbFormaPagto.getSelectionModel().getSelectedItem(), Double.parseDouble(lbtotal.getText()),
                            dpemissao, dpdatavencimento, txdiasentreparc, qtde, cod, num_boleto);
                    atualizarReceber();
                } catch (ParseException ex) {
                    Logger.getLogger(TelaVendaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao().equals("fiado")) {
                if (Receber != null) {
                    Receber.clear();
                }
                atualizarReceber();
            }
        }
    }

    @FXML
    private void editUnitario(TableColumn.CellEditEvent<Produto_Venda, Double> event) {

        prodv = tabela.getSelectionModel().getSelectedItem();
        prodv.setUnitario(event.getNewValue());
        atualizarTabela();
        calculaTotal();
    }

    @FXML
    private void editTotal(TableColumn.CellEditEvent<Produto_Venda, Double> event) {

        prodv = tabela.getSelectionModel().getSelectedItem();
        prodv.setTotal(event.getNewValue());
        atualizarTabela();
        calculaTotal();

    }

    @FXML
    private void editEmissao(TableColumn.CellEditEvent<ContasReceber, String> event) {
        //R= tabela.getItems();
        rec = tabelaReceber.getSelectionModel().getSelectedItem();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date emissao;
        try {
            emissao = (Date) formatter.parse(event.getNewValue());
            rec.setEmissao(emissao);
            atualizarTabelaReceber();
        } catch (ParseException ex) {
            Logger.getLogger(TelaLancarDespesasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editValor(TableColumn.CellEditEvent<ContasReceber, Double> event) {
        //Produtos= tabela.getItems();
        rec = tabelaReceber.getSelectionModel().getSelectedItem();
        rec.setValor(event.getNewValue());
        if (rec.getCondicaopagamento().getDescricao().equals("à vista") && rec.getParcela().equals("1/1")) {
            rec.setValor_pago(event.getNewValue());
        }
        atualizarTabelaReceber();

    }

    @FXML
    private void editVencimento(TableColumn.CellEditEvent<ContasReceber, String> event) {
        Produtos = tabela.getItems();
        rec = tabelaReceber.getSelectionModel().getSelectedItem();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date vencimento;
        try {
            vencimento = (Date) formatter.parse(event.getNewValue());
            if (!vencimento.before(rec.getEmissaoDate())) {
                rec.setVencimento(vencimento);
                atualizarTabelaReceber();
            } else {
                msg.Error("Prometheus Informa", "Data de vencimento não pode ser anterior a de emissão!");
                atualizarTabelaReceber();
            }
        } catch (ParseException ex) {
            Logger.getLogger(TelaLancarDespesasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editDataPago(TableColumn.CellEditEvent<ContasReceber, String> event) {

        rec = tabelaReceber.getSelectionModel().getSelectedItem();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date datapago;
        try {
            datapago = (Date) formatter.parse(event.getNewValue());
            rec.setData_pago(datapago);
            atualizarTabelaReceber();
        } catch (ParseException ex) {
            Logger.getLogger(TelaLancarDespesasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editValorPago(TableColumn.CellEditEvent<ContasReceber, Double> event) {
        rec = tabelaReceber.getSelectionModel().getSelectedItem();
        rec.setValor_pago(event.getNewValue());
        atualizarTabelaReceber();
    }

    @FXML
    private void preencheBanco(KeyEvent event) {
        if (cbFormaPagto.getSelectionModel().getSelectedItem() != null
                && cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao().toLowerCase().equals("cheque")) {
            if (event.getCode() == KeyCode.F1) {
                txemissorcheque.setText("");
                txemissorcheque.setText("Banco do Brasil S.A");
            } else if (event.getCode() == KeyCode.F2) {
                txemissorcheque.setText("");
                txemissorcheque.setText("Sicoob ");
            }
        }
    }

}

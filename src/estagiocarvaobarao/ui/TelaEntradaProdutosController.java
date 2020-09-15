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
import estagiocarvaobarao.controller.ControllerEntradaProdutos;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Armazem;

import estagiocarvaobarao.entidade.CondPagto;
import estagiocarvaobarao.entidade.ContasPagar;

import estagiocarvaobarao.entidade.EntradaProdutos;
import estagiocarvaobarao.entidade.Fornecedor;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.entidade.Produto_Entrada;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
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
public class TelaEntradaProdutosController implements Initializable {

    @FXML
    private JFXButton btnfinalizar;
    @FXML
    private JFXButton btnnovo;
    @FXML
    private JFXTextField txcod;
    @FXML
    private DatePicker dpemissao;

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
    private TableView<Produto_Entrada> tabela;
    @FXML
    private TableColumn<Produto_Entrada, Integer> colCod;
    @FXML
    private TableColumn<Produto_Entrada, String> colDescri;
    @FXML
    private TableColumn<Produto_Entrada, Integer> colQtde;
    @FXML
    private TableColumn<Produto_Entrada, Double> colUnit;
    @FXML
    private TableColumn<Produto_Entrada, Double> colTotal;

    public static Funcionario funcionario;
    private static Fornecedor forn;
    private static Produto prod;
    private static Armazem armazem;
    public static List<Produto_Entrada> Produtos;
    public static List<ContasPagar> ReceberE;
    public Double total;
    public static String tipoFormaPagto;
    public static ContasPagar contaspagar;
    public static EntradaProdutos entradaproduto;
    ControllerEntradaProdutos centra = new ControllerEntradaProdutos();

    public static Armazem getArmazem() {
        return armazem;
    }

    public static void setArmazem(Armazem armazem) {
        TelaEntradaProdutosController.armazem = armazem;
    }

    public static EntradaProdutos getEntradaproduto() {
        return entradaproduto;
    }

    public static void setEntradaproduto(EntradaProdutos entradaproduto) {
        TelaEntradaProdutosController.entradaproduto = entradaproduto;
    }

    public static ContasPagar getContasreceber() {
        return contaspagar;
    }

    public static void setContasreceber(ContasPagar contaspagar) {
        TelaEntradaProdutosController.contaspagar = contaspagar;
    }

    public static List<ContasPagar> getReceber() {
        return ReceberE;
    }

    public static void setReceber(List<ContasPagar> Receber) {
        TelaEntradaProdutosController.ReceberE = Receber;
    }
    @FXML
    private JFXButton btnconfirmar;
    @FXML
    private JFXTextField txcodfor;
    @FXML
    private JFXButton btnpesquisarfor;
    @FXML
    private JFXTextField txfornome;
    @FXML
    private Label lberrofornecedor;
    @FXML
    private Label lberroproduto;
    @FXML
    private Label lberroqtdeparc;
    @FXML
    private Label lberroentredias;
    @FXML
    private Label lberro1venc;
    @FXML
    private Label lberroemissao;
    @FXML
    private Label lberroqtde;
    @FXML
    private JFXButton btnimprimir;

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
        TelaEntradaProdutosController.tipoFormaPagto = tipoFormaPagto;
    }

    public static int getCodCondPagto() {
        return CodCondPagto;
    }

    public static void setCodCondPagto(int CodCondPagto) {
        TelaEntradaProdutosController.CodCondPagto = CodCondPagto;
    }
    public static int CodCondPagto;

    public static Funcionario getFuncionario() {
        return funcionario;
    }

    public static void setFuncionario(Funcionario funcionario) {
        TelaEntradaProdutosController.funcionario = funcionario;
    }

    public static Fornecedor getForn() {
        return forn;
    }

    public static void setForn(Fornecedor forn) {
        TelaEntradaProdutosController.forn = forn;
    }

    public static Produto getProd() {
        return prod;
    }

    public static void setProd(Produto prod) {
        TelaEntradaProdutosController.prod = prod;
    }

    public static List<Produto_Entrada> getProdutos() {
        return Produtos;
    }

    public static void setProdutos(List<Produto_Entrada> Produtos) {
        TelaEntradaProdutosController.Produtos = Produtos;
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
    private TableView<ContasPagar> tabelaReceber;
    @FXML
    private TableColumn<ContasPagar, String> colEmissao;
    @FXML
    private TableColumn<ContasPagar, String> colParcela;
    @FXML
    private TableColumn<ContasPagar, Double> colValor;
    @FXML
    private TableColumn<ContasPagar, String> colVEncimento;
    @FXML
    private TableColumn<ContasPagar, String> colDataPago;
    @FXML
    private TableColumn<ContasPagar, Double> colValorPago;
    @FXML
    private Pane pnprodutos;
    @FXML
    private Pane pnparcelas;
    @FXML
    private Pane pnbotoes;
    Mensagens msg = new Mensagens();
    public static Produto_Entrada prode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        MaskFieldUtil.numericField(txcodfor);
        MaskFieldUtil.numericField(txcodprod);
        MaskFieldUtil.numericField(txquantidade);
        MaskFieldUtil.monetaryField(txpreco);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colCod.setCellValueFactory(new PropertyValueFactory("codprod"));
        colDescri.setCellValueFactory(new PropertyValueFactory("descri"));
        colQtde.setCellValueFactory(new PropertyValueFactory("qtde"));
        colUnit.setCellValueFactory(new PropertyValueFactory("unitario"));
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));

        colQtde.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        addButtonToTable();

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

        dpemissao.setValue(LocalDate.now());
        Produtos = new ArrayList<Produto_Entrada>();
        ReceberE = new ArrayList<ContasPagar>();
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
        atualizarTabelaP(index, codP, cod);
        centra.calculaTotal(total, lbtotal, Produtos);
        //estadoGravouProduto();
    }

    private void addButtonToTable() {
        TableColumn<Produto_Entrada, Void> colBtn = new TableColumn("Ações");

        Callback<TableColumn<Produto_Entrada, Void>, TableCell<Produto_Entrada, Void>> cellFactory = new Callback<TableColumn<Produto_Entrada, Void>, TableCell<Produto_Entrada, Void>>() {
            @Override
            public TableCell<Produto_Entrada, Void> call(final TableColumn<Produto_Entrada, Void> param) {
                final TableCell<Produto_Entrada, Void> cell = new TableCell<Produto_Entrada, Void>() {
                    Image imageDecline = new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/trash.png"));

                    private final JFXButton btn = new JFXButton("Excluir");

                    {
                        btn.setGraphic(new ImageView(imageDecline));
                        btn.setOnAction((ActionEvent event) -> {
                            Produto_Entrada produto_entrada = getTableView().getItems().get(getIndex());
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

    public void carregarNivel() {
        CondPagto condpagto = new CondPagto();
        List<CondPagto> ListaCond = condpagto.get();//Lista de cond
        cbFormaPagto.setItems(FXCollections.observableArrayList(ListaCond));
        cbFormaPagto.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void evtFinalizar(ActionEvent event) throws ParseException {
        int cod = 0, erro = 0;
        limparLabel();
        Mensagens msg = new Mensagens();

        boolean fiado = false;
        CondPagto condpagto = new CondPagto();
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

                if (txemissorcheque.getText().isEmpty()) {
                    msg.campoVazio(txemissorcheque, "");
                    erro = 1;
                }

            }
            Date vencimento;
            if (ReceberE != null && !ReceberE.isEmpty()) {
                for (int i = 0; i < ReceberE.size(); i++) {
                    vencimento = ReceberE.get(i).getVencimentoDate();
                    if (vencimento.before(ReceberE.get(i).getEmissaoDate())) {
                        erro = 1;
                        msg.Error("Prometheus Informa", "Data de vencimento não pode ser anterior a de emissão!");
                        i = ReceberE.size();
                    }
                }
                if (centra.calculaTotalVenda(ReceberE, Double.parseDouble(lbtotal.getText())) == false) {
                    erro = 1;
                    msg.Error("Prometheus Informa", "Somatório das parcelas é maior/menor que total da venda!!");
                }
            }
        }
        if (dpdatavencimento.getValue() == null) {
            msg.campoLabel(dpdatavencimento, lberro1venc, "Campo não pode estar vazio!");
            erro = 1;
        }

        if (erro == 0) {
            if (centra.finalizar(Double.parseDouble(lbtotal.getText()), txcod, lbtotal, tipoFormaPagto, cbFormaPagto, txqtdeparc,
                    contaspagar, txcodfor, Produtos, dpemissao, txdiasentreparc, tabelaReceber, tabela, funcionario, txemissorcheque)) {
                if (cod == 0) {
                    msg.Affirmation("", "Compra realizada com sucesso!");
                } else {
                    msg.Affirmation("", "Compra alterada com sucesso!");
                }
                tabelaReceber.setVisible(true);
                tabelaReceber.setItems(null);
                tabelaReceber.setItems(FXCollections.observableArrayList(ReceberE));
                estadoInicial();
            } else {
                if (cod == 0) {
                    msg.Error("", "Erro ao gerar compra!");
                } else {
                    msg.Error("", "Erro ao alterar compra!");
                }

            }

        }
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
        txqtdeparc.setText("");
        txdiasentreparc.setText("");
        txemissorcheque.setText("");
        dpdatavencimento.setValue(LocalDate.now());
        limparLabel();
        Produtos.clear();
        carregarNivel();
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
        txcodfor.requestFocus();

    }

    public void limpaProd() {
        txcodprod.setText("");
        txproddesc.setText("");
        txpreco.setText("");
    }

    private void pesquisarFornecedor(Fornecedor forn) {
        limpaProd();
        centra.pesquisaFornecedor(forn, txcodfor, txfornome);

    }

    @FXML
    private void evtProcurarFornecedor(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            Stage stage = new Stage();
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaFornecedor.fxml"));
            Scene scene = new Scene(pesquisa);
            stage.setTitle("Consulta de Fornecedor");
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
            if (forn != null) {
                pesquisarFornecedor(forn);
            }

        } catch (Exception e) {
            msg.Error("Erro ao Consultar", "Nenhum Fornecedor foi selecionado!");
            exit(1);
        }
    }

    @FXML
    private void onExitFornecedor(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodfor.getText().isEmpty()) {
            centra.pesquisaFornecedor(forn, txcodfor, txfornome);
            limpaProd();
        }
    }

    public void pesquisarProduto(Produto prod) {
        centra.pesquisaProduto(armazem, txcodprod, txproddesc, txquantidade, txpreco, txcodfor);

    }

    @FXML
    private void onExitProduto(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodprod.getText().isEmpty() && !txcodfor.getText().isEmpty()) {
            centra.pesquisaProduto(armazem, txcodprod, txproddesc, txquantidade, txpreco, txcodfor);
        }
    }

    @FXML
    private void evtProcurarProduto(ActionEvent event) {
        Mensagens msg = new Mensagens();
        int codfor = 0;
        try {
            try {
                codfor = Integer.parseInt(txcodfor.getText());
            } catch (Exception e) {
                codfor = 0;
            }
            if (codfor != 0) {
                txfornome.resetValidation();
                Stage stage = new Stage();
                ConsultaArmazemController.setCod_for(codfor);
                ConsultaArmazemController.setFlag(0);
                Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaArmazem.fxml"));
                Scene scene = new Scene(pesquisa);
                //Show main Window
                stage.setScene(scene);
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
                stage.setResizable(false);
                stage.setTitle("Consulta de Produtos");
                stage.showAndWait();
                if (armazem != null) {
                    pesquisarProduto(armazem.getProduto());

                }
            } else {
                msg.campoVazio(txfornome, "");
            }

        } catch (Exception e) {
            msg.Error("", e.getMessage());
        }

    }

    public void atualizarTabela() {
        ObservableList<Produto_Entrada> prod_v;
        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }

        prod_v = FXCollections.observableArrayList(Produtos);
        tabela.setItems(prod_v);
    }

    public void atualizarTabelaP(int index, int codP, int cod) {
        ObservableList<Produto_Entrada> prod_v;
        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }

        prod_v = FXCollections.observableArrayList(Produtos);
        if (cod != 0) {
            centra.excluirProd(cod, codP);
        }
        tabela.setItems(prod_v);
    }

    @FXML
    private void evtaddProd(ActionEvent event) {
        int erro = 0;
        String estoque = "";
        limparLabel();

        if (txcodfor.getText().isEmpty()) {
            msg.campoVazio(txfornome, "Campo não pode estar vazio!");
            erro = 1;
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
            centra.addProduto(txcodprod, txproddesc, txquantidade, txpreco, Produtos, tabela, lbtotal, total);

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
        Mensagens msg = new Mensagens();

        if (entradaproduto == null) {
            msg.Error("", "Nenhum compra informada");
            exit(1);
        } else {
            if (msg.Confirmation("", "Confirmar exclusão?")) {
                if (!entradaproduto.verificarParcelaPaga(entradaproduto.getCodigo())) {
                    if (entradaproduto.apagar(entradaproduto.getCodigo(), Produtos)) {
                        msg.Affirmation("", "Compra excluída com sucesso.");

                        if (ReceberE != null || !ReceberE.isEmpty()) {

                            ReceberE.clear();
                            atualizarReceber();
                        }
                        estadoInicial();
                    }
                } else {
                    msg.Error("", "Há parcelas pagas para esta compra.");
                    // exit(1);
                }
            }
        }
    }

    @FXML
    private void evtImprimir(ActionEvent event) {
    }

    @FXML
    private void evtConfirmarCond(ActionEvent event) {
        int erro = 0, cod = 0;
        tipoFormaPagto = cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao();
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

                if (txemissorcheque.getText().isEmpty()) {
                    msg.campoVazio(txemissorcheque, "");
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
            lbdata.setVisible(true);
            lbdata.setText(dpdatavencimento.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            lbforma.setVisible(true);
            lbforma.setText(tipoFormaPagto);
            ftformpagt.setVisible(true);
            if (tipoFormaPagto.equals("avista")) {
                ftformpagt.setIconName("MONEY");
            } else if (tipoFormaPagto.equals("prazo")) {
                ftformpagt.setIconName("CALENDAR");
            } else if (tipoFormaPagto.equals("debito")) {
                ftformpagt.setIconName("CREDIT_CARD");
            } else if (tipoFormaPagto.equals("cheque")) {
                ftformpagt.setIconName("CALENDAR");
            } else if (tipoFormaPagto.equals("credito")) {
                ftformpagt.setIconName("CREDIT_CARD");
            }
            lbqtdeparc.setVisible(true);
            lbqtdeparc.setText(txqtdeparc.getText());
            lbqtdeentre.setVisible(true);
            lbqtdeentre.setText(txdiasentreparc.getText());
            centra.confirmarPag(tipoFormaPagto, cbFormaPagto, lbqtdeparc, lbqtdeentre,
                    lbdata, txqtdeparc, lbforma, ftformpagt, txdiasentreparc, dpdatavencimento,
                    cod, Double.parseDouble(lbtotal.getText()), dpemissao, tabelaReceber, txemissorcheque);

        }
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
                txqtdeparc.setVisible(true);
                txqtdeparc.setPromptText("Quantidade de Cheques ");
                txdiasentreparc.setVisible(false);
            } else if (tipoFormaPagto.equals("fiado") || tipoFormaPagto.equals("à vista")
                    || tipoFormaPagto.equals("débito")) {
                limparLabel();
                txemissorcheque.setVisible(false);
                txqtdeparc.setVisible(false);
                txdiasentreparc.setVisible(false);
            } else if (tipoFormaPagto.equals("fixa")) {
                txemissorcheque.setVisible(false);
                txqtdeparc.setVisible(true);
                limparLabel();
                txqtdeparc.setPromptText("Quantidade de Meses ");
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
            ObservableList<ContasPagar> contr;
            contr = FXCollections.observableArrayList(ReceberE);
            tabelaReceber.setItems(contr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void estadoEdicaoP() {
        Mensagens msg = new Mensagens();
        conteudodir1.setDisable(false);
        conteudodir2.setDisable(false);
        pnprodutos.setDisable(false);
        tabela.setDisable(false);
        tabela.setVisible(true);
        pnparcelas.setDisable(false);

    }

    public void carregarCompra() throws ParseException {
        estadoEdicaoP();
        centra.carregarCompra(entradaproduto, dpemissao, txcod, txcodfor, txfornome, txcodfor, txfornome, Produtos, cbFormaPagto,
                txqtdeparc, txdiasentreparc, ReceberE, btnexcluir, btnfinalizar, btncancelar, btnnovo, tabela, lbtotal, tabelaReceber, txemissorcheque);

    }

    @FXML
    private void evtProcurarCompras(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            Stage stage = new Stage();
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaEntradaProdutos.fxml"));
            Scene scene = new Scene(pesquisa);
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.setTitle("Consulta de Compras");
            stage.showAndWait();
            if (entradaproduto != null) {

                carregarCompra();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }

    private void limparLabel() {
        lberro1venc.setText("");
        lberroentredias.setText("");
        lberroemissao.setText("");
        lberroqtde.setText("");
        lberroqtdeparc.setText("");
        lbqtdeparc.setText("");
        txemissorcheque.resetValidation();
        txfornome.resetValidation();
        txproddesc.resetValidation();
        txqtdeparc.resetValidation();
        txdiasentreparc.resetValidation();
        txquantidade.resetValidation();
    }

    public void atualizarTabelaReceber() {
        ObservableList<ContasPagar> prod_rec = null;
        if (tabelaReceber.getItems() != null) {
            tabelaReceber.getItems().clear();
        }
        prod_rec = FXCollections.observableArrayList(ReceberE);
        tabelaReceber.setItems(prod_rec);
    }

    @FXML
    private void editEmissao(TableColumn.CellEditEvent<ContasPagar, String> event) {

        contaspagar = tabelaReceber.getSelectionModel().getSelectedItem();
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
        for (ContasPagar contasPagar : ReceberE) {
            total += contasPagar.getValor();
        }
        if (total > ReceberE.get(0).getValor_total()) {
            return false;
        }

        return true;
    }

    @FXML
    private void editValor(TableColumn.CellEditEvent<ContasPagar, Double> event) {

        contaspagar = tabelaReceber.getSelectionModel().getSelectedItem();
        contaspagar.setValor(event.getNewValue());
        if (contaspagar.getCondicaopagamento().getDescricao().equals("à vista") && contaspagar.getParcela().equals("1/1")) {
            contaspagar.setValor_pago(event.getNewValue());

        }
        atualizarTabelaReceber();
    }

    @FXML
    private void editVencimento(TableColumn.CellEditEvent<ContasPagar, String> event) {

        contaspagar = tabelaReceber.getSelectionModel().getSelectedItem();
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

        contaspagar = tabelaReceber.getSelectionModel().getSelectedItem();
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
        contaspagar = tabelaReceber.getSelectionModel().getSelectedItem();
        contaspagar.setValor_pago(event.getNewValue());
        atualizarTabelaReceber();
    }

    @FXML
    private void editQtde(TableColumn.CellEditEvent<Produto_Entrada, Integer> event) {
        prode = tabela.getSelectionModel().getSelectedItem();
        atualizarTabela();
        prode.setQtde(event.getNewValue());
        centra.calculaTotal(total, lbtotal, Produtos, prode);
        Double tot = centra.calculaTotalP(total, lbtotal, Produtos, prode);
        prode.setTotal(tot);
        if (ReceberE != null && !ReceberE.isEmpty() && !cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao().equals("fiado")) {
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
                ReceberE.clear();
                ReceberE = centra.gerarParcelas(cbFormaPagto.getSelectionModel().getSelectedItem(), Double.parseDouble(lbtotal.getText()),
                        dpemissao, dpdatavencimento, txdiasentreparc, qtde, cod);
                atualizarReceber();
            } catch (ParseException ex) {
                Logger.getLogger(TelaVendaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void preencherCheque(KeyEvent event) {
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.controller.ControllerProducao;
import estagiocarvaobarao.entidade.Armazem;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Producao;
import estagiocarvaobarao.entidade.Producao_Produtos;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.utils.ConsultaProdutoController;
import estagiocarvaobarao.utils.Mensagens;
import static java.lang.System.exit;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import static javafx.scene.input.KeyCode.S;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaProducaoController implements Initializable {

    @FXML
    private Pane pnbotoes;
    @FXML
    private JFXButton btnfinalizar;
    @FXML
    private JFXButton btnnovo;
    @FXML
    private JFXButton btncancelar;
    @FXML
    private JFXButton btnpesquisar;
    @FXML
    private JFXButton btnexcluir;

    @FXML
    private Pane conteudo;
    @FXML
    private JFXTextField txcod;
    @FXML
    private DatePicker dpemissao;
    @FXML
    private Label lberrodata;
    @FXML
    private JFXTextField txcodprod;
    @FXML
    private JFXTextField txproddesc;
    @FXML
    private JFXTextField txtqtdepac;
    @FXML
    private Label lberroqtdepac;
    @FXML
    private JFXTextField txtqtdepackg;
    @FXML
    private JFXTextField txtqtdekgmoinha;
    @FXML
    private Label lberroqtdekgmoinha;
    @FXML
    private JFXTextField txtqtdeperda;
    @FXML
    private Label lberroqtdeperda;
    @FXML
    private TableView<Producao_Produtos> tabela;
    @FXML
    private TableColumn<Producao_Produtos, String> coldesc;
    @FXML
    private TableColumn<Producao_Produtos, Integer> qtdepac;
    @FXML
    private TableColumn<Producao_Produtos, Integer> qtdkg;
    @FXML
    private Label lbtotmoinha;
    @FXML
    private Label lbtotpacotes;
    @FXML
    private Label lbtotkilos;

    public static Producao_Produtos prod;
    public static Producao producao;
    public static Produto produto;

    public static Produto getProduto() {
        return produto;
    }

    public static void setProduto(Produto produto) {
        TelaProducaoController.produto = produto;
    }

    public static Producao getProducao() {
        return producao;
    }

    public static void setProducao(Producao producao) {
        TelaProducaoController.producao = producao;
    }

    public static Producao_Produtos getProd() {
        return prod;
    }

    public static void setProd(Producao_Produtos prod) {
        TelaProducaoController.prod = prod;
    }

    public static List<Producao_Produtos> Produtos;

    ControllerProducao ctrlproducao = new ControllerProducao();

    Mensagens msg = new Mensagens();
    public static int total_pacotes;
    public static int total_pacotesanterior;
    public static double total_kilos;

    public static int getTotal_pacotesanterior() {
        return total_pacotesanterior;
    }

    public static void setTotal_pacotesanterior(int total_pacotesanterior) {
        TelaProducaoController.total_pacotesanterior = total_pacotesanterior;
    }

    public static int getTotal_pacotes() {
        return total_pacotes;
    }

    public static void setTotal_pacotes(int total_pacotes) {
        TelaProducaoController.total_pacotes = total_pacotes;
    }

    public static double getTotal_kilos() {
        return total_kilos;
    }

    public static void setTotal_kilos(double total_kilos) {
        TelaProducaoController.total_kilos = total_kilos;
    }

    public static Funcionario func;

    public static Funcionario getFunc() {
        return func;
    }

    public static void setFunc(Funcionario func) {
        TelaProducaoController.func = func;
    }
    public static List<AuxValor> aux = new ArrayList();

    public static List<AuxValor> getAux() {
        return aux;
    }

    public static void setAux(List<AuxValor> aux) {
        TelaProducaoController.aux = aux;
    }
    @FXML
    private JFXButton btnpesquisarprod;
    @FXML
    private JFXButton btnaddprod;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        coldesc.setCellValueFactory(new PropertyValueFactory("produto"));
        qtdepac.setCellValueFactory(new PropertyValueFactory("qtde_pacotes"));
        qtdkg.setCellValueFactory(new PropertyValueFactory("total"));
        tabela.setEditable(true);

        qtdepac.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        addButtonToTable();
        dpemissao.setValue(LocalDate.now());
        Produtos = new ArrayList<Producao_Produtos>();
        estadoInicial();

    }

   


    public class AuxValor {

        private int cod;
        private int total;

        public AuxValor() {
        }

        public AuxValor(int cod, int total) {
            this.cod = cod;
            this.total = total;
        }

        public int getCod() {
            return cod;
        }

        public void setCod(int cod) {
            this.cod = cod;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

    }

    public void atualizarTabela() {
        ObservableList<Producao_Produtos> prod_v = null;

        int totpac = 0;
        double tot = 0.0;
        if (Produtos != null) {
            prod_v = FXCollections.observableArrayList(Produtos);
            for (Producao_Produtos Produto : Produtos) {
                totpac += Produto.getQtde_pacotes();
                tot += Produto.getQtde_pacotes() * Produto.getProduto().getPeso();
            }
            total_kilos = tot;
            total_pacotes = totpac;
            lbtotkilos.setText("Total de kilos:  " + tot + "Kg");
            lbtotpacotes.setText("Quantidade de Pacote:" + String.valueOf(totpac));
            if (!txtqtdekgmoinha.getText().isEmpty()) {
                lbtotmoinha.setText("Quantidade em Kg de Moinha: " + txtqtdekgmoinha.getText() + "Kg");
            }
        }
        tabela.getItems().clear();
        tabela.setItems(prod_v);
    }

    public void excluirProduto(int index) {
        Produtos.remove(index);
        atualizarTabela();
        //calculaTotal();
        //estadoGravouProduto();
    }

    private void addButtonToTable() {
        TableColumn<Producao_Produtos, Void> colBtn = new TableColumn("Ações");

        Callback<TableColumn<Producao_Produtos, Void>, TableCell<Producao_Produtos, Void>> cellFactory = new Callback<TableColumn<Producao_Produtos, Void>, TableCell<Producao_Produtos, Void>>() {
            @Override
            public TableCell<Producao_Produtos, Void> call(final TableColumn<Producao_Produtos, Void> param) {
                final TableCell<Producao_Produtos, Void> cell = new TableCell<Producao_Produtos, Void>() {
                    Image imageDecline = new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/trash.png"));

                    private final JFXButton btn = new JFXButton("Excluir");

                    {
                        btn.setGraphic(new ImageView(imageDecline));
                        btn.setOnAction((ActionEvent event) -> {
                            Producao_Produtos produto_venda = getTableView().getItems().get(getIndex());
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
    private void evtFinalizar(ActionEvent event) {
        int erro = 0, cod = 0;
        limpaLabel();
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }

        if (txtqtdekgmoinha.getText().isEmpty()) {
            msg.campoVazio(txtqtdekgmoinha, "");
            erro = 1;
        } else {
            if (Integer.parseInt(txtqtdekgmoinha.getText()) == 0) {
                msg.campoLabel(txtqtdekgmoinha, lberroqtdekgmoinha, "Campo não pode ser 0!");
                erro = 1;
            }
            if (Integer.parseInt(txtqtdekgmoinha.getText()) < 0) {
                msg.campoLabel(txtqtdekgmoinha, lberroqtdekgmoinha, "Campo não pode ser negativo!");
                erro = 1;
            }
        }

        if (txtqtdeperda.getText().isEmpty()) {
            msg.campoVazio(txtqtdeperda, "");
            erro = 1;
        } else {
            if (Integer.parseInt(txtqtdeperda.getText()) == 0) {
                msg.campoLabel(txtqtdeperda, lberroqtdeperda, "Campo não pode ser 0!");
                erro = 1;
            }
            if (Integer.parseInt(txtqtdeperda.getText()) < 0) {
                msg.campoLabel(txtqtdeperda, lberroqtdeperda, "Campo não pode ser negativo!");
                erro = 1;
            }
        }

        if (dpemissao.getValue() == null) {
            msg.campoLabel(dpemissao, lberrodata, "Campo não pode estar vazio!");
            erro = 1;
        } else {
            if (dpemissao.getValue().isAfter(LocalDate.now())) {
                msg.campoLabel(dpemissao, lberrodata, "Data maior que atual!");
                erro = 1;
            }
        }
        if (erro == 0) {
            if (ctrlproducao.gravar(cod, func, dpemissao, txcodprod, txtqtdekgmoinha, txtqtdepac, txtqtdepackg, txtqtdeperda, tabela, Produtos,
                    btnfinalizar, btncancelar, btnexcluir, btnnovo, conteudo, lbtotkilos, lbtotpacotes, aux, lbtotmoinha)) {

                Produtos.clear();

                estadoInicial();
            }
        }
    }

    private void estadoEdicao() {
        Mensagens msg = new Mensagens();
        conteudo.setDisable(false);
        tabela.setDisable(false);
        tabela.setVisible(true);
        btnfinalizar.setDisable(false);
        btncancelar.setDisable(false);
        btnpesquisar.setDisable(false);

        btnexcluir.setDisable(true);
        btnnovo.setDisable(false);
    }

    private void estadoInicial() {
        conteudo.setDisable(true);
        tabela.setVisible(true);
        pnbotoes.setDisable(false);
        btnfinalizar.setDisable(true);
        btnpesquisar.setDisable(false);
        btncancelar.setDisable(false);
        btnnovo.setDisable(false);
        btnexcluir.setDisable(true);
        dpemissao.setValue(LocalDate.now());
        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }
        txtqtdekgmoinha.resetValidation();
        txtqtdepac.resetValidation();
        txtqtdeperda.resetValidation();
        Produtos.clear();
        lberrodata.setText("");
        lberroqtdekgmoinha.setText("");
        lberroqtdepac.setText("");
        lberroqtdeperda.setText("");

        lbtotkilos.setText("Total de kilos:");
        lbtotpacotes.setText("Quantidade de Pacote:");
        ObservableList<Node> componentes = conteudo.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
        }
    }

    @FXML
    private void evtNovo(ActionEvent event) {
        estadoEdicao();

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
    private void evtExcluir(ActionEvent event) {
        if (ctrlproducao.excluir(producao, event)) {
            if (tabela != null) {
                tabela.getItems().clear();
            }
            estadoInicial();
        }
    }


    @FXML
    private void onExitProduto(KeyEvent event) {
        if (!txcodprod.getText().isEmpty()) {
            ctrlproducao.oxexitProduto(Integer.parseInt(txcodprod.getText()), event, txcodprod, txproddesc);
        }

    }

    public void pesquisarProduto(Produto produto) {
        ctrlproducao.pesquisarProduto(produto, txcodprod, txproddesc);
        limpaLabel();

    }

    @FXML
    private void evtProcurarProduto(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            Stage stage = new Stage();
            ConsultaProdutoController.setFlag(1);
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaProduto.fxml"));
            Scene scene = new Scene(pesquisa);
            stage.setTitle("Consulta de Produto");
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
            if (produto != null) {
                pesquisarProduto(produto);
            }

        } catch (Exception e) {
            msg.Error("Erro ao Consultar", "Nenhum Produto foi selecionada!");
            exit(1);
        }

    }

    public void limpaLabel() {
        txtqtdekgmoinha.resetValidation();
        txtqtdepac.resetValidation();
        txtqtdeperda.resetValidation();
        txproddesc.resetValidation();
        lberrodata.setText("");
        lberroqtdekgmoinha.setText("");
        lberroqtdepac.setText("");
        lberroqtdeperda.setText("");
    }

    public void limpaCampos() {
        lberrodata.setText("");
        lberroqtdekgmoinha.setText("");
        lberroqtdepac.setText("");
        lberroqtdeperda.setText("");
        txcodprod.setText("");
        txproddesc.setText("");
        txtqtdepac.setText("");
        txtqtdepackg.setText("");
    }

    @FXML
    private void evtaddProduto(ActionEvent event) {
        int erro = 0;
        limpaLabel();

        if (txcodprod.getText().isEmpty()) {
            msg.campoVazio(txproddesc, "");
            erro = 1;
        }
        if (txtqtdepac.getText().isEmpty()) {
            msg.campoVazio(txtqtdepac, "");
            erro = 1;
        } else {
            if (Integer.parseInt(txtqtdepac.getText()) == 0) {
                msg.campoLabel(txtqtdepac, lberroqtdepac, "Campo não pode ser 0!");
                erro = 1;
            }
            if (Integer.parseInt(txtqtdepac.getText()) < 0) {
                msg.campoLabel(txtqtdepac, lberroqtdepac, "Campo não pode ser negativo!");
                erro = 1;
            }
        }

        if (erro == 0) {
            ctrlproducao.addProduto(txcodprod.getText(), txtqtdekgmoinha, txtqtdepac, txtqtdepackg, txtqtdeperda,
                    dpemissao, tabela, Produtos, lbtotkilos, lbtotpacotes, lbtotmoinha);
            limpaCampos();
        }

    }

    @FXML
    private void onExitToTal(KeyEvent event) {
        if (!txcodprod.getText().isEmpty() && !txtqtdepac.getText().isEmpty()) {
            ctrlproducao.oxexitTotal(Integer.parseInt(txcodprod.getText()), event, txtqtdepac, txtqtdepackg);
        }
    }
    @FXML
    private void editqtdePacotes(TableColumn.CellEditEvent<Producao_Produtos, Integer> event) {
        prod = tabela.getSelectionModel().getSelectedItem();
        prod.setQtde_pacotes(event.getNewValue());
        prod.setTotal(event.getNewValue() * prod.getProduto().getPeso());
        atualizarTabela();
    }

    private void desabilitaCampos() {
        txcod.setDisable(false);
        txcodprod.setDisable(false);
        txproddesc.setDisable(false);
        txtqtdekgmoinha.setDisable(false);
        txtqtdepac.setDisable(false);
        txtqtdepackg.setDisable(false);
        txtqtdeperda.setDisable(false);
    }

    private void carregarCampos() {
        if (producao != null) {

            btnexcluir.setDisable(false);

            txcod.setText(String.valueOf(producao.getCodigo()));
            int ano = producao.getEmissaoDate().getYear() + 1900;
            int mes = producao.getEmissaoDate().getMonth() + 1;
            int dia = producao.getEmissaoDate().getDate();
            dpemissao.setValue(LocalDate.of(ano, mes, dia));
            dpemissao.setShowWeekNumbers(true);

            StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
                DateTimeFormatter dateFormatter
                        = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }//To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, dateFormatter);
                    } else {
                        return null;
                    } //To change body of generated methods, choose Tools | Templates.
                }
            };
            dpemissao.setConverter(converter);
            txtqtdekgmoinha.setText(String.valueOf(producao.getQtde_moinhaKg()));
            txtqtdeperda.setText(String.valueOf(producao.getQtde_perdaKg()));
            ctrlproducao.atualizarTabela(tabela, producao, Produtos, lbtotkilos, lbtotpacotes, txtqtdekgmoinha, lbtotmoinha);
            btnnovo.setDisable(true);
            btnfinalizar.setDisable(false);
            btnexcluir.setDisable(false);
        }
    }

    public void pesquisarProducao(Producao producao) {
        estadoEdicao();
        Produtos = ctrlproducao.pesquisarProducao(producao, tabela, Produtos, lbtotkilos, lbtotpacotes, lbtotmoinha, txtqtdekgmoinha);

        for (Producao_Produtos Produto : Produtos) {
            aux.add(new AuxValor(Produto.getCodigo(), Produto.getQtde_pacotes()));
        }
        limpaLabel();
        carregarCampos();

    }

    @FXML
    private void evtProcurarProducao(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            Stage stage = new Stage();
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaProducao.fxml"));
            Scene scene = new Scene(pesquisa);
            stage.setTitle("Consulta de Producao");
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
            if (producao != null) {
                pesquisarProducao(producao);
            }

        } catch (Exception e) {
            msg.Error("Erro ao Consultar", "Nenhuma Produção foi selecionada!");
            exit(1);
        }
    }

}

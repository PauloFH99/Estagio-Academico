/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.ContasPagar;
import estagiocarvaobarao.entidade.Fornecedor;
import static estagiocarvaobarao.ui.TelaLancarDespesasController.funcionario;
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
public class TelaContasPagarController implements Initializable {

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
    private JFXTextField txcodfor;
    @FXML
    private JFXButton btnpesquisarfor;
    @FXML
    private JFXTextField txfornome;
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
    private Label lbtotbaixado;
    @FXML
    private Label lbtotbaixado1;
    @FXML
    private Label lbtotpendente;
    @FXML
    private Label lbtotal;
    @FXML
    private JFXButton btnestornar;
    @FXML
    private TableView<ContasPagar> tabela;
    @FXML
    private TableColumn<ContasPagar, Integer> colcod;
    @FXML
    private TableColumn<ContasPagar, String> colpar;
    @FXML
    private TableColumn<ContasPagar, String> colfor;
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
        TelaContasPagarController.valores = valores;
    }
    public static Fornecedor fornecedor;

    public static Fornecedor getFornecedor() {
        return fornecedor;
    }

    public static void setFornecedor(Fornecedor fornecedor) {
        TelaContasPagarController.fornecedor = fornecedor;
    }

    public static double valorbaixado;
    public static ContasPagar contapagar;
    public static ContasPagar contapagarclic;

    public static ContasPagar getContapagarclic() {
        return contapagarclic;
    }

    public static void setContapagarclic(ContasPagar contapagarclic) {
        TelaContasPagarController.contapagarclic = contapagarclic;
    }

    public static double getValorbaixado() {
        return valorbaixado;
    }

    public static void setValorbaixado(double valorbaixado) {
        TelaContasPagarController.valorbaixado = valorbaixado;
    }

    public static ContasPagar getContapagar() {
        return contapagar;
    }

    public static void setContapagar(ContasPagar contapagar) {
        TelaContasPagarController.contapagar = contapagar;
    }
    @FXML
    private Pane pntotais;
    @FXML
    private JFXButton btnfiltrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colpar.setCellValueFactory(new PropertyValueFactory("parcela"));
        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colfor.setCellValueFactory(new PropertyValueFactory("cliente"));
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
    }

    public void inicializarTabela() throws ParseException {
        double baixado = 0.00;
        NumberFormat nf = new DecimalFormat("#,###.0000");

        baixado += nf.parse(lbtotbaixado.getText()).doubleValue() + valorbaixado;
        if (!String.valueOf(valorbaixado).isEmpty()) {
            lbtotbaixado.setText(String.valueOf(baixado).replace(".", ","));

        }
        ContasPagar cp = new ContasPagar();

        ObservableList<ContasPagar> tabcontas;
        valores = (List<ContasPagar>) cp.get("");
        tabcontas = FXCollections.observableArrayList(valores);
        this.tabela.setItems(tabcontas);
        calcularTotal();
        calcularTotalPendente();
    }

    public void inicializarTabelaBusca(ContasPagar contasPagar) throws ParseException {
        double baixado = 0.00;
        NumberFormat nf = new DecimalFormat("#,###.0000");

        baixado += nf.parse(lbtotbaixado.getText()).doubleValue() + valorbaixado;
        if (!String.valueOf(valorbaixado).isEmpty()) {
            lbtotbaixado.setText(String.valueOf(baixado).replace(".", ","));

        }
        ObservableList<ContasPagar> tabcontas;
        valores = (List<ContasPagar>) contasPagar.getConta(contasPagar.getCod_iddes());
        tabcontas = FXCollections.observableArrayList(valores);
        this.tabela.setItems(tabcontas);
        calcularTotal();
        calcularTotalPendente();
    }

    public void calcularTotal() {
        double total = 0.0;
        for (ContasPagar tot : valores) {
            total += tot.getValor_total();
        }

        lbtotal.setText(String.valueOf(total).replace(".", ","));
    }

    public void calcularTotalPendente() {
        double total = 0.0;
        for (ContasPagar tot : valores) {
            total += tot.getValor_pendente();
        }

        lbtotpendente.setText(String.valueOf(total).replace(".", ","));
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
        txfornome.requestFocus();
        inicializarTabela();

    }

    @FXML
    private void evtNovo(ActionEvent event) throws ParseException {
        estadoEdicao();
    }

    @FXML
    private void evtFinalizar(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            Stage stage = new Stage();
            TelaBaixarController.setTipo(0);
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/TelaBaixar.fxml"));
            Scene scene = new Scene(pesquisa);
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.setTitle("Pagamento");
            stage.showAndWait();
            estadoEdicao();

        } catch (Exception e) {
            msg.Error("", e.getMessage());
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

    @FXML
    private void onExitFornecedor(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodfor.getText().isEmpty()) {
            pesquisarFornecedor(new Fornecedor(Integer.parseInt(txcodfor.getText())));
        }
    }

    public void pesquisarFornecedor(Fornecedor fornece) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor = fornecedor.get(fornece.getCodigo());

        if (fornecedor != null) {
            txcodfor.setText(String.valueOf(fornecedor.getCodigo()));
            txfornome.setText(fornecedor.getNomefantasia());
        } else {
            txcodfor.setText("0");
            txfornome.setText("Valor digitado não encontrado...");
        }
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
            pesquisarFornecedor(fornecedor);

        } catch (Exception e) {
            msg.Error("Erro ao Consultar", "Nenhum Fornecedor foi selecionado!");
            exit(1);
        }
    }

    public void pesquisarContaPagar(ContasPagar contapagar) throws ParseException {
        ContasPagar contasPagar = new ContasPagar();
        contasPagar = contasPagar.get(contapagar.getCodigo());
        if (contasPagar != null) {
            txcodfor.setText(String.valueOf(contasPagar.getFornecedor().getCodigo()));
            txfornome.setText(String.valueOf(contasPagar.getFornecedor().getNomefantasia()));
            if (valores != null) {
                valores.clear();
            }
            inicializarTabelaBusca(contasPagar);

        }
    }

    @FXML
    private void evtProcurarContasPagar(ActionEvent event) {
        Mensagens msg = new Mensagens();
        try {
            Stage stage = new Stage();
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/ConsultaContasPagar.fxml"));
            Scene scene = new Scene(pesquisa);
            stage.setTitle("Consultar Contas Pagar");
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
            pesquisarContaPagar(contapagar);

        } catch (Exception e) {
            msg.Error("Erro ao Consultar", "Nenhuma Conta foi selecionado!");
            exit(1);
        }
    }

    public void atualizarParcela(int cod) {
        ContasPagar cp = new ContasPagar();
        List<ContasPagar> listparcelas = new ArrayList<ContasPagar>();
        cp = cp.get(cod);
        String parcela = "";
        String parcelaNum = "";

        int i = 0, j = 0;
        listparcelas = cp.getConta(cp.getCod_iddes());

        for (ContasPagar listparcela : listparcelas) {
            if (i == listparcelas.size() - 1) {
                for (j = 0; listparcela.getParcela().charAt(j) != '/';) {
                    parcela += listparcela.getParcela().charAt(j);
                    j++;
                }

            }
            i++;
        }
        for (ContasPagar listparcela : listparcelas) {
            listparcela.setParcela(listparcela.getParcela().charAt(0) + "/" + Integer.parseInt(parcela));
//            listparcela.alterar(listparcela,);
        }
    }

    @FXML
    private void evtEstornar(ActionEvent event) throws ParseException {
        Mensagens msg = new Mensagens();
        int cod = 0;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja realmente estornar ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                cod = contaspagarclic.getCodigo();
            } catch (Exception e) {
                cod = 0;
            }
            ContasPagar cp = new ContasPagar();
            cp = cp.get(cod);
            cp.setData_pago(null);
            cp.setValor_pago(0.0);
            cp.setValor_pendente(0.0);
            List<ContasPagar> listc = new ArrayList<ContasPagar>();
            listc = cp.getConta(cp.getCod_iddes());
            for (ContasPagar contasPagar : listc) {
                if (contasPagar.getCodigo() > cp.getCodigo()) {
                    cp.apagar(contasPagar);
                }
            }
//            if (cp.alterar(cp)) {
//                msg.Affirmation("Gravação concluida", "Estorno realizado!");
//                atualizarParcela(cod);
//            } else {
//                msg.Affirmation("Erro na gravação", "Problemas ao realizar o estorno!");
//            }
        }
        inicializarTabela();

    }

    @FXML
    private void evtPegarConta(MouseEvent event) {
        contapagarclic = tabela.getSelectionModel().getSelectedItem();
        TelaBaixarController.setContaspagarclic(contapagarclic);
        txcodfor.setText(String.valueOf(contapagarclic.getFornecedor().getCodigo()));
        txfornome.setText(contapagarclic.getFornecedor().getNomefantasia());
//        txcodfunc.setText(String.valueOf(contapagarclic.getFuncionario().getCodigo()));
//        txnomefunc.setText(contapagarclic.getFuncionario().getNome());
        lbtotal.setText(String.valueOf(contapagarclic.getValor()).replace(".", ","));
        lbtotpendente.setText(String.valueOf(contapagarclic.getValor_pendente()).replace(".", ","));
        if (contapagarclic.getValor_pendente() == 0 && contapagarclic.getData_pago() == null && contapagarclic.getValor_pago() == 0) {
            btnfinalizar.setDisable(false);
            btnestornar.setDisable(false);
        } else if (contapagarclic.getValor_pendente() > 0 && contapagarclic.getData_pago() != null && contapagarclic.getValor_pago() != 0) {
            btnfinalizar.setDisable(false);
            btnestornar.setDisable(false);
        } else if (contapagarclic.getValor_pendente() > 0 && contapagarclic.getData_pago() != null && contapagarclic.getValor_pago() == 0) {
            btnfinalizar.setDisable(false);
            btnestornar.setDisable(false);
        }else if (contapagarclic.getValor_pendente() == 0 && contapagarclic.getData_pago() != null && contapagarclic.getValor_pago() >= 0) {
            btnfinalizar.setDisable(false);
            btnestornar.setDisable(false);
        }
        else {
            btnfinalizar.setDisable(true);
            btnestornar.setDisable(true);
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
            carregarConsultaEmissao(filtroini, filtrofim, filtrorb);
        }

    }

    public void carregarConsultaEmissao(String filtroini, String filtrofim, String filtrorb) {
        DALConsulta dal = new DALConsulta();
        if (valores != null) {
            valores.clear();
        }
        ObservableList<ContasPagar> contp;

        valores = (List<ContasPagar>) dal.getFiltros(filtroini, filtrofim, filtrorb,"");
        contp = FXCollections.observableArrayList(valores);
        this.tabela.setItems(contp);
    }

    public void carregarConsultaPendente(String tabela, String filtro) {
        DALConsulta dal = new DALConsulta();
        if (valores != null) {
            valores.clear();
        }
        ObservableList<ContasPagar> contp;

        valores = (List<ContasPagar>) dal.getPen(filtro);
        contp = FXCollections.observableArrayList(valores);
        this.tabela.setItems(contp);
    }

    @FXML
    private void evtPendente(ActionEvent event) {
        if (rbpendente.isSelected()) {
            carregarConsultaPendente("contas_pagar", "");
            calcularTotal();
            calcularTotalPendente();
        }
    }

    public void carregarConsultabaixado(String tabela, String filtro) {
        DALConsulta dal = new DALConsulta();
        if (valores != null) {
            valores.clear();
        }
        ObservableList<ContasPagar> contp;
        valores = (List<ContasPagar>) dal.getBaixado(filtro);
        contp = FXCollections.observableArrayList(valores);
        this.tabela.setItems(contp);
    }

    @FXML
    private void evtBaixado(ActionEvent event) {
        if (rbbaixado.isSelected()) {
            carregarConsultabaixado("contas_pagar", "");
            calcularTotal();
            calcularTotalPendente();
        }

    }

    public void carregarConsultaTodos(String tabela, String filtro) {
        DALConsulta dal = new DALConsulta();
        if (valores != null) {
            valores.clear();
        }
        ObservableList<ContasPagar> contp;
        valores = (List<ContasPagar>) dal.get(tabela, filtro);
        contp = FXCollections.observableArrayList(valores);
        this.tabela.setItems(contp);
    }

    @FXML
    private void evtTodos(ActionEvent event) {
        if (rbtodos.isSelected()) {
            carregarConsultaTodos("contas_pagar", "");
            calcularTotal();
            calcularTotalPendente();
        }
    }

}

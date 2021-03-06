package estagiocarvaobarao.utils;

import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.ContasPagar;
import estagiocarvaobarao.entidade.Fornecedor;
import estagiocarvaobarao.ui.TelaCompensarChequeController;
import estagiocarvaobarao.ui.TelaContasPagarController;
import estagiocarvaobarao.ui.TelaLancarDespesasController;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConsultaContasPagarController implements Initializable {

    private RadioButton rbCodigo;
    private RadioButton rbNome;
    @FXML
    private Button btnPesquisar;
    @FXML
    private TableView<ContasPagar> tabela;
    @FXML
    private TableColumn<ContasPagar, Integer> colCod;
    @FXML
    private TableColumn<ContasPagar, String> colFor;
    @FXML
    private TableColumn<ContasPagar, Double> colValor;
    @FXML
    private TableColumn<ContasPagar, Date> colVenc;

    @FXML
    private TextField txPesquisa;

    public static ContasPagar contaspagar;

    public static ContasPagar getContaspagar() {
        return contaspagar;
    }

    public static void setContaspagar(ContasPagar contaspagar) {
        ConsultaContasPagarController.contaspagar = contaspagar;
    }
    public static int flagCP;

    public static int getFlagCP() {
        return flagCP;
    }

    public static void setFlag(int flagCP) {
        ConsultaContasPagarController.flagCP = flagCP;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colFor.setCellValueFactory(new PropertyValueFactory("fornecedor"));
        colValor.setCellValueFactory(new PropertyValueFactory("valor"));
        colVenc.setCellValueFactory(new PropertyValueFactory("vencimento"));
        carregarConsulta("contas_pagar", "");
    }

    private void evtRbCodigo(ActionEvent event) {
        if (rbNome.isSelected()) {
            rbNome.setSelected(false);
        }
    }

    private void evtRbNome(ActionEvent event) {
        if (rbCodigo.isSelected()) {
            rbCodigo.setSelected(false);
        }
    }

    @FXML
    private void evtPesquisar(ActionEvent event) {
        String filtro = "";
        filtro = txPesquisa.getText().trim().toLowerCase();
        flagCP = 4;
        carregarConsulta("contas_pagar", filtro);
    }

    public void carregarConsulta(String tabela, String filtro) {
        DALConsulta dal = new DALConsulta();
        List<ContasPagar> contp = new ArrayList();
        ObservableList<ContasPagar> cp;

        if (flagCP == 0 && filtro.isEmpty()) {
            contp = (List<ContasPagar>) dal.get(tabela, filtro);
        } else if (flagCP == 1 && filtro.isEmpty()) {
            contp = (List<ContasPagar>) dal.listaDespesas();
        } else if (flagCP == 3 && filtro.isEmpty()) {
            contp = (List<ContasPagar>) dal.getCheques();
        } else {
            int flag = 0;
            Fornecedor fornecedor = new Fornecedor();
            fornecedor = fornecedor.getF(filtro);
            if (fornecedor == null ) {
                flag = 1;
            } else {
                flag = 2;
            }
            contp = (List<ContasPagar>) dal.getCheques(filtro, flag);
        }
        cp = FXCollections.observableArrayList(contp);
        this.tabela.setItems(cp);
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        contaspagar = tabela.getSelectionModel().getSelectedItem();
        TelaContasPagarController.setContapagar(contaspagar);
        TelaLancarDespesasController.setContaspagar(contaspagar);
        TelaCompensarChequeController.setContapagar(contaspagar);
        Stage self = (Stage) tabela.getScene().getWindow();
        self.close();
    }

}

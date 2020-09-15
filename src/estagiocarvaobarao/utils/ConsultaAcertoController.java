package estagiocarvaobarao.utils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.AcertoEstoque;
import estagiocarvaobarao.ui.TelaAcertoEstoqueController;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConsultaAcertoController implements Initializable {

    public static AcertoEstoque acertoestoque;

    private RadioButton rbCodigo;
    private RadioButton rbNome;
    @FXML
    private Button btnPesquisar;
    @FXML
    private TableColumn<AcertoEstoque, String> colObs;
    @FXML
    private TableColumn<AcertoEstoque, String> colEmissao;
    @FXML
    private TableView<AcertoEstoque> tabela;
    private TextField txPesquisa;
    @FXML
    private Label lberrodtfinal;
    @FXML
    private Label lberrodtini;
    @FXML
    private FontAwesomeIcon iconcalendar;
    @FXML
    private Label lbfim;
    @FXML
    private Label lbini;
    @FXML
    private DatePicker dpfinal;
    @FXML
    private DatePicker dpinicial;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colObs.setCellValueFactory(new PropertyValueFactory("observacoes"));
        colEmissao.setCellValueFactory(new PropertyValueFactory("emissao"));
        carregarConsulta("acertoestoque", "");
    }

    public static AcertoEstoque getAcertoestoque() {
        return acertoestoque;
    }

    public static void setAcertoestoque(AcertoEstoque acertoestoque) {
        ConsultaAcertoController.acertoestoque = acertoestoque;
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
        String dtini = "";
        String dtfin = "";
        if (dpinicial.getValue() != null && dpfinal.getValue() != null) {
            dtini = dpinicial.getValue().toString();
            dtfin = dpfinal.getValue().toString();
        }
        carregarConsultaFiltro(dtini, dtfin);
    }

    public void carregarConsultaFiltro(String dtni, String dtfim) {
        DALConsulta dal = new DALConsulta();
        List<AcertoEstoque> acertos = new ArrayList();
        ObservableList<AcertoEstoque> acert;

        acertos = (List<AcertoEstoque>) dal.getAcerto(dtni, dtfim);
        acert = FXCollections.observableArrayList(acertos);
        this.tabela.setItems(acert);
    }

    public void carregarConsulta(String tabela, String filtro) {
        DALConsulta dal = new DALConsulta();
        List<AcertoEstoque> acertos = new ArrayList();
        ObservableList<AcertoEstoque> acert;

        acertos = (List<AcertoEstoque>) dal.get(tabela, filtro);
        acert = FXCollections.observableArrayList(acertos);
        this.tabela.setItems(acert);
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        acertoestoque = tabela.getSelectionModel().getSelectedItem();
        TelaAcertoEstoqueController.setAcertoestoque(acertoestoque);
        Stage self = (Stage) tabela.getScene().getWindow();
        self.close();
    }

}

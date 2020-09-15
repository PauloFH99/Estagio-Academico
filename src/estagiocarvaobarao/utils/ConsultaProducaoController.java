package estagiocarvaobarao.utils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Armazem;
import estagiocarvaobarao.entidade.Producao;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.ui.TelaAcertoEstoqueController;
import estagiocarvaobarao.ui.TelaEntradaProdutosController;
import estagiocarvaobarao.ui.TelaProducaoController;
import estagiocarvaobarao.ui.TelaVendaController;
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

/**
 * FXML Controller class
 *
 *
 */
public class ConsultaProducaoController implements Initializable {

    @FXML
    private Button btnPesquisar;
    @FXML
    private TableView<Producao> tabela;
    @FXML
    private TableColumn<Producao, Integer> colCod;
    @FXML
    private TableColumn<Producao, String> colemissao;
   
    public static Producao producao;

    public static Producao getProduto() {
        return producao;
    }

    public static void setProduto(Producao producao) {
        ConsultaProducaoController.producao = producao;
    }
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
     
        colCod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colemissao.setCellValueFactory(new PropertyValueFactory("emissao"));

        carregarConsulta("","","");
    }

    @FXML
    private void evtBtnPesquisar(ActionEvent event) {
        String filtro = "";
        String dtini = "";
        String dtfin = "";
        if (dpinicial.getValue() != null && dpfinal.getValue() != null) {
            dtini = dpinicial.getValue().toString();
            dtfin = dpfinal.getValue().toString();
        }
        carregarConsulta(filtro,dtini,dtfin);
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        producao = tabela.getSelectionModel().getSelectedItem();
        TelaProducaoController.setProducao(producao);
        Stage self = (Stage) tabela.getScene().getWindow();
        self.close();
    }

    public void carregarConsulta(String filtro, String dtini, String dtfin) {
        DALConsulta dal = new DALConsulta();
        List<Producao> prod = new ArrayList();
        ObservableList<Producao> f;

        prod = (List<Producao>) dal.getProducao(filtro,dtini,dtfin);
        f = FXCollections.observableArrayList(prod);
        tabela.setItems(f);
    }

}

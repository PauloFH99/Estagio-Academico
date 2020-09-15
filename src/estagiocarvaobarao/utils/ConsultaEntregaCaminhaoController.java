package estagiocarvaobarao.utils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Entrega;
import estagiocarvaobarao.ui.TelaPedidosCaminhaoController;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 *
 */
public class ConsultaEntregaCaminhaoController implements Initializable {

    @FXML
    private Button btnPesquisar;
    @FXML
    private TableView<Entrega> tabela;
    @FXML
    private TableColumn<Entrega, Integer> colCod;
    @FXML
    private TableColumn<Entrega, String> colemissao;

    public static Entrega entrega;

    public static Entrega getEntrega() {
        return entrega;
    }

    public static void setEntrega(Entrega entrega) {
        ConsultaEntregaCaminhaoController.entrega = entrega;
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

        carregarConsulta("", "", "");
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
        carregarConsulta(filtro, dtini, dtfin);
    }

    @FXML
    private void doubleClick(MouseEvent event) {
        entrega = tabela.getSelectionModel().getSelectedItem();
        TelaPedidosCaminhaoController.setEntrega(entrega);
        Stage self = (Stage) tabela.getScene().getWindow();
        self.close();
    }

    public void carregarConsulta(String filtro, String dtini, String dtfin) {
        DALConsulta dal = new DALConsulta();
        List<Entrega> prod = new ArrayList();
        ObservableList<Entrega> f;

        prod = (List<Entrega>) dal.getEntregasCaminhao(filtro, dtini, dtfin);
        f = FXCollections.observableArrayList(prod);
        tabela.setItems(f);
    }

}

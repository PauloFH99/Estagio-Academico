//package estagiocarvaobarao.utils;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.MouseEvent;
//import javafx.stage.Stage;
//
//
//
//
//
//
//
//public class ConsultaContasReceber implements Initializable {
//
//    public static ContasReceber contasreceber;
//
//    @FXML
//    private RadioButton rbCodigo;
//    @FXML
//    private RadioButton rbNome;
//    @FXML
//    private Button btnPesquisar;
//    @FXML
//    private TableColumn<ContasReceber, Integer> colCod;
//    @FXML
//    private TableColumn<ContasReceber, String> colNome;
//    @FXML
//    private TableView<ContasReceber> tabela;
//    @FXML
//    private TextField txPesquisa;
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        colCod.setCellValueFactory(new PropertyValueFactory("codigo"));
//        colNome.setCellValueFactory(new PropertyValueFactory("emissao"));
//        carregarConsulta("receber", "");
//    }
//
//    public static ContasReceber getContasreceber() {
//        return contasreceber;
//    }
//
//    public static void setContasreceber(ContasReceber contasreceber) {
//        ConsultaContasReceber.contasreceber = contasreceber;
//    }
//
//   
//
//    @FXML
//    private void evtRbCodigo(ActionEvent event) {
//        if (rbNome.isSelected()) {
//            rbNome.setSelected(false);
//        }
//    }
//
//    @FXML
//    private void evtRbNome(ActionEvent event) {
//        if (rbCodigo.isSelected()) {
//            rbCodigo.setSelected(false);
//        }
//    }
//
//    @FXML
//    private void evtPesquisar(ActionEvent event) {
//        String filtro = "";
//
//        if (!txPesquisa.getText().isEmpty()) {
//            if (rbCodigo.isSelected()) {
//                filtro = " (codigo) = '" + txPesquisa.getText().trim().toLowerCase() + "'";
//                carregarConsulta("receber", filtro);
//            } else if (rbNome.isSelected()) {
//                filtro = txPesquisa.getText().trim().toLowerCase();
//                carregarConsultaData("receber", filtro);
//
//            }
//        }
//        else
//            carregarConsulta("receber", filtro);
//
//    }
//
//    public void carregarConsulta(String tabela, String filtro) {
//        DALConsulta dal = new DALConsulta();
//        List<ContasReceber> func = new ArrayList();
//        ObservableList<ContasReceber> f;
//
//        func = (List<ContasReceber>) dal.getTodos(filtro);
//        f = FXCollections.observableArrayList(func);
//        this.tabela.setItems(f);
//    }
//    public void carregarConsultaData(String tabela, String filtro) {
//        DALConsulta dal = new DALConsulta();
//        List<ContasReceber> func = new ArrayList();
//        ObservableList<ContasReceber> f;
//
//        func = (List<ContasReceber>) dal.getEmissao(filtro);
//        f = FXCollections.observableArrayList(func);
//        this.tabela.setItems(f);
//    }
//
//    
//
//    @FXML
//    private void doubleClick(MouseEvent event) {
//        contasreceber = tabela.getSelectionModel().getSelectedItem();
//        TelaContaReceberController.setContasreceber(contasreceber);
//        Stage self = (Stage) tabela.getScene().getWindow();
//        self.close();
//    }
//
//}

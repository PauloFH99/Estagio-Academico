/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import estagiocarvaobarao.EstagioCarvaoBarao;
import estagiocarvaobarao.dal.DALCidade;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.dal.DALFornecedor;
import estagiocarvaobarao.dal.DALFuncionario;
import estagiocarvaobarao.dal.DALNivelFuncionario;
import estagiocarvaobarao.entidade.Cidade;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.NivelFuncionario;
import static estagiocarvaobarao.ui.TelaFornecedorCadController.cid;
import estagiocarvaobarao.utils.MaskFieldUtil;
import estagiocarvaobarao.utils.Messages;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaFuncionarioCadController implements Initializable {

    public static Cidade cid;
    public static boolean primeiro_acesso;

    public static Cidade getCid() {
        return cid;
    }

    public static void setCid(Cidade cid) {
        TelaFuncionarioCadController.cid = cid;
    }

    public static boolean isPrimeiro_acesso() {
        return primeiro_acesso;
    }

    public static void setPrimeiro_acesso(boolean primeiro_acesso) {
        TelaFuncionarioCadController.primeiro_acesso = primeiro_acesso;
    }

    @FXML
    private Pane pnbtn;
    @FXML
    private JFXButton btnovo;
    @FXML
    private JFXButton btalterar;
    @FXML
    private JFXButton btapagar;
    @FXML
    private JFXButton btconfirmar;
    @FXML
    private JFXButton btcancelar;
    @FXML
    private Pane pnpesquisa;
    @FXML
    private JFXTextField txpesquisar;
    @FXML
    private JFXButton btpesquisar;
    @FXML
    private TableView<Funcionario> tabela;
    @FXML
    private TableColumn<Funcionario, Integer> colcod;
    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;
    @FXML
    private JFXCheckBox chkAtivo;
    @FXML
    private ToggleGroup Pesquisa1;
    @FXML
    private TableColumn<Funcionario, String> colnome;
    @FXML
    private JFXTextField txtelefone;
    @FXML
    private JFXTextField txbairro;
    @FXML
    private JFXTextField txnum;
    @FXML
    private JFXTextField txcodcid;
    @FXML
    private JFXButton btnpesquisarcid;
    @FXML
    private JFXTextField txcid;
    @FXML
    private JFXTextField txcep;
    @FXML
    private JFXTextField txemail;
    @FXML
    private TableColumn<Funcionario, String> colcpf;
    @FXML
    private TableColumn<Funcionario, String> colnivel;
    @FXML
    private TableColumn<Funcionario, String> colativo;
    @FXML
    private JFXTextField txnome;
    @FXML
    private JFXTextField txcpf;
    private JFXTextField txlendereco;
    @FXML
    private JFXComboBox<NivelFuncionario> cbnivel;
    @FXML
    private JFXTextField txlogin;
    @FXML
    private JFXPasswordField txsenha;
    @FXML
    private JFXRadioButton rbnome;
    @FXML
    private JFXRadioButton rbcpf;
    @FXML
    private JFXTextField txendereco;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        TelaFuncionarioCadController.setPrimeiro_acesso(false);
        MaskFieldUtil.cepField(txcep);
        MaskFieldUtil.cpfField(txcpf);
        MaskFieldUtil.foneField(txtelefone);
        MaskFieldUtil.numericField(txcodcid);

        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colnome.setCellValueFactory(new PropertyValueFactory("nome"));
        colcpf.setCellValueFactory(new PropertyValueFactory("cpf"));
        colnivel.setCellValueFactory(new PropertyValueFactory("nivel"));
        colativo.setCellValueFactory(new PropertyValueFactory("ativo"));
        estadoInicial();
    }

    private void estadoInicial() {
        pnpesquisa.setDisable(false);
        pndados.setDisable(true);
        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);
        ObservableList<Node> componentes = pndados.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
            if (n instanceof ComboBox) {
                ((ComboBox) n).getSelectionModel().select(0);
            }
        }
        carregarNivel();
        carregaTabela("");
    }

    private int carregaTabela(String filtro) {
        DALFuncionario dal = new DALFuncionario();
        List<Funcionario> res = dal.get(filtro);
        ObservableList<Funcionario> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
        return res.size();
    }

    private void carregarNivel() {
        DALNivelFuncionario dal = new DALNivelFuncionario();
        List<NivelFuncionario> nf = dal.get();
        cbnivel.setItems(FXCollections.observableArrayList(nf));
        cbnivel.getSelectionModel().select(0);
    }

    private void estadoEdicao() {

        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txnome.requestFocus();
    }

    @FXML
    private void clknovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        estadoEdicao();
        DALFuncionario dal = new DALFuncionario();
        DALCidade dalc = new DALCidade();
        Funcionario f = (Funcionario) tabela.getSelectionModel().getSelectedItem();
        Cidade  cidade=null;
        
        f=dal.getFuncionario(f.getCodigo());
        cidade = dalc.get(f.getCidade().getCid_cod());
        txcod.setText("" + f.getCodigo());
        txnome.setText("" + f.getNome());
        txcpf.setText("" + f.getCpf());
        txendereco.setText("" + f.getEndereco());
        txtelefone.setText("" + f.getTelefone());
        chkAtivo.setText("" + f.getAtivo());
        txbairro.setText("" + f.getBairro());
        txnum.setText("" + f.getNumero());
        txcodcid.setText(String.valueOf(f.getCidade().getCid_cod()));
        txcid.setText(""+cidade.getCid_nome());
        txcep.setText("" + f.getCep());
        txsenha.setText("" + f.getSenha());
        txlogin.setText("" + f.getLogin());
        txemail.setText("" + f.getEmail());
        cbnivel.getSelectionModel().select(0);
        cbnivel.getSelectionModel().select(f.getNivel());
       
    }

    @FXML
    private void clkapagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            DALFuncionario dal = new DALFuncionario();
            Funcionario f;
            f = tabela.getSelectionModel().getSelectedItem();
            dal.apagar(f.getCodigo());
            carregaTabela("");
        }
    }

    private boolean CampoVazio(String valor) {
        boolean resultado = (valor.isEmpty() || valor.trim().isEmpty());
        return resultado;
    }

    private int retornaValor(String valor) {
        int res = 0;
        if (!valor.equals("")) {
            res = Integer.parseInt(valor);
        }
        return res;
    }

    @FXML
    private void clkconfirmar(ActionEvent event) {
        int cod, erro = 0;
        String ativo, pAcesso;
        NivelFuncionario nivel;
        DALFuncionario dal = new DALFuncionario();
        Cidade cidade;
        Messages msg = new Messages();
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        if (chkAtivo.isSelected()) {
            ativo = "S";
        } else {
            ativo = "N";
        }

        if (cod == 0) {
            pAcesso = "S";
        } else {
            pAcesso = "N";
        }
        cidade = new Cidade(Integer.parseInt(txcodcid.getText()));
        nivel = cbnivel.valueProperty().getValue();
        if (erro == 0) {

            Funcionario f = new Funcionario(cod, txnome.getText(), txcpf.getText(), txendereco.getText(),
                    txnum.getText(), txtelefone.getText(), txemail.getText(), txlogin.getText(),
                    txsenha.getText(), ativo, pAcesso, cidade, nivel, txbairro.getText(), txcep.getText());
            

            if (f.getCodigo() == 0) {
                if (dal.salvar(f)) {
                    msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
                } else {
                    msg.Error("Erro ao gravar!", "Problemas ao Gravar");
                }
            } else if (dal.alterar(f)) {
                msg.Confirmation("Gravação concluida", "Alterado com Sucesso");
            } else {
                msg.Error("Erro ao alterar!", "Problemas ao Alterar");
            }
            estadoInicial();
        }
    }

    @FXML
    private void clkcancelar(ActionEvent event) {
        if (!pndados.isDisabled())//encontra em estado de edição
        {
            estadoInicial();
        } else {
            btnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        if (!txpesquisar.getText().isEmpty()) {
            if (rbnome.isSelected()) {
                carregaTabela("upper(nome) like '%" + txpesquisar.getText().toUpperCase() + "%'");
            } else {
                if (rbcpf.isSelected()) {
                    carregaTabela("upper(cpf) like '%" + txpesquisar.getText().toUpperCase() + "%'");
                }
            }
        } else {
            carregaTabela("upper(nome) like '%" + txpesquisar.getText().toUpperCase() + "%'");
        }
    }

    @FXML
    private void evtTabela(MouseEvent event) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    @FXML
    private void voltar_menu(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaAutenticacao.fxml"));
        EstagioCarvaoBarao.stage.getScene().setRoot(root);
    }

    public void pesquisarCidade(Cidade cid) {
        DALConsulta dal = new DALConsulta();
        Cidade cidade = dal.getCidade(cid);

        if (cidade != null) {
            txcodcid.setText(String.valueOf(cidade.getCid_cod()));
            txcid.setText(cidade.getCid_nome());
        } else {
            txcodcid.setText("0");
            txcid.setText("Valor digitado não encontrado...");
        }
    }

    @FXML
    private void evtProcurarCidade(ActionEvent event) {
        Messages msg = new Messages();
        try {
            Stage stage = new Stage();
            Parent pesquisa = FXMLLoader.load(getClass().getResource("/estagiocarvaobarao/utils/Consulta.fxml"));
            Scene scene = new Scene(pesquisa);
            //Show main Window
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.setTitle("Consulta de cidades");
            stage.showAndWait();
            if (cid != null) {
                pesquisarCidade(cid);
                txcep.requestFocus();
            }

        } catch (Exception e) {
            msg.Error("", e.getMessage());
        }
    }

    @FXML
    private void onExitCidade(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodcid.getText().isEmpty()) {
            pesquisarCidade(new Cidade(Integer.parseInt(txcodcid.getText())));
        }
    }

    @FXML
    private void evRbNome(ActionEvent event) {
        if (rbcpf.isSelected()) {
            rbcpf.setSelected(false);
        } else {
            rbnome.setSelected(true);
        }
        MaskFieldUtil.onlyDigitsValue(txpesquisar);
    }

    @FXML
    private void evRbCpf(ActionEvent event) {
        if (rbnome.isSelected()) {
            rbnome.setSelected(false);
        } else {
            rbcpf.setSelected(true);
        }
        MaskFieldUtil.cpfField(txpesquisar);
    }

}

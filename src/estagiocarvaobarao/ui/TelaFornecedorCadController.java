/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
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
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.dal.DALFornecedor;
import estagiocarvaobarao.entidade.Cidade;
import estagiocarvaobarao.entidade.Fornecedor;
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
public class TelaFornecedorCadController implements Initializable {

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
    private TableView<Fornecedor> tabela;
    @FXML
    private TableColumn<Fornecedor, Integer> colcod;
    @FXML
    private Pane pndados;
    @FXML
    private JFXTextField txcod;
    @FXML
    private JFXCheckBox chkAtivo;
    @FXML
    private TableColumn<Fornecedor, String> colnome;
    @FXML
    private TableColumn<Fornecedor, String> colcid;
    @FXML
    private TableColumn<Fornecedor, String> colfone;
    @FXML
    private JFXTextField txfantasia;
    @FXML
    private JFXTextField txcnpj;
    @FXML
    private JFXTextField txrazasocial;
    @FXML
    private JFXTextField txtelefone;
    @FXML
    private JFXTextField txlogradouro;
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
    private JFXTextField txnomecontato;
    @FXML
    private JFXTextField txfonecontato;
    @FXML
    private JFXTextField txemail;

    /**
     * Initializes the controller class.
     */
    public static Cidade cid;
    

    public static Cidade getCid() {
        return cid;
    }

    public static void setCid(Cidade cid) {
        TelaFornecedorCadController.cid = cid;
    }

   
    @FXML
    private ToggleGroup Pesquisa;
    @FXML
    private JFXRadioButton rbfantasia;
    @FXML
    private JFXRadioButton rbcnpj;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MaskFieldUtil.cpfCnpjField(txcnpj);
        MaskFieldUtil.foneField(txtelefone);
        MaskFieldUtil.cepField(txcep);
        MaskFieldUtil.foneField(txfonecontato);

        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        colnome.setCellValueFactory(new PropertyValueFactory("nomefantasia"));
        colcid.setCellValueFactory(new PropertyValueFactory("cidade"));
        colfone.setCellValueFactory(new PropertyValueFactory("telefone"));
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
        carregaTabela("");
    }

    private int carregaTabela(String filtro) {
        DALFornecedor dal = new DALFornecedor();
        List<Fornecedor> res = dal.get(filtro);
        ObservableList<Fornecedor> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
        return res.size();
    }

    private void estadoEdicao() {

        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txfantasia.requestFocus();
    }

    @FXML
    private void clknovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        estadoEdicao();
        Fornecedor f = (Fornecedor) tabela.getSelectionModel().getSelectedItem();
        txcod.setText("" + f.getCodigo());
        txfantasia.setText("" + f.getNomefantasia());
        txcnpj.setText("" + f.getCnpj());
        txrazasocial.setText("" + f.getRazaosocial());
        txtelefone.setText("" + f.getTelefone());
        chkAtivo.setText("" + f.isAtivo());
        txlogradouro.setText("" + f.getLogradouro());
        txbairro.setText("" + f.getBairro());
        txnum.setText("" + f.getNumero());
        txcodcid.setText(String.valueOf(f.getCidade().getCid_cod()));
        txcid.setText("");
        txcep.setText("" + f.getCep());
        txnomecontato.setText("" + f.getNomecontato());
        txfonecontato.setText("" + f.getTelefonecontato());
        txemail.setText("" + f.getEmail());

    }

    @FXML
    private void clkapagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            DALFornecedor dal = new DALFornecedor();
            Fornecedor f;
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
        Messages msg = new Messages();
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        if (erro == 0) {

            Fornecedor f = new Fornecedor(cod, txfantasia.getText(), txcnpj.getText(), chkAtivo.isSelected(),
                    txlogradouro.getText(), txbairro.getText(), txnum.getText(),
                    new Cidade(Integer.parseInt(txcodcid.getText())), txcep.getText(), txnomecontato.getText(),
                    txfonecontato.getText(), txemail.getText(), txtelefone.getText(), txrazasocial.getText());
            DALFornecedor dal = new DALFornecedor();

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
            if (rbfantasia.isSelected()) {
                carregaTabela("upper(nomefantasia) like '%" + txpesquisar.getText().toUpperCase() + "%'");
            } else {
                if (rbcnpj.isSelected()) {
                    carregaTabela("upper(cnpj) like '%" + txpesquisar.getText().toUpperCase() + "%'");
                }
            }
        }
        else
        {
             carregaTabela("upper(nomefantasia) like '%" + txpesquisar.getText().toUpperCase() + "%'");
        }
    }

    @FXML
    private void evtTabela(MouseEvent event) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

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
    private void onExitCidade(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !txcodcid.getText().isEmpty()) {
            pesquisarCidade(new Cidade(Integer.parseInt(txcodcid.getText())));
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

    private void exit(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

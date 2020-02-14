/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import estagiocarvaobarao.dal.DALCategoria;
import estagiocarvaobarao.dal.DALProduto;
import estagiocarvaobarao.entidade.Categoria;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.utils.MaskFieldUtil;
import estagiocarvaobarao.utils.Messages;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import static javafx.application.Platform.exit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaProdCadController implements Initializable {

    @FXML
    private Button btnovo;
    @FXML
    private Button btalterar;
    @FXML
    private Button btapagar;
    @FXML
    private Button btconfirmar;
    @FXML
    private Button btcancelar;
    @FXML
    private AnchorPane pndados;
    @FXML
    private TextField txcode;
    @FXML
    private TextField txdescricao;
    @FXML
    private TextField txest_min;
    @FXML
    private TextField txest_max;
    @FXML
    private TextField txestoque;
    @FXML
    private ComboBox<Categoria> cbCat;
    @FXML
    private TextField txpreco;
    @FXML
    private VBox pnpesquisa;
    @FXML
    private TextField txpesquisar;
    @FXML
    private Button btpesquisar;
    @FXML
    private TableView<Produto> tabela;
    @FXML
    private TableColumn<Produto, Integer> colcod;
    @FXML
    private TableColumn<Produto, String> coldescricao;
    @FXML
    private TableColumn<Produto, Double> colpreco;
    @FXML
    private TableColumn<Categoria, Integer> colcat;

    @FXML
    private CheckBox chkAtivo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFieldUtil.monetaryField(txpreco);
        MaskFieldUtil.numericField(txest_min);
        MaskFieldUtil.numericField(txest_max);
        MaskFieldUtil.numericField(txestoque);

        colcod.setCellValueFactory(new PropertyValueFactory("codigo"));
        coldescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        colpreco.setCellValueFactory(new PropertyValueFactory("preco"));
        colcat.setCellValueFactory(new PropertyValueFactory("categoria"));
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

    private void estadoEdicao() {
        carregaCategorias();
        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txdescricao.requestFocus();
    }

    private void carregaCategorias() {
        DALCategoria dal = new DALCategoria();
        List<Categoria> cat = dal.get("");
        cbCat.setItems(FXCollections.observableArrayList(cat));
    }

    private int carregaTabela(String filtro) {
        DALProduto dal = new DALProduto();
        List<Produto> res = dal.get(filtro);
        ObservableList<Produto> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
        return res.size();
    }

    private void carregaTabelaCategoria(String filtro) {
        DALProduto dal = new DALProduto();
        List<Produto> res = dal.getCategoria(filtro);
        ObservableList<Produto> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);

    }

    private void carregaTabelaInt(Double filtro) {
        DALProduto dal = new DALProduto();
        List<Produto> res = dal.get(filtro);
        ObservableList<Produto> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);

    }

    @FXML
    private void clkPesquisar(ActionEvent event) {
        int tam = -1;
        if (txpesquisar.getText().matches(".*\\d.*")) {
            carregaTabelaInt(Double.parseDouble(txpesquisar.getText().toUpperCase().replace(",", ".")));
        } else {
            tam = carregaTabela("upper(descricao) like '%" + txpesquisar.getText().toUpperCase() + "%'");
            if (tam == 0) {
                carregaTabelaCategoria(txpesquisar.getText());
            }
        }

    }

    @FXML
    private void clknovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkalterar(ActionEvent event) {
        estadoEdicao();
        Produto p = (Produto) tabela.getSelectionModel().getSelectedItem();
        txcode.setText("" + p.getCodigo());
        txest_min.setText("" + p.getEst_min());
        txest_max.setText("" + p.getEst_max());
        txdescricao.setText(p.getDescricao());
        txpreco.setText("" + p.getPreco());
        txestoque.setText("" + p.getEstoque());
        chkAtivo.setSelected(p.isAtivo());
        cbCat.getSelectionModel().select(0);
        cbCat.getSelectionModel().select(p.getCategoria());
    }

    @FXML
    private void clkapagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            DALProduto dal = new DALProduto();
            Produto p;
            p = tabela.getSelectionModel().getSelectedItem();
            dal.apagar(p.getCodigo());
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
            cod = Integer.parseInt(txcode.getText());
        } catch (Exception e) {
            cod = 0;
        }

        if (CampoVazio(txest_min.getText()) || CampoVazio(txest_max.getText()) || CampoVazio(txestoque.getText())) {
            msg.Error("Informação inválida!", "O campo estoque mínimo/estoque máximo ou estoque físico estão em branco!");

            txest_min.requestFocus();
            exit(1);
            erro = 1;
        }
        if ((retornaValor(txest_min.getText()) < 0) || (retornaValor(txest_max.getText()) < 0) || (retornaValor(txestoque.getText()) < 0)) {
            msg.Error("Informação inválida!", "O campo estoque mínimo/máximo ou estoque físico não podem ser negativo!");

            txest_min.requestFocus();
            exit(1);
            erro = 1;
        }
        if (retornaValor(txest_max.getText()) <= retornaValor(txest_min.getText())) {
            msg.Error("Informação inválida!", "O estoque máximo tem que ser maior que o estoque mínimo!");

            txest_max.requestFocus();
            exit(1);
            erro = 1;
        }
        if (retornaValor(txest_min.getText()) >= retornaValor(txest_max.getText())) {
            msg.Error("Informação inválida!", "O estoque mínimo tem que ser menor que o estoque máximo!");

            txest_min.requestFocus();
            exit(1);
            erro = 1;
        }

        if (retornaValor(txestoque.getText()) > retornaValor(txest_max.getText())) {
            msg.Error("Informação inválida!", "O estoque físico tem que ser menor que o estoque máximo!");

            txestoque.requestFocus();
            exit(1);
            erro = 1;
        }
        if (CampoVazio(txdescricao.getText())) {
            msg.Error("Informação inválida!", "O campo descrição esta em branco!");

            txdescricao.requestFocus();
            exit(1);
            erro = 1;
        }
        if (CampoVazio(txpreco.getText())) {
            msg.Error("Informação inválida!", "O campo preço esta em branco!");

            txpreco.requestFocus();
            exit(1);
            erro = 1;
        }
        if (cbCat.getSelectionModel().isEmpty()) {
            msg.Error("Informação inválida!", "A categoria não foi selecionada!");

            cbCat.requestFocus();
            exit(1);
            erro = 1;
        }

        if (erro == 0) {
            Produto p = new Produto(cod, Integer.parseInt(txest_min.getText()), Integer.parseInt(txest_max.getText()), txdescricao.getText(), Double.parseDouble(txpreco.getText().replace(",", ".")), Integer.parseInt(txestoque.getText()), chkAtivo.isSelected(), cbCat.getSelectionModel().getSelectedItem());
            DALProduto dal = new DALProduto();

            if (p.getCodigo() == 0) {
                if (dal.salvar(p)) {
                    msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
                } else {
                    msg.Error("Erro ao gravar!", "Problemas ao Gravar");
                }
            } else if (dal.alterar(p)) {
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
    private void evtTabela(MouseEvent event) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }

    private void exit(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

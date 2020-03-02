/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import estagiocarvaobarao.entidade.Funcionario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaInicialController implements Initializable {

    public static Funcionario func;

    public static Funcionario getFunc() {
        return func;
    }

    public static void setFunc(Funcionario func) {
        TelaInicialController.func = func;
    }
    @FXML
    private HBox menubar;
    boolean flag = true;
    @FXML
    private BorderPane tela;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clkCadProduto(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaProdCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Produto");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void abrir_menulateral(ActionEvent event) throws IOException {
        if (flag == true) {
            Parent sidebar = FXMLLoader.load(getClass().getResource("MenuLateral.fxml"));
            tela.setLeft(sidebar);
            flag = false;
        } else {
            tela.setLeft(null);
            flag = true;
        }
    }

    @FXML
    private void clkCadFornecedor(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaFornecedorCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Fornecedores");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadFuncionario(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaFuncionarioCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Funcionarios");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadNivelFuncionarios(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaNivelFuncionarioCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Nivel de Funcionarios");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadCategorias(ActionEvent event) {
         try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaCategoriaCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Categorias");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadDespesas(ActionEvent event) {
         try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaDespesasCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Categorias");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadCliente(ActionEvent event) {
        
    }

}

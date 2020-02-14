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

}

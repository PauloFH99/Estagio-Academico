/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao;

import estagiocarvaobarao.banco.Banco;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Paulo
 */
public class EstagioCarvaoBarao extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("ui/TelaAutenticacao.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("ui/TelaProdCad.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Autenticação de Usuário");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("ui/icons/icon.png")));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(!Banco.conectar())
            JOptionPane.showMessageDialog(null, Banco.getCon().getMensagemErro());
        else
            launch(args);
    }
    
}

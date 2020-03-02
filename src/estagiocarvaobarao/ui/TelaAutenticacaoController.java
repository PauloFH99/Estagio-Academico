package estagiocarvaobarao.ui;

import estagiocarvaobarao.dal.DALFuncionario;
import estagiocarvaobarao.utils.Messages;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.stage.Modality;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;

public class TelaAutenticacaoController implements Initializable {

    @FXML
    private Button btnEntrar;
    @FXML
    private Button btnSair;
    @FXML
    private TextField txLogin;
    @FXML
    private PasswordField psSenha;
    @FXML
    private Pane conteudo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public TelaAutenticacaoController() {
    }

    @FXML
    private void evtEntrar(ActionEvent event) {
        logar();
    }

    @FXML
    private void evtSair(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void keyEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            logar();
        }
    }

    @FXML
    private void KeySair(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Stage stage = (Stage) btnSair.getScene().getWindow();
            stage.close();
        }
    }

    private void logar() {
        DALFuncionario dal = new DALFuncionario();
        Messages msg = new Messages();
        String usuario, senha;

        usuario = txLogin.getText();
        senha = psSenha.getText();

        if (usuario.equals("")) {
            msg.Error("", "Usuário não digitado.");
            txLogin.requestFocus();
        } else if (senha.equals("")) {
            msg.Error("", "Senha não digitada.");
            psSenha.requestFocus();
        } else {
            if (dal.getLogin(usuario, senha) == null) {
                msg.Error("", "Usuário não encontrado.");
                psSenha.requestFocus();
            } else {

                try {
                    TelaInicialController.setFunc(dal.getLogin(usuario, senha));
                    Stage stage = new Stage();
                    Stage self = new Stage();
                    Parent inicial = FXMLLoader.load(getClass().getResource("TelaInicial.fxml"));
                    Scene scene = new Scene(inicial);

                    stage.setScene(scene);
                    stage.setTitle("BEM-VINDO: CARVÃO BARÃO");
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
                    stage.setMaximized(true);

                    //Close authentication Window
                    self = (Stage) btnSair.getScene().getWindow();
                    self.close();

                    //Show main Window
                    stage.show();

                } catch (Exception e) {
                    msg.Error("Erro inesperado", e.getMessage());
                }
            }
        }
    }

//    @FXML
//    private void evtPrimeiroAcesso(ActionEvent event) {
//        try {
//            Parent cadFunc = FXMLLoader.load(getClass().getResource("TelaCadFuncionario.fxml"));
//            Scene scene = new Scene(cadFunc);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.setTitle("Cadastro de Funcionário");
//            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
//            stage.show();
//            TelaCadFuncionarioController.setPrimeiro_acesso(true);
//
//        } catch (Exception e) {
//        }
//    }

    @FXML
    private void abrir_registro(MouseEvent event) throws IOException {
         Parent cadFunc = FXMLLoader.load(getClass().getResource("TelaFuncionarioCad.fxml"));
         conteudo.getChildren().removeAll();
         conteudo.getChildren().setAll(cadFunc);
    }
}

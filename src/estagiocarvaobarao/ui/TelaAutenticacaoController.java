package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sun.javafx.scene.control.skin.TextFieldSkin;
import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.dal.DALFuncionario;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.utils.Mensagens;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;

import javafx.stage.Modality;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;

public class TelaAutenticacaoController implements Initializable {
    
    @FXML
    private Pane conteudo;
    @FXML
    private Button btnSair;
    @FXML
    private TextField txLogin;
    @FXML
    private PasswordField psSenha;
    @FXML
    private Button btnEntrar;
    @FXML
    private Hyperlink linkconta;
    @FXML
    private Label lbnovousu;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Mensagens msg = new Mensagens();
        Banco.conectar();
        ResultSet rs = Banco.getCon().consultar("select  * from funcionario");
        int rows = 0;
        try {
            while (rs.next()) {
                rows = rs.getRow();
            }
        } catch (Exception e) {
        }
        if (rows >= 1) {
            linkconta.setVisible(false);
            lbnovousu.setVisible(false);
        } else {
            linkconta.setVisible(true);
            lbnovousu.setVisible(true);
        }


    }
    
    public TelaAutenticacaoController() {
    }
    
    @FXML
    private void evtEntrar(ActionEvent event) throws SQLException {
        logar();
    }
    
    @FXML
    private void evtSair(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void keyEnter(KeyEvent event) throws SQLException {
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
    
    private void logar() throws SQLException {
        Funcionario f = new Funcionario();
        Mensagens msg = new Mensagens();
        String usuario, senha;
        
        usuario = txLogin.getText();
        senha = psSenha.getText();
        
        if (usuario.equals("")) {
            msg.Error("", "Usuário não digitado.");
            txLogin.requestFocus();
        } else if (senha.equals("")) {
            
            msg.Error("", "Senha não digitada.");
            psSenha.requestFocus();
        } else if (f.getLoginF(usuario, senha) == null) {
            msg.Error("", "Usuário não encontrado.");
            psSenha.requestFocus();
        } else if (f.getLoginF(usuario, senha).getAtivo().equals("não ativo")) {
            msg.Error("", "Usuário não ativo.");
            psSenha.requestFocus();
        } else {
            
            Banco.conectar();
            ResultSet rs = Banco.getCon().consultar("select  * FROM empresa_parametros");
            int rows = 0;
            try {
                while (rs.next()) {
                    rows = rs.getRow();
                }
                if (rows == 0) {
                    try {
                        Stage stage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("TelaEmpresaParametrosCad.fxml"));
                        Scene scene = new Scene(root);
                        stage.setTitle("Cadastro da Empresa");
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
                        stage.setResizable(false);
                        stage.showAndWait();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    
                } else {
                    try {
                        TelaInicialController.setFunc(f.getLoginF(usuario, senha));
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
                
            } catch (SQLException ex) {
                
            }
            
        }
    }
    
    @FXML
    private void evtPrimeiroAcesso(ActionEvent event) {
        try {
            Parent cadFunc = FXMLLoader.load(getClass().getResource("TelaFuncionarioCad.fxml"));
            Scene scene = new Scene(cadFunc);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Cadastro de Funcionário");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.show();
            TelaFuncionarioCadController.setPrimeiro_acesso(true);
            
        } catch (Exception e) {
        }
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import estagiocarvaobarao.entidade.Funcionario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

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

}

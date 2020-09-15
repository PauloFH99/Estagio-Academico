/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.controller.ControllerProduto;
import estagiocarvaobarao.entidade.Categoria;
import java.io.File;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class RelClienteController implements Initializable {

    @FXML
    private CheckBox chkativo;
    @FXML
    private JFXButton btngerarel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void gerar(ActionEvent event) {
        String sql = "";

        if (chkativo.isSelected()) {
            sql = "select  c.nome,c.saldo_devedor,c.limite_fiado,c.ativo "
                    + "from cliente c "
                    + "where c.ativo='Ativo' "
                    + "order by c.nome";
        } else {
            sql = "select  c.nome,c.saldo_devedor,c.limite_fiado,c.ativo "
                    + "from cliente c "
                    + "where c.ativo='Não Ativo' "
                    + "order by c.nome";
        }

        gerarRelatorio(sql, "src/relatorios/Clientes.jasper", "Lista de Clientes");
    }

    public void gerarRelatorio(String sql, String relat, String titulotela) {
        try {  //sql para obter os dados para o relatorio
            ResultSet rs = Banco.getCon().consultar(sql);
            //implementação da interface JRDataSource para DataSource ResultSet
            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            //preenchendo e chamando o relatório

            String jasperPrint = JasperFillManager.fillReportToFile(relat, null, jrRS);
            JasperViewer viewer = new JasperViewer(jasperPrint, false, false);

            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);//maximizado
            viewer.setTitle(titulotela);
            viewer.setVisible(true);
        } catch (JRException erro) {
            System.out.println(erro);
        }
    }
}

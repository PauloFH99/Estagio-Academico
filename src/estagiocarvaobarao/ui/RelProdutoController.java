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
public class RelProdutoController implements Initializable {

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
            sql = "SELECT c.descricao AS cat_nome, p.preco AS prod_preco ,p.descricao AS prod_nome,p.ativo "
                    + "FROM produto p join categoria_produto c ON  "
                    + "c.codigo = p.cod_categoria and p.ativo='true'"
                    + "GROUP BY c.descricao,p.codigo "
                    + "ORDER BY c .descricao DESC";
        } else {
            sql = "SELECT c.descricao AS cat_nome, p.preco AS prod_preco ,p.descricao AS prod_nome,p.ativo "
                    + "FROM produto p join categoria_produto c ON  "
                    + "c.codigo = p.cod_categoria and p.ativo='false'"
                    + "GROUP BY c.descricao,p.codigo "
                    + "ORDER BY c .descricao DESC";
        }

        gerarRelatorio(sql, "src/relatorios/Produtos_Cat.jasper", "Lista de Produtos");
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

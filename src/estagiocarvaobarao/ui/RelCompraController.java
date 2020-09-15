/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import estagiocarvaobarao.banco.Banco;

import estagiocarvaobarao.controller.ControllerVenda;

import estagiocarvaobarao.entidade.CondPagto;
import estagiocarvaobarao.entidade.Fornecedor;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class RelCompraController implements Initializable {

    private CheckBox chkativo;
    @FXML
    private JFXButton btngerarel;
    @FXML
    private DatePicker dpfinal;
    @FXML
    private DatePicker dpinicial;
    @FXML
    private JFXComboBox<Fornecedor> cbcfornecedor;
    @FXML
    private JFXComboBox<CondPagto> cbcond;

    ControllerVenda v = new ControllerVenda();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        v.carregarNivel(cbcond);
        v.carregarFornecedor(cbcfornecedor);
    }

    @FXML
    private void gerar(ActionEvent event) {
        String sql = "";
        sql = "select  e.codigo,e.emissao,e.total,c.descricao,f.nomefantasia "
                + "from entrada_produtos e,condpagto c,fornecedor f "
                + "where e.cod_condpagto = c.codigo and e.cod_fornecedor = f.codigo order by e.emissao";

        gerarRelatorio(sql, "src/relatorios/Compras.jasper", "Lista de Compras");
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

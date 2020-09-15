/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.banco.Banco;
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
public class RelChequesController implements Initializable {

    private CheckBox chkativo;
    @FXML
    private JFXButton btngerarel;
    @FXML
    private DatePicker dpfinal;
    @FXML
    private DatePicker dpinicial;
    @FXML
    private JFXTextField txtbcemissor;

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
        sql = "SELECT cp.codigo,cp.emissao,cp.valor,cp.emissor_cheque,f.nomefantasia "
                + "FROM contas_pagar cp,condpagto c,fornecedor f "
                + "WHERE cp.cod_condicaopagamento=c.codigo"
                + " AND c.descricao ='cheque' "
                + "AND cp.cod_fornecedor = f.codigo"
                + " ORDER BY cp.emissao DESC";

        gerarRelatorio(sql, "src/relatorios/Cheques.jasper", "Lista de Cheques");
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

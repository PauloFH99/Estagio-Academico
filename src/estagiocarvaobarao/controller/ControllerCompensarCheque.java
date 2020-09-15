/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.ContasPagar;
import static estagiocarvaobarao.ui.TelaCompensarChequeController.valores;
import static estagiocarvaobarao.ui.TelaContasPagarController.valores;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 *
 * @author Paulo
 */
public class ControllerCompensarCheque {

    public void carregatabel(TableView<ContasPagar> tabela, List<ContasPagar> valores) {
        ContasPagar cp = new ContasPagar();
        ObservableList<ContasPagar> tabcontas;
        valores = (List<ContasPagar>) cp.get("cheque");
        tabcontas = FXCollections.observableArrayList(valores);
        tabela.setItems(tabcontas);
    }

    public boolean finalizar(ContasPagar contapagarclic) {
        contapagarclic.setValor_pendente(0);
        contapagarclic.setValor_pago(contapagarclic.getValor());
        contapagarclic.setData_pago(java.sql.Date.valueOf(LocalDate.now()));
        return contapagarclic.compensarCheque(contapagarclic);
    }

    public boolean estornar(ContasPagar contaspagarclic) {

        int cod = 0;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja realmente estornar ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                cod = contaspagarclic.getCodigo();
            } catch (Exception e) {
                cod = 0;
            }
            ContasPagar cp = new ContasPagar();
            cp = cp.get(cod);
            cp.setData_pago(null);
            cp.setValor_pago(0.0);
            cp.setValor_pendente(cp.getValor());
            if (cp.estornar(cp)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void carregarConsultabaixado(String tabela, String filtro, List<ContasPagar> valores, TableView<ContasPagar> tabelaR) {
        DALConsulta dal = new DALConsulta();
        if (valores != null) {
            valores.clear();
        }
        ObservableList<ContasPagar> contp;
        valores = (List<ContasPagar>) dal.getBaixado("cheque");
        contp = FXCollections.observableArrayList(valores);
        tabelaR.setItems(contp);
    }

    public void carregarConsultaTodos(String tabela, String filtro, List<ContasPagar> valores, TableView<ContasPagar> tabelaR) {
        DALConsulta dal = new DALConsulta();
        if (valores != null) {
            valores.clear();
        }
        ObservableList<ContasPagar> contp;
        valores = (List<ContasPagar>) dal.get(tabela, "cheque");
        contp = FXCollections.observableArrayList(valores);
        tabelaR.setItems(contp);
    }

    public void carregarConsultaPendente(String tabela, String filtro, List<ContasPagar> valores, TableView<ContasPagar> tabelaR) {
        DALConsulta dal = new DALConsulta();
        if (valores != null) {
            valores.clear();
        }
        ObservableList<ContasPagar> contp;

        valores = (List<ContasPagar>) dal.getPen("cheque");
        contp = FXCollections.observableArrayList(valores);
        tabelaR.setItems(contp);
    }

    public void inicializarTabelaBusca(ContasPagar contasPagar, TableView<ContasPagar> tabela, List<ContasPagar> valores, Label lbtotal) throws ParseException {
        ObservableList<ContasPagar> tabcontas;
        valores = (List<ContasPagar>) contasPagar.getCheque(contasPagar.getCodigo());
        tabcontas = FXCollections.observableArrayList(valores);
        tabela.setItems(tabcontas);
        calcularTotal(valores, lbtotal);

    }

    public void calcularTotal(List<ContasPagar> valores, Label lbtotal) {
        double total = 0.0;
        ContasPagar aux = new ContasPagar();
        if(valores==null)
            valores=aux.get("");
        
        for (ContasPagar tot : valores) {
            total += tot.getValor();
        }

        lbtotal.setText(String.valueOf(total).replace(".", ","));
    }

    public void pesquisarContaPagar(JFXTextField txcod, TableView<ContasPagar> tabela, List<ContasPagar> valores, int cod, Label lbtotal) {
        ContasPagar contasPagar = new ContasPagar();
        
        contasPagar = contasPagar.get(cod);
        if (contasPagar != null) {
            txcod.setText(String.valueOf(contasPagar.getCodigo()));
         
            if (valores != null) {
                valores.clear();
            }
            try {
                inicializarTabelaBusca(contasPagar, tabela, valores, lbtotal);
            } catch (ParseException ex) {
                Logger.getLogger(ControllerCompensarCheque.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void carregarConsultaEmissao(String filtroini, String filtrofim, String filtrorb, String cheque, List<ContasPagar> valores, TableView<ContasPagar> tabela) {
        DALConsulta dal = new DALConsulta();
        if (valores != null) {
            valores.clear();
        }
        ObservableList<ContasPagar> contp;

        valores = (List<ContasPagar>) dal.getFiltros(filtroini, filtrofim, filtrorb, cheque);
        contp = FXCollections.observableArrayList(valores);
        tabela.setItems(contp);

    }
}

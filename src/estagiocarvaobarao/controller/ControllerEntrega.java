/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.entidade.Armazem;
import estagiocarvaobarao.entidade.ContasPagar;
import estagiocarvaobarao.entidade.Despesas;
import estagiocarvaobarao.entidade.Despesas_Entrega;
import estagiocarvaobarao.entidade.Entrega;
import estagiocarvaobarao.entidade.Funcionario;
import java.awt.Container;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;

/**
 *
 * @author Paulo
 */
public class ControllerEntrega {

    public void addDesp(JFXTextField txcoddes, JFXTextField txtipodes, JFXTextField txvalor, List<Despesas_Entrega> Despesas, TableView<Despesas_Entrega> tabelaDespesa) {
        NumberFormat nf = new DecimalFormat("#,###.00");
        boolean achou = false;
        int indexDescp = 0;
        double valorAlterar = 0.0;
        for (Despesas_Entrega despesa : Despesas) {
            if (despesa.getDespesa().getCodigo() == Integer.parseInt(txcoddes.getText())) {
                indexDescp = Despesas.indexOf(despesa);
                try {
                    valorAlterar = despesa.getValor() + nf.parse(txvalor.getText()).doubleValue();
                } catch (ParseException ex) {
                    Logger.getLogger(ControllerEntrega.class.getName()).log(Level.SEVERE, null, ex);
                }
                achou = !achou;
            }
        }
        if (achou) {
            Despesas.get(indexDescp).setValor(valorAlterar);

        } else {
            try {
                Despesas.add(new Despesas_Entrega(new Despesas(Integer.parseInt(txcoddes.getText())),
                        new Entrega(),
                        txtipodes.getText(),
                        nf.parse(txvalor.getText()).doubleValue()));
            } catch (ParseException ex) {
                Logger.getLogger(ControllerEntrega.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        atualizarTabela(tabelaDespesa, Despesas);
    }

    public void atualizarTabela(TableView<Despesas_Entrega> tabelaDespesa, List<Despesas_Entrega> Despesas) {
        ObservableList<Despesas_Entrega> prod_v = null;
        if (tabelaDespesa.getItems() != null) {
            tabelaDespesa.getItems().clear();
        }
        prod_v = FXCollections.observableArrayList(Despesas);
        tabelaDespesa.setItems(prod_v);
    }

    public List<Entrega> initTable() {
        Entrega e = new Entrega();
        return e.getInit();
    }

    public boolean finalizar(int cod, List<Despesas_Entrega> Despesas, List<Entrega> Entregas,
            LocalDate emissao, Funcionario funcionario, String rota) {
        Entrega e = new Entrega();
        if (cod == 0) {
            if (e.gravar(Despesas, Entregas, emissao, funcionario, rota)) {
                return true;
            }
        } else {
            if (e.alterar(cod, Despesas, Entregas, emissao, funcionario, rota)) {
                return true;
            }
        }
        return false;
    }

    public void carregarEntrega(Entrega entrega, DatePicker dpinicial, JFXTextField txcod, List<Despesas_Entrega> Despesas,
            List<Entrega> Entregas, JFXButton btnexcuir, JFXButton btnfinalizar, JFXButton btncancelar,
            JFXButton btnnovo, TableView<Despesas_Entrega> tabelaDespesa, TableView<Entrega> tabelaReceber, JFXComboBox<String> cbRota) {
        int ano = entrega.getEmissaoDate().getYear() + 1900;
        int mes = entrega.getEmissaoDate().getMonth() + 1;
        int dia = entrega.getEmissaoDate().getDate();
        dpinicial.setValue(LocalDate.of(ano, mes, dia));

        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter
                    = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }//To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                } //To change body of generated methods, choose Tools | Templates.
            }
        };
        dpinicial.setConverter(converter);
        ContasPagar cp = new ContasPagar();
        List<ContasPagar> listp = new ArrayList();
        List<Despesas_Entrega> de = new ArrayList();
        listp.addAll(cp.getConta(entrega.getCodigo()));
        Despesas des = new Despesas();
        Despesas.clear();
        if (!listp.isEmpty()) {
            for (ContasPagar contasp : listp) {
                Despesas.add(new Despesas_Entrega(des.get(contasp.getDespesas().getCodigo()), contasp.getDespesas().getDescricao(),
                        contasp.getValor()));
            }

            atualizarDespesa(tabelaDespesa, Despesas);
        }
        Entrega e = new Entrega();
        Entregas.clear();
        Entregas = e.getAll(entrega.getCodigo());
        atualizarEntregas(tabelaReceber, Entregas);
        cbRota.getSelectionModel().select(entrega.getRota());
        txcod.setText(String.valueOf(entrega.getCodigo()));
        btnexcuir.setDisable(false);
        btnfinalizar.setDisable(false);
    }

    public void atualizarDespesa(TableView<Despesas_Entrega> tabelaDespesa, List<Despesas_Entrega> Despesas) {
        try {
            tabelaDespesa.setVisible(true);
            ObservableList<Despesas_Entrega> contr;
            contr = FXCollections.observableArrayList(Despesas);
            tabelaDespesa.setItems(contr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void atualizarEntregas(TableView<Entrega> tabelaReceber, List<Entrega> Entregas) {
        try {
            tabelaReceber.setVisible(true);
            ObservableList<Entrega> contr;
            contr = FXCollections.observableArrayList(Entregas);
            tabelaReceber.setItems(contr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean excluir(int cod) {
        boolean aux = false;
        Armazem arm = new Armazem();
        Entrega e = new Entrega();
        ContasPagar cp = new ContasPagar();
        List<Entrega> prod = new ArrayList();
        prod = e.getProds(cod);
        for (Entrega entrega : prod) {
            if (entrega.getRetorno() > 0) {
                arm.alterar(entrega.getProduto().getCodigo(), entrega.getRetorno(), "s");
            }
        }
        aux = e.apagarProds(cod);
        if (!aux) {
            return aux;
        }
        
        aux = e.apagar(cod);
        if (!aux) {
            return aux;
        }

        aux = cp.apagarDespesa(cod);
        return aux;
    }

}

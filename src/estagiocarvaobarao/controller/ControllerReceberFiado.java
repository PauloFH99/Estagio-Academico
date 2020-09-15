/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.entidade.Cliente;
import estagiocarvaobarao.entidade.ContasReceber;
import estagiocarvaobarao.entidade.Venda;
import static estagiocarvaobarao.ui.TelaReceberFiadoController.ReceberFiado;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

/**
 *
 * @author Paulo
 */
public class ControllerReceberFiado {

    public void pesquisarCliente(int codcli, JFXTextField txcodcli, JFXTextField txclinome) {
        Cliente cliente = new Cliente();
        cliente = cliente.get(codcli);

        if (cliente != null) {
            txcodcli.setText(String.valueOf(cliente.getCodigo()));
            txclinome.setText(cliente.getNome());
        } else {
            txcodcli.setText("0");
            txclinome.setText("Valor digitado não encontrado...");
        }
    }

    public void pesquisarCliente(Cliente cli, JFXTextField txcodcli, JFXTextField txclinome) {
        Cliente cliente = new Cliente();
        cliente = cliente.get(cli.getCodigo());

        if (cliente != null) {
            txcodcli.setText(String.valueOf(cliente.getCodigo()));
            txclinome.setText(cliente.getNome());
        } else {
            txcodcli.setText("0");
            txclinome.setText("Valor digitado não encontrado...");
        }
    }

    public List<ContasReceber> buscarCli(JFXTextField txcodcli, TableView<ContasReceber> tabela, JFXTextField txsaldo) {
        ContasReceber contr = new ContasReceber();
        Cliente c = new Cliente();
        c = c.get(Integer.parseInt(txcodcli.getText()));
        List<ContasReceber> ListaFiado = contr.getFiado(Integer.parseInt(txcodcli.getText()));//Lista de fiado
        tabela.setItems(FXCollections.observableArrayList(ListaFiado));
        txsaldo.setText("" + c.getSaldo_devedor());
        return ListaFiado;
    }

    public List<Venda> buscarCli(JFXTextField txcodcli, TableView<Venda> tabelav) {
        Venda v = new Venda();
        Cliente c = new Cliente();
        c = c.get(Integer.parseInt(txcodcli.getText()));
        List<Venda> ListaFiado = v.getFiado(Integer.parseInt(txcodcli.getText()));//Lista de fiado
        tabelav.setItems(FXCollections.observableArrayList(ListaFiado));
        return ListaFiado;
    }

    public void atualizarTabela(int cod, TableView<ContasReceber> tabela, JFXTextField txsaldo) {
        ObservableList<ContasReceber> prod_v = null;
        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }
        ContasReceber aux = new ContasReceber();
        ReceberFiado = aux.getFiado(cod);
        prod_v = FXCollections.observableArrayList(ReceberFiado);
        Cliente c = new Cliente();
        c = c.get(cod);
        txsaldo.setText("" + c.getSaldo_devedor());
        tabela.setItems(prod_v);
    }

    public boolean estornar(ContasReceber contasreceberclic) {
        int cod = 0, codcli = 0;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja realmente estornar ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                cod = contasreceberclic.getCodigo();
            } catch (Exception e) {
                cod = 0;
            }
            try {
                codcli = contasreceberclic.getCliente().getCodigo();
            } catch (Exception e) {
                codcli = 0;
            }
            ContasReceber cr = new ContasReceber();
            cr = cr.get(cod);
            cr.setData_pago(null);
            cr.setValor_pago(0.0);
            cr.setValor_pendente(cr.getValor());
            if (cr.estornar(cr)) {
                Cliente c = new Cliente();
                c = c.get(codcli);
                c.alterarSaldo(c, cr.getValor(), "+");
                return true;
            }
        }
        return false;
    }

}
